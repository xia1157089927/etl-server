package com.xia.etl.write;

import com.google.common.collect.Lists;
import com.xia.etl.Page;
import com.xia.etl.db.DataBaseEntity;
import com.xia.etl.db.util.BatchSql;
import com.xia.etl.db.util.SpringJdbcUntil;
import com.xia.etl.pools.DataSourcePools;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class PostgresWrite extends AbstractWrite {
    private static Logger logger = LoggerFactory.getLogger(PostgresWrite.class);

    public static PostgresWrite getInstance(DataBaseEntity dataBaseEntity, String sql, String tableName, Page page) {
        PostgresWrite write = new PostgresWrite();
        write.setDataBaseEntity(dataBaseEntity);
        write.setSql(sql);
        write.setTableName(tableName);
        write.setPage(page);
        return write;
    }

    @Override
    public Map<String, Object> writeData(List<Map<String, Object>> datas) throws Exception {
        String tableName = getTableName();
        DataBaseEntity dataBaseEntity = getDataBaseEntity();
        SpringJdbcUntil jdbcUntil = DataSourcePools.getSpringJdbcUntil("", getDataBaseEntity());

        String columns_sql = "SELECT " +
                "	A .attnum, " +
                "	A .attname AS field, " +
                "	T .typname AS TYPE, " +
                "	A .attlen AS LENGTH, " +
                "	A .atttypmod AS lengthvar, " +
                "	A .attnotnull AS NOTNULL, " +
                "	b.description AS COMMENT " +
                "FROM " +
                "	pg_class C, " +
                "	pg_attribute A " +
                "LEFT OUTER JOIN pg_description b ON A .attrelid = b.objoid " +
                "AND A .attnum = b.objsubid, " +
                " pg_type T " +
                "WHERE " +
                "	C .relname = '"+ tableName +"' " +
                "AND A .attnum > 0 " +
                "AND A .attrelid = C .oid " +
                "AND A .atttypid = T .oid " +
                "ORDER BY " +
                "	A .attnum";

        List<Map<String, Object>> columnInfos = jdbcUntil.queryForList(columns_sql);

        List<String> columns = Lists.newArrayList();
        List<String> whys = Lists.newArrayList();
        for (Map<String, Object> column : columnInfos){
            columns.add(MapUtils.getString(column, "field"));
            whys.add("?");
        }

        String writeSql = String.format("insert into %s (%s) values (%s)", tableName, StringUtils.join(columns, ", "),
                StringUtils.join(whys, ", "));

        BatchSql batchSql = new BatchSql();
        List<Object> values = null;
        for (Map<String, Object> data : datas){
            values = Lists.newArrayList();
            for (String column : columns){
                values.add(MapUtils.getObject(data, column));
            }
            batchSql.addBatch(writeSql, values.toArray());
        }
        jdbcUntil.doInTransaction(batchSql);
        logger.info("写入数据完成");
        return null;
    }
}
