package com.weicoder.admin.po

import javax.persistence.Entity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import com.weicoder.site.entity.base.BaseEntityIdTime
import scala.beans.BeanProperty

/**
 * 操作日志实体
 * @author WD
 * @since JDK7
 * @version 1._ 2_11-_4-_3
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@DynamicInsert
@DynamicUpdate
class Logs extends BaseEntityIdTime {
  // 内容
  @BeanProperty
  var content: String = _
  // 用户ID
  @BeanProperty
  var userId: Integer = _
  // 状态
  @BeanProperty
  var state: Integer = _
  // 名称
  @BeanProperty
  var name: String = _
  //操作IP
  @BeanProperty
  var ip: String = _
}