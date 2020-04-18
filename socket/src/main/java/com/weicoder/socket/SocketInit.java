package com.weicoder.socket;
 
import com.weicoder.common.init.Init;
import com.weicoder.netty.server.TcpServer;
import com.weicoder.netty.server.WebSocketServer;
import com.weicoder.socket.params.SocketParams;

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
//			((Server) C.newInstance("com.weicoder.netty.server.TcpServer")).bind();
			new TcpServer().bind();
		// 初始化 websocket服务端
		if (SocketParams.WEBSOCKET_PORT > 0)
//			((Server) C.newInstance("com.weicoder.netty.server.WebSocketServer")).bind();
			new WebSocketServer().bind();
	}
}
