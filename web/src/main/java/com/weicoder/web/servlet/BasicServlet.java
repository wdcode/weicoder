package com.weicoder.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.log.Logs;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.context.Contexts;
import com.weicoder.web.params.ServletParams;
import com.weicoder.web.util.RequestUtil;
import com.weicoder.web.util.ResponseUtil;

/**
 * 基础Servlet 3
 * @author WD
 */
@WebServlet("/*")
public class BasicServlet extends HttpServlet {
	private static final long serialVersionUID = 3117468121294921856L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long curr = System.currentTimeMillis();
		// 获得客户端IP
		String ip = RequestUtil.getIp(request);
		// 获得callback
		String callback = RequestUtil.getParameter(request, "callback");
		Logs.trace("check ip request ip={},ips={}", ip, ServletParams.IPS);
		// 过滤IP
		if (!EmptyUtil.isEmpty(ServletParams.IPS)) {
			// 如果在允许列表继续 否则退出
			if (!ServletParams.IPS.contains(ip)) {
				Logs.debug("this ip={}", ip);
				ResponseUtil.json(response, callback, "not exist ip");
				return;
			}
		}
		// 获得path
		String path = request.getPathInfo();
		Logs.trace("request ip={},path={},{}", ip, path, request.getQueryString());
		if (!EmptyUtil.isEmpty(path)) {
			// 分解提交action 去处开头的/ 并且按_分解出数组
			String[] actions = StringUtil.split(StringUtil.subString(path, 1, path.length()), StringConstants.BACKSLASH);
			if (EmptyUtil.isEmpty(actions)) {
				Logs.debug("this path={}", path);
				ResponseUtil.json(response, callback, "action is null path");
				return;
			}
			// 获得Action
			String name = actions[0];
			Object action = Contexts.ACTIONS.get(name);
			// action为空
			if (action == null) {
				// 如果使用action_method模式 直接返回
				if (actions.length == 2) {
					Logs.trace("request ip={},path={},no action", ip, path);
					ResponseUtil.json(response, callback, "no.action");
					return;
				}
				// 查找方法对应action
				action = Contexts.METHODS_ACTIONS.get(name);
			}
			// 获得方法
			Map<String, Method> methods = Contexts.ACTIONS_METHODS.get(name);
			if (EmptyUtil.isEmpty(methods)) {
				methods = Contexts.METHODS;
			}
			Method method = methods.get(actions.length > 1 ? actions[1] : actions[0]);
			if (method == null) {
				Logs.trace("request ip={},path={},no method", ip, path);
				ResponseUtil.json(response, callback, "no.method");
				return;
			}
			Logs.debug("request ip={},name={}", ip, name);
			// 设置参数
			Parameter[] pars = method.getParameters();
			Object[] params = null;
			Logs.trace("request to set parameter");
			if (!EmptyUtil.isEmpty(pars)) {
				// 参数不为空 设置参数
				params = new Object[pars.length];
				// 所有提交的参数
				Map<String, String> ps = RequestUtil.getParameters(request);
				// action全部参数下标
				int i = 0;
				for (; i < pars.length; i++) {
					// 判断类型并设置
					Parameter p = pars[i];
					// 参数的类型
					Class<?> cs = p.getType();
					if (HttpServletRequest.class.equals(cs)) {
						params[i] = request;
					} else if (HttpServletResponse.class.equals(cs)) {
						params[i] = response;
					} else if (Map.class.equals(cs)) {
						params[i] = ps;
					} else if (ClassUtil.isBaseType(cs)) {
						params[i] = Conversion.to(ps.get(p.getName()), cs);
						// 判断参数为空并且参数名为ip
						if (EmptyUtil.isEmpty(params[i]) && "ip".equals(p.getName())) {
							// 赋值为调用客户端IP
							params[i] = ip;
						}
						Logs.debug("request ip={},name={},params index={},name={},type={},value={}", ip, name, i, p.getName(), cs, params[i]);
					} else {
						params[i] = BeanUtil.copy(ps, cs);
						Logs.debug("request ip={},name={},params={}", ip, name, params[i]);
					}
				}
			}
			// 调用方法
			ResponseUtil.json(response, callback, BeanUtil.invoke(action, method, params));
			Logs.info("request ip={},name={},params={},time={} end", ip, name, params, System.currentTimeMillis() - curr);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (ServletParams.GET) {
			doPost(request, response);
		} else {
			ResponseUtil.json(response, "not supported get");
		}
	}
}