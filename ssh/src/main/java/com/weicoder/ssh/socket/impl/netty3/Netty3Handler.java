package com.weicoder.ssh.socket.impl.netty3;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.ChannelHandler.Sharable;

import com.weicoder.ssh.socket.Session; 
import com.weicoder.ssh.socket.process.Process;

/**
 * Netty3 处理器
 * @author WD 
 * @version 1.0 
 */ 
@Sharable
public final class Netty3Handler extends SimpleChannelHandler {
	// 名称
	private String	name;
	// 消息处理器
	private Process	process;

	/**
	 * 构造
	 * @param process
	 */
	public Netty3Handler(String name, Process process) {
		this.name = name;
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
		Session session = process.session(channel.getId());
		// 如果Session为空
		if (session == null) {
			session = new Netty3Session(name, channel);
		}
		// 返回Session
		return session;
	}
}
