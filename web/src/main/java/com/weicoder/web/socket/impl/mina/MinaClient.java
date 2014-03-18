package com.weicoder.web.socket.impl.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.base.BaseClient;

/**
 * mina客户端
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-19
 */
public final class MinaClient extends BaseClient {
	// MinaHandler
	private MinaHandler		handler;
	// 客户端连接
	private SocketConnector	connector;
	// 客户端ConnectFuture
	private ConnectFuture	future;

	/**
	 * 构造方法
	 * @param name
	 */
	public MinaClient(String name) {
		super(name);
		// 客户端
		this.connector = new NioSocketConnector(1);
		// 实例化handler
		handler = new MinaHandler(process);
		// 获得Session配置
		SocketSessionConfig sc = connector.getSessionConfig();
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
		connector.setHandler(handler);
		// 绑定服务器数据监听端口，启动服务器
		connector.setDefaultRemoteAddress(new InetSocketAddress(SocketParams.getHost(name), SocketParams.getPort(name)));
	}

	@Override
	public void connect() {
		future = connector.connect().awaitUninterruptibly();
		IoSession io = future.getSession();
		setSession(new MinaSession(io));
	}

	@Override
	public void close() {
		// 关闭Session
		future.getSession().close(false).awaitUninterruptibly();
		// 关闭acceptor
		connector.dispose(false);
	}
}
