package com.weicoder.common.io;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousByteChannel;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.CloseUtil;
import com.weicoder.common.U;

/**
 * aio读写字节流
 * 
 * @author wudi
 */
public class AsynChannelUtil {
	/**
	 * 读取出通道的所有字节
	 * 
	 * @param  asc 通道
	 * @return     字节数组
	 */
	public static byte[] read(AsynchronousByteChannel asc) {
		return read(asc, CommonParams.IO_CLOSE);
	}

	/**
	 * 读取出通道的所有字节
	 * 
	 * @param  asc     通道
	 * @param  isClose 是否关闭流
	 * @return         字节数组
	 */
	public static byte[] read(AsynchronousByteChannel asc, boolean isClose) {
		if (asc == null)
			return ArrayConstants.BYTES_EMPTY;
		// 创建结果字节缓存
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			// 获得一个ByteBuffer
			ByteBuffer buffer = ByteBuffer.allocate(CommonParams.IO_BUFFERSIZE);
			// 声明保存读取字符数量
			int num = 0;
			// 循环读取
			while ((num = asc.read(buffer).get()) > 0) {
				// 添加
				out.write(buffer.hasArray() ? buffer.array() : ArrayConstants.BYTES_EMPTY, 0, num);
				// 清除缓存
				buffer.clear();
			}
			// 返回字节数组
			return out.toByteArray();
		} catch (Exception e) {
			Logs.error(e);
			return ArrayConstants.BYTES_EMPTY;
		} finally {
			// 关闭资源
			if (isClose)
				CloseUtil.close(asc);
		}
	}

	/**
	 * 把字节流写入到aio中
	 * 
	 * @param  asc 写入通道
	 * @param  b   字节数组
	 * @return     true false
	 */
	public static int write(AsynchronousByteChannel asc, byte[] b) {
		return write(asc, b, CommonParams.IO_CLOSE);
	}

	/**
	 * 把字节流写入到aio中
	 * 
	 * @param  asc     写入通道
	 * @param  b       字节数组
	 * @param  isClose 是否关闭流
	 * @return         true false
	 */
	public static int write(AsynchronousByteChannel asc, byte[] b, boolean isClose) {
		// 如果输出或则输入流为空
		if (asc == null || U.E.isEmpty(b)) {
			return -1;
		}
		try {
			// 返回成功
			return asc.write(ByteBuffer.wrap(b)).get();
		} catch (Exception e) {
			Logs.error(e);
			return -1;
		} finally {
			// 关闭资源
			if (isClose)
				CloseUtil.close(asc);
		}
	}
}
