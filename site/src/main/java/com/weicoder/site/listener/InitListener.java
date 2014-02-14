package com.weicoder.site.listener;

import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.xml.DOMConfigurator;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.params.Params;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.ResourceUtil;
import com.weicoder.core.config.ConfigFactory;
import com.weicoder.core.engine.QuartzEngine;
import com.weicoder.core.params.QuartzParams;
import com.weicoder.site.engine.StaticsEngine;
import com.weicoder.site.params.SiteParams;
import com.weicoder.web.params.SocketParams;
import com.weicoder.web.socket.Sockets;
import com.weicoder.base.params.BaseParams;

/**
 * 初始化监听器 在web.xml中配置 configLocation 配置文件位置,参数文件默认在classPath下
 * <p>
 * <listener> <listener-class> org.springframework.web.context.ContextLoaderListener
 * </listener-class> </listener> <context-param> <param-name>config</param-name>
 * <param-value>../config/config.xml</param-value> </context-param>
 * <p>
 * @author WD
 * @since JDK7
 * @version 1.0 2009-09-08
 */
public class InitListener implements ServletContextListener {
	/**
	 * 初始化资源
	 */
	public void contextInitialized(ServletContextEvent event) {
		// 获得Servlet上下文
		ServletContext context = event.getServletContext();
		// 设置路径
		setPath(context);
		// 设置配置文件
		setConfig(context.getInitParameter("config"));
		// 设置log4j配置
		setLog4j(context);
		// 配置数据源配置文件路径
		System.setProperty("dataSourceConfig", BaseParams.DATA_SOURCE_CONFIG);

		// 是否静态化
		if (SiteParams.STAICS_POWER) {
			StaticsEngine.start();
		}
		// 是否开启任务
		if (QuartzParams.POWER) {
			QuartzEngine.init();
		}
		// 是否开启socket
		if (SocketParams.POWER) {
			Sockets.init();
		}
	}

	/**
	 * 销毁资源
	 */
	public void contextDestroyed(ServletContextEvent event) {
		// 是否静态化
		if (SiteParams.STAICS_POWER) {
			StaticsEngine.close();
		}
		// 是否开启任务
		if (QuartzParams.POWER) {
			QuartzEngine.close();
		}
		// 是否开启socket
		if (SocketParams.POWER) {
			Sockets.close();
		}
	}

	/**
	 * 设置log4j配置
	 */
	private void setLog4j(ServletContext context) {
		// 获得log4j配置文件url
		URL url = ResourceUtil.getResource("config/log4j.xml");
		// 如果url不为空
		if (url != null) {
			DOMConfigurator.configure(url);
		}
	}

	/**
	 * 设置路径
	 */
	private void setPath(ServletContext context) {
		// 工程路径Key
		String path = "path";
		// 设置工程路径为path
		context.setAttribute(path, context.getContextPath());
		// 配置系统路径
		System.setProperty(path, context.getRealPath(StringConstants.EMPTY));
	}

	/**
	 * 设置配置文件
	 * @param config 配置文件
	 */
	private void setConfig(String fileName) {
		Params.setConfig(EmptyUtil.isEmpty(fileName) ? ConfigFactory.getConfig() : ConfigFactory.getConfig(fileName));
	}
}
