package luozhou.top.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 多次执行策略
 * @author: luozhou
 * @create: 2019-10-15 09:22
 **/
@Slf4j
public class MultiTimesStragegy implements IStrategy {
    /**
     * 设置执行时间间隔，单位是毫秒，比如间隔是一秒，执行三次，数组设置元素为 [1000,2000,3000] */
    private long[] times;
    private int pos = 0;

    @Override
    public Long getNextSecond() {
        if (null == times || times.length<=0){
            log.error("执行策略时间为空，请设置执行策略时间！");
            return -1L;
        }
        while (pos < times.length) {
            return times[pos++];
        }
        return -1L;
    }

    @Override
    public Long getCurrentSecond() {
        return times[pos];
    }

    public long[] getTimes() {
        return times;
    }

    public void setTimes(long[] times) {
        this.times = times;
    }

    public int getPos() {
        return pos;
    }

}
