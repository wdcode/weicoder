package com.weicoder.tomcat;

import com.weicoder.common.lang.C;
import com.weicoder.common.util.StringUtil; 
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
			if (StringUtil.equals("stop", args[0])) {
				TomcatServer.stop();
				return;
			}
			port = C.toInt(args[0]);
		}
		if (args.length > 1) {
			path = args[1];
		}
		// 启动tomcat
		TomcatServer.start(port, path);
	}
}
