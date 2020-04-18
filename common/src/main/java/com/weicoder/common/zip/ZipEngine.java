package com.weicoder.common.zip;

import com.weicoder.common.C.A;
import com.weicoder.common.W.B; 
import com.weicoder.common.zip.factory.ZipFactory; 

/**
 * 压缩引擎
 * 
 * @author WD
 */
public class ZipEngine {
	/** Zlib压缩器 */
	public final static Zip ZLIB = ZipFactory.getZip("zlib");// new ZlibImpl();
	/** gzip压缩器 */
	public final static Zip GZIP = ZipFactory.getZip("gzip");// new GzipImpl();
	/** zip压缩器 */
	public final static Zip ZIP  = ZipFactory.getZip("zip"); // new ZipImpl();
	// 压缩器
	private final static Zip Z = ZipFactory.getZip();// "gzip".equals(ZipParams.IMPL) ? GZIP :
														// "zip".equals(ZipParams.IMPL) ? ZIP : ZLIB;

	/**
	 * 压缩数据
	 * 
	 * @param  obj 要压缩的对象
	 * @return     压缩后的字节数组或则原对象的字节数组
	 */
	public static byte[] compress(Object obj) {
		return obj == null ? A.BYTES_EMPTY : Z.compress(B.toBytes(obj));
	}

	/**
	 * 解压数据
	 * 
	 * @param  obj 要解压的对象
	 * @return     解压后数据
	 */
	public static byte[] extract(Object obj) {
		return obj == null ? A.BYTES_EMPTY : Z.extract(B.toBytes(obj));
	}
}