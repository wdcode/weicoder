package com.weicoder.core.socket;

import com.weicoder.common.interfaces.Close;

/**
 * Socket接口
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-20
 */
public interface Socket extends Close {
	/**
	 * 添加要处理的Handler
	 * @param handler
	 */
	void addHandler(Handler<?> handler);

	/**
	 * 服务器名
	 */
	String name();

	/**
	 * 设置连接管理处理器
	 * @param connected 连接处理器
	 */
	void connected(Connected connected);

	/**
	 * 添加关闭处理器
	 * @param closed 关闭处理器
	 */
	void closed(Closed closed);
}
