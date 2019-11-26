package top.luozhou.config;

import lombok.Data;
import top.luozhou.core.Iworker;
import top.luozhou.core.persistence.service.JobPersistenceService;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 数据操作需要的相关依赖
 * @author: luozhou
 * @create: 2019-11-25 14:52
 **/
@Data
public class OprationConfig {
    private Iworker worker;
    private ThreadPoolExecutor executor;
    private ThreadPoolExecutor dbThreadPool;
    private JobPersistenceService service ;

    public OprationConfig(Iworker worker, ThreadPoolExecutor executor) {
        this.worker = worker;
        this.executor = executor;
    }
}
