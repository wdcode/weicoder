package com.weicoder.seata;

import io.seata.core.exception.TransactionException;
import io.seata.rm.RMClient;
import io.seata.tm.TMClient;
import io.seata.tm.api.GlobalTransaction;
import io.seata.tm.api.GlobalTransactionContext;

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
