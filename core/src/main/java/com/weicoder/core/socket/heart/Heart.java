package com.weicoder.core.socket.heart;

import java.util.Map;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.ScheduledUtile;
import com.weicoder.core.log.Logs;
import com.weicoder.core.socket.Handler;
import com.weicoder.core.socket.Session;
import com.weicoder.core.socket.message.Null;

/**
 * 心跳检测
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-30
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

	/**
	 * 构造
	 */
	public Heart(final short id, int time) {
		this.id = id;
		this.heart = time;
		times = Maps.getConcurrentMap();
		sessions = Maps.getConcurrentMap();
		// 定时检测
		ScheduledUtile.delay(new Runnable() {
			@Override
			public void run() {
				// 获得当前时间
				int time = DateUtil.getTime();
				Logs.debug("heart check=" + DateUtil.getDate());
				// 循环检测
				for (Map.Entry<Integer, Integer> e : times.entrySet()) {
					// 获得心跳时间
					int t = Conversion.toInt(e.getValue());
					// 如果心跳时间超过发送时间
					if (time - t > heart) {
						// 关闭Session
						sessions.get(e.getKey()).close();
						sessions.remove(e.getKey());
						times.remove(e.getKey());
						Logs.info("heart close session=" + e.getKey());
					}
				}
			}
		}, heart);
		// 是否启动心跳
		if (heart > 0) {
			// 心跳指令
			// 定时发送心跳信息
			ScheduledUtile.delay(new Runnable() {
				@Override
				public void run() {
					// 循环发送心跳信息
					for (Session session : sessions.values()) {
						// 发送心跳消息
						session.send(id, null);
					}
					Logs.debug("send heart session");
				}
			}, heart / 2);
		}
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
	}
}
