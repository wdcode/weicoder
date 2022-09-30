package com.weicoder.common.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.SocketChannel;

import com.weicoder.common.constants.C;
import com.weicoder.common.io.I; 
import com.weicoder.common.log.Logs;

/**
 * TCP 客户端发包处理器
 * 
 * @author WD
 */
public final class TcpClient {
	/**
	 * bio模式发送数据 不接收返回数据
	 * 
	 * @param host
	 * @param port
	 * @param data
	 */
	public static void send(String host, int port, byte[] data) {
		send(host, port, data, false);
	}

	/**
	 * bio模式发送数据
	 * 
	 * @param host 服务器主机
	 * @param port 服务器端口
	 * @param data 发送数据
	 * @param read 是否读取回执数据
	 * @return byte[] 字节流
	 */
	public static byte[] send(String host, int port, byte[] data, boolean read) {
		return send(new InetSocketAddress(host, port), data, read);
	}

	/**
	 * bio模式发送数据
	 * 
	 * @param addr 服务器调用地址
	 * @param data 发送数据
	 * @param read 是否读取回执数据
	 * @return byte[] 字节流
	 */
	public static byte[] send(InetSocketAddress addr, byte[] data, boolean read) {
		// 实例化Socket
		try (Socket socket = new Socket()) {
			// 连接服务器
			socket.connect(addr);
			// 写入数据流
			I.write(socket.getOutputStream(), data, false);
			// 读取数据
			if (read) {
				socket.shutdownOutput();
				return I.read(socket.getInputStream(), false);
			}
		} catch (IOException e) {
			Logs.error(e);
		}
		return C.A.BYTES_EMPTY;
	}

	/**
	 * nio模式发送数据
	 * 
	 * @param host 服务器主机
	 * @param port 服务器端口
	 * @param data 发送数据
	 */
	public static void write(String host, int port, byte[] data) {
		write(host, port, data, false);
	}

	/**
	 * nio模式发送数据
	 * 
	 * @param host 服务器主机
	 * @param port 服务器端口
	 * @param data 发送数据
	 * @param read 是否读取回执数据
	 * @return 接收的数据
	 */
	public static byte[] write(String host, int port, byte[] data, boolean read) {
		return write(new InetSocketAddress(host, port), data, read);
	}

	/**
	 * nio模式发送数据
	 * 
	 * @param addr 服务器调用地址
	 * @param data 发送数据
	 * @param read 是否读取回执数据
	 * @return 接收的数据
	 */
	public static byte[] write(InetSocketAddress addr, byte[] data, boolean read) {
		// 实例化Socket
		try (SocketChannel socket = SocketChannel.open()) {
			// 连接服务器
			socket.connect(addr);
			// 写入数据流
			I.C.write(socket, data, false);
			// 读取数据
			if (read) {
				socket.shutdownOutput();
				return I.C.read(socket, false);
			}
		} catch (IOException e) {
			Logs.error(e);
		}
		return C.A.BYTES_EMPTY;
	}

	/**
	 * aio模式发送数据
	 * 
	 * @param host 服务器主机
	 * @param port 服务器端口
	 * @param data 发送数据
	 */
	public static void asyn(String host, int port, byte[] data) {
		asyn(host, port, data, false);
	}

	/**
	 * aio模式发送数据 接收返回数据
	 * 
	 * @param host 服务器主机
	 * @param port 服务器端口
	 * @param data 发送数据
	 * @param read 是否读取数据
	 * @return 接收的数据
	 */
	public static byte[] asyn(String host, int port, byte[] data, boolean read) {
		return asyn(new InetSocketAddress(host, port), data, read);
	}

	/**
	 * aio模式发送数据 接收返回数据
	 * 
	 * @param addr 服务器调用地址
	 * @param data 发送数据
	 * @param read 是否读取数据
	 * @return 接收的数据
	 */
	public static byte[] asyn(InetSocketAddress addr, byte[] data, boolean read) {
		// 实例化Socket
		try (AsynchronousSocketChannel socket = AsynchronousSocketChannel.open()) {
			// 连接服务器
			socket.connect(addr).get();
			// 写入数据流
			I.A.write(socket, data, false);
			// 读取数据
			if (read) {
				socket.shutdownOutput();
				return I.A.read(socket, false);
			}
		} catch (Exception e) {
			Logs.error(e);
		}
		return C.A.BYTES_EMPTY;
	}

	private TcpClient() {
	}
}
