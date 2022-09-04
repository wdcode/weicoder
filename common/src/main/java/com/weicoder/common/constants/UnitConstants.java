package com.weicoder.common.constants;

/**
 * 关于计算机的换算常量 比如位 字节 等
 * 
 * @author WD
 */
public sealed class UnitConstants permits C.U {
	/** 一个字节占用的位 */
	public final static int	BYTE_BIT	= 8;
	/** 一个字节占用的空间 */
	public final static int	BYTE		= 1;
	/** 一个短整型占用的位 */
	public final static int	SHORT_BIT	= 16;
	/** 一个短整型占用的空间 */
	public final static int	SHORT		= 2;
	/** 一个整型占用的位 */
	public final static int	INT_BIT		= 32;
	/** 一个整型占用的空间 */
	public final static int	INT			= 4;
	/** 一个长整型占用的位 */
	public final static int	LONG_BIT	= 64;
	/** 一个长整型占用的空间 */
	public final static int	LONG		= 8;
}
