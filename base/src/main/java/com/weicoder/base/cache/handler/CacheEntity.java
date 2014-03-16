package com.weicoder.base.cache.handler;

import com.weicoder.web.socket.message.Message;

/**
 * 缓存更新消息实体
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-30
 */
public final class CacheEntity extends Message {
	// 实体
	private String	entity;
	// 命令 set remove
	private String	common;
	// Json
	private String	json;

	/**
	 * 构造
	 */
	public CacheEntity() {}

	/**
	 * 构造
	 * @param entity
	 * @param common
	 * @param json
	 */
	public CacheEntity(String entity, String common, String json) {
		this.entity = entity;
		this.common = common;
		this.json = json;
	}

	/**
	 * 获得实体
	 * @return 实体
	 */
	public String getEntity() {
		return entity;
	}

	/**
	 * 设置实体
	 * @param entity 实体
	 */
	public void setEntity(String entity) {
		this.entity = entity;
	}

	/**
	 * 获得命令
	 * @return 命令
	 */
	public String getCommon() {
		return common;
	}

	/**
	 * 设置命令
	 * @param common 命令
	 */
	public void setCommon(String common) {
		this.common = common;
	}

	/**
	 * 获得Json
	 * @return Json
	 */
	public String getJson() {
		return json;
	}

	/**
	 * 设置Json
	 * @param json Json
	 */
	public void setJson(String json) {
		this.json = json;
	}
}
