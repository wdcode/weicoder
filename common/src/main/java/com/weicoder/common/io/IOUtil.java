package com.weicoder.common.io;

import java.io.InputStream;
import java.io.OutputStream;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.params.CommonParams;

/**
 * IO流操作
 * @author WD
 */
public final class IOUtil {
	// IO接口
	private final static IO IO = "io".equalsIgnoreCase(CommonParams.IO_MODE) ? new OIO()
			: new NIO();

	/**
	 * 读取InputStream内容成为字符串 默认使用UTF-8
	 * @param in 输入流
	 * @return 读取的字符串
	 */
	public static String readString(InputStream in) {
		return in == null ? StringConstants.EMPTY : IO.readString(in);
	}

	/**
	 * 读取InputStream内容成为字符串
	 * @param in 输入流
	 * @param charsetName 编码格式
	 * @return 读取的字符串
	 */
	public static String readString(InputStream in, String charsetName) {
		return in == null ? StringConstants.EMPTY : IO.readString(in, charsetName);
	}

	/**
	 * 读取InputStream内容成为字符串
	 * @param in 输入流
	 * @param charsetName 编码格式
	 * @param isClose 是否关闭流
	 * @return 读取的字符串
	 */
	public static String readString(InputStream in, String charsetName, boolean isClose) {
		return in == null ? StringConstants.EMPTY : IO.readString(in, charsetName, isClose);
	}

	/**
	 * 读取出输入流的所有字节
	 * @param in 输入流
	 * @return 字节数组
	 */
	public static byte[] read(InputStream in) {
		return in == null ? ArrayConstants.BYTES_EMPTY : IO.read(in);
	}

	/**
	 * 读取出输入流的所有字节
	 * @param in 输入流
	 * @param isClose 是否关闭流
	 * @return 字节数组
	 */
	public static byte[] read(InputStream in, boolean isClose) {
		return in == null ? ArrayConstants.BYTES_EMPTY : IO.read(in, isClose);
	}

	/**
	 * 把text写入到os中 默认使用UTF-8编码
	 * @param out 输出流
	 * @param text 输入的字符串
	 * @return boolean
	 */
	public static boolean write(OutputStream out, String text) {
		return out == null ? false : IO.write(out, text);
	}

	/**
	 * 把text写入到os中
	 * @param out 输出流
	 * @param text 输入的字符串
	 * @param charsetName 编码格式
	 * @return true false
	 */
	public static boolean write(OutputStream out, String text, String charsetName) {
		return out == null ? false : IO.write(out, text, charsetName);
	}

	/**
	 * 把text写入到os中
	 * @param out 输出流
	 * @param text 输入的字符串
	 * @param charsetName 编码格式
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	public static boolean write(OutputStream out, String text, String charsetName,
			boolean isClose) {
		return out == null ? false : IO.write(out, text, charsetName, isClose);
	}

	/**
	 * 把字节数组写入到流中
	 * @param out 输出流
	 * @param b 字节数组
	 * @return 是否成功
	 */
	public static boolean write(OutputStream out, byte[] b) {
		return out == null ? false : IO.write(out, b);
	}

	/**
	 * 把字节数组写入到流中
	 * @param out 输出流
	 * @param b 字节数组
	 * @param isClose 是否关闭流
	 * @return 是否成功
	 */
	public static boolean write(OutputStream out, byte[] b, boolean isClose) {
		return out == null ? false : IO.write(out, b, isClose);
	}

	/**
	 * 把text写入到out中
	 * @param out 输出流
	 * @param in 输入流
	 * @return true false
	 */
	public static boolean write(OutputStream out, InputStream in) {
		return out == null ? false : IO.write(out, in);
	}

	/**
	 * 把text写入到out中
	 * @param out 输出流
	 * @param in 输入流
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	public static boolean write(OutputStream out, InputStream in, boolean isClose) {
		return out == null ? false : IO.write(out, in, isClose);
	}

	private IOUtil() {}
}
