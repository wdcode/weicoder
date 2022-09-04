package com.weicoder.common.bean;

import java.util.Objects;

/**
 * 返回结果
 * 
 * @author wdcode
 *
 */
public class Result<T> {
	// 状态码
	protected int		code;
	// 内容
	protected T			content;
	// 状态码对应信息
	protected String	message;

	public Result() {
	}

	public Result(int code, T content, String message) {
		super();
		this.code = code;
		this.content = content;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public T getContent() {
		return content;
	}

	public T setContent(T content) {
		return this.content = content;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, content, message);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Result<?> other = (Result<?>) obj;
		return code == other.code && Objects.equals(content, other.content) && Objects.equals(message, other.message);
	}

	@Override
	public String toString() {
		return "Result [code=" + code + ", content=" + content + ", message=" + message + "]";
	} 
}
