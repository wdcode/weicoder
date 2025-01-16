package com.weicoder.seata;

import org.apache.seata.core.exception.TransactionException;
import org.apache.seata.rm.RMClient;
import org.apache.seata.tm.TMClient;
import org.apache.seata.tm.api.GlobalTransaction;
import org.apache.seata.tm.api.GlobalTransactionContext;

/**
 * seata 操作类
 * 
 * @author wdcode
 *
 */
public final class Seatas {
	/**
	 * 初始化seata
	 * 
	 * @param id    应用ID
	 * @param group 应用组
	 */
	public static void init(String id, String group) {
		TMClient.init(id, group);
		RMClient.init(id, group);
	}

	/**
	 * 获得当前或创建全局事务
	 * 
	 * @return GlobalTransaction
	 */
	public static GlobalTransaction current() {
		return GlobalTransactionContext.getCurrentOrCreate();
	}

	/**
	 * 根据xid载入全局事务
	 * 
	 * @param xid 全局事务ID
	 * @return GlobalTransaction 如果xid没有返回null
	 */
	public static GlobalTransaction reload(String xid) {
		try {
			return GlobalTransactionContext.reload(xid);
		} catch (TransactionException e) {
			return null;
		}
	}

	private Seatas() {
	}
}
