package com.weicoder.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorInputStream;
import org.apache.commons.compress.compressors.zstandard.ZstdCompressorOutputStream;

import com.weicoder.common.zip.base.BaseCompressor;

/**
 * Zstd压缩
 * 
 * @author WD
 */
public final class ZstdImpl extends BaseCompressor {

	@Override
	protected InputStream is(ByteArrayInputStream bis) throws Exception {
		return new ZstdCompressorInputStream(bis);
	}

	@Override
	protected OutputStream os(ByteArrayOutputStream bos) throws Exception {
		return new ZstdCompressorOutputStream(bos);
	}
}