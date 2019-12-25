package luozhou.top;

import top.luozhou.config.ScheduleConfig;
import top.luozhou.core.AbstractJob;
import top.luozhou.core.persistence.service.JobPersistenceService;
import top.luozhou.core.persistence.service.impl.JobPersistenceServiceImpl;
import top.luozhou.strategy.MultiTimesStragegy;
import top.luozhou.test.PersistenceTestJob;
import top.luozhou.test.Teacher;
import org.junit.Test;

import java.util.List;

/**
 * @description:
 * @author: luozhou
 * @create: 2019-10-15 11:28
 **/
public class DBTest {

    @Test
    public void dbTest() {
        ScheduleConfig config = new ScheduleConfig();
        config.setPersistence(true);
        config.setJdbcUrl("jdbc:sqlite:/Users/luozhou/Documents/project/dynamic-schedule/dynamic-schedule.sqlite");
        config.init();
        JobPersistenceService service = new JobPersistenceServiceImpl();
          List<AbstractJob> ls= service.queryAllJob();
        System.out.println(ls==null?"null":ls.size());
    }
    @Test
    public void dbinsert(){
        PersistenceTestJob job = new PersistenceTestJob();
        job.setStrategy(new MultiTimesStragegy(new long[]{1000,2000,3000,4000}));
        job.setTimeStamp(20000);
        job.setBody(new Teacher("luozhou"));
        JobPersistenceService service = new JobPersistenceServiceImpl();
        service.insert(job);
    }
    @Test
    public void update(){
        PersistenceTestJob job = new PersistenceTestJob();
//        job.setStatus(0);
        job.setStrategy(new MultiTimesStragegy(new long[]{1000,2000,3000,4000}));
        job.setTimeStamp(20000);
        JobPersistenceService service = new JobPersistenceServiceImpl();
        service.update(job);
    }
    @Test
    public void delete() {
        PersistenceTestJob job = new PersistenceTestJob();
//        job.setStatus(0);
        job.setStrategy(new MultiTimesStragegy(new long[]{1000, 2000, 3000, 4000}));
        job.setTimeStamp(20000);
        JobPersistenceService service = new JobPersistenceServiceImpl();
        service.delete(job);
    }
}
