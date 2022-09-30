package com.weicoder.test;

import java.util.Arrays;

import com.weicoder.common.log.Logs;
import com.weicoder.common.socket.TcpServers; 
import com.weicoder.common.util.U;

public class TcpServer {

	public static void main(String[] args) {
		TcpServers.nio(8888, b -> {
			long time = System.currentTimeMillis();
			Logs.info("aio socket accept end time={} b={}", U.D.diff(time), b.length);
			return Arrays.copyOf(b, b.length / 2);
		});
		System.out.println("tcp socket server start...");
	}

}
