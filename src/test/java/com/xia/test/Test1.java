package com.xia.test;

import com.xia.etl.Page;
import com.xia.etl.db.DataBaseEntity;
import com.xia.etl.pools.DataSourcePools;
import com.xia.etl.read.AbstractRead;
import com.xia.etl.read.MysqlRead;
import com.xia.etl.transport.Transport;
import com.xia.etl.write.AbstractWrite;
import com.xia.etl.write.PostgresWrite;
import org.junit.Test;

public class Test1 {

    @Test
    public void t1(){
        String tableName = "t_city_pandect_info";
        DataBaseEntity mysql = new DataBaseEntity("mysql", "mysql", "jdbc:mysql://10.211.55.6/suntf", "com.mysql.jdbc" +
                ".Driver",
                "root", "root", "select 1");
        DataSourcePools.createDataSource(mysql);

        DataBaseEntity mysql_2 = new DataBaseEntity("mysql", "mysql", "jdbc:mysql://10.211.55.6/suntf", "com.mysql" +
                ".jdbc" +
                ".Driver",
                "root", "root", "select 1");
        DataSourcePools.createDataSource(mysql_2);

        Page page = new Page(1, 10);
        AbstractRead read = MysqlRead.getInstance(mysql,"", tableName, page);
        AbstractWrite write = new PostgresWrite();

        Transport transport = new Transport(read, write);
        transport.transport();


    }
}
