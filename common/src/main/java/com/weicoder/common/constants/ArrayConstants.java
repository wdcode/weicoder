package com.weicoder.common.constants;

/**
 * 数组常量
 * 
 * @author WD
 */
public sealed class ArrayConstants permits C.A {
	/** 一个空的字节数组 */
	public final static byte[]		BYTES_EMPTY		= new byte[0];
	/** 一个空的long数组 */
	public final static long[]		LONGS_EMPTY		= new long[0];
	/** 一个空的字符串数组 */
	public final static String[]	STRING_EMPTY	= new String[0];
}
