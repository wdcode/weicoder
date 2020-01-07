package com.weicoder.admin.po;

import lombok.Data;

/**
 * 角色对应操作
 * 
 * @author wudi
 */
@Data
public class RoleOperate {
	// 角色ID
	private int roleId;
	// 操作动作
	private String operate;
}
