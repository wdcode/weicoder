package com.weicoder.socket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;

/**
 * Netty 处理器
 * 
 * @author WD
 */
@Sharable
public final class WebSocketHandler extends NettyHandler {
	// websocket路经
	private static final String			WEBSOCKET_PATH	= "ws://%s/websocket";
	// websocket处理器
	private WebSocketServerHandshaker	handshaker;

	public WebSocketHandler(String name) {
		super(name);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 判断数据类型
		if (msg instanceof FullHttpRequest) {
			// http握手请求
			http(ctx, (FullHttpRequest) msg);
		} else if (msg instanceof WebSocketFrame) {
			// websocket请求
			websocket(ctx, (WebSocketFrame) msg);
			ReferenceCountUtil.release(msg);
		} else
			super.channelRead(ctx, msg);
	}

	/*
	 * http请求
	 */
	private void http(ChannelHandlerContext ctx, FullHttpRequest req) {
		// 请求不成功
		if (!req.decoderResult().isSuccess()) {
			send(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
			return;
		}

		// 只允许get请求
		if (req.method() != HttpMethod.GET) {
			send(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
			return;
		}

		// 处理websocket
		WebSocketServerHandshakerFactory	wsFactory	= new WebSocketServerHandshakerFactory(String.format(WEBSOCKET_PATH, req.headers().get(HttpHeaderNames.HOST)), null, true,
				5 * 1024 * 1024);
		WebSocketServerHandshaker			handshaker	= wsFactory.newHandshaker(req);
		if (handshaker == null) {
			WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
		} else {
			handshaker.handshake(ctx.channel(), req);
		}
	}

	/*
	 * websocket请求
	 */
	private void websocket(ChannelHandlerContext ctx, WebSocketFrame frame) {
		// 关闭websocket
		if (frame instanceof CloseWebSocketFrame) {
			handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
			return;
		}
		// ping模式websocket
		if (frame instanceof PingWebSocketFrame) {
			ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
			return;
		}
		// websocket流模式
		read(ctx, frame.content());
//		if (frame instanceof BinaryWebSocketFrame) {
//			read(ctx, frame.content());
//		} else if (frame instanceof TextWebSocketFrame) {
//			read(ctx, frame.content());
//		}
	}

	/*
	 * 发送前端内容
	 */
	private static void send(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
		// 如果请求码不是成功OK 200
		if (!res.status().equals(HttpResponseStatus.OK)) {
			ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
			res.content().writeBytes(buf);
			buf.release();
			HttpUtil.setContentLength(res, res.content().readableBytes());
		}

		// 发送消息到前端
		ChannelFuture f = ctx.channel().writeAndFlush(res);
		if (!HttpUtil.isKeepAlive(req) || !res.status().equals(HttpResponseStatus.OK)) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}
}
