package com.weicoder.common.zip.base;

import com.weicoder.common.U;
import com.weicoder.common.zip.Zip;

/**
 * 基础压缩实现
 * @author WD
 */
public abstract class BaseZip implements Zip {

	@Override
	public final byte[] compress(byte[] b) {
		try {
			// 获得压缩数据
			byte[] data = compress0(b);
			// 如果压缩比原始数据大 返回原始数据
			return data.length >= b.length ? b : data;
		} catch (Exception e) {
			// 如果压缩异常 返回原数据
			return b;
		}
	}

	@Override
	public final byte[] extract(byte[] b) {
		try {
			// 解压缩
			byte[] data = extract0(b);
			// 如果解压缩的为空 返回原数据
			return U.E.isEmpty(data) ? b : data;
		} catch (Exception e) {
			// 如果异常或则无法解压 返回原数据
			return b;
		}
	}

	/**
	 * 实际压缩算法的实现
	 * @param b 字节数组
	 * @return 压缩后数据
	 * @throws Exception 抛出的异常
	 */
	protected abstract byte[] compress0(byte[] b) throws Exception;

	/**
	 * 实际的解压缩算法
	 * @param b 字节数组
	 * @return 压缩后数据
	 * @throws Exception 抛出的异常
	 */
	protected abstract byte[] extract0(byte[] b) throws Exception;
}
