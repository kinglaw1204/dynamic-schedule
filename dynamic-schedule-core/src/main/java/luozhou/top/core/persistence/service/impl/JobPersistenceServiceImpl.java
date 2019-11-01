package luozhou.top.core.persistence.service.impl;

import com.alibaba.fastjson.JSON;
import luozhou.top.constant.JobStatus;
import luozhou.top.core.AbstractJob;
import luozhou.top.core.persistence.config.HikariCPDataSource;
import luozhou.top.core.persistence.service.JobPersistenceService;
import luozhou.top.core.serialize.DefaultSerializer;
import luozhou.top.strategy.IStrategy;
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
    public boolean insert(AbstractJob job) {
        if (job == null) {
            return false;
        }
        QueryRunner run = new QueryRunner(HikariCPDataSource.getDataSource());
        String sql = "insert into tb_job (id, status,timestamp,strategy,body,class_strategy,class_body,class_job) values (?,?,?,?,?,?,?,?)";
        int n = 0;
        try {
            n = run.update(sql, job.getId(), job.getStatus(), job.getTimeStamp(), JSON.toJSONString(job.getStrategy()), JSON.toJSONString(job.getBody()),
                    job.getStrategy().getClass().getName(), job.getBody().getClass().getName(), job.getClass().getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }


    @Override
    public boolean update(AbstractJob job) {
        QueryRunner run = new QueryRunner(HikariCPDataSource.getDataSource());
        String sql = " update tb_job set status = ? ,timestamp = ? ,strategy = ? where id = ? ";
        int n = 0;
        try {
            n = run.update(sql, job.getStatus(), job.getTimeStamp(), JSON.toJSONString(job.getStrategy()),job.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    @Override
    public boolean delete(AbstractJob job) {
        QueryRunner run = new QueryRunner(HikariCPDataSource.getDataSource());
        String sql = "delete from tb_job where id = ?";
        int n = 0;
        try {
            n = run.update(sql, job.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    @Override
    public List<AbstractJob> queryJobWithNotDone() {
        QueryRunner run = new QueryRunner(HikariCPDataSource.getDataSource());
        ResultSetHandler<List<AbstractJob>> handler = new ResultSetHandler<List<AbstractJob>>() {
            @Override
            public List<AbstractJob> handle(ResultSet rs) throws SQLException {
                List<AbstractJob> list = new ArrayList<>();
                while (rs.next()) {
                    ResultSetMetaData meta = rs.getMetaData();
                    int cols = meta.getColumnCount();
                    try {
                        AbstractJob job = (AbstractJob) Class.forName(rs.getString("class_job")).newInstance();
                        job.setId(rs.getLong("id"));
                        job.setStatus(rs.getInt("status"));
                        job.setTimeStamp(rs.getLong("timestamp"));
                        job.setBody(new DefaultSerializer().toObj(rs.getString("body"), Class.forName(rs.getString("class_body"))));
                        job.setStrategy((IStrategy) new DefaultSerializer().toObj(rs.getString("strategy"), Class.forName(rs.getString("class_strategy"))));
                        list.add(job);
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
        List<AbstractJob> result = null;
        try {
            result = run.query("select * from tb_job where status = ? or status = ? ", handler,JobStatus.START.getStatus(),JobStatus.RUNNING.getStatus());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


}
