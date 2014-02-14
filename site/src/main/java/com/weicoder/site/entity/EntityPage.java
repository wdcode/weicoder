package com.weicoder.site.entity;

import java.io.Serializable;

/**
 * 带页面关键字的实体
 * @author WD
 * @since JDK7
 * @version 1.0 2012-05-09
 */
public final class EntityPage implements Serializable {
	// 序列化ID
	private static final long	serialVersionUID	= -4463651301979869157L;
	// 页面标题
	private String				title;
	// 页面关键字
	private String				keyWords;
	// 页面描述
	private String				description;

	/**
	 * 获得页面标题
	 * @return 页面标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置页面标题
	 * @param title 页面标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 获得页面关键字
	 * @return 页面关键字
	 */
	public String getKeyWords() {
		return keyWords;
	}

	/**
	 * 设置页面关键字
	 * @param keyWords 页面关键字
	 */
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	/**
	 * 获得页面描述
	 * @return 页面描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置页面描述
	 * @param description 页面描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
