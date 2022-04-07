package com.weicoder.frame.action;

import java.lang.reflect.Method;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.web.util.ResponseUtil;

/**
 * 基础action实现 
 * @author WD
 * @version 1.0
 */
public class BasicAction {
	// 回调方法处理
	protected final static Map<String, Method> METHODS = Maps.newMap();

	/**
	 * 以ajax模式输出数据到客户端方法
	 * 
	 * @param response HttpServletResponse
	 * @param data     对象
	 * @return 返回字符串
	 */
	public String ajax(HttpServletResponse response, Object data) {
		// 写字符串
		ResponseUtil.json(response, data);
		// 返回空
		return null;
	}

	/**
	 * 方法回调 所有直接Action回调的方法 一边统一处理
	 * 
	 * @param response HttpServletResponse
	 * @param obj      处理对象
	 * @return 返回标识
	 */
	public String callback(HttpServletResponse response, Object obj) {
		return callback(response, obj, StringConstants.EMPTY);
	}

	/**
	 * 方法回调 所有直接Action回调的方法 一边统一处理
	 * 
	 * @param response HttpServletResponse
	 * @param obj      处理对象
	 * @param mode     模式
	 * @return 返回标识
	 */
	public String callback(HttpServletResponse response, Object obj, String mode) {
		// 声明方法
		Method method = null;
		// 获得Key相对的方法是否存在
		if (METHODS.containsKey(mode)) {
			method = METHODS.get(mode);
		} else {
			// 不存在获得
			synchronized (METHODS) {
				METHODS.put(mode, method = BeanUtil.getMethod(this, mode, Object.class));
			}
		}
		// 方法不为空
		if (method != null) {
			obj = BeanUtil.invoke(this, method, response, obj);
		}
		// 返回对象字符串
		return Conversion.toString(obj, null);
	}

	/**
	 * 获得程序路径
	 * 
	 * @param request HttpServletRequest
	 * @param name    文件名
	 * @return 程序路径
	 */
	public String getRealPath(HttpServletRequest request, String name) {
		return request.getServletContext().getRealPath(StringConstants.BACKSLASH) + name;
	}
}
