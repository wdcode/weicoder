package com.weicoder.html;

import java.util.List;

/**
 * Tag的集合
 * 
 * @author wdcode
 *
 */
public interface Tags extends List<Tag> {
	/**
	 * 查找不符合规则的所有标签
	 * 
	 * @param query 查找条件
	 * @return 标签集合
	 */
	Tags not(String query);

	/**
	 * 获得第一个标签
	 */
	Tag first();

	/**
	 * 获得标签下文本
	 * 
	 * @return 文本
	 */
	String text();
}
