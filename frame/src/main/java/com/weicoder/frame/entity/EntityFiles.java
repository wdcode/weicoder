package com.weicoder.frame.entity;

/**
 * 有上传文件数组的实体接口
 * @author WD
 * 
 * @version 1.0 2012-6-25
 */
public interface EntityFiles {
	/**
	 * 获得文件数组路径
	 * @return 文件数组路径
	 */
	String[] getPaths();

	/**
	 * 设置文件数组路径
	 * @param paths 文件数组路径
	 */
	void setPaths(String[] paths);
}
