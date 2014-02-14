package com.weicoder.pay.constants;

/**
 * 支付常量
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-23
 */
public final class PayConstants {
	/**
	 * 支付常量 支付宝
	 */
	public final static int	TYPE_ALIPAY	= 1;
	/**
	 * 支付常量 财付通
	 */
	public final static int	TYPE_TENPAY	= 2;
	/**
	 * 支付常量 易宝
	 */
	public final static int	TYPE_YEEPAY	= 3;
	/**
	 * 支付常量 短信 深圳盈华讯方移动短信支付
	 */
	public final static int	TYPE_SMS	= 4;
	/**
	 * 支付常量 手机卡
	 */
	public final static int	TYPE_CARD	= 5;

	private PayConstants() {}
}
