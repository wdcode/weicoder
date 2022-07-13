package com.weicoder.zip.impl;
 
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorOutputStream; 
import com.weicoder.common.zip.base.BaseZip;

/**
 * LZ4Block块压缩
 * 
 * @author WD
 */
public final class Lz4BlockImpl extends BaseZip {

	@Override
	protected InputStream is(InputStream is) throws Exception {
		return new BlockLZ4CompressorInputStream(is);
	}

	@Override
	protected OutputStream os(OutputStream os) throws Exception {
		return new BlockLZ4CompressorOutputStream(os);
	}
}