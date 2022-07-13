package com.weicoder.zip.impl;
 
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorInputStream;
import org.apache.commons.compress.compressors.snappy.FramedSnappyCompressorOutputStream;

import com.weicoder.common.zip.base.BaseZip;
 

/**
 * SnappyFramed帧压缩
 * 
 * @author WD
 */
public final class SnappyFramedImpl extends BaseZip {
	@Override
	protected InputStream is(InputStream is) throws Exception {
		return new FramedSnappyCompressorInputStream(is);
	}

	@Override
	protected OutputStream os(OutputStream os) throws Exception {
		return new FramedSnappyCompressorOutputStream(os);
	}
}