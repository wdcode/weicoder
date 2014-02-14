package com.weicoder.core.engine;

import java.io.InputStream;
import java.util.List;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.core.log.Logs;
import com.weicoder.common.util.BeanUtil;
import com.weicoder.common.util.CloseUtil;

import com.linuxense.javadbf.DBFReader;

/**
 * DBF处理器
 * @author WD
 * @since JDK7
 * @version 1.0 2012-08-10
 */
public final class DbfEngine {
	/**
	 * 读取出所有元素到列表中
	 * @param in 输入流
	 * @return List 所有集合
	 */
	public static List<Object[]> read(InputStream in) {
		try {
			// 实例化DBF读取器
			DBFReader reader = new DBFReader(in);
			// 获得数据行数
			int size = reader.getRecordCount();
			// 声明返回列表
			List<Object[]> list = Lists.getList(size);
			// 循环填充数据
			for (int i = 0; i < size; i++) {
				list.add(reader.nextRecord());
			}
			// 返回数据列表
			return list;
		} catch (Exception e) {
			Logs.warn(e);
		} finally {
			CloseUtil.close(in);
		}
		// 返回空列表
		return Lists.getList();
	}

	/**
	 * 读取出所有元素到列表中
	 * @param in 输入流
	 * @param entityClass 实体类
	 * @return List 所有集合
	 */
	public static <E> List<E> read(InputStream in, Class<E> entityClass) {
		try {
			// 实例化DBF读取器
			DBFReader reader = new DBFReader(in);
			// 获得列名数量
			int len = reader.getFieldCount();
			// 声明列名数组
			String[] cols = new String[len];
			// 循环获得列名数组
			for (int i = 0; i < len; i++) {
				cols[i] = reader.getField(i).getName();
			}
			// 获得数据行数
			int size = reader.getRecordCount();
			// 声明返回列表
			List<E> list = Lists.getList(size);
			// 循环填充数据
			for (int i = 0; i < size; i++) {
				list.add(BeanUtil.copyProperties(entityClass, Maps.getMap(cols, reader.nextRecord())));
			}
			// 返回数据列表
			return list;
		} catch (Exception e) {
			Logs.warn(e);
		} finally {
			CloseUtil.close(in);
		}
		// 返回空列表
		return Lists.getList();
	}

	/**
	 * 私有构造
	 */
	private DbfEngine() {}
}
