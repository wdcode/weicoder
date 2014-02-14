package com.weicoder.admin.exception;

/**
 * 权限异常
 * @author WD
 * @since JDK7
 * @version 1.0 2009-09-30
 */
public final class AdminException extends RuntimeException {
	// 序列化ID
	private static final long	serialVersionUID	= -5051653856175182140L;

	public AdminException() {
		super();
	}

	public AdminException(String message) {
		super(message);
	}

	public AdminException(Throwable cause) {
		super(cause);
	}
}
