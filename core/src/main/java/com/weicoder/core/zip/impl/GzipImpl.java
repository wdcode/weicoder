package com.weicoder.core.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.weicoder.common.io.IOUtil;
import com.weicoder.core.zip.base.BaseZip;

/**
 * GZIP压缩
 * @author WD 
 * @version 1.0 
 */
public final class GzipImpl extends BaseZip {

	@Override
	protected byte[] compress0(byte[] b) throws Exception {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); GZIPOutputStream gzip = new GZIPOutputStream(baos)) {
			// 把压缩后的字节数组写到输出流
			IOUtil.write(gzip, b, false);
			// 完成压缩数据
			gzip.finish();
			// 返回字节数组
			return baos.toByteArray();
		}
	}

	@Override
	protected byte[] extract0(byte[] b) throws Exception {
		try (GZIPInputStream gzip = new GZIPInputStream(new ByteArrayInputStream(b))) {
			return IOUtil.read(gzip, false);
		}
	}
}