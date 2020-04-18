package com.weicoder.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.commons.compress.compressors.xz.XZCompressorInputStream;
import org.apache.commons.compress.compressors.xz.XZCompressorOutputStream;

import com.weicoder.common.zip.base.BaseCompressor;

/**
 * XZ压缩
 * 
 * @author WD
 */
public final class XzImpl extends BaseCompressor {

	@Override
	protected InputStream is(ByteArrayInputStream bis) throws Exception {
		return new XZCompressorInputStream(bis);
	}

	@Override
	protected OutputStream os(ByteArrayOutputStream bos) throws Exception {
		return new XZCompressorOutputStream(bos);
	}
}