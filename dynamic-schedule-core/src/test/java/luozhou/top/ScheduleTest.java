package luozhou.top;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import top.luozhou.config.ScheduleConfig;
import top.luozhou.core.AbstractJob;
import top.luozhou.core.DefaultWorker;
import top.luozhou.core.Iworker;
import top.luozhou.strategy.DoubleTimeStragegy;
import top.luozhou.test.PersistenceTestJob;
import top.luozhou.test.Teacher;

import java.time.format.DateTimeFormatter;

/**
 * @description:
 * @author: luozhou
 * @create: 2019-09-10 11:44
 **/
@Slf4j
public class ScheduleTest {
    DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Test
    public void test() {
        //初始化调度器
        ScheduleConfig config = new ScheduleConfig();
        config.setPersistence(true);
        config.setJdbcUrl("jdbc:sqlite:/Users/luozhou/Documents/project/dynamic-schedule/dynamic-schedule.sqlite");
        config.init();

        Iworker worker = DefaultWorker.getWorker();
//        for (int i = 0; i <10 ; i++) {
            AbstractJob<Teacher> job = new PersistenceTestJob();
            job.setBody(new Teacher("小粥老师"));
            job.setStrategy(new DoubleTimeStragegy(1000L,2));
            worker.addJob(job);
//        }

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
