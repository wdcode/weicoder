package com.weicoder.netty3;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import com.weicoder.socket.params.SocketParams;
import com.weicoder.socket.process.Process;

import com.weicoder.socket.Server;

/**
 * netty实现
 * 
 * @author  WD
 * @version 1.0
 */
public final class Netty3Server implements Server {
	// Netty ServerBootstrap
	private ServerBootstrap bootstrap;
	// NettyHandler
	private Netty3Handler handler;

	/**
	 * 构造函数
	 * 
	 * @param name 名称
	 */
	public Netty3Server(String name) {
		// 实例化ServerBootstrap
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory());
		// NettyHandler
		handler = new Netty3Handler(name, new Process(name));
		// 设置属性
		bootstrap.setOption("child.reuseAddress", true);
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", false);
		bootstrap.setOption("child.soLinger", 0);
		bootstrap.setOption("reuseAddress", true);
		bootstrap.setOption("localAddress", new InetSocketAddress(SocketParams.SERVER_PORT));
		// 设置handler
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(handler);
			}
		});
	}

	@Override
	public void bind() {
		bootstrap.bind();
	}
}
