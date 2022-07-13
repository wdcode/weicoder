package com.weicoder.zip.impl;
 
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

import com.weicoder.common.zip.base.BaseZip;

/**
 * BZip2压缩
 * 
 * @author WD
 */
public final class BZip2Impl extends BaseZip {

	@Override
	protected InputStream is(InputStream in) throws Exception {
		return new BZip2CompressorInputStream(in);
	}

	@Override
	protected OutputStream os(OutputStream out) throws Exception {
		return new BZip2CompressorOutputStream(out);
	}
}