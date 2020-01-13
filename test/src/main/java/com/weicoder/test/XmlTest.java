package com.weicoder.test;

import com.weicoder.xml.XmlEngine;

public class XmlTest {

	public static void main(String[] args) {
		// 1910 1821 2059 - 1961 1903 1890
		SearchBean bean = new SearchBean(1,1, "hhh");
		String xml = XmlEngine.toXML(bean);
		System.out.println(xml);
		System.out.println(XmlEngine.toBean(xml, SearchBean.class));
		// jdom2 1577 10685 7904 - 1920 2078 1739
		// dom4j 9165 4545 5428 - 934 918 888
		int n = 100;
//		String f = "user.xml";
//		InputStream in = ResourceUtil.loadResource(f); 
		long curr = System.currentTimeMillis();
//		Document doc = XmlBuilder.readDocument(ResourceUtil.loadResource(f));
//		System.out.println(doc.getRootElement().getName());
//		System.out.println(doc.getRootElement().getText());
//		System.out.println(doc.getRootElement().getElements().size());
//		doc.getRootElement().getElements().forEach(e -> System.out.println(e.getName()));
		for (int i = 0; i < n; i++) {
//			doc = XmlBuilder.readDocument(ResourceUtil.loadResource(f));
//			doc.getRootElement().getName();
//			doc.getRootElement().getElements().forEach(e -> e.getName());
			xml = XmlEngine.toXML(bean);
			XmlEngine.toBean(xml, SearchBean.class);
		}
//		System.out.println(doc.getRootElement().getName());
//		System.out.println(doc.getRootElement().getText());
//		System.out.println(doc.getRootElement().getElements().size());
		System.out.println(System.currentTimeMillis() - curr);
	}
}
