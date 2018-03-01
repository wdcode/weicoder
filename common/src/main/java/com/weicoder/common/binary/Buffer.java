package com.weicoder.common.binary;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;

/**
 * 类说明：数据包类 ,字节缓存类，字节操作高位在前，低位在后
 * @author WD
 */
public final class Buffer implements ByteArray {
	// 字节数组
	private byte[]	data;
	// 写数据的偏移量，每写一次增加
	private int		top;
	// 读数据的偏移量,每读一次增加
	private int		offset;
	// 是否线程安全
	private boolean	sync;
	// 线程锁
	private Lock	lock;

	/**
	 * 按默认的大小构造一个字节缓存对象
	 */
	public Buffer() {
		this(CommonParams.IO_BUFFERSIZE, false);
	}

	/**
	 * 按默认的大小构造一个字节缓存对象
	 * @param sync 是否线程安全
	 */
	public Buffer(boolean sync) {
		this(CommonParams.IO_BUFFERSIZE, false);
	}

	/**
	 * 按指定的大小构造一个字节缓存对象
	 * @param capacity 初始容量
	 * @param sync 是否线程安全
	 */
	public Buffer(int capacity, boolean sync) {
		this(new byte[capacity], 0, 0, sync);
	}

	/**
	 * 按指定的字节数组构造一个字节缓存对象
	 * @param data 初始化数组
	 */
	public Buffer(byte[] data) {
		this(data, 0, data.length, false);
	}

	/**
	 * 按指定的字节数组构造一个字节缓存对象
	 * @param data 初始化数组
	 * @param sync 是否线程安全
	 */
	public Buffer(byte[] data, boolean sync) {
		this(data, 0, data.length, sync);
	}

	/**
	 * 按指定的字节数组构造一个字节缓存对象
	 * @param data 初始化数组
	 * @param index 读索引
	 * @param length 写索引
	 * @param sync 是否线程安全
	 */
	public Buffer(byte[] data, int index, int length, boolean sync) {
		this.data = data;
		top = index + length;
		offset = index;
		this.sync = sync;
		// 线程安全 初始化锁
		if (sync) {
			lock = new ReentrantLock(true);
		}
	}

	/**
	 * 设置字节缓存的容积，只能扩大容积
	 * @param len 长度
	 */
	public void capacity(int len) {
		// 要扩展的容量小于原长度 放弃修改
		if (len <= data.length) {
			return;
		}
		// 如果同步 加锁
		if (sync) {
			lock.lock();
		}
		// 声明个新长度临时数组
		byte[] temp = new byte[len < CommonParams.IO_BUFFERSIZE ? CommonParams.IO_BUFFERSIZE : len];
		// 读取原有数据
		System.arraycopy(data, 0, temp, 0, top);
		// 复制到新数组
		data = temp;
		// 如果同步 解锁
		if (sync) {
			lock.unlock();
		}
	}

	/**
	 * 得到写字节的偏移量
	 * @return 偏移
	 */
	public int top() {
		return top;
	}

	/**
	 * 设置写字节的偏移量
	 * @param top 偏移
	 */
	public void top(int top) {
		if (top < offset)
			return;
		if (top > length())
			capacity(top);
		this.top = top;
	}

	/**
	 * 得到读数据的偏移量
	 * @return 偏移
	 */
	public int offset() {
		return offset;
	}

	/**
	 * 设置读数据的偏移量
	 * @param offset 偏移
	 */
	public void offset(int offset) {
		if (offset < 0 || offset > top)
			return;
		this.offset = offset;
	}

	/**
	 * 剩余多少可读字节==写偏移量-读偏移量得差值
	 * @return 剩余字节
	 */
	public int remaining() {
		return top - offset;
	}

	/**
	 * 是否还有任何一个可读字节
	 * @return 是否可读
	 */
	public boolean hasRemaining() {
		return remaining() > 0;
	}

	/**
	 * 得到字节数组的长度
	 * @return 长度
	 */
	public int length() {
		return data.length;
	}

	/**
	 * 按当前偏移位置读入指定的长度的字节数组
	 * @param len 长度
	 * @return 字节数组
	 */
	public byte[] read(int len) {
		return read(new byte[len]);
	}

	/**
	 * 按当前偏移位置读入指定的字节数组
	 * @param data 指定的字节数组
	 * @return 字节数组
	 */
	public byte[] read(byte[] data) {
		return read(data, 0, data.length);
	}

	/**
	 * 按当前偏移位置读入指定的字节数组
	 * @param data 指定的字节数组
	 * @param pos 指定的字节数组的起始位置
	 * @param len 读入的长度
	 * @return 字节数组
	 */
	public byte[] read(byte[] data, int pos, int len) {
		// 如果同步 加锁
		if (sync) {
			lock.lock();
		}
		// 复制原数组
		System.arraycopy(this.data, offset, data, pos, len);
		offset += len;
		// 如果同步 解锁
		if (sync) {
			lock.unlock();
		}
		// 返回数据
		return data;
	}

	/**
	 * 读出一个布尔值
	 * @return boolean
	 */
	public boolean readBoolean() {
		return readByte() != 0;
	}

	/**
	 * 读出一个字节
	 * @return 字节
	 */
	public byte readByte() {
		return read(1)[0];
	}

	/**
	 * 读出一个字符
	 * @return 字符
	 */
	public char readChar() {
		return (char) readShort();
	}

	/**
	 * 读出一个短整型数值
	 * @return short
	 */
	public short readShort() {
		return Bytes.toShort(read(2));
	}

	/**
	 * 读出一个整型数值
	 * @return int
	 */
	public int readInt() {
		return Bytes.toInt(read(4));
	}

	/**
	 * 读出一个浮点数值
	 * @return float
	 */
	public float readFloat() {
		return Bytes.toFloat(read(4));
	}

	/**
	 * 读出一个长整型数值
	 * @return long
	 */
	public long readLong() {
		return Bytes.toLong(read(8));
	}

	/**
	 * 读出一个双浮点数值
	 * @return double
	 */
	public double readDouble() {
		return Bytes.toDouble(read(8));
	}

	/**
	 * 读出一个指定长度的字符串
	 * @param len 长度
	 * @return String
	 */
	public String readString(int len) {
		return len == 0 ? StringConstants.EMPTY : new String(read(new byte[len], 0, len));
	}

	/**
	 * 读出一个字符串，长度不超过65534
	 * @return String
	 */
	public String readString() {
		return readString(readShort());
	}

	/**
	 * 写入指定字节数组
	 * @param data 指定的字节数组
	 * @return 字节数组
	 */
	public byte[] write(byte[] data) {
		return write(data, 0, data.length);
	}

	/**
	 * 写入指定字节数组
	 * @param data 指定的字节数组
	 * @param pos 指定的字节数组的起始位置
	 * @param len 写入的长度
	 * @return 字节数组
	 */
	public byte[] write(byte[] data, int pos, int len) {
		// 容量不足扩容
		if (data.length < top + len) {
			capacity(top + len);
		}
		// 如果同步 加锁
		if (sync) {
			lock.lock();
		}
		// 复制原数组
		System.arraycopy(data, pos, this.data, top, len);
		top += len;
		// 如果同步 解锁
		if (sync) {
			lock.unlock();
		}
		// 返回数组
		return data;
	}

	/**
	 * 写入一个布尔值
	 * @param b 布尔
	 */
	public void writeBoolean(boolean b) {
		writeByte((byte) (b ? 1 : 0));
	}

	/**
	 * 写入一个字节
	 * @param b 字节
	 */
	public void writeByte(byte b) {
		write(new byte[] { b });
	}

	/**
	 * 写入一个字符
	 * @param c 字符
	 */
	public void writeChar(char c) {
		write(Bytes.toBytes(c), 0, 2);
	}

	/**
	 * 写入一个短整型数值
	 * @param i int
	 */
	public void writeShort(int i) {
		writeShort((short) i);
	}

	/**
	 * 写入一个短整型数值
	 * @param s short
	 */
	public void writeShort(short s) {
		writeShort(s, 0);
	}

	/**
	 * 在指定位置写入一个短整型数值，length不变
	 * @param s short
	 * @param pos 位置
	 */
	public void writeShort(short s, int pos) {
		write(Bytes.toBytes(s), pos, pos + 2);
	}

	/**
	 * 写入一个整型数值
	 * @param i int
	 */
	public void writeInt(int i) {
		writeInt(i, 0);
	}

	/**
	 * 在指定位置写入一个整型数值，length不变
	 * @param i int
	 * @param pos 位置
	 */
	public void writeInt(int i, int pos) {
		write(Bytes.toBytes(i), pos, pos + 4);
	}

	/**
	 * 写入一个浮点数值
	 * @param f float
	 */
	public void writeFloat(float f) {
		writeInt(Float.floatToIntBits(f));
	}

	/**
	 * 写入一个长整型数值
	 * @param l long
	 */
	public void writeLong(long l) {
		writeLong(l, 0);
	}

	/**
	 * 写入一个长整型数值
	 * @param l long
	 * @param pos 位置
	 */
	public void writeLong(long l, int pos) {
		write(Bytes.toBytes(l), pos, pos + 8);
	}

	/**
	 * 写入一个双浮点数值
	 * @param d double
	 */
	public void writeDouble(double d) {
		writeLong(Double.doubleToLongBits(d));
	}

	/**
	 * 写入一个字符串，可以为null
	 * @param s 字符串
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
	 * 压缩缓冲区 抛弃以读数据 并把容量截取到写坐标
	 */
	public void compact() {
		// 读位置不为0时才需要压缩
		if (offset > 0) {
			// 如果同步 加锁
			if (sync) {
				lock.lock();
			}
			// 移动数据
			System.arraycopy(data, offset, data, 0, remaining());
			// 重置下标
			top -= offset;
			offset = 0;
			// 如果同步 解锁
			if (sync) {
				lock.unlock();
			}
		}
	}

	/**
	 * 获得有效数据
	 */
	public byte[] array() {
		return Bytes.copy(data, 0, top);
	}

	/**
	 * 清除字节缓存对象
	 */
	public void clear() {
		// 如果数组长度小于默认缓存长度 重新生成数组
		if (length() < CommonParams.IO_BUFFERSIZE) {
			data = new byte[CommonParams.IO_BUFFERSIZE];
		}
		top = 0;
		offset = 0;
	}

	@Override
	public ByteArray array(byte[] b) {
		clear();
		write(b);
		return this;
	}

	@Override
	public String toString() {
		return StringUtil.add("(top=", top, ",offset=", offset, ",len=" + length() + ")");
	}
}