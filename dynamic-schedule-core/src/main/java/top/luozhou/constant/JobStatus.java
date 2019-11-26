package top.luozhou.constant;

import lombok.extern.slf4j.Slf4j;
import top.luozhou.config.OprationConfig;
import top.luozhou.core.AbstractJob;
import top.luozhou.core.persistence.async.AsyncService;

/**
 * @description: job status
 * status:===========start------>running----->completed------->stop---*
 * |                                                                 |
 * |                                                                 |
 * |                                                                 |
 * *-----------------------------------------------------------------*
 * @author: luozhou
 * @create: 2019-10-14 15:39
 **/
@Slf4j
public enum JobStatus {
    /**
     * 开始状态，job默认开始状态
     */
    START(0) {
        @Override
        public void doOpration(AbstractJob job, OprationConfig config) {
            doRun(job, config);
        }
    },
    /**
     * job运行状态，job正在处理
     */
    RUNNING(1) {
        @Override
        public void doOpration(AbstractJob job, OprationConfig config) {
            doRun(job, config);
        }
    },
    /**
     * job 完成状态
     */
    COMPLETED(2) {
        @Override
        public void doOpration(AbstractJob job, OprationConfig config) {
            doComplieted(job, config);
        }
    },
    /**
     * job 结束状态，此状态下job不再执行下一条策略
     */
    FINISHED(3) {
        @Override
        public void doOpration(AbstractJob job, OprationConfig config) {
            doComplieted(job, config);
        }
    };

    private int status;

    JobStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public abstract void doOpration(AbstractJob job, OprationConfig config);

    protected void doComplieted(AbstractJob job, OprationConfig config) {
        config.getDbThreadPool().submit(new AsyncService(config.getService(), JobOperationType.DELETE, job));
        config.getWorker().removePersistJob(job);
    }

    protected void doRun(AbstractJob job, OprationConfig config) {
        try {
            Long time = job.getTimeStamp() - System.currentTimeMillis();
            if (time > 0) {
                Thread.sleep(time);
            }
            config.getExecutor().submit(job);
            config.getWorker().addJob(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
