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
import com.weicoder.common.constants.EncodingConstants;
import com.weicoder.common.constants.EncryptConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.MathUtil;
import com.weicoder.pay.Pay;
import com.weicoder.pay.bean.PayBean;
import com.weicoder.pay.bean.TradeBean;
import com.weicoder.pay.constants.PayConstants;
import com.weicoder.pay.params.PayParams;
import com.weicoder.web.util.HttpUtil;
import com.weicoder.web.util.RequestUtil;

/**
 * 手机卡支付
 * @author WD 2014-1-6
 */
@Component
public final class Card implements Pay {
	@Override
	public int type() {
		return PayConstants.TYPE_CARD;
	}

	@Override
	public String pay(HttpServletRequest request, HttpServletResponse response, PayBean pay) {
		return HttpUtil.toForm(getUrl(), getParameters(request, pay), getCharset());
	}

	@Override
	public String getCharset() {
		return PayParams.CARD_CHARSET;
	}

	@Override
	public TradeBean trade(HttpServletRequest request, HttpServletResponse response) {
		// 商家密钥
		String keyValue = PayParams.CARD_KEY;
		// 业务类型
		String r0_Cmd = RequestUtil.getParameter(request, "r0_Cmd");
		// 商户编号
		String p1_MerId = PayParams.CARD_ID;
		// 支付结果
		String r1_Code = RequestUtil.getParameter(request, "r1_Code");
		// 商户订单号
		String p2_Order = RequestUtil.getParameter(request, "p2_Order");
		// 支付金额
		String p3_Amt = RequestUtil.getParameter(request, "p3_Amt");
		// 支付方式
		String p4_FrpId = RequestUtil.getParameter(request, "p4_FrpId");
		// 卡序列号组
		String p5_CardNo = RequestUtil.getParameter(request, "p5_CardNo");
		// 确认金额组
		String p6_confirmAmount = RequestUtil.getParameter(request, "p6_confirmAmount");
		// 实际金额组
		String p7_realAmount = RequestUtil.getParameter(request, "p7_realAmount");
		// 卡状态组
		String p8_cardStatus = RequestUtil.getParameter(request, "p8_cardStatus");
		// 扩展信息
		String p9_MP = RequestUtil.getParameter(request, "p9_MP");
		// 支付余额 注：此项仅为订单成功,并且需要订单较验时才会有值。失败订单的余额返部返回原卡密中
		String pb_BalanceAmt = RequestUtil.getParameter(request, "pb_BalanceAmt");
		// 余额卡号 注：此项仅为订单成功,并且需要订单较验时才会有值
		String pc_BalanceAct = RequestUtil.getParameter(request, "pc_BalanceAct");
		// 签名数据
		String hmac = RequestUtil.getParameter(request, "hmac");
		boolean isOK = false;
		boolean notify = false;
		// 校验返回数据包
		isOK = verifyCallback(hmac, r0_Cmd, r1_Code, p1_MerId, p2_Order, p3_Amt, p4_FrpId, p5_CardNo, p6_confirmAmount, p7_realAmount, p8_cardStatus, p9_MP, pb_BalanceAmt, pc_BalanceAct, keyValue);
		if (isOK) {
			// 在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
			if (r1_Code.equals("1")) {
				// 如果在发起交易请求时 设置使用应答机制时，必须应答以"success"开头的字符串，大小写不敏感
				try {
					notify = true;
					response.getWriter().println("SUCCESS");
					response.getWriter().flush();
				} catch (IOException e) {}
			}
		}
		// 返回空
		return new TradeBean(p2_Order, isOK, notify, p3_Amt);
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
		data.put("p0_Cmd", "ChargeCardDirect");
		data.put("p1_MerId", PayParams.CARD_ID);
		data.put("p2_Order", Conversion.toString(pay.getNo()));
		data.put("p3_Amt", MathUtil.scale(pay.getTotal(), 2).toPlainString());
		data.put("p4_verifyAmt", "true");
		data.put("p5_Pid", Conversion.toString(pay.getSubject()));
		data.put("p6_Pcat", StringConstants.EMPTY);
		data.put("p7_Pdesc", Conversion.toString(pay.getBody()));
		data.put("p8_Url", PayParams.CARD_REDIRECT);
		data.put("pa_MP", Conversion.toString(pay.getType()));
		data.put("pd_FrpId", Conversion.toString(pay.getDitch()).toUpperCase());
		data.put("pr_NeedResponse", "1");
		data.put("pa7_cardAmt", RequestUtil.getParameter(request, "pa7_cardAmt"));
		data.put("pa8_cardNo", RequestUtil.getParameter(request, "pa8_cardNo"));
		data.put("pa9_cardPwd", RequestUtil.getParameter(request, "pa9_cardPwd"));
		data.put("hmac", sign(data));
		// 返回参数列表
		return data;
	}

	/**
	 * 获得支付url
	 * @return
	 */
	public String getUrl() {
		return PayParams.CARD_URL;
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
		sValue.append(data.get("p4_verifyAmt"));
		// 商品名称
		sValue.append(data.get("p5_Pid"));
		// 商品种类
		sValue.append(data.get("p6_Pcat"));
		// 商品描述
		sValue.append(data.get("p7_Pdesc"));
		// 商户接收支付成功数据的地址
		sValue.append(data.get("p8_Url"));
		// 送货地址
		sValue.append(data.get("pa_MP"));
		// 商户扩展信息
		sValue.append(data.get("pa_MP"));
		// 卡组信息
		sValue.append(data.get("pa7_cardAmt"));
		sValue.append(data.get("pa8_cardNo"));
		sValue.append(data.get("pa9_cardPwd"));
		// 银行编码
		sValue.append(data.get("pd_FrpId"));
		// 应答机制
		sValue.append(data.get("pr_NeedResponse"));
		return hmacSign(sValue.toString(), PayParams.CARD_KEY);
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
	private boolean verifyCallback(String hmac, String r0_Cmd, String r1_Code, String p1_MerId, String p2_Order, String p3_Amt, String p4_FrpId, String p5_CardNo, String p6_confirmAmount, String p7_realAmount, String p8_cardStatus, String p9_MP, String pb_BalanceAmt, String pc_BalanceAct,
			String keyValue) {
		StringBuffer sValue = new StringBuffer();
		// 业务类型
		sValue.append(r0_Cmd);
		// 支付结果
		sValue.append(r1_Code);
		sValue.append(p2_Order);
		sValue.append(p3_Amt);
		sValue.append(p4_FrpId);
		sValue.append(p5_CardNo);
		sValue.append(p6_confirmAmount);
		sValue.append(p7_realAmount);
		sValue.append(p8_cardStatus);
		sValue.append(p9_MP);
		sValue.append(pb_BalanceAmt);
		sValue.append(pc_BalanceAct);
		String sNewString = null;
		sNewString = hmacSign(sValue.toString(), keyValue);
		if (hmac.equals(sNewString)) {
			return true;
		}
		return false;
	}

}
