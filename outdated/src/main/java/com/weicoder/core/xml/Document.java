package com.weicoder.core.xml;

/**
 * XML Document接口
 * <h2>注: 本包功能需要jdom或dom4j依赖包</h2>
 * @author WD
 * @version 1.0
 */
public interface Document {
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
