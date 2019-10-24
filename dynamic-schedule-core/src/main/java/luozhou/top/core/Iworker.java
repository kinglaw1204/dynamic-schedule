package luozhou.top.core;

/**
 * @description: job 工作器
 * @author: luozhou
 * @create: 2019-09-09 15:56
 **/
public interface Iworker {

    /** 添加job
     * @param job
     */
    void addJob(AbstractJob job);

    /** 获取任务job
     * @return job
     */
    AbstractJob getJob() throws InterruptedException;


    /** 获取持久化job
     * @return job
     * @throws InterruptedException
     */
    AbstractJob getPersistJob() throws InterruptedException;
}
