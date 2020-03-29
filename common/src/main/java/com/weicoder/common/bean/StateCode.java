package com.weicoder.common.bean;

import com.weicoder.common.params.StateParams;

/**
 * 注解State返回状态码使用
 * 
 * @author wudi
 */
public final class StateCode {
	/** 状态码成功 */
	public final static StateCode SUCCESS = new StateCode(StateParams.SUCCESS, StateParams.SUCCESS_MSG);
	/** 状态码失败 */
	public final static StateCode ERROR   = new StateCode(StateParams.ERROR, StateParams.getMessage(StateParams.ERROR));
	/** 状态码失败 */
	public final static StateCode NULL    = new StateCode(StateParams.NULL, StateParams.getMessage(StateParams.NULL));

	// 状态码
	private int code;
	// 内容
	private Object content;
	// 状态码对应信息
	private String message;

	/**
	 * 构造状态码
	 * 
	 * @param  code 状态码
	 * @return      StateCode
	 */
	public static StateCode build(int code) {
		return build(code, StateParams.getMessage(code));
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
	 * 构造状态码
	 * 
	 * @param  code    状态码
	 * @param  message 状态码信息
	 * @return         StateCode
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
		return new Object[]{code, message};
	}

	/**
	 * 获得内容
	 * 
	 * @return 内容
	 */
	public Object getContent() {
		return content;
	}

	/**
	 * 设置内容
	 * 
	 * @param content 内容
	 */
	public void setContent(String content) {
		this.content = content;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateCode other = (StateCode) obj;
		if (code != other.code)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StateCode [code=" + code + ", content=" + content + ", message=" + message + "]";
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
