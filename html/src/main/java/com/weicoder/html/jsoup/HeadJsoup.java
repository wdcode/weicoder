package com.weicoder.html.jsoup;

import org.jsoup.nodes.Element;

import com.weicoder.html.Head;

/**
 * Head的Jsoup实现
 * 
 * @author wdcode
 *
 */
public class HeadJsoup extends TagJsoup implements Head {

	public HeadJsoup(Element e) {
		super(e);
	}
}
