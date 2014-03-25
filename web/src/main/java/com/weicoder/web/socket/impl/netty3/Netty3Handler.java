package com.weicoder.web.socket.impl.netty3;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.process.Process;

/**
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-15
 */
// @org.jboss.netty.channel.ChannelHandler.Sharable
public final class Netty3Handler extends SimpleChannelHandler {
	// 消息处理器
	private Process	process;

	/**
	 * 构造
	 * @param process
	 */
	public Netty3Handler(Process process) {
		this.process = process;
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		process.connected(getSesson(ctx.getChannel()));
		// super.channelConnected(ctx, e);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		process.closed(getSesson(ctx.getChannel()));
		// super.channelClosed(ctx, e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		// 获得通道
		ChannelBuffer msg = (ChannelBuffer) e.getMessage();
		// 声明字节流
		byte[] data = new byte[msg.readableBytes()];
		// 读取字节流
		msg.readBytes(data);
		// 交给数据处理器
		process.process(getSesson(ctx.getChannel()), data);
	}

	/**
	 * 获得Session
	 * @param channel 通道
	 * @return Session
	 */
	private Session getSesson(Channel channel) {
		// 获得Session
		Session session = process.getSession(channel.getId());
		// 如果Session为空
		if (session == null) {
			session = new Netty3Session(channel);
		}
		// 返回Session
		return session;
	}
}
