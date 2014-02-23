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
 * @version 1._ 2_12-_7-29
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
  var name: String = _
  // 密码 
  var password: String = _
  // 状态
  @BeanProperty
  var state: Integer = _
  // 权限
  @BeanProperty
  var roleId: Integer = _
  //IP
  @BeanProperty
  var ip: String = _
  //Email
  @BeanProperty
  var email: String = _
  //登录IP
  @BeanProperty
  var loginIp: String = _
  //登录时间
  @BeanProperty
  var loginTime: Integer = _

  override def setPassword(password: String) = this.password = Digest.password(password)

  override def getPassword: String = password
}
