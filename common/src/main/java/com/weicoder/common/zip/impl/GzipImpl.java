package com.weicoder.common.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream; 
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
 
import com.weicoder.common.zip.base.BaseCompressor; 

/**
 * GZIP压缩
 * 
 * @author WD
 */
public final class GzipImpl extends BaseCompressor {  
	@Override
	protected InputStream is(ByteArrayInputStream bis) throws Exception {
		return new GZIPInputStream(bis);
	}

	@Override
	protected OutputStream os(ByteArrayOutputStream bos) throws Exception {
		return new GZIPOutputStream(bos);
	}
}