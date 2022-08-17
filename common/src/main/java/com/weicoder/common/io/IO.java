package com.weicoder.common.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.weicoder.common.binary.Buffer;
import com.weicoder.common.interfaces.Callback;
import com.weicoder.common.interfaces.CallbackVoid;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.StringUtil;

/**
 * IO方法接口 内部使用
 * 
 * @author WD
 */
interface IO {
	/**
	 * 读取InputStream内容成为字符串
	 * 
	 * @param in 输入流
	 * @return 读取的字符串 失败返回""
	 */
	default String readString(InputStream in) {
		return readString(in, CommonParams.ENCODING);
	}

	/**
	 * 读取InputStream内容成为字符串
	 * 
	 * @param in          输入流
	 * @param charsetName 编码格式
	 * @return 读取的字符串 失败返回""
	 */
	default String readString(InputStream in, String charsetName) {
		return readString(in, charsetName, CommonParams.IO_CLOSE);
	}

	/**
	 * 读取出输入流的所有字节
	 * 
	 * @param in 输入流
	 * @return 字节数组
	 */
	default byte[] read(InputStream in) {
		return read(in, CommonParams.IO_CLOSE);
	}

	/**
	 * 读取出输入流的所有字节
	 * 
	 * @param in      输入流
	 * @param isClose 是否关闭流
	 * @return 字节数组
	 */
	byte[] read(InputStream in, boolean isClose);

	/**
	 * 读取出输入流的所有字节
	 * 
	 * @param in      输入流
	 * @param isClose 是否关闭流
	 * @return 字节数组
	 */
	long read(InputStream in, int buff, boolean isClose, CallbackVoid<Buffer> call);

	/**
	 * 把text写入到os中
	 * 
	 * @param out  输出流
	 * @param text 输入的字符串
	 * @return true false
	 */
	default boolean write(OutputStream out, String text) {
		return write(out, StringUtil.toBytes(text));
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param out         输出流
	 * @param text        输入的字符串
	 * @param charsetName 编码格式
	 * @return true false
	 */
	default boolean write(OutputStream out, String text, String charsetName) {
		return write(out, text, charsetName, CommonParams.IO_CLOSE);
	}

	/**
	 * 把字节数组写入到out中
	 * 
	 * @param out 输出流
	 * @param b   字节数组
	 * @return true false
	 */
	default boolean write(OutputStream out, byte[] b) {
		return write(out, b, CommonParams.IO_CLOSE);
	}

	/**
	 * 把字节数组写入到os中
	 * 
	 * @param out     输出流
	 * @param b       字节数组
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	default boolean write(OutputStream out, byte[] b, boolean isClose) {
		return write(out, new ByteArrayInputStream(b), isClose);
	}

	/**
	 * 把输入流写入到os中
	 * 
	 * @param out 输出流
	 * @param in  输入流
	 * @return true false
	 */
	default boolean write(OutputStream out, InputStream in) {
		return write(out, in, CommonParams.IO_CLOSE);
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param out     输出流
	 * @param in      输入流
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	boolean write(OutputStream out, InputStream in, boolean isClose);

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
	long write(OutputStream out, InputStream in, int buff, boolean isClose, Callback<Buffer, Buffer> call);

	/**
	 * 读取InputStream内容成为字符串
	 * 
	 * @param in          输入流
	 * @param charsetName 编码格式
	 * @param isClose     是否关闭流
	 * @return 读取的字符串
	 */
	default String readString(InputStream in, String charsetName, boolean isClose) {
		return StringUtil.toString(read(in, isClose), charsetName);
	}

	/**
	 * 把text写入到os中
	 * 
	 * @param out         输出流
	 * @param text        输入的字符串
	 * @param charsetName 编码格式
	 * @param isClose     是否关闭流
	 * @return true false
	 */
	default boolean write(OutputStream out, String text, String charsetName, boolean isClose) {
		return write(out, StringUtil.toBytes(text, charsetName), isClose);
	}
}
