package com.weicoder.core.engine;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.io.ChannelUtil;
import com.weicoder.core.log.Logs;
import com.weicoder.core.params.SocketParams;

/**
 * UDP 客户端发包处理器
 * @author WD
 * @version 1.0
 */
public final class UdpEngine {
	/**
	 * bio模式发送数据 不接收返回数据
	 * @param data 发送数据
	 * @return
	 */
	public static void send(byte[] data) {
		send(SocketParams.HOST, SocketParams.PORT, data);
	}

	/**
	 * bio模式发送数据 不接收返回数据
	 * @param data 发送数据
	 * @param len 接收返回数据长度
	 * @return
	 */
	public static byte[] send(byte[] data, int len) {
		return send(SocketParams.HOST, SocketParams.PORT, data, len);
	}

	/**
	 * bio模式发送数据 不接收返回数据
	 * @param data 发送数据
	 * @return
	 */
	public static void write(byte[] data) {
		write(SocketParams.HOST, SocketParams.PORT, data);
	}

	/**
	 * bio模式发送数据 不接收返回数据
	 * @param data 发送数据
	 * @param len 接收返回数据长度
	 * @return
	 */
	public static byte[] write(byte[] data, int len) {
		return write(SocketParams.HOST, SocketParams.PORT, data, len);
	}

	/**
	 * bio模式发送数据 不接收返回数据
	 * @param host 服务器主机
	 * @param port 服务器端口
	 * @param data 发送数据
	 */
	public static void send(String host, int port, byte[] data) {
		// 实例化Socket
		try (DatagramSocket socket = new DatagramSocket()) {
			// 连接服务器
			socket.connect(new InetSocketAddress(host, port));
			// 写入数据流
			socket.send(new DatagramPacket(data, data.length));
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
		try (DatagramSocket socket = new DatagramSocket()) {
			// 连接服务器
			socket.connect(new InetSocketAddress(host, port));
			// 写入数据流
			socket.send(new DatagramPacket(data, data.length));
			// 读取数据
			DatagramPacket p = new DatagramPacket(new byte[4], 4);
			socket.receive(p);
			return p.getData();
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
		try (DatagramChannel socket = DatagramChannel.open()) {
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
		try (DatagramChannel socket = DatagramChannel.open()) {
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

	private UdpEngine() {}
}
