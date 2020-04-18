package com.weicoder.common.exception;

import com.weicoder.common.bean.StateCode;

/**
 * 状态码异常类
 * 
 * @author wudi
 */
public class StateException extends RuntimeException {
	private static final long serialVersionUID = 155084592472374331L;
	// 状态码
	private StateCode code;

	/**
	 * 构造 默认StateCode.ERROR
	 */
	public StateException() {
		this(StateCode.ERROR);
	}

	/**
	 * 根据错误码构造异常
	 * 
	 * @param code 错误码
	 */
	public StateException(int code) {
		this(StateCode.build(code));
	}

	/**
	 * 根据错误码和错误信息构造异常
	 * 
	 * @param code    错误码
	 * @param message 错误信息
	 */
	public StateException(int code, String message) {
		this(StateCode.build(code, message));
	}

	/**
	 * 根据状态码信息构造异常
	 * 
	 * @param code 状态码信息
	 */
	public StateException(StateCode code) {
		this.code = code;
	}

	/**
	 * 获得异常状态码
	 * 
	 * @return 状态码
	 */
	public StateCode state() {
		return code;
	}
}
