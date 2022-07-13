package com.weicoder.zip.impl;
 
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;

import com.weicoder.common.zip.base.BaseZip;
 

/**
 * XZ压缩
 * 
 * @author WD
 */
public final class XzImpl extends BaseZip {

	@Override
	protected InputStream is(InputStream is) throws Exception {
		return new XZCompressorInputStream(is);
	}

	@Override
	protected OutputStream os(OutputStream os) throws Exception {
		return new XZCompressorOutputStream(os);
	}
}