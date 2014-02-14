package com.weicoder.web.socket.interfaces;

/**
 * 字节缓冲接口
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-22
 */
public interface Buffer {
	/**
	 * 添加字节流到缓冲区
	 * @param src 字节流
	 * @return
	 */
	void put(byte[] src);

	/**
	 * 反转缓冲区
	 * @return
	 */
	void flip();

	/**
	 * 获得可读字节数
	 * @return
	 */
	int remaining();

	/**
	 * 压缩缓冲区
	 */
	void compact();

	/**
	 * 重置缓冲区
	 */
	void rewind();

	/**
	 * 清除缓冲区
	 */
	void clear();

	/**
	 * 读取缓冲区一个int整型
	 * @return
	 */
	int getInt();

	/**
	 * 读取指定长度的字节流
	 * @param data 字节流
	 */
	void get(byte[] data);
}
