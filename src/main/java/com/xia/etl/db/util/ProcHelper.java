package com.xia.etl.db.util;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class ProcHelper extends StoredProcedure {
	HashMap<Object, Object> map = new HashMap<>();

	public ProcHelper(DataSource dataSource, String sql) {
		super();
		setDataSource(dataSource);
		setSql(sql);
	}

	public void setValue(String key, Object obj) {
		map.put(key, obj);
	}

	public Map<String, Object> execute() throws DataAccessException {
		if (this.getSql() == null || this.getSql().equals(""))
			return null;
		this.compile();
		return execute(map);
	}

	public void setVarcharParam(String param) {
		this.declareParameter(new SqlParameter(param, Types.VARCHAR));
	}
	
	public void setDateParam(String param) {
		this.declareParameter(new SqlParameter(param, Types.DATE));
	}

	public void setDoubleParam(String param) {
		this.declareParameter(new SqlParameter(param, Types.DOUBLE));
	}

	public void setIntegerParam(String param) {
		this.declareParameter(new SqlParameter(param, Types.INTEGER));
	}

	public void setVarcharOutParam(String param) {
		this.declareParameter(new SqlOutParameter(param, Types.VARCHAR));
	}

	public void setDoubleOutParam(String param) {
		this.declareParameter(new SqlOutParameter(param, Types.DOUBLE));
	}

	public void setIntegerOutParam(String param) {
		this.declareParameter(new SqlOutParameter(param, Types.INTEGER));
	}
}
