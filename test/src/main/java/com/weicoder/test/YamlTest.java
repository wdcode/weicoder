package com.weicoder.test;

import com.weicoder.yaml.YamlReader;

public class YamlTest {

	public static void main(String[] args) {
		System.out.println(YamlReader.getInstance().getValueByKey("rpc", "rpct"));
		System.out.println(YamlReader.properties.getProperty("cache.expire"));
	} 
}
