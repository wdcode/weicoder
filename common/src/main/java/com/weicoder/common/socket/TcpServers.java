package com.weicoder.common.socket;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.weicoder.common.interfaces.Calls;
import com.weicoder.common.io.I;
import com.weicoder.common.util.U;
import com.weicoder.common.log.Logs;
import com.weicoder.common.thread.T;

/**
 * aio server socket
 * 
 * @author wudi
 */
public final class TcpServers {
	/**
	 * aio绑定监听端口
	 * 
	 * @param port 端口
	 * @param call 回调
	 */
	public static void aio(int port, Calls.EoR<byte[], byte[]> call) {
		T.start(() -> {
			// 开启aio socket server
			try (AsynchronousServerSocketChannel socket = AsynchronousServerSocketChannel.open()) {
				// 绑定端口监听
				socket.bind(new InetSocketAddress(port));
				Logs.debug("aio socket bind is port={} open={}", port, socket.isOpen());
				// 循环接收数据
				while (true) {
					try {
						// 接收客户端数据 获取aio通道
						AsynchronousSocketChannel asc = socket.accept().get();
						long time = System.currentTimeMillis();
						// 读取出数据 放入回调 并且接收回调处理结果
						byte[] b = call.call(I.A.read(asc, false));
						// 如果返回结果不为空 写入到客户端
						if (U.E.isNotEmpty(b)) {
							I.A.write(asc, b, false);
							asc.shutdownOutput();
						}
						Logs.info("aio socket accept end time={} channel={}", U.D.diff(time), asc);
					} catch (Exception e) {
						Logs.error(e);
					}
				}
			} catch (Exception e) {
				Logs.error(e);
			}
		});
	}

	/**
	 * nio绑定监听端口
	 * 
	 * @param port 端口
	 * @param call 回调
	 */
	public static void nio(int port, Calls.EoR<byte[], byte[]> call) {
		T.start(() -> {
			// 开启nio socket server
			try (ServerSocketChannel socket = ServerSocketChannel.open()) {
				// 绑定端口监听
				socket.bind(new InetSocketAddress(port));
				Logs.debug("nio socket bind is port={} open={}", port, socket.isOpen());
				// 循环接收数据
				while (true) {
					try {
						// 接收客户端数据 获取aio通道
						SocketChannel sc = socket.accept();
						long time = U.D.now();
						// 读取出数据 放入回调 并且接收回调处理结果
						byte[] b = call.call(I.C.read(sc, false));
						// 如果返回结果不为空 写入到客户端
						if (U.E.isNotEmpty(b)) {
							I.C.write(sc, b, false);
							sc.shutdownOutput();
						}
						Logs.info("nio socket accept end time={} channel={}", U.D.diff(time), sc);
					} catch (Exception e) {
						Logs.error(e);
					}
				}
			} catch (Exception e) {
				Logs.error(e);
			}
		});
	}

	/**
	 * bio绑定监听端口
	 * 
	 * @param port 端口
	 * @param call 回调
	 */
	public static void bio(int port, Calls.EoR<byte[], byte[]> call) {
		T.start(() -> {
			// 开启bio socket server
			try (ServerSocket socket = new ServerSocket(port)) {
				Logs.debug("bio socket bind is port={} bound={} close={}", port, socket.isBound(), socket.isClosed());
				// 循环接收数据
				while (true) {
					try {
						// 接收客户端数据 获取aio通道
						Socket sc = socket.accept();
						long time = System.currentTimeMillis();
						// 读取出数据 放入回调 并且接收回调处理结果
						byte[] b = call.call(I.read(sc.getInputStream(), false));
						// 如果返回结果不为空 写入到客户端
						if (U.E.isNotEmpty(b)) {
							I.write(sc.getOutputStream(), b, false);
							sc.shutdownOutput();
						}
						Logs.info("bio socket accept end time={} channel={}", U.D.diff(time), sc);
					} catch (Exception e) {
						Logs.error(e);
					}
				}
			} catch (Exception e) {
				Logs.error(e);
			}
		});
	}

	private TcpServers() {
	}
}
