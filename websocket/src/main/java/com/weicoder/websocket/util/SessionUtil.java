package com.weicoder.websocket.util;

import java.io.IOException;

import jakarta.websocket.Session;

import com.weicoder.common.log.Logs;
import com.weicoder.json.J;

/**
 * Session 工具类
 * @author WD
 */
public final class SessionUtil {
	/**
	 * 发送信息到前端
	 * @param session 前端连接
	 * @param message 发送的消息
	 */
	public static void send(Session session, Object message) {
		try {
			session.getBasicRemote().sendText(J.toJson(message));
		} catch (IOException e) {
			Logs.error(e);
		}
	}

	private SessionUtil() {}
}
