package com.weicoder.common.util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.SocketChannel;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.io.ChannelUtil;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;

/**
 * TCP 客户端发包处理器
 * @author WD
 */
public final class TcpUtil {
	/**
	 * bio模式发送数据 不接收返回数据
	 * @param data 发送数据
	 */
	public static void send(byte[] data) {
		send(CommonParams.TCP_HOST, CommonParams.TCP_PORT, data);
	}

	/**
	 * bio模式发送数据 不接收返回数据
	 * @param data 发送数据
	 * @param len 接收返回数据长度
	 * @return 发送的数据
	 */
	public static byte[] send(byte[] data, int len) {
		return send(CommonParams.TCP_HOST, CommonParams.TCP_PORT, data, len);
	}

	/**
	 * bio模式发送数据 不接收返回数据
	 * @param data 发送数据
	 */
	public static void write(byte[] data) {
		write(CommonParams.TCP_HOST, CommonParams.TCP_PORT, data);
	}

	/**
	 * bio模式发送数据 不接收返回数据
	 * @param data 发送数据
	 * @param len 接收返回数据长度
	 * @return 发送的数据
	 */
	public static byte[] write(byte[] data, int len) {
		return write(CommonParams.TCP_HOST, CommonParams.TCP_PORT, data, len);
	}

	/**
	 * bio模式发送数据 不接收返回数据
	 * @param data 发送数据
	 */
	public static void asyn(byte[] data) {
		asyn(CommonParams.TCP_HOST, CommonParams.TCP_PORT, data);
	}

	/**
	 * bio模式发送数据 不接收返回数据
	 * @param data 发送数据
	 * @param len 接收返回数据长度
	 * @return 发送的数据
	 */
	public static byte[] asyn(byte[] data, int len) {
		return asyn(CommonParams.TCP_HOST, CommonParams.TCP_PORT, data, len);
	}

	/**
	 * bio模式发送数据 不接收返回数据
	 * @param host 服务器主机
	 * @param port 服务器端口
	 * @param data 发送数据
	 */
	public static void send(String host, int port, byte[] data) {
		// 实例化Socket
		try (Socket socket = new Socket()) {
			// 连接服务器
			socket.connect(new InetSocketAddress(host, port));
			// 写入数据流
			IOUtil.write(socket.getOutputStream(), data, false);
		} catch (IOException e) {
			Logs.error(e);
		}
	}

	/**
	 * bio模式发送数据 接收返回数据
	 * @param host 服务器主机
	 * @param port 服务器端口
	 * @param data 发送数据
	 * @param len 接收返回数据长度
	 * @return 接收的数据
	 */
	public static byte[] send(String host, int port, byte[] data, int len) {
		// 实例化Socket
		try (Socket socket = new Socket()) {
			// 连接服务器
			socket.connect(new InetSocketAddress(host, port));
			// 写入数据流
			IOUtil.write(socket.getOutputStream(), data, false);
			// 读取数据
			return IOUtil.read(socket.getInputStream(), false);
		} catch (IOException e) {
			Logs.error(e);
			return ArrayConstants.BYTES_EMPTY;
		}
	}

	/**
	 * nio模式发送数据
	 * @param host 服务器主机
	 * @param port 服务器端口
	 * @param data 发送数据
	 */
	public static void write(String host, int port, byte[] data) {
		// 实例化Socket
		try (SocketChannel socket = SocketChannel.open()) {
			// 连接服务器
			socket.connect(new InetSocketAddress(host, port));
			// 写入数据流
			ChannelUtil.write(socket, data, false);
		} catch (IOException e) {
			Logs.error(e);
		}
	}

	/**
	 * nio模式发送数据 接收返回数据
	 * @param host 服务器主机
	 * @param port 服务器端口
	 * @param data 发送数据
	 * @param len 接收返回数据长度
	 * @return 接收的数据
	 */
	public static byte[] write(String host, int port, byte[] data, int len) {
		// 实例化Socket
		try (SocketChannel socket = SocketChannel.open()) {
			// 连接服务器
			socket.connect(new InetSocketAddress(host, port));
			// 写入数据流
			ChannelUtil.write(socket, data, false);
			// 读取数据
			return ChannelUtil.read(socket, false);
		} catch (IOException e) {
			Logs.error(e);
			return ArrayConstants.BYTES_EMPTY;
		}
	}

	/**
	 * aio模式发送数据
	 * @param host 服务器主机
	 * @param port 服务器端口
	 * @param data 发送数据
	 */
	public static void asyn(String host, int port, byte[] data) {
		// 实例化Socket
		try (AsynchronousSocketChannel socket = AsynchronousSocketChannel.open()) {
			// 连接服务器
			socket.connect(new InetSocketAddress(host, port)).get();
			// 写入数据流
			socket.write(ByteBuffer.wrap(data)).get();
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * aio模式发送数据 接收返回数据
	 * @param host 服务器主机
	 * @param port 服务器端口
	 * @param data 发送数据
	 * @param len 接收返回数据长度
	 * @return 接收的数据
	 */
	public static byte[] asyn(String host, int port, byte[] data, int len) {
		// 实例化Socket
		try (AsynchronousSocketChannel socket = AsynchronousSocketChannel.open()) {
			// 连接服务器
			socket.connect(new InetSocketAddress(host, port)).get();
			// 写入数据流
			socket.write(ByteBuffer.wrap(data)).get();
			// 读取数据
			ByteBuffer buf = ByteBuffer.allocate(len);
			socket.read(buf).get();
			return buf.array();
		} catch (Exception e) {
			Logs.error(e);
			return ArrayConstants.BYTES_EMPTY;
		}
	}

	private TcpUtil() {}
}
