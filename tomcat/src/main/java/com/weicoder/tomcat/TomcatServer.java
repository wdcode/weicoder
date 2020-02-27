package com.weicoder.tomcat;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.startup.Tomcat;

import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.log.Logs;
import com.weicoder.tomcat.params.TomcatParams;
import com.weicoder.web.action.Actions;
import com.weicoder.web.servlet.BasicServlet;

/**
 * tomcat server
 * 
 * @author wudi
 */
public final class TomcatServer {
	/**
	 * 启动tomcat
	 */
	public static void start() {
		start(TomcatParams.PORT, TomcatParams.PATH);
	}

	/**
	 * 启动tomcat
	 * 
	 * @param port 端口
	 * @param path 路径
	 */
	public static void start(int port, String path) {
		try {
			// 声明tomcat 设置参数
			Tomcat tomcat = new Tomcat();
			tomcat.setBaseDir(SystemConstants.BASE_DIR);
			tomcat.setPort(port);
			// 声明Connector 设置参数
			Connector connector = new Connector(TomcatParams.PROTOCOL);
			connector.setPort(port);
			connector.addLifecycleListener(new AprLifecycleListener());
			tomcat.setConnector(connector);

			// 添加路径与servlet
			tomcat.addWebapp(path, SystemConstants.BASE_DIR).setReloadable(false);
			tomcat.addServlet(path, "basic", new BasicServlet()).addMapping("/*");
			tomcat.enableNaming();

			// 初始化
			Actions.init();
			tomcat.init();

			// 启动
			tomcat.start();
			tomcat.getServer().await();
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	private TomcatServer() {
	}
}
