package com.weicoder.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.weicoder.common.bean.StateCode;
import com.weicoder.common.constants.C;
import com.weicoder.common.exception.StateException;
import com.weicoder.common.annotation.Asyn;
import com.weicoder.common.lang.W; 
import com.weicoder.common.log.Log;
import com.weicoder.common.log.LogFactory;
import com.weicoder.common.log.Logs;
import com.weicoder.common.params.P; 
import com.weicoder.common.token.TokenBean;
import com.weicoder.common.token.TokenEngine;
import com.weicoder.common.util.U;  
import com.weicoder.web.annotation.Action;
import com.weicoder.web.annotation.Cookies;
import com.weicoder.web.annotation.Forward;
import com.weicoder.web.annotation.Get;
import com.weicoder.web.annotation.Ips;
import com.weicoder.web.annotation.Json;
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
	private static final long	serialVersionUID	= 3117468121294921856L;
	// 日志
	private final static Log	LOG					= LogFactory.getLog(BasicServlet.class);

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
		if (U.E.isNotEmpty(path)) {
			// 分解提交action 去处开头的/ 并且按/或者_分解出数组
			String actionName = U.S.subString(path, 1, path.length());
			String[] actions = U.S.contains(actionName, C.S.BACKSLASH) ? U.S.split(actionName, C.S.BACKSLASH) : U.S.split(actionName, C.S.UNDERLINE);
			if (U.E.isEmpty(actions)) {
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
			if (WebParams.IPS || a.ips() || action.getClass().isAnnotationPresent(Ips.class)) {
				// 如果在允许列表继续 否则退出
				if (!U.IP.contains(ip)) {
					LOG.debug("this ip={}", ip);
					ResponseUtil.json(response, callback, "not exist ip");
					return;
				}
			}
			// 获得方法
			Map<String, Method> methods = WebCommons.ACTIONS_METHODS.get(name);
			if (U.E.isEmpty(methods))
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
			if (method.isAnnotationPresent(Post.class) && !U.S.equals("POST", m.toUpperCase())) {
				ResponseUtil.json(response, callback, "no method is " + m);
				return;
			}
			// 校验是否只使用get方法提交
			if (method.isAnnotationPresent(Get.class) && !U.S.equals("GET", m.toUpperCase())) {
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
			if (U.E.isNotEmpty(pars)) {
				// 参数不为空 设置参数
				params = new Object[pars.length];
				// ip没有传 注入当前客户端IP
				if (U.E.isEmpty(ps.get("ip")))
					ps.put("ip", ip);
				// 当前时间time没有传注入time
				if (U.E.isEmpty(ps.get("time")))
					ps.put("time", W.C.toString(U.D.getTime()));
				LOG.trace("request all ip={} params={}", ip, params);
				// token验证通过在执行
//				if (code == P.S.SUCCESS) {
				// action全部参数下标
				int i = 0;
				for (; i < pars.length; i++) {
					// 判断类型并设置
					Parameter p = pars[i];
					// 获得参数值
					String v = ps.get(p.getName());
					// 参数的类型
					Class<?> cs = p.getType();
					if (HttpServletRequest.class.equals(cs))
						params[i] = request;
					else if (HttpServletResponse.class.equals(cs))
						params[i] = response;
					else if (TokenBean.class.equals(cs))
						// 设置Token
						params[i] = TokenEngine.decrypt(v);
					else if (Map.class.equals(cs))
						params[i] = ps;
					else if (cs.isArray())
						params[i] = U.A.array(v, cs);
					else if (U.C.isBaseType(cs)) {
						// 获得参数
						params[i] = W.C.to(v, cs);
						// 验证参数
						if (code == P.S.SUCCESS)
							if ((code = Validators.validator(p, params[i])) != P.S.SUCCESS)
								break;
					} else {
						// 设置属性
						params[i] = U.B.copy(ps, cs);
						// 验证参数
						if (code == P.S.SUCCESS)
							if ((code = Validators.validator(params[i])) != P.S.SUCCESS)
								break;
					}
				}
//				}
			}
			// 调用方法
			// try {
			if (code == P.S.SUCCESS) {
				// 判断是否异步
				if (a.async() || method.isAnnotationPresent(Asyn.class) || action.getClass().isAnnotationPresent(Asyn.class)) {
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
//					T.E.pool("async").execute(() -> {
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
				String url = W.C.toString(res);
				if (U.E.isEmpty(url))
					ResponseUtil.json(response, callback, "Redirect is null");
				else
					response.sendRedirect(url);
			}
			// 是否Forward跳转
			if (method.isAnnotationPresent(Forward.class) || action.getClass().isAnnotationPresent(Forward.class)) {
				String url = W.C.toString(res);
				if (U.E.isEmpty(url))
					ResponseUtil.json(response, callback, "Forward is null");
				else
					request.getRequestDispatcher(url).forward(request, response);
			}
			// 是否json
			if (method.isAnnotationPresent(Json.class) || action.getClass().isAnnotationPresent(Json.class)) {
			} else if (WebParams.STATE || method.isAnnotationPresent(State.class) || action.getClass().isAnnotationPresent(State.class)) {
				// 状态码对象
				State state = method.getAnnotation(State.class);
				if (state == null)
					state = action.getClass().getAnnotation(State.class);
				// 字段名
				String code = state == null ? "code" : state.code();
				String content = state == null ? "content" :state.content();
				String message = state == null ? "message" :state.message();
				// 如果res为状态码
				if (res == null)
					// 写空信息
					res = W.M.newMap(new String[] { code, message }, StateCode.NULL.to());
				else if (res instanceof StateCode)
					// 写错误信息
					res = W.M.newMap(new String[] { code, message }, ((StateCode) res).to());
				else
					// 写入到前端
					res = W.M.newMap(new String[] { code, content }, new Object[] { StateCode.SUCCESS.getCode(), res });
			} else {
				// 如果结果为空
				if (res == null)
					// 结果设置为空map
					res = W.M.empty();
			}
			// 是否写cookie
			if (cookie)
				CookieUtil.adds(response, c.maxAge(), res, names);
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
			Object result = method.invoke(action, params);
//			Object result = method.invoke(action, U.E.isEmpty(params) ? null : params);
			if (result == null && void.class.equals(method.getReturnType()))
				result = StateCode.SUCCESS;
			// 后置执行
			for (Aops aop : aops)
				aop.after(action, params, result, request, response);
			// 返回结果
			return result;
		} catch (StateException e) {
			return e.state();
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
		List<Aops> aops = W.L.list(WebCommons.AOP_ALL);
		// 检查action是否有aop
		if (obj.getClass().isAnnotationPresent(Aop.class)) {
			Aop aop = obj.getClass().getAnnotation(Aop.class);
			if (aop != null && U.E.isNotEmpty(aop.value()))
				aops.add(WebCommons.AOPS.get(aop.value()));
		}
		// 检查method是否有aop
		if (method.isAnnotationPresent(Aop.class)) {
			Aop aop = method.getClass().getAnnotation(Aop.class);
			if (aop != null && U.E.isNotEmpty(aop.value()))
				aops.add(WebCommons.AOPS.get(aop.value()));
		}
		// 返回列表
		return W.L.notNull(aops);
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
//		if (U.E.isEmpty(actions)) {
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