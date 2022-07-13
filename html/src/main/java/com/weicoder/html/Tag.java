package com.weicoder.html;

/**
 * Html标签tag
 * 
 * @author wdcode
 *
 */
public interface Tag {
	/**
	 * 根据ID查找标签
	 * 
	 * @param id ID
	 * @return 标签
	 */
	Tag id(String id);

//	/**
//	 * 根据标签名返回相应标签
//	 * @param name 标签名
//	 * @return 标签 一般为
//	 */
//	Tag tag(String name);

	/**
	 * 根据标签名返回相应标签集合
	 * 
	 * @param name 标签名
	 * @return 标签名
	 */
	Tags tags(String name);

	/**
	 * 根据CSS名称查找标签集合
	 * 
	 * @param css CSS名称
	 * @return 标签集合
	 */
	Tags css(String css);

	/**
	 * 判断标签是否是css
	 * 
	 * @param css CSS名称
	 * @return 是否存在CSS
	 */
	boolean hasCss(String css);

	/**
	 * 获得标签下的全部文本
	 * 
	 * @return 全部文本
	 */
	String text();
}
