package com.weicoder.core.socket;

/**
 * Session 关闭处理接口
 * @author WD 
 *  
 */
public interface Closed {
	/**
	 * 关闭Session处理
	 * @param session
	 */
	void closed(Session session);
}
