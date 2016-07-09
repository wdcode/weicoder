package com.weicoder.tag.head;

import com.weicoder.tag.base.BaseSimpleTag;

/**
 * JS头标签
 * @author WD 
 * @version 1.0 
 */
public final class JsTag extends BaseSimpleTag {
	@Override
	protected String getInfo() {
		StringBuilder info = new StringBuilder("<script type=\"text/javascript\" src=\"");
		info.append(getPath());
		info.append("/wdstatic/js/");
		info.append(name);
		info.append(".js\"></script>");
		return info.toString();
	}
}
