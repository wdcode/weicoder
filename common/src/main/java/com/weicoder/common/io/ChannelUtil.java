package com.weicoder.common.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import com.weicoder.common.U;
import com.weicoder.common.binary.Buffer;
import com.weicoder.common.interfaces.Callback;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.CloseUtil;

/**
 * nio通道操作
 * 
 * @author WD
 */
public class ChannelUtil {
	/**
	 * 读取出通道的所有字节
	 * 
	 * @param ch 通道
	 * @return 字节数组
	 */
	public static byte[] read(ReadableByteChannel ch) {
		return read(ch, CommonParams.IO_CLOSE);
	}

	/**
	 * 读取出通道的所有字节
	 * 
	 * @param ch      通道
	 * @param isClose 是否关闭流
	 * @return 字节数组
	 */
	public static byte[] read(ReadableByteChannel ch, boolean isClose) {
		// 创建结果字节流
		ByteArrayOutputStream out = new ByteArrayOutputStream(CommonParams.IO_BUFFERSIZE * 10);
		// 写入字节流
		write(ch, Channels.newChannel(out), isClose);
		// 返回字节流中全部数组
		return out.toByteArray();
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param wbc 写入通道
	 * @param b   字节数组
	 * @return true false
	 */
	public static long write(WritableByteChannel wbc, byte[] b) {
		return write(wbc, b, CommonParams.IO_CLOSE);
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param wbc     写入通道
	 * @param b       字节数组
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	public static long write(WritableByteChannel wbc, byte[] b, boolean isClose) {
		return write(wbc, new ByteArrayInputStream(b), isClose);
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param wbc 写入通道
	 * @param in  输入流
	 * @return true false
	 */
	public static long write(WritableByteChannel wbc, InputStream in) {
		return write(wbc, in, CommonParams.IO_CLOSE);
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param wbc     写入通道
	 * @param in      输入流
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	public static long write(WritableByteChannel wbc, InputStream in, boolean isClose) {
		return write(Channels.newChannel(in), wbc, isClose);
	}

	/**
	 * 写入文件
	 * 
	 * @param file 要写入的文件
	 * @param src  原始数据
	 * @return 写入字节数
	 */
	public static int write(String file, ByteBuffer src) {
		try (FileOutputStream fos = U.F.getOutputStream(file, true);
				WritableByteChannel wbc = Channels.newChannel(fos)) {
			return wbc.write(src);
		} catch (Exception e) {
			Logs.error(e);
			return -1;
		}
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param wbc     写入通道
	 * @param rbc     输入流
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	public static long write(ReadableByteChannel rbc, WritableByteChannel wbc, boolean isClose) {
		return write(rbc, wbc, CommonParams.IO_BUFFERSIZE, isClose, r -> r);
	}

	/**
	 * 读取并写入数据 默认不关闭流
	 * 
	 * @param rbc  读取通道
	 * @param wbc  写入通道
	 * @param buff 每次读取缓存熟
	 * @param call 回调
	 * @return 读取流总数
	 */
	public static long write(ReadableByteChannel rbc, WritableByteChannel wbc, int buff,
			Callback<Buffer, Buffer> call) {
		return write(rbc, wbc, buff, false, call);
	}

	/**
	 * 读取并写入数据
	 * 
	 * @param rbc     读取通道
	 * @param wbc     写入通道
	 * @param buff    每次读取缓存数
	 * @param isClose 是否关闭流
	 * @param call    回调
	 * @return 读取流总数
	 */
	public static long write(ReadableByteChannel rbc, WritableByteChannel wbc, int buff, boolean isClose,
			Callback<Buffer, Buffer> call) {
		// 如果输出或则输入流为空
		if (wbc == null || rbc == null) {
			return -1;
		}
		// 声明保存读取字符数量
		long sum = 0;
		try {
			// 获得一个
			ByteBuffer buffer = ByteBuffer.allocate(buff);
			// 每次读取的长度
			int num = 0;
			// 循环读写
			while ((num = rbc.read(buffer)) > 0) {
				// 回调函数并获得写入缓存
				Buffer buf = call.callback(Buffer.wrap(buffer.array(), 0, num));
				// 写文件
				if (buf.length() > 0)
					wbc.write(ByteBuffer.wrap(buf.array()));
				// 清空缓存
				buffer.clear();
				// 累加读取流长度
				sum += num;
			}
		} catch (IOException e) {
			Logs.error(e);
		} finally {
			// 关闭资源
			if (isClose)
				CloseUtil.close(wbc, rbc);
		}
		// 返回总读取字节数
		return sum;
	}
}
