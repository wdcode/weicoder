package com.weicoder.admin.po;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

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
	// 操作列表
	@Type(type = "com.weicoder.hibernate.type.JsonType")
	private List<String> operates;
	// 菜单
	@Type(type = "com.weicoder.hibernate.type.JsonType")
	private List<Integer> menus;
}