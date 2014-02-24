package com.weicoder.core.zip.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.io.IOUtil;
import com.weicoder.core.zip.Zip;

/**
 * ZIP压缩
 * @author WD
 * @since JDK7
 * @version 1.0 2012-09-15
 */
public final class ZipImpl implements Zip {
	/**
	 * 压缩数据
	 * @param b 字节数组
	 * @return 压缩后的字节数组
	 */
	public byte[] compress(byte[] b) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ZipOutputStream zip = new ZipOutputStream(baos)) {
			// 设置压缩实体
			zip.putNextEntry(new ZipEntry(StringConstants.EMPTY));
			// 把压缩后的字节数组写到输出流
			IOUtil.write(zip, b, false);
			// 完成压缩数据
			zip.finish();
			// 返回字节数组
			return baos.toByteArray();
		} catch (Exception e) {
			return b;
		}
	}

	/**
	 * 解压数据
	 * @param b 压缩字节
	 * @return 解压后数据
	 */
	public byte[] extract(byte[] b) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ZipInputStream zin = new ZipInputStream(new ByteArrayInputStream(b))) {
			// 循环解压缩
			while (zin.getNextEntry() != null) {
				baos.write(IOUtil.read(zin, false));
				baos.flush();
			}
			// 返回字节数组
			return baos.toByteArray();
		} catch (Exception e) {
			return b;
		}
	}
}
