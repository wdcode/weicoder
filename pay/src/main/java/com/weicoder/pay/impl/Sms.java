package com.weicoder.pay.impl;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import com.weicoder.common.crypto.Digest;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.MathUtil;
import com.weicoder.pay.Pay;
import com.weicoder.pay.bean.PayBean;
import com.weicoder.pay.bean.TradeBean;
import com.weicoder.pay.constants.PayConstants;
import com.weicoder.pay.params.PayParams;
import com.weicoder.web.util.HttpUtil;
import com.weicoder.web.util.RequestUtil;

/**
 * 短信支付
 * @author WD
 * @since JDK7
 * @version 1.0 2014-1-13
 */
@Component
public final class Sms implements Pay {
	@Override
	public int type() {
		return PayConstants.TYPE_SMS;
	}

	@Override
	public String pay(HttpServletRequest request, HttpServletResponse response, PayBean pay) {
		return HttpUtil.toUrl(getUrl(), getParameters(request, pay));
	}

	@Override
	public String getCharset() {
		return CommonParams.ENCODING;
	}

	@Override
	public TradeBean trade(HttpServletRequest request, HttpServletResponse response) {
		// spid 商户代码 非空 5位数字商户代码
		// String spid = RequestUtil.getParameter(request, "spid");
		// md5 32位MD5 大写 非空 32位MD5 大写
		String md5 = RequestUtil.getParameter(request, "md5");
		// oid 盈华讯方平台订单号 非空 平台服务商订单号码
		String oid = RequestUtil.getParameter(request, "oid");
		// sporder 商户订单号 非空 商户提交时候产的唯一订单号
		String sporder = RequestUtil.getParameter(request, "sporder");
		// mz 支付结果 非空 整数面值
		String mz = RequestUtil.getParameter(request, "mz");
		// zdy 商户自定义 非空 商户自定义数据
		// String zdy = RequestUtil.getParameter(request, "zdy");
		// spuid 用户ID 非空 用户ID
		// String spuid = RequestUtil.getParameter(request, "spuid");
		// 加密MD5
		// 设置提交参数
		StringBuilder sb = new StringBuilder();
		sb.append(oid);
		sb.append(sporder);
		sb.append(PayParams.SMS_ID);
		sb.append(mz);
		sb.append(PayParams.SMS_KEY);
		String MD5 = Digest.md5(sb.toString()).toUpperCase();
		// 通知成功
		try {
			response.getWriter().println("okydzf");
			response.getWriter().flush();
		} catch (IOException e) {}
		return new TradeBean(sporder, MD5.equals(md5), true, mz);
	}

	/**
	 * 获得支付url
	 * @return
	 */
	public String getUrl() {
		return PayParams.SMS_URL;
	}

	/**
	 * 获得支付参数
	 * @param request
	 * @param pay
	 * @return
	 */
	public Map<String, String> getParameters(HttpServletRequest request, PayBean pay) {
		// 设置提交参数
		StringBuilder sb = new StringBuilder();
		Map<String, String> data = Maps.getMap();
		data.put("sp", PayParams.SMS_ID);
		sb.append(PayParams.SMS_ID);
		data.put("od", pay.getNo());
		sb.append(pay.getNo());
		sb.append(PayParams.SMS_KEY);
		String mz = MathUtil.scale(pay.getTotal(), 0).toPlainString();
		data.put("mz", mz);
		sb.append(mz);
		data.put("spzdy", "true");
		data.put("uid", RequestUtil.getParameter(request, "uid"));
		String spreq = RequestUtil.getParameter(request, "spreq");
		data.put("spreq", spreq);
		sb.append(spreq);
		String spsuc = RequestUtil.getParameter(request, "spsuc");
		data.put("spsuc", spsuc);
		sb.append(spsuc);
		data.put("md5", Digest.md5(sb.toString()).toUpperCase());
		// 返回参数列表
		return data;
	}
}
