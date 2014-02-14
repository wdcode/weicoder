package com.weicoder.pay.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import com.weicoder.common.codec.Hex;
import com.weicoder.common.crypto.Digest;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.MathUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.pay.Pay;
import com.weicoder.pay.bean.PayBean;
import com.weicoder.pay.bean.TradeBean;
import com.weicoder.pay.constants.PayConstants;
import com.weicoder.pay.params.PayParams;
import com.weicoder.web.http.HttpEngine;
import com.weicoder.web.util.HttpUtil;
import com.weicoder.web.util.RequestUtil;

/**
 * 支付宝即时到帐
 * @author WD
 * @since JDK6
 * @version 1.0 2012-12-04
 */
@Component
public final class Alipay implements Pay {
	@Override
	public int type() {
		return PayConstants.TYPE_ALIPAY;
	}

	@Override
	public String pay(HttpServletRequest request, HttpServletResponse response, PayBean pay) {
		// 获得提交参数
		Map<String, String> params = getParameters(request, pay);
		// 清空_input_charset
		params.remove("_input_charset");
		// 获得提交表单
		String form = HttpUtil.toForm(getUrl(), params, getCharset());
		// 处理表单并返回
		return StringUtil.replace(form, getUrl(), getUrl() + "?_input_charset=" + getCharset());
	}

	@Override
	public TradeBean trade(HttpServletRequest request, HttpServletResponse response) {
		// 交易状态
		String tradeStatus = RequestUtil.getParameter(request, "trade_status");
		// 通知校验ID
		String notifyId = RequestUtil.getParameter(request, "notify_id");
		// 通知校验码
		String sign = RequestUtil.getParameter(request, "sign");
		// 计算出校验码
		String mysign = sign(RequestUtil.getParameters(request));
		// 校验URL
		String veryfyUrl = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner=" + PayParams.ALIPAY_ID + "&notify_id=" + notifyId;
		// 获得交易网站验证
		boolean verifyResponse = Conversion.toBoolean(HttpEngine.get(veryfyUrl));
		// 获得交易状态
		boolean status = "TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus);
		// 返回实体
		return new TradeBean(RequestUtil.getParameter(request, "out_trade_no"), verifyResponse && status && sign.equals(mysign), false, request.getParameter("total_fee"));
	}

	/**
	 * 获得提交参数
	 * @param pay 支付实体
	 * @return 支付参数
	 */
	public Map<String, String> getParameters(HttpServletRequest request, PayBean pay) {
		// 设置提交参数
		Map<String, String> data = Maps.getMap();
		data.put("service", "create_direct_pay_by_user");
		data.put("partner", PayParams.ALIPAY_ID);
		data.put("return_url", PayParams.ALIPAY_REDIRECT);
		data.put("notify_url", PayParams.ALIPAY_REDIRECT);
		data.put("_input_charset", PayParams.ALIPAY_CHARSET);
		data.put("payment_type", "1");
		data.put("seller_email", PayParams.ALIPAY_SELLER);
		data.put("out_trade_no", pay.getNo());
		data.put("subject", pay.getSubject());
		data.put("body", pay.getBody());
		data.put("total_fee", MathUtil.scale(pay.getTotal(), 2).toPlainString());
		data.put("paymethod", "post");
		data.put("sign", sign(data));
		data.put("sign_type", PayParams.ALIPAY_SIGNTYPE);
		// 返回参数列表
		return data;
	}

	/**
	 * 获得支付url
	 * @return
	 */
	public String getUrl() {
		return PayParams.ALIPAY_URL;
	}

	/**
	 * 加密签名
	 * @param data 参数
	 * @return 签名
	 */
	protected String sign(Map<String, String> data) {
		data.remove("sign");
		data.remove("sign_type");
		data.remove("key");
		return Hex.encode(Digest.getMessageDigest(StringUtil.toBytes(HttpUtil.toParameters(data) + PayParams.ALIPAY_KEY, getCharset()), PayParams.ALIPAY_SIGNTYPE));
	}

	@Override
	public String getCharset() {
		return PayParams.ALIPAY_CHARSET;
	}
}
