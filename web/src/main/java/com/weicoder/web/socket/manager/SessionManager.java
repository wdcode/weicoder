package com.weicoder.web.socket.manager;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.ExecutorUtil;
import com.weicoder.core.log.Logs;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.Sockets;
import com.weicoder.web.socket.empty.SessionEmpty;

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
	public Session remove(String key, int id) {
		// 获得注册列表
		Map<Integer, Session> register = registers.get(key);
		// 列表为null
		if (register == null) {
			return SessionEmpty.EMPTY;
		}
		// 删除列表
		return register.remove(id);
	}

	/**
	 * 从列表删除Session 根据ID删除 循环所有服务器列表删除
	 * @param id 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	public Session remove(int id) {
		// 如果存在
		if (keys.containsKey(id) && ids.containsKey(id)) {
			// 删除Session
			return remove(keys.get(id), ids.get(id));
		} else {
			return SessionEmpty.EMPTY;
		}
	}

	/**
	 * 从列表删除Session 根据Session删除 循环所有服务器列表删除
	 * @param Session session
	 * @return true 删除成功 false 删除成功
	 */
	public Session remove(Session session) {
		return remove(session.getId());
	}

	/**
	 * 根据注册ID获得Session
	 * @param key 注册键
	 * @param id 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	public Session get(String key, int id) {
		// 获得Session
		Session session = registers.get(key).get(id);
		// 如果Session为空 返回空实现
		return session == null ? SessionEmpty.EMPTY : session;
	}

	/**
	 * 根据SessionID获得Session
	 * @param id 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	public Session get(int id) {
		return get(keys.get(id), ids.get(id));
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

	@Override
	public void broad(short id, Object message) {
		// 声明Sesson列表
		List<Session> sessions = Lists.getList();
		// 获得相应的Session
		for (Map<Integer, Session> map : registers.values()) {
			sessions.addAll(map.values());
		}
		// 广播
		broad(sessions, id, message);
	}

	@Override
	public void broad(String key, short id, Object message) {
		broad(sessions(key), id, message);
	}

	@Override
	public void broad(String key, Set<Integer> ids, short id, Object message) {
		// 声明Sesson列表
		List<Session> sessions = Lists.getList();
		// 日志
		long curr = System.currentTimeMillis();
		Logs.info("manager broad start key=" + key + ";ids=" + ids.size() + ";id=" + id + ";time=" + DateUtil.getTheDate());
		// 获得相应的Session
		for (Map.Entry<Integer, Session> e : registers.get(key).entrySet()) {
			// ID存在
			if (ids.contains(e.getKey())) {
				sessions.add(e.getValue());
			}
		}
		// 日志
		Logs.info("manager broad end key=" + key + ";ids=" + ids.size() + ";id=" + id + ";time=" + (System.currentTimeMillis() - curr));
		// 广播
		broad(sessions, id, message);
	}

	/**
	 * 广播
	 * @param sessions
	 * @param id
	 * @param message
	 */
	private void broad(List<Session> sessions, short id, Object message) {
		// 获得列表长度
		int size = sessions.size();
		// 如果线程池乘2倍
		int broad = SocketParams.BROAD;
		// 包装数据
		final byte[] data = Sockets.pack(id, message);
		// 日志
		Logs.info("manager broad num=" + size + ";broad=" + broad + ";id=" + id + ";data=" + data.length + ";time=" + DateUtil.getTheDate());
		// 循环分组广播
		for (int i = 0; i < size;) {
			// 获得执行Session列表
			final List<Session> list = Lists.subList(sessions, i, i += broad);
			// 线程执行
			ExecutorUtil.execute(new Runnable() {
				@Override
				public void run() {
					// 日志
					long curr = System.currentTimeMillis();
					Logs.info("manager pool broad start size=" + list.size() + ";time=" + DateUtil.getTheDate());
					// 广播消息
					for (Session session : list) {
						session.send(data);
					}
					Logs.info("manager pool broad end size=" + list.size() + ";time=" + (System.currentTimeMillis() - curr));
				}
			});
		}
	}
}
