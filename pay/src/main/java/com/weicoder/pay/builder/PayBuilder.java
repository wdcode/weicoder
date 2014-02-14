package com.weicoder.pay.builder;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import com.weicoder.base.context.Context;
import com.weicoder.common.lang.Maps;
import com.weicoder.pay.Pay;

/**
 * 支付构建器
 * @author WD
 * @since JDK6
 * @version 1.0 2012-12-04
 */
@Component
public final class PayBuilder {
	@Resource
	private Context				context;
	// 支付Map
	private Map<Integer, Pay>	maps;

	/**
	 * 初始化
	 */
	@PostConstruct
	protected void init() {
		// 实例化支付Map
		maps = Maps.getConcurrentMap();
		// 获得支付接口实现
		for (Map.Entry<String, Pay> e : context.getBeans(Pay.class).entrySet()) {
			maps.put(e.getValue().type(), e.getValue());
		}
	}

	/**
	 * 获得支付接口
	 * @param type 支付类型
	 * @return 支付接口
	 */
	public Pay build(int type) {
		return maps.get(type);
	}
}
