package com.weicoder.admin.po

import javax.persistence.Entity
import javax.validation.constraints.Size

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import com.weicoder.base.annotation.Cache
import com.weicoder.base.entity.EntityUser
import com.weicoder.common.crypto.Digest
import com.weicoder.site.entity.base.BaseEntityIdTime

/**
 * 管理员
 * @author WD
 * @since JDK7
 * @version 1.0 2012-07-29
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Cache
@DynamicInsert
@DynamicUpdate
class Admin extends BaseEntityIdTime implements EntityUser {
	// 名称
	@Size(min=5)
	String		name
	// 密码
	String		password
	// 状态
	Integer		state
	// 权限
	Integer		roleId
	//IP
	String		ip
	//Email
	String		email
	//登录IP
	String		loginIp
	//登录时间
	Integer		loginTime

	/**
	 * 设置用户密码
	 * @param password 用户密码
	 */
	public void setPassword(String password){
		this.password = Digest.password(password)
	}
}
