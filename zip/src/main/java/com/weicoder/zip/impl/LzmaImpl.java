package com.weicoder.zip.impl;
 
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream;
import org.apache.commons.compress.compressors.lzma.LZMACompressorOutputStream;

import com.weicoder.common.zip.base.BaseZip;  

/**
 * LZMA压缩
 * 
 * @author  WD 
 */
public final class LzmaImpl extends BaseZip { 

	@Override
	protected InputStream is(InputStream is) throws Exception {
		return new LZMACompressorInputStream(is);
	}

	@Override
	protected OutputStream os(OutputStream os) throws Exception {
		return new LZMACompressorOutputStream(os);
	}
}