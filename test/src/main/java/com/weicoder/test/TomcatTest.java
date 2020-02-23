package com.weicoder.test;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
 

public class TomcatTest {
	public static int    TOMCAT_PORT     = 8080;
	public static String TOMCAT_HOSTNAME = "127.0.0.1";
	public static String WEBAPP_PATH     = "src/main";
	public static String WEBINF_CLASSES  = "/WEB-INF/classes";
	public static String CLASS_PATH      = "target/classes";
	public static String INTERNAL_PATH   = "/";
	
	 private static int port = 8080;
	    private static String contextPath = "/upload";

	public static void main(String[] args) throws LifecycleException {
//		Tomcat tomcat = new Tomcat();
//		tomcat.setPort(TOMCAT_PORT);
//		tomcat.setHostname(TOMCAT_HOSTNAME);
//		tomcat.setBaseDir("."); // tomcat 信息保存在项目下
//
//		/*
//		 * https://www.cnblogs.com/ChenD/p/10061008.html
//		 */
//		StandardContext myCtx = (StandardContext) tomcat.addWebapp("/upload",
//				System.getProperty("user.dir") + File.separator + WEBAPP_PATH);
//		/*
//		 * true时：相关classes | jar 修改时，会重新加载资源，不过资源消耗很大 autoDeploy 与这个很相似，tomcat自带的热部署不是特别可靠，效率也不高。生产环境不建议开启。 相关文档：
//		 * http://www.blogjava.net/wangxinsh55/archive/2011/05/31/351449.html
//		 */
//		myCtx.setReloadable(false);
//		// 上下文监听器
//		myCtx.addLifecycleListener(new AprLifecycleListener());
//
//		/*
//		 * String webAppMount = System.getProperty("user.dir") + File.separator + TomcatStart.CLASS_PATH;
//		 * WebResourceRoot root = new StandardRoot(myCtx); root.addPreResources( new DirResourceSet(root,
//		 * TomcatStart.WEBINF_CLASSES, webAppMount, TomcatStart.INTERNAL_PATH));
//		 */
//
//		// 注册servlet
//		tomcat.addServlet("/upload", "uploadServlet", new UploadServlet());
//		// servlet mapping
//		myCtx.addServletMappingDecoded("/upload.do", "uploadServlet");
//		tomcat.init();
//		tomcat.start();
//		tomcat.getServer().await();
		
		Tomcat tomcat = new Tomcat();
        String baseDir = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        tomcat.setBaseDir(baseDir);
        tomcat.setPort(port);
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(port);
        tomcat.setConnector(connector);
       
        tomcat.addWebapp(contextPath, baseDir);
//        tomcat.addServlet(contextPath, "uploadServlet", new UploadServlet());
        tomcat.enableNaming();
       
        //手动创建
        //tomcat.getConnector();
        tomcat.start();
        tomcat.getServer().await(); 
	}
}
