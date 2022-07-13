package com.weicoder.zip.impl;
 
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorInputStream;
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream;

import com.weicoder.common.zip.base.BaseZip;
 

/**
 * Zstd压缩
 * 
 * @author WD
 */
public final class ZstdImpl extends BaseZip {

	@Override
	protected InputStream is(InputStream is) throws Exception {
		return new ZstdCompressorInputStream(is);
	}

	@Override
	protected OutputStream os(OutputStream os) throws Exception {
		return new ZstdCompressorOutputStream(os);
	}
}