package com.weicoder.socket.server;

import io.netty.channel.ChannelHandler;

import com.weicoder.socket.base.BaseServer;
import com.weicoder.socket.handler.NettyHandler;
import com.weicoder.socket.params.SocketParams;

/**
 * netty tcp 服务器
 * @author WD
 */
public final class TcpServer extends BaseServer {

	@Override
	protected ChannelHandler handler() {
		return new NettyHandler("server");
	}

	@Override
	protected int port() {
		return SocketParams.SERVER_PORT;
	}
}
