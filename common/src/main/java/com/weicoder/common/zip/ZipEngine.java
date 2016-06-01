package com.weicoder.common.zip;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.factory.FactoryKey;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.zip.impl.GzipImpl;
import com.weicoder.common.zip.impl.ZipImpl;
import com.weicoder.common.zip.impl.ZlibImpl;

/**
 * 压缩引擎
 * @author WD 
 *   
 */
public final class ZipEngine extends FactoryKey<String, Zip> {
	//工厂
	private final static ZipEngine	FACTORY	= new ZipEngine();
	// 压缩器
	private final static Zip		ZIP		= FACTORY.getInstance(CommonParams.ZIP);

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

	@Override
	public Zip newInstance(String key) {
		// 判断算法
		switch (key) {
			case "gzip":
				return new GzipImpl();
			case "zip":
				return new ZipImpl();
			default:
				return new ZlibImpl();
		}
	}

	private ZipEngine() {}
}