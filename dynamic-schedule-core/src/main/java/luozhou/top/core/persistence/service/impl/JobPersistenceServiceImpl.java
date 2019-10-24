package luozhou.top.core.persistence.service.impl;

import luozhou.top.core.AbstractJob;
import luozhou.top.core.persistence.config.HikariCPDataSource;
import luozhou.top.core.persistence.service.JobPersistenceService;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: luozhou
 * @create: 2019-10-16 15:59
 **/
public class JobPersistenceServiceImpl implements JobPersistenceService {

    @Override
    public boolean insert(AbstractJob job,Class cls) {
        return false;
    }

    @Override
    public boolean update(AbstractJob job) {
        return false;
    }

    @Override
    public boolean delete(AbstractJob job) {
        return false;
    }

    @Override
    public List<AbstractJob> queryJobWithNotDone() {
        QueryRunner run = new QueryRunner(HikariCPDataSource.getDataSource());
        ResultSetHandler<List<AbstractJob>> handler = new ResultSetHandler<List<AbstractJob>>() {
            @Override
            public List<AbstractJob> handle(ResultSet rs) throws SQLException {
                List<AbstractJob> list = new ArrayList<>();
                while (rs.next()){
                    ResultSetMetaData meta = rs.getMetaData();
                    int cols = meta.getColumnCount();
                    try {
                        AbstractJob job = (AbstractJob) Class.forName(rs.getString(cols)).newInstance();
                        job.setId(rs.getLong(1));
                        job.setStatus(2);
                        job.setTimeStamp(3);
//                        job.setStrategy();
////                        job.setBody();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }


                }


                return list;
            }
        };
        Object[] result=null;
//        try {
//         //   result =run.query("select * from tb_job",handler);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        System.out.println(result.length);
        return null;
    }
}
