package com.weicoder.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @author       david
 * @Title:       solr搜索实体类
 * @Description: 封装查询结果
 * @date         2018/4/18 18:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBean {
	// 靓号
	@Field
	private long id;
	// 靓号
	@Field
	private long uid;
	// 用户昵称
	@Field
	private String nickname;
}
