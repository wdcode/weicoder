package com.weicoder.admin.security;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.weicoder.admin.params.AdminParams;
import com.weicoder.admin.po.Admin; 
import com.weicoder.admin.token.AdminToken;
import com.weicoder.base.service.SuperService;
import com.weicoder.common.util.DateUtil;
import com.weicoder.site.engine.LoginEngine;
import com.weicoder.web.util.IpUtil;

/**
 * Spring Security 登录成功处理器
 * @author WD
 * @since JDK6
 * @version 1.0 2013-1-10
 */
@Component
public final class LoginSuccess extends SavedRequestAwareAuthenticationSuccessHandler {
	// 通用业务接口
	@Resource
	protected SuperService	service;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		// 获得登录管理员
		Admin admin = ((AdminToken) authentication.getPrincipal()).getAdmin();
		// 写入登录凭证
		LoginEngine.addLogin(request, response, admin, -1);
		// 添加登录信息
		admin.setLoginIp(IpUtil.getIp(request));
		admin.setLoginTime(DateUtil.getTime());
		service.update(admin);
		setDefaultTargetUrl(AdminParams.BACK_PATH + "main.htm");
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
