package com.weicoder.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.common.bean.StateCode;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.C;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.StateParams;
import com.weicoder.common.token.TokenBean;
import com.weicoder.common.token.TokenEngine;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.ClassUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.IpUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.web.annotation.Action;
import com.weicoder.web.annotation.Async;
import com.weicoder.web.annotation.Cookies;
import com.weicoder.web.annotation.Forward;
import com.weicoder.web.annotation.Get;
import com.weicoder.web.annotation.Post;
import com.weicoder.web.annotation.Redirect;
import com.weicoder.web.annotation.State;
import com.weicoder.web.aop.Aop;
import com.weicoder.web.aop.Aops;
import com.weicoder.web.common.WebCommons;
import com.weicoder.web.params.WebParams;
import com.weicoder.web.util.CookieUtil;
import com.weicoder.web.util.RequestUtil;
import com.weicoder.web.util.ResponseUtil;
import com.weicoder.web.validator.Validators;

/**
 * 基础Servlet 3
 * 
 * @author WD
 */
@WebServlet(value = "/*", asyncSupported = true)
public class BasicServlet extends HttpServlet {
	private static final long serialVersionUID = 3117468121294921856L;
	// 日志
	private final static Log LOG = LogFactory.getLog(BasicServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long curr = System.currentTimeMillis();
		// 获得客户端IP
		String ip = RequestUtil.getIp(request);
		// 获得callback
		String callback = RequestUtil.getParameter(request, "callback");
		// 提交方法
		String m = request.getMethod();
		// 获得path
		String path = request.getPathInfo();
		LOG.debug("request ip={} path={} Method={} scheme={} queryString={}", ip, path, m, request.getScheme(), request.getQueryString());
		if (EmptyUtil.isNotEmpty(path)) {
			// 分解提交action 去处开头的/ 并且按/或者_分解出数组
			String actionName = StringUtil.subString(path, 1, path.length());
			String[] actions = StringUtil.contains(actionName, StringConstants.BACKSLASH) ? StringUtil.split(actionName, StringConstants.BACKSLASH)
					: StringUtil.split(actionName, StringConstants.UNDERLINE);
			if (EmptyUtil.isEmpty(actions)) {
				LOG.debug("this path={}", path);
				ResponseUtil.json(response, callback, "action is null path");
				return;
			}
			// 获得Action
			String name = actions[actions.length - 1];
			Object action = null;
			for (int i = actions.length - 2; i >= 0; i--) {
				name = actions[i];
				action = WebCommons.ACTIONS.get(name);
				if (action != null)
					break;
			}
			// action为空
			if (action == null) {
				// 如果使用action[_/]method模式 直接返回
				if (actions.length == 2) {
					LOG.debug("request ip={},path={},no action", ip, path);
					ResponseUtil.json(response, callback, "no action");
					return;
				}
				// 查找方法对应action
				action = WebCommons.METHODS_ACTIONS.get(name);
			}
			// action为空
			if (action == null) {
				// 还是为空
				LOG.warn("request ip={},path={},name={},actionName={},ma={},no action and method", ip, path, name, actionName, WebCommons.METHODS_ACTIONS);
				ResponseUtil.json(response, callback, "no action and method");
				return;
			}
			// 过滤IP
			Action a = action.getClass().getAnnotation(Action.class);
			if (a.ips()) {
				// 如果在允许列表继续 否则退出
				if (!IpUtil.contains(ip)) {
					LOG.debug("this ip={}", ip);
					ResponseUtil.json(response, callback, "not exist ip");
					return;
				}
			}
			// 获得方法
			Map<String, Method> methods = WebCommons.ACTIONS_METHODS.get(name);
			if (EmptyUtil.isEmpty(methods))
				methods = WebCommons.METHODS;
			Method method = methods.get(actions[actions.length - 1]);
			if (method == null) {
				LOG.debug("request ip={},path={},no method", ip, path);
				ResponseUtil.json(response, callback, "no method");
				return;
			}
			// 判断提交方法
			// if (a.method()) {
			// 校验是否只使用post方法提交
			if (method.isAnnotationPresent(Post.class) && !StringUtil.equals("POST", m.toUpperCase())) {
				ResponseUtil.json(response, callback, "no method is " + m);
				return;
			}
			// 校验是否只使用get方法提交
			if (method.isAnnotationPresent(Get.class) && !StringUtil.equals("GET", m.toUpperCase())) {
				ResponseUtil.json(response, callback, "no method is " + m);
				return;
			}
			// }
			// 设置参数
			Parameter[] pars = WebCommons.METHODS_PARAMES.get(method);
			Object[] params = null;
			// 所有提交的参数
			Map<String, String> ps = RequestUtil.getAll(request);
			LOG.debug("action={} params={}", actionName, ps);
			// 验证
			int code = Validators.validator(method, action, ps, ip);
			if (EmptyUtil.isNotEmpty(pars)) {
				// 参数不为空 设置参数
				params = new Object[pars.length];
				// ip没有传 注入当前客户端IP
				if (EmptyUtil.isEmpty(ps.get("ip")))
					ps.put("ip", ip);
				// 当前时间time没有传注入time
				if (EmptyUtil.isEmpty(ps.get("time")))
					ps.put("time", C.toString(DateUtil.getTime()));
				LOG.trace("request all ip={} params={}", ip, params);
				// token验证通过在执行
//				if (code == StateParams.SUCCESS) {
				// action全部参数下标
				int i = 0;
				for (; i < pars.length; i++) {
					// 判断类型并设置
					Parameter p = pars[i];
					// 参数的类型
					Class<?> cs = p.getType();
					if (HttpServletRequest.class.equals(cs))
						params[i] = request;
					else if (HttpServletResponse.class.equals(cs))
						params[i] = response;
					else if (TokenBean.class.equals(cs))
						// 设置Token
						params[i] = TokenEngine.decrypt(ps.get(p.getName()));
					else if (Map.class.equals(cs))
						params[i] = ps;
					else if (ClassUtil.isBaseType(cs)) {
						// 获得参数
						params[i] = C.to(ps.get(p.getName()), cs);
						// 验证参数
						if (code == StateParams.SUCCESS)
							if ((code = Validators.validator(p, params[i])) != StateParams.SUCCESS)
								break;
					} else {
						// 设置属性
						params[i] = BeanUtil.copy(ps, cs);
						// 验证参数
						if (code == StateParams.SUCCESS)
							if ((code = Validators.validator(params[i])) != StateParams.SUCCESS)
								break;
					}
				}
//				}
			}
			// 调用方法
			// try {
			if (code == StateParams.SUCCESS) {
				// 判断是否异步
				if (a.async() || method.isAnnotationPresent(Async.class)) {
					// 获得异步全局
					AsyncContext async = request.startAsync();
					Object ac = action;
					Object[] p = params;
					// 异步执行
					async.start(() -> {
						// 执行方法并返回结果
						result(method, ac, invoke(ac, method, p, request, response), callback, request, response, ip, actionName, p, pars, curr);
						// 通知主线程完成
						async.complete();
					});
//					// 异步处理
//					ExecutorUtil.pool("async").execute(() -> {
//						// 执行方法并返回结果
//						try {
//							result(method, ac, invoke(ac, method, p, request, response), callback, request, response, ip, actionName, p, pars, curr);
//						} catch (Exception e) {
//							Logs.error(e);
//						}
//						// 通知主线程完成
//						async.complete();
//					});
				} else
					result(method, action, invoke(action, method, params, request, response), callback, request, response, ip, actionName, params, pars, curr);
			} else
				result(method, action, StateCode.build(code), callback, request, response, ip, actionName, params, pars, curr);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (WebParams.GET)
			doPost(request, response);
		else
			ResponseUtil.json(response, "not supported get");
	}

	private void result(Method method, Object action, Object res, String callback, HttpServletRequest request, HttpServletResponse response, String ip, String actionName, Object[] params,
			Parameter[] pars, long curr) {
		try {
			// 判断是否需要写cookie
			boolean cookie = method.isAnnotationPresent(Cookies.class) || action.getClass().isAnnotationPresent(Cookies.class);
			String[] names = null;
			Cookies c = null;
			if (cookie) {
				// 获得Cookies注解
				c = method.getAnnotation(Cookies.class);
				if (c == null)
					c = action.getClass().getAnnotation(Cookies.class);
				names = c.names();
			}
			// 判断是否跳转url
			if (method.isAnnotationPresent(Redirect.class) || action.getClass().isAnnotationPresent(Redirect.class)) {
				String url = C.toString(res);
				if (EmptyUtil.isEmpty(url)) {
					ResponseUtil.json(response, callback, "Redirect is null");
					return;
				} else {
					LOG.debug("redirect url:{}", url);
					response.sendRedirect(url);
					return;
				}
			} else if (method.isAnnotationPresent(Forward.class) || action.getClass().isAnnotationPresent(Forward.class)) {
				String url = C.toString(res);
				if (EmptyUtil.isEmpty(url)) {
					ResponseUtil.json(response, callback, "Forward is null");
					return;
				} else {
					LOG.debug("forward url:{}", url);
					request.getRequestDispatcher(url).forward(request, response);
					return;
				}
			} else if (method.isAnnotationPresent(State.class) || action.getClass().isAnnotationPresent(State.class)) {
				// 状态码对象
				State state = method.getAnnotation(State.class);
				if (state == null)
					state = action.getClass().getAnnotation(State.class);
				// 字段名
				String status = state.state();
				String success = state.success();
				String error = state.error();
				// 如果res为状态码
				if (res == null)
					// 写空信息
					res = Maps.newMap(new String[]{status, error}, StateCode.NULL.to());
//				else if (res instanceof Integer) {
//					// 写错误信息
//					int errorcode = Conversion.toInt(res);
//					res = Maps.newMap(new String[]{status, errorcode == StateParams.SUCCESS ? success : error},
//							new Object[]{errorcode, errorcode == StateParams.SUCCESS ? StateParams.SUCCESS_MSG : StateParams.getMessage(errorcode)});
//				}
				else if (res instanceof StateCode)
					// 写错误信息
					res = Maps.newMap(new String[]{status, error}, ((StateCode) res).to());
				else {
					// 是否写cookie
					if (cookie)
						CookieUtil.adds(response, c.maxAge(), res, names);
					// 写入到前端
					res = Maps.newMap(new String[]{status, success}, new Object[]{StateCode.SUCCESS.getCode(), res});
				}
			} else {
				// 如果结果为空
				if (res == null)
					// 结果设置为空map
					res = Maps.emptyMap();
				else if (cookie)
					// 写cookie
					CookieUtil.adds(response, c.maxAge(), res, names);
			}
			// 写到前端
			LOG.info("request ip={} name={} time={} params={} pars={} method={} type={} res={} end", ip, actionName, System.currentTimeMillis() - curr, params, pars, request.getMethod(),
					request.getContentType(), ResponseUtil.json(response, callback, res));
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	private Object invoke(Object action, Method method, Object[] params, HttpServletRequest request, HttpServletResponse response) {
		// 获得所有aop
		List<Aops> aops = aops(action, method);
		try {
			// 前置执行
			aops.forEach(aop -> aop.before(action, params, request, response));
			// 执行方法返回结果
			Object result = method.invoke(action, EmptyUtil.isEmpty(params) ? null : params);
			// 后置执行
			aops.forEach(aop -> aop.after(action, params, result, request, response));
			// 返回结果
			return result;
		} catch (Exception e) {
			Logs.error(e, "action invoke method={} args={} params={}", method.getName(), Arrays.toString(params), Arrays.toString(method.getParameters()));
			// 异常执行
			aops.forEach(aop -> aop.exception(e, action, params, request, response));
			// 返回错误
			return StateCode.ERROR;
		}
	}

	private List<Aops> aops(Object obj, Method method) {
		// 声明aop列表
		List<Aops> aops = Lists.newList(WebCommons.AOP_ALL);
		// 检查action是否有aop
		if (obj.getClass().isAnnotationPresent(Aop.class)) {
			Aop aop = obj.getClass().getAnnotation(Aop.class);
			if (aop != null && EmptyUtil.isNotEmpty(aop.value()))
				aops.add(WebCommons.AOPS.get(aop.value()));
		}
		// 检查method是否有aop
		if (method.isAnnotationPresent(Aop.class)) {
			Aop aop = method.getClass().getAnnotation(Aop.class);
			if (aop != null && EmptyUtil.isNotEmpty(aop.value()))
				aops.add(WebCommons.AOPS.get(aop.value()));
		}
		// 返回列表
		return Lists.notNull(aops);
	}

//	/**
//	 * 获得Action
//	 * 
//	 * @param path       提交路经
//	 * @param actionName action名称
//	 * @param actions    action数组
//	 * @param callback   回调属性
//	 * @param ip         用户ip
//	 * @param response   HttpServletResponse
//	 * @return 相关action
//	 */
//	private Object getAction(String path, String actionName, String[] actions, String callback, String ip,
//			HttpServletResponse response) {
//		// 声明action
//		Object action = null;
//		if (EmptyUtil.isEmpty(actions)) {
//			LOG.debug("this path={}", path);
//			ResponseUtil.json(response, callback, "action is null path");
//			return null;
//		}
//		// 获得Action
//		String name = actions[actions.length - 1];
//		for (int i = actions.length - 2; i >= 0; i--) {
//			name = actions[i];
//			action = WebCommons.ACTIONS.get(name);
//			if (action != null)
//				break;
//		}
//		// action为空
//		if (action == null) {
//			// 如果使用action[_/]method模式 直接返回
//			if (actions.length == 2) {
//				LOG.debug("request ip={},path={},no action", ip, path);
//				ResponseUtil.json(response, callback, "no action");
//				return null;
//			}
//			// 查找方法对应action
//			action = WebCommons.METHODS_ACTIONS.get(name);
//			// 判断是否为空
//			if (action == null) {
//				LOG.warn("request ip={},path={},name={},actionName={},ma={},no action and method", ip, path, name,
//						actionName, WebCommons.METHODS_ACTIONS);
//				return null;
//			}
//		}
//		// 返回action
//		return action;
//	}
}