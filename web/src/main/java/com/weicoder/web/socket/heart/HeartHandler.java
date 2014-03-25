package com.weicoder.web.socket.heart;

import java.util.Map;

import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.ScheduledUtile;
import com.weicoder.core.log.Logs;
import com.weicoder.web.socket.Handler;
import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.manager.Manager;
import com.weicoder.web.socket.message.Null;

/**
 * 心跳检测
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-30
 */
public final class HeartHandler implements Handler<Null>, Heart {
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
	public HeartHandler(short id, int time) {
		this.id = id;
		this.heart = time + 30;
		times = Maps.getConcurrentMap();
		sessions = Maps.getConcurrentMap();
		// 定时检测
		ScheduledUtile.rate(new Runnable() {
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
						Logs.debug("heart close session=" + e.getKey());
					}
				}
			}
		}, heart);
	}

	/**
	 * 添加Session
	 * @param session
	 */
	public void add(Session session) {
		sessions.put(session.getId(), session);
		times.put(session.getId(), DateUtil.getTime());
	}

	/**
	 * 删除Session
	 * @param session
	 */
	public void remove(Session session) {
		sessions.remove(session.getId());
		times.remove(session.getId());
	}

	@Override
	public short getId() {
		return id;
	}

	@Override
	public void handler(Session session, Null data, Manager manager) {
		// 记录时间
		times.put(session.getId(), DateUtil.getTime());
	}
}
