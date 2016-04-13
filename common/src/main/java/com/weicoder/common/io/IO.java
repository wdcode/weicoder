package com.weicoder.common.io;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * IO方法接口 内部使用
 * @author WD 
 * @version 1.0 
 */
interface IO {
	/**
	 * 读取InputStream内容成为字符串
	 * @param in 输入流
	 * @return 读取的字符串 失败返回""
	 */
	String readString(InputStream in);

	/**
	 * 读取InputStream内容成为字符串
	 * @param in 输入流
	 * @param charsetName 编码格式
	 * @return 读取的字符串 失败返回""
	 */
	String readString(InputStream in, String charsetName);

	/**
	 * 读取出输入流的所有字节
	 * @param in 输入流
	 * @return 字节数组
	 */
	byte[] read(InputStream in);

	/**
	 * 读取出输入流的所有字节
	 * @param in 输入流
	 * @param isClose 是否关闭流
	 * @return 字节数组
	 */
	byte[] read(InputStream in, boolean isClose);

	/**
	 * 把text写入到os中
	 * @param out 输出流
	 * @param text 输入的字符串
	 * @return true false
	 */
	boolean write(OutputStream out, String text);

	/**
	 * 把text写入到os中
	 * @param out 输出流
	 * @param text 输入的字符串
	 * @param charsetName 编码格式
	 * @return true false
	 */
	boolean write(OutputStream out, String text, String charsetName);

	/**
	 * 把字节数组写入到out中
	 * @param out 输出流
	 * @param b 字节数组
	 * @return true false
	 */
	boolean write(OutputStream out, byte[] b);

	/**
	 * 把字节数组写入到os中
	 * @param out 输出流
	 * @param in 输入流
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	boolean write(OutputStream out, byte[] b, boolean isClose);

	/**
	 * 把输入流写入到os中
	 * @param out 输出流
	 * @param in 输入流
	 * @return true false
	 */
	boolean write(OutputStream out, InputStream in);

	/**
	 * 把text写入到os中
	 * @param out 输出流
	 * @param in 输入流
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	boolean write(OutputStream out, InputStream in, boolean isClose);

	/**
	 * 读取InputStream内容成为字符串
	 * @param in 输入流
	 * @param charsetName 编码格式
	 * @param isClose 是否关闭流
	 * @return 读取的字符串
	 */
	String readString(InputStream in, String charsetName, boolean isClose);

	/**
	 * 把text写入到os中
	 * @param out 输出流
	 * @param text 输入的字符串
	 * @param charsetName 编码格式
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	boolean write(OutputStream out, String text, String charsetName, boolean isClose);
}
