package com.weicoder.web.socket.simple;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.util.EmptyUtil;

/**
 * 类说明：数据包类 ,字节缓存类，字节操作高位在前，低位在后
 * @author WD
 * @since JDK7
 * @version 1.0 2014-2-13
 */
public final class ByteBuf {
	/** 默认的初始容量大小 */
	public static final int	CAPACITY		= 32;
	/** 默认的动态数据或文字的最大长度，400k */
	public static final int	MAX_DATA_LENGTH	= 400 * 1024;
	// 字节数组
	private byte[]			bytes;
	// 写数据的偏移量，每写一次增加
	private int				top;
	// 读数据的偏移量,每读一次增加 */
	private int				offset;

	/**
	 * 按默认的大小构造一个字节缓存对象
	 */
	public ByteBuf() {
		this(CAPACITY);
	}

	/**
	 * 按指定的大小构造一个字节缓存对象
	 */
	public ByteBuf(int capacity) {
		this(new byte[capacity], 0, 0);
	}

	/**
	 * 按指定的字节数组构造一个字节缓存对象
	 */
	public ByteBuf(byte[] data) {
		this(data, 0, data.length);
	}

	/**
	 * 按指定的字节数组构造一个字节缓存对象
	 */
	public ByteBuf(byte[] data, int index, int length) {
		bytes = data;
		top = index + length;
		offset = index;
	}

	/**
	 * 设置字节缓存的容积，只能扩大容积
	 */
	public void setCapacity(int len) {
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
			throw new IllegalArgumentException(this + " setTop, invalid top:" + top);
		if (top > bytes.length)
			setCapacity(top);
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
			throw new IllegalArgumentException(this + " setOffset, invalid offset:" + offset);
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
		return bytes;
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
	public void write(int b, int pos) {
		bytes[pos] = (byte) b;
	}

	/**
	 * 按当前偏移位置读入指定的字节数组
	 * @param data 指定的字节数组
	 * @param pos 指定的字节数组的起始位置
	 * @param len 读入的长度
	 */
	public void read(byte[] data, int pos, int len) {
		System.arraycopy(bytes, offset, data, pos, len);
		offset += len;
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
	 * 读出一个无符号字节
	 */
	public int readUnsignedByte() {
		return bytes[offset++] & 0xff;
	}

	/**
	 * 读出一个字符
	 */
	public char readChar() {
		return (char) readUnsignedShort();
	}

	/**
	 * 读出一个短整型数值
	 */
	public short readShort() {
		return (short) readUnsignedShort();
	}

	/**
	 * 读出一个无符号的短整型数值
	 */
	public int readUnsignedShort() {
		int pos = offset;
		offset += 2;
		return (bytes[pos + 1] & 0xff) + ((bytes[pos] & 0xff) << 8);
	}

	/**
	 * 读出一个整型数值
	 */
	public int readInt() {
		int pos = offset;
		offset += 4;
		return (bytes[pos + 3] & 0xff) + ((bytes[pos + 2] & 0xff) << 8) + ((bytes[pos + 1] & 0xff) << 16) + ((bytes[pos] & 0xff) << 24);
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
		return (bytes[pos + 7] & 0xffL) + ((bytes[pos + 6] & 0xffL) << 8) + ((bytes[pos + 5] & 0xffL) << 16) + ((bytes[pos + 4] & 0xffL) << 24) + ((bytes[pos + 3] & 0xffL) << 32) + ((bytes[pos + 2] & 0xffL) << 40) + ((bytes[pos + 1] & 0xffL) << 48) + ((bytes[pos] & 0xffL) << 56);
	}

	/**
	 * 读出一个双浮点数值
	 */
	public double readDouble() {
		return Double.longBitsToDouble(readLong());
	}

	/**
	 * 读出动态长度， 数据大小采用动态长度，整数类型下，最大为512M 1xxx,xxxx表示（0~0x80） 0~128B 01xx,xxxx,xxxx,xxxx表示（0~0x4000）
	 * 0~16K 001x,xxxx,xxxx,xxxx,xxxx,xxxx,xxxx,xxxx表示（0~0x20000000） 0~512M
	 */
	public int readLength() {
		int n = bytes[offset] & 0xff;
		if (n >= 0x80) {
			offset++;
			return n - 0x80;
		} else if (n >= 0x40)
			return readUnsignedShort() - 0x4000;
		else if (n >= 0x20)
			return readInt() - 0x20000000;
		else
			throw new IllegalArgumentException(this + " readLength, invalid number:" + n);
	}

	/**
	 * 读出一个指定长度的字节数组，可以为null
	 */
	public byte[] readData() {
		int len = readLength() - 1;
		if (len < 0)
			return null;
		if (len > MAX_DATA_LENGTH)
			throw new IllegalArgumentException(this + " readData, data overflow:" + len);
		byte[] data = new byte[len];
		read(data, 0, len);
		return data;
	}

	/**
	 * 读出一个短字节数组，长度不超过254
	 */
	public byte[] readShortData() {
		int len = readUnsignedByte();
		if (len == 255)
			return null;
		byte[] data = new byte[len];
		if (len != 0)
			read(data, 0, len);
		return data;
	}

	/**
	 * 读出一个指定长度的字符串
	 */
	public String readString(int len) {
		byte[] data = new byte[len];
		if (len == 0)
			return "";
		read(data, 0, len);
		return new String(data);
	}

	/**
	 * 读出一个短字符串，长度不超过254
	 */
	public String readShortString() {
		int len = readShort();
		if (len == 65535)
			return null;
		return readString(len);
	}

	/**
	 * 读出一个字符串，长度不超过65534
	 */
	public String readString() {
		return readString(readInt());
	}

	/**
	 * 读出一个指定长度和编码类型的字符串
	 */
	public String readUTF(String charsetName) {
		int len = readLength() - 1;
		if (len < 0)
			return null;
		if (len > MAX_DATA_LENGTH)
			throw new IllegalArgumentException(this + " readUTF, data overflow:" + len);
		byte[] data = new byte[len];
		read(data, 0, len);
		if (charsetName == null)
			return new String(data);
		try {
			return new String(data, charsetName);
		} catch (Exception e) {
			throw new IllegalArgumentException(this + " readUTF, invalid charsetName:" + charsetName);
		}
	}

	/**
	 * 读出一个指定长度的utf字符串
	 */
	public String readUTF() {
		int len = readLength() - 1;
		if (len < 0)
			return null;
		if (len == 0)
			return StringConstants.EMPTY;
		if (len > MAX_DATA_LENGTH)
			throw new IllegalArgumentException(this + " readUTF, data overflow:" + len);
		offset += len;
		return readUTF(offset, len);
	}

	/**
	 * 将指定的UTF8格式的字节数据转换为字符串， 返回0表示成功，否则表示失败位置
	 */
	public String readUTF(int pos, int len) {
		StringBuffer sb = new StringBuffer(len);
		int i, c, cc, ccc;
		int end = pos + len;
		while (pos < end) {
			c = bytes[pos] & 0xff;
			i = c >> 4;
			if (i < 8) {
				// 0xxx xxxx
				pos++;
				sb.append((char) c);
			} else if (i == 12 || i == 13) {
				// 110x xxxx 10xx xxxx
				pos += 2;
				if (pos > end)
					return StringConstants.EMPTY;
				cc = bytes[pos - 1];
				if ((cc & 0xC0) != 0x80)
					return StringConstants.EMPTY;
				sb.append((char) (((c & 0x1f) << 6) | (cc & 0x3f)));
			} else if (i == 14) {
				// 1110 xxxx 10xx xxxx 10xx
				// xxxx
				pos += 3;
				if (pos > end)
					return StringConstants.EMPTY;
				cc = bytes[pos - 2];
				ccc = bytes[pos - 1];
				if (((cc & 0xC0) != 0x80) || ((ccc & 0xC0) != 0x80))
					return StringConstants.EMPTY;
				sb.append((char) (((c & 0x0f) << 12) | ((cc & 0x3f) << 6) | (ccc & 0x3f)));
			} else
				// 10xx xxxx 1111 xxxx
				return StringConstants.EMPTY;
		}
		return StringConstants.EMPTY;
	}

	/**
	 * 写入指定字节数组
	 * @param data 指定的字节数组
	 * @param pos 指定的字节数组的起始位置
	 * @param len 写入的长度
	 */
	public void write(byte[] data, int pos, int len) {
		if (bytes.length < top + len)
			setCapacity(top + len);
		System.arraycopy(data, pos, bytes, top, len);
		top += len;
	}

	/**
	 * 写入一个布尔值
	 */
	public void writeBoolean(boolean b) {
		if (bytes.length < top + 1)
			setCapacity(top + CAPACITY);
		bytes[top++] = (byte) (b ? 1 : 0);
	}

	/**
	 * 写入一个字节
	 */
	public void writeByte(int b) {
		if (bytes.length < top + 1)
			setCapacity(top + CAPACITY);
		bytes[top++] = (byte) b;
	}

	/**
	 * 写入一个字符
	 */
	public void writeChar(int c) {
		writeShort(c);
	}

	/**
	 * 写入一个短整型数值
	 */
	public void writeShort(int s) {
		int pos = top;
		if (bytes.length < pos + 2)
			setCapacity(pos + CAPACITY);
		bytes[pos] = (byte) (s >>> 8);
		bytes[pos + 1] = (byte) s;
		top += 2;
	}

	/**
	 * 在指定位置写入一个短整型数值，length不变
	 */
	public void writeShort(int s, int pos) {
		if (bytes.length < pos + 2)
			setCapacity(pos + CAPACITY);
		bytes[pos] = (byte) (s >>> 8);
		bytes[pos + 1] = (byte) s;
	}

	/**
	 * 写入一个整型数值
	 */
	public void writeInt(int i) {
		int pos = top;
		if (bytes.length < pos + 4)
			setCapacity(pos + CAPACITY);
		bytes[pos] = (byte) (i >>> 24);
		bytes[pos + 1] = (byte) (i >>> 16);
		bytes[pos + 2] = (byte) (i >>> 8);
		bytes[pos + 3] = (byte) i;
		top += 4;
	}

	/**
	 * 在指定位置写入一个整型数值，length不变
	 */
	public void writeInt(int i, int pos) {
		if (bytes.length < pos + 4)
			setCapacity(pos + CAPACITY);
		bytes[pos] = (byte) (i >>> 24);
		bytes[pos + 1] = (byte) (i >>> 16);
		bytes[pos + 2] = (byte) (i >>> 8);
		bytes[pos + 3] = (byte) i;
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
		int pos = top;
		if (bytes.length < pos + 8)
			setCapacity(pos + CAPACITY);
		bytes[pos] = (byte) (l >>> 56);
		bytes[pos + 1] = (byte) (l >>> 48);
		bytes[pos + 2] = (byte) (l >>> 40);
		bytes[pos + 3] = (byte) (l >>> 32);
		bytes[pos + 4] = (byte) (l >>> 24);
		bytes[pos + 5] = (byte) (l >>> 16);
		bytes[pos + 6] = (byte) (l >>> 8);
		bytes[pos + 7] = (byte) l;
		top += 8;
	}

	/**
	 * 写入一个双浮点数值
	 */
	public void writeDouble(double d) {
		writeLong(Double.doubleToLongBits(d));
	}

	/**
	 * 写入动态长度
	 */
	public void writeLength(int len) {
		if (len >= 0x20000000 || len < 0)
			throw new IllegalArgumentException(this + " writeLength, invalid len:" + len);
		if (len >= 0x4000)
			writeInt(len + 0x20000000);
		else if (len >= 0x80)
			writeShort(len + 0x4000);
		else
			writeByte(len + 0x80);
	}

	/**
	 * 写入一个字节数组，可以为null
	 */
	public void writeData(byte[] data) {
		writeData(data, 0, (data != null) ? data.length : 0);
	}

	/**
	 * 写入一个字节数组，可以为null
	 */
	public void writeData(byte[] data, int pos, int len) {
		if (data == null) {
			writeLength(0);
			return;
		}
		writeLength(len + 1);
		write(data, pos, len);
	}

	/**
	 * 写入一个字符串，可以为null
	 */
	public void writeString(String s) {
		if (EmptyUtil.isEmpty(s)) {
			writeInt(0);
		} else {
			byte[] temp = s.getBytes();
			writeInt(temp.length);
			write(temp, 0, temp.length);
		}
	}

	/**
	 * 写入一个字符串，以指定的字符进行编码
	 */
	public void writeUTF(String str, String charsetName) {
		if (str == null) {
			writeLength(0);
			return;
		}
		byte[] data;
		if (charsetName != null) {
			try {
				data = str.getBytes(charsetName);
			} catch (Exception e) {
				throw new IllegalArgumentException(this + " writeUTF, invalid charsetName:" + charsetName);
			}
		} else
			data = str.getBytes();
		writeLength(data.length + 1);
		write(data, 0, data.length);
	}

	/**
	 * 写入一个utf字符串，可以为null
	 */
	public void writeUTF(String str) {
		writeUTF(str, 0, (str != null) ? str.length() : 0);
	}

	/**
	 * 写入一个utf字符串中指定的部分，可以为null
	 */
	public void writeUTF(String str, int index, int length) {
		if (str == null) {
			writeLength(0);
			return;
		}
		int len = getUTFLength(str, index, length);
		writeLength(len + 1);
		int pos = top;
		if (bytes.length < pos + len)
			setCapacity(pos + len);
		writeUTF(str, index, length, bytes, pos);
		top += len;
	}

	/**
	 * 将指定的字符串转换为UTF8格式的字节数据
	 */
	public void writeUTF(String str, int index, int len, byte[] data, int pos) {
		int c;
		for (int i = index; i < len; i++) {
			c = str.charAt(i);
			if ((c >= 0x0001) && (c <= 0x007f)) {
				data[pos++] = (byte) c;
			} else if (c > 0x07ff) {
				data[pos++] = (byte) (0xe0 | ((c >> 12) & 0x0f));
				data[pos++] = (byte) (0x80 | ((c >> 6) & 0x3f));
				data[pos++] = (byte) (0x80 | (c & 0x3f));
			} else {
				data[pos++] = (byte) (0xc0 | ((c >> 6) & 0x1f));
				data[pos++] = (byte) (0x80 | (c & 0x3f));
			}
		}
	}

	/**
	 * 获得指定的字符串转换为UTF8格式的字节数据的长度
	 */
	public int getUTFLength(String str, int index, int len) {
		int utfLen = 0;
		int c;
		for (int i = index; i < len; i++) {
			c = str.charAt(i);
			if ((c >= 0x0001) && (c <= 0x007f))
				utfLen++;
			else if (c > 0x07ff)
				utfLen += 3;
			else
				utfLen += 2;
		}
		return utfLen;
	}

	/**
	 * 在指定位置写入一个字节，length不变
	 */
	public void writeByte(int b, int pos) {
		if (bytes.length < pos + 1)
			setCapacity(pos + CAPACITY);
		bytes[pos] = (byte) b;
	}

	/**
	 * 得到可读取的字节数组，长度为写偏移量-读偏移量
	 */
	public byte[] getTopBytes() {
		byte[] data = new byte[top - offset];
		System.arraycopy(bytes, offset, data, 0, data.length);
		return data;
	}

	/**
	 * 清除字节缓存对象
	 */
	public void clear() {
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
		if (!(obj instanceof ByteBuf))
			return false;
		ByteBuf bb = (ByteBuf) obj;
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
		return super.toString() + "[" + top + "," + offset + "," + bytes.length + "] ";
	}
}