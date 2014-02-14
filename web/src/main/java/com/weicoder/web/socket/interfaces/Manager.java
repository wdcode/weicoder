package com.weicoder.web.socket.interfaces;

import java.util.List;
import java.util.Set;


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
	 * @return true 删除成功 false 删除成功
	 */
	boolean remove(String key, int id);

	/**
	 * 从列表删除Session 根据ID删除 循环所有服务器列表删除
	 * @param id 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	boolean remove(int id);

	/**
	 * 从列表删除Session 根据Session删除 循环所有服务器列表删除
	 * @param Session session
	 * @return true 删除成功 false 删除成功
	 */
	boolean remove(Session session);

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
}
