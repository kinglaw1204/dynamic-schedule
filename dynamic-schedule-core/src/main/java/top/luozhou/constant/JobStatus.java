package top.luozhou.constant;

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
public enum JobStatus {
    /**
     * 开始状态，job默认开始状态
     */
    START(0),
    /**
     * job运行状态，job正在处理
     */
    RUNNING(1),
    /**
     * job 完成状态
     */
    COMPLETED(2),
    /**
     * job 结束状态，此状态下job不再执行下一条策略*/
    FINISHED(3);

    private int status;

    JobStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
