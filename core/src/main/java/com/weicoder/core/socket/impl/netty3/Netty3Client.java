package com.weicoder.core.socket.impl.netty3;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import com.weicoder.common.log.Logs;
import com.weicoder.core.params.SocketParams;
import com.weicoder.core.socket.base.BaseClient;

/**
 * netty客户端
 * @author WD 
 * @version 1.0 
 */
public final class Netty3Client extends BaseClient {
	// 保存Netty客户端 Bootstrap
	private ClientBootstrap	bootstrap;
	// 保存Netty服务器 ChannelFuture
	private ChannelFuture	future;
	// NettyHandler
	private Netty3Handler	handler;

	/**
	 * 构造方法
	 * @param name
	 */
	public Netty3Client(String name) {
		super(name);
		// 实例化ServerBootstrap
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory());
		// NettyHandler
		handler = new Netty3Handler(name, process);
		// 设置属性
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", false);
		bootstrap.setOption("soLinger", 0);
		bootstrap.setOption("remoteAddress", new InetSocketAddress(SocketParams.getHost(name), SocketParams.getPort(name)));
		// 设置handler
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(handler);
			}
		});
	}

	@Override
	public void connect() {
		future = bootstrap.connect().awaitUninterruptibly();
		session(new Netty3Session(name, future.getChannel()));
	}

	@Override
	public void close() {
		session.close();
		bootstrap.releaseExternalResources();
		bootstrap.shutdown();
		Logs.info("client close name=" + name);
	}
}
