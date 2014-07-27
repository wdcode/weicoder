package com.weicoder.admin.po;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.weicoder.frame.entity.base.BaseEntityId;

/**
 * 菜单实体
 * @author WD
 * @since JDK7
 * @version 1.0 2009-11-23
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class Menu extends BaseEntityId {
	// 上级菜单ID
	private Integer	menuId;
	// 链接
	private String	url;
	// 名称
	private String	name;
}
