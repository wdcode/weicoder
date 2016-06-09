package com.weicoder.common.io;

import java.io.InputStream;
import java.io.OutputStream;

import com.weicoder.common.params.CommonParams;

/**
 * IO流操作
 * @author WD  
 */
public final class IOUtil {
	// IO接口
	private final static IO IO = "io".equalsIgnoreCase(CommonParams.IO_MODE) ? new OIO() : new NIO();

	/**
	 * 读取InputStream内容成为字符串 默认使用UTF-8
	 * @param in 输入流
	 * @return 读取的字符串
	 */
	public static String readString(InputStream in) {
		return IO.readString(in);
	}

	/**
	 * 读取InputStream内容成为字符串
	 * @param in 输入流
	 * @param charsetName 编码格式
	 * @return 读取的字符串
	 */
	public static String readString(InputStream in, String charsetName) {
		return IO.readString(in, charsetName);
	}

	/**
	 * 读取InputStream内容成为字符串
	 * @param in 输入流
	 * @param charsetName 编码格式
	 * @param isClose 是否关闭流
	 * @return 读取的字符串
	 */
	public static String readString(InputStream in, String charsetName, boolean isClose) {
		return IO.readString(in, charsetName, isClose);
	}

	/**
	 * 读取出输入流的所有字节
	 * @param in 输入流
	 * @return 字节数组
	 */
	public static byte[] read(InputStream in) {
		return IO.read(in);
	}

	/**
	 * 读取出输入流的所有字节
	 * @param in 输入流
	 * @param isClose 是否关闭流
	 * @return 字节数组
	 */
	public static byte[] read(InputStream in, boolean isClose) {
		return IO.read(in, isClose);
	}

	/**
	 * 把text写入到os中 默认使用UTF-8编码
	 * @param os 输出流
	 * @param text 输入的字符串
	 */
	public static boolean write(OutputStream out, String text) {
		return IO.write(out, text);
	}

	/**
	 * 把text写入到os中
	 * @param out 输出流
	 * @param text 输入的字符串
	 * @param charsetName 编码格式
	 * @return true false
	 */
	public static boolean write(OutputStream out, String text, String charsetName) {
		return IO.write(out, text, charsetName);
	}

	/**
	 * 把text写入到os中
	 * @param out 输出流
	 * @param text 输入的字符串
	 * @param charsetName 编码格式
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	public static boolean write(OutputStream out, String text, String charsetName, boolean isClose) {
		return IO.write(out, text, charsetName, isClose);
	}

	/**
	 * 把字节数组写入到流中
	 * @param out 输出流
	 * @param b 字节数组
	 * @return 是否成功
	 */
	public static boolean write(OutputStream out, byte[] b) {
		return IO.write(out, b);
	}

	/**
	 * 把字节数组写入到流中
	 * @param out 输出流
	 * @param b 字节数组
	 * @param isClose 是否关闭流
	 * @return 是否成功
	 */
	public static boolean write(OutputStream out, byte[] b, boolean isClose) {
		return IO.write(out, b, isClose);
	}

	/**
	 * 把text写入到out中
	 * @param out 输出流
	 * @param in 输入流
	 * @return true false
	 */
	public static boolean write(OutputStream out, InputStream in) {
		return IO.write(out, in);
	}

	/**
	 * 把text写入到out中
	 * @param out 输出流
	 * @param in 输入流
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	public static boolean write(OutputStream out, InputStream in, boolean isClose) {
		return IO.write(out, in, isClose);
	}

	private IOUtil() {}
}
