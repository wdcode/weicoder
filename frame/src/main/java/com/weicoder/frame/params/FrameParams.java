package com.weicoder.frame.params;

import com.weicoder.common.params.P;

/**
 * WdBase包所用参数读取类
 * @author WD  
 */
public final class FrameParams {
	/** 验证码出现的字符集 */
	public final static String	VERIFY_CODE			= P.getString("verify.code", "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	/** 验证码出现的字符集 */
	public final static char[]	VERIFY_CODE_CHARS	= VERIFY_CODE.toCharArray();
	/** 验证码长度 */
	public final static int		VERIFY_LENGTH		= P.getInt("verify.length", 4);
	/** 保存到session中的key */
	public final static String	VERIFY_KEY			= P.getString("verify.key", "verifyCode");
	/** 验证码字体 */
	public final static String	VERIFY_FONT			= P.getString("verify.font", "Times New Roman");
	/** 分页使用当前页的标识 */
	public final static String	PAGE_FLAG			= P.getString("page.flag",
			"pager.currentPage");
	/** 分页大小 */
	public final static int		PAGE_SIZE			= P.getInt("page.size", 20);
	/** 缓存是否有效 */
	public final static boolean	CACHE_VALID_POWER	= P.getBoolean("cache.valid.power", true);
	/** 缓存类型 */
	public final static String	CACHE_TYPE			= P.getString("cache.type", "map");

	/**
	 * 获得是否使用缓存
	 * @param name 名称
	 * @return 是否
	 */
	public static boolean getCache(String name) {
		return P.getBoolean(P.getKey("cache", "valid", name), CACHE_VALID_POWER);
	}

	private FrameParams() {}
}
