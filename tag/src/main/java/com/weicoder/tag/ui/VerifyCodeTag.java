package com.weicoder.tag.ui;

import com.weicoder.common.util.EmptyUtil;
import com.weicoder.tag.base.BaseSimpleTag;

/**
 * 验证码标签
 * @author WD
 * @since JDK7
 * @version 1.0 2009-09-25
 */
public final class VerifyCodeTag extends BaseSimpleTag {
	// 获得图片的url
	private String	url;
	// CSS样式
	private String	cssClass;

	/**
	 * 获得图片的url
	 * @return 获得图片的url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置获得图片的url
	 * @param url 获得图片的url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获得CSS样式
	 * @return CSS样式
	 */
	public String getCssClass() {
		return cssClass;
	}

	/**
	 * 设置CSS样式
	 * @param cssClass CSS样式
	 */
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	@Override
	protected String getInfo() {
		// 要打印到页面的字符串
		StringBuilder info = new StringBuilder("<img id='verifyCode' src='");
		info.append(url);
		info.append("'");
		// 如果样式不为空设置样式
		if (!EmptyUtil.isEmpty(cssClass)) {
			info.append(" class='");
			info.append(cssClass);
			info.append("'");
		}
		info.append(" onclick='javascript:var src=this.src;src.indexOf(\"?\")==-1?this.src=src+\"?\":this.src=src.substring(0,src.indexOf(\"?\"));'");
		info.append(" style='cursor:pointer;*cursor:hand;'/>");
		return info.toString();
	}
}
