package com.weicoder.tomcat;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.startup.Tomcat;

import com.weicoder.common.C; 
import com.weicoder.common.init.Inits;
import com.weicoder.common.log.Logs; 
import com.weicoder.tomcat.params.TomcatParams; 
import com.weicoder.web.servlet.BasicServlet;  

/**
 * tomcat server
 * 
 * @author wudi
 */
public final class TomcatServer {
	// tomcat服务器
	private static Tomcat tomcat = null;

	/**
	 * 启动tomcat
	 */
	public static void start() {
		start(TomcatParams.PORT, TomcatParams.PATH);
	}

	/**
	 * 关闭tomcat
	 */
	public static void stop() {
		if (tomcat != null) {
			try {
				tomcat.stop();
			} catch (LifecycleException e) {
				Logs.error(e);
			}
		}
	}

	/**
	 * 启动tomcat
	 * 
	 * @param port 端口
	 * @param path 路径
	 */
	public static void start(int port, String path) {
		// 如果已经启动 返回
		if (tomcat != null) {
			Logs.warn("tomcat server port={} path={} already start");
			return;
		}
		try {
			// 声明tomcat 设置参数
			tomcat = new Tomcat();
//			tomcat.setBaseDir(C.O.BASE_DIR);
			tomcat.setBaseDir(C.S.BACKSLASH); 
			tomcat.setPort(port);
			// 声明Connector 设置参数
			Connector connector = new Connector(TomcatParams.PROTOCOL);
			connector.setPort(port);
			connector.addLifecycleListener(new AprLifecycleListener());
			tomcat.setConnector(connector);

			// 添加路径与servlet
//			tomcat.addContext(path, C.O.BASE_DIR);
//			tomcat.addWebapp(path, C.O.BASE_DIR).setReloadable(false);
			tomcat.addContext(path, C.S.BACKSLASH);
			tomcat.addWebapp(path, C.S.BACKSLASH).setReloadable(false);
			tomcat.addServlet(path, "basic", BasicServlet.class.getName()).addMapping("/*");
//			tomcat.addServlet(path, "basic", new BasicServlet()).addMapping("/*");
//			tomcat.addServlet(path, "basic", new TomcatServlet()).addMapping("/*");
//			tomcat.addServlet(path, "basic", "com.weicoder.web.servlet.BasicServlet").addMapping("/*");
			tomcat.enableNaming();

			// 初始化
			Inits.init();
			tomcat.init();

			// 启动
			tomcat.start();
			tomcat.getServer().await();
			Logs.info("tomcat server port={} path={} start");
		} catch (Exception e) {
			Logs.error(e);
		}
	} 

	private TomcatServer() {
	}
}
