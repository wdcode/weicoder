package com.weicoder.pay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.pay.bean.PayBean;
import com.weicoder.pay.bean.TradeBean;

/**
 * 支付接口
 * @author WD
 * @since JDK6
 * @version 1.0 2012-11-30
 */
public interface Pay {
	/**
	 * 支付类型
	 * @return
	 */
	int type();

	/**
	 * 支付
	 * @param request HttpServletRequest
	 * @param pay 支付实体
	 * @return 返回支付url
	 */
	String pay(HttpServletRequest request, HttpServletResponse response, PayBean pay);

	/**
	 * 获得编码
	 * @return 获得编码
	 */
	String getCharset();

	/**
	 * 支付返回调用方法
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return 返回回调Bean 保存订单号是否成功等消息
	 */
	TradeBean trade(HttpServletRequest request, HttpServletResponse response);
}
