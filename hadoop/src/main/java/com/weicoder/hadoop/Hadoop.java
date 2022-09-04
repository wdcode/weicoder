package com.weicoder.hadoop;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.weicoder.common.constants.C;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.U;

/**
 * Hapoop操作类
 * 
 * @author wdcode
 *
 */
public class Hadoop {
	private FileSystem fs;

	/**
	 * 构造
	 * 
	 * @param uri 地址
	 */
	public Hadoop(String uri) {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", uri);
		try {
			fs = FileSystem.get(new URI(uri), conf);
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 获得FileSystem
	 * 
	 * @return FileSystem
	 */
	public FileSystem fs() {
		return fs;
	}

	/**
	 * 创建目录
	 * 
	 * @param dir 目录名
	 * @return 是否成功
	 */
	public boolean mkdir(String dir) {
		try {
			return fs.mkdirs(new Path(dir));
		} catch (Exception e) {
			Logs.error(e);
			return false;
		}
	}

	/**
	 * 删除目录或文件
	 * 
	 * @param path 目录或文件
	 */
	public boolean delete(String path) {
		try {
			return fs.delete(new Path(path), false);
		} catch (Exception e) {
			Logs.error(e);
			return false;
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param file 文件名
	 * @param in   数据流
	 */
	public void create(String file, InputStream in) {
		try {
			IOUtil.write(fs.create(new Path(file)), in);
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param file 文件名
	 * @param b    数据
	 */
	public void create(String file, byte[] b) {
		try {
			IOUtil.write(fs.create(new Path(file)), b);
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 获得路径下的所有文件
	 * 
	 * @param path 路径
	 * @return
	 */
	public List<FileStatus> list(String path) {
		try {
			return Lists.newList(fs.listStatus(new Path(path)));
		} catch (Exception e) {
			return Lists.emptyList();
		}
	}

	/**
	 * 读取文件
	 * 
	 * @param path 文件路径
	 * @return 字节数组
	 */
	public byte[] read(String path) {
		try {
			return U.I.read(fs.open(new Path(path)));
		} catch (Exception e) {
			return C.A.BYTES_EMPTY;
		}
	}

	/**
	 * 读取文件内容到输出流
	 * 
	 * @param path 路径
	 * @param os   输出流
	 */
	public void read(String path, OutputStream os) {
		U.I.write(os, read(path));
	}

	/**
	 * 重命名
	 * 
	 * @param oldPath 旧文件
	 * @param newPath 新文件
	 */
	public void rename(String oldPath, String newPath) {
		try {
			fs.rename(new Path(oldPath), new Path(newPath));
		} catch (Exception e) {
			Logs.error(e);
		}
	}
}
