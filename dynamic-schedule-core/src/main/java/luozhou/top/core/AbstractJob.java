package luozhou.top.core;

import lombok.Data;
import luozhou.top.strategy.IStrategy;

import java.util.Objects;
import java.util.UUID;

/**
 * @description: job抽象类，所有的job必须继承此类
 * @author: luozhou
 * @create: 2019-10-02 14:53
 **/
@Data
public abstract class AbstractJob<T> implements Comparable<AbstractJob>, Runnable {
    private String id =UUID.randomUUID().toString();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractJob<?> job = (AbstractJob<?>) o;
        return timeStamp == job.timeStamp &&
                status == job.status &&
                Objects.equals(id, job.id) &&
                Objects.equals(strategy, job.strategy) &&
                Objects.equals(body, job.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, strategy, timeStamp, body, status);
    }

    @Override
    public String toString() {
        return "AbstractJob{" +
                "id=" + id +
                ", strategy=" + strategy +
                ", timeStamp=" + timeStamp +
                ", body=" + body +
                ", status=" + status +
                '}';
    }
}
