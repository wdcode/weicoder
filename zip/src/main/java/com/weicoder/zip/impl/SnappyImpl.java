package com.weicoder.zip.impl;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.snappy.SnappyCompressorInputStream;
import org.apache.commons.compress.compressors.snappy.SnappyCompressorOutputStream;

import com.weicoder.common.zip.base.BaseZip;

/**
 * Snappy压缩
 * 
 * @author WD
 */
public final class SnappyImpl extends BaseZip {
	@Override
	protected InputStream is(InputStream is) throws Exception {
		return new SnappyCompressorInputStream(is);
	}

	@Override
	protected OutputStream os(OutputStream os) throws Exception {
		return new SnappyCompressorOutputStream(os, Long.MAX_VALUE);
	}
}