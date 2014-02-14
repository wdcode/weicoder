package com.weicoder.core.zip;

/**
 * 压缩与解压接口
 * @author WD
 * @since JDK7
 * @version 1.0 2012-09-15
 */
public interface Zip {
	/**
	 * 压缩数据
	 * @param b 字节数组
	 * @return 压缩后的字节数组
	 */
	byte[] compress(byte[] b);

	/**
	 * 解压数据
	 * @param b 压缩字节
	 * @return 解压后数据
	 */
	byte[] extract(byte[] b);
}