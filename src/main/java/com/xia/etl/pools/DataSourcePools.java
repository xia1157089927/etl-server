package com.xia.etl.pools;

import com.xia.etl.db.DataBaseEntity;
import com.xia.etl.db.util.SpringJdbcUntil;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据源连接池
 */
public class DataSourcePools {

    /**
     * 数据源池
     */
    private final static Map<String, HikariDataSource> DATASOURCE_POOL = new ConcurrentHashMap<>();

    /**
     * JdbcTemplate池
     */
    private final static Map<String, SpringJdbcUntil> JDBCUTIL_POOL = new ConcurrentHashMap<>();

    /**
     * 数据源引用情况，引用计数器，当数据库连接用完后 便于关闭 JdbcTemplate和DataSource
     */
    private final static Map<String, String> JDBCUTIL_REF = new ConcurrentHashMap<>();

    public synchronized static void createDataSource(DataBaseEntity dataBaseEntity){
        String dbAlias = dataBaseEntity.getDbAlias();

        //当前dataSource不存在时, 则创建该dataSource，并且加入数据源池中，以便复用
        if(!DATASOURCE_POOL.containsKey(dbAlias)){
            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setPoolName(dbAlias);
            dataSource.setConnectionTestQuery(dataBaseEntity.getValidationQuery());
            dataSource.setJdbcUrl(dataBaseEntity.getUrl());
            dataSource.setDriverClassName(dataBaseEntity.getClassName());
            dataSource.setUsername(dataBaseEntity.getUsername());
            dataSource.setPassword(dataBaseEntity.getPassword());
            dataSource.setMaximumPoolSize(20);

            DATASOURCE_POOL.put(dbAlias, dataSource);
            JDBCUTIL_POOL.put(dbAlias, new SpringJdbcUntil(dataSource));
        }

    }

    /**
     * 返回对应的 JdbcTemplate
     * @param ref 引用方信息
     * @param dataBaseEntity //数据库连接信息
     * @return
     */
    public static SpringJdbcUntil getSpringJdbcUntil(String ref, DataBaseEntity dataBaseEntity) {
        String dbAlias = dataBaseEntity.getDbAlias();
        // 数据库连接不存在时，创建数据库连接
        if(!DATASOURCE_POOL.containsKey(dbAlias)){
            createDataSource(dataBaseEntity);
        }
        // 数据库引用加入计数器中
        JDBCUTIL_REF.put(ref, dbAlias);
        return JDBCUTIL_POOL.get(dbAlias);
    }

    /**
     * 销毁数据源
     * @param ref
     */
    public synchronized static void destroyJdbcTemplate(String ref, String dbAlias){
        //从计数器中移除,对应的引用
        JDBCUTIL_REF.remove(ref, dbAlias);
        // 如果该数据源不存在其他引用,释放数据库连接
        if(!JDBCUTIL_REF.containsValue(dbAlias)){
            JDBCUTIL_POOL.remove(dbAlias);
            HikariDataSource dataSource = DATASOURCE_POOL.remove(dbAlias);
            dataSource.close();
        }
    }

}
