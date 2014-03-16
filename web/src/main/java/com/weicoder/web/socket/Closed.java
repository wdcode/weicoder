package com.weicoder.web.socket;


/**
 * Session 关闭处理接口
 * @author WD
 * @since JDK7
 * @version 1.0 2014-1-30
 */
public interface Closed {
	/**
	 * 关闭Session处理
	 * @param session
	 */
	void closed(Session session);
}
