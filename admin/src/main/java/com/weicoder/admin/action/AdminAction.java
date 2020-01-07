package com.weicoder.admin.action;

import static com.weicoder.dao.service.SuperService.DAO;

import com.weicoder.admin.po.Admin;
import com.weicoder.admin.state.ErrorCode;
import com.weicoder.admin.token.AdminToken;
import com.weicoder.admin.vo.Result;
import com.weicoder.common.crypto.Digest;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.web.annotation.Action;
import com.weicoder.web.annotation.State;
import com.weicoder.web.validator.annotation.Token;

/**
 * 管理员action
 * 
 * @author wudi
 */
@Action
@State
public class AdminAction {
	/**
	 * 注册管理员
	 * 
	 * @param  admin 管理员
	 * @return
	 */
	public Object register(Admin admin) {
		// 判断用户名与密码
		if (admin == null || EmptyUtil.isEmpty(admin.getName()) || EmptyUtil.isEmpty(admin.getPassword()))
			return ErrorCode.ADMIN_NULL;
		// 判断用户名长度
		if (admin.getName().length() > 8)
			return ErrorCode.ADMIN_LEN;
		// 设置密码
		admin.setPassword(Digest.password(admin.getPassword()));
		// 入库并返回
		return DAO.insert(admin);
	}

	/**
	 * 管理员登录
	 * 
	 * @param  admin 管理员
	 * @return
	 */
	public Object login(String name, String password, String ip) {
		// 查询获得用户实体
		Admin bean = DAO.get(Admin.class, name);
		// 用户是否存在
		if (bean == null)
			return ErrorCode.ADMIN_NOT;
		// 判断密码是否相等
		if (!bean.getPassword().equals(Digest.password(password)))
			return ErrorCode.ADMIN_PWD;
		// 登录成功 生成Token返回
		return new Result(AdminToken.encrypt(name, ip), bean);
	}

	/**
	 * 修改管理员密码
	 * 
	 * @return
	 * @throws Exception
	 */
	@Token
	public Object password(String token, String old, String pwd) {
		// 获得用户名
		String name = AdminToken.decrypt(token);
		// 获取用户
		Admin admin = DAO.get(Admin.class, name);
		// 用户是否存在
		if (admin == null)
			return ErrorCode.ADMIN_NOT;
		// 判断密码是否相等
		if (!admin.getPassword().equals(Digest.password(old)))
			return ErrorCode.ADMIN_PWD;
		// 设置新密码
		admin.setPassword(Digest.password(pwd));
		// 更新并返回
		return DAO.update(admin);
	}
}
