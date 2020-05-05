package com.weicoder.admin.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 操作日志实体
 * 
 * @author WD
 */
@Entity
@Data
public class Logs {
	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// 名称
	private String name;
	// 内容
	private String content;
	// 操作IP
	private String ip;
	// 时间
	private Integer time;
}