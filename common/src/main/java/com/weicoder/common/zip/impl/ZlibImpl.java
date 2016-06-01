package com.weicoder.common.zip.impl;

import java.io.ByteArrayInputStream;
import java.util.zip.DeflaterInputStream;
import java.util.zip.InflaterInputStream;

import com.weicoder.common.io.IOUtil;
import com.weicoder.common.zip.base.BaseZip;

/**
 * ZLIB压缩
 * @author WD 
 *  
 */
public final class ZlibImpl extends BaseZip {

	@Override
	protected byte[] compress0(byte[] b) throws Exception {
		return IOUtil.read(new DeflaterInputStream(new ByteArrayInputStream(b)));
	}

	@Override
	protected byte[] extract0(byte[] b) throws Exception {
		return IOUtil.read(new InflaterInputStream(new ByteArrayInputStream(b)));
	}
}