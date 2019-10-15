package luozhou.top.pool;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 线程池工厂类接口
 * @author: luozhou
 * @create: 2019-08-20 16:52
 **/
public interface IThreadPoolFactory {
    /**
     * @Description: 创建线程池
     * @Param: poolName 线程池名称
     * @Param: corePoolSize 核心线程数
     * @Param: maximumPoolSize 最大线程数
     * @Param: keepAliveTime 空闲线程存活时间
     * @Param: unit 单位
     * @return: 线程实例
     * @Author: luozhou
     * @Date: 2019/8/20
     */
    ThreadPoolExecutor createThreadPool(String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit);

    /**
     * @Description: 根据名称获取线程池
     * @Param: poolName  线程池名称
     * @return: 线程池实例
     * @Author: luozhou
     * @Date: 2019/8/20
     */
    ThreadPoolExecutor getThreadPool(String poolName);
}
