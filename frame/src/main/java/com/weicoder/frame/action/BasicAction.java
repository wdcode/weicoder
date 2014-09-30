package com.weicoder.frame.action;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weicoder.frame.entity.Entity;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.crypto.Encrypts;
import com.weicoder.common.io.IOUtil;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.params.CommonParams;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.MathUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.json.JsonEngine;
import com.weicoder.web.constants.HttpConstants;
import com.weicoder.web.util.AttributeUtil;
import com.weicoder.web.util.CookieUtil;
import com.weicoder.web.util.IpUtil;
import com.weicoder.web.util.ResponseUtil;

/**
 * Struts2 Action 的抽象实现 其它Struts2 Action可继承此类
 * @author WD
 * @since JDK7
 * @version 1.0 2009-08-26
 */
public abstract class BasicAction {
	// 成功
	protected static final String				SUCCESS	= "success";
	// 错误
	protected static final String				ERROR	= "error";
	// 登录页
	protected static final String				LOGIN	= "login";
	// LIST
	protected final static String				LIST	= "list";
	// 回调方法处理
	protected final static Map<String, Method>	METHODS	= Maps.getMap();
	// 提交的url
	protected String							url;
	// 跨域方法
	protected String							callback;

	// 模板名
	protected String							module;
	// 方法名
	protected String							method;
	// 返回模式名
	protected String							mode;
	// 要回执消息的字段
	protected String							field;
	// HttpServletRequest
	protected HttpServletRequest				request;
	// HttpServletResponse
	protected HttpServletResponse				response;
	// 错误信息
	protected List<String>						error;
	// 错误信息
	protected List<String>						message;

	/**
	 * 初始化方法
	 */
	@PostConstruct
	protected void init() {
		// 声明错误信息
		error = Lists.getList();
		// 声明信息
		message = Lists.getList();
	}

	/**
	 * 获得url
	 * @return 提交的URL
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置url
	 * @param url 提交的URL
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获得跨域方法
	 * @return 跨域方法
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * 设置跨域方法
	 * @param callback 跨域方法
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}

	/**
	 * 设置模式名
	 * @param mode 模式名
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * 获得模式名
	 * @return
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * 获得错误信息列表
	 * @return
	 */
	public List<String> getError() {
		return error;
	}

	/**
	 * 获得信息列表
	 * @return
	 */
	public List<String> getMessage() {
		return message;
	}

	/**
	 * 获得国际化值
	 */
	public String add(List<Object> values) {
		return MathUtil.add(values.toArray()).toPlainString();
	}

	/**
	 * 获得域名路径
	 * @param name 文件名
	 * @return 域名路径
	 */
	public String getServer() {
		// 获得path
		String path = request.getServerName();
		// 返回域名路径
		return IpUtil.LOCAL_IP.equals(path) ? IpUtil.getIp() : path;
	}

	/**
	 * 获得域名路径
	 * @param name 文件名
	 * @return 域名路径
	 */
	public String getDomain() {
		// 获得域名
		String domain = HttpConstants.HTTP + getServer() + getBase();
		// 返回域名
		return domain.endsWith(StringConstants.BACKSLASH) ? domain : domain + StringConstants.BACKSLASH;
	}

	/**
	 * 获得项目路径
	 * @return 项目路径
	 */
	public String getBase() {
		return request.getContextPath();
	}

	/**
	 * 获得提交IP
	 * @return 提交IP
	 */
	public String getIp() {
		return IpUtil.getIp(request);
	}

	/**
	 * 设置属性
	 * @param key 属性键
	 * @param value 属性值
	 */
	public void set(String key, Object value) {
		AttributeUtil.set(request, response, key, value);
	}

	/**
	 * 根据属性键获得属性值
	 * @param key 属性键
	 * @return 属性值
	 */
	public Object get(String key) {
		return AttributeUtil.get(request, key);
	}

	/**
	 * 删除属性
	 * @param key 属性键
	 */
	public void remove(String key) {
		AttributeUtil.remove(request, response, key);
	}

	/**
	 * 添加错误信息 错误Field=key value=国际化value
	 * @param key 国际化文件的Key
	 */
	public String addError(String key) {
		error.add(key);
		return key;
	}

	/**
	 * 添加信息 调用addActionMessage做国际化处理
	 * @param key 国际化文件的Key
	 */
	public String addMessage(String key) {
		message.add(key);
		return key;
	}

	/**
	 * 获得HttpServletRequest
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * 获得HttpServletResponse
	 * @return HttpServletResponse
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * 获得客户端的Cookie数组
	 * @return Cookie数组
	 */
	public Cookie[] getCookies() {
		return request.getCookies();
	}

	/**
	 * 添加Cookie到客户端
	 * @param name CookieName
	 * @param value CookieValue
	 */
	public void addCookie(String name, String value) {
		CookieUtil.add(response, name, Encrypts.encrypt(value));
	}

	/**
	 * 删除Cookie
	 * @param name CookieName
	 * @param value CookieValue
	 */
	public void removeCookie(String name) {
		CookieUtil.remove(response, name);
	}

	/**
	 * 根据name获得Cookie 没找到返回null
	 * @param name CookieName
	 * @return Cookie
	 */
	public Cookie getCookie(String name) {
		return CookieUtil.getCookie(request, name);
	}

	/**
	 * 根据name获得CookieValue 没找到返回""
	 * @param name CookieName
	 * @return CookieValue
	 */
	public String getCookieValue(String name) {
		return CookieUtil.getCookieValue(request, name);
	}

	/**
	 * 截取字符串
	 * @param str 要截取的字符串
	 * @param len 截取长度
	 * @return 截取后字符串
	 */
	public String substring(String str, int len) {
		// 判断字符串为空
		if (EmptyUtil.isEmpty(str)) {
			return str;
		}
		// 判断长度大于指定截取长度
		if (str.length() > len) {
			return StringUtil.subString(str, 0, len) + "...";
		}
		// 返回原字符串
		return str;
	}

	/**
	 * 获得Action方法
	 * @return Action方法
	 */
	public List<Object> getFields(Collection<Object> list, String fieldName) {
		return BeanUtil.getFieldValues(list, fieldName);
	}

	/**
	 * 获得要显示的字段
	 * @return 要显示的字段
	 */
	public String getField() {
		return field;
	}

	/**
	 * 设置要显示的字段
	 * @param field 要显示的字段
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * 写数据到前端
	 * @param str 要写的字符串
	 */
	public void write(String str) {
		write(str, CommonParams.ENCODING);
	}

	/**
	 * 写数据到前端
	 * @param str 要写的字符串
	 * @param charsetName 编码
	 */
	public void write(String str, String charsetName) {
		// 清除缓存
		ResponseUtil.noCache(response);
		// 设置编码
		response.setCharacterEncoding(charsetName);
		// 写入到前端
		try {
			response.getWriter().write(str);
		} catch (IOException e) {}
	}

	/**
	 * 写数据到前端
	 * @param str 要写的字符串
	 */
	public void out(String str) {
		out(str, CommonParams.ENCODING);
	}

	/**
	 * 写数据到前端
	 * @param str 要写的字符串
	 * @param charsetName 编码
	 */
	public void out(String str, String charsetName) {
		// 清除缓存
		ResponseUtil.noCache(response);
		// 写入到前端
		try {
			IOUtil.write(response.getOutputStream(), str, charsetName, false);
		} catch (IOException e) {}
	}

	/**
	 * 把对象转换成json
	 * @param json 对象
	 * @param charsetName 编码
	 */
	public String json(Object data) {
		// 声明返回字符串
		StringBuilder s = new StringBuilder();
		// 如果callback不为空 填补左括号
		if (!EmptyUtil.isEmpty(callback)) {
			s.append(callback).append(StringConstants.LEFT_PARENTHESIS);
		}
		// 添加json数据
		s.append(data instanceof String || data instanceof Number ? Conversion.toString(data) : JsonEngine.toJson(data));
		// 如果callback不为空 填补右括号
		if (!EmptyUtil.isEmpty(callback)) {
			s.append(StringConstants.RIGHT_PARENTHESIS);
		}
		// 返回空
		return s.toString();
	}

	/**
	 * 输出数据到客户端方法
	 * @param data 数据对象
	 * @param charsetName 编码
	 */
	public String ajax(Object data, String charsetName) {
		// 写字符串
		write(json(data), charsetName);
		// 返回空
		return null;
	}

	/**
	 * 方法回调 所有直接Action回调的方法 一边统一处理
	 * @param obj 处理对象
	 * @return 返回标识
	 */
	public String callback(Object obj) {
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
			return Conversion.toString(BeanUtil.invoke(this, method, obj), null);
		}
		if (obj == null) {
			return addMessage(ERROR);
		} else if (obj instanceof String) {
			String re = Conversion.toString(obj);
			return SUCCESS.equals(re) || ERROR.equals(re) ? addMessage(re) : re;
		} else if (obj instanceof List<?> || obj instanceof Map<?, ?>) {
			return LIST;
		} else if (obj instanceof Boolean) {
			return Conversion.toBoolean(obj) ? SUCCESS : ERROR;
		} else if (obj instanceof Integer) {
			return EmptyUtil.isEmpty(obj) ? ERROR : SUCCESS;
		} else {
			return addMessage(SUCCESS);
		}
	}

	/**
	 * 设置模板名
	 * @param module 模板名
	 */
	public void setModule(String module) {
		this.module = module;
	}

	/**
	 * 获得模板名
	 * @return
	 */
	public String getModule() {
		return module;
	}

	/**
	 * 获得方法名
	 * @return
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * 以ajax模式输出数据到客户端方法
	 * @param json 对象
	 */
	protected String ajax(Object obj) {
		return ajax(obj == null ? ERROR : !EmptyUtil.isEmpty(field) && obj instanceof Entity ? BeanUtil.getFieldValue(obj, field) : obj, CommonParams.ENCODING);
	}

	/**
	 * 以sign模式输出数据到客户端方法
	 * @param json 对象
	 */
	protected String sign(Object obj) {
		return ajax(obj instanceof String || obj instanceof Number ? obj : EmptyUtil.isEmpty(obj) ? ERROR : SUCCESS);
	}

	/**
	 * 以key模式输出数据到客户端方法
	 * @param json 对象
	 */
	protected String key(Object obj) {
		return ajax(obj instanceof String || obj instanceof Number ? obj : obj instanceof Entity ? ((Entity) obj).getKey() : ERROR);
	}

	/**
	 * 获得程序路径
	 * @param name 文件名
	 * @return 程序路径
	 */
	public abstract String getRealPath(String name);
}
