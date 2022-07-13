package com.weicoder.html.jsoup;

import org.jsoup.nodes.Element;

import com.weicoder.html.Tag;
import com.weicoder.html.Tags;

/**
 * Tag的Jsoup
 * 
 * @author wdcode
 *
 */
public class TagJsoup implements Tag {
	// Jsoup的Element
	private Element e;

	/**
	 * 构造Element
	 * 
	 * @param e Element
	 */
	public TagJsoup(Element e) {
		this.e = e;
	}

	@Override
	public Tag id(String id) {
		return new TagJsoup(e.getElementById(id));
	}

//	@Override
//	public Tag tag(String name) {
//		return tags(name).get(0);
//	}

	@Override
	public Tags tags(String name) {
		return new TagsJsoup(e.getElementsByTag(name));
	}

	@Override
	public Tags css(String css) {
		return new TagsJsoup(e.getElementsByClass(css));
	}

	@Override
	public String text() {
		return e.text();
	}

	@Override
	public boolean hasCss(String css) {
		return e.hasClass(css);
	}
}
