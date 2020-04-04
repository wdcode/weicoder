package com.weicoder.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import com.weicoder.common.zip.base.BaseCompressor;

/**
 * BZip2压缩
 * 
 * @author WD
 */
public final class BZip2Impl extends BaseCompressor {

	@Override
	protected InputStream is(ByteArrayInputStream bis) throws Exception {
		return new BZip2CompressorInputStream(bis);
	}

	@Override
	protected OutputStream os(ByteArrayOutputStream bos) throws Exception {
		return new BZip2CompressorOutputStream(bos);
	}
}