package com.weicoder.common.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.StringUtil;

/**
 * 基础IO操作
 * @author WD 
 *   
 */
public abstract class BaseIO implements IO {
	/**
	 * 读取InputStream内容成为字符串
	 * @param in 输入流
	 * @return 读取的字符串 失败返回""
	 */
	public String readString(InputStream in) {
		return readString(in, CommonParams.ENCODING);
	}

	/**
	 * 读取InputStream内容成为字符串
	 * @param in 输入流
	 * @param charsetName 编码格式
	 * @return 读取的字符串 失败返回""
	 */
	public String readString(InputStream in, String charsetName) {
		return readString(in, charsetName, true);
	}

	/**
	 * 读取出输入流的所有字节
	 * @param in 输入流
	 * @return 字节数组
	 */
	public byte[] read(InputStream in) {
		return read(in, true);
	}

	/**
	 * 把text写入到os中
	 * @param out 输出流
	 * @param text 输入的字符串
	 * @return true false
	 */
	public boolean write(OutputStream out, String text) {
		return write(out, StringUtil.toBytes(text));
	}

	/**
	 * 把text写入到os中
	 * @param out 输出流
	 * @param text 输入的字符串
	 * @param charsetName 编码格式
	 * @return true false
	 */
	public boolean write(OutputStream out, String text, String charsetName) {
		return write(out, text, charsetName, true);
	}

	/**
	 * 把输入流写入到os中
	 * @param out 输出流
	 * @param in 输入流
	 * @return true false
	 */
	public boolean write(OutputStream out, InputStream in) {
		return write(out, in, true);
	}

	/**
	 * 读取InputStream内容成为字符串
	 * @param in 输入流
	 * @param charsetName 编码格式
	 * @param isClose 是否关闭流
	 * @return 读取的字符串
	 */
	public String readString(InputStream in, String charsetName, boolean isClose) {
		return StringUtil.toString(read(in, isClose), charsetName);
	}

	/**
	 * 把text写入到os中
	 * @param out 输出流
	 * @param text 输入的字符串
	 * @param charsetName 编码格式
	 * @param isClose 是否关闭流
	 * @return true false
	 */
	public boolean write(OutputStream out, String text, String charsetName, boolean isClose) {
		return write(out, StringUtil.toBytes(text, charsetName), isClose);
	}

	/**
	 * 把字节数组写入到流中
	 * @param out 输出流
	 * @param b 字节数组
	 * @return 是否成功
	 */
	public boolean write(OutputStream out, byte[] b) {
		return write(out, b, true);
	}

	/**
	 * 把字节数组写入到流中
	 * @param out 输出流
	 * @param b 字节数组
	 * @param isClose 是否关闭流
	 * @return 是否成功
	 */
	public boolean write(OutputStream out, byte[] b, boolean isClose) {
		return write(out, new ByteArrayInputStream(b), isClose);
	}
}
