package com.weicoder.common.zip.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.DeflaterInputStream;
import java.util.zip.InflaterOutputStream;

import com.weicoder.common.zip.base.BaseZip;

/**
 * ZLIB压缩
 * 
 * @author WD
 */
public final class ZlibImpl extends BaseZip {

	@Override
	protected InputStream is(InputStream in) throws Exception {
		return new DeflaterInputStream(in);
	}

	@Override
	protected OutputStream os(OutputStream os) throws Exception {
		return new InflaterOutputStream(os);
	}
}