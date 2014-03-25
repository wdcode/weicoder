package com.weicoder.web.socket.impl.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.weicoder.common.lang.Conversion;
import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.process.Process;

/**
 * mina实现
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-28
 */
public final class MinaHandler extends IoHandlerAdapter {
	// 消息处理器
	private Process	process;

	/**
	 * 构造
	 * @param process
	 */
	public MinaHandler(Process process) {
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
		Session s = process.getSession(id);
		// 如果为null
		if (s == null) {
			// 实例化包装Session
			s = new MinaSession(session);
		}
		// 返回
		return s;
	}
}
