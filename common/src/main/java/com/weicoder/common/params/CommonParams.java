package com.weicoder.common.params;

import com.weicoder.common.config.Config;
import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.constants.EncryptConstants;
import com.weicoder.common.constants.StringConstants;

/**
 * Common包参数读取类
 * @author WD
 */
public final class CommonParams {
	/** 读取 common.properties 文件里的配置 */
	public final static Config		CONFIG			= Params.getConfig();
	/** log实现 */
	public final static String		LOG_CLASS		= CONFIG.getString("log.class",
			"com.weicoder.core.log.Log4j2");
	/** IO缓冲区大小 */
	public final static int			IO_BUFFERSIZE	= CONFIG.getInt("io.buffer", 8192);
	/** IO模式 */
	public final static String		IO_MODE			= CONFIG.getString("io.mode", "nio");
	/** 默认编码 */
	public final static String		ENCODING		= CONFIG.getString("encoding", "UTF-8");
	/** 日期格式 */
	public final static String		DATE_FORMAT		= CONFIG.getString("date.format",
			DateConstants.FORMAT_Y_M_D_H_M_S);
	/** 转换字节数组算法 */
	public final static String		BYTES			= CONFIG.getString("bytes", "high");
	/** 加密使用的密钥 字符串 */
	public final static String		ENCRYPT_KEY		= CONFIG.getString("encrypt.key",
			"www.weicoder.com");
	/** 加密使用的算法 */
	public final static String		ENCRYPT_ALGO	= CONFIG.getString("encrypt.algo",
			EncryptConstants.ALGO_AES);
	/** 加密使用摘要算法 */
	public final static String		ENCRYPT_DIGEST	= CONFIG.getString("encrypt.digest",
			EncryptConstants.ALGO_SHA_1);
	/** 执行任务名称数组 */
	public final static String[]	INIT_CLASSES	= CONFIG.getStringArray("init.class",
			ArrayConstants.STRING_EMPTY);

	/**
	 * 获得包名
	 * @param name 名称
	 * @return 名称下的包名
	 */
	public static String getPackages(String name) {
		return CONFIG.getString(Params.getKey(StringConstants.EMPTY, name, "packages"));
	}

	private CommonParams() {}
}
