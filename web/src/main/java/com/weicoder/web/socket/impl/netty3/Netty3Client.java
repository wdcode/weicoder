package com.weicoder.web.socket.impl.netty3;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.base.BaseClient;

/**
 * netty客户端
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-19
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
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
		// NettyHandler
		handler = new Netty3Handler(process);
		// 设置属性
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", true);
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
		setSession(new Netty3Session(future.getChannel()));
	}

	@Override
	public void close() {
		session.close();
		bootstrap.releaseExternalResources();
		bootstrap.shutdown();
	}
}
