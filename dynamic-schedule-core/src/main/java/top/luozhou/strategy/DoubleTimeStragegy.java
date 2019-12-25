package top.luozhou.strategy;

import lombok.Data;

/**
 * @description: 双倍策略模式，第二次执行时间间隔是前一次的2倍
 * @author: luozhou
 * @create: 2019-11-27 17:56
 **/
@Data
public class DoubleTimeStragegy implements IStrategy {
    /**
     * 基础间隔，单位是毫秒，比如baseTime为1000ms，第二次就是2000ms，第三次就是4000ms
     */
    private  long baseTime;
    /**
     * 执行次数，如果不设置或者设置小于等于0的数就是无限执行
     **/
    private int times;

    private  volatile int count;

    public DoubleTimeStragegy(Long baseTime, int times) {
        this.baseTime = baseTime;
        this.times = times;
    }

    public DoubleTimeStragegy() {
    }

    @Override
    public Long doGetNextSecond() {
        if (count++ >= times && times > 0) {
            return -1L;
        }
        if (count == 1) {
            return baseTime;
        }
        baseTime = baseTime * 2;
        return baseTime;
    }

    @Override
    public Long doGetCurrentSecond() {

        return baseTime;
    }
}
