package com.weicoder.admin.po;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import lombok.Data;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 管理员
 * 
 * @author WD
 */
@Entity
@DynamicInsert
@DynamicUpdate
@Data
public class Admin {
	// ID
	@Id
	@Size(max = 8)
	private String name;
	// 密码
	@JSONField(serialize = false)
	private String password;
	// 状态
	private Integer state;
	// IP
	private String ip;
	// 时间
	private Integer time;
	// 权限
	private Integer roleId;
}
