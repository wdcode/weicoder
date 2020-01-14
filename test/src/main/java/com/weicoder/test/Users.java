package com.weicoder.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 * 
 * @author WD
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
	private boolean anchor;
	private byte    admin;
	private short   nid;
	private int     sex;
	private float   exp;
	private long    uid;
	private double  level;
	private String  nickname;
	private byte[]  b;
}
