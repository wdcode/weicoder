package com.weicoder.web.websocket;

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
	public void onMessage(String message, Session session) {
		// 获得session id
		String sid = session.getId();
		Logs.debug("websocket={};len={};message={}", sid, message.length(), message);
		// // 获得全局buffer
		// Buffer buff = session.buffer();
		// // 添加新消息到全局缓存中
		// buff.write(message);
		// // 循环读取数据
		// while (true) {
		// // 剩余字节长度不足，等待下次信息
		// if (buff.remaining() < 4) {
		// // 压缩并跳出循环
		// break;
		// }
		// // 获得信息长度
		// short length = buff.readShort();
		// // 无长度 发送消息不符合 关掉连接
		// if (length < 2 || length > Short.MAX_VALUE) {
		// CloseUtil.close(session);
		// Logs.info("name={};error len close id={};len={}", name, session.getId(), length);
		// return;
		// }
		// // 剩余字节长度不足，等待下次信息
		// if (buff.remaining() < length) {
		// // 重置缓存
		// buff.offset(buff.offset() - 2);
		// break;
		// }
		// // 读取指令id
		// short id = buff.readShort();
		// // 消息长度
		// int len = length - 2;
		// // 读取指定长度的字节数
		// byte[] data = new byte[len];
		// // 读取指定长度字节数组
		// if (len > 0) {
		// // 读取字节数组
		// buff.read(data);
		// // 启用压缩
		// if (zip) {
		// // 解压缩
		// data = ZipEngine.extract(data);
		// }
		// }
		// // 获得相应的方法
		// Method m = methods.get(id);
		// // 如果处理器为空
		// if (m == null) {
		// // 抛弃这次消息
		// Logs.warn("name={};socket={};handler message discard id={};message len={}", name,
		// sid, id, len);
		// return;
		// }
		// Logs.info("name={};socket={};receive len={};id={};method={};time={}", name, sid, length,
		// id, m, DateUtil.getTheDate());
		// try {
		// // 当前时间
		// long curr = System.currentTimeMillis();
		// // 回调处理器
		// m.invoke(handlers.get(id), getParames(m, data, session));
		// // 设置心跳时间
		// session.setHeart(DateUtil.getTime());
		// Logs.info("name={};socket={};handler end time={}", name, sid,
		// System.currentTimeMillis() - curr);
		// } catch (Exception e) {
		// Logs.error(e);
		// }
		// // 如果缓存区为空
		// if (buff.remaining() == 0) {
		// // 清除并跳出
		// buff.clear();
		// break;
		// }
		// }
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
