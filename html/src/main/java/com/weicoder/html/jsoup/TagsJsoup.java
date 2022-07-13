package com.weicoder.html.jsoup;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.weicoder.common.W;
import com.weicoder.html.Tag;
import com.weicoder.html.Tags;

/**
 * Tags的Jsoup
 * 
 * @author wdcode
 *
 */
public class TagsJsoup extends ArrayList<Tag> implements Tags {
	private static final long	serialVersionUID	= 1L;
	// Elements
	private Elements			es;

	/**
	 * Tags构造
	 * 
	 * @param es Elements
	 */
	public TagsJsoup(Elements es) {
		this.es = es;
		for (Element e : es)
			super.add(new TagJsoup(e));
	}

	@Override
	public Tags not(String query) {
		return new TagsJsoup(es.not(query));
	}

	@Override
	public Tag first() { 
		return W.L.get(this, 0);
	}

	@Override
	public String text() { 
		return es.text();
	}
}
