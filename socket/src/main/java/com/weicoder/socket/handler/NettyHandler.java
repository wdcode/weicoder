package com.weicoder.socket.handler;

import java.util.Map;

import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.socket.Session;
import com.weicoder.socket.process.Process;
import com.weicoder.socket.session.NettySession;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.ChannelHandler.Sharable; 

/**
 * Netty 处理器
 * @author WD
 */
@Sharable
public class NettyHandler extends SimpleChannelInboundHandler<ByteBuf> {
	// 名称
	private String					name;
	// 消息处理器
	private Process					process;
	// 保存session
	private Map<ChannelId, Session>	sessions;
	// 保存Session连接
//	private AttributeKey<Session>	sessionKey;

	/**
	 * 构造
	 * @param name 名称
	 */
	public NettyHandler(String name) {
		this.name = name;
		this.process = new Process(name);
		this.sessions = Maps.newConcurrentMap();
//		this.sessionKey = AttributeKey.valueOf("session");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Session s = getSesson(ctx.channel());
		if (s == null)
			Logs.debug("channel session is null = {}", ctx);
		else
			Logs.debug("channel is inactive = {}", ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 设置session
		Channel c = ctx.channel();
		Session s = new NettySession(name, c);
		sessions.put(c.id(), s);
//		ctx.channel().attr(sessionKey).set(s);
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
		read(ctx, msg);
	}

	/**
	 * 读取数据流
	 * @param ctx ChannelHandlerContext
	 * @param msg 消息
	 */
	protected void read(ChannelHandlerContext ctx, ByteBuf msg) {
		// 声明字节流
		byte[] data = new byte[msg.readableBytes()];
		// 读取字节流
		msg.readBytes(data);
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
		Session s = sessions.get(channel.id()); // channel.attr(sessionKey).get();
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
