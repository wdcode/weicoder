package com.weicoder.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream; 
import com.weicoder.common.zip.base.BaseCompressor; 

/**
 * LZMA压缩
 * 
 * @author  WD 
 */
public final class LZMAImpl extends BaseCompressor { 

	@Override
	protected InputStream is(ByteArrayInputStream bis) throws Exception {
		return new LZMACompressorInputStream(bis);
	}

	@Override
	protected OutputStream os(ByteArrayOutputStream bos) throws Exception {
		return new LZMACompressorOutputStream(bos);
	}
}