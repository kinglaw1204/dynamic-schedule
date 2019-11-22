package luozhou.top.core.persistence.async;

import luozhou.top.constant.JobOperationType;
import luozhou.top.core.AbstractJob;
import luozhou.top.core.persistence.service.JobPersistenceService;

/**
 * @description: 异步持久化操作
 * @author: luozhou
 * @create: 2019-11-22 09:45
 **/
public class AsyncService implements Runnable {
    private JobPersistenceService service;
    private JobOperationType type;
    private AbstractJob job;

    public AsyncService(JobPersistenceService service, JobOperationType type, AbstractJob job) {
        this.service = service;
        this.type = type;
        this.job = job;
    }

    @Override
    public void run() {
        if (service != null) {
            switch (type) {
                case DELETE:
                    service.delete(job);
                    break;
                case INSERT:
                    service.insert(job);
                    break;
                case UPDATE:
                    service.update(job);
                    break;
                default:
                    break;
            }
        }
    }
}
