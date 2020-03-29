package com.weicoder.socket;

import com.weicoder.common.init.Init;
import com.weicoder.socket.params.SocketParams;
import com.weicoder.socket.server.TcpServer;
import com.weicoder.socket.server.WebSocketServer;

/**
 * socket初始化
 * 
 * @author wudi
 */
public class SocketInit implements Init {

	@Override
	public void init() {
		// 初始化 tcp服务端
		if (SocketParams.SERVER_PORT > 0)
			new TcpServer().bind();
		// 初始化 websocket服务端
		if (SocketParams.WEBSOCKET_PORT > 0)
			new WebSocketServer().bind();
	}
}
