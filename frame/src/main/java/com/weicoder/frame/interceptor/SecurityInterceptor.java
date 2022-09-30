package com.weicoder.frame.interceptor;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import com.weicoder.frame.action.StrutsAction;
import com.weicoder.frame.params.SecurityParams;
import com.weicoder.web.util.RequestUtil;
import com.weicoder.common.lang.W;
import com.weicoder.common.util.U;

/**
 * 基于动态映射方法的安全拦截器 出来掉不合法的一些请求
 * @author WD
 *  
 */
public final class SecurityInterceptor extends BasicInterceptor<StrutsAction> {
	// 方法对应实体Map
	private Map<String, List<String>> methods;

	public void init() {
		// 实例化Map
		methods = W.M.map();
		// 安全验证方法
		for (String method : SecurityParams.SECURITY_METHODS) {
			methods.put(method, SecurityParams.getModules(method));
		}
	}

	protected boolean before(StrutsAction action, HttpServletRequest request) {
		// 过滤IP
		if (SecurityParams.SECURITY_POWER_IP) {
			// 获得IP
			String ip = RequestUtil.getIp(request);
			// 如果不存在允许列表中
			if (!SecurityParams.SECURITY_IPS.contains(ip)) {
				return false;
			}
		}
		// 过滤方法
		if (SecurityParams.SECURITY_POWER_METHOD) {
			// 获得执行的方法
			String method = action.getMethod();
			// 实体
			String module = action.getModule();
			// 实体玉方法相同 为自定义方法 直接通过
			if (module.equals(method)) {
				return true;
			}
			// 获得方法下的实体列表
			List<String> modules = methods.get(method);
			// 判断是可执行实体方法
			return U.E.isEmpty(modules) ? false : modules.contains(module);
		}
		// 如果都不过滤 返回true
		return true;
	}
}
