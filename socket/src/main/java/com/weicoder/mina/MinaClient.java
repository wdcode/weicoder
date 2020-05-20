package com.weicoder.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
 
import com.weicoder.socket.params.SocketParams;
import com.weicoder.socket.process.Process;
import com.weicoder.socket.Client;
import com.weicoder.socket.base.BaseSession;

/**
 * mina客户端
 * 
 * @author  WD
 * @version 1.0
 */
public final class MinaClient extends BaseSession implements Client {
	// MinaHandler
	private MinaHandler handler;
	// 客户端连接
	private SocketConnector connector;
	// 客户端ConnectFuture
	private ConnectFuture future;
	// Session
	private IoSession session;

	/**
	 * 构造方法
	 * 
	 * @param name
	 */
	public MinaClient(String name) {
		super(name);
		// 客户端
		this.connector = new NioSocketConnector(SocketParams.POOL);
		// 实例化handler
		handler = new MinaHandler(name, new Process(name));
		// 获得Session配置
		SocketSessionConfig sc = connector.getSessionConfig();
		// flush函数的调用 设置为非延迟发送，为true则不组装成大包发送，收到东西马上发出
		sc.setTcpNoDelay(true);
		sc.setKeepAlive(false);
		sc.setSoLinger(0);
		// 设置最小读取缓存
		sc.setMinReadBufferSize(64);
		// 设置输入缓冲区的大小
		sc.setReceiveBufferSize(1024 * 8);
		// 设置输出缓冲区的大小
		sc.setSendBufferSize(1024 * 32);
		// 设置超时时间
		sc.setWriteTimeout(30);
		sc.setWriterIdleTime(60);
		sc.setReaderIdleTime(30);
		sc.setBothIdleTime(180);
		// 绑定Mina服务器管理模块
		connector.setHandler(handler);
		// 绑定服务器数据监听端口，启动服务器
		connector.setDefaultRemoteAddress(new InetSocketAddress(SocketParams.CLINET_HOST, SocketParams.CLINET_PORT));
		future = connector.connect().awaitUninterruptibly();
		session = future.getSession(); 
	}

	@Override
	public void close() {
		connector.dispose();
		session.closeOnFlush();
	}

	@Override
	public void write(byte[] data) {
		session.write(IoBuffer.wrap(data));
	}

	@Override
	public void flush() {
	}
}
