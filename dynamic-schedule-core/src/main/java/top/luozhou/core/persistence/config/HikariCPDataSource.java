package top.luozhou.core.persistence.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import top.luozhou.config.ScheduleConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description: 数据库连接池配置
 * @author: luozhou
 * @create: 2019-10-15 11:00
 **/
public class HikariCPDataSource {
    private static HikariDataSource ds;

    public void init(ScheduleConfig scheduleConfig) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(scheduleConfig.getJdbcUrl());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private static class SingletonHolder {
        private static final HikariCPDataSource source = new HikariCPDataSource();
    }

    public static HikariCPDataSource getHikariCPDataSource() {
        return SingletonHolder.source;
    }

    public static DataSource getDataSource() {
        return ds;
    }

    private HikariCPDataSource() {
    }
}
