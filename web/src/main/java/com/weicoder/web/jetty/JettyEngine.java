package com.weicoder.web.jetty;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import com.weicoder.common.log.Logs;
import com.weicoder.web.params.JettyParams;

/**
 * Jetty 启动器
 * @author WD 
 * @version 1.0 
 */
public final class JettyEngine {

	/**
	 * 启动 Jetty 服务器
	 */
	public static void startup() {
		startup(JettyParams.HOST, JettyParams.PORT, JettyParams.WEBAPP, JettyParams.CONTEXT);
	}

	/**
	 * 启动 Jetty 服务器
	 * @param host 监控主机
	 * @param port 监控端口
	 * @param webapp war路径
	 * @param context context路径
	 */
	public static void startup(String host, int port, String webapp, String context) {
		// 声明jetty服务器
		Server server = new Server();
		// 声明连接器
		ServerConnector connector = new ServerConnector(server);
		// 设置属性
		connector.setHost(host);
		connector.setPort(port);
		connector.setReuseAddress(true);
		connector.setAcceptQueueSize(2048);
		server.setConnectors(new Connector[] { connector });
		// 设置webapp路径
		server.setHandler(new WebAppContext(webapp, context));

		// 启动
		try {
			server.start();
			server.join();
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	private JettyEngine() {}
}
