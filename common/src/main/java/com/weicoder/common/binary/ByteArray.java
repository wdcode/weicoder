package com.weicoder.common.binary;

/**
 * 序列化字节数组
 * @author WD
 * @since JDK7
 * @version 1.0 2014-3-12
 */
public interface ByteArray {
	/**
	 * 把相关字段转换成字节数组
	 * @return 字节数组
	 */
	byte[] array();

	/**
	 * 把字节数组转换成自己的字段
	 * @param b 要转换的字节数组
	 * @return 一般返回自身 也可以返回副本
	 */
	ByteArray array(byte[] b);
}
