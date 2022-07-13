package com.weicoder.html.jsoup;

import org.jsoup.nodes.Document;

import com.weicoder.html.Body;
import com.weicoder.html.Head;
import com.weicoder.html.Html;

/**
 * Html的jsoup实现
 * 
 * @author wdcode
 *
 */
public class HtmlJsoup implements Html {
	// jsoup的Document
	private Document doc;

	/**
	 * 构造
	 * 
	 * @param doc Document
	 */
	public HtmlJsoup(Document doc) {
		this.doc = doc;
	}

	@Override
	public Head head() {
		return new HeadJsoup(doc.head());
	}

	@Override
	public Body body() {
		return new BodyJsoup(doc.body());
	}
}
