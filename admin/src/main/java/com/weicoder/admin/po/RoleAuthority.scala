package com.weicoder.admin.po

import com.weicoder.site.entity.base.BaseEntityId
import scala.beans.BeanProperty
import org.springframework.stereotype.Component
import org.springframework.context.annotation.Scope
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import javax.persistence.Entity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import com.weicoder.base.annotation.Cache

/**
 * 角色与权限关系表
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@DynamicInsert
@DynamicUpdate
@Cache
class RoleAuthority extends BaseEntityId {
  //操作	
  @BeanProperty
  var authorityId: Int = _
  //角色
  @BeanProperty
  var roleId: Int = _
}