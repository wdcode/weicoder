package com.weicoder.test;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
 
import lombok.AllArgsConstructor;
import lombok.Data; 
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户
 * 
 * @author WD
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true) 
public class User {
	// 主键
	@Id
	private long uid;
	// 昵称
	private String nickname;
	// 头像
	private String head;
	// 性别
	private Integer sex;
	// 签名
	private String sign;
	// 注册IP
	private String ip;
	// 情感状态
	private String emotion;
	// 时间
	private Integer time;
	// 所在地
	private String address;
	// 生日
	private String birthday;
	// 渠道
	private String oem;
	// 平台
	private Integer oemid;
}