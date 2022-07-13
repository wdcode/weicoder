package com.weicoder.html.jsoup;

import org.jsoup.nodes.Element;

import com.weicoder.html.Body;

/**
 * Body的Jsoup实现
 * @author wdcode
 *
 */
public class BodyJsoup extends TagJsoup implements Body{

	public BodyJsoup(Element e) {
		super(e);
	}
}
