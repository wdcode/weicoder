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
 * 角色与菜单关系
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@DynamicInsert
@DynamicUpdate
@Cache
class RoleMenu extends BaseEntityId {
  //角色ID
  @BeanProperty
  var roleId: Int = _
  //菜单ID
  @BeanProperty
  var menuId: Int = _
}