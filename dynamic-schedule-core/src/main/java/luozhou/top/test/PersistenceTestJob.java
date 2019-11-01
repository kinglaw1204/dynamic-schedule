package luozhou.top.test;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import luozhou.top.core.AbstractJob;

/**
 * @description:
 * @author: luozhou
 * @create: 2019-10-16 17:32
 **/
@Slf4j
@Data
public class PersistenceTestJob extends AbstractJob<Teacher> {
    @Override
    public void run() {
        log.info("我执行啦{}");
    }
}
