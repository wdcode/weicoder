package com.weicoder.websocket.endpoint;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.RemoteEndpoint;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import com.weicoder.common.lang.W; 
import com.weicoder.common.log.Logs;
import com.weicoder.common.token.TokenBean;
import com.weicoder.common.token.TokenEngine;
import com.weicoder.common.util.U; 
import com.weicoder.json.J;
import com.weicoder.websocket.common.WebSocketCommons;

/**
 * 基础实现的WebSocket类
 * @author WD
 */
@ServerEndpoint("/{}")
public class BasicServerEndpoint {
	// session 管理
	private final static Map<String, Session> SESSIONS = W.M.concurrent();

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
	 * @param b 数据
	 * @param last 客户端发送过来的消息
	 * @param session 客户端的连接会话
	 */
	@OnMessage
	public void onMessage(byte[] b, boolean last, Session session) {
		Logs.debug("websocket={};byte[] len={};last={}", session.getId(), b.length, last);
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息 要求为json 并带action
	 * @param session 客户端的连接会话
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		Logs.debug("websocket={};message={}", session.getId(), message);
		// 获得异步客户端通道
		RemoteEndpoint.Async async = session.getAsyncRemote();
		// 把传过来的字符串转换成map 要求过来数据为json
		Map<String, Object> ps = J.toMap(message);
		// 获得参数里的action
		String name = W.C.toString(ps.get("action"));
		// 判断action为空
		if (U.E.isEmpty(name)) {
			async.sendText("no action");
			return;
		}
		// 获得对应的action
		Object action = WebSocketCommons.WEBSOCKES.get(name);
		if (action == null) {
			// action为空 返回
			async.sendText("action is null");
			return;
		}
		// 获得对应方法
		Method method = WebSocketCommons.METHODS.get(name);
		if (method == null) {
			// method为空 返回
			async.sendText("method is null");
			return;
		}
		// 设置参数
		Parameter[] pars = WebSocketCommons.PARAMES.get(method);
		if (U.E.isNotEmpty(pars)) {
			Object[] params = new Object[pars.length];
			// action全部参数下标
			int i = 0;
			for (; i < pars.length; i++) {
				// 判断类型并设置
				Parameter p = pars[i];
				// 参数的类型
				Class<?> cs = p.getType();
				if (TokenBean.class.equals(cs))
					// 设置Token
					params[i] = TokenEngine.decrypt(W.C.toString(ps.get(p.getName())));
				else if (Map.class.equals(cs))
					params[i] = ps;
				else if (U.C.isBaseType(cs)) {
					// 获得参数
					params[i] = W.C.to(ps.get(p.getName()), cs);
				} else {
					// 设置属性
					params[i] = U.B.copy(ps, cs);
				}
				// 调用方法
				Object res = U.B.invoke(action, method, params);
				// 返回结果
				async.sendText(res instanceof String || res instanceof Number ? W.C.toString(res) : J.toJson(res));
			}
		}
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
