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
 * 角色与操作关系表
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@DynamicInsert
@DynamicUpdate
@Cache
class RoleOperate extends BaseEntityId {
  //操作	
  @BeanProperty
  var operate: String = _
  //角色
  @BeanProperty
  var roleId: Int = _
}