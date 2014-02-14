package com.weicoder.pay.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.util.MathUtil;
import com.weicoder.pay.Pay;
import com.weicoder.pay.bean.PayBean;
import com.weicoder.pay.bean.TradeBean;
import com.weicoder.pay.constants.PayConstants;
import com.weicoder.pay.impl.tenpay.RequestHandler;
import com.weicoder.pay.impl.tenpay.ResponseHandler;
import com.weicoder.pay.impl.tenpay.client.ClientResponseHandler;
import com.weicoder.pay.impl.tenpay.client.TenpayHttpClient;
import com.weicoder.pay.params.PayParams;

/**
 * 财付通（即时交易）
 * @author WD
 * @since JDK6
 * @version 1.0 2012-12-04
 */
@Component
public final class Tenpay implements Pay {
	@Override
	public int type() {
		return PayConstants.TYPE_TENPAY;
	}

	@Override
	public String pay(HttpServletRequest request, HttpServletResponse response, PayBean pay) {
		// 创建支付请求对象
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init();
		// 设置密钥
		reqHandler.setKey(PayParams.TENPAY_KEY);
		// 设置支付网关
		reqHandler.setGateUrl(PayParams.TENPAY_URL);

		// -----------------------------
		// 设置支付参数
		// -----------------------------
		reqHandler.setParameter("partner", PayParams.TENPAY_ID); // 商户号
		reqHandler.setParameter("out_trade_no", pay.getNo()); // 商家订单号
		reqHandler.setParameter("total_fee", Integer.toString(MathUtil.multiply(pay.getTotal(), 100).intValue())); // 商品金额,以分为单位
		reqHandler.setParameter("return_url", PayParams.TENPAY_RETURN); // 交易完成后跳转的URL
		reqHandler.setParameter("notify_url", PayParams.TENPAY_NOTIFY); // 接收财付通通知的URL
		reqHandler.setParameter("body", pay.getBody()); // 商品描述
		reqHandler.setParameter("bank_type", Conversion.toString(pay.getDitch(), "DEFAULT")); // 银行类型(中介担保时此参数无效)
		reqHandler.setParameter("spbill_create_ip", request.getRemoteAddr()); // 用户的公网ip，不是商户服务器IP
		reqHandler.setParameter("fee_type", "1"); // 币种，1人民币
		reqHandler.setParameter("subject", pay.getSubject()); // 商品名称(中介交易时必填)

		// 系统可选参数
		reqHandler.setParameter("sign_type", "MD5"); // 签名类型,默认：MD5
		reqHandler.setParameter("service_version", "1.0"); // 版本号，默认为1.0
		reqHandler.setParameter("input_charset", getCharset()); // 字符编码
		reqHandler.setParameter("sign_key_index", "1"); // 密钥序号

		// 业务可选参数
		reqHandler.setParameter("attach", pay.getType()); // 附加数据，原样返回
															// product_fee=total_fee
		reqHandler.setParameter("trade_mode", "1"); // 交易模式，1即时到账(默认)，2中介担保，3后台选择（买家进支付中心列表选择）
		reqHandler.setParameter("trans_type", "2"); // 交易类型，1实物交易，2虚拟交易
		// 请求的url
		return reqHandler.getRequestURL();
	}

	@Override
	public TradeBean trade(HttpServletRequest request, HttpServletResponse response) {
		// 创建支付应答对象
		ResponseHandler resHandler = new ResponseHandler(request, response);
		resHandler.setKey(PayParams.TENPAY_KEY);
		// 商户订单号
		String out_trade_no = resHandler.getParameter("out_trade_no");
		// 商户订单号
		boolean notify = Conversion.toBoolean(resHandler.getParameter("notify"));
		// 是否成功
		boolean isOk = false;
		// 金额
		String total_fee = MathUtil.divide(resHandler.getParameter("total_fee"), 100).toPlainString();
		// 判断签名
		if (resHandler.isTenpaySign()) {
			try {
				// 通知id
				String notify_id = resHandler.getParameter("notify_id");
				// 创建请求对象
				RequestHandler queryReq = new RequestHandler(null, null);
				// 通信对象
				TenpayHttpClient httpClient = new TenpayHttpClient();
				// 应答对象
				ClientResponseHandler queryRes = new ClientResponseHandler();
				// 通过通知ID查询，确保通知来至财付通
				queryReq.init();
				queryReq.setKey(PayParams.TENPAY_KEY);
				queryReq.setGateUrl("https://gw.tenpay.com/gateway/simpleverifynotifyid.xml");
				queryReq.setParameter("partner", PayParams.TENPAY_ID);
				queryReq.setParameter("notify_id", notify_id);
				// 通信对象
				httpClient.setTimeOut(5);
				// 设置请求内容
				httpClient.setReqContent(queryReq.getRequestURL());
				// 后台调用
				if (httpClient.call()) {
					// 设置结果参数
					queryRes.setContent(httpClient.getResContent());
					queryRes.setKey(PayParams.TENPAY_KEY);
					// 获取id验证返回状态码，0表示此通知id是财付通发起
					String retcode = queryRes.getParameter("retcode");
					// 支付结果
					String trade_state = resHandler.getParameter("trade_state");
					// 交易模式，1即时到账，2中介担保
					String trade_mode = resHandler.getParameter("trade_mode");
					// 判断签名及结果
					if (queryRes.isTenpaySign() && "0".equals(retcode)) {
						if ("1".equals(trade_mode)) { // 即时到账
							if ("0".equals(trade_state)) {
								isOk = true;
								// 异步通知
								if (notify) {
									// 给财付通系统发送成功信息，财付通系统收到此结果后不再进行后续通知
									resHandler.sendToCFT("success");
								}
							}
						}
					}
				}

			} catch (Exception e) {}
		}
		// 返回实体
		return new TradeBean(out_trade_no, isOk, notify, total_fee);
	}

	@Override
	public String getCharset() {
		return PayParams.TENPAY_CHARSET;
	}
}
