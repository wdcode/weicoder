package com.weicoder.common.socket;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.weicoder.common.interfaces.Callback;
import com.weicoder.common.io.AsynChannelUtil;
import com.weicoder.common.io.ChannelUtil;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.ThreadUtil;

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
	public static void aio(int port, Callback<byte[], byte[]> call) {
		ThreadUtil.start(() -> {
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
						byte[] b = call.callback(AsynChannelUtil.read(asc, false));
						// 如果返回结果不为空 写入到客户端
						if (EmptyUtil.isNotEmpty(b)) {
							AsynChannelUtil.write(asc, b, false);
							asc.shutdownOutput();
						}
						Logs.info("aio socket accept end time={} channel={}", DateUtil.diff(time), asc);
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
	public static void nio(int port, Callback<byte[], byte[]> call) {
		ThreadUtil.start(() -> {
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
						long time = System.currentTimeMillis();
						// 读取出数据 放入回调 并且接收回调处理结果
						byte[] b = call.callback(ChannelUtil.read(sc, false));
						// 如果返回结果不为空 写入到客户端
						if (EmptyUtil.isNotEmpty(b)) {
							ChannelUtil.write(sc, b, false);
							sc.shutdownOutput();
						}
						Logs.info("nio socket accept end time={} channel={}", DateUtil.diff(time), sc);
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
	public static void bio(int port, Callback<byte[], byte[]> call) {
		ThreadUtil.start(() -> {
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
						byte[] b = call.callback(IOUtil.read(sc.getInputStream(), false));
						// 如果返回结果不为空 写入到客户端
						if (EmptyUtil.isNotEmpty(b)) {
							IOUtil.write(sc.getOutputStream(), b, false);
							sc.shutdownOutput();
						}
						Logs.info("bio socket accept end time={} channel={}", DateUtil.diff(time), sc);
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
