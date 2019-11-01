package luozhou.top.strategy;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @description: 多次执行策略
 * @author: luozhou
 * @create: 2019-10-15 09:22
 **/
@Slf4j
@Data
public class MultiTimesStragegy implements IStrategy {
    /**
     * 设置执行时间间隔，单位是毫秒，比如间隔是一秒，执行三次，数组设置元素为 [1000,2000,3000] */
    private long[] times;
    private int pos;

    public MultiTimesStragegy(long[] times) {
        this.times = times;
    }

    public MultiTimesStragegy() {
    }

    @Override
    public  Long doGetNextSecond() {
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
    public Long doGetCurrentSecond() {
        if (times == null){
            times =new long[1];
        }
        return times[pos];
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(new MultiTimesStragegy()));
    }
}
