package com.weicoder.admin.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 角色实体
 * 
 * @author WD
 */
@Entity
@Data
public class Role {
	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// 名称
	private String name;
}