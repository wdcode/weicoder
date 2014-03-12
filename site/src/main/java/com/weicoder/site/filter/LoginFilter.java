package com.weicoder.site.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.base.entity.EntityUser;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.params.Params;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.site.engine.LoginEngine;

import java.io.IOException;

/**
 * 检测用户是否登陆
 * @author WD
 * @since JDK7
 * @version 1.0 2010-03-07
 */
public final class LoginFilter<L extends EntityUser> implements Filter {
	// 常量
	private final static String	REDIRECT	= "redirect";
	private final static String	LOGIN		= "login";
	/**
	 * 跳转类型
	 */
	private String				result;
	// 登录过滤Key
	private String				login;
	// 跳转页
	private String				index;
	// 不过滤页
	private String[]			special;

	/**
	 * 载入过滤器到服务 读取过滤器配置参数
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		login = filterConfig.getInitParameter(LOGIN);
		index = Params.getString(Params.getKey(LOGIN, login, "index"), StringConstants.BACKSLASH);
		special = Params.getStringArray(Params.getKey(LOGIN, login, "special"), ArrayConstants.STRING_EMPTY);
		result = Params.getString(Params.getKey(LOGIN, login, "result"), REDIRECT);
	}

	/**
	 * 过滤
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 转换Request
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		// 转换Response
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// 校验页面
		boolean find = true;
		// 获得当前页面
		String paths = httpRequest.getServletPath();
		// //如果路径为""或者"/"不检查
		// if(){}
		// 有数组
		if (!EmptyUtil.isEmpty(special)) {
			// 循环数组
			for (int i = 0; i < special.length; i++) {
				// 查找是相同页
				if (paths.indexOf(special[i]) >= 0) {
					// 不检查
					find = false;
					// 跳出循环
					break;
				}
			}
		}

		// 判断检查
		if (find) {
			// 是否登录
			if (LoginEngine.isLogin(httpRequest, login)) {
				chain.doFilter(request, response);
			} else {
				// 判断跳转方式
				if (REDIRECT.equals(result)) {
					// redirect
					httpResponse.sendRedirect(httpRequest.getContextPath() + index);
				} else {
					// forward
					httpRequest.getRequestDispatcher(httpRequest.getContextPath() + index).forward(request, response);
				}
			}

		} else {
			// 交出控制权
			chain.doFilter(request, response);
		}
	}

	/**
	 * 销毁实例调用
	 */
	public void destroy() {}
}
