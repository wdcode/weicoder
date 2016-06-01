package com.weicoder.common.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.zip.base.BaseZip;

/**
 * ZIP压缩
 * @author WD 
 *  
 */
public final class ZipImpl extends BaseZip {

	@Override
	protected byte[] compress0(byte[] b) throws Exception {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ZipOutputStream zip = new ZipOutputStream(baos)) {
			// 设置压缩实体
			zip.putNextEntry(new ZipEntry(StringConstants.EMPTY));
			// 把压缩后的字节数组写到输出流
			IOUtil.write(zip, b, false);
			// 完成压缩数据
			zip.finish();
			// 返回字节数组
			return baos.toByteArray();
		}
	}

	@Override
	protected byte[] extract0(byte[] b) throws Exception {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ZipInputStream zin = new ZipInputStream(new ByteArrayInputStream(b))) {
			// 循环解压缩
			while (zin.getNextEntry() != null) {
				baos.write(IOUtil.read(zin, false));
				baos.flush();
			}
			// 返回字节数组
			return baos.toByteArray();
		}
	}
}
