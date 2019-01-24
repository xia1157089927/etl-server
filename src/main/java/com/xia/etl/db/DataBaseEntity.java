package com.xia.etl.db;

/**
 * 数据库信息实体
 */
public class DataBaseEntity {
    /**
     * //数据库类型
     */
    private String dbType;
    /**
     * //数据库别名
     * url与username拼接
     */
    private String dbAlias;
    private String url;
    private String className;
    private String username;
    private String password;
    private String validationQuery;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbAlias() {
        return dbAlias;
    }

    public void setDbAlias(String dbAlias) {
        this.dbAlias = dbAlias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public DataBaseEntity(String dbType, String dbAlias, String url, String className, String username, String password, String validationQuery) {
        this.dbType = dbType;
        this.dbAlias = dbAlias;
        this.url = url;
        this.className = className;
        this.username = username;
        this.password = password;
        this.validationQuery = validationQuery;
    }

    @Override
    public String toString() {
        return "DataBaseEntity{" +
                "dbType='" + dbType + '\'' +
                ", dbAlias='" + dbAlias + '\'' +
                ", url='" + url + '\'' +
                ", className='" + className + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", validationQuery='" + validationQuery + '\'' +
                '}';
    }
}
