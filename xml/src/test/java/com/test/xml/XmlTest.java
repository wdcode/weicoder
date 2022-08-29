package com.test.xml; 
 
import org.junit.jupiter.api.Test;

import com.weicoder.common.U;
import com.weicoder.common.U.D;
import com.weicoder.xml.Document;
import com.weicoder.xml.XmlEngine;
import com.weicoder.xml.builder.XmlBuilder;

public class XmlTest {

	@Test
	public void main() {
		// 1910 1821 2059 - 1961 1903 1890
		SearchBean bean = new SearchBean(1,1, "hhh");
		String xml = XmlEngine.toXML(bean);
		System.out.println(xml);
		System.out.println(XmlEngine.toBean(xml, SearchBean.class));
		// jdom2 1577 10685 7904 - 1920 2078 1739
		// dom4j 9165 4545 5428 - 934 918 888
		int n = 1;
		String f = "user.xml";  
		D.dura();
		Document doc = XmlBuilder.readDocument(U.I.readString(U.R.loadResource(f)));
//		Document doc = null;
		System.out.println(doc.getRootElement().getName());
		System.out.println(doc.getRootElement().getText());
		System.out.println(doc.getRootElement().getElements().size());
		doc.getRootElement().getElements().forEach(e -> System.out.println(e.getName()));
		for (int i = 0; i < n; i++) {
			doc = XmlBuilder.readDocument(U.R.loadResource(f));
			doc.getRootElement().getName();
			doc.getRootElement().getElements().forEach(e -> e.getName());
			xml = XmlEngine.toXML(bean);
			XmlEngine.toBean(xml, SearchBean.class);
		}
		System.out.println(doc.getRootElement().getName());
		System.out.println(doc.getRootElement().getText());
		System.out.println(doc.getRootElement().getElements().size());
		System.out.println(D.dura());
	}
}
