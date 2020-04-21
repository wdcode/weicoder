package com.weicoder.socket.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.log.Logs;
import com.weicoder.socket.params.SocketParams;
import com.weicoder.socket.Session;
import com.weicoder.socket.Sockets;

/**
 * Session管理类
 * @author WD
 */
public final class Manager {
	// 保存注册的Session
	private Map<Long, Session> registers;

	public Manager() {
		registers = Maps.newConcurrentMap();
		// 定时检测
		Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
			// 检测连接超时
			// try {
			// 获得当前时间
			int curr = DateUtil.getTime();
			int n = 0;
			for (Session s : sessions()) {
				// 超时
				if (curr - s.getHeart() >= SocketParams.TIMEOUT) {
					// 关闭Session
					Logs.info("heart close session={}", s.getId());
					registers.remove(s.getId());
					CloseUtil.close(s);
				}
				n++;
			}
			Logs.debug("testing heart num={}", n);
		}, 0, SocketParams.TIME, TimeUnit.SECONDS);
	}

	/**
	 * 注册到列表
	 * @param session Socket Session
	 */
	public void register(Session session) {
		registers.put(session.getId(), session);
	}

	/**
	 * 从列表删除Session 根据Session删除 循环所有服务器列表删除
	 * @param session Session
	 * @return true 删除成功 false 删除成功
	 */
	public Session remove(Session session) {
		return remove(session.getId());
	}

	/**
	 * 从列表删除Session
	 * @param id 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	public Session remove(long id) {
		return registers.remove(id);
	}

	/**
	 * 根据注册ID获得Session
	 * @param id 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	public Session get(long id) {
		return registers.get(id);
	}

	/**
	 * 根据注册ID获得Session
	 * @param ids 注册ID
	 * @return true 删除成功 false 删除成功
	 */
	public List<Session> gets(List<Long> ids) {
		List<Session> list = Lists.newList(ids.size());
		ids.forEach(id -> list.add(registers.get(id)));
		return list;
	}

	/**
	 * 验证Session是否注册
	 * @param session Session
	 * @return true 以注册 false 未注册
	 */
	public boolean exists(Session session) {
		return registers.containsValue(session);
	}

	/**
	 * 根据键获得注册Session列表
	 * @return Session列表
	 */
	public List<Session> sessions() {
		return Lists.newList(registers.values());
	}

	/**
	 * 获得所有注册Session数量
	 * @return 数量
	 */
	public int size() {
		return registers.size();
	}

	/**
	 * 广播数据 发送给管理器下所有的session
	 * @param id 指令
	 * @param message 消息
	 */
	public void broad(short id, Object message) {
		broad0(sessions(), id, message);
	}

	/**
	 * 广播数据 发送给管理器下所有的session
	 * @param ids 所有session id
	 * @param id 指令
	 * @param message 消息
	 */
	public void broad(List<Long> ids, short id, Object message) {
		broad0(gets(ids), id, message);
	}

	/**
	 * 广播
	 * @param sessions
	 * @param id
	 * @param message
	 */
	private void broad0(List<Session> sessions, short id, Object message) {
		// 列表为空
		if (EmptyUtil.isEmpty(sessions))
			return;
		// 日志
		Logs.info("manager broad num={};id={};time={}", sessions.size(), id, DateUtil.getTheDate());
		// 直接广播数据
		broad(sessions, Sockets.pack(id, message));
	}

	/**
	 * 广播
	 * @param sessions
	 * @param data
	 */
	private void broad(List<Session> sessions, byte[] data) {
		// 日志
		long curr = System.currentTimeMillis();
		Logs.debug("manager pool broad start size={};time=", sessions.size(), DateUtil.getTheDate());
		// 广播消息
		sessions.forEach(session -> session.send(data));
		Logs.debug("manager pool broad end size={};time={}", sessions.size(), (System.currentTimeMillis() - curr));
	}
}
