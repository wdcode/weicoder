package com.weicoder.yaml;

import java.util.Map;
import java.util.Properties;

import org.yaml.snakeyaml.Yaml;

import com.weicoder.common.U.R;
import com.weicoder.common.config.Config;

public final class YamlConfig {
	private Config config;

	public YamlConfig(String name) {
		config = new Config(new Yaml().loadAs(R.loadResource(name), Properties.class));
	}

	public Config getConfig() {
		return config;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,Object> get(String key){
		return (Map<String, Object>) config.getProperties().get(key);
	}
}
