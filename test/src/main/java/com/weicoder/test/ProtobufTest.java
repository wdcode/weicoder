package com.weicoder.test;

import com.weicoder.protobuf.ProtobufEngine;

public class ProtobufTest {

	public static void main(String[] args) {
		Users s = new Users(true, Byte.parseByte("1"), Short.parseShort("2"), 3, 4.5f, 6, 7.8, "admin", new byte[]{9, 10, 11});
		System.out.println(s);
		byte[] b = ProtobufEngine.toBytes(s);
		System.out.println(b.length);
		Users u = ProtobufEngine.toBean(b, Users.class);
		System.out.println(u);
	}
}
