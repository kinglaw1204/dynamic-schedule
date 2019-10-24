package luozhou.top.core;

import lombok.extern.slf4j.Slf4j;
import luozhou.top.config.ScheduleConfig;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @description:
 * @author: luozhou
 * @create: 2019-10-02 14:24
 **/
@Slf4j
public class DefaultWorker implements Iworker {
    private PriorityBlockingQueue<AbstractJob> queue = new PriorityBlockingQueue(10);
    private PriorityBlockingQueue<AbstractJob> persistQueue = new PriorityBlockingQueue(10);

    private DefaultWorker(PriorityBlockingQueue<AbstractJob> queue) {
        this.queue = queue;
    }

    private DefaultWorker() {

    }

    @Override
    public void addJob(AbstractJob job) {
        ScheduleConfig config =ScheduleConfig.getConfig();
        long nextSecond = job.getStrategy().getNextSecond();
        if (nextSecond > -1L) {
            job.setTimeStamp(nextSecond + System.currentTimeMillis());
            queue.add(job);
            //开启持久化模式
            if (config.isPersistence()) {
                log.info("开启了持久化模式");
                persistQueue.add(job);
            }
        }

    }

    @Override
    public AbstractJob getJob() throws InterruptedException {
        return queue.take();
    }

    @Override
    public AbstractJob getPersistJob() throws InterruptedException {
        return persistQueue.take();
    }

    private static class SingletonHolder {
        private static final Iworker iworker = new DefaultWorker();
    }

    public static Iworker getWorker() {
        return SingletonHolder.iworker;
    }
}
