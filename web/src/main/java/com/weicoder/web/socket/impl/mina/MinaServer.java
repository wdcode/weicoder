package com.weicoder.web.socket.impl.mina;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import com.weicoder.core.log.Logs;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.base.BaseServer;

/**
 * mina实现
 * @author WD
 * @since JDK7
 * @version 1.0 2013-11-28
 */
public final class MinaServer extends BaseServer {
	// SocketAcceptor
	private SocketAcceptor	acceptor;
	// MinaHandler
	protected MinaHandler	handler;

	/**
	 * 构造方法
	 * @param name 名称
	 */
	public MinaServer(String name) {
		super(name);
		// 服务器
		this.acceptor = new NioSocketAcceptor(SocketParams.POOL);
		// 实例化handler
		handler = new MinaHandler(process);
		// 获得Session配置
		SocketSessionConfig sc = acceptor.getSessionConfig();
		// 设置每一个非主监听连接的端口可以重用
		sc.setReuseAddress(true);
		// 设置最小读取缓存
		sc.setMinReadBufferSize(64);
		// 设置输入缓冲区的大小
		sc.setReceiveBufferSize(1024 * 8);
		// 设置输出缓冲区的大小
		sc.setSendBufferSize(1024 * 32);
		// flush函数的调用 设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
		sc.setTcpNoDelay(true);
		sc.setSoLinger(0);
		// 设置超时时间
		sc.setWriteTimeout(10000);
		sc.setWriterIdleTime(60);
		sc.setReaderIdleTime(30);
		sc.setBothIdleTime(180);
		// 绑定Mina服务器管理模块
		acceptor.setHandler(handler);
		// 绑定服务器数据监听端口，启动服务器
		acceptor.setDefaultLocalAddress(new InetSocketAddress(SocketParams.getPort(name)));

	}

	/**
	 * 启动服务器监听
	 */
	public void bind() {
		// 绑定端口并启动
		try {
			acceptor.bind();
		} catch (IOException e) {
			Logs.warn(e);
		}
	}

	@Override
	public void close() {
		acceptor.dispose();
	}
}
