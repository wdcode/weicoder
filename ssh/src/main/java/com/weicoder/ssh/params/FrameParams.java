package com.weicoder.ssh.params;

import com.weicoder.common.params.Params;

/**
 * WdBase包所用参数读取类
 * 
 * @author WD
 */
public final class FrameParams {
	/** 验证码出现的字符集 */
	public final static String	VERIFY_CODE			= Params.getString("verify.code", "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	/** 验证码出现的字符集 */
	public final static char[]	VERIFY_CODE_CHARS	= VERIFY_CODE.toCharArray();
	/** 验证码长度 */
	public final static int		VERIFY_LENGTH		= Params.getInt("verify.length", 4);
	/** 保存到session中的key */
	public final static String	VERIFY_KEY			= Params.getString("verify.key", "verifyCode");
	/** 验证码字体 */
	public final static String	VERIFY_FONT			= Params.getString("verify.font", "Times New Roman");
	/** 分页使用当前页的标识 */
	public final static String	PAGE_FLAG			= Params.getString("page.flag", "pager.currentPage");
	/** 分页大小 */
	public final static int		PAGE_SIZE			= Params.getInt("page.size", 20);
	/** 缓存是否有效 */
	public final static boolean	CACHE_VALID_POWER	= Params.getBoolean("cache.valid.power", true); 
	/** 数据源配置 */
	public final static String	DATA_SOURCE_CONFIG	= Params.getString("datasource.config", "db/db");

	/**
	 * 获得是否使用缓存<br/>
	 * 需在配置文件中配置,如果不配置或配置不对将优先使用CACHE_VALID<br/>
	 * <h2>配置方式如下: <br/>
	 * Properties: cache.valid.xxx = ? <br/>
	 * XML: {@literal <cache><valid><xxx>?</xxx></valid></cache>}</h2>
	 * 
	 * @return 是否使用缓存
	 */
	public static boolean getCache(String name) {
		return Params.getBoolean(Params.getKey("cache", "valid", name), CACHE_VALID_POWER);
	}

	private FrameParams() {
	}
}
