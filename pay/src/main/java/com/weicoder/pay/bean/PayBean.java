package com.weicoder.pay.bean;

import java.math.BigDecimal;

/**
 * 支付实体
 * @author WD
 * @since JDK7
 * @version 1.0 2011-11-08
 */
public final class PayBean {
	// 订单号
	private String		no;
	// 支付类型
	private String		type;
	// 支付总额
	private BigDecimal	total;
	// 标题
	private String		subject;
	// 内容
	private String		body;
	// 支付渠道
	private String		ditch;

	/**
	 * 获得订单号
	 * @return 订单号
	 */
	public String getNo() {
		return no;
	}

	/**
	 * 设置订单号
	 * @param no 订单号
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * 获得支付总额
	 * @return 支付总额
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * 设置支付总额
	 * @param total 支付总额
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	/**
	 * 获得标题
	 * @return 标题
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * 设置标题
	 * @param subject 标题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 获得内容
	 * @return 内容
	 */
	public String getBody() {
		return body;
	}

	/**
	 * 设置内容
	 * @param body 内容
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * 获得支付类型
	 * @return 支付类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置支付类型
	 * @param type 支付类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获得支付渠道
	 * @return 支付渠道
	 */
	public String getDitch() {
		return ditch;
	}

	/**
	 * 设置支付渠道
	 * @param ditch 支付渠道
	 */
	public void setDitch(String ditch) {
		this.ditch = ditch;
	}
}
