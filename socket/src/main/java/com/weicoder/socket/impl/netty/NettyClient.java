package com.weicoder.socket.impl.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.weicoder.socket.params.SocketParams;
import com.weicoder.socket.base.BaseClient;

/**
 * netty客户端
 * @author WD
 */
public final class NettyClient extends BaseClient {
	// 保存Netty客户端 Bootstrap
	private Bootstrap		bootstrap;
	// 保存Netty服务器 ChannelFuture
	private ChannelFuture	future;
	// NettyHandler
	private NettyHandler	handler;

	/**
	 * 构造方法
	 * @param name 名称
	 */
	public NettyClient(String name) {
		super(name);
		// 实例化ClientBootstrap
		bootstrap = new Bootstrap();
		// NettyHandler
		handler = new NettyHandler(name, process);
		// 设置group
		bootstrap.group(new NioEventLoopGroup(1));
		// 设置属性
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, false);
		bootstrap.option(ChannelOption.SO_LINGER, 0);
		bootstrap.option(ChannelOption.SO_SNDBUF, 1024 * 32);
		bootstrap.option(ChannelOption.SO_RCVBUF, 1024 * 8);
		// 设置channel
		bootstrap.channel(NioSocketChannel.class);
		// 设置初始化 handler
		bootstrap.handler(handler);
		// 设置监听端口
		bootstrap.remoteAddress(SocketParams.getHost(name), SocketParams.getPort(name));
	}

	@Override
	public void connect() {
		future = bootstrap.connect().awaitUninterruptibly();
		session(new NettySession(name, future.channel()));
	}

	// @Override
	// public void close() {
	// session.close();
	// bootstrap.group().shutdownGracefully();
	// Logs.info("client close name=" + name);
	// }
}
