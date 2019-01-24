package com.xia.etl.write;

import java.util.List;
import java.util.Map;

public class PostgresWrite extends AbstractWrite {

    @Override
    public Map<String, Object> writeData(List<Map<String, Object>> datas) throws Exception {
        for (Map<String, Object> data:datas){
            System.out.println(data);
        }
        return null;
    }
}
