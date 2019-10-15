package luozhou.top.core;

import lombok.extern.slf4j.Slf4j;
import luozhou.top.constant.JobStatus;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 调度器,调度器只保证任务在正确的时间内调度到业务系统，业务系统如果处理逻辑时间大于下次调度的时间，就会出现并发情况，业务层需要做好幂等性或者线程安全。
 * @author: luozhou
 * @create: 2019-09-09 15:52
 **/
@Slf4j
public class Scheduler implements Runnable, Comparable {


    private Iworker worker;

    private ThreadPoolExecutor executor;

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
                    log.info("job【{}】任务结束", job.getClass().getName());
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
