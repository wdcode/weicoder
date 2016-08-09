package com.weicoder.socket.impl.netty;

import com.weicoder.common.log.Logs;
import com.weicoder.socket.Session;
import com.weicoder.socket.process.Process;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.AttributeKey;

/**
 * Netty 处理器
 * @author WD
 */
@Sharable
public final class NettyHandler extends SimpleChannelInboundHandler<ByteBuf> {
	// 名称
	private String					name;
	// 消息处理器
	private Process					process;
	// 保存Session连接
	private AttributeKey<Session>	sessionKey;

	/**
	 * 构造
	 * @param name 名称
	 * @param process 处理器
	 */
	public NettyHandler(String name, Process process) {
		this.name = name;
		this.process = process;
		this.sessionKey = AttributeKey.valueOf("session");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		process.closed(getSesson(ctx.channel()));
		Logs.debug("channel is inactive = {}", ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 设置session
		Session s = null;
		ctx.channel().attr(sessionKey).set(s = new NettySession(name, ctx.channel()));
		// 调用连接
		process.connected(s);
		Logs.debug("channel is active = {}", ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Logs.error(cause);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		// 声明字节流
		byte[] data = new byte[msg.readableBytes()];
		// 读取字节流
		msg.readBytes(data);
		Logs.trace("channel read data len={} channel={}", data.length, ctx);
		// 交给数据处理器
		process.process(getSesson(ctx.channel()), data);
	}

	/**
	 * 获得包装Session
	 * @param channel netty channel
	 * @return Session
	 */
	private Session getSesson(Channel channel) {
		// 获得Session
		Session s = channel.attr(sessionKey).get();
		// Session为空直接断开连接
		if (s == null) {
			Logs.warn("channel to session is null channel", channel);
			channel.close();
			channel.disconnect();
		}
		// 返回
		return s;
	}
}
