package com.weicoder.admin.po;

import javax.persistence.Entity;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.weicoder.ssh.entity.EntityUser;
import com.weicoder.ssh.entity.base.BaseEntityIdTime;
import com.weicoder.common.crypto.Digest;

/**
 * 管理员
 * @author WD
 * @since JDK7
 * @version 1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class Admin extends BaseEntityIdTime implements EntityUser {
	// 名称
	@Size(min = 5)
	private String	name;
	// 密码
	private String	password;
	// 状态
	private Integer	state;
	// 权限
	private Integer	roleId;
	// IP
	private String	ip;
	// Email
	private String	email;
	// 登录IP
	private String	loginIp;
	// 登录时间
	private Integer	loginTime;

	@Override
	public void setPassword(String password) {
		this.password = Digest.password(password);
	}
}
