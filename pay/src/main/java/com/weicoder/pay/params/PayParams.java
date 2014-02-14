package com.weicoder.pay.params;

import com.weicoder.common.constants.EncodingConstants;
import com.weicoder.common.constants.EncryptConstants;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.params.Params;

/**
 * 支付配置读取
 * @author WD
 * @since JDK7
 * @version 1.0 2011-11-08
 */
public final class PayParams {
	/* 支付宝 */
	/**
	 * 合作身份者ID
	 */
	public final static String	ALIPAY_ID		= Params.getString("pay.alipay.id");
	/**
	 * 密钥
	 */
	public final static String	ALIPAY_KEY		= Params.getString("pay.alipay.key");
	/**
	 * 回调URL
	 */
	public final static String	ALIPAY_REDIRECT	= Params.getString("pay.alipay.redirect");
	/**
	 * 支付URL
	 */
	public final static String	ALIPAY_URL		= Params.getString("pay.alipay.url", "https://mapi.alipay.com/gateway.do");
	/**
	 * 回调URL
	 */
	public final static String	ALIPAY_SELLER	= Params.getString("pay.alipay.seller");

	/**
	 * 编码类型
	 */
	public final static String	ALIPAY_CHARSET	= Params.getString("pay.alipay.charset", CommonParams.ENCODING);
	/**
	 * 签名类型
	 */
	public final static String	ALIPAY_SIGNTYPE	= Params.getString("pay.alipay.signtype", EncryptConstants.ALGO_MD5);

	/* 财付通 */
	/**
	 * 合作身份者ID
	 */
	public final static String	TENPAY_ID		= Params.getString("pay.tenpay.id");
	/**
	 * 密钥
	 */
	public final static String	TENPAY_KEY		= Params.getString("pay.tenpay.key");
	/**
	 * 编码类型
	 */
	public final static String	TENPAY_CHARSET	= Params.getString("pay.tenpay.charset", CommonParams.ENCODING);
	/**
	 * 支付URL
	 */
	public final static String	TENPAY_URL		= Params.getString("pay.tenpay.url", "https://gw.tenpay.com/gateway/pay.htm");
	/**
	 * 回调URL
	 */
	public final static String	TENPAY_RETURN	= Params.getString("pay.tenpay.return");
	/**
	 * 回调URL
	 */
	public final static String	TENPAY_NOTIFY	= Params.getString("pay.tenpay.notify");

	/* 易宝 */
	/**
	 * 合作身份者ID
	 */
	public final static String	YEEPAY_ID		= Params.getString("pay.yeepay.id");
	/**
	 * 密钥
	 */
	public final static String	YEEPAY_KEY		= Params.getString("pay.yeepay.key");
	/**
	 * 支付URL
	 */
	public final static String	YEEPAY_URL		= Params.getString("pay.yeepay.url", "https://www.yeepay.com/app-merchant-proxy/node");
	/**
	 * 回调URL
	 */
	public final static String	YEEPAY_REDIRECT	= Params.getString("pay.yeepay.redirect");
	/**
	 * 编码类型
	 */
	public final static String	YEEPAY_CHARSET	= Params.getString("pay.yeepay.charset", EncodingConstants.GBK);

	/* 手机卡 */
	/**
	 * 合作身份者ID
	 */
	public final static String	CARD_ID			= Params.getString("pay.card.id");
	/**
	 * 密钥
	 */
	public final static String	CARD_KEY		= Params.getString("pay.card.key");
	/**
	 * 支付URL
	 */
	public final static String	CARD_URL		= Params.getString("pay.card.url", "https://www.yeepay.com/app-merchant-proxy/node");
	/**
	 * 回调URL
	 */
	public final static String	CARD_REDIRECT	= Params.getString("pay.card.redirect");
	/**
	 * 编码类型
	 */
	public final static String	CARD_CHARSET	= Params.getString("pay.card.charset", EncodingConstants.GBK);

	/* 短信支付 */
	/**
	 * 合作身份者ID
	 */
	public final static String	SMS_ID			= Params.getString("pay.sms.id");
	/**
	 * 密钥
	 */
	public final static String	SMS_KEY			= Params.getString("pay.sms.key");
	/**
	 * 支付URL
	 */
	public final static String	SMS_URL			= Params.getString("pay.sms.url", "http://ydzf.vnetone.com/Default_mo.aspx");

	/**
	 * 私有构造
	 */
	private PayParams() {}
}
