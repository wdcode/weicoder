package com.weicoder.quartz.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.params.Params;

/**
 * quartz参数获取
 * 
 * @author WD
 */
public final class QuartzParams {
	// 前缀
	private final static String PREFIX = "job";
	// Properties配置
	private final static Config CONFIG  = ConfigFactory.getConfig(PREFIX);
	private final static String TRIGGER = "trigger";

	/**
	 * 根据健获得Trigger
	 * 
	 * @param  key          健
	 * @param  defaultValue 默认值
	 * @return              Trigger
	 */
	public static String getTrigger(String key, String defaultValue) {
		return CONFIG.getString(Params.getKey(key, TRIGGER),
				Params.getString(Params.getKey(PREFIX, key, TRIGGER), defaultValue));
	}

	private QuartzParams() {
	}
}
