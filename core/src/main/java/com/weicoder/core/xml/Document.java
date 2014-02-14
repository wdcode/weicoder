package com.weicoder.core.xml;

import com.weicoder.common.interfaces.Empty;

/**
 * XML Document接口 <h2>注: 本包功能需要jdom或dom4j依赖包</h2>
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-18
 */
public interface Document extends Empty {
	/**
	 * 设置根节点
	 * @param root 根节点
	 */
	void setRootElement(Element root);

	/**
	 * 获得根接点
	 * @return 根接点
	 */
	Element getRootElement();
}
