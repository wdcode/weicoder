package com.weicoder.yaml;

import java.io.IOException;
import java.io.InputStream; 
import java.util.Map;
import java.util.Properties;

import org.yaml.snakeyaml.Yaml;

public class YamlReader {
	public static Properties properties;

	private YamlReader() {
		if (SingletonHolder.instance != null) {
			throw new IllegalStateException();
		}
	}

	/**
	 * use static inner class achieve singleton
	 */
	private static class SingletonHolder {
		private static YamlReader instance = new YamlReader();
	}

	public static YamlReader getInstance() {
		return SingletonHolder.instance;
	}

	// init property when class is loaded
	static {

		InputStream in = null;
		try {
			properties = new Properties();
			Yaml yaml = new Yaml();
			in = YamlReader.class.getClassLoader().getResourceAsStream("config.yaml");
			properties = yaml.loadAs(in, Properties.class);
			System.out.println(properties);
			Map<String, Object> object = yaml.load(in);
			System.out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * get yaml property
	 *
	 * @param  key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getValueByKey(String root, String key) {
		return ((Map<String,Object>)properties.get(root)).get(key);
//		return rootProperty.getOrDefault(key, "");
	}
}
