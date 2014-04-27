package com.weicoder.core.zip.impl;

import java.io.ByteArrayInputStream;
import java.util.zip.DeflaterInputStream;
import java.util.zip.InflaterInputStream;

import com.weicoder.common.io.IOUtil;
import com.weicoder.core.zip.base.BaseZip;

/**
 * ZLIB压缩
 * @author WD
 * @since JDK7
 * @version 1.0 2012-09-15
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