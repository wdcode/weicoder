package com.weicoder.common.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;

import com.weicoder.common.binary.Buffer;
import com.weicoder.common.interfaces.Calls;  

/**
 * 非堵塞IO操作
 * 
 * @author WD
 */
public final class NIO implements IO {
	/**
	 * 读取出输入流的所有字节
	 * 
	 * @param in      输入流
	 * @param isClose 是否关闭流
	 * @return 字节数组
	 */
	public byte[] read(InputStream in, boolean isClose) {
//		try (ReadableByteChannel rbc = Channels.newChannel(in)) {
//			return I.C.read(rbc, isClose);
//		} catch (Exception e) {
//			return C.A.BYTES_EMPTY;
//		}
		return I.C.read(Channels.newChannel(in), isClose);
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param out     输出流
	 * @param in      输入流
	 * @param isClose 是否关闭流
	 * @return 写入成功字节数
	 */
	public long write(OutputStream out, InputStream in, boolean isClose) {
		return I.C.write(Channels.newChannel(out), in, isClose);
	}

	@Override
	public long write(OutputStream out, InputStream in, int buff, boolean isClose, Calls.EoR<Buffer, Buffer> call) {
		return I.C.write(Channels.newChannel(in), Channels.newChannel(out), buff, isClose, call);
	}

	@Override
	public long read(InputStream in, int buff, boolean isClose, Calls.EoV<Buffer> call) {
		return I.C.write(Channels.newChannel(in), Channels.newChannel(ByteArrayOutputStream.nullOutputStream()),
				buff, isClose, b -> {
					call.call(b);
					return Buffer.empty();
				});
	}
}
