package com.xia.etl.write;

import java.util.List;
import java.util.Map;

public interface Write {
    Map<String, Object> writeData(List<Map<String, Object>> datas) throws Exception;
}
