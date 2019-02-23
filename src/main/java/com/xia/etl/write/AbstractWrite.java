package com.xia.etl.write;

import com.xia.etl.Page;
import com.xia.etl.db.DataBaseEntity;

public abstract class AbstractWrite implements Write {
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


}
