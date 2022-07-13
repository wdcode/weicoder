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
import com.weicoder.common.constants.ArrayConstants;
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
		return read(ch, true);
	}

	/**
	 * 读取出通道的所有字节
	 * 
	 * @param ch      通道
	 * @param isClose 是否关闭流
	 * @return 字节数组
	 */
	public static byte[] read(ReadableByteChannel ch, boolean isClose) {
		// 创建结果字节缓存
		ByteArrayOutputStream out = new ByteArrayOutputStream(CommonParams.IO_BUFFERSIZE * 10);
		try {
			// 获得一个ByteBuffer
			ByteBuffer buffer = ByteBuffer.allocate(CommonParams.IO_BUFFERSIZE);
			// 声明保存读取字符数量
			int num = 0;
			// 循环读取
			while ((num = ch.read(buffer)) > 0) {
				// 添加
				out.write(buffer.hasArray() ? buffer.array() : ArrayConstants.BYTES_EMPTY, 0, num);
				// 清除缓存
				buffer.clear();
			}
		} catch (IOException e) {
			Logs.error(e);
		} finally {
			// 关闭资源
			if (isClose) {
				CloseUtil.close(ch);
			}
		}
		// 返回字节数组
		return out.toByteArray();
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param wbc 写入通道
	 * @param b   字节数组
	 * @return true false
	 */
	public static boolean write(WritableByteChannel wbc, byte[] b) {
		return write(wbc, b, true);
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param wbc     写入通道
	 * @param b       字节数组
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	public static boolean write(WritableByteChannel wbc, byte[] b, boolean isClose) {
		return write(wbc, new ByteArrayInputStream(b), isClose);
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param wbc 写入通道
	 * @param in  输入流
	 * @return true false
	 */
	public static boolean write(WritableByteChannel wbc, InputStream in) {
		return write(wbc, in, true);
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param wbc     写入通道
	 * @param in      输入流
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	public static boolean write(WritableByteChannel wbc, InputStream in, boolean isClose) {
		return write(wbc, Channels.newChannel(in), isClose);
	}

	/**
	 * 写入文件
	 * 
	 * @param file 要写入的文件
	 * @param src  原始数据
	 * @return 写入字节数
	 */
	public static int write(String file, ByteBuffer src) {
		try (FileOutputStream fos = U.F.getOutputStream(file, true); WritableByteChannel wbc = Channels.newChannel(fos)) {
			return wbc.write(src);
		} catch (Exception e) {
			Logs.error(e);
			return 0;
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
	public static boolean write(WritableByteChannel wbc, ReadableByteChannel rbc, boolean isClose) {
		// 如果输出或则输入流为空
		if (wbc == null || rbc == null) {
			return false;
		}
		try {
			// 获得一个
			ByteBuffer buffer = ByteBuffer.allocate(CommonParams.IO_BUFFERSIZE);
			// 声明保存读取字符数量
			int num = 0;

			// 循环读写
			while ((num = rbc.read(buffer)) > 0) {
				// 写文件
				wbc.write(buffer.hasArray() ? ByteBuffer.wrap(buffer.array(), 0, num) : ByteBuffer.wrap(ArrayConstants.BYTES_EMPTY));
				// 清空缓存
				buffer.clear();
			}
			// 返回成功
			return true;
		} catch (IOException e) {
			Logs.error(e);
		} finally {
			// 关闭资源
			if (isClose) {
				CloseUtil.close(wbc, rbc);
			}
		}
		// 返回失败
		return false;
	}
}
