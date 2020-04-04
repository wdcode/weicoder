package com.weicoder.netty3;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;

import com.weicoder.socket.Session;
import com.weicoder.socket.base.BaseSession;

/**
 * netty Session实现
 * 
 * @author  WD
 * @version 1.0
 */
public final class Netty3Session extends BaseSession implements Session {
	// 通道
	private Channel channel;

	/**
	 * 构造
	 * 
	 * @param id      sessionId
	 * @param channel
	 */
	public Netty3Session(String name, Channel channel) {
		super(name);
		this.id = channel.getId();
		this.channel = channel;
		address(channel.getRemoteAddress());
	}

	@Override
	public void write(byte[] data) {
		channel.write(ChannelBuffers.wrappedBuffer(data));
	}

	@Override
	public void close() throws Exception {
		channel.close();
		channel.disconnect();
	}

	@Override
	public void flush() {
	}
}
