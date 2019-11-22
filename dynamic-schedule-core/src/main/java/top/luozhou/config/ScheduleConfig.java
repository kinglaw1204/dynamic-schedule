package top.luozhou.config;

import lombok.Data;
import top.luozhou.core.AbstractJob;
import top.luozhou.core.DefaultWorker;
import top.luozhou.core.Iworker;
import top.luozhou.core.Scheduler;
import top.luozhou.core.persistence.config.HikariCPDataSource;
import top.luozhou.core.persistence.service.JobPersistenceService;
import top.luozhou.core.persistence.service.impl.JobPersistenceServiceImpl;
import top.luozhou.pool.ScheduleThreadPoolFactory;
import top.luozhou.strategy.IStrategy;

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
    private static final String PERSISTENCE_POOL_NAME = "db_persistence";
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
        if (persistence) {
            HikariCPDataSource.getHikariCPDataSource().init(this);
            ThreadPoolExecutor dbThreadPool = scheduleThreadPoolFactory.createThreadPool(PERSISTENCE_POOL_NAME, 1, 2, 0L, TimeUnit.SECONDS);
            scheduler.setDbThreadPool(dbThreadPool);
            DefaultWorker.getWorker().setDbThreadPool(dbThreadPool);
            freshJob();
        }
        config = this;

    }

    public static ScheduleConfig getConfig() {
        return config;
    }

    /**
     * 刷新未完成的job到工作队列中
     */
    private void freshJob() {
        JobPersistenceService service = new JobPersistenceServiceImpl();
        List<AbstractJob> ls = service.queryAllJob();
        Iworker worker = DefaultWorker.getWorker();
        if (ls != null && ls.size() > 0) {
            ls.forEach(item -> {
                if (persistence) {
                    worker.addPersistJob(item);
                }
                worker.addJob(item);
            });
        }
    }
}


