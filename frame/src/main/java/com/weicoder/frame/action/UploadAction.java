package com.weicoder.frame.action;

import java.io.File;

import com.weicoder.common.codec.Hex;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.crypto.Digest;
import com.weicoder.common.io.FileUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.ImageUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.frame.params.UploadParams;

/**
 * 上传Action
 * @author WD
 * @version 1.0
 */
public abstract class UploadAction extends BasicAction {
	// 上传文件
	protected File		file;
	// 上传文件类型
	protected String	fileContentType;
	// 上传文件名
	protected String	fileFileName;

	// 上传文件数组
	protected File[]	files;
	// 上传文件数组类型
	protected String[]	filesContentType;
	// 上传文件数组名
	protected String[]	filesFileName;

	/**
	 * 上传文件
	 * @param fileName 文件名
	 * @return 文件路径
	 */
	protected String[] uploads(File[] uploads, String[] uploadsFileName) {
		// 如果没有文件跳出
		if (EmptyUtil.isEmpty(uploads)) {
			return ArrayConstants.STRING_EMPTY;
		}
		// 获得文件个数
		int size = uploads.length;
		// 获得路径
		String[] names = new String[size];
		// 循环文件名
		for (int i = 0; i < size; i++) {
			// 获得路径
			names[i] = upload(uploads[i], uploadsFileName[i]);
		}
		// 返回路径
		return names;
	}

	/**
	 * 上传文件
	 * @param file 上传文件
	 * @param fileName 文件名
	 * @return 文件路径
	 */
	protected String upload(File file, String fileName) {
		// 如果没有文件跳出
		if (EmptyUtil.isEmpty(file)) {
			return null;
		}
		// 获得上次路径
		String name = getFileName(file, fileName);
		// 上传文件
		String fn = UploadParams.UPLOAD_RESOURCE ? UploadParams.UPLOAD_PATH + name : getRealPath(name);
		// 文件是否存在
		if (FileUtil.exists(fn)) {
			// 获得上传文件MD5
			String md5 = Hex.encode(Digest.md5(FileUtil.read(file)));
			// 文件存在始终循环
			while (FileUtil.exists(fn)) {
				// 验证MD5
				if (Hex.encode(Digest.md5(FileUtil.read(fn))).equals(md5)) {
					// 相同文件不处理跳出循环
					break;
				} else {
					// 文件不同 获得新文件名
					name = getFileName(file, (DateUtil.getTime() % 100) + StringConstants.UNDERLINE + fileName);
					fn = UploadParams.UPLOAD_RESOURCE ? UploadParams.UPLOAD_PATH + name : getRealPath(name);
				}
			}
		}
		// 文件不存在写文件
		if (!FileUtil.exists(fn)) {
			FileUtil.write(fn, file);
			// 是否开启图片压缩 并且是图片
			if (UploadParams.UPLOAD_IMAGE_COMPRESS_POWER && ImageUtil.isImage(file)) {
				// 循环压缩图片
				for (String compress : UploadParams.UPLOAD_IMAGE_COMPRESS_NAMES) {
					// 获取压缩文件保存文件名
					String f = StringUtil.subStringLastEnd(fn, StringConstants.BACKSLASH) + StringConstants.BACKSLASH + compress + StringConstants.BACKSLASH + StringUtil.subStringLast(fn, StringConstants.BACKSLASH);
					// 写入压缩图片
					ImageUtil.compress(file, FileUtil.getOutputStream(f), UploadParams.getWidth(compress), UploadParams.getHeight(compress), true);
				}
			}
		}
		// 返回路径
		return UploadParams.UPLOAD_SERVER ? getDomain() + name : name;
	}

	/**
	 * 上传文件
	 * @return
	 * @throws Exception
	 */
	public String upload() throws Exception {
		return ajax(upload(file, fileFileName));
	}

	/**
	 * 上传文件
	 * @return
	 * @throws Exception
	 */
	public String uploads() throws Exception {
		return ajax(uploads(files, filesFileName));
	}

	/**
	 * 获得文件
	 * @return 文件
	 */
	public File getFile() {
		return file;
	}

	/**
	 * 设置文件
	 * @param file 文件
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 获得文件数组
	 * @return 文件数组
	 */
	public File[] getFiles() {
		return files;
	}

	/**
	 * 设置文件数组
	 * @param files 文件数组
	 */
	public void setFiles(File[] files) {
		this.files = files;
	}

	/**
	 * 获得上传文件类型
	 * @return 上传文件类型
	 */
	public String getFileContentType() {
		return fileContentType;
	}

	/**
	 * 设置上传文件类型
	 * @param fileContentType 上传文件类型
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * 获得上传文件名
	 * @return 上传文件名
	 */
	public String getFileFileName() {
		return fileFileName;
	}

	/**
	 * 设置上传文件名
	 * @param fileFileName 上传文件名
	 */
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * 获得上传文件数组类型
	 * @return 上传文件数组类型
	 */
	public String[] getFilesContentType() {
		return filesContentType;
	}

	/**
	 * 设置上传文件数组类型
	 * @param filesContentType 上传文件数组类型
	 */
	public void setFilesContentType(String[] filesContentType) {
		this.filesContentType = filesContentType;
	}

	/**
	 * 获得上传文件数组名
	 * @return 上传文件数组名
	 */
	public String[] getFilesFileName() {
		return filesFileName;
	}

	/**
	 * 设置上传文件数组名
	 * @param filesFileName 上传文件数组名
	 */
	public void setFilesFileName(String[] filesFileName) {
		this.filesFileName = filesFileName;
	}

	/**
	 * 获得文件名
	 * @param fileName 文件名
	 * @return 文件名
	 */
	private String getFileName(File file, String fileName) {
		// 上传路径
		StringBuilder name = new StringBuilder(UploadParams.UPLOAD_RESOURCE ? StringConstants.EMPTY : UploadParams.UPLOAD_PATH);
		name.append(EmptyUtil.isEmpty(module) ? StringConstants.EMPTY : module + StringConstants.BACKSLASH);
		name.append(Digest.absolute(fileName, 20));
		// 是否处理后缀
		if (UploadParams.UPLOAD_SUFFIX) {
			// 获得后缀
			String suffix = StringUtil.subStringLast(fileName, StringConstants.POINT);
			// 判断后缀不在处理列表中
			if (!UploadParams.UPLOAD_POSTFIX.contains(suffix)) {
				name.append(StringConstants.POINT);
				name.append(suffix);
			}
		}
		// 返回文件名
		return name.toString();
	}
}
