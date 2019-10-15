package luozhou.top.core;

/**
 * @description: job 工作器
 * @author: luozhou
 * @create: 2019-09-09 15:56
 **/
public interface Iworker {

    /**
     * @param job
     */
    void addJob(AbstractJob job);

    /**
     * @return job
     */
    AbstractJob getJob() throws InterruptedException;
}
