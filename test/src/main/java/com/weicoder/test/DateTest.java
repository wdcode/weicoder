package com.weicoder.test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.weicoder.common.util.MathUtil;

public class DateTest {

	public static void main(String[] args) {
		System.out.println(LocalDateTime.now());
		long curr = System.currentTimeMillis();
		Instant i = Instant.now();
		System.out.println(curr);
		System.out.println(i.toEpochMilli());
		System.out.println(i.getEpochSecond());
		System.out.println(i.getNano());
		System.out.println(System.nanoTime()); 
		System.out.println(curr / 1000);
		System.out.println(MathUtil.divide(curr, 1000).intValue()); 
		System.out.println(LocalDateTime.ofInstant(i, ZoneId.systemDefault()));
		Instant n = Instant.ofEpochMilli(curr);
		System.out.println(n.toEpochMilli());
		System.out.println(n.getEpochSecond());
		System.out.println(LocalDateTime.ofInstant(n, ZoneId.systemDefault()));
		Instant s = Instant.ofEpochSecond(curr / 1000);
		System.out.println(s.toEpochMilli());
		System.out.println(s.getEpochSecond());
		System.out.println(LocalDateTime.ofInstant(s, ZoneId.systemDefault()));
	} 
}
