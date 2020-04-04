package com.weicoder.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorInputStream;
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorOutputStream;

import com.weicoder.common.zip.base.BaseCompressor;

/**
 * SnappyFramed帧压缩
 * 
 * @author WD
 */
public final class SnappyFramedImpl extends BaseCompressor {
	@Override
	protected InputStream is(ByteArrayInputStream bis) throws Exception {
		return new FramedSnappyCompressorInputStream(bis);
	}

	@Override
	protected OutputStream os(ByteArrayOutputStream bos) throws Exception {
		return new FramedSnappyCompressorOutputStream(bos);
	}
}