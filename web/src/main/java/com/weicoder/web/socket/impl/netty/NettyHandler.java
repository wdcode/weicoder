package com.weicoder.web.socket.impl.netty;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.socket.Process;
import com.weicoder.web.socket.Session;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-15
 */
public final class NettyHandler extends SimpleChannelInboundHandler<ByteBuf> {
	// 消息处理器
	private Process	process;

	/**
	 * 构造
	 * @param process
	 */
	public NettyHandler(Process process) {
		this.process = process;
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		process.closed(getSesson(ctx));
		super.channelUnregistered(ctx);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		process.connected(getSesson(ctx), new NettyBuffer());
		super.channelActive(ctx);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		// 声明字节流
		byte[] data = new byte[msg.readableBytes()];
		// 读取字节流
		msg.readBytes(data);
		// 交给数据处理器
		process.process(getSesson(ctx), data);
	}

	/**
	 * 获得包装Session
	 * @param session Mina session
	 * @return
	 */
	private Session getSesson(ChannelHandlerContext ctx) {
		// 获得SessionId
		int id = Conversion.toInt(StringUtil.subString(ctx.name(), StringConstants.WELL));
		// 获得包装Session
		Session s = process.getSession(id);
		// 如果为null
		if (s == null) {
			// 实例化包装Session
			s = new NettySession(id, ctx.channel());
		}
		// 返回
		return s;
	}
}
