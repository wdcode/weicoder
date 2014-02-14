package com.weicoder.core.zip;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.core.zip.factory.ZipFactory;

/**
 * 压缩引擎
 * @author WD
 * @since JDK7
 * @version 1.0 2012-01-18
 */
public final class ZipEngine {
	// 压缩器
	private final static Zip	ZIP	= ZipFactory.getZip();

	/**
	 * 压缩数据
	 * @param obj 要压缩的对象
	 * @return 压缩后的字节数组或则原对象的字节数组
	 */
	public static byte[] compress(Object obj) {
		return obj == null ? ArrayConstants.BYTES_EMPTY : ZIP.compress(Bytes.toBytes(obj));
	}

	/**
	 * 解压数据
	 * @param obj 要解压的对象
	 * @return 解压后数据
	 */
	public static byte[] extract(Object obj) {
		return obj == null ? ArrayConstants.BYTES_EMPTY : ZIP.extract(Bytes.toBytes(obj));
	}

	/**
	 * 私有构造
	 */
	private ZipEngine() {}
}