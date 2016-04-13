package com.weicoder.tag.head;

import com.weicoder.tag.base.BaseSimpleTag;

/**
 * CSS头标签
 * @author WD 
 * @version 1.0 
 */
public final class CssTag extends BaseSimpleTag {
	@Override
	protected String getInfo() {
		StringBuilder info = new StringBuilder("<link href=\"");
		info.append(getPath());
		info.append("/wdstatic/css/");
		info.append(name);
		info.append(".css\" rel=\"stylesheet\" type=\"text/css\">");
		return info.toString();
	}
}
