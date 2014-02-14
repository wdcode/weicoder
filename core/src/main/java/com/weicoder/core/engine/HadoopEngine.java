package com.weicoder.core.engine;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.io.IOUtil; 
import com.weicoder.core.log.Logs;

/**
 * Hadoop相关操作类
 * @author WD
 * @since JDK7
 * @version 1.0 2012-11-17
 */
public final class HadoopEngine {
	// Hadoop 文件系统
	private static FileSystem	hdfs;
	static {
		try {
			hdfs = FileSystem.get(new Configuration());
		} catch (IOException e) {
			Logs.warn(e);
		}
	}

	/**
	 * 写文件
	 * @param fileName 文件名
	 * @param data 数据
	 */
	public static void write(String fileName, byte[] data) {
		try (FSDataOutputStream out = hdfs.create(new Path(fileName))) {
			IOUtil.write(out, data);
		} catch (Exception e) {
			Logs.warn(e);
		}
	}

	/**
	 * 读文件
	 * @param fileName 文件名
	 */
	public static byte[] read(String fileName) {
		try (FSDataInputStream in = hdfs.open(new Path(fileName))) {
			return IOUtil.read(in);
		} catch (Exception e) {
			return ArrayConstants.BYTES_EMPTY;
		}
	}

	/**
	 * 读文件
	 * @param fileName 文件名
	 */
	public static boolean delete(String fileName) {
		try {
			return hdfs.delete(new Path(fileName), true);
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 创建目录
	 * @param path 目录路径
	 * @return true 成功 false 失败
	 */
	public static boolean mkdirs(String path) {
		try {
			return hdfs.mkdirs(new Path(path));
		} catch (IOException e) {
			return false;
		}
	}

	private HadoopEngine() {}
}
