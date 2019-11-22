package top.luozhou.core;

import lombok.extern.slf4j.Slf4j;
import top.luozhou.config.ScheduleConfig;
import top.luozhou.constant.JobOperationType;
import top.luozhou.constant.JobStatus;
import top.luozhou.core.persistence.async.AsyncService;
import top.luozhou.core.persistence.service.JobPersistenceService;
import top.luozhou.core.persistence.service.impl.JobPersistenceServiceImpl;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 调度器, 调度器只保证任务在正确的时间内调度到业务系统，业务系统如果处理逻辑时间大于下次调度的时间，就会出现并发情况，业务层需要做好幂等性或者线程安全。
 * @author: luozhou
 * @create: 2019-09-09 15:52
 **/
@Slf4j
public class Scheduler implements Runnable, Comparable {


    private Iworker worker;

    private ThreadPoolExecutor executor;
    private ThreadPoolExecutor dbThreadPool;
    private JobPersistenceService service = new JobPersistenceServiceImpl();

    public Scheduler(Iworker worker, ThreadPoolExecutor executor) {
        this.worker = worker;
        this.executor = executor;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                AbstractJob job = worker.getJob();

                if (JobStatus.FINISHED.getStatus() == job.getStatus()) {
                    log.debug("job【{}】任务结束", job.getClass().getName());
                    continue;
                }
                Long time = job.getTimeStamp() - System.currentTimeMillis();
                if (time > 0) {
                    Thread.sleep(time);
                }
                if (JobStatus.FINISHED.getStatus() != job.getStatus()) {
                    executor.submit(job);
                    worker.addJob(job);
                }
                //完成后就删除job
                if (ScheduleConfig.getConfig().isPersistence() && (JobStatus.FINISHED.getStatus() == job.getStatus()
                        || JobStatus.COMPLETED.getStatus() == job.getStatus())) {
                    dbThreadPool.submit(new AsyncService(service, JobOperationType.DELETE, job));
                    worker.removePersistJob(job);
                    log.debug("删除job{}", job.toString());
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setDbThreadPool(ThreadPoolExecutor dbThreadPool) {
        this.dbThreadPool = dbThreadPool;
    }
}
