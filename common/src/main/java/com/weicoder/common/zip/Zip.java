package com.weicoder.common.zip;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 压缩与解压接口
 * 
 * @author WD
 */
public interface Zip {
	/**
	 * 压缩数据
	 * 
	 * @param b 字节数组
	 * @return 压缩后的字节数组
	 */
	byte[] compress(byte[] b);

	/**
	 * 解压数据
	 * 
	 * @param b 压缩字节
	 * @return 解压后数据
	 */
	byte[] extract(byte[] b);

	/**
	 * 压缩数据
	 * 
	 * @param in 要压缩的输入流
	 * @return 压缩后的字节输出流
	 */
	ByteArrayOutputStream compress(InputStream in);

	/**
	 * 解压数据
	 * 
	 * @param in 要解压的输入流
	 * @return 解压后的字节输出流
	 */
	ByteArrayOutputStream extract(InputStream in);

	/**
	 * 压缩数据
	 * 
	 * @param in  要压缩的输入流
	 * @param out 压缩的输出流
	 */
	<E extends OutputStream> E compress(InputStream in, E out);

	/**
	 * 解压数据
	 * 
	 * @param in  要解缩的输入流
	 * @param out 解压的输出流
	 */
	<E extends OutputStream> E extract(InputStream in, E out);
}