package com.weicoder.socket.base;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import com.weicoder.common.binary.Buffer;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.C;
import com.weicoder.common.util.StringUtil;
import com.weicoder.common.log.Logs;
import com.weicoder.common.token.TokenBean;
import com.weicoder.socket.Session;
import com.weicoder.socket.Sockets;

/**
 * 基础Socket Session实现
 * 
 * @author WD
 */
public abstract class BaseSession implements Session {
	// 名称
	protected String name;
	// SessionId
	protected long id;
	// 保存IP
	protected String ip;
	// 保存端口
	protected int port;
	// 心跳存活时间
	protected int heart;
	// 写缓存
	protected Buffer buffer;
	// 用户Token
	protected TokenBean token; 
	// 保存属性 一般为绑定的对象
	protected Object obj;

	/**
	 * 构造
	 * 
	 * @param name 名称
	 */
	public BaseSession(String name) {
		// 获得名称
		this.name = name;
		// 声明缓存
		buffer = new Buffer();
	}

	@Override
	public long getId() {
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

	@Override
	public void send(short id, Object message) {
		send(Sockets.pack(id, message));
	}

	@Override
	public void send(Object message) {
		send(Sockets.pack(message));
	}

	@Override
	public void write(short id, Object message) {
		write(Sockets.pack(id, message));
	}

	@Override
	public void write(Object message) {
		write(Sockets.pack(message));
	}

	@Override
	public Buffer buffer() {
		return buffer;
	}

	@Override
	public int getHeart() {
		return heart;
	}

	@Override
	public void setHeart(int heart) {
		this.heart = heart;
	}

	@Override
	public void send(byte[] data) {
		// 发送数据
		write(data);
		flush();
		Logs.info("name={};socket={};send len={}", name, id, data.length);
	}
	
	@Override
	public TokenBean getToken() {
		return token;
	}

	@Override
	public void setToken(TokenBean token) {
		this.token = token;
	}

	@Override
	public <E> void set(E e) {
		this.obj = e;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E> E get() {
		return (E) obj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseSession other = (BaseSession) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/**
	 * 设置IP与端口
	 * 
	 * @param address Socket地址
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
			this.port = C.toInt(StringUtil.subString(host, StringConstants.COLON));
		}
	}
}
