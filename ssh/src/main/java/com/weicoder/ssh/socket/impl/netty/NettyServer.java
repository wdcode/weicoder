package com.weicoder.ssh.socket.impl.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.weicoder.ssh.params.SocketParams;
import com.weicoder.ssh.socket.base.BaseServer;

/**
 * netty实现
 * @author WD 
 * @version 1.0 
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
		bootstrap.group(new NioEventLoopGroup(SocketParams.getPool(name)), new NioEventLoopGroup(SocketParams.getPool(name)));
		// 设置属性
		bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, false);
		bootstrap.childOption(ChannelOption.SO_LINGER, 0); 
		bootstrap.childOption(ChannelOption.SO_SNDBUF, 1024 * 32);
		bootstrap.childOption(ChannelOption.SO_RCVBUF, 1024 * 8);
		bootstrap.option(ChannelOption.SO_REUSEADDR, true);
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, false);
		bootstrap.option(ChannelOption.SO_LINGER, 0); 
		bootstrap.option(ChannelOption.SO_SNDBUF, 1024 * 32);
		bootstrap.option(ChannelOption.SO_RCVBUF, 1024 * 8);
		// 设置channel
		bootstrap.channel(NioServerSocketChannel.class);
		// 设置初始化 handler
		bootstrap.childHandler(handler);
		// 设置监听端口
		bootstrap.localAddress(SocketParams.getPort(name));
	}

	@Override
	public void close() {
		bootstrap.config().group().shutdownGracefully();
		bootstrap.config().childGroup().shutdownGracefully();
	}

	@Override
	public void bind() {
		bootstrap.bind();
	}
}
