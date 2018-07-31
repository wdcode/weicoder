package com.weicoder.common.params;

import java.util.Set;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.constants.EncryptConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.lang.Sets;

/**
 * Common包参数读取类
 * @author WD
 */
public final class CommonParams {
	/** log实现 */
	public final static String		LOG_CLASS				= Params.getString("log.class",
			"com.weicoder.core.log.Log4j2");
	/** IO缓冲区大小 */
	public final static int			IO_BUFFERSIZE			= Params.getInt("io.buffer", 8192);
	/** IO模式 */
	public final static String		IO_MODE					= Params.getString("io.mode", "nio");
	/** 默认编码 */
	public final static String		ENCODING				= Params.getString("encoding", "UTF-8");
	/** 日期格式 */
	public final static String		DATE_FORMAT				= Params.getString("date.format",
			DateConstants.FORMAT_Y_M_D_H_M_S);
	/** 转换字节数组算法 */
	public final static String		BYTES					= Params.getString("bytes", "high");
	/** 加密使用的密钥 字符串 */
	public final static String		ENCRYPT_KEY				= Params.getString("encrypt.key", "www.weicoder.com");
	/** 加密使用的算法 */
	public final static String		ENCRYPT_ALGO			= Params.getString("encrypt.algo",
			EncryptConstants.ALGO_AES);
	/** 加密使用摘要算法 */
	public final static String		ENCRYPT_DIGEST			= Params.getString("encrypt.digest",
			EncryptConstants.ALGO_SHA_1);
	/** 包名 */
	public final static String		PACKAGES				= Params.getString("packages");
	/** 获得ips过滤组 */
	public final static String[]	IPS						= Params.getStringArray("ips", ArrayConstants.STRING_EMPTY);
	/** token 验证长度 */
	public final static int			TOKEN_LENGHT			= Params.getInt("token.lenght", 8);
	/** token 验证长度 */
	public final static int			TOKEN_SIGN				= Params.getInt("token.sign", Byte.MIN_VALUE);
	/** 截取日志长度 */
	public final static int			LOGS_LEN				= Params.getInt("logs.len", 500);
	/** token 发放服务器 */
	public final static Set<String>	TOKEN_SERVERS			= Sets
			.newSet(Params.getStringArray("token.servers", ArrayConstants.STRING_EMPTY));
	/** http连接超时时间 */
	public final static int			HTTP_CONNECT_TIMEOUT	= Params.getInt("http.connect.timeout", 3000);
	/** http读取超时时间 */
	public final static int			HTTP_READ_TIMEOUT		= Params.getInt("http.read.timeout", 10000);

	/**
	 * 获得包名
	 * @param name 名称
	 * @return 名称下的包名
	 */
	public static String getPackages(String name) {
		return Params.getString(Params.getKey(StringConstants.EMPTY, name, "packages"), PACKAGES);
	}

	/**
	 * 获得定时任务池
	 * @param name 名称
	 * @return 数量
	 */
	public static int getScheduledPool(String name) {
		return Params.getInt(Params.getKey("scheduled", name, "pool"), SystemConstants.CPU_NUM);
	}

	/**
	 * 获得定时任务池是否守护线程
	 * @param name 名称
	 * @return 数量
	 */
	public static boolean getScheduledDaemon(String name) {
		return Params.getBoolean(Params.getKey("scheduled", name, "daemon"), true);
	}

	/**
	 * 获得线程池
	 * @param name 名称
	 * @return 数量
	 */
	public static int getExecutorPool(String name) {
		return Params.getInt(Params.getKey("executor", name, "pool"), SystemConstants.CPU_NUM);
	}

	/**
	 * 获得线程是否守护线程
	 * @param name 名称
	 * @return 数量
	 */
	public static boolean getExecutorDaemon(String name) {
		return Params.getBoolean(Params.getKey("executor", name, "daemon"), true);
	}

	private CommonParams() {}
}
