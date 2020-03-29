package com.weicoder.common.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

/**
 * 非堵塞IO操作
 * @author WD  
 */
public final class NIO extends BaseIO {
	/**
	 * 读取出输入流的所有字节
	 * @param in 输入流
	 * @param isClose 是否关闭流
	 * @return 字节数组 
	 */
	public byte[] read(InputStream in, boolean isClose) {
		return ChannelUtil.read(Channels.newChannel(in), isClose);
	}

	/**
	 * 把text写入到os中
	 * @param out 输出流
	 * @param in 输入流
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	public boolean write(OutputStream out, InputStream in, boolean isClose) {
		return ChannelUtil.write(Channels.newChannel(out), in, isClose);
	}
}
