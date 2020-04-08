package com.weicoder.netty.server;

import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

import com.weicoder.netty.base.BaseServer;
import com.weicoder.netty.handler.WebSocketHandler;
import com.weicoder.socket.params.SocketParams;

/**
 * netty tcp 服务器
 * @author WD
 */
public final class WebSocketServer extends BaseServer {

	@Override
	protected ChannelHandler handler() {
		return new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(final SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new HttpServerCodec());
				ch.pipeline().addLast(new HttpObjectAggregator(1024 * 1024));
				ch.pipeline().addLast(new WebSocketHandler("websocket"));
				ch.config().setAllocator(PooledByteBufAllocator.DEFAULT);
			}
		};
	}

	@Override
	protected int port() {
		return SocketParams.WEBSOCKET_PORT;
	}
}
