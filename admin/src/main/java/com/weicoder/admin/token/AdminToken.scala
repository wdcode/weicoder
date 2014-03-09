package com.weicoder.admin.token;

import java.io.Serializable
import java.util.List
import org.springframework.security.core.userdetails.UserDetails
import com.weicoder.admin.params.AdminParams
import com.weicoder.admin.po.Admin
import com.weicoder.admin.po.Authority
import com.weicoder.admin.po.Menu
import com.weicoder.admin.po.Role
import com.weicoder.admin.po.RoleAuthority
import com.weicoder.admin.po.RoleMenu
import com.weicoder.base.service.SuperService
import com.weicoder.base.token.AuthToken
import com.weicoder.common.lang.Conversion
import com.weicoder.common.lang.Lists
import com.weicoder.common.util.BeanUtil
import com.weicoder.core.json.JsonEngine
import com.weicoder.site.token.LoginToken
import com.weicoder.admin.po.Role
import scala.beans.BeanProperty
import scala.beans.BooleanBeanProperty
import com.weicoder.common.util.DateUtil

/**
 * Spring Security 登录凭证
 * @author WD
 * @since JDK7
 * @version 1.0 2013-1-7
 */
final class AdminToken extends LoginToken with UserDetails with AuthToken {
  // 密码
  @BeanProperty
  var password: String = _
  // 是否启用
  @BooleanBeanProperty
  var enabled: Boolean = _
  // 用户名
  @BeanProperty
  var username: String = _
  // 管理员实体
  @BeanProperty
  var admin: Admin = _
  // 角色
  @BeanProperty
  var role: Role = _
  // 权限
  @BeanProperty
  var authorities: List[Authority] = _
  // 菜单
  @BeanProperty
  var menus: List[Menu] = _

  /**
   * 构造
   */
  def this(admin: Admin, role: Role, authorities: List[Authority], menus: List[Menu]) = {
    this();
    this.admin = admin
    this.time = DateUtil.getTime
    this.id = admin.getId
    this.password = admin.getPassword
    this.username = admin.getName
    this.enabled = Conversion.toInt(admin.getState) == 1
    this.authorities = authorities
    this.menus = menus;
  }

  override def isAccountNonExpired: Boolean = true

  override def isAccountNonLocked: Boolean = true

  override def isCredentialsNonExpired: Boolean = true
}
