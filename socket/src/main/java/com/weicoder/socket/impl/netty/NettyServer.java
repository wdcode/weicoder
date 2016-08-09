package com.weicoder.socket.impl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.weicoder.socket.params.SocketParams;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.socket.base.BaseServer;

/**
 * netty实现
 * @author WD
 */
public final class NettyServer extends BaseServer {
	// Netty ServerBootstrap
	private ServerBootstrap	bootstrap;
	// NettyHandler
	private NettyHandler	handler;

	/**
	 * 构造函数
	 * @param name 名称
	 */
	public NettyServer(String name) {
		super(name);
		// 实例化ServerBootstrap
		bootstrap = new ServerBootstrap();
		// NettyHandler
		handler = new NettyHandler(name, process);
		// 设置group
		bootstrap.group(new NioEventLoopGroup(1),
				new NioEventLoopGroup(SystemConstants.CPU_NUM * 2));
		// 设置属性
		bootstrap.childOption(ChannelOption.SO_REUSEADDR, true)
				.childOption(ChannelOption.TCP_NODELAY, true)
				.childOption(ChannelOption.SO_KEEPALIVE, false)
				.childOption(ChannelOption.SO_LINGER, 0)
				.childOption(ChannelOption.SO_BACKLOG, Short.MAX_VALUE*1)
				.childOption(ChannelOption.SO_SNDBUF, 1024 * 32)
				.childOption(ChannelOption.SO_RCVBUF, 1024 * 8);
		bootstrap.option(ChannelOption.SO_REUSEADDR, true)
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.SO_KEEPALIVE, false)
				.option(ChannelOption.SO_LINGER, 0)
				.childOption(ChannelOption.SO_BACKLOG, Short.MAX_VALUE*1)
				.option(ChannelOption.SO_SNDBUF, 1024 * 32)
				.option(ChannelOption.SO_RCVBUF, 1024 * 8);
		// 设置channel
		bootstrap.channel(NioServerSocketChannel.class);
		// 设置初始化 handler
		bootstrap.childHandler(handler);
		// 设置监听端口
		bootstrap.localAddress(SocketParams.getPort(name));
	}

	@Override
	public void bind() {
		bootstrap.bind();
	}
}
