package com.weicoder.web.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Validate;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.web.constants.HttpConstants;

/**
 * IP工具集
 * @author WD
 * @since JDK6
 * @version 1.0 2013-09-27
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
			// 返回第一个ip
			return EmptyUtil.isEmpty(ips) ? StringConstants.EMPTY : ips[0];
		} else {
			return SERVER_IP;
		}
	}

	/**
	 * 获得本机IP数组
	 * @param request Request
	 * @return 客户连接IP
	 */
	public static String[] getIps() {
		try {
			// 声明IP列表
			List<String> list = Lists.getList();
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
			String[] t = ip.split(StringConstants.DOT);
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

	/**
	 * 获得客户连接IP
	 * @param request Request
	 * @return 客户连接IP
	 */
	public static String getIp(HttpServletRequest request) {
		// 获得ip列表
		String[] ips = getIps(request);
		// 返回第一个ip
		return EmptyUtil.isEmpty(ips) ? StringConstants.EMPTY : ips[0];
	}

	// /**
	// * 获得IP详细信息
	// * @param ip 要查询的IP
	// * @return 对应信息的键值
	// */
	// public static Map<String, String> getIpInfo(String ip) {
	// // http://ip.taobao.com/service/getIpInfo.php?ip=?
	// // http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=?
	// // http://ip.qq.com/cgi-bin/searchip?searchip1=?
	// return Maps.emptyMap();
	// }

	/**
	 * 获得客户连接IP数组 一般通过代理的可或则所以IP
	 * @param request Request
	 * @return 客户连接IP
	 */
	public static String[] getIps(HttpServletRequest request) {
		// 判断不为空
		if (!EmptyUtil.isEmpty(request)) {
			// 获得IP
			String ip = request.getHeader(HttpConstants.HEADER_IP_X_FORWARDED_FOR);
			// 判断如果为空继续获得
			if (EmptyUtil.isEmpty(ip)) {
				// 为空换方法获得
				ip = request.getHeader(HttpConstants.HEADER_IP_X_REAL_IP);
			}
			// 判断如果为空继续获得
			if (EmptyUtil.isEmpty(ip)) {
				// 为空换方法获得
				ip = request.getRemoteAddr();
			}
			// 返回IP
			return EmptyUtil.isEmpty(ip) ? ArrayConstants.STRING_EMPTY : ip.indexOf(StringConstants.COMMA) == -1 ? new String[] { ip } : ip.split(StringConstants.COMMA);
		}
		// 返回""
		return ArrayConstants.STRING_EMPTY;
	}

	private IpUtil() {}
}
