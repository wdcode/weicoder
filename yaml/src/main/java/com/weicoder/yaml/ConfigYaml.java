package com.weicoder.yaml;

import java.util.Properties;

import org.yaml.snakeyaml.Yaml;

import com.weicoder.common.U.R;
import com.weicoder.common.config.ConfigProperties;

/**
 * Yaml读取配置
 * 
 * @author wdcode
 *
 */
public class ConfigYaml extends ConfigProperties {

	public ConfigYaml(String name) {
		super(new Yaml().loadAs(R.loadResource(name), Properties.class));
	}
}
