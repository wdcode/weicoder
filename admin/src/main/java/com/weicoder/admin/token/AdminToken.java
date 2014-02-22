package com.weicoder.admin.token;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import com.weicoder.admin.params.AdminParams;
import com.weicoder.admin.po.Admin;
import com.weicoder.admin.po.Authority;
import com.weicoder.admin.po.Role;
import com.weicoder.base.service.SuperService;
import com.weicoder.base.token.AuthToken;
import com.weicoder.common.lang.Conversion;
import com.weicoder.core.json.JsonEngine;
import com.weicoder.site.token.LoginToken;

/**
 * Spring Security 登录凭证
 * @author WD
 * @since JDK7
 * @version 1.0 2013-1-7
 */
public final class AdminToken extends LoginToken implements UserDetails, AuthToken {
	private static final long	serialVersionUID	= -3399985197865994512L;
	// 密码
	private String				password;
	// 权限
	private List<Authority>		authorities;
	// 是否启用
	private boolean				enabled;
	// 用户名
	private String				username;
	// 管理员实体
	private Admin				admin;
	// 角色
	private Role				role;

	public AdminToken(Admin admin, SuperService service, int time) {
		this.admin = admin;
		// 获得权限
		authorities = service.all(Authority.class);
		// 获得角色
		role = service.get(Role.class, Conversion.toInt(admin.getRoleId()));
		// 对权限和菜单赋值
		// if (role != null && AdminParams.ADMIN.equals(admin.getName())) {
		// role.setMenus(service.all(Menu.class));
		// }
		// 对权限和菜单赋值
		if (AdminParams.ADMIN.equals(admin.getName())) {

		} else {
			// 不是创建者 权限ID不为空
			if (admin.getRoleId() != null) {
				// 获得权限
				authorities = service.eq(Authority.class, "roleId", admin.getRoleId(), -1, -1);
			}
		}
		this.id = admin.getId();
		this.password = admin.getPassword();
		this.username = admin.getName();
		this.time = time;
		this.enabled = Conversion.toInt(admin.getState()) == 1;
	}

	/**
	 * 获得管理员
	 * @return 管理员
	 */
	public Admin getAdmin() {
		return admin;
	}

	@Override
	public List<Authority> getAuthorities() {
		return authorities;
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public int getId() {
		return id == 0 && admin != null ? admin.getId() : id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public String toString() {
		return JsonEngine.toJson(this);
	}

	@Override
	public String getUsername() {
		return username;
	}

	/**
	 * 获得角色
	 * @return 角色
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * 设置角色
	 * @param role 角色
	 */
	public void setRole(Role role) {
		this.role = role;
	}
}
