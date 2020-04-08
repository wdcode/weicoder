package com.weicoder.netty.client;

import io.netty.bootstrap.Bootstrap; 
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.weicoder.socket.params.SocketParams;
import com.weicoder.netty.session.NettySession;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.weicoder.common.log.Logs;
import com.weicoder.socket.Client;
import com.weicoder.netty.handler.NettyHandler;

/**
 * netty客户端
 * 
 * @author WD
 */
public final class NettyClient extends NettySession implements Client {
	/**
	 * 构造方法
	 * 
	 * @param name 名称
	 */
	public NettyClient(String name) {
		super(name);
		// 实例化ClientBootstrap
		Bootstrap bootstrap = new Bootstrap();
		// NettyHandler
		NettyHandler handler = new NettyHandler(name);
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
		// 设置监听端口 并连接远程服务器
		bootstrap.remoteAddress(SocketParams.CLINET_HOST, SocketParams.CLINET_PORT);
		ChannelFuture future = bootstrap.connect().awaitUninterruptibly();
		channel(future.channel());
		// 定时检测
		if (SocketParams.HEART) {
			Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
				// 发送心跳
				send(SocketParams.HEART_ID, null);
				Logs.trace("testing heart client");
			}, 0, SocketParams.TIME / 2, TimeUnit.SECONDS);
		}
	}
}
