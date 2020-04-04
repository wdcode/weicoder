package com.weicoder.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream;
import org.apache.commons.compress.compressors.snappy.SnappyCompressorOutputStream;

import com.weicoder.common.zip.base.BaseCompressor;

/**
 * Snappy压缩
 * 
 * @author WD
 */
public final class SnappyImpl extends BaseCompressor {
	@Override
	protected InputStream is(ByteArrayInputStream bis) throws Exception {
		return new SnappyCompressorInputStream(bis);
	}

	@Override
	protected OutputStream os(ByteArrayOutputStream bos) throws Exception {
		return new SnappyCompressorOutputStream(bos, bos.size());
	}
}