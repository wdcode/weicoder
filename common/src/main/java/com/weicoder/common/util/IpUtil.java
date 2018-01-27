package com.weicoder.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Validate;

/**
 * IP工具集
 * @author WD
 * 
 */
public final class IpUtil {
	// 本机IP 127.0.0.1
	public final static String	LOCAL_IP	= "127.0.0.1";
	// 本服务器IP
	public final static String	SERVER_IP	= getIp();

	/**
	 * 设置代理
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
	 * @return 本机IP
	 */
	public static String getIp() {
		if (EmptyUtil.isEmpty(SERVER_IP) && !LOCAL_IP.equals(SERVER_IP)) {
			// 获得ip列表
			String[] ips = getIps();
			// 如果为空
			if (EmptyUtil.isEmpty(ips)) {
				return StringConstants.EMPTY;
			}
			// 获得第一个IP
			String ip = ips[0];
			// 循环全部IP
			for (int i = 1; i < ips.length; i++) {
				// 不是内网IP
				String tmp = ips[i];
				if (!tmp.startsWith("192.168") && !tmp.startsWith("10.")) {
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
					if (Validate.isIp(ip) && !LOCAL_IP.equals(ip)) {
						list.add(ip);
					}
				}
			}
			// 返回IP数组
			return Lists.toArray(list);
		} catch (Exception e) {
			return ArrayConstants.STRING_EMPTY;
		}
	}

	/**
	 * 编码IP为int
	 * @param ip 要编码的IP
	 * @return 返回编码后的int
	 */
	public static int encode(String ip) {
		// 判断是IP
		if (Validate.isIp(ip)) {
			// 拆分IP
			String[] t = ip.split("\\.");
			// 判断数组长度为4
			if (t.length == 4) {
				return Conversion.toInt(t[0]) << 24 | Conversion.toInt(t[1]) << 16 | Conversion.toInt(t[2]) << 8 | Conversion.toInt(t[3]);
			}
		}
		// 失败返回0
		return 0;
	}

	/**
	 * 编码IP为int
	 * @param ip 要编码的IP
	 * @return 返回编码后的int
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

	private IpUtil() {}
}
