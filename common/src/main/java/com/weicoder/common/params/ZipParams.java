package com.weicoder.common.params;

/**
 * 压缩引擎参数读取类
 * 
 * @author WD
 */
public final class ZipParams {
	/** ZIP实现 */
	public final static String IMPL = Params.getString("zip.impl", "zlib");

	private ZipParams() {
	}
}
