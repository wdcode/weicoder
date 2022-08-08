package com.weicoder.common.zip.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.weicoder.common.io.IOUtil;
import com.weicoder.common.log.Logs;
import com.weicoder.common.zip.Zip;

/**
 * 基础压缩实现
 * 
 * @author WD
 */
public abstract class BaseZip implements Zip {
	@Override
	public byte[] compress(byte[] b) {
		return compress(new ByteArrayInputStream(b)).toByteArray();
	}

	@Override
	public byte[] extract(byte[] b) {
		return extract(new ByteArrayInputStream(b)).toByteArray();
	}

	@Override
	public ByteArrayOutputStream compress(InputStream in) {
		return compress(in, new ByteArrayOutputStream());
	}

	@Override
	public ByteArrayOutputStream extract(InputStream in) {
		return extract(in, new ByteArrayOutputStream());
	}

	@Override
	public <E extends OutputStream> E compress(InputStream in, E out) {
		try {
			IOUtil.write(out, is(in));
		} catch (Exception e) {
			Logs.error(e);
		}
		return out;
	}

	@Override
	public <E extends OutputStream> E extract(InputStream in, E out) {
		try {
			IOUtil.write(os(out), in);
		} catch (Exception e) {
			Logs.error(e);
		}
		return out;
	}

	/**
	 * 根据子类实现生成解压流
	 * 
	 * @param in 字节输入流
	 * @return
	 */
	protected abstract InputStream is(InputStream in) throws Exception;

	/**
	 * 根据子类实现生成压缩流
	 * 
	 * @param os 字节输出流
	 * @return
	 */
	protected abstract OutputStream os(OutputStream os) throws Exception;
}
