package top.luozhou.core;

import lombok.extern.slf4j.Slf4j;
import top.luozhou.config.ScheduleConfig;
import top.luozhou.constant.JobOperationType;
import top.luozhou.constant.JobStatus;
import top.luozhou.core.persistence.async.AsyncService;
import top.luozhou.core.persistence.service.JobPersistenceService;
import top.luozhou.core.persistence.service.impl.JobPersistenceServiceImpl;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description:
 * @author: luozhou
 * @create: 2019-10-02 14:24
 **/
@Slf4j
public class DefaultWorker implements Iworker {
    private static PriorityBlockingQueue<AbstractJob> queue = new PriorityBlockingQueue(10);
    private static PriorityBlockingQueue<AbstractJob> persistQueue = new PriorityBlockingQueue(10);
    private JobPersistenceService service = new JobPersistenceServiceImpl();
    private ThreadPoolExecutor dbThreadPool;

    private DefaultWorker() {

    }

    @Override
    public void addJob(AbstractJob job) {
        ScheduleConfig config = ScheduleConfig.getConfig();
        long nextSecond = job.getStrategy().doGetNextSecond();
        if (nextSecond <= -1L) {
            //执行策略全部执行完，设置job状态为结束
            job.setStatus(JobStatus.FINISHED.getStatus());
            //提交删除持久化job
            if (config.isPersistence()) {
                dbThreadPool.submit(new AsyncService(service, JobOperationType.DELETE, job));
            }
            return;
        }
        job.setTimeStamp(nextSecond + System.currentTimeMillis());
        queue.add(job);

        if (!config.isPersistence()) {
            return;
        }
        //开启持久化模式
        log.debug("开启了持久化模式");
        if (!persistQueue.contains(job)) {
            addPersistJob(job);
            log.debug("插入job{}", job.toString());
            dbThreadPool.submit(new AsyncService(service, JobOperationType.INSERT, job));

        } else {
            log.debug("更新job{}", job.toString());
            dbThreadPool.submit(new AsyncService(service, JobOperationType.UPDATE, job));

        }

    }

    @Override
    public void addPersistJob(AbstractJob job) {
        persistQueue.add(job);
    }

    @Override
    public AbstractJob getJob() throws InterruptedException {
        return queue.take();
    }

    @Override
    public void removePersistJob(AbstractJob job) {
        persistQueue.remove(job);
    }

    private static class SingletonHolder {
        private static final DefaultWorker iworker = new DefaultWorker();
    }

    public static DefaultWorker getWorker() {
        return SingletonHolder.iworker;
    }

    public void setDbThreadPool(ThreadPoolExecutor dbThreadPool) {
        this.dbThreadPool = dbThreadPool;
    }
}
