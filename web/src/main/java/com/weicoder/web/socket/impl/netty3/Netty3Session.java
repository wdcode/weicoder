package com.weicoder.web.socket.impl.netty3;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.base.BaseSession;

/**
 * netty Session实现
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-20
 */
public final class Netty3Session extends BaseSession implements Session {
	// 通道
	private Channel	channel;

	/**
	 * 构造
	 * @param id sessionId
	 * @param channel
	 */
	public Netty3Session(Channel channel) {
		this.id = channel.getId();
		this.channel = channel;
		address(channel.getRemoteAddress());
	}

	@Override
	public void close() {
		channel.disconnect();
		channel.close();
	}

	@Override
	public boolean isConnect() {
		return channel.isConnected();
	}

	@Override
	public boolean isClose() {
		return !channel.isOpen();
	}

	@Override
	public void send(byte[] data) {
		channel.write(ChannelBuffers.wrappedBuffer(data));
	}
}
