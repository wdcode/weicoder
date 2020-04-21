package com.weicoder.socket.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.weicoder.socket.params.SocketParams;
import com.weicoder.socket.Server;

/**
 * netty实现
 * @author WD
 */
public final class NettyServer implements Server {
	// Netty ServerBootstrap
	private ServerBootstrap	bootstrap;
	// NettyHandler
	private NettyHandler	handler;

	/**
	 * 构造函数
	 * @param name 名称
	 */
	public NettyServer(String name) {
		// 实例化ServerBootstrap
		bootstrap = new ServerBootstrap();
		// NettyHandler
		handler = new NettyHandler(name);
		// 设置group
		bootstrap.group(new NioEventLoopGroup(1), new NioEventLoopGroup(SocketParams.POOL));
		// 设置属性
		bootstrap.option(ChannelOption.SO_REUSEADDR, true);
		// bootstrap.option(ChannelOption.TCP_NODELAY, true);
		// bootstrap.option(ChannelOption.SO_KEEPALIVE, false);
		// bootstrap.option(ChannelOption.SO_LINGER, 0);
		bootstrap.option(ChannelOption.SO_BACKLOG, Short.MAX_VALUE * 1);
		// bootstrap.option(ChannelOption.SO_SNDBUF, 1024 * 32);
		bootstrap.option(ChannelOption.SO_RCVBUF, 1024 * 8);

		bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, false);
		bootstrap.childOption(ChannelOption.SO_LINGER, 0);
		bootstrap.childOption(ChannelOption.SO_BACKLOG, Short.MAX_VALUE * 1);
		bootstrap.childOption(ChannelOption.SO_SNDBUF, 1024 * 32);
		bootstrap.childOption(ChannelOption.SO_RCVBUF, 1024 * 8);

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
