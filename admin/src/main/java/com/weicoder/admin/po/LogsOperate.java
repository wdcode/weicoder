package com.weicoder.admin.po;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.weicoder.ssh.entity.EntityIp;
import com.weicoder.ssh.entity.EntityUserId;
import com.weicoder.ssh.entity.base.BaseEntityIdTime;

/**
 * 操作日志实体
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
public class LogsOperate extends BaseEntityIdTime implements EntityUserId, EntityIp {
	// 内容
	private String	content;
	// 用户ID
	private Long		userId;
	// 状态
	private Integer	state;
	// 名称
	private String	name;
	// 操作IP
	private String	ip;
}