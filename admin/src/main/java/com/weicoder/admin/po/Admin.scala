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
import scala.beans.BeanProperty

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
class Admin extends BaseEntityIdTime with EntityUser {
  // 名称
  @Size(min = 5)
  @BeanProperty
  var name: String = null
  // 密码
  @BeanProperty
  var password: String = null
  // 状态
  @BeanProperty
  var state: Integer = null
  // 权限
  @BeanProperty
  var roleId: Integer = null
  //IP
  @BeanProperty
  var ip: String = null
  //Email
  @BeanProperty
  var email: String = null
  //登录IP
  @BeanProperty
  var loginIp: String = null
  //登录时间
  @BeanProperty
  var loginTime: Integer = null

//  /**
//   * 设置用户密码
//   * @param password 用户密码
//   */ 
  //  def password_$eq(password: String) = { this.password = Digest.password(password) }
}
