package com.weicoder.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface; 
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.RegexConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.U;
import com.weicoder.common.W;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Sets;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.CommonParams;

/**
 * IP工具集
 * 
 * @author WD
 */
public class IpUtil {
	/** 本机IP 127.0.0.1 */
	public final static String LOCAL_IP;
	/** 本服务器IP */
	public final static String SERVER_IP;
	/** 本服务器IP编码后字符串 */
	public final static String CODE;
	// 过滤ip列表
	private final static Set<String> IPS_ALL;
	private final static Set<String> IPS_ONE;
	private final static Set<String> IPS_TWO;
	private final static Set<String> IPS_THREE;

	static {
		LOCAL_IP = "127.0.0.1";
		SERVER_IP = getIp();
		CODE = W.C.toString(encode(SERVER_IP));
		IPS_ALL = Sets.newSet();
		IPS_ONE = Sets.newSet();
		IPS_TWO = Sets.newSet();
		IPS_THREE = Sets.newSet();
		// 获得所有要过滤的ip
		for (String ip : CommonParams.IPS) {
			// 处理并保存ip
			if (StringUtil.contains(ip, StringConstants.ASTERISK)) {
				// 有*号匹配切掉*号并保存
				String s = StringUtil.subStringEnd(ip, StringConstants.ASTERISK);
				String[] t = StringUtil.split(s, RegexConstants.POINT);
				// 判断解析处理的ip放在不同列表
				if (t.length == 3) {
					IPS_THREE.add(s);
				} else if (t.length == 2) {
					IPS_TWO.add(s);
				} else if (t.length == 1) {
					IPS_ONE.add(s);
				}
			} else
				// 没有*匹配直接添加到列表
				IPS_ALL.add(ip);
			Logs.debug("add ips ip={}", ip);
		}
		Logs.info("add ips all={} one={} two={} three={}", IPS_ALL, IPS_ONE, IPS_TWO, IPS_THREE);
	}

	/**
	 * 校验ip是否在列表里 一般用在过滤ip白名单 支持泛*等操作
	 * 
	 * @param  ip 查询ip是否存在过滤列表
	 * @return    true 存在 false 不存在
	 */
	public static boolean contains(String ip) {
		// 本地IP放行
		if (LOCAL_IP.equals(ip))
			return true;
		// 分解ip段
		String p = StringConstants.POINT;
		String[] t = StringUtil.split(ip, RegexConstants.POINT);
		// 判断解析处理的ip放在不同列表
		return IPS_ALL.contains(ip) || IPS_THREE.contains(StringUtil.add(t[0], p, t[1], p, t[2], p))
				|| IPS_TWO.contains(StringUtil.add(t[0], p, t[1], p)) || IPS_ONE.contains(StringUtil.add(t[0], p));
	}

	/**
	 * 校验ip是否相等 支持*段
	 * 
	 * @param  regex ip正则
	 * @param  ip    ip
	 * @return       是否相等
	 */
	public static boolean contains(String regex, String ip) {
		return RegexUtil.is(regex, ip);
	}

	/**
	 * 校验ip是否相等 分4段检查 从左开始匹配几个段就返回几
	 * 
	 * @param  ip1 ip1
	 * @param  ip2 ip2
	 * @return     返回数字几
	 */
	public static int equals(String ip1, String ip2) {
		// 返回结果
		int res = 0;
		// ip1的字段
		byte[] b1 = Bytes.toBytes(encode(ip1));
		// ip2的字段
		byte[] b2 = Bytes.toBytes(encode(ip2));
		// 对比ip段
		for (int i = 0; i < 4; i++)
			if (b1[i] == b2[i])
				res++;
			else
				break;
		// 返回结果
		return res;
	}

	/**
	 * 设置代理
	 * 
	 * @param host 代理服务器
	 * @param port 代理端口
	 */
	public static void setProxy(String host, String port) {
		// 设置代理模式
		System.getProperties().setProperty("proxySet", "true");
		// 设置代理服务器
		System.getProperties().setProperty("http.proxyHost", host);
		// 设置代理端口
		System.getProperties().setProperty("http.proxyPort", port);
	}

	/**
	 * 获得本机IP
	 * 
	 * @return 本机IP
	 */
	public static String getIp() {
		if (U.E.isEmpty(SERVER_IP) && !LOCAL_IP.equals(SERVER_IP)) {
			// 获得ip列表
			String[] ips = getIps();
			// 如果为空
			if (U.E.isEmpty(ips))
				return StringConstants.EMPTY;
			// 获得第一个IP
			String ip = ips[0];
			// 循环全部IP
			for (int i = 1; i < ips.length; i++) {
				// 不是内网IP
				String tmp = ips[i];
				if (!tmp.startsWith("192.168") && !tmp.startsWith("10.") && !tmp.startsWith("172.")) {
					ip = tmp;
					break;
				}
			}
			// 返回ip
			return ip;
		} else {
			return SERVER_IP;
		}
	}

	/**
	 * 获得本机IP数组
	 * 
	 * @return 客户端IP组
	 */
	public static String[] getIps() {
		try {
			// 声明IP列表
			List<String> list = Lists.newList();
			// 获得网络接口迭代
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			// 循环所以网络接口 获得IP
			while (netInterfaces.hasMoreElements()) {
				// 获得IP迭代
				Enumeration<InetAddress> ips = netInterfaces.nextElement().getInetAddresses();
				// 循环获得IP
				while (ips.hasMoreElements()) {
					// 获得IP
					String ip = ips.nextElement().getHostAddress();
					// 判断不是IP和本机IP
					if (RegexUtil.isIp(ip) && !LOCAL_IP.equals(ip))
						list.add(ip);
				}
			}
			// 返回IP数组
			return Lists.toArray(list);
		} catch (Exception e) {
			return ArrayConstants.STRING_EMPTY;
		}
	}

	/**
	 * 编码本服务器IP为数字
	 * 
	 * @return 返回编码后的数字
	 */
	public static String code() {
		return W.C.toString(encode(SERVER_IP));
	}

	/**
	 * 编码IP为int
	 * 
	 * @param  ip 要编码的IP
	 * @return    返回编码后的int
	 */
	public static int encode(String ip) {
		// 判断是IP
		if (RegexUtil.isIp(ip)) {
			// 拆分IP
			String[] t = ip.split("\\.");
			// 判断数组长度为4
			if (t.length == 4)
				return W.C.toInt(t[0]) << 24 | W.C.toInt(t[1]) << 16 | W.C.toInt(t[2]) << 8 | W.C.toInt(t[3]);
		}
		// 失败返回0
		return 0;
	}

	/**
	 * 编码IP为int
	 * 
	 * @param  ip 要编码的IP
	 * @return    返回编码后的int
	 */
	public static String decode(int ip) {
		// 声明IP字符串缓存
		StringBuilder sb = new StringBuilder(15);
		sb.append(ip >>> 24);
		sb.append(StringConstants.POINT);
		sb.append((ip >> 16) & 0xFF);
		sb.append(StringConstants.POINT);
		sb.append((ip >> 8) & 0xFF);
		sb.append(StringConstants.POINT);
		sb.append(ip & 0xFF);
		// 失败返回0
		return sb.toString();
	}
}
