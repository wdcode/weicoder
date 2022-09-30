package com.weicoder.common.params;
 
/**
 * 参数使用
 * 
 * @author wdcode
 *
 */
public final class P extends Params {
	private P() {
	}

	/**
	 * @see CommonParams 参数读取类引用
	 * @author wudi
	 */
	public static final class C extends CommonParams {
	}

	/**
	 * @see HttpParams 参数读取类引用
	 * @author wudi
	 */
	public static final class H extends HttpParams {
	}

	/**
	 * @see LogParams 参数读取类引用
	 * @author wdcode
	 *
	 */
	public static final class L extends LogParams {
	}

	/**
	 * @see StateParams 参数读取类引用
	 * @author wudi
	 */
	public static final class S extends StateParams {
	}
}
