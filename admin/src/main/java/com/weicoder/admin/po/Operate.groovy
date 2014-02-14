package com.weicoder.admin.po

import java.io.Serializable

import javax.persistence.Entity
import javax.persistence.Id

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import com.weicoder.base.annotation.Cache
import com.weicoder.common.lang.Conversion
import com.weicoder.site.entity.base.BaseEntity

/**
 * 操作实体
 * @author WD
 * @since JDK7
 * @version 1.0 2009-11-23
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@DynamicInsert
@DynamicUpdate
@Cache
class Operate extends BaseEntity {
	// 操作连接
	@Id
	String		link
	// 名称
	String		name
	//类型
	Integer		type

	/**
	 * 获得主键
	 */
	public Serializable getKey() {
		return link
	}

	/**
	 * 设置主键
	 */
	public void setKey(Serializable key) {
		link = Conversion.toString(key)
	}
}