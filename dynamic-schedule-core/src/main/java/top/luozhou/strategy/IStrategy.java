package top.luozhou.strategy;

/**
 * @description: 动态执行规则器
 * @author: luozhou
 * @create: 2019-10-02 15:50
 **/
public interface IStrategy {
    /**
     * 获取下次执行时间
     *
     * @return 距离现在执行的秒数
     */
    Long doGetNextSecond();

    /**
     * 获取当前的执行时间
     *
     * @return
     */
    Long doGetCurrentSecond();
}
