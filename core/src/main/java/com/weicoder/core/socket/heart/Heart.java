package com.weicoder.core.socket.heart;

import java.util.Map;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.ScheduledUtile;
import com.weicoder.common.log.Logs;
import com.weicoder.core.socket.Handler;
import com.weicoder.core.socket.Session;
import com.weicoder.core.socket.message.Null;

/**
 * 心跳检测
 * @author WD 
 * @version 1.0  
 */
public final class Heart implements Handler<Null> {
	// 保存Session 时间
	private Map<Integer, Integer>	times;
	// 保存Session
	private Map<Integer, Session>	sessions;
	// 心跳检测时间
	private int						heart;
	// 心跳检查ID指令
	private short					id;
	//是否回包
	private boolean					isPack;

	/**
	 * 构造
	 */
	public Heart(final short id, int time, boolean isPack) {
		this.id = id;
		this.heart = time;
		this.isPack = isPack;
		times = Maps.newConcurrentMap();
		sessions = Maps.newConcurrentMap();
		// 定时检测
		ScheduledUtile.delay(() -> {
			// 获得当前时间
			int curr = DateUtil.getTime();
			Logs.debug("heart check=" + DateUtil.getDate());
			// 循环检测
			for (Map.Entry<Integer, Integer> e : times.entrySet()) {
				// 获得心跳时间
				int t = Conversion.toInt(e.getValue());
				// 如果心跳时间超过发送时间
				if (curr - t > heart) {
					// 关闭Session
					CloseUtil.close(sessions.get(e.getKey()));
					sessions.remove(e.getKey());
					times.remove(e.getKey());
					Logs.info("heart close session=" + e.getKey());
				}
			}
		}, heart);
		//		// 是否启动心跳
		//		if (heart > 0) {
		//			// 心跳指令
		//			// 定时发送心跳信息
		//			ScheduledUtile.delay(() -> {
		//				// 循环发送心跳信息
		//				for (Session session : sessions.values()) {
		//					// 发送心跳消息
		//					session.send(id, null);
		//				}
		//				Logs.debug("send heart session");
		//			}, heart / 2);
		//		}
	}

	/**
	 * 添加Session
	 * @param session
	 */
	public void add(Session session) {
		sessions.put(session.id(), session);
		times.put(session.id(), DateUtil.getTime());
	}

	/**
	 * 删除Session
	 * @param session
	 */
	public void remove(Session session) {
		sessions.remove(session.id());
		times.remove(session.id());
	}

	@Override
	public short id() {
		return id;
	}

	@Override
	public void handler(Session session, Null data) {
		times.put(session.id(), DateUtil.getTime());
		//回包
		if (isPack) {
			session.send(id, data);
		}
	}
}
