package com.weicoder.core.zip.factory;

import com.weicoder.core.factory.FactoryKey;
import com.weicoder.core.params.CoreParams;
import com.weicoder.core.zip.Zip;
import com.weicoder.core.zip.impl.BZip2Impl;
import com.weicoder.core.zip.impl.GzipImpl;
import com.weicoder.core.zip.impl.ZipImpl;
import com.weicoder.core.zip.impl.ZlibImpl;

/**
 * 压缩器工厂
 * @author WD
 * @since JDK6
 * @version 1.0 2013-03-07
 */
public final class ZipFactory extends FactoryKey<String, Zip> {
	// 工厂
	private final static ZipFactory	FACTORY	= new ZipFactory();

	/**
	 * 获得压缩器
	 * @param key 键
	 * @return Zip
	 */
	public static Zip getZip() {
		return getZip(CoreParams.ZIP);
	}

	/**
	 * 获得压缩器
	 * @param key 键
	 * @return Zip
	 */
	public static Zip getZip(String key) {
		return FACTORY.getInstance(key);
	}

	@Override
	public Zip newInstance(String key) {
		// 判断算法
		switch (key) {
			case "gzip":
				return new GzipImpl();
			case "zip":
				return new ZipImpl();
			case "bzip2":
				return new BZip2Impl();
			default:
				return new ZlibImpl();
		}
	}
}
