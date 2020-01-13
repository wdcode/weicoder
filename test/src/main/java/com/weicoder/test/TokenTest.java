package com.weicoder.test;

import com.weicoder.common.lang.Bytes;
import com.weicoder.common.token.TokenBean;
import com.weicoder.common.token.TokenEngine;
import com.weicoder.core.json.JsonEngine;

public class TokenTest {
	public static void main(String[] args) {
		String token1 = "57506BA0B5FFBD1CE4365702E7D98F4DEE0D61E9C382C8FD04582A09";
		String token2 = "57506BAFB5FFBD6CE4365702E7F5DF4DEE0061E9C3F2C86D06582A09";
		System.out.println(token1.length());
		System.out.println(token2.length());
		System.out.println("7506bab5ffbdce41".length());
		boolean is = false;
		byte[] b = Bytes.toBytes(is);
		System.out.println(b.length);
		System.out.println(b[0] = 123);
		System.out.println(Bytes.toBoolean(b));

		TokenBean t1 = TokenEngine.decrypt(token1);
		TokenBean t2 = TokenEngine.decrypt(token2);
		System.out.println(JsonEngine.toJson(t1));
		System.out.println(JsonEngine.toJson(t2));
	}
}
