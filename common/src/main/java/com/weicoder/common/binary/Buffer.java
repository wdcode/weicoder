package com.weicoder.common.binary;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * 类说明：数据包类 ,字节缓存类，字节操作高位在前，低位在后
 * @author WD
 * @since JDK7
 * @version 1.0 2014-2-13
 */
public final class Buffer implements ByteArray {
	/** 默认的初始容量大小 */
	private static final int	CAPACITY	= 32;
	// 字节数组
	private byte[]				bytes;
	// 写数据的偏移量，每写一次增加
	private int					top;
	// 读数据的偏移量,每读一次增加
	private int					offset;

	/**
	 * 按默认的大小构造一个字节缓存对象
	 */
	public Buffer() {
		this(CAPACITY);
	}

	/**
	 * 按指定的大小构造一个字节缓存对象
	 */
	public Buffer(int capacity) {
		this(new byte[capacity], 0, 0);
	}

	/**
	 * 按指定的字节数组构造一个字节缓存对象
	 */
	public Buffer(byte[] data) {
		this(data, 0, data.length);
	}

	/**
	 * 按指定的字节数组构造一个字节缓存对象
	 */
	public Buffer(byte[] data, int index, int length) {
		bytes = data;
		top = index + length;
		offset = index;
	}

	/**
	 * 设置字节缓存的容积，只能扩大容积
	 */
	public void capacity(int len) {
		int c = bytes.length;
		if (len <= c)
			return;
		for (; c < len; c = (c << 1) + 1)
			;
		byte[] temp = new byte[c];
		System.arraycopy(bytes, 0, temp, 0, top);
		bytes = temp;
	}

	/**
	 * 得到写字节的偏移量
	 */
	public int top() {
		return top;
	}

	/**
	 * 设置写字节的偏移量
	 */
	public void top(int top) {
		if (top < offset)
			return;
		if (top > bytes.length)
			capacity(top);
		this.top = top;
	}

	/**
	 * 得到读数据的偏移量
	 */
	public int offset() {
		return offset;
	}

	/**
	 * 设置读数据的偏移量
	 */
	public void offset(int offset) {
		if (offset < 0 || offset > top)
			return;
		this.offset = offset;
	}

	/**
	 * 剩余多少可读字节==写偏移量-读偏移量得差值
	 */
	public int remaining() {
		return top - offset;
	}

	/**
	 * 是否还有任何一个可读字节
	 */
	public boolean hasRemaining() {
		return remaining() > 0;
	}

	/**
	 * 得到字节数组的长度
	 */
	public int length() {
		return bytes.length;
	}

	/**
	 * 得到字节缓存的字节数组
	 */
	public byte[] array() {
		return Bytes.copy(bytes, 0, top);
	}

	/**
	 * 得到指定偏移位置的字节
	 */
	public byte read(int pos) {
		return bytes[pos];
	}

	/**
	 * 设置指定偏移位置的字节
	 */
	public void write(byte b, int pos) {
		bytes[pos] = (byte) b;
	}

	/**
	 * 按当前偏移位置读入指定的字节数组
	 * @param data 指定的字节数组
	 * @param pos 指定的字节数组的起始位置
	 * @param len 读入的长度
	 */
	public byte[] read(byte[] data) {
		return read(data, 0, data.length);
	}

	/**
	 * 按当前偏移位置读入指定的字节数组
	 * @param data 指定的字节数组
	 * @param pos 指定的字节数组的起始位置
	 * @param len 读入的长度
	 */
	public byte[] read(byte[] data, int pos, int len) {
		System.arraycopy(bytes, offset, data, pos, len);
		offset += len;
		return data;
	}

	/**
	 * 读出一个布尔值
	 */
	public boolean readBoolean() {
		return (bytes[offset++] != 0);
	}

	/**
	 * 读出一个字节
	 */
	public byte readByte() {
		return bytes[offset++];
	}

	/**
	 * 读出一个字符
	 */
	public char readChar() {
		return (char) readShort();
	}

	/**
	 * 读出一个短整型数值
	 */
	public short readShort() {
		int pos = offset;
		offset += 2;
		return Bytes.toShort(bytes, pos);
	}

	/**
	 * 读出一个整型数值
	 */
	public int readInt() {
		int pos = offset;
		offset += 4;
		return Bytes.toInt(bytes, pos);
	}

	/**
	 * 读出一个浮点数值
	 */
	public float readFloat() {
		return Float.intBitsToFloat(readInt());
	}

	/**
	 * 读出一个长整型数值
	 */
	public long readLong() {
		int pos = offset;
		offset += 8;
		return Bytes.toLong(bytes, pos);
	}

	/**
	 * 读出一个双浮点数值
	 */
	public double readDouble() {
		return Double.longBitsToDouble(readLong());
	}

	/**
	 * 读出一个指定长度的字符串
	 */
	public String readString(int len) {
		if (len == 0)
			return StringConstants.EMPTY;
		return new String(read(new byte[len], 0, len));
	}

	/**
	 * 读出一个字符串，长度不超过65534
	 */
	public String readString() {
		return readString(readShort());
	}

	/**
	 * 写入指定字节数组
	 * @param data 指定的字节数组
	 * @param pos 指定的字节数组的起始位置
	 * @param len 写入的长度
	 */
	public void write(byte[] data) {
		write(data, 0, data.length);
	}

	/**
	 * 写入指定字节数组
	 * @param data 指定的字节数组
	 * @param pos 指定的字节数组的起始位置
	 * @param len 写入的长度
	 */
	public void write(byte[] data, int pos, int len) {
		if (bytes.length < top + len)
			capacity(top + len);
		System.arraycopy(data, pos, bytes, top, len);
		top += len;
	}

	/**
	 * 写入一个布尔值
	 */
	public void writeBoolean(boolean b) {
		if (bytes.length < top + 1)
			capacity(top + CAPACITY);
		bytes[top++] = (byte) (b ? 1 : 0);
	}

	/**
	 * 写入一个字节
	 */
	public void writeByte(byte b) {
		if (bytes.length < top + 1)
			capacity(top + CAPACITY);
		bytes[top++] = b;
	}

	/**
	 * 写入一个字符
	 */
	public void writeChar(char c) {
		write(Bytes.toBytes(c), 0, 2);
	}

	/**
	 * 写入一个短整型数值
	 */
	public void writeShort(int i) {
		writeShort((short) i);
	}

	/**
	 * 写入一个短整型数值
	 */
	public void writeShort(short s) {
		writeShort(s, 0);
	}

	/**
	 * 在指定位置写入一个短整型数值，length不变
	 */
	public void writeShort(short s, int pos) {
		write(Bytes.toBytes(s), pos, pos + 2);
	}

	/**
	 * 写入一个整型数值
	 */
	public void writeInt(int i) {
		writeInt(i, 0);
	}

	/**
	 * 在指定位置写入一个整型数值，length不变
	 */
	public void writeInt(int i, int pos) {
		write(Bytes.toBytes(i), pos, pos + 4);
	}

	/**
	 * 写入一个浮点数值
	 */
	public void writeFloat(float f) {
		writeInt(Float.floatToIntBits(f));
	}

	/**
	 * 写入一个长整型数值
	 */
	public void writeLong(long l) {
		writeLong(l, 0);
	}

	/**
	 * 写入一个长整型数值
	 */
	public void writeLong(long l, int pos) {
		write(Bytes.toBytes(l), pos, pos + 8);
	}

	/**
	 * 写入一个双浮点数值
	 */
	public void writeDouble(double d) {
		writeLong(Double.doubleToLongBits(d));
	}

	/**
	 * 写入一个字符串，可以为null
	 */
	public void writeString(String s) {
		if (EmptyUtil.isEmpty(s)) {
			writeShort(0);
		} else {
			byte[] temp = StringUtil.toBytes(s);
			writeShort(temp.length);
			write(temp, 0, temp.length);
		}
	}

	/**
	 * 在指定位置写入一个字节，length不变
	 */
	public void writeByte(byte b, int pos) {
		if (bytes.length < pos + 1)
			capacity(pos + CAPACITY);
		bytes[pos] = b;
	}

	/**
	 * 压缩缓冲区 抛弃以读数据 并把容量截取到写坐标
	 */
	public void compact() {
		bytes = Bytes.copy(bytes, offset, top);
		top -= offset;
		offset = 0;
	}

	/**
	 * 重置读坐标为0
	 */
	public void rewind() {
		this.offset = 0;
	}

	/**
	 * 清除字节缓存对象
	 */
	public void clear() {
		bytes = new byte[length()];
		top = 0;
		offset = 0;
	}

	@Override
	public int hashCode() {
		int hash = 17;
		for (int i = top - 1; i >= 0; i--)
			hash = 65537 * hash + bytes[i];
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Buffer))
			return false;
		Buffer bb = (Buffer) obj;
		if (bb.top != top)
			return false;
		if (bb.offset != offset)
			return false;
		for (int i = top - 1; i >= 0; i--) {
			if (bb.bytes[i] != bytes[i])
				return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "[" + top + "," + offset + "," + bytes.length + "] ";
	}

	@Override
	public ByteArray array(byte[] b) {
		clear();
		write(b);
		return this;
	}
}