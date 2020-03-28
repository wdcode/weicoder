package com.weicoder.test;

import java.util.Arrays;

import com.weicoder.common.lang.Bytes;
import com.weicoder.common.socket.TcpClient;

public class TcpCilent {

	public static void main(String[] args) {
		Users s = new Users(6, true, Byte.parseByte("1"), Short.parseShort("2"), 3, 4.5f, 7.8, "admin",
				new byte[]{9, 10, 11});
		byte[] data = Bytes.toBytes(s);
		System.out.println(data.length);
		System.out.println(TcpClient.send("127.0.0.1", 8888, data, true).length);
		System.out.println(TcpClient.write("127.0.0.1", 8888, Arrays.copyOf(data, 100000), true).length);
		System.out.println(TcpClient.asyn("127.0.0.1", 8888, Arrays.copyOf(data, 200000), true).length);
		System.out.println("tcp socket server start...");
	}

}
