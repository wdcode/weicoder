package com.weicoder.web.socket.base;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.ScheduledUtile;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.message.Message;
import com.weicoder.web.socket.message.Null;
import com.weicoder.web.socket.simple.DataBuffer;

/**
 * 基础Socket Session实现
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-22
 */
public abstract class BaseSession implements Session {
	// SessionId
	protected int			id;
	// 保存IP
	protected String		ip;
	// 保存端口
	protected int			port;
	// 写缓存
	protected DataBuffer	buffer;

	/**
	 * 构造
	 * @param name
	 */
	public BaseSession() {
		// 使用写缓存
		if (SocketParams.WRITE > 0) {
			// 声明缓存
			buffer = new DataBuffer();
			// 定时监控写缓存
			ScheduledUtile.delay(new Runnable() {
				@Override
				public void run() {
					// 有写入数据
					if (buffer.hasRemaining()) {
						// 获得写入缓存字节数组
						byte[] b = buffer.array();
						// 清除缓存
						buffer.clear();
						// 写缓存
						send(b);
					}
				}
			}, SocketParams.WRITE);
		}
	}

	@Override
	public void send(short id, Object message) {
		// 声明字节数组
		byte[] data = toByte(message);
		// 发送数据
		write(Bytes.toBytes(data.length + 2, id, data));
	}

	@Override
	public void send(Object message) {
		// 声明字节数组
		byte[] data = toByte(message);
		// 发送数据
		write(Bytes.toBytes(data.length, data));
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getIp() {
		return ip;
	}

	@Override
	public int getPort() {
		return port;
	}

	/**
	 * 设置IP与端口
	 */
	protected void address(SocketAddress address) {
		if (address instanceof InetSocketAddress) {
			// InetSocketAddress
			InetSocketAddress inet = (InetSocketAddress) address;
			this.ip = inet.getHostName();
			this.port = inet.getPort();
		} else {
			// 普通SocketAddress
			String host = address.toString();
			this.ip = StringUtil.subString(host, StringConstants.BACKSLASH, StringConstants.COLON);
			this.port = Conversion.toInt(StringUtil.subString(host, StringConstants.COLON));
		}
	}

	/**
	 * 转换message为字节数组
	 * @param message
	 * @return
	 */
	protected byte[] toByte(Object message) {
		// 声明字节数组
		byte[] data = null;
		// 判断类型
		if (message == null) {
			// 空
			data = ArrayConstants.BYTES_EMPTY;
		} else if (message instanceof Null) {
			// 空
			data = ArrayConstants.BYTES_EMPTY;
		} else if (message instanceof String) {
			// 字符串
			data = StringUtil.toBytes(Conversion.toString(message));
		} else if (message instanceof Message) {
			// 消息体
			data = ((Message) message).array();
		} else {
			// 不知道的类型 以字节数组发送
			data = Bytes.toBytes(message);
		}
		// 返回字节数组
		return data;
	}

	/**
	 * 写入数据
	 * @param data 字节流数据
	 */
	protected void write(byte[] data) {
		// 是否使用写缓存
		if (SocketParams.WRITE > 0) {
			// 使用缓存
			buffer.write(data);
		} else {
			// 不用缓存 发送数据
			send(data);
		}
	}

	/**
	 * 发送数据
	 * @param data 字节流数据
	 */
	protected abstract void send(byte[] data);
}
