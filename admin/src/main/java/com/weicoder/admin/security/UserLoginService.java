package com.weicoder.admin.security;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.weicoder.admin.params.AdminParams;
import com.weicoder.admin.po.Admin;
import com.weicoder.admin.po.Authority;
import com.weicoder.admin.po.Menu;
import com.weicoder.admin.po.Role;
import com.weicoder.admin.po.RoleAuthority;
import com.weicoder.admin.po.RoleMenu; 
import com.weicoder.admin.token.AdminToken;
import com.weicoder.base.service.SuperService;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.util.BeanUtil;

/**
 * 该类的主要作用是为Spring Security提供一个经过用户认证后的UserDetails。 该UserDetails包括用户名、密码、是否可用、是否过期等信息。
 * @author WD
 * @since JDK6
 * @version 1.0 2012-08-22
 */
@Component
public final class UserLoginService implements UserDetailsService {
	@Resource
	private SuperService	service;

	/**
	 * Spring Security 登录
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 根据用户名查询EntityLogin实体
		Admin admin = service.get(Admin.class, "name", username);
		// 如果为空抛出用户名为空异常
		if (admin == null) {
			throw new UsernameNotFoundException(username);
		}
		// 角色ID
		int roleId = Conversion.toInt(admin.getRoleId());
		// 角色
		Role role = null;
		// 获得角色
		if (roleId > 0) {
			role = service.get(Role.class, roleId);
		}
		// 权限
		List<Authority> authorities = null;
		// 菜单
		List<Menu> menus = null;
		// 对权限和菜单赋值
		if (AdminParams.ADMIN == admin.getId()) {
			// 获得权限
			authorities = service.all(Authority.class);
			// 获得菜单
			menus = service.all(Menu.class);
		} else {
			// 不是创建者 权限ID不为空
			if (roleId > 0) {
				// 获得权限
				List<Object> ras = BeanUtil.getFieldValues(service.eq(RoleAuthority.class, "roleId", roleId, -1, -1), "authorityId");
				authorities = service.gets(Authority.class, Lists.toArray(ras, Serializable.class));
				// 获得菜单
				List<Object> mus = BeanUtil.getFieldValues(service.eq(RoleMenu.class, "roleId", roleId, -1, -1), "menuId");
				menus = service.gets(Menu.class, Lists.toArray(mus, Serializable.class));
			}
		}
		// 声明返回凭证
		return new AdminToken(admin, role, authorities, menus);
	}
}