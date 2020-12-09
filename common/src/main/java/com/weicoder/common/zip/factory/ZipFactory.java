package com.weicoder.common.zip.factory;

import com.weicoder.common.factory.FactoryInterface;
import com.weicoder.common.zip.Zip;
import com.weicoder.common.zip.impl.ZlibImpl;

/**
 * 压缩算法zip工厂
 * 
 * @author wudi
 */
public class ZipFactory extends FactoryInterface<Zip> {
	// 工厂
	private final static ZipFactory FACTORY = new ZipFactory();

	/**
	 * 获得压缩算法实现
	 * 
	 * @return 实现
	 */
	public static Zip getZip() {
		return FACTORY.getInstance();
	}

	/**
	 * 获得压缩算法实现
	 * 
	 * @param  name 实现名称
	 * @return      实现
	 */
	public static Zip getZip(String name) {
		return FACTORY.getInstance(name);
	}

	private ZipFactory() {
	}

	@Override
	protected Class<? extends Zip> def() { 
		return ZlibImpl.class;
	}
}
