package com.weicoder.frame.entity;

/**
 * 有上传文件的实体接口
 * @author WD
 * 
 * @version 1.0 2012-6-25
 */
public interface EntityFile {
	/**
	 * 获得文件路径
	 * @return 文件路径
	 */
	String getPath();

	/**
	 * 设置文件路径
	 * @param path 文件路径
	 */
	void setPath(String path);
}
