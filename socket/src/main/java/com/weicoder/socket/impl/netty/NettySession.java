package com.weicoder.socket.impl.netty;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;

import com.weicoder.socket.Session;
import com.weicoder.socket.base.BaseSession;

/**
 * netty Session实现
 * @author WD
 */
public final class NettySession extends BaseSession implements Session {
	// 通道
	private Channel channel;

	/**
	 * 构造
	 * @param name 名称
	 * @param channel 通道
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
}
