package com.weicoder.admin.po

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import com.weicoder.base.annotation.Cache
import com.weicoder.common.lang.Conversion
import com.weicoder.site.entity.base.BaseEntity
import scala.beans.BeanProperty

/**
 * 操作实体
 * @author WD
 * @since JDK7
 * @version 1._ 2__9-11-23
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Entity
@DynamicInsert
@DynamicUpdate
@Cache
class Operate extends BaseEntity {
  // 操作连接
  @Id
  @BeanProperty
  var link: String = _
  // 名称
  @BeanProperty
  var name: String = _
  //类型
  //	var type:Integer

  /**
   * 获得主键
   */
  def getKey: Serializable = link

  /**
   * 设置主键
   */
  def setKey(key: Serializable) = link = Conversion.toString(key)
}