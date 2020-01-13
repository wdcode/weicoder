package com.weicoder.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Param {
	private String type     = "mobile";
	private String mobile   = "13261115158";
	private String password = "123456";
}
