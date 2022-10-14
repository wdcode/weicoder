package com.weicoder.html;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;

import com.weicoder.common.lang.W;
import com.weicoder.common.log.Logs;
import com.weicoder.html.jsoup.HtmlJsoup;

/**
 * Html工具集 获取HTML方法
 * 
 * @author wdcode
 *
 */
public final class Htmls {
	/**
	 * 使用Post方法获取网络HTML
	 * 
	 * @param url 网址
	 * @param key 参数键
	 * @param val 参数值
	 * @return HTML
	 */
	public static Html post(String url, String key, String val) {
		return post(url, W.M.map(key, val));
	}

	/**
	 * 使用Post方法获取网络HTML
	 * 
	 * @param url  网址
	 * @param data 参数
	 * @return HTML
	 */
	public static Html post(String url, Map<String, String> data) {
		try {
			return new HtmlJsoup(Jsoup.connect(url).data(data).post());
		} catch (IOException e) {
			Logs.error(e);
			return null;
		}
	}

	/**
	 * 使用get方法获取网络HTML
	 * 
	 * @param url 网址
	 * @return HTML
	 */
	public static Html get(String url) {
		try {
			return new HtmlJsoup(Jsoup.connect(url).get());
		} catch (IOException e) {
			Logs.error(e);
			return null;
		}
	}

	private Htmls() {
	}
}
