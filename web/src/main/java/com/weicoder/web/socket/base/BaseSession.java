package com.weicoder.web.socket.base;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.weicoder.common.binary.Buffer;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.ScheduledUtile;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.log.Logs;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.Sockets;

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
	protected Buffer	buffer;

	/**
	 * 构造
	 * @param name
	 */
	public BaseSession() {
		// 使用写缓存
		if (SocketParams.WRITE > 0) {
			// 声明缓存
			buffer = new Buffer();
			// 定时监控写缓存
			ScheduledUtile.delay(new Runnable() {
				@Override
				public void run() {
					// 有写入数据
					if (buffer.hasRemaining()) {
						// 获得写入缓存字节数组
						byte[] data = buffer.array();
						// 清除缓存
						buffer.clear();
						Logs.info("socket=" + id + ";buffer send len=" + Bytes.toInt(data) + ";id=" + Bytes.toShort(data, 4) + ";data=" + (data.length - 6));
						// 写缓存
						send(data);
					}
				}
			}, SocketParams.WRITE);
		}
	}

	@Override
	public void send(short id, Object message) {
		// 发送数据
		write(Sockets.pack(id, message));
	}

	@Override
	public void send(Object message) {
		// 发送数据
		write(Sockets.pack(message));
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
	 * 写入数据
	 * @param data 字节流数据
	 */
	protected void write(byte[] data) {
		// 是否使用写缓存
		if (SocketParams.WRITE > 0) {
			// 使用缓存
			buffer.write(data);
		} else {
			Logs.info("socket=" + id + ";send len=" + Bytes.toInt(data) + ";id=" + Bytes.toShort(data, 4) + ";data=" + (data.length - 6));
			// 不用缓存 发送数据
			send(data);
		}
	}
}
