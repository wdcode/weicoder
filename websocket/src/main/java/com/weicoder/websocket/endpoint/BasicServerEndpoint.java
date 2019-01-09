package com.weicoder.websocket.endpoint;

import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;

/**
 * 基础实现的WebSocket类
 * @author WD
 */
@ServerEndpoint("/*")
public class BasicServerEndpoint {
	// session 管理
	private final static Map<String, Session> SESSIONS = Maps.newConcurrentMap();

	/**
	 * 连接建立成功调用的方法
	 * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session) {
		// 添加到列表中
		SESSIONS.put(session.getId(), session);
		Logs.info("open websocket session={}", session.getId());
	}

	/**
	 * 连接关闭调用的方法
	 * @param session 客户端的连接会话
	 */
	@OnClose
	public void onClose(Session session) {
		SESSIONS.remove(session.getId());
		Logs.info("close websocket session={}", session.getId());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 客户端的连接会话
	 */
	@OnMessage
	public void onMessage(byte[] b, boolean last, Session session) { 
		Logs.debug("websocket={};len={};last={}", session.getId(), b.length, last);
	}
	
	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 客户端的连接会话
	 */
	@OnMessage
	public void onMessage(String message, Session session) {  
		Logs.debug("websocket={};message={}", session.getId(),message);  
	}

	/**
	 * 发生错误时调用
	 * @param session 客户端的连接会话
	 * @param error 错误异常
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		Logs.error(error);
	}
}
