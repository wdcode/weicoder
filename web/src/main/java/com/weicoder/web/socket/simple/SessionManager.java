package com.weicoder.web.socket.simple;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.interfaces.Manager;
import com.weicoder.web.socket.interfaces.Session;

/**
 * Session管理类
 * @author WD
 * @since JDK7
 * @version 1.0 2014-1-14
 */
public final class SessionManager implements Manager {
	// 保存注册的Session
	private Map<String, Map<Integer, Session>>	registers;
	// 保存Session对应所在列表
	private Map<Integer, String>				keys;
	// 保存Session对应注册ID
	private Map<Integer, Integer>				ids;

	/**
	 * 构造方法
	 */
	public SessionManager() {
		// 初始化
		registers = Maps.getConcurrentMap();
		keys = Maps.getConcurrentMap();
		ids = Maps.getConcurrentMap();
		// 注册列表Map
		for (String register : SocketParams.REGISTERS) {
			registers.put(register, new ConcurrentHashMap<Integer, Session>());
		}
	}

	/**
	 * 注册到列表
	 * @param key 注册键
	 * @param id 注册ID
	 * @param session Socket Session
	 * @return true 注册成功 false 注册失败
	 */
	public boolean register(String key, int id, Session session) {
		// 获得注册列表
		Map<Integer, Session> register = registers.get(key);
		// 列表为null
		if (register == null) {
			return false;
		}
		// 添加到列表
		register.put(id, session);
		// session id
		int sid = session.getId();
		// 记录索引
		keys.put(sid, key);
		// 记录ID
		ids.put(sid, id);
		// 返回成功
		return true;
	}

	/**
	 * 从列表删除Session
	 * @param key 注册键
	 * @param id 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	public boolean remove(String key, int id) {
		// 获得注册列表
		Map<Integer, Session> register = registers.get(key);
		// 列表为null
		if (register == null) {
			return false;
		}
		// 删除列表
		return register.remove(id) != null;
	}

	/**
	 * 从列表删除Session 根据ID删除 循环所有服务器列表删除
	 * @param id 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	public boolean remove(int id) {
		// 删除标识
		boolean is = false;
		// 循环删除列表
		for (String key : keys()) {
			if (is = remove(key, id)) {
				continue;
			}
		}
		// 返回是否成功
		return is;
	}

	/**
	 * 从列表删除Session 根据Session删除 循环所有服务器列表删除
	 * @param Session session
	 * @return true 删除成功 false 删除成功
	 */
	public boolean remove(Session session) {
		// Session ID
		int id = session.getId();
		// 如果存在
		if (keys.containsKey(id) && ids.containsKey(id)) {
			// 删除Session
			return remove(keys.get(id), ids.get(id));
		} else {
			return false;
		}
	}

	/**
	 * 根据键获得注册Session列表
	 * @param key 注册键
	 * @return Session列表
	 */
	public List<Session> sessions(String key) {
		return Lists.getList(registers.get(key).values());
	}

	/**
	 * 获得全部注册Session列表
	 * @return Session列表
	 */
	public List<Session> sessions() {
		// 声明Session列表
		List<Session> sessions = Lists.getList();
		// 循环获得全部Session
		for (String key : keys()) {
			// 添加到列表
			sessions.addAll(sessions(key));
		}
		// 返回列表
		return sessions;
	}

	@Override
	public Set<String> keys() {
		return registers.keySet();
	}

	@Override
	public int size() {
		// 声明总数
		int sum = 0;
		// 循环计算数量
		for (String key : keys()) {
			// 添加到列表
			sum += size(key);
		}
		// 返回总数
		return sum;
	}

	@Override
	public int size(String key) {
		return Maps.size(registers.get(key));
	}
}
