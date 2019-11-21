package luozhou.top.config;

import lombok.Data;
import luozhou.top.core.AbstractJob;
import luozhou.top.core.DefaultWorker;
import luozhou.top.core.Iworker;
import luozhou.top.core.Scheduler;
import luozhou.top.core.persistence.service.JobPersistenceService;
import luozhou.top.core.persistence.service.impl.JobPersistenceServiceImpl;
import luozhou.top.pool.ScheduleThreadPoolFactory;
import luozhou.top.strategy.IStrategy;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description: 配置类
 * @author: luozhou
 * @create: 2019-09-10 11:36
 **/
@Data
public class ScheduleConfig {
    private static final String EXECUTOR_POOL_NAME = "executor-pool";
    private static final String JOB_POOL_NAME = "job-pool";
    private static ScheduleConfig config;
    /***默认核心线程数*/
    private int defaultCoreThreadNum = 4;
    /**
     * 默认最大工作线程数
     */
    private int defaultMaxThreadNum = 10;
    /**
     * 默认的调度策略
     */
    private IStrategy defaultStrategy;
    /**
     * 持久化模式
     */
    private boolean persistence = false;
    /**
     * sqllite jdbcUrl
     */
    private String jdbcUrl;

    public ScheduleConfig() {
    }

    public ScheduleConfig(int defaultCoreThreadNum, int defaultMaxThreadNum) {
        this.defaultCoreThreadNum = defaultCoreThreadNum;
        this.defaultMaxThreadNum = defaultMaxThreadNum;
    }

    public void init() {
        ScheduleThreadPoolFactory scheduleThreadPoolFactory = new ScheduleThreadPoolFactory();
        ThreadPoolExecutor executor = scheduleThreadPoolFactory.createThreadPool(EXECUTOR_POOL_NAME, defaultCoreThreadNum, defaultMaxThreadNum, 0L, TimeUnit.SECONDS);
        Scheduler scheduler = new Scheduler(DefaultWorker.getWorker(), executor);
        ThreadPoolExecutor jobPool = scheduleThreadPoolFactory.createThreadPool(JOB_POOL_NAME, 1, 1, 0L, TimeUnit.SECONDS);
        jobPool.submit(scheduler);
        config = this;
        freshJob();

    }

    public static ScheduleConfig getConfig() {
        return config;
    }

    /**
     * 刷新未完成的job到工作队列中
     */
    private void freshJob() {
        JobPersistenceService service = new JobPersistenceServiceImpl();
        List<AbstractJob> ls = service.queryJobWithNotDone();
        Iworker worker = DefaultWorker.getWorker();
        if (ls != null && ls.size() > 0) {
            ls.forEach(item -> {
                if (persistence){
                    worker.addPersistJob(item);
                }
                worker.addJob(item);
            });
        }
    }
}


