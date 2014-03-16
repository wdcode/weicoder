package com.weicoder.web.socket.impl.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

import com.weicoder.web.socket.Buffer;

/**
 * Netty 缓冲区实现
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-22
 */
public final class NettyBuffer implements Buffer {
	// Netty ByteBuf
	private ByteBuf	buf;

	/**
	 * 构造
	 */
	public NettyBuffer() {
		buf = PooledByteBufAllocator.DEFAULT.buffer();
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
	public short getShort() {
		return buf.readShort();
	}

	@Override
	public void get(byte[] data) {
		buf.readBytes(data);
	}
}
