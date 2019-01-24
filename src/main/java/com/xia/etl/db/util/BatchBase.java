package com.xia.etl.db.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;

/**
 * @author xiams
 * @date 2015-03-05
 */
public abstract class BatchBase {
	/**
	 * 真正执行入口
	 * @author xiams 2015-03-05
	 * @throws Exception
	 */
	public abstract void execute () throws Exception;
	
	/**
	 * 设置参数
	 * @author xiams 2015-03-05
	 * @param jdbc
	 * @param status
	 */
	public abstract void setParams (JdbcTemplate jdbc, TransactionStatus status) throws Exception;
	
	/**
	 * 进行回滚
	 * @author xiams 2015-03-05
	 */
	public abstract void rollBack ();
}
