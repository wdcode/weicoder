package com.weicoder.netty3;

import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
 
import com.weicoder.socket.params.SocketParams;
import com.weicoder.socket.process.Process;
import com.weicoder.socket.base.BaseSession;

/**
 * netty客户端
 * 
 * @author  WD
 * @version 1.0
 */
public final class Netty3Client extends BaseSession {
	// 保存Netty客户端 Bootstrap
	private ClientBootstrap bootstrap;
	// 保存Netty服务器 ChannelFuture
	private ChannelFuture future;
	// NettyHandler
	private Netty3Handler handler;
	// 通道
	private Channel channel;
	 
	/**
	 * 构造方法
	 * 
	 * @param name
	 */
	public Netty3Client(String name) {
		super(name);
		// 实例化ServerBootstrap
		bootstrap = new ClientBootstrap(new NioClientSocketChannelFactory());
		// NettyHandler
		handler = new Netty3Handler(name, new Process(name));
		// 设置属性
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", false);
		bootstrap.setOption("soLinger", 0);
		bootstrap.setOption("remoteAddress",
				new InetSocketAddress(SocketParams.CLINET_HOST, SocketParams.CLINET_PORT));
		// 设置handler
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				return Channels.pipeline(handler);
			}
		});
		future = bootstrap.connect().awaitUninterruptibly();
		channel = future.getChannel(); 
	}

	@Override
	public void close() { 
		channel.close();
		channel.disconnect();
		bootstrap.releaseExternalResources(); 
	}

	@Override
	public void write(byte[] data) {
		channel.write(ChannelBuffers.wrappedBuffer(data));
	}

	@Override
	public void flush() {

	}
}
