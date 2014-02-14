package com.weicoder.pay.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import com.weicoder.common.codec.Hex;
import com.weicoder.common.codec.URLCode;
import com.weicoder.common.constants.EncodingConstants;
import com.weicoder.common.constants.EncryptConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.MathUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.pay.Pay;
import com.weicoder.pay.bean.PayBean;
import com.weicoder.pay.bean.TradeBean;
import com.weicoder.pay.constants.PayConstants;
import com.weicoder.pay.params.PayParams;
import com.weicoder.web.util.HttpUtil;
import com.weicoder.web.util.RequestUtil;

/**
 * 易宝网银支付
 * @author WD
 * @since JDK6
 * @version 1.0 2012-12-04
 */
@Component
public final class Yeepay implements Pay {
	@Override
	public int type() {
		return PayConstants.TYPE_YEEPAY;
	}

	@Override
	public String pay(HttpServletRequest request, HttpServletResponse response, PayBean pay) {
		return HttpUtil.toForm(getUrl(), getParameters(request, pay), getCharset());
	}

	@Override
	public String getCharset() {
		return PayParams.YEEPAY_CHARSET;
	}

	@Override
	public TradeBean trade(HttpServletRequest request, HttpServletResponse response) {
		// 商家密钥
		String keyValue = PayParams.YEEPAY_KEY;
		// 业务类型
		String r0_Cmd = RequestUtil.getParameter(request, "r0_Cmd");
		// 商户编号
		String p1_MerId = PayParams.YEEPAY_ID;
		// 支付结果
		String r1_Code = RequestUtil.getParameter(request, "r1_Code");
		// 易宝支付交易流水号
		String r2_TrxId = RequestUtil.getParameter(request, "r2_TrxId");
		// 支付金额
		String r3_Amt = RequestUtil.getParameter(request, "r3_Amt");
		// 交易币种
		String r4_Cur = RequestUtil.getParameter(request, "r4_Cur");
		// 商品名称
		String r5_Pid = StringUtil.subString(request.getQueryString(), "r5_Pid=", "&");
		r5_Pid = URLCode.decode(r5_Pid, getCharset());
		// 商户订单号
		String r6_Order = RequestUtil.getParameter(request, "r6_Order");
		// 易宝支付会员ID
		String r7_Uid = RequestUtil.getParameter(request, "r7_Uid");
		// 商户扩展信息
		String r8_MP = RequestUtil.getParameter(request, "r8_MP");
		// 交易结果返回类型
		String r9_BType = RequestUtil.getParameter(request, "r9_BType");
		// 签名数据
		String hmac = RequestUtil.getParameter(request, "hmac");
		boolean isOK = false;
		boolean notify = false;
		// 校验返回数据包
		isOK = verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if (isOK) {
			// 在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
			if (r1_Code.equals("1")) {
				// 产品通用接口支付成功返回-浏览器重定向
				if (r9_BType.equals("1")) {
					// "callback方式:产品通用接口支付成功返回-浏览器重定向"
					// 产品通用接口支付成功返回-服务器点对点通讯
				} else if (r9_BType.equals("2")) {
					// 如果在发起交易请求时 设置使用应答机制时，必须应答以"success"开头的字符串，大小写不敏感
					try {
						notify = true;
						response.getWriter().println("SUCCESS");
						response.getWriter().flush();
					} catch (IOException e) {}
					// 产品通用接口支付成功返回-电话支付返回
				}
				// 下面页面输出是测试时观察结果使用
				// return r6_Order;
			}
		}
		// 返回空
		return new TradeBean(r6_Order, isOK, notify, r3_Amt);
	}

	/**
	 * 获得支付参数
	 * @param request
	 * @param pay
	 * @return
	 */
	public Map<String, String> getParameters(HttpServletRequest request, PayBean pay) {
		// 设置提交参数
		Map<String, String> data = Maps.getMap();
		data.put("p0_Cmd", "Buy");
		data.put("p1_MerId", PayParams.YEEPAY_ID);
		data.put("p2_Order", Conversion.toString(pay.getNo()));
		data.put("p3_Amt", MathUtil.scale(pay.getTotal(), 2).toPlainString());
		data.put("p4_Cur", "CNY");
		data.put("p5_Pid", Conversion.toString(pay.getSubject()));
		data.put("p6_Pcat", StringConstants.EMPTY);
		data.put("p7_Pdesc", Conversion.toString(pay.getBody()));
		data.put("p8_Url", PayParams.YEEPAY_REDIRECT);
		data.put("p9_SAF", "0");
		data.put("pa_MP", Conversion.toString(pay.getType()));
		data.put("pd_FrpId", Conversion.toString(pay.getDitch()).toUpperCase());
		data.put("pr_NeedResponse", "1");
		data.put("hmac", sign(data));
		// 返回参数列表
		return data;
	}

	/**
	 * 获得支付url
	 * @return
	 */
	public String getUrl() {
		return PayParams.YEEPAY_URL;
	}

	/**
	 * 签名
	 * @param data
	 * @return
	 */
	protected String sign(Map<String, String> data) {
		StringBuffer sValue = new StringBuffer();
		// 业务类型
		sValue.append(data.get("p0_Cmd"));
		// 商户编号
		sValue.append(data.get("p1_MerId"));
		// 商户订单号
		sValue.append(data.get("p2_Order"));
		// 支付金额
		sValue.append(data.get("p3_Amt"));
		// 交易币种
		sValue.append(data.get("p4_Cur"));
		// 商品名称
		sValue.append(data.get("p5_Pid"));
		// 商品种类
		sValue.append(data.get("p6_Pcat"));
		// 商品描述
		sValue.append(data.get("p7_Pdesc"));
		// 商户接收支付成功数据的地址
		sValue.append(data.get("p8_Url"));
		// 送货地址
		sValue.append(data.get("p9_SAF"));
		// 商户扩展信息
		sValue.append(data.get("pa_MP"));
		// 银行编码
		sValue.append(data.get("pd_FrpId"));
		// 应答机制
		sValue.append(data.get("pr_NeedResponse"));
		return hmacSign(sValue.toString(), PayParams.YEEPAY_KEY);
	}

	/**
	 * @param aValue
	 * @param aKey
	 * @return
	 */
	private String hmacSign(String aValue, String aKey) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(EncodingConstants.UTF_8);
			value = aValue.getBytes(EncodingConstants.UTF_8);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}

		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(EncryptConstants.ALGO_MD5);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return Hex.encode(dg);
	}

	/**
	 * 返回校验hmac方法
	 * @param hmac 商户编号
	 * @param p1_MerId 业务类型
	 * @param r0_Cmd 支付结果
	 * @param r1_Code 易宝支付交易流水号
	 * @param r2_TrxId 支付金额
	 * @param r3_Amt 交易币种
	 * @param r4_Cur 商品名称
	 * @param r5_Pid 商户订单号
	 * @param r6_Order 易宝支付会员ID
	 * @param r7_Uid 商户扩展信息
	 * @param r8_MP 交易结果返回类型
	 * @param r9_BType 交易结果返回类型
	 * @param keyValue
	 * @return
	 */
	private boolean verifyCallback(String hmac, String p1_MerId, String r0_Cmd, String r1_Code, String r2_TrxId, String r3_Amt, String r4_Cur, String r5_Pid, String r6_Order, String r7_Uid, String r8_MP, String r9_BType, String keyValue) {
		StringBuffer sValue = new StringBuffer();
		// 商户编号
		sValue.append(p1_MerId);
		// 业务类型
		sValue.append(r0_Cmd);
		// 支付结果
		sValue.append(r1_Code);
		// 易宝支付交易流水号
		sValue.append(r2_TrxId);
		// 支付金额
		sValue.append(r3_Amt);
		// 交易币种
		sValue.append(r4_Cur);
		// 商品名称
		sValue.append(r5_Pid);
		// 商户订单号
		sValue.append(r6_Order);
		// 易宝支付会员ID
		sValue.append(r7_Uid);
		// 商户扩展信息
		sValue.append(r8_MP);
		// 交易结果返回类型
		sValue.append(r9_BType);
		String sNewString = null;
		sNewString = hmacSign(sValue.toString(), keyValue);
		if (hmac.equals(sNewString)) {
			return true;
		}
		return false;
	}
}
