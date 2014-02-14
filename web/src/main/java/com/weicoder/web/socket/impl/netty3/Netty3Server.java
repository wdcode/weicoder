package com.weicoder.web.socket.impl.netty3;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.base.BaseServer;

/**
 * netty实现
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-15
 */
public final class Netty3Server extends BaseServer {
	// Netty ServerBootstrap
	private ServerBootstrap	bootstrap;
	// NettyHandler
	private Netty3Handler	handler;

	/**
	 * 构造函数
	 * @param name 名称
	 */
	public Netty3Server(String name) {
		super(name);
		// 实例化ServerBootstrap
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		// NettyHandler
		handler = new Netty3Handler(process);
		// 设置属性
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setOption("child.reuseAddress", true);
		bootstrap.setOption("reuseAddress", true);
		bootstrap.setOption("localAddress", new InetSocketAddress(SocketParams.getPort(name)));
		// 设置handler
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(handler);
			}
		});
	}

	@Override
	public void close() {
		bootstrap.releaseExternalResources();
		bootstrap.shutdown();
	}

	@Override
	public void bind() {
		bootstrap.bind();
	}
}
