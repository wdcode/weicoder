package com.weicoder.pay.builder;

import java.util.Map;

import com.weicoder.common.lang.Maps;
import com.weicoder.pay.Pay;
import com.weicoder.pay.constants.PayConstants;
import com.weicoder.pay.impl.Alipay;
import com.weicoder.pay.impl.Card;
import com.weicoder.pay.impl.Sms;
import com.weicoder.pay.impl.Tenpay;
import com.weicoder.pay.impl.Yeepay;

/**
 * 支付构建器
 * 
 * @author WD 
 */
public final class PayBuilder {
	// 支付Map
	private final static Map<Integer, Pay> MAPS = Maps.newMap();

	static {
		MAPS.put(PayConstants.TYPE_ALIPAY, new Alipay());
		MAPS.put(PayConstants.TYPE_CARD, new Card());
		MAPS.put(PayConstants.TYPE_SMS, new Sms());
		MAPS.put(PayConstants.TYPE_TENPAY, new Tenpay());
		MAPS.put(PayConstants.TYPE_YEEPAY, new Yeepay());
	}

	/**
	 * 获得支付接口
	 * 
	 * @param type 支付类型
	 * @return 支付接口
	 */
	public static Pay build(int type) {
		return MAPS.get(type);
	}

	private PayBuilder() {
	}
}
