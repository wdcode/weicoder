package com.weicoder.mina;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;

import com.weicoder.common.log.Logs;
import com.weicoder.socket.Session;
import com.weicoder.socket.base.BaseSession;

/**
 * Mina实现
 * 
 * @author  WD
 * @version 1.0
 */
public final class MinaSession extends BaseSession implements Session {
	// Mina Session
	private IoSession session;

	/**
	 * 构造方法
	 * 
	 * @param name    名称
	 * @param session
	 */
	public MinaSession(String name, IoSession session) {
		super(name);
		this.id = session.getId();
		this.session = session;
		address(session.getRemoteAddress());
	}

	@Override
	public void write(byte[] data) {
		// Session为null
		if (session == null)
			return;
		// 发送数据过多
		if (session.getScheduledWriteBytes() > Short.MAX_VALUE
				|| session.getScheduledWriteMessages() > Byte.MAX_VALUE) {
			Logs.info("message num many close=" + id);
			return;
		}
		// 发送数据
		session.write(IoBuffer.wrap(data));
	}

	@Override
	public void close() throws Exception {
		session.closeOnFlush();
	}

	@Override
	public void flush() {
	}
}
