package com.weicoder.netty.session;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;

import com.weicoder.common.lang.W;
import com.weicoder.socket.base.BaseSession;

/**
 * netty Session实现
 * 
 * @author WD
 */
public class NettySession extends BaseSession {
	// 通道
	private Channel channel;

	/**
	 * 构造
	 * 
	 * @param name
	 */
	public NettySession(String name) {
		super(name);
	}

	/**
	 * 构造
	 * 
	 * @param name    名称
	 * @param channel 通道
	 */
	public NettySession(String name, Channel channel) {
		this(name);
		channel(channel);
	}

	/**
	 * 设置Channel
	 * 
	 * @param channel
	 */
	protected void channel(Channel channel) {
		address(channel.remoteAddress());
		this.id = W.C.toLong(channel.id().asLongText()); // Bytes.toLong(Bytes.toBytes(IpUtil.encode(ip), port));
		this.channel = channel;
	}

	@Override
	public void write(byte[] data) {
		channel.write(PooledByteBufAllocator.DEFAULT.buffer().writeBytes(data));
	}

	@Override
	public void close() {
		channel.flush();
		channel.close();
		channel.disconnect();
		channel = null;
	}

	@Override
	public void flush() {
		channel.flush();
	}
}
