package com.weicoder.core.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import com.weicoder.common.io.IOUtil;
import com.weicoder.core.zip.base.BaseZip;

/**
 * BZip2压缩
 * @author WD
 * @since JDK7
 * @version 1.0 2012-09-15
 */
public final class BZip2Impl extends BaseZip {

	@Override
	protected byte[] compress0(byte[] b) throws Exception {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); BZip2CompressorOutputStream bzip = new BZip2CompressorOutputStream(baos)) {
			IOUtil.write(bzip, b);
			// 压缩
			bzip.finish();
			bzip.flush();
			// 返回结果
			return baos.toByteArray();
		}
	}

	@Override
	protected byte[] extract0(byte[] b) throws Exception {
		try (BZip2CompressorInputStream gis = new BZip2CompressorInputStream(new ByteArrayInputStream(b))) {
			return IOUtil.read(gis, false);
		}
	}
}