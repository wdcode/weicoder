package com.weicoder.common.params;

import java.util.Set;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.DateConstants;
import com.weicoder.common.constants.EncryptConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.constants.SystemConstants;
import com.weicoder.common.constants.C.S;
import com.weicoder.common.lang.Sets;
import com.weicoder.common.util.U.E;

/**
 * Common包参数读取类
 * 
 * @author WD
 */
public sealed class CommonParams permits P.C {
	/** log实现 */
	public final static String		LOG_CLASS				= P.getString("log.class", "com.weicoder.log4j.Log4j2");
	/** 缓冲区大小 默认1024*1024 */
	public final static int			BUFFER_SIZE				= P.getInt("buffer.size", 1024 * 1024);
	/** IO缓冲区大小 默认使用BUFFER_SIZE */
	public final static int			IO_BUFFERSIZE			= P.getInt("io.buffer", BUFFER_SIZE);
	/** IO模式 oio nio aio 默认nio */
	public final static String		IO_MODE					= P.getString("io.mode", "nio");
	/** IO是否自动关闭流 默认true */
	public final static boolean		IO_CLOSE				= P.getBoolean("io.close", true);
	/** 文件写入模式是否追加 默认true */
	public final static boolean		FILE_APPEND				= P.getBoolean("file.append", true);
	/** 默认编码 */
	public final static String		ENCODING				= P.getString("encoding", "UTF-8");
	/** 日期格式 */
	public final static String		DATE_FORMAT				= P.getString("date.format", DateConstants.FORMAT_Y_M_D_H_M_S);
	/** 转换字节数组算法 */
	public final static String		BYTES					= P.getString("bytes", "high");
	/** 加密使用的密钥 字符串 */
	public final static String		ENCRYPT_KEY				= P.getString("encrypt.key", "www.weicoder.com");
	/** 加密使用的算法 */
	public final static String		ENCRYPT_ALGO			= P.getString("encrypt.algo", EncryptConstants.ALGO_AES);
	/** 加密使用摘要算法 */
	public final static String		ENCRYPT_DIGEST			= P.getString("encrypt.digest", EncryptConstants.ALGO_SHA_1);
	/** 获得ips过滤组 */
	public final static String[]	IPS						= P.getStringArray("ips", ArrayConstants.STRING_EMPTY);
	/** token 验证KEY */
	public final static String		TOKEN_KEY				= P.getString("token.key");
	/** token 验证长度 */
	public final static int			TOKEN_LENGHT			= P.getInt("token.lenght", 8);
	/** token 标记 */
	public final static short		TOKEN_SIGN				= P.getShort("token.sign", Byte.MIN_VALUE);
	/** token 有效期 */
	public static final int			TOKEN_EXPIRE			= P.getInt("token.expire", DateConstants.DAY * 8);
	/** 截取日志长度 */
	public final static int			LOGS_LEN				= P.getInt("logs.len", 500);
	/** token 发放服务器 */
	public final static Set<String>	TOKEN_SERVERS			= Sets
			.newSet(P.getStringArray("token.servers", ArrayConstants.STRING_EMPTY));
	/** http连接超时时间 */
	public final static int			HTTP_CONNECT_TIMEOUT	= P.getInt("http.connect.timeout", 3000);
	/** http读取超时时间 */
	public final static int			HTTP_READ_TIMEOUT		= P.getInt("http.read.timeout", 10000);
	/** 分页每页大小 默认20 */
	public final static int			PAGE_SIZE				= P.getInt("page.size", 20);
	/** 验证码出现的字符集 */
	public final static char[]		VERIFY_CODE				= P
			.getString("verify.code", "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
	/** 验证码长度 */
	public final static int			VERIFY_LENGTH			= P.getInt("verify.length", 4);
	/** 是否驻留程序 */
	public final static boolean		MAIN					= P.getBoolean("main", true);
	/** 默认报名 */
	private final static String		DEFAULT_PACKAGES		= "com.weicoder";
	public final static String		PACKAGES				= getPackages(S.EMPTY);
	/** ClassUtil 排除Bean名称尾缀 */
	public final static String[]	CLASS_NAMES				= P.getStringArray("class.names",
			new String[] { "Action", "Service", "Dao", "Job", "Cache", "Impl", "Http" });

	/**
	 * 获得初始化开关
	 * 
	 * @param name 初始化的模块
	 * @return true 初始化 false 不执行
	 */
	public static boolean power(String name) {
		return P.getBoolean("init.power." + name, true);
	}

	/**
	 * 获得包名
	 * 
	 * @param name 名称
	 * @return 名称下的包名
	 */
	public static String getPackages(String name) {
		// 获得包名
		String pack = P.getString(P.getKey(StringConstants.EMPTY, name, "packages"));
		// 如果包名为空返回默认包名 不为空加上本包名
		if (E.isEmpty(pack) || DEFAULT_PACKAGES.equals(pack))
			return DEFAULT_PACKAGES;
		else
			return DEFAULT_PACKAGES + StringConstants.COMMA + pack;
	}

	/**
	 * 获得定时任务池
	 * 
	 * @param name 名称
	 * @return 数量
	 */
	public static int getScheduledPool(String name) {
		return P.getInt(P.getKey("scheduled", name, "pool"), SystemConstants.CPU_NUM);
	}

	/**
	 * 获得定时任务池是否守护线程
	 * 
	 * @param name 名称
	 * @return 数量
	 */
	public static boolean getScheduledDaemon(String name) {
		return P.getBoolean(P.getKey("scheduled", name, "daemon"), true);
	}

	/**
	 * 获得线程池
	 * 
	 * @param name 名称
	 * @return 数量
	 */
	public static int getExecutorPool(String name) {
		return P.getInt(P.getKey("executor", name, "pool"), SystemConstants.CPU_NUM);
	}

	/**
	 * 获得线程是否守护线程
	 * 
	 * @param name 名称
	 * @return 数量
	 */
	public static boolean getExecutorDaemon(String name) {
		return P.getBoolean(P.getKey("executor", name, "daemon"), true);
	}
}
