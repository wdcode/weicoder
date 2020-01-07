package com.weicoder.admin.vo;

import com.weicoder.admin.po.Admin;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 返回结果
 * 
 * @author wudi
 */
@Data
@AllArgsConstructor
public class Result {
	// 管理员Token
	private String token;
	// 管理员实体
	private Admin admin;
}
