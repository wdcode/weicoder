package com.weicoder.admin.po

import java.util.List
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import com.weicoder.base.annotation.Cache
import com.weicoder.common.constants.StringConstants
import com.weicoder.common.lang.Conversion
import com.weicoder.common.lang.Lists
import com.weicoder.common.util.EmptyUtil
import com.weicoder.site.entity.base.BaseEntityId
import scala.beans.BeanProperty

/**
 * 角色实体
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
class Role extends BaseEntityId {
  // 名称
  @BeanProperty
  var name: String = _
  // 操作列表
//  @ManyToMany //  	@JoinTable(name = "role_operate",joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "operate"))
//  @JoinTable(name = "role_operate",joinColumns=Array(@JoinColumn(name="role_id")))
//  @BeanProperty
//  var operates: List[Operate] = _
//  // 菜单
//  @ManyToMany //  	@JoinTable(name = "role_menu", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
//  @JoinTable(name = "role_menu")
//  @BeanProperty
//  var menus: List[Menu] = _

//  /**
//   * 设置菜单
//   * @param operates
//   */
  //	def menu.(ms:String)= {
  //		//操作链接不为空
  //		if(!EmptyUtil.isEmpty(ms)){
  //			//声明列表
  //			menus = Lists.getList()
  //			//循环赋值
  //			for(String s:ms.split(StringConstants.COMMA)){
  //				//实例化操作类
  //				Menu m = new Menu()
  //				//设置链接
  //				m.setId(Conversion.toInt(s.trim()))
  //				//添加到列表
  //				menus.add(m)
  //			}
  //		}
  //	}

//  /**
//   * 设置操作
//   * @param operates
//   */
  //	def   operate(ops:String)= {
  //		//操作链接不为空
  //		if(!EmptyUtil.isEmpty(ops)){
  //			//声明列表
  //			operates = Lists.getList()
  //			//循环赋值
  //			for(String s:ops.split(StringConstants.COMMA)){
  //				//实例化操作类
  //				Operate op = new Operate()
  //				//设置链接
  //				op.setLink(s.trim())
  //				//添加到列表
  //				operates.add(op)
  //			}
  //		}
  //	}
}