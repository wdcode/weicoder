package com.weicoder.admin.template;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.weicoder.common.constants.FileConstants;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.io.FileUtil;
import com.weicoder.common.params.Params;
import com.weicoder.common.lang.Conversion;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.xml.Attribute;
import com.weicoder.core.xml.Element;
import com.weicoder.core.xml.builder.XmlBuilder;

/**
 * 模板处理
 * @author WD
 * @since JDK7
 * @version 1.0 2012-02-20
 */
public final class TemplateEngine {
	/**
	 * Class路径
	 */
	public static String												classPath;
	// 模板列表
	public final static Map<String, Map<String, Map<String, Object>>>	TEMPLATES;
	// 基本模板Map
	private final static Map<String, Map<String, Object>>				TEMPLATES_BASE;
	// 模板路径
	private final static String											TEMPLET_PATH;
	// 基础模板路径
	private final static String											TEMPLET_BASE;
	/* 字符常量 */
	private final static String											STRING_BASE;
	private final static String											STRING_MODULE;
	private final static String											STRING_TEMPLATE;
	private final static String											STRING_NODE;
	private final static String											STRING_NEXT;
	private final static String											STRING_REF;

	/**
	 * 静态初始化
	 */
	static {
		// 基本模板Map
		TEMPLATES_BASE = Maps.getMap();
		// 模板列表
		TEMPLATES = Maps.getMap();
		/* 字符常量 */
		STRING_BASE = "base";
		STRING_MODULE = "module";
		STRING_TEMPLATE = "template";
		STRING_NODE = "node";
		STRING_NEXT = "next";
		STRING_REF = "ref";
		// 模板路径
		TEMPLET_PATH = Params.getString("templates.path", "templates");
		// 基础模板路径
		TEMPLET_BASE = File.separator + STRING_BASE + StringConstants.POINT + FileConstants.SUFFIX_XML;
	}

	/**
	 * 初始化模板
	 * @param classPath 类路径
	 */
	public static void init() {
		// 清除模板列表
		clear();
		// 获得模板路径
		String path = classPath + TEMPLET_PATH;
		// 加载基础模板
		base(XmlBuilder.readDocument(FileUtil.getFile(path + TEMPLET_BASE)).getRootElement());
		// 循环模板文件 加载模板
		for (File file : FileUtil.getFile(path).listFiles()) {
			template(XmlBuilder.readDocument(file).getRootElement());
		}
	}

	/**
	 * 清空列表
	 */
	private static void clear() {
		TEMPLATES_BASE.clear();
		TEMPLATES.clear();
	}

	/**
	 * 基础模板加载
	 * @param root 根节点
	 */
	private static void base(Element root) {
		// 循环基础模板列表
		for (Element base : root.getElements(STRING_BASE)) {
			// 声明一个Map
			Map<String, Object> map = getElement(base);
			// 添加到基础模板常量
			TEMPLATES_BASE.put(Conversion.toString(map.remove(StringConstants.KEY)), map);
		}
	}

	/**
	 * 模板加载
	 * @param root 根节点
	 */
	private static void template(Element root) {
		// 循环节点
		for (Element eModule : root.getElements(STRING_MODULE)) {
			// 声明模块Map
			Map<String, Map<String, Object>> mapModule = Maps.getMap();
			// 获得模块Key
			String moduleKey = eModule.getAttributeValue(StringConstants.KEY);
			// 循环节点
			for (Element eTemple : eModule.getElements(STRING_TEMPLATE)) {
				// 声明节点Map
				Map<String, Object> mapTemple = Maps.getMap();
				// 获得ref
				String ref = eTemple.getAttributeValue(STRING_REF);
				// 如果ref不为空
				if (!EmptyUtil.isEmpty(ref)) {
					// 获得mapTemple
					Map<String, Object> refMap = mapModule.get(ref);
					// remMap不为空
					if (!EmptyUtil.isEmpty(refMap)) {
						mapTemple.putAll(refMap);
					}
				}
				// 声明节点Map
				mapTemple.putAll(getElement(eTemple));
				// 删除ref属性
				mapTemple.remove(STRING_REF);
				// 解析节点
				node(eTemple, mapTemple);
				// 把模板添加到模块里
				mapModule.put(Conversion.toString(mapTemple.remove(StringConstants.KEY)), mapTemple);
			}
			// 把模块添加到模板常量里
			TEMPLATES.put(moduleKey, mapModule);
		}
	}

	/**
	 * 解析元素为Map
	 * @param e 元素
	 * @return Map
	 */
	private static Map<String, Object> getElement(Element e) {
		// 声明Map
		Map<String, Object> map = Maps.getMap();
		// 获得节点ref属性
		String ref = Conversion.toString(e.getAttributeValue(STRING_REF));
		// 判断不为空
		if (!EmptyUtil.isEmpty(ref)) {
			// 获得ref base map
			Map<String, Object> refMap = TEMPLATES_BASE.get(ref);
			// refMap不为空
			if (!EmptyUtil.isEmpty(refMap)) {
				// 在基础模板里获得节点
				map.putAll(refMap);
			}
			// 删除ref属性
			map.remove(STRING_REF);
		}
		// 循环节点属性
		for (Attribute attr : e.getAttributes()) {
			// 把属性键值写入到节点Map
			map.put(attr.getName(), attr.getValue());
		}
		// 返回Map
		return map;
	}

	/**
	 * 获得节点元素
	 * @param e 含有节点元素的Element
	 * @return 解析后的节点元素
	 */
	private static void node(Element eTemple, Map<String, Object> mapTemple) {
		// 循环节点
		for (Element eNode : eTemple.getElements(STRING_NODE)) {
			// 获得节点Key
			String nodeKey = eNode.getAttributeValue(StringConstants.KEY);
			// 获得节点对象
			Object node = mapTemple.get(nodeKey);
			// 声明节点列表
			List<Object> list = null;
			// 判断节点类型
			if (node instanceof List<?>) {
				// 强制转换
				list = (List<Object>) node;
			} else {
				// 获得列表
				list = Lists.getList();
			}
			// 获得Map节点
			Map<String, Object> mapNode = getElement(eNode);
			// 删除Key
			mapNode.remove(StringConstants.KEY);
			// 判断是否有下级元素
			if (Conversion.toBoolean(mapNode.get(STRING_NEXT))) {
				node(eNode, mapNode);
			}
			// 判断Key不为空
			if (EmptyUtil.isEmpty(nodeKey)) {
				// 添加节点Map到模板里
				mapTemple.putAll(mapNode);
			} else {
				// 添加节点Map到列表里
				list.add(mapNode);
				// 添加列表到模板Map里
				mapTemple.put(nodeKey, list);
			}
		}
	}

	/**
	 * 静态初始化
	 */
	private TemplateEngine() {}
}
