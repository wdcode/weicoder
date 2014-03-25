package com.weicoder.base.cache.handler;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import com.weicoder.base.context.Context;
import com.weicoder.base.entity.Entity;
import com.weicoder.base.service.SuperService;
import com.weicoder.web.socket.Handler;
import com.weicoder.web.socket.Session;
import com.weicoder.web.socket.manager.Manager;

/**
 * 缓存Socket通知类
 * @author WD
 * @since JDK7
 * @version 1.0 2013-12-30
 */
@Component
public final class CacheLoadHandler implements Handler<String> {
	// SuperService
	@Resource
	private SuperService	service;
	// Context
	@Resource
	private Context			context;

	@Override
	public short getId() {
		return 1;
	}

	@Override
	public void handler(Session session, String data, Manager manager) {
		// 获得要更新缓存的类
		Class<? extends Entity> c = context.getClass(data);
		// 判断类是否为空
		if (c == null) {
			// 更新所有缓存
			service.cache();
		} else {
			// 更新指定类缓存
			service.load(c);
		}
	}
}
