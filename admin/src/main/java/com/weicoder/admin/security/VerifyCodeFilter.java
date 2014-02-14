package com.weicoder.admin.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import com.weicoder.admin.params.AdminParams;
import com.weicoder.web.util.VerifyCodeUtil;

/**
 * 验证码过滤器
 * @author WD
 * @since JDK6
 * @version 1.0 2013-01-08
 */
@Component
public class VerifyCodeFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		// 是登录过程
		if (request.getRequestURI().indexOf("login") > -1) {
			// 校验验证码
			if (!VerifyCodeUtil.check(request, response, request.getParameter("verifyCode"))) {
				// 跳转到主页
				response.sendRedirect(request.getContextPath() + AdminParams.BACK_PATH);
				return;
			}
		}
		// 交出权限
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {}
}
