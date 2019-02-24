package com.xia.etl.read;

import com.xia.etl.Page;
import com.xia.etl.db.DataBaseEntity;
import com.xia.etl.pools.DataSourcePools;
import com.xia.etl.db.util.SpringJdbcUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class MysqlRead extends AbstractRead {
    private static Logger logger = LoggerFactory.getLogger(MysqlRead.class);

    public static MysqlRead getInstance(DataBaseEntity dataBaseEntity, String sql, String tableName, Page page) {
        MysqlRead read = new MysqlRead();
        read.setDataBaseEntity(dataBaseEntity);
        read.setSql(sql);
        read.setTableName(tableName);
        read.initPage(page);
        read.setPage(page);
        return read;
    }

    @Override
    public List<Map<String, Object>> readData(Page page) throws Exception {
        String tableName = getTableName();
        DataBaseEntity dataBaseEntity = getDataBaseEntity();

        SpringJdbcUntil jdbcUntil = DataSourcePools.getSpringJdbcUntil(String.format("%s(read)", tableName),
                getDataBaseEntity());

        String pageSql = String.format("select * from %s where 1 = 1 limit %d offset %d ",
                tableName, page.getPageNumber(), (page.getCurrentPage() - 1)*page.getPageNumber());
        logger.info(pageSql);
        return jdbcUntil.queryForList(pageSql);
    }
}
