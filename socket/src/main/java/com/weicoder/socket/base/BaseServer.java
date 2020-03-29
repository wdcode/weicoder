package com.weicoder.socket.base;

import io.netty.bootstrap.ServerBootstrap; 
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.weicoder.socket.params.SocketParams;
import com.weicoder.socket.Server;

/**
 * netty实现
 * @author WD
 */
public abstract class BaseServer implements Server {
	// Netty ServerBootstrap
	private ServerBootstrap bootstrap;

	/**
	 * 构造函数 
	 */
	public BaseServer() {
		// 实例化ServerBootstrap
		bootstrap = new ServerBootstrap();
		// 设置group
		bootstrap.group(new NioEventLoopGroup(1), new NioEventLoopGroup(SocketParams.POOL));
		// 设置属性
		bootstrap.option(ChannelOption.SO_REUSEADDR, true);
		// bootstrap.option(ChannelOption.TCP_NODELAY, true);
		// bootstrap.option(ChannelOption.SO_KEEPALIVE, false);
		// bootstrap.option(ChannelOption.SO_LINGER, 0);
//		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
		// bootstrap.option(ChannelOption.SO_SNDBUF, 1024 * 32);
		bootstrap.option(ChannelOption.SO_RCVBUF, 1024 * 8);

		bootstrap.childOption(ChannelOption.SO_REUSEADDR, true);
		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, false);
		bootstrap.childOption(ChannelOption.SO_LINGER, 0);
		bootstrap.childOption(ChannelOption.SO_BACKLOG, 1024);
		bootstrap.childOption(ChannelOption.SO_SNDBUF, 1024 * 32);
		bootstrap.childOption(ChannelOption.SO_RCVBUF, 1024 * 8);

		// 设置channel
		bootstrap.channel(channel());
		// 设置初始化 handler
		bootstrap.childHandler(handler());
		// 设置监听端口
		bootstrap.localAddress(port());
	}

	@Override
	public void bind() {
		bootstrap.bind();
	}

	/**
	 * 获得server处理handler
	 * @return ChannelHandler
	 */
	protected abstract ChannelHandler handler();

	/**
	 * 获得服务器监听端口
	 * @return 端口
	 */
	protected abstract int port();

	/**
	 * 获得server处理Channel
	 * @return Channel
	 */
	protected Class<? extends ServerChannel> channel() {
		return NioServerSocketChannel.class;
	}
}
