package com.weicoder.core.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import com.weicoder.common.io.IOUtil;
import com.weicoder.core.zip.Zip;

/**
 * BZip2压缩
 * @author WD
 * @since JDK7
 * @version 1.0 2012-09-15
 */
public final class BZip2Impl implements Zip {
	/**
	 * 压缩数据
	 * @param b 字节数组
	 * @return 压缩后的字节数组
	 */
	public byte[] compress(byte[] b) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); BZip2CompressorOutputStream bzip = new BZip2CompressorOutputStream(baos)) {
			IOUtil.write(bzip, b);
			// 压缩
			bzip.finish();
			bzip.flush();
			// 返回结果
			return baos.toByteArray();
		} catch (Exception e) {
			return b;
		}
	}

	/**
	 * 解压数据
	 * @param b 压缩字节
	 * @return 解压后数据
	 */
	public byte[] extract(byte[] b) {
		try (BZip2CompressorInputStream gis = new BZip2CompressorInputStream(new ByteArrayInputStream(b))) {
			return IOUtil.read(gis, false);
		} catch (Exception e) {
			return b;
		}
	}
}