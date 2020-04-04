package com.weicoder.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream;
import com.weicoder.common.zip.base.BaseCompressor;

/**
 * LZ4Framed帧压缩
 * 
 * @author WD
 */
public final class LZ4FramedImpl extends BaseCompressor {
	@Override
	protected InputStream is(ByteArrayInputStream bis) throws Exception {
		return new FramedLZ4CompressorInputStream(bis);
	}

	@Override
	protected OutputStream os(ByteArrayOutputStream bos) throws Exception {
		return new FramedLZ4CompressorOutputStream(bos);
	}
}