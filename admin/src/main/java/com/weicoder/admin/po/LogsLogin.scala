package com.weicoder.admin.po

import javax.persistence.Entity
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import com.weicoder.base.entity.EntityIp
import com.weicoder.base.entity.EntityShards
import com.weicoder.base.entity.EntityUserId
import com.weicoder.site.entity.base.BaseEntityIdTime
import scala.beans.BeanProperty

/**
 * 登录日志实体
 * @author WD
 * @since JDK7
 * @version 1._ 2_11-_4-_3
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@DynamicInsert
@DynamicUpdate
class LogsLogin extends BaseEntityIdTime with EntityIp {
  // 登录IP
  @BeanProperty
  var ip: String = _
  // 用户ID
  @BeanProperty
  var userId: Integer = _
  // 状态
  @BeanProperty
  var state: Integer = _
  // 名称
  @BeanProperty
  var name: String = _
}
