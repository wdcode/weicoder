package com.weicoder.admin.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 登录日志实体
 * 
 * @author WD
 */
@Entity
@Data
public class Login {
	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// 名称
	private String name;
	// 登录IP
	private String ip;
	// 状态
	private Integer state;
	// 时间
	private Integer time;
}
