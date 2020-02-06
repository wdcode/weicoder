package com.weicoder.kafka.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.config.ConfigFactory;
import com.weicoder.common.params.Params;
import com.weicoder.common.util.IpUtil;

/**
 * kafka参数
 * 
 * @author WD
 */
public final class KafkaParams {
	/** kafka使用 */
	public final static String PREFIX = "kafka";
	// Properties配置
	private final static Config CONFIG       = ConfigFactory.getConfig(PREFIX);
	private final static String COMPRESS     = "compress";
	private final static String SERVERS      = "servers";
	private final static String GROUP        = "group";
	private final static String TIMEOUT      = "timeout";
	private final static String MAXPOLL      = "maxPoll";
	private final static String OFFSET_RESET = "offset.reset";
	private final static String INTERVAL     = "interval";

	/**
	 * 获得kafka服务器
	 * 
	 * @param  name 名字
	 * @return      服务器
	 */
	public static String getCompress(String name) {
		return CONFIG.getString(Params.getKey(name, COMPRESS),
				Params.getString(Params.getKey(PREFIX, name, COMPRESS), "gzip"));
	}

	/**
	 * 获得kafka服务器
	 * 
	 * @param  name 名字
	 * @return      服务器
	 */
	public static String getServers(String name) {
		return CONFIG.getString(Params.getKey(name, SERVERS), Params.getString(Params.getKey(PREFIX, name, SERVERS)));
	}

	/**
	 * 获得kafka组id
	 * 
	 * @param  name 名字
	 * @return      组id
	 */
	public static String getGroup(String name) {
		return CONFIG.getString(Params.getKey(name, GROUP),
				String.format(Params.getString(Params.getKey(PREFIX, name, GROUP), IpUtil.CODE), IpUtil.CODE));
	}

	/**
	 * 获得kafka获取最大records数量
	 * 
	 * @param  name 名字
	 * @return      数量
	 */
	public static int getMaxPoll(String name) {
		return CONFIG.getInt(Params.getKey(name, MAXPOLL), Params.getInt(Params.getKey(PREFIX, name, MAXPOLL), 1000));
	}

	/**
	 * 获得kafka Session 超时时间
	 * 
	 * @param  name 名字
	 * @return      超时时间
	 */
	public static int getTimeout(String name) {
		return CONFIG.getInt(Params.getKey(name, TIMEOUT), Params.getInt(Params.getKey(PREFIX, name, TIMEOUT), 30000));
	}

	/**
	 * 获得偏移量失效方案 earliest 从最早开始 latest 从最新开始
	 * 
	 * @param  name 名称
	 * @return      偏移量失效方案
	 */
	public static String getOffsetReset(String name) {
		return CONFIG.getString(Params.getKey(name, OFFSET_RESET),
				Params.getString(Params.getKey(PREFIX, name, OFFSET_RESET), "latest"));
	}

	/**
	 * 获得kafka间隔时间
	 * 
	 * @param  name 名字
	 * @return      间隔时间
	 */
	public static int getInterval(String name) {
		return CONFIG.getInt(Params.getKey(name, INTERVAL), Params.getInt(Params.getKey(PREFIX, name, INTERVAL), 1000));
	}

	private KafkaParams() {
	}
}
