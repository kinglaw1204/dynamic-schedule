package luozhou.top.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 消息线程池工厂，维护线程池
 * @author: luozhou
 * @create: 2019-08-20 16:26
 **/
public class ScheduleThreadPoolFactory implements IThreadPoolFactory {
    /**
     * 线程池管理map
     */
    private Map<String, ThreadPoolExecutor> ThreadPools = new HashMap<>(10);

    private int defaultCorePoolSize = 4;

    private int defaultMaximumPoolSize = 10;

    private long defaultKeepAliveTime = 0L;

    private TimeUnit defaultTimeUnit = TimeUnit.MILLISECONDS;

    @Override
    public ThreadPoolExecutor createThreadPool(String poolName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat(poolName + "dynamic-schedule-pool-%d").build();
        ScheduleThreadPoolExecutor pool = new ScheduleThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, unit,
                new PriorityBlockingQueue(), namedThreadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        ThreadPools.put(poolName, pool);

        return pool;
    }

    @Override
    public ThreadPoolExecutor getThreadPool(String name) {
        if (ThreadPools.containsKey(name)) {
            return ThreadPools.get(name);
        }
        return createThreadPool(name, defaultCorePoolSize, defaultMaximumPoolSize, defaultKeepAliveTime, defaultTimeUnit);
    }
}
