package com.weicoder.web.socket.empty;

import com.weicoder.web.socket.Session;

/**
 * 空Session实现
 * @author WD
 * @since JDK7
 * @version 1.0 2014-3-17
 */
public final class SessionEmpty implements Session {
	/** 空Session */
	public final static Session	EMPTY	= new SessionEmpty();

	@Override
	public void close() {}

	@Override
	public int getId() {
		return -1;
	}

	@Override
	public void send(short id, Object message) {}

	@Override
	public void send(Object message) {}

	@Override
	public boolean isConnect() {
		return false;
	}

	@Override
	public boolean isClose() {
		return false;
	}

	@Override
	public String getIp() {
		return null;
	}

	@Override
	public int getPort() {
		return 0;
	}

	@Override
	public void send(byte[] message) {}

	private SessionEmpty() {}
}
