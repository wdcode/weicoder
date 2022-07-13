package com.weicoder.common.zip.impl;
 
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.weicoder.common.zip.base.BaseZip;
  

/**
 * GZIP压缩
 * 
 * @author WD
 */
public final class GzipImpl extends BaseZip {  
	@Override
	protected InputStream is(InputStream is) throws Exception {
		return new GZIPInputStream(is);
	}

	@Override
	protected OutputStream os(OutputStream os) throws Exception {
		return new GZIPOutputStream(os);
	}
}