package com.weicoder.web.socket.impl.netty;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;

import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.base.BaseSession;

/**
 * netty Session实现
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-20
 */
public final class NettySession extends BaseSession implements Session {
	// 通道
	private Channel	channel;

	/**
	 * 构造
	 * @param id sessionId
	 * @param channel
	 */
	public NettySession(Channel channel) {
		this.id = channel.hashCode();
		this.channel = channel;
		address(channel.remoteAddress());
	}

	@Override
	public void close() {
		channel.disconnect();
		channel.close();
	}

	@Override
	public boolean isConnect() {
		return channel.isActive();
	}

	@Override
	public boolean isClose() {
		return !channel.isOpen();
	}

	@Override
	public void send(byte[] data) {
		channel.writeAndFlush(PooledByteBufAllocator.DEFAULT.buffer().writeBytes(data));
	}
}
