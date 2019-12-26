package com.weicoder.admin.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.weicoder.admin.constants.AdminConstants;
import com.weicoder.admin.params.AdminParams;
import com.weicoder.admin.template.TemplateEngine;

/**
 * 后台初始化监听器
 * @author WD
 * @since JDK7
 * @version 1.0 2009-12-07
 */
@WebListener
public class AdminInitListener implements ServletContextListener {
	/**
	 * 初始化资源
	 */
	public void contextInitialized(ServletContextEvent event) {
		// 执行父方法
		// super.contextInitialized(event);
		// 设置后台主题路径
		event.getServletContext().setAttribute(AdminConstants.THEME_BACK, AdminParams.BACK_THEME);
		// 设置ClassPath
		TemplateEngine.classPath = event.getServletContext().getRealPath("WEB-INF/classes")
				+ File.separator;
		// 加载后台模板
		TemplateEngine.init();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {}
}