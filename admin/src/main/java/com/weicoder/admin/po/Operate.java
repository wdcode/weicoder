package com.weicoder.admin.po;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * 操作实体
 * 
 * @author WD
 */
@Entity
@Data
public class Operate {
	// 操作连接
	@Id
	private String link;
	// 名称
	private String name;
}