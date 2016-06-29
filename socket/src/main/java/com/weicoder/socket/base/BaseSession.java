package com.weicoder.socket.base;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.weicoder.common.binary.Buffer;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.StringUtil;
import com.weicoder.common.util.ThreadUtil;
import com.weicoder.common.zip.ZipEngine;
import com.weicoder.common.log.Logs;
import com.weicoder.common.schedule.ScheduledUtile;
import com.weicoder.socket.params.SocketParams;
import com.weicoder.socket.Session;
import com.weicoder.socket.message.Null;

/**
 * 基础Socket Session实现
 * @author WD
 */
public abstract class BaseSession implements Session {
	// 名称
	protected String	name;
	// SessionId
	protected int		id;
	// 保存IP
	protected String	ip;
	// 保存端口
	protected int		port;
	// 写缓存
	protected Buffer	buffer;
	// 是否使用压缩
	protected boolean	zip;

	/**
	 * 构造
	 * @param name
	 */
	public BaseSession(final String name) {
		// 获得名称
		this.name = name;
		// 获得是否压缩
		this.zip = SocketParams.isZip(name);
		// 声明缓存
		buffer = new Buffer();
		// 使用写缓存
		if (SocketParams.WRITE > 0) {
			// 定时监控写缓存
			ScheduledUtile.rate(() -> flush(), SocketParams.WRITE);
		}
	}

	@Override
	public int id() {
		return id;
	}

	@Override
	public String ip() {
		return ip;
	}

	@Override
	public int port() {
		return port;
	}

	@Override
	public byte[] send(short id, Object message) {
		return send(pack(id, message));
	}

	@Override
	public byte[] send(Object message) {
		return send(pack(message));
	}

	@Override
	public byte[] buffer(short id, Object message) {
		return buffer.write(pack(id, message));
	}

	@Override
	public byte[] buffer(Object message) {
		return buffer.write(pack(message));
	}

	@Override
	public void flush() {
		// 有写入数据
		if (buffer.hasRemaining()) {
			// 获得写入缓存字节数组
			byte[] data = buffer.array();
			// 清除缓存
			buffer.clear();
			// 写缓存
			write(data);
			Logs.info("name=" + name + ";socket=" + id + ";buffer send len=" + data.length);
		}
	}

	@Override
	public byte[] send(byte[] data) {
		// 是否使用写缓存
		if (SocketParams.WRITE > 0) {
			// 使用缓存
			buffer.write(data);
		} else {
			// 不用缓存 发送数据
			write(data);
			Logs.info("name=" + name + ";socket=" + id + ";send len=" + data.length + ";id=" + Bytes.toShort(data, 4));
		}
		// 返回原始数据
		return data;
	}

	@Override
	public void close() {
		// 使用写缓存
		if (SocketParams.WRITE > 0) {
			while (buffer.hasRemaining()) {
				ThreadUtil.sleep(SocketParams.WRITE + 5);
			}
		}
		// 调用关闭
		close0();
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
	 * 包装数据
	 * @param id 指令
	 * @param message 消息
	 * @return 字节数组
	 */
	protected byte[] pack(short id, Object message) {
		// 声明字节数组
		byte[] data = toBytes(message);
		// 返回数据
		return Bytes.toBytes(Conversion.toShort(data.length + 2), id, data);
	}

	/**
	 * 包装数据
	 * @param message 消息
	 * @return 字节数组
	 */
	protected byte[] pack(Object message) {
		// 声明字节数组
		byte[] data = toBytes(message);
		// 返回数据
		return Bytes.toBytes(Conversion.toShort(data.length), data);
	}

	/**
	 * 转换message为字节数组
	 * @param message
	 * @return
	 */
	protected byte[] toBytes(Object message) {
		// 日志
		Logs.info("name=" + name + ";socket=" + id + ";message=" + message);
		// 声明字节数组
		byte[] data = null;
		// 判断类型
		if (message == null || message instanceof Null) {
			// 空
			data = ArrayConstants.BYTES_EMPTY;
		} else if (message instanceof String) {
			// 字符串
			data = StringUtil.toBytes(Conversion.toString(message));
		} else {
			// 不知道的类型 以字节数组发送
			data = Bytes.toBytes(message);
		}
		// 使用压缩并且长度大于一个字节长度返回压缩 不使用直接返回字节数组
		return zip && data.length > Byte.MAX_VALUE ? ZipEngine.compress(data) : data;
	}

	/**
	 * 关闭
	 */
	protected abstract void close0();
}
