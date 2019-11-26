package top.luozhou.core;

import lombok.extern.slf4j.Slf4j;
import top.luozhou.config.OprationConfig;
import top.luozhou.constant.JobStatus;

/**
 * @description: 调度器, 调度器只保证任务在正确的时间内调度到业务系统，业务系统如果处理逻辑时间大于下次调度的时间，就会出现并发情况，业务层需要做好幂等性或者线程安全。
 * @author: luozhou
 * @create: 2019-09-09 15:52
 **/
@Slf4j
public class Scheduler implements Runnable, Comparable {


    private OprationConfig config;

    public Scheduler(OprationConfig config) {
        this.config = config;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public void run() {
        while (true) {
            try {
                AbstractJob job = config.getWorker().getJob();
                getJobStatusByStatus(job.getStatus()).doOpration(job, config);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private JobStatus getJobStatusByStatus(int status) {
        switch (status) {
            case 0:
                return JobStatus.START;
            case 1:
                return JobStatus.RUNNING;
            case 2:
                return JobStatus.COMPLETED;
            case 3:
                return JobStatus.FINISHED;
            default:
                return JobStatus.START;
        }
    }

}
