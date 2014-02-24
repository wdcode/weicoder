package com.weicoder.core.zip.impl;

import java.io.ByteArrayInputStream;
import java.util.zip.DeflaterInputStream;
import java.util.zip.InflaterInputStream;

import com.weicoder.common.io.IOUtil; 
import com.weicoder.core.zip.Zip;

/**
 * ZLIB压缩
 * @author WD
 * @since JDK7
 * @version 1.0 2012-09-15
 */
public final class ZlibImpl implements Zip {
	/**
	 * 压缩数据
	 * @param b 字节数组
	 * @return 压缩后的字节数组
	 */
	public byte[] compress(byte[] b) {
		return IOUtil.read(new DeflaterInputStream(new ByteArrayInputStream(b)));
	}

	/**
	 * 解压数据
	 * @param b 压缩字节
	 * @return 解压后数据
	 */
	public byte[] extract(byte[] b) {
		return IOUtil.read(new InflaterInputStream(new ByteArrayInputStream(b)));
	}
}