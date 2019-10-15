package luozhou.top.strategy;

/**
 * @description:
 * @author: luozhou
 * @create: 2019-10-02 15:55
 **/
public class OneMinuteThreeTimesStrategy implements IStrategy {

    private long[] times = {1000, 2000, 3000};
    private int pos = 0;

    @Override
    public Long getNextSecond() {
        while (pos < times.length) {
            return times[pos++];
        }
        return -1L;
    }

    @Override
    public Long getCurrentSecond() {
        return times[pos];
    }
}
