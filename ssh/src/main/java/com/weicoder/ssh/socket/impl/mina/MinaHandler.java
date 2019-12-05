package com.weicoder.ssh.socket.impl.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.weicoder.common.lang.Conversion;
import com.weicoder.ssh.socket.Session;
import com.weicoder.ssh.socket.process.Process;

/**
 * mina实现
 * @author WD 
 * @version 1.0 
 */
public final class MinaHandler extends IoHandlerAdapter {
	// 名称
	private String	name;
	// 消息处理器
	private Process	process;

	/**
	 * 构造
	 * @param name
	 * @param process
	 */
	public MinaHandler(String name, Process process) {
		this.name = name;
		this.process = process;
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		process.connected(getSesson(session));
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		process.closed(getSesson(session));
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		// 转换成IoBuffer
		IoBuffer buffer = (IoBuffer) message;
		// 设置读取字节流长度
		byte[] b = new byte[buffer.remaining()];
		// 读取字节流
		buffer.get(b);
		// 清理缓存
		buffer.clear();
		buffer.free();
		// 交给数据处理器处理
		process.process(getSesson(session), b);
	}

	/**
	 * 获得包装Session
	 * @param session Mina session
	 * @return
	 */
	private Session getSesson(IoSession session) {
		// 获得SessionId
		int id = Conversion.toInt(session.getId());
		// 获得包装Session
		Session s = process.session(id);
		// 如果为null
		if (s == null) {
			// 实例化包装Session
			s = new MinaSession(name, session);
		}
		// 返回
		return s;
	}
}
