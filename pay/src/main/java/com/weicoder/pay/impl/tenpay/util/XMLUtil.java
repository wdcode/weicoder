package com.weicoder.pay.impl.tenpay.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.weicoder.common.util.CloseUtil;
import com.weicoder.core.xml.Document;
import com.weicoder.core.xml.Element;
import com.weicoder.core.xml.builder.XmlBuilder;

/**
 * xml工具类
 * @author miklchen
 */
public class XMLUtil {

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */ 
	public static Map<String, String> doXMLParse(String strxml) {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		Map<String, String> m = new HashMap<String, String>();
		InputStream in = null;
		try {
			in = HttpClientUtil.String2Inputstream(strxml);
			Document doc = XmlBuilder.createXMLRead().build(in);
			Element root = doc.getRootElement();
			List<Element> list = root.getElements();
			Iterator<Element> it = list.iterator();
			while (it.hasNext()) {
				Element e = it.next();
				String k = e.getName();
				String v = "";
				List<Element> children = e.getElements();
				if (children.isEmpty()) {
					v = e.getText();
				} else {
					v = XMLUtil.getChildrenText(children);
				}

				m.put(k, v);
			}
		} catch (Exception e) {} finally {
			// 关闭流
			CloseUtil.close(in);
		}
		return m;
	}

	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */ 
	public static String getChildrenText(List<Element> children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator<Element> it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getText();
				List<Element> list = e.getElements();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(XMLUtil.getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

	/**
	 * 获取xml编码字符集
	 * @param strxml
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	public static String getXMLEncoding(String strxml) {
		InputStream in = HttpClientUtil.String2Inputstream(strxml);
		Document doc = XmlBuilder.createXMLRead().build(in);
		return (String) doc.getRootElement().getAttributeValue("encoding");
	}

}
