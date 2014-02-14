package com.weicoder.core.nosql.hbase.factory;

import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import com.weicoder.common.constants.StringConstants;
import com.weicoder.common.lang.Bytes;
import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.core.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.StringUtil;
import com.weicoder.core.nosql.hbase.HBaseDao;

/**
 * HBaseDao实现
 * @author WD
 * @since JDK7
 * @version 1.0 2010-12-12
 */
final class HBaseDaoImpl implements HBaseDao {
	// 配置
	private Configuration	cfg;
	// 表名
	private String			tableName;
	// 表对象
	private HTable			table;

	/**
	 * 构造方法
	 * @param cfg 配置
	 * @param tableName 表名
	 */
	public HBaseDaoImpl(Configuration cfg, String tableName) {
		this.cfg = cfg;
		this.tableName = tableName;
		try {
			table = new HTable(this.cfg, this.tableName);
		} catch (Exception e) {
			Logs.warn(e);
		}
	}

	/**
	 * 添加数据
	 * @param row 行数
	 * @param key 键
	 * @param value 值
	 */
	public void add(String row, String key, String value) {
		try {
			// 声明Put
			Put put = new Put(StringUtil.toBytes(row));
			// 添加键值
			put.add(StringUtil.toBytes(row), StringUtil.toBytes(key), StringUtil.toBytes(value));
			// 添加到表里
			table.put(put);
		} catch (Exception e) {
			Logs.warn(e);
		}
	}

	/**
	 * 删除行数据
	 * @param rowkey 行键
	 */
	public void delete(String... rowkey) {
		// 声明列表
		List<Delete> list = Lists.getList(rowkey.length);
		// 循环获得删除对象
		for (int i = 0; i < rowkey.length; i++) {
			// 添加到列表
			list.add(new Delete(Bytes.toBytes(rowkey[i])));
		}
		// 判断如果列表不为空
		if (EmptyUtil.isEmpty(list)) {
			// 删除列表
			try {
				table.delete(list);
			} catch (Exception e) {
				Logs.warn(e);
			}
		}
	}

	/**
	 * 根据键获得值
	 * @param rowKey 键
	 * @return 值
	 */
	public String get(String rowKey) {
		try {
			// 获得结果集
			Result rs = table.get(new Get(Bytes.toBytes(rowKey)));
			// 获得键值
			Cell cell = rs.rawCells()[0];
			// 判断键值不为空
			if (cell != null) {
				return new String(CellUtil.cloneValue(cell));
			}
		} catch (Exception e) {
			// 记录日志
			Logs.warn(e);
		}
		// 返回空字符串
		return StringConstants.EMPTY;
	}

	/**
	 * 读取所有
	 * @return 数据列表
	 */
	public List<Map<String, String>> queryAll() {
		try {
			// 声明查询器
			Scan s = new Scan();
			// 获得查询结果
			ResultScanner ss = table.getScanner(s);
			// 声明列表
			List<Map<String, String>> list = Lists.getList();
			// 循环获得所以结果
			for (Result r : ss) {
				// 获得所有键值
				for (Cell cell : r.rawCells()) {
					// 声明Map
					Map<String, String> map = Maps.getMap();
					// 设置键值
					map.put(StringUtil.toString(CellUtil.cloneQualifier(cell)), StringUtil.toString(CellUtil.cloneValue(cell)));
					// 把结果添加到列表
					list.add(map);
				}
			}
			// 返回列表
			return list;
		} catch (Exception e) {
			return Lists.emptyList();
		}
	}
}
