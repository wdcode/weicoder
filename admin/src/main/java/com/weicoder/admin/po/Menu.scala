package com.weicoder.admin.po

import javax.persistence.Entity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import com.weicoder.base.annotation.Cache
import com.weicoder.site.entity.base.BaseEntityId
import scala.beans.BeanProperty

/**
 * 菜单实体
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
class Menu extends BaseEntityId {
  // 上级菜单ID
  @BeanProperty
  var menuId: Integer = null
  // 链接
  @BeanProperty
  var url: String = null
  // 名称
  @BeanProperty
  var name: String = null
  //类型
  //	var type:Integer		
}
