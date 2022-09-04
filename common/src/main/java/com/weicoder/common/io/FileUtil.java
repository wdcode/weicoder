package com.weicoder.common.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream; 
import java.io.RandomAccessFile;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.weicoder.common.binary.Buffer;
import com.weicoder.common.constants.C;
import com.weicoder.common.interfaces.Callback;
import com.weicoder.common.interfaces.CallbackVoid;
import com.weicoder.common.lang.W;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;

import com.weicoder.common.util.StringUtil;
import com.weicoder.common.util.U;

/**
 * 对文件进行一些处理。
 * 
 * @author WD
 */
public class FileUtil {
	// IO模式
	private final static boolean	IO	= "io".equalsIgnoreCase(CommonParams.IO_MODE);
	// AIO模式
	private final static boolean	AIO	= "aio".equalsIgnoreCase(CommonParams.IO_MODE);

	/**
	 * 创建目录
	 * 
	 * @param path 目录路径
	 * @return true 成功 false 失败
	 */
	public static boolean mkdirs(String path) {
		return newFile(StringUtil.subStringLastEnd(path, File.separator)).mkdirs();
	}

	/**
	 * 文件是否存在
	 * 
	 * @param name 文件名
	 * @return true 存在 false 不存在
	 */
	public static boolean exists(String name) {
		return newFile(name).exists();
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName 文件名
	 * @return true 成功 false 失败
	 */
	public static boolean delete(String fileName) {
		return delete(newFile(fileName));
	}

	/**
	 * 删除文件
	 * 
	 * @param file 文件名
	 * @return true 成功 false 失败
	 */
	public static boolean delete(File file) {
		return file.delete();
	}

	/**
	 * 复制文件
	 * 
	 * @param src    原文件
	 * @param target 目标文件
	 * @return 写入成功字节数
	 */
	public static long copy(String src, String target) {
		return write(target, getInputStream(src));
	}

	/**
	 * 复制文件
	 * 
	 * @param src    原文件
	 * @param target 目标文件
	 * @return 写入成功字节数
	 */
	public static long copy(File src, File target) {
		return U.I.write(getOutputStream(target), getInputStream(src));
	}

	/**
	 * 读取文件 默认使用UTF-8编码
	 * 
	 * @param fileName 要读取的文件
	 * @return String 读取出的字符串
	 */
	public static String readString(String fileName) {
		return readString(fileName, CommonParams.ENCODING);
//		try {
//			return Files.readString(Paths.get(fileName));
//		} catch (IOException e) {
//			return C.S.EMPTY;
//		}
	}

	/**
	 * 读取文件
	 * 
	 * @param fileName    要读取的文件
	 * @param charsetName 编码格式
	 * @return 读取文件的内容
	 */
	public static String readString(String fileName, String charsetName) {
		return IOUtil.readString(getInputStream(fileName), charsetName);
//		try {
//			return Files.readString(Paths.get(fileName), Charset.forName(charsetName));
//		} catch (IOException e) {
//			return C.S.EMPTY;
//		}
	}

	/**
	 * 读取文件为字节数组 可指定开始读取位置
	 * 
	 * @param fileName
	 * @param pos
	 * @param len
	 * @return
	 */
	public static byte[] read(String fileName, long pos, long len) {
		if (IO)
			// 获得随机读写文件
			try (RandomAccessFile file = getRandomAccessFile(fileName, "rw", pos);) {
				// 声明字节数组
				byte[] b = new byte[W.C.toInt(len == -1 ? file.length() - pos : len)];
				// 读取文件
				file.read(b);
				// 返回字节数组
				return b;
			} catch (IOException e) {
				Logs.error(e);
			}
		else if (AIO)
			// 获得文件通道
			try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(fileName));) {
				// 声明字节数组
				ByteBuffer buf = ByteBuffer.allocate(W.C.toInt(len == -1 ? channel.size() - pos : len));
				// 读取字节数组
				channel.read(buf, pos);
				// 返回字节数组
				return buf.array();
			} catch (Exception e) {
				Logs.error(e);
			}
		else
			// 获得文件通道
			try (FileChannel channel = FileChannel.open(Paths.get(fileName))) {
				// 声明字节数组
				ByteBuffer buf = ByteBuffer.allocate(W.C.toInt(len == -1 ? channel.size() - pos : len));
				// 读取字节数组
				channel.read(buf, pos);
				// 返回字节数组
				return buf.array();
			} catch (Exception e) {
				Logs.error(e);
			}
		// 返回空字节数组
		return C.A.BYTES_EMPTY;
	}

	/**
	 * 以流模式分段读取文件 使用默认IO缓冲和默认IO模式
	 * 
	 * @param name 文件名
	 * @param call 回调
	 * @return 读取长度
	 */
	public static long read(String name, CallbackVoid<Buffer> call) {
		return U.I.read(in(name), CommonParams.IO_BUFFERSIZE, true, call);
	}

	/**
	 * 读取文件为字节数组 可指定开始读取位置
	 * 
	 * @param fileName 文件名
	 * @param pos      偏移
	 * @return 字节数组
	 */
	public static byte[] read(String fileName, long pos) {
		return read(fileName, pos, -1);
	}

	/**
	 * 读取文件
	 * 
	 * @param fileName 要读取的文件
	 * @return 读取文件字节数组
	 */
	public static byte[] read(String fileName) {
//		try (FileInputStream in = in(fileName)) {
//			return U.I.read(in);
//		} catch (IOException e) {
//			return C.A.BYTES_EMPTY;
//		}
		return U.I.read(in(fileName));
//		try {
//			return Files.readAllBytes(Paths.get(fileName));
//		} catch (Exception e) {
//			return C.A.BYTES_EMPTY;
//		}
	}

	/**
	 * 读取文件
	 * 
	 * @param file 要读取的文件
	 * @return 读取文件字节数组
	 */
	public static byte[] read(File file) {
		return IOUtil.read(getInputStream(file));
	}

	/**
	 * 转换文件 读取文件并写入指定文件 按缓冲字节写入
	 * 
	 * @param read  读取文件
	 * @param write 写入文件
	 * @param call  回调
	 * @return 读取长度
	 */
	public static long convert(String read, String write, Callback<Buffer, Buffer> call) {
		return U.I.write(out(write), in(read), call);
	}

	/**
	 * 把InputStream流中的内容保存到文件中
	 * 
	 * @param fileName 文件名
	 * @param is       流
	 * @return 写入成功字节数
	 */
	public static long write(String fileName, InputStream is) {
		return IOUtil.write(getOutputStream(fileName), is);
	}

	/**
	 * 把文件写指定路径中
	 * 
	 * @param fileName 文件名
	 * @param file     文件
	 * @return 写入成功字节数
	 */
	public static long write(String fileName, File file) {
		return IOUtil.write(getOutputStream(fileName), getInputStream(file));
	}

	/**
	 * 把文件写指定路径中
	 * 
	 * @param fileName 文件名
	 * @param b        字节数组
	 * @return 写入成功字节数
	 */
	public static long write(String fileName, byte[] b) {
		return write(fileName, b, CommonParams.FILE_APPEND);
	}

	/**
	 * 把文件写指定路径中
	 * 
	 * @param fileName 文件名
	 * @param b        字节数组
	 * @param append   是否追加
	 * @return 写入成功字节数
	 */
	public static long write(String fileName, byte[] b, boolean append) {
		return IOUtil.write(FileUtil.getOutputStream(fileName, append), b);
	}

	/**
	 * 把字节写到文件中 可指定写入位置
	 * 
	 * @param fileName 文件名
	 * @param b        字节数组
	 * @param pos      偏移
	 */
	public static void write(String fileName, byte[] b, long pos) {
		if (IO)
			// 获得随机读写文件
			try (RandomAccessFile file = getRandomAccessFile(fileName, "rw", pos);) {
				// 写字节数组
				file.write(b);
			} catch (IOException e) {
				Logs.error(e);
			}
		else if (AIO)
			// 获得文件通道
			try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get(fileName),
					StandardOpenOption.WRITE);) {
				// 写字节数组
				channel.write(ByteBuffer.wrap(b), pos);
			} catch (Exception e) {
				Logs.error(e);
			}
		else
			// 获得文件通道
			try (FileChannel channel = FileChannel.open(Paths.get(fileName), StandardOpenOption.WRITE);) {
				// 写字节数组
				channel.write(ByteBuffer.wrap(b), pos);
			} catch (Exception e) {
				Logs.error(e);
			}
	}

	/**
	 * 写文件 默认使用UTF-8编码
	 * 
	 * @param text     写入的内容
	 * @param fileName 文件名
	 * @return true false
	 */
	public static long write(String fileName, String text) {
		return write(fileName, text, CommonParams.FILE_APPEND);
	}

	/**
	 * 写文件 默认使用UTF-8编码
	 * 
	 * @param text     写入的内容
	 * @param fileName 文件名
	 * @param append   是否追加
	 * @return true false
	 */
	public static long write(String fileName, String text, boolean append) {
		return write(fileName, text, CommonParams.ENCODING, append);
	}

	/**
	 * 写文件
	 * 
	 * @param text        写入的内容
	 * @param fileName    文件名
	 * @param charsetName 编码格式
	 * @return 写入成功字节数
	 */
	public static long write(String fileName, String text, String charsetName) {
		return write(fileName, text, charsetName, CommonParams.FILE_APPEND);
	}

	/**
	 * 写文件
	 * 
	 * @param text        写入的内容
	 * @param fileName    文件名
	 * @param charsetName 编码格式
	 * @param append      是否追加
	 * @return 写入成功字节数
	 */
	public static long write(String fileName, String text, String charsetName, boolean append) {
		return IOUtil.write(getOutputStream(fileName, append), text, charsetName);
	}

	/**
	 * 获得文件
	 * 
	 * @param fileName 文件名含路径
	 * @return File对象
	 */
	public static File newFile(String fileName) {
		return new File(fileName);
	}

	/**
	 * 获得文件
	 * 
	 * @param URI uri 文件名含路径
	 * @return File对象
	 */
	public static File newFile(URI uri) {
		return new File(uri);
	}

	/**
	 * 获得文件
	 * 
	 * @param fileName 文件名含路径
	 * @param mode     打开模式
	 * @param pos      偏移
	 * @return RandomAccessFile对象
	 */
	public static RandomAccessFile getRandomAccessFile(String fileName, String mode, long pos) {
		// 声明RandomAccessFile
		RandomAccessFile file = null;
		try {
			File f = newFile(fileName);
			// //如果文件不存在 创建
			if (!f.exists()) {
				mkdirs(fileName);
				f.createNewFile();
			}
			// 实例化随机读取文件实例
			file = new RandomAccessFile(f, mode);
			// 设置偏移量
			file.seek(pos);
		} catch (Exception e) {
			Logs.error(e);
		}
		// 返回RandomAccessFile
		return file;
	}

	/**
	 * 获得文件输入流 如果失败返回null
	 * 
	 * @param fileName 文件名
	 * @return 输入流
	 */
	public static FileInputStream in(String fileName) {
		return getInputStream(fileName);
	}

	/**
	 * 获得文件输入流 如果失败返回null
	 * 
	 * @param fileName 文件名
	 * @return 输入流
	 */
	public static FileInputStream getInputStream(String fileName) {
		return getInputStream(newFile(fileName));
	}

	/**
	 * 获得文件输出流 如果失败返回null
	 * 
	 * @param fileName 文件名
	 * @return 输出流
	 */
	public static FileOutputStream out(String fileName) {
		return getOutputStream(fileName);
	}

	/**
	 * 获得文件输出流 如果失败返回null
	 * 
	 * @param fileName 文件名
	 * @return 输出流
	 */
	public static FileOutputStream getOutputStream(String fileName) {
		return getOutputStream(newFile(fileName));
	}

	/**
	 * 获得文件输出流 如果失败返回null
	 * 
	 * @param fileName 文件名
	 * @param append   是否追加
	 * @return 输出流
	 */
	public static FileOutputStream getOutputStream(String fileName, boolean append) {
		return getOutputStream(newFile(fileName), append);
	}

	/**
	 * 获得文件输入流 如果失败返回null
	 * 
	 * @param file 文件
	 * @return 输入流
	 */
	public static FileInputStream getInputStream(File file) {
		try {
			return file == null ? null : file.exists() ? new FileInputStream(file) : null;
		} catch (Exception e) {
			Logs.error(e);
			return null;
		}
	}

	/**
	 * 获得文件输出流 如果失败返回null
	 * 
	 * @param file 文件
	 * @return 输出流
	 */
	public static FileOutputStream getOutputStream(File file) {
		return getOutputStream(file, CommonParams.FILE_APPEND);
	}

	/**
	 * 获得文件输出流 如果失败返回null
	 * 
	 * @param file   文件
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
			Logs.error(e);
			return null;
		}
	}
}
