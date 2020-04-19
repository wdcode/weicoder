package com.weicoder.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.BlockLZ4CompressorOutputStream;
import com.weicoder.common.zip.base.BaseCompressor;

/**
 * LZ4Block块压缩
 * 
 * @author WD
 */
public final class Lz4BlockImpl extends BaseCompressor {

	@Override
	protected InputStream is(ByteArrayInputStream bis) throws Exception {
		return new BlockLZ4CompressorInputStream(bis);
	}

	@Override
	protected OutputStream os(ByteArrayOutputStream bos) throws Exception {
		return new BlockLZ4CompressorOutputStream(bos);
	}
}