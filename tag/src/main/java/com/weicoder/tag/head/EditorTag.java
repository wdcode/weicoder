package com.weicoder.tag.head;

import com.weicoder.tag.base.BaseSimpleTag;

/**
 * Editor头标签
 * @author WD 
 * @version 1.0 
 */
public final class EditorTag extends BaseSimpleTag {
	@Override
	protected String getInfo() {
		StringBuilder info = new StringBuilder("<script type=\"text/javascript\" src=\"");
		info.append(getPath());
		info.append("/wdstatic/editor/");
		info.append(name);
		info.append("/");
		info.append(name);
		info.append(".js\"></script>");
		return info.toString();
	}
}
