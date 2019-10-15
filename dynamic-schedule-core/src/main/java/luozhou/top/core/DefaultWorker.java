package luozhou.top.core;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @description:
 * @author: luozhou
 * @create: 2019-10-02 14:24
 **/
public class DefaultWorker implements Iworker {
    private PriorityBlockingQueue<AbstractJob> queue = new PriorityBlockingQueue(10);

    private DefaultWorker(PriorityBlockingQueue<AbstractJob> queue) {
        this.queue = queue;
    }

    private DefaultWorker() {

    }

    @Override
    public void addJob(AbstractJob job) {
        long nextSecond = job.getStrategy().getNextSecond();
        if (nextSecond > -1L) {
            job.setTimeStamp(nextSecond + System.currentTimeMillis());
            queue.add(job);
        }
    }

    @Override
    public AbstractJob getJob() throws InterruptedException {
        return queue.take();
    }

    private static class SingletonHolder {
        private static final Iworker iworker = new DefaultWorker();
    }

    public static Iworker getWorker() {
        return SingletonHolder.iworker;
    }
}
