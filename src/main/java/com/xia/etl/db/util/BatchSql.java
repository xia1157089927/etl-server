package com.xia.etl.db.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchSql {
	private List<Map<String, Object>> sqlList = new ArrayList<>();

	public void addBatch(String sql, Object[] objects) {
		Map<String, Object> map = new HashMap<>();
		map.put("sql", sql);
		map.put("objects", objects);
		sqlList.add(map);
	}

	public void addBatch(String sql) {
		Map<String, Object> map = new HashMap<>();
		map.put("sql", sql);
		map.put("objects", new Object[] {});
		sqlList.add(map);
	}

	public List<Map<String, Object>> getSqlList() {
		return sqlList;
	}
}
