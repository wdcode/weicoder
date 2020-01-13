package com.weicoder.test;

import com.weicoder.common.log.Logs;
import com.weicoder.email.EmailEngine;

public class EmailTest {

	public static void main(String[] args) {
		EmailEngine.send("2198224846@qq.com", "test", "hhhhhhh");
		Logs.debug("email...");
	}
}
