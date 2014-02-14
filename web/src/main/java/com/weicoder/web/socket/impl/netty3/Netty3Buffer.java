package com.weicoder.web.socket.impl.netty3;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import com.weicoder.web.socket.interfaces.Buffer;

/**
 * Netty 缓冲区实现
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-22
 */
public final class Netty3Buffer implements Buffer {
	// Netty ByteBuf
	private ChannelBuffer	buf;

	/**
	 * 构造
	 */
	public Netty3Buffer() {
		buf = ChannelBuffers.dynamicBuffer();
	}

	@Override
	public void put(byte[] src) {
		buf.writeBytes(src);
	}

	@Override
	public void flip() {}

	@Override
	public int remaining() {
		return buf.readableBytes();
	}

	@Override
	public void compact() {
		buf.discardReadBytes();
	}

	@Override
	public void rewind() {
		buf.resetReaderIndex();
	}

	@Override
	public void clear() {
		buf.clear();
	}

	@Override
	public int getInt() {
		return buf.readInt();
	}

	@Override
	public void get(byte[] data) {
		buf.readBytes(data);
	}
}
