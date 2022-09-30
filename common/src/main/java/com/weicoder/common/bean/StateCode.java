package com.weicoder.common.bean;

import com.weicoder.common.params.P;

/**
 * 注解State返回状态码使用
 * 
 * @author wudi
 */
public final class StateCode extends Result<Object> {
	/** 状态码成功 */
	public final static StateCode SUCCESS = new StateCode(P.S.SUCCESS, P.S.SUCCESS_MSG);
	/** 状态码失败 */
	public final static StateCode ERROR = new StateCode(P.S.ERROR, P.S.getMessage(P.S.ERROR));
	/** 状态码失败 */
	public final static StateCode NULL = new StateCode(P.S.NULL, P.S.getMessage(P.S.NULL));

	/**
	 * 构造状态码
	 * 
	 * @param code 状态码
	 * @return StateCode
	 */
	public static StateCode build(int code) {
		return build(code, P.S.getMessage(code));
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
		this.code = P.S.SUCCESS;
		this.content = content;
		this.message = P.S.SUCCESS_MSG;
	}

	private StateCode() {
	}
}
