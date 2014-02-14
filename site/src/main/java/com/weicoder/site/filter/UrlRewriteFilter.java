package com.weicoder.site.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.weicoder.common.io.FileUtil;

/**
 * UrlRewriteFilter扩展 如果页面存在 直接访问不存在执行伪静态
 * @author WD
 * @since JDK6
 * @version 1.0 2013-01-23
 */
public class UrlRewriteFilter extends org.tuckey.web.filters.urlrewrite.UrlRewriteFilter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 判断是否存在
		if (FileUtil.exists(request.getServletContext().getRealPath(((HttpServletRequest) request).getServletPath()))) {
			// 存在跳转
			chain.doFilter(request, response);
		} else {
			// 不存在继续执行
			super.doFilter(request, response, chain);
		}
	}
}
