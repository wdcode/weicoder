package com.weicoder.web.socket.impl.mina;

import org.apache.mina.core.buffer.IoBuffer;
import com.weicoder.common.params.CommonParams;
import com.weicoder.web.socket.interfaces.Buffer;

/**
 * mina缓冲区实现
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-22
 */
public final class MinaBuffer implements Buffer {
	// mina IoBuffer
	private IoBuffer	buffer;

	public MinaBuffer() {
		buffer = IoBuffer.allocate(CommonParams.IO_BUFFERSIZE);
		buffer.setAutoExpand(true);
		buffer.setAutoShrink(true);
	}

	@Override
	public void put(byte[] src) {
		buffer.put(src);
	}

	@Override
	public void flip() {
		buffer.flip();
	}

	@Override
	public int remaining() {
		return buffer.remaining();
	}

	@Override
	public void compact() {
		buffer.compact();
	}

	@Override
	public void rewind() {
		buffer.rewind();
	}

	@Override
	public void clear() {
		buffer.clear();
	}

	@Override
	public int getInt() {
		return buffer.getInt();
	}

	@Override
	public void get(byte[] data) {
		buffer.get(data);
	}
}
