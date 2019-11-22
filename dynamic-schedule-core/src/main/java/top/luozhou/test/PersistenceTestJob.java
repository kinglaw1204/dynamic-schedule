package top.luozhou.test;

import lombok.extern.slf4j.Slf4j;
import top.luozhou.constant.JobStatus;
import top.luozhou.core.AbstractJob;

/**
 * @description:
 * @author: luozhou
 * @create: 2019-10-16 17:32
 **/
@Slf4j
public class PersistenceTestJob extends AbstractJob<Teacher> {
    @Override
    public void run() {
        log.info("我执行啦{}",getStatus());
        log.info("获取数据实体{}",getBody());
        setStatus(JobStatus.COMPLETED.getStatus());
    }

    @Override
    public String toString() {
        return super.toString();
    }
    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
       return super.hashCode();
    }

}
