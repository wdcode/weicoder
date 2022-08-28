package com.weicoder.common.io;
 
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels; 
 
import com.weicoder.common.binary.Buffer;
import com.weicoder.common.interfaces.Callback;
import com.weicoder.common.interfaces.CallbackVoid;

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
//			return U.Ch.read(rbc, isClose);
//		} catch (Exception e) {
//			return C.A.BYTES_EMPTY;
//		}
		return ChannelUtil.read(Channels.newChannel(in), isClose);
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
		return ChannelUtil.write(Channels.newChannel(out), in, isClose);
	}

	@Override
	public long write(OutputStream out, InputStream in, int buff, boolean isClose, Callback<Buffer, Buffer> call) {
		return ChannelUtil.write(Channels.newChannel(in), Channels.newChannel(out), buff, isClose, call);
	}

	@Override
	public long read(InputStream in, int buff, boolean isClose, CallbackVoid<Buffer> call) {
//		return ChannelUtil.write(Channels.newChannel(in), Channels.newChannel(ByteArrayOutputStream.nullOutputStream()),
//				buff, isClose, b -> {
//					call.callback(b);
//					return Buffer.empty();
//				});
		return ChannelUtil.write(Channels.newChannel(in), Channels.newChannel(new NullOutputStream()),
				buff, isClose, b -> {
					call.callback(b);
					return Buffer.empty();
				});
	}
}
