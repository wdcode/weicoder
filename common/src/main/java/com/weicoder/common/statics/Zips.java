package com.weicoder.common.statics;

import com.weicoder.common.constants.C;
import com.weicoder.common.constants.C.A;
import com.weicoder.common.lang.W.B;
import com.weicoder.common.zip.Zip;
import com.weicoder.common.zip.factory.ZipFactory;

/**
 * 压缩引擎
 * 
 * @author WD
 */
public sealed class Zips permits S.Z {
//	/** Zlib压缩器 */
//	public final static Zip		ZLIB	= get("zlib");
//	/** gzip压缩器 */
//	public final static Zip		GZIP	= get("gzip");
//	/** zip压缩器 */
//	public final static Zip		ZIP		= get("zip");
	// 压缩器
	private final static Zip Z = get(C.S.EMPTY);

	/**
	 * 压缩数据
	 * 
	 * @param obj 要压缩的对象
	 * @return 压缩后的字节数组或则原对象的字节数组
	 */
	public static byte[] compress(Object obj) {
		return obj == null ? A.BYTES_EMPTY : Z.compress(B.toBytes(obj));
	}

	/**
	 * 解压数据
	 * 
	 * @param obj 要解压的对象
	 * @return 解压后数据
	 */
	public static byte[] extract(Object obj) {
		return obj == null ? A.BYTES_EMPTY : Z.extract(B.toBytes(obj));
	}

	/**
	 * 调用ZipFactory.getZip(key)
	 * 
	 * @param key 键
	 * @return 值
	 */
	public static Zip get(String key) {
		return ZipFactory.getZip(key);
	}
}