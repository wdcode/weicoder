package com.weicoder.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.params.CommonParams;

import com.weicoder.common.util.StringUtil;

/**
 * 对文件进行一些处理。
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-01
 */
public final class FileUtil {
	// IO模式
	private final static boolean	IO;
	// AIO模式
	private final static boolean	AIO;

	static {
		IO = "io".equalsIgnoreCase(CommonParams.IO_MODE);
		AIO = "aio".equalsIgnoreCase(CommonParams.IO_MODE);
	}

	/**
	 * 创建目录
	 * @param path 目录路径
	 * @return true 成功 false 失败
	 */
	public static boolean mkdirs(String path) {
		return getFile(StringUtil.subStringLastEnd(path, File.separator)).mkdirs();
	}

	/**
	 * 文件是否存在
	 * @param name 文件名
	 * @return true 存在 false 不存在
	 */
	public static boolean exists(String name) {
		return getFile(name).exists();
	}

	/**
	 * 删除文件
	 * @param fileName 文件名
	 * @return true 成功 false 失败
	 */
	public static boolean delete(String fileName) {
		return delete(getFile(fileName));
	}

	/**
	 * 删除文件
	 * @param file 文件名
	 * @return true 成功 false 失败
	 */
	public static boolean delete(File file) {
		return file.delete();
	}

	/**
	 * 复制文件
	 * @param src 原文件
	 * @param target 目标文件
	 */
	public static boolean copy(String src, String target) {
		return write(target, getInputStream(src));
	}

	/**
	 * 复制文件
	 * @param src 原文件
	 * @param target 目标文件
	 */
	public static boolean copy(File src, File target) {
		return IOUtil.write(getOutputStream(target), getInputStream(src));
	}

	/**
	 * 读取文件 默认使用UTF-8编码
	 * @param fileName 要读取的文件
	 * @return String 读取出的字符串
	 */
	public static String readString(String fileName) {
		return readString(fileName, CommonParams.ENCODING);
	}

	/**
	 * 读取文件
	 * @param fileName 要读取的文件
	 * @param charsetName 编码格式
	 * @return 读取文件的内容
	 */
	public static String readString(String fileName, String charsetName) {
		return IOUtil.readString(getInputStream(fileName), charsetName);
	}

	/**
	 * 读取文件为字节数组 可指定开始读取位置
	 * @param fileName 文件名
	 * @param pos 偏移
	 * @return 字节数组
	 */
	public static byte[] read(String fileName, long pos) {
		if (IO) {
			// 获得随机读写文件
			try (RandomAccessFile file = getRandomAccessFile(fileName, "rw", pos);) {
				// 声明字节数组
				byte[] b = new byte[Conversion.toInt(file.length() - pos)];
				// 读取文件
				file.read(b);
				// 返回字节数组
				return b;
			} catch (IOException e) {}
		} else if (AIO) {
			// 获得文件通道
			try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(fileName));) {
				// 声明字节数组
				ByteBuffer buf = ByteBuffer.allocate(Conversion.toInt(channel.size() - pos));
				// 读取字节数组
				channel.read(buf, pos);
				// 返回字节数组
				return buf.array();
			} catch (Exception e) {}
		} else {
			// 获得文件通道
			try (FileChannel channel = FileChannel.open(Paths.get(fileName));) {
				// 声明字节数组
				ByteBuffer buf = ByteBuffer.allocate(Conversion.toInt(channel.size() - pos));
				// 读取字节数组
				channel.read(buf, pos);
				// 返回字节数组
				return buf.array();
			} catch (Exception e) {}
		}
		// 返回空字节数组
		return ArrayConstants.BYTES_EMPTY;
	}

	/**
	 * 读取文件
	 * @param fileName 要读取的文件
	 * @return 读取文件字节数组
	 */
	public static byte[] read(String fileName) {
		return IOUtil.read(getInputStream(fileName));
	}

	/**
	 * 读取文件
	 * @param file 要读取的文件
	 * @return 读取文件字节数组
	 */
	public static byte[] read(File file) {
		return IOUtil.read(getInputStream(file));
	}

	/**
	 * 把InputStream流中的内容保存到文件中
	 * @param fileName 文件名
	 * @param is 流
	 * @return true 成功 false 失败
	 */
	public static boolean write(String fileName, InputStream is) {
		return IOUtil.write(getOutputStream(fileName), is);
	}

	/**
	 * 把文件写指定路径中
	 * @param fileName 文件名
	 * @param file 文件
	 * @return true 成功 false 失败
	 */
	public static boolean write(String fileName, File file) {
		return IOUtil.write(getOutputStream(fileName), getInputStream(file));
	}

	/**
	 * 把文件写指定路径中
	 * @param fileName 文件名
	 * @param b 字节数组
	 * @return true 成功 false 失败
	 */
	public static boolean write(String fileName, byte[] b) {
		return write(fileName, b, true);
	}

	/**
	 * 把文件写指定路径中
	 * @param fileName 文件名
	 * @param b 字节数组
	 * @param append 是否追加
	 * @return true 成功 false 失败
	 */
	public static boolean write(String fileName, byte[] b, boolean append) {
		return IOUtil.write(FileUtil.getOutputStream(fileName, append), b);
	}

	/**
	 * 把字节写到文件中 可指定写入位置
	 * @param fileName 文件名
	 * @param b 字节数组
	 * @param pos 偏移
	 * @return true 成功 false 失败
	 */
	public static void write(String fileName, byte[] b, long pos) {
		if (IO) {
			// 获得随机读写文件
			try (RandomAccessFile file = getRandomAccessFile(fileName, "rw", pos);) {
				// 写字节数组
				file.write(b);
			} catch (IOException e) {}
		} else if (AIO) {
			// 获得文件通道
			try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(fileName));) {
				// 写字节数组
				channel.write(ByteBuffer.wrap(b), pos);
			} catch (Exception e) {}
		} else {
			// 获得文件通道
			try (FileChannel channel = FileChannel.open(Paths.get(fileName));) {
				// 写字节数组
				channel.write(ByteBuffer.wrap(b), pos);
			} catch (Exception e) {}
		}
	}

	/**
	 * 写文件 默认使用UTF-8编码
	 * @param text 写入的内容
	 * @param fileName 文件名
	 * @return true false
	 */
	public static boolean write(String fileName, String text) {
		return write(fileName, text, true);
	}

	/**
	 * 写文件 默认使用UTF-8编码
	 * @param text 写入的内容
	 * @param fileName 文件名
	 * @param append 是否追加
	 * @return true false
	 */
	public static boolean write(String fileName, String text, boolean append) {
		return write(fileName, text, CommonParams.ENCODING, append);
	}

	/**
	 * 写文件
	 * @param text 写入的内容
	 * @param fileName 文件名
	 * @param charsetName 编码格式
	 * @return true false
	 */
	public static boolean write(String fileName, String text, String charsetName) {
		return write(fileName, text, charsetName, true);
	}

	/**
	 * 写文件
	 * @param text 写入的内容
	 * @param fileName 文件名
	 * @param charsetName 编码格式
	 * @param append 是否追加
	 * @return true false
	 */
	public static boolean write(String fileName, String text, String charsetName, boolean append) {
		return IOUtil.write(getOutputStream(fileName, append), text, charsetName);
	}

	/**
	 * 获得文件
	 * @param fileName 文件名含路径
	 * @return File对象
	 */
	public static File getFile(String fileName) {
		return new File(fileName);
	}

	/**
	 * 获得文件
	 * @param fileName 文件名含路径
	 * @param mode 打开模式
	 * @param pos 偏移
	 * @return RandomAccessFile对象
	 */
	public static RandomAccessFile getRandomAccessFile(String fileName, String mode, long pos) {
		// 声明RandomAccessFile
		RandomAccessFile file = null;
		try {
			File f = getFile(fileName);
			// //如果文件不存在 创建
			if (!f.exists()) {
				mkdirs(fileName);
				f.createNewFile();
			}
			// 实例化随机读取文件实例
			file = new RandomAccessFile(f, mode);
			// 设置偏移量
			file.seek(pos);
		} catch (Exception e) {}
		// 返回RandomAccessFile
		return file;
	}

	/**
	 * 获得文件输入流 如果失败返回null
	 * @param fileName 文件名
	 * @return 输入流
	 */
	public static FileInputStream getInputStream(String fileName) {
		return getInputStream(getFile(fileName));
	}

	/**
	 * 获得文件输出流 如果失败返回null
	 * @param fileName 文件名
	 * @return 输出流
	 */
	public static FileOutputStream getOutputStream(String fileName) {
		return getOutputStream(getFile(fileName));
	}

	/**
	 * 获得文件输出流 如果失败返回null
	 * @param fileName 文件名
	 * @param append 是否追加
	 * @return 输出流
	 */
	public static FileOutputStream getOutputStream(String fileName, boolean append) {
		return getOutputStream(getFile(fileName), append);
	}

	/**
	 * 获得文件输入流 如果失败返回null
	 * @param file 文件
	 * @return 输入流
	 */
	public static FileInputStream getInputStream(File file) {
		try {
			return file.exists() ? new FileInputStream(file) : null;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获得文件输出流 如果失败返回null
	 * @param file 文件
	 * @return 输出流
	 */
	public static FileOutputStream getOutputStream(File file) {
		return getOutputStream(file, true);
	}

	/**
	 * 获得文件输出流 如果失败返回null
	 * @param file 文件
	 * @param append 是否追加
	 * @return 输出流
	 */
	public static FileOutputStream getOutputStream(File file, boolean append) {
		try {
			// //如果文件不存在 创建
			if (!file.exists()) {
				mkdirs(file.getPath());
				file.createNewFile();
			}
			return new FileOutputStream(file, append);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 私有构造禁止外部实例化
	 */
	private FileUtil() {}
}
