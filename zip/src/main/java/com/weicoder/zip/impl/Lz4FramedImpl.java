package com.weicoder.zip.impl;
 
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream;

import com.weicoder.common.zip.base.BaseZip; 

/**
 * LZ4Framed帧压缩
 * 
 * @author WD
 */
public final class Lz4FramedImpl extends BaseZip {
	@Override
	protected InputStream is(InputStream is) throws Exception {
		return new FramedLZ4CompressorInputStream(is);
	}

	@Override
	protected OutputStream os(OutputStream os) throws Exception {
		return new FramedLZ4CompressorOutputStream(os);
	}
}