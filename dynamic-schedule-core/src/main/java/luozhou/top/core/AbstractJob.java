package luozhou.top.core;

import lombok.Data;
import luozhou.top.strategy.IStrategy;
import luozhou.top.utils.IdWorker;

/**
 * @description: job抽象类，所有的job必须继承此类
 * @author: luozhou
 * @create: 2019-10-02 14:53
 **/
@Data
public abstract class AbstractJob<T> implements Comparable<AbstractJob>,Runnable {
    private long id =IdWorker.getWorker().nextId();
    private IStrategy strategy;
    private volatile long timeStamp;
    private T body;
    private volatile int status;


    @Override
    public void run() {

    }

    @Override
    public int compareTo(AbstractJob o) {
        return (int) (this.timeStamp - o.getTimeStamp());
    }

    public IStrategy getStrategy() {
        if (strategy == null) {
           throw new IllegalArgumentException("Job's strategy is null,please set strategy for job");
        }
        return strategy;
    }
}
