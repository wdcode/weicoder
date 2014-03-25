package com.weicoder.web.socket.manager;

import java.util.List;
import java.util.Set;

import com.weicoder.web.socket.Session;

/**
 * Socket Session 管理器
 * @author WD
 * @since JDK7
 * @version 1.0 2014-1-14
 */
public interface Manager {
	/**
	 * 注册到列表
	 * @param key 注册键
	 * @param id 注册ID
	 * @param session Socket Session
	 * @return true 注册成功 false 注册失败
	 */
	boolean register(String key, int id, Session session);

	/**
	 * 从列表删除Session
	 * @param key 注册键
	 * @param id 注册ID
	 * @return 返回删除的session
	 */
	Session remove(String key, int id);

	/**
	 * 根据Session Id 删除
	 * @param id SessionID
	 * @return 返回删除的session
	 */
	Session remove(int id);

	/**
	 * 从列表删除Session 根据Session删除 循环所有服务器列表删除
	 * @param Session session
	 * @return 返回删除的session
	 */
	Session remove(Session session);

	/**
	 * 根据注册ID获得Session
	 * @param key 注册键
	 * @param id 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	Session get(String key, int id);

	/**
	 * 根据SessionID获得Session
	 * @param id 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	Session get(int id);

	/**
	 * 根据键获得注册Session列表
	 * @param key 注册键
	 * @return Session列表
	 */
	List<Session> sessions(String key);

	/**
	 * 获得全部注册Session列表
	 * @return Session列表
	 */
	List<Session> sessions();

	/**
	 * 获得所有Key
	 * @return key列表
	 */
	Set<String> keys();

	/**
	 * 获得所有注册Session数量
	 * @return 数量
	 */
	int size();

	/**
	 * 根据Key获得注册Session数量
	 * @param key 注册键
	 * @return 数量
	 */
	int size(String key);

	/**
	 * 广播数据 发送给管理器下所有的session
	 * @param id 指令
	 * @param message 消息
	 */
	void broad(short id, Object message);

	/**
	 * 广播数据 发送给管理器指定KEY下所有的session
	 * @param key 注册KEY
	 * @param id 指令
	 * @param message 消息
	 */
	void broad(String key, short id, Object message);

	/**
	 * 广播数据 发送给管理器指定KEY下所有的session
	 * @param key 注册KEY
	 * @param ids 注册的ID
	 * @param id 指令
	 * @param message 消息
	 */
	void broad(String key, Set<Integer> ids, short id, Object message);
}
