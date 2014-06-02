package com.weicoder.admin.po;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.weicoder.frame.entity.base.BaseEntity;
import com.weicoder.common.lang.Conversion;

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
@Data
@EqualsAndHashCode(callSuper = false)
public class Operate extends BaseEntity {
	// 操作连接
	@Id
	private String	link;
	// 名称
	private String	name;

	@Override
	public Serializable getKey() {
		return link;
	}

	@Override
	public void setKey(Serializable key) {
		this.link = Conversion.toString(key);
	}
}