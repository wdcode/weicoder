package com.weicoder.test;

import org.junit.jupiter.api.Test;

import com.weicoder.common.log.Logs;
import com.weicoder.email.EmailEngine;

public class EmailTest {
	@Test
	public void main() {
		EmailEngine.send("2198224846@qq.com", "test", "hhhhhhh");
		Logs.debug("email...");
	}
}
