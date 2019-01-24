package com.xia.etl.read;

import com.xia.etl.Page;

import java.util.List;
import java.util.Map;

public interface Read {
    List<Map<String, Object>> readData(Page page) throws Exception;
}
