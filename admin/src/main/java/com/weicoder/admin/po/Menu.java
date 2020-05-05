package com.weicoder.admin.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 菜单实体
 * 
 * @author WD
 */
@Entity
@Data
public class Menu {
	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// 上级菜单ID
	private Integer menuId;
	// 链接
	private String url;
	// 名称
	private String name;
}
