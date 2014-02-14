package com.weicoder.admin.po

import javax.persistence.Entity

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import com.weicoder.base.entity.EntityIp
import com.weicoder.base.entity.EntityShards
import com.weicoder.base.entity.EntityUserId
import com.weicoder.site.entity.base.BaseEntityIdTime

/**
 * 登录日志实体
 * @author WD
 * @since JDK7
 * @version 1.0 2011-04-03
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@DynamicInsert
@DynamicUpdate
class LogsLogin extends BaseEntityIdTime {
	// 登录IP
	String				ip
	// 用户ID
	Integer				userId
	// 状态
	Integer				state
	// 名称
	String				name
}
