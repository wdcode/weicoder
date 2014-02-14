package com.weicoder.admin.po

import javax.persistence.Entity

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import com.weicoder.site.entity.base.BaseEntityIdTime

/**
 * 操作日志实体
 * @author WD
 * @since JDK7
 * @version 1.0 2011-04-03
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@DynamicInsert
@DynamicUpdate
class Logs extends BaseEntityIdTime {
	// 内容
	String		content
	// 用户ID
	Integer		userId
	// 状态
	Integer		state
	// 名称
	String		name
	//操作IP
	String 		ip
}