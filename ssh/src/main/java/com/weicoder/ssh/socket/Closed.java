package com.weicoder.ssh.socket;

/**
 * Session 关闭处理接口
 * @author WD 
 * @version 1.0 
 */
public interface Closed {
	/**
	 * 关闭Session处理
	 * @param session
	 */
	void closed(Session session);
}
