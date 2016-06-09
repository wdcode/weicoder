package com.weicoder.common.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.common.util.EmptyUtil;

/**
 * 堵塞IO操作
 * @author WD 
 */
public final class OIO extends BaseIO {
	/**
	 * 读取出输入流的所有字节
	 * @param in 输入流
	 * @param isClose 是否关闭流
	 * @return 字节数组
	 */
	public byte[] read(InputStream in, boolean isClose) {
		// 创建结果字节缓存
		ByteArrayOutputStream out = new ByteArrayOutputStream(CommonParams.IO_BUFFERSIZE * 10);
		try {
			// 声明用于缓存的字节数组
			byte[] buffer = new byte[CommonParams.IO_BUFFERSIZE];
			// 每次读取字节数组的长度
			int length = 0;
			// 循环读取流 如果读取长度大于0 继续循环
			while ((length = in.read(buffer)) > 0) {
				// 把字节数组添加到缓存里
				out.write(buffer, 0, length);
			}
		} catch (IOException e) {
			Logs.error(e); 
		} finally {
			// 关闭资源
			if (isClose) {
				CloseUtil.close(in);
			}
		}
		// 返回字节数组
		return out.toByteArray();
	}

	/**
	 * 把text写入到out中
	 * @param out 输出流
	 * @param in 输入流
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	public boolean write(OutputStream out, InputStream in, boolean isClose) {
		// 判断如果流为空 直接返回
		if (EmptyUtil.isEmpty(out) || EmptyUtil.isEmpty(in)) { return false; }
		try {
			// 声明字节数组 当缓存用
			byte[] buffer = new byte[CommonParams.IO_BUFFERSIZE];
			// 声明保存读取字符数量
			int num = 0;
			// 循环读取
			while ((num = in.read(buffer)) > 0) {
				// 输出到文件流
				out.write(buffer, 0, num);
			}
			// 刷新文件流 把流内所有内容更新到文件上
			out.flush();
			// 返回成功
			return true;
		} catch (IOException e) {
			Logs.error(e); 
		} finally {
			// 关闭资源
			if (isClose) {
				CloseUtil.close(out, in);
			}
		}
		// 返回失败
		return false;
	}
}
