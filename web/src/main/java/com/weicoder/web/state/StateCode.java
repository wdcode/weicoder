package com.weicoder.web.state;

import com.weicoder.core.params.ErrorCodeParams;

/**
 * 注解State返回状态码使用
 * 
 * @author wudi
 */
public final class StateCode {
	/** 状态码成功 */
	public final static StateCode SUCCESS = new StateCode(ErrorCodeParams.SUCCESS, ErrorCodeParams.getMessage(ErrorCodeParams.SUCCESS));
	/** 状态码失败 */
	public final static StateCode ERROR   = new StateCode(ErrorCodeParams.ERROR, ErrorCodeParams.getMessage(ErrorCodeParams.ERROR));

	// 状态码
	private int code;
	// 状态码对应信息
	private String message;

	/**
	 * 构造状态码
	 * 
	 * @param  code 状态码
	 * @return      StateCode
	 */
	public static StateCode build(int code) {
		return build(code, ErrorCodeParams.getMessage(code));
	}

	/**
	 * 构造状态码
	 * 
	 * @param  code    状态码
	 * @param  message 状态码信息
	 * @return         StateCode
	 */
	public static StateCode build(int code, String message) {
		return new StateCode(code, message);
	}

	/**
	 * 获得状态码
	 * 
	 * @return 状态码
	 */
	public int getCode() {
		return code;
	}

	/**
	 * 设置状态码
	 * 
	 * @param code 状态码
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * 获得信息
	 * 
	 * @return 信息
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 设置信息
	 * 
	 * @param message 信息
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 构造方法
	 * 
	 * @param code    状态码
	 * @param message 状态码消息
	 */
	private StateCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
