package com.weicoder.frame.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.weicoder.common.log.Logs;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.util.ResourceUtil;
import com.weicoder.web.util.HttpUtil;
import com.weicoder.web.util.ResponseUtil;
import com.weicoder.frame.util.UrlUtil; 

/**
 * 过滤wdui使用的js和css请求,过滤已/wdui开头的js和css请求
 * @author WD 
 * @version 1.0 
 */
public final class StaticFilter implements Filter {
	// 提交路径名
	private static String	urlPath		= "/wdstatic";
	// 提交文件路径名
	private static String	filePath	= "static";

	/**
	 * 初始化过滤器
	 */
	public void init(FilterConfig filterConfig) throws ServletException {}

	/**
	 * 执行过滤器
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 转换Request
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		try {
			// 获得ServletPath
			String servletPath = httpRequest.getServletPath();
			// 获得/wdui在哪开始
			int pos = servletPath.indexOf(urlPath);
			// 判断是要过滤的路径
			if (pos > -1) {
				// 判断是要过滤的路径
				if (pos > 0) {
					servletPath = servletPath.substring(pos);
				}
				// 设置ContentType
				ResponseUtil.setContentType(response, HttpUtil.getContentType(servletPath));
				// 写入到客户端
				IOUtil.write(response.getOutputStream(), UrlUtil.openStream(ResourceUtil.getResource(servletPath.replaceAll(urlPath, filePath))));
			}
		} catch (RuntimeException e) {
			Logs.warn(e);
		}
	}

	/**
	 * 销毁过滤器
	 */
	public void destroy() {}
}
