package com.weicoder.core.socket.impl.netty;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;

import com.weicoder.core.socket.Session;
import com.weicoder.core.socket.base.BaseSession;

/**
 * netty Session实现
 * @author WD
 * @since JDK7
 * @version 1.0 
 */
public final class NettySession extends BaseSession implements Session {
	// 通道
	private Channel	channel;

	/**
	 * 构造
	 * @param id sessionId
	 * @param channel
	 */
	public NettySession(String name, Channel channel) {
		super(name);
		this.id = channel.hashCode();
		this.channel = channel;
		address(channel.remoteAddress());
	}

	@Override
	public void write(byte[] data) {
		channel.writeAndFlush(PooledByteBufAllocator.DEFAULT.buffer().writeBytes(data));
	}

	@Override
	protected void close0() {
		channel.close();
		channel.disconnect();
		channel = null;
	}

	@Override
	public boolean isEmpty() {
		return channel == null;
	}
}
