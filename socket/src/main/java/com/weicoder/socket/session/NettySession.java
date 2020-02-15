package com.weicoder.socket.session;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
 
import com.weicoder.common.lang.C; 
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
		address(channel.remoteAddress());
		this.id = C.toLong(channel.id().asLongText()); // Bytes.toLong(Bytes.toBytes(IpUtil.encode(ip), port));
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
