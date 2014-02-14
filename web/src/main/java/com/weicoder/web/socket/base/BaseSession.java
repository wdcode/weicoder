package com.weicoder.web.socket.base;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.socket.interfaces.Session;
import com.weicoder.web.socket.simple.Message;

/**
 * 基础Socket Session实现
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-22
 */
public abstract class BaseSession implements Session {
	// SessionId
	protected int		id;
	// 保存IP
	protected String	ip;
	// 保存端口
	protected int		port;

	@Override
	public void send(int id, Object message) {
		// 声明字节数组
		byte[] data = null;
		// 判断类型
		if (message == null) {
			// 空
			data = ArrayConstants.BYTES_EMPTY;
		} else if (message instanceof String) {
			// 字符串
			data = StringUtil.toBytes(Conversion.toString(message));
		} else if (message instanceof Message) {
			// 消息体
			data = ((Message) message).toBytes();
		} else if (message instanceof ByteBuffer) {
			// ByteBuffer
			data = ((ByteBuffer) message).array();
		} else {
			// 不知道的类型 以字节数组发送
			data = Bytes.toBytes(message);
		}
		// 发送数据
		send(Bytes.toBytes(data.length + 4, id, data));
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
	 * 发送数据
	 * @param data 字节流数据
	 */
	protected abstract void send(byte[] data);
}
