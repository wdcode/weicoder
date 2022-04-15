package com.test.xml;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBean {
	// 靓号 
	private long id;
	// 靓号 
	private long uid;
	// 用户昵称 
	private String nickname;
}
