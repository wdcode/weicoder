package com.weicoder.common.interfaces;

/**
 * 序列化Bean
 * @author WD
 * @since JDK6
 * @version 1.0 2013-10-8
 */
public interface BytesBean {
	/**
	 * 把相关字段转换成字节数组
	 * @return 字节数组
	 */
	byte[] toBytes();

	/**
	 * 把字节数组转换成自己的字段
	 * @param b 要转换的字节数组
	 * @return 一般返回自身 也可以返回副本
	 */
	BytesBean toBean(byte[] b);
}
