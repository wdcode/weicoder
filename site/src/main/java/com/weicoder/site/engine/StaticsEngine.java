package com.weicoder.site.engine;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.weicoder.base.params.BaseParams;
import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.interfaces.Config;
import com.weicoder.common.lang.Conversion;
import com.weicoder.core.config.ConfigFactory;
import com.weicoder.web.util.HttpUtil;

/**
 * 静态化操作
 * @author WD
 * @since JDK7
 * @version 1.0 2010-12-22
 */
public final class StaticsEngine {
	// 定时器
	private static ScheduledExecutorService	service;

	/**
	 * 开始执行
	 */
	public static void start() {
		// 定时任务
		service = Executors.newSingleThreadScheduledExecutor();
		// 获得配置
		Config config = ConfigFactory.getConfig(BaseParams.STAICS_CONFIG);
		// 获得静态化开关
		String[] powers = config.getStringArray("statics.static.power");
		// 获得静态化URL
		final String[] urls = config.getStringArray("statics.static.url");
		// 获得静态化文件路径
		final String[] files = config.getStringArray("statics.static.file");
		// 获得静态化间隔时间
		String[] times = config.getStringArray("statics.static.time");
		// 获得静态化开始时间
		String[] starts = config.getStringArray("statics.static.start");
		// 循环执行
		for (int i = 0; i < powers.length; i++) {
			// 判断开关是否打开
			if (Conversion.toBoolean(powers[i])) {
				// 下标
				final int n = i;
				// 定时任务
				service.scheduleAtFixedRate(new Runnable() {
					public void run() {
						HttpUtil.saveToFile(urls[n], files[n]);
					}
				}, Conversion.toLong(starts[i]) * DateConstants.TIME_SECOND, Conversion.toLong(times[i]) * DateConstants.TIME_SECOND, TimeUnit.MILLISECONDS);
			}
		}
	}

	/**
	 * 关闭静态化定时器
	 */
	public static void close() {
		if (service != null) {
			service.shutdown();
		}
	}
}