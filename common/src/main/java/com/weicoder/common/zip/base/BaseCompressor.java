package com.weicoder.common.zip.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream; 
import java.io.InputStream;
import java.io.OutputStream;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.io.IOUtil; 

/**
 * apache 基础 compress 包实现
 * 
 * @author wudi
 */
public abstract class BaseCompressor extends BaseZip {

	@Override
	protected byte[] compress0(byte[] b) throws Exception {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); OutputStream os = os(bos)) {
			// 写入压缩流
			IOUtil.write(os, b);
			os.flush();
			// 返回结果
			return bos.toByteArray();
		} catch (Exception e) {
			return ArrayConstants.BYTES_EMPTY;
		}
	}

	@Override
	protected byte[] extract0(byte[] b) throws Exception {
		try (InputStream is = is(new ByteArrayInputStream(b))) {
			return IOUtil.read(is);
		} catch (Exception e) {
			return ArrayConstants.BYTES_EMPTY;
		}
	}

	/**
	 * 根据子类实现生成解压流
	 * 
	 * @param  bis 字节输入流
	 * @return
	 */
	protected abstract InputStream is(ByteArrayInputStream bis) throws Exception;

	/**
	 * 根据子类实现生成压缩流
	 * 
	 * @param  bos 字节输出流
	 * @return
	 */
	protected abstract OutputStream os(ByteArrayOutputStream bos) throws Exception;
}