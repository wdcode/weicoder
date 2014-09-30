package com.weicoder.core.socket;

/**
 * Session 连接器处理器
 * @author WD
 * @since JDK7
 * @version 1.0 2014-4-15
 */
public interface Connected {
	/**
	 * 连接处理器
	 * @param session Session
	 * @return 是否连接
	 */
	boolean connected(Session session);
}
