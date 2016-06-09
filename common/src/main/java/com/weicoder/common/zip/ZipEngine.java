package com.weicoder.common.zip;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.zip.impl.GzipImpl;
import com.weicoder.common.zip.impl.ZipImpl;
import com.weicoder.common.zip.impl.ZlibImpl;

/**
 * 压缩引擎
 * @author WD 
 */
public final class ZipEngine {
	/**Zlib压缩器*/
	public final static Zip		ZLIB	= new ZlibImpl();
	/**gzip压缩器*/
	public final static Zip		GZIP	= new GzipImpl();
	/**zip压缩器*/
	public final static Zip		ZIP		= new ZipImpl();
	// 压缩器
	private final static Zip	Z		= "gzip".equals(CommonParams.ZIP) ? GZIP : "zip".equals(CommonParams.ZIP) ? ZIP : ZLIB;

	/**
	 * 压缩数据
	 * @param obj 要压缩的对象
	 * @return 压缩后的字节数组或则原对象的字节数组
	 */
	public static byte[] compress(Object obj) {
		return obj == null ? ArrayConstants.BYTES_EMPTY : Z.compress(Bytes.toBytes(obj));
	}

	/**
	 * 解压数据
	 * @param obj 要解压的对象
	 * @return 解压后数据
	 */
	public static byte[] extract(Object obj) {
		return obj == null ? ArrayConstants.BYTES_EMPTY : Z.extract(Bytes.toBytes(obj));
	}

	private ZipEngine() {}
}