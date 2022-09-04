package com.weicoder.common.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.weicoder.common.binary.Buffer;
import com.weicoder.common.interfaces.Callback;
import com.weicoder.common.interfaces.CallbackVoid;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.statics.Closes;

/**
 * 堵塞IO操作
 * 
 * @author WD
 */
public final class BIO implements IO {
	/**
	 * 读取出输入流的所有字节
	 * 
	 * @param in      输入流
	 * @param isClose 是否关闭流
	 * @return 字节数组
	 */
	public byte[] read(InputStream in, boolean isClose) {
		// 创建结果字节流
		ByteArrayOutputStream out = new ByteArrayOutputStream(CommonParams.IO_BUFFERSIZE * 10);
		// 写入字节流
		write(out, in, isClose);
		// 返回字节流中全部数组
		return out.toByteArray();
	}

	/**
	 * 把text写入到out中
	 * 
	 * @param out     输出流
	 * @param in      输入流
	 * @param isClose 是否关闭流
	 * @return 写入成功字节数
	 */
	public long write(OutputStream out, InputStream in, boolean isClose) {
		return write(out, in, CommonParams.IO_BUFFERSIZE, isClose, r -> r);
	}

	@Override
	public long write(OutputStream out, InputStream in, int buff, boolean isClose, Callback<Buffer, Buffer> call) {
		// 判断如果流为空 直接返回
		if (out == null || in == null)
			return -1;
		// 返回结果
		long sum = 0;
		try {
			// 声明字节数组 当缓存用
			byte[] buffer = new byte[buff];
			// 声明保存读取字符数量
			int num = 0;
			// 循环读取
			while ((num = in.read(buffer)) > 0) {
				// 回调
				Buffer buf = call.callback(Buffer.wrap(buffer));
				// 写入流
				out.write(buf.array(), 0, buf.length());
				// 刷新文件流 把流内所有内容更新到文件上
				out.flush();
				// 累加长度
				sum += num;
				// 重新声明字节数组
				buffer = new byte[buff];
			}
		} catch (IOException e) {
			Logs.error(e);
		} finally {
			// 关闭资源
			if (isClose)
				Closes.close(out, in);
		}
		// 返回结果
		return sum;
	}

	@Override
	public long read(InputStream in, int buff, boolean isClose, CallbackVoid<Buffer> call) {
		return write(ByteArrayOutputStream.nullOutputStream(), in, buff, isClose, b -> {
			call.callback(b);
			return Buffer.empty();
		});
//		// 判断如果流为空 直接返回
//		if (in == null)
//			return -1;
//		// 返回结果
//		long sum = 0;
//		try {
//			// 声明字节数组 当缓存用
//			byte[] buffer = new byte[buff];
//			// 声明保存读取字符数量
//			int num = 0;
//			// 循环读取
//			while ((num = in.read(buffer)) > 0) {
//				// 回调
//				call.callback(Buffer.wrap(buffer));
//				// 累加长度
//				sum += num;
//				// 重新声明字节数组
//				buffer = new byte[buff];
//			}
//		} catch (IOException e) {
//			Logs.error(e);
//		} finally {
//			// 关闭资源
//			if (isClose)
//				CloseUtil.close(in);
//		}
//		// 返回结果
//		return sum;
	}
}
