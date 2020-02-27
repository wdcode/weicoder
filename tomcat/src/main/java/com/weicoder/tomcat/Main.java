package com.weicoder.tomcat;

import com.weicoder.extend.C;
import com.weicoder.tomcat.params.TomcatParams;

/**
 * 主入口 jar包启动
 * 
 * @author wudi
 */
public class Main {

	public static void main(String[] args) {
		// 声明默认端口与路径
		int port = TomcatParams.PORT;
		String path = TomcatParams.PATH;
		// 如果有输入参数 更改端口路径
		if (args.length > 0) {
			port = C.toInt(args[0]);
		}
		if (args.length > 1) {
			path = args[1];
		}
		// 启动tomcat
		TomcatServer.start(port, path);
	}
}
