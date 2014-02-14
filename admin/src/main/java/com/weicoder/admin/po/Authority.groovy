package com.weicoder.admin.po

import javax.persistence.Entity

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.context.annotation.Scope
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import com.weicoder.base.annotation.Cache
import com.weicoder.site.entity.base.BaseEntityId

/**
 * 权限实体
 * @author WD
 * @since JDK6
 * @version 1.0 2013-01-07
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@Cache
@DynamicInsert
@DynamicUpdate
class Authority extends BaseEntityId implements GrantedAuthority {
	// 权限
	String				authority
	// 名称
	String				name
}
