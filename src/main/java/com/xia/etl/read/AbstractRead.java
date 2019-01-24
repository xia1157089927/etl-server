package com.xia.etl.read;

import com.xia.etl.Page;
import com.xia.etl.db.DataBaseEntity;
import com.xia.etl.pools.DataSourcePools;
import com.xia.etl.db.util.SpringJdbcUntil;

public abstract class AbstractRead implements Read {
    /**
     * //数据库信息
     */
    private DataBaseEntity dataBaseEntity;
    /**
     * //抽取相关的SQL
     */
    private String sql;

    /**
     * 相关表名
     */
    private String tableName;

    /**
     * //分页参数
     */
    private Page page;

    public DataBaseEntity getDataBaseEntity() {
        return dataBaseEntity;
    }

    public void setDataBaseEntity(DataBaseEntity dataBaseEntity) {
        this.dataBaseEntity = dataBaseEntity;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void initPage(Page page){
        String tableName = getTableName();
        DataBaseEntity dataBaseEntity = getDataBaseEntity();
        SpringJdbcUntil jdbcUntil = DataSourcePools.getSpringJdbcUntil(tableName, dataBaseEntity);
        long total = jdbcUntil.queryForLong(String.format("select count(1) from %s ", tableName));
        page.setTotalNumber(total);

        //计算总页数
        long totalpageTemp = page.getTotalNumber() / page.getPageNumber();
        long plus = (page.getTotalNumber()%page.getPageNumber() == 0 )? 0:1;
        totalpageTemp = totalpageTemp + plus;
        if(totalpageTemp <= 0){
            totalpageTemp = 1;
        }
        //设置总页数
        page.setTotalPage(totalpageTemp);
    }

    public AbstractRead(DataBaseEntity dataBaseEntity, String sql, String tableName, Page page) {
        this.dataBaseEntity = dataBaseEntity;
        this.sql = sql;
        this.tableName = tableName;
        this.page = page;
    }

    public AbstractRead() {
    }
}
