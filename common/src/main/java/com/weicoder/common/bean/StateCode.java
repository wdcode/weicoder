package com.weicoder.common.bean;

import com.weicoder.common.params.StateParams;

/**
 * 注解State返回状态码使用
 * 
 * @author wudi
 */
public final class StateCode extends Result<Object> {
	/** 状态码成功 */
	public final static StateCode SUCCESS = new StateCode(StateParams.SUCCESS, StateParams.SUCCESS_MSG);
	/** 状态码失败 */
	public final static StateCode ERROR = new StateCode(StateParams.ERROR, StateParams.getMessage(StateParams.ERROR));
	/** 状态码失败 */
	public final static StateCode NULL = new StateCode(StateParams.NULL, StateParams.getMessage(StateParams.NULL));

	/**
	 * 构造状态码
	 * 
	 * @param code 状态码
	 * @return StateCode
	 */
	public static StateCode build(int code) {
		return build(code, StateParams.getMessage(code));
	}

	/**
	 * 构造状态码
	 * 
	 * @param code    状态码
	 * @param message 状态码信息
	 * @return StateCode
	 */
	public static StateCode build(int code, String message) {
		return new StateCode(code, message);
	}

	/**
	 * 构造状态码
	 * 
	 * @param code    状态码
	 * @param message 状态码信息
	 * @return StateCode
	 */
	public static StateCode build(Object content) {
		return new StateCode(content);
	}

	/**
	 * 判断是否成功状态
	 * 
	 * @return 是否成功状态
	 */
	public boolean success() {
		return this.equals(SUCCESS);
	}

	/**
	 * 转换成对象数组 表示 code,message
	 * 
	 * @return new Object[]{code, message}
	 */
	public Object[] to() {
		return new Object[] { code, message };
	}

	private StateCode(int code, String message) {
		this.code = code;
		this.content = message;
		this.message = message;
	}

	private StateCode(Object content) {
		this.code = StateParams.SUCCESS;
		this.content = content;
		this.message = StateParams.SUCCESS_MSG;
	}

	private StateCode() {
	}
}
