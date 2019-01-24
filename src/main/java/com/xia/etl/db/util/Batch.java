package com.xia.etl.db.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;

/**
 * @author xiams
 * @date 2015-03-05
 */
public abstract class Batch extends BatchBase {
	private static Logger     logger            = Logger.getLogger(Batch.class);

	private JdbcTemplate      jdbcTemplate;
	private TransactionStatus transactionStatus;

	/**
	 * //默认的fetchsize
	 */
	public final static int   DEFAULT_FETCHSIZE = 32;
	
	public JdbcTemplate getJdbcTemplate () {
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate (JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public TransactionStatus getTransactionStatus () {
		return transactionStatus;
	}
	
	public void setTransactionStatus (TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	
	@Override
	public void setParams (JdbcTemplate jdbc, TransactionStatus status) {
		this.setJdbcTemplate(jdbc);
		this.setTransactionStatus(status);
	}
	
	@Override
	public void rollBack () {
		this.getTransactionStatus().setRollbackOnly();
	}
	
	/**
	 * 显示错误信息
	 * @author xiams 2015-03-05
	 * @param e
	 * @param sql
	 * @param objects
	 * @throws Exception
	 */
	private void getError (Exception e, String sql, Object[] objects) throws Exception {
		//出现错误进行回滚
		this.rollBack();
		logger.info("数据库查询出错");
		throw new Exception("数据库查询出错");
	}
	
	/**
	 * 显示错误信息
	 * @author xiams 2015-03-05
	 * @param e
	 */
	public String getError (Exception e) {
		StringBuffer str = new StringBuffer();
		str.append(e.getMessage());
		for (int i = 0; i < e.getStackTrace().length; i++) {
			str.append(e.getStackTrace()[i] + "\n");
		}
		return str.toString();
	}
	
	/**
	 * 返回一条记录
	 * @param sql 传入的sql语句: select *from table where user_id=?
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryForMap (String sql, Object[] objects) throws Exception {
		Map<String, Object> map = null;
		try {
			map = this.getJdbcTemplate().queryForMap(sql, objects);
		} catch (Exception e) {
			getError(e, sql, objects);
		}
		if (map == null) map = new HashMap<String, Object>();
		return map;
	}
	
	/**
	 * 返回一条记录
	 * @param sql 传入的sql语句: select *from table where user_id=?
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryForMap (String sql) throws Exception {
		return this.queryForMap(sql, null);
	}
	
	/**
	 * 获取某个字段的值
	 * @param sql
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	public String queryForString (String sql, Object[] objects) throws Exception {
		try {
			return this.getJdbcTemplate().queryForObject(sql, objects, String.class);
		} catch (Exception e) {
			getError(e, sql, objects);
			return "";
		}
	}
	
	/**
	 * 获取某个字段的值
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public String queryForString (String sql) throws Exception {
		return this.queryForString(sql, null);
	}
	
	/**
	 * 返回数据集
	 * @param sql 传入的sql语句: select *from table where user_id=?
	 * @param objects
	 * @param fetchSize
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, Object[] objects, int fetchSize) throws Exception {
		JdbcTemplate jdbc = this.getJdbcTemplate();
		jdbc.setFetchSize(fetchSize);
		List<Map<String, Object>> list = null;
		try {
			list = jdbc.queryForList(sql, objects);
		} catch (Exception e) {
			getError(e, sql, objects);
		}
		if (list == null) list = new ArrayList<Map<String, Object>>();
		return list;
	}
	
	/**
	 * 查询条件不确定时返回数据集
	 * @param sql sql_where拼接 sql="select * from table where name='"+v_name+"'";
	 * @param fetchSize 一次获取的数据条数
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, int fetchSize) throws Exception {
		return this.queryForList(sql, new Object[] {}, fetchSize);
	}
	
	/**
	 * 返回数据集 查询时条件不确定，将条件放入一个List<String>中
	 * @param sql
	 * @param list
	 * @param fetchSize
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, List<String> list, int fetchSize) throws Exception {
		return this.queryForList(sql, list.toArray(), fetchSize);
	}
	
	/**
	 * 返回数据集
	 * @param sql 传入的sql语句: select *from table where user_id=?
	 * @param objects
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, Object[] objects) throws Exception {
		return this.queryForList(sql, objects, DEFAULT_FETCHSIZE);
	}
	
	/**
	 * 返回数据集 查询时条件不确定，将条件放入一个List<String>中
	 * @param sql
	 * @param list
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql, List<String> list) throws Exception {
		return this.queryForList(sql, list.toArray(), DEFAULT_FETCHSIZE);
	}
	
	/**
	 * 查询条件不确定时返回数据集
	 * @param sql sql_where拼接 sql="select * from table where name='"+v_name+"'";
	 * @return
	 */
	public List<Map<String, Object>> queryForList (String sql) throws Exception {
		return this.queryForList(sql, DEFAULT_FETCHSIZE);
	}
	
	/**
	 * insert,update,delete 操作
	 * @param sql 传入的语句 sql="insert into tables values(?,?)";
	 * @param objects
	 * @return 0:失败 1:成功
	 */
	public int update (String sql, Object[] objects) throws Exception {
		int exc = 1;
		try {
			this.getJdbcTemplate().update(sql, objects);
		} catch (Exception e) {
			exc = 0;
			getError(e, sql, objects);
		}
		return exc;
	}
	
}
