package com.weicoder.excel.base;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.weicoder.common.lang.Lists;
import com.weicoder.common.lang.Maps;
import com.weicoder.common.log.Logs;
import com.weicoder.common.U;
import com.weicoder.common.U.B;
import com.weicoder.common.U.C;
import com.weicoder.common.W.L;
import com.weicoder.excel.Excel;

/**
 * Excel相关操作类,使用getExcelUtil或createExcelUtil获得实例
 * 
 * @author WD
 */
public abstract class BaseExcel implements Excel {
	// 第几个工作薄
	private int index;

	/**
	 * 获得第一张Sheet表内容
	 * 
	 * @return 返回List
	 */
	public final List<List<String>> readSheet() {
		return readSheet(index);
	}

	/**
	 * 获得所有Sheet表内容
	 * 
	 * @return 返回List
	 */
	public final List<List<String>> readSheetByAll() {
		// 获得Sheet数量
		int num = getSheets();
		// 声明列表 保存所有数据
		List<List<String>> list = Lists.newList();
		// 循环Sheet
		for (int i = 0; i < num; i++) {
			// 获得指定Sheet页值 添加到列表
			list.addAll(readSheet(i));
		}
		// 返回列表
		return list;
	}

	@Override
	public <E> void write(List<E> list) {
		list.forEach(e -> write(e));
	}

	@Override
	public <E> List<E> reads(Class<E> cls) {
		// 获得行数
		int rows = getRows();
		Logs.info("read excel cls={} rows={}", cls, rows);
		// 声明列表
		List<E> list = L.newList(rows);
		// 读取所有行
		for (int i = 0; i < rows; i++)
			list.add(read(i, cls));
		// 返回列表
		return list;
	}

	@Override
	public <E> E read(int row, Class<E> cls) {
		// 实例化对象
		E e = C.newInstance(cls);
		// 读取列按字段序列注入属性
		for (int i = 0; i < getColumns(); i++)
			B.setFieldValue(e, i, readContents(row, i));
		// 返回对象
		return e;
	}

	@Override
	public List<String> readCol(int col) {
		int rows = getRows();
		List<String> cols = L.newList(rows);
		for (int i = 0; i < rows; i++)
			cols.add(readContents(i, col));
		return cols;
	}

	/**
	 * 获得指定页码Sheet表内容
	 * 
	 * @param index 页码
	 * @return 返回List
	 */
	public final List<List<String>> readSheet(int index) {
		// 设置Sheet页码
		setIndex(index);
		// 获得行数
		int rows = getRows();
		// 获得列数
		int cols = getColumns();
		// 声明行 列表
		List<List<String>> lsRow = Lists.newList(rows);
		// 声明列 列表
		List<String> lsCol = null;
		// 获得Sheet所有行和列内容
		// 行循环
		for (int i = 0; i < rows; i++) {
			// 实例化行 列表
			lsCol = Lists.newList(cols);
			// 列循环
			for (int j = 0; j < cols; j++) {
				// 添加内容到列 列表
				lsCol.add(readContents(i, j));
			}
			// 把列 列表 添加到行 列表中
			lsRow.add(lsCol);
		}
		// 返回行 列表
		return lsRow;
	}

	/**
	 * 获得指定名称Sheet表内容
	 * 
	 * @param sheetName Sheet名
	 * @return 返回List
	 */
	public final List<List<String>> readSheet(String sheetName) {
		// 获得Sheet位置
		int index = getSheetIndex(sheetName);
		// 判断是否存在
		if (index == -1) {
			return Collections.emptyList();
		}
		// 返回Sheet内容
		return readSheet(index);
	}

	/**
	 * 获得指定名称Sheet表内容
	 * 
	 * @param sheetName Sheet名
	 * @return 返回List
	 */
	public final List<Map<String, String>> readSheetByCol(String sheetName) {
		// 获得Sheet位置
		int index = getSheetIndex(sheetName);
		// 判断是否存在
		if (index == -1) {
			return Collections.emptyList();
		}
		// 返回Sheet内容
		return readSheetByCol(index);
	}

	/**
	 * 获得第一张Sheet表内容
	 * 
	 * @return 返回List
	 */
	public final List<Map<String, String>> readSheetByCol() {
		return readSheetByCol(index);
	}

	/**
	 * 获得第一张Sheet表内容
	 * 
	 * @return 返回List
	 */
	public final List<Map<String, String>> readSheetByColByAll() {
		// 获得Sheet数量
		int num = getSheets();
		// 声明列表保存数据
		List<Map<String, String>> list = Lists.newList();
		// 循环Sheet
		for (int i = 0; i < num; i++) {
			// 获得指定Sheet页值 添加到列表
			list.addAll(readSheetByCol(i));
		}
		// 返回列表
		return list;
	}

	/**
	 * 获得指定页码Sheet表内容
	 * 
	 * @param index 页码
	 * @return 返回List @ 读取失败
	 */
	public final List<Map<String, String>> readSheetByCol(int index) {
		// 获得列名
		String[] colNames = getColumnNames(index);
		// 获得Sheet所有内容
		List<List<String>> lsList = readSheet(index);
		// 列名和内容是否存在
		if (U.E.isEmpty(colNames) || U.E.isEmpty(lsList)) {
			return Collections.emptyList();
		}
		// 列表大小
		int size = lsList.size();
		// 声明列表 保存所有数据
		List<Map<String, String>> lsMap = Lists.newList(size - 1);
		// 声明列表 保存行数据
		List<String> list = null;
		// 声明Map 保存行数据
		Map<String, String> map = null;
		// 保存大小
		int num = colNames.length;
		// 循环行
		for (int i = 1; i < size; i++) {
			// 获得行内容
			list = lsList.get(i);
			// 实例化Map
			map = Maps.newMap();
			// 循环
			for (int j = 0; j < num; j++) {
				// 设置列名与列值
				map.put(colNames[j], list.get(j));
			}
			// 添加到列表
			lsMap.add(map);
		}
		// 返回列表
		return lsMap;
	}

	/**
	 * 获得Sheet，第一行第一列内容
	 * 
	 * @return 单元格内容
	 */
	public final String readContents() {
		return readContents(0, 0);
	}

	/**
	 * 获得指定Sheet，指定行列内容
	 * 
	 * @param index Sheet码 注 添加了这个参数 就直接设置了Sheet 以后调用 getContents(int row,int col)就可获得这页内容
	 * @param row   行码
	 * @param col   列码
	 * @return 单元格内容
	 */
	public final String readContents(int index, int row, int col) {
		// 设置Sheet页码
		setIndex(index);
		// 调用自己方法
		return readContents(row, col);
	}

	/**
	 * 写入第一张Sheet表内容
	 * 
	 * @param list 列表
	 */
	public final void writeSheet(List<List<String>> list) {
		writeSheet(list, index);
	}

	/**
	 * 写入数据到工作薄中
	 * 
	 * @param list  List要写入的内容
	 * @param index 页码
	 */
	public final void writeSheet(List<List<String>> list, int index) {
		// 设置Sheet索引
		setIndex(index);
		// 声明列表 用于 保存行数据
		List<String> ls = null;
		// 循环行
		for (int i = 0; i < list.size(); i++) {
			// 获得行数据
			ls = list.get(i);
			// 循环列
			for (int j = 0; j < ls.size(); j++) {
				// 添加数据
				writeContents(i, j, ls.get(j));
			}
		}
	}

	/**
	 * 获得第一张Sheet表内容
	 * 
	 * @param list 列表
	 */
	public final void writeSheetByCol(List<Map<String, String>> list) {
		writeSheetByCol(list, index);
	}

	/**
	 * 写Sheet，第一行第一列内容
	 * 
	 * @param content 单元格内容
	 */
	public final void writeContents(String content) {
		writeContents(0, 0, content);
	}

	/**
	 * 写Sheet，新行第N列内容
	 * 
	 * @param col     第几列
	 * @param content 单元格内容
	 */
	public final void writeContentsByNewRow(int col, String content) {
		writeContents(getRows() + 1, col, content);
	}

	/**
	 * 写Sheet，新行第1列内容
	 * 
	 * @param content 单元格内容
	 */
	public final void writeContentsByNewRow(String content) {
		writeContents(getRows() + 1, 0, content);
	}

	/**
	 * 写Sheet，N行新列内容
	 * 
	 * @param row     第几行
	 * @param content 单元格内容
	 */
	public final void writeContentsByNewCol(int row, String content) {
		writeContents(row, getColumns() + 1, content);
	}

	/**
	 * 写Sheet，1行新列内容
	 * 
	 * @param content 单元格内容
	 */
	public final void writeContentsByNewCol(String content) {
		writeContents(0, getColumns() + 1, content);
	}

	/**
	 * 获得指定Sheet，指定行列内容
	 * 
	 * @param index   Sheet码 注 添加了这个参数 就直接设置了Sheet 以后调用 getContents(int row,int col)就可获得这页内容
	 * @param row     行码
	 * @param col     列码
	 * @param content 内容
	 */
	public final void writeContents(int index, int row, int col, String content) {
		// 设置Sheet页码
		setIndex(index);
		// 调用自己方法
		writeContents(row, col, content);
	}

	/**
	 * 获得Sheet所有列名
	 * 
	 * @return 数组保存列名
	 */
	public final String[] getColumnNames() {
		return getColumnNames(index);
	}

	/**
	 * 获得指定Sheet所有列名
	 * 
	 * @param name Sheet名
	 * @return 数组保存列名
	 */
	public final String[] getColumnNames(String name) {
		// 获得Sheet位置
		int index = getSheetIndex(name);
		// 判断是否存在
		if (index == -1) {
			return null;
		}
		// 返回Sheet内容
		return getColumnNames(index);
	}

	/**
	 * 获得指定Sheet所有列名
	 * 
	 * @param index Sheet索引
	 * @return 数组保存列名
	 */
	public final String[] getColumnNames(int index) {
		// 设置Sheet页码
		setIndex(index);
		// 获得列数
		int cols = getColumns();
		// 实例化数组
		String[] colNames = new String[cols];
		// 循环获得列名
		for (int i = 0; i < cols; i++) {
			// 获得列名
			colNames[i] = readContents(0, i);
		}
		// 返回列名数组
		return colNames;
	}

	/**
	 * 根据列名返回列索引
	 * 
	 * @param colName 列名
	 * @return 列索引 -1 为没有这列
	 */
	public final int getColumnIndexByName(String colName) {
		// 列索引
		int ci = -1;
		// 所有列名
		String[] colNames = getColumnNames();
		// 循环列名
		for (int i = 0; i < colNames.length; i++) {
			// 列名相同
			if (colNames[i].equals(colName)) {
				// 获得索引
				ci = i;
				// 跳出循环
				break;
			}
		}
		// 返回列索引
		return ci;
	}

	/**
	 * 返回正在使用的工作薄索引
	 * 
	 * @return int
	 */
	public final int getIndex() {
		return index;
	}

	/**
	 * 设置工作薄索引
	 * 
	 * @param index 工作薄索引
	 */
	public final void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 获取Sheet的名称
	 * 
	 * @return Sheet的名称
	 */
	public final String getSheetName() {
		return getSheetName(index);
	}

	/**
	 * 获取Sheet表中所包含的总列数
	 * 
	 * @return Sheet的总列数
	 */
	public final int getColumns() {
		return getColumns(index);
	}

	/**
	 * 获取Sheet表中所包含的总行数
	 * 
	 * @return Sheet的总行数
	 */
	public final int getRows() {
		return getRows(index);
	}

	/**
	 * 写入数据到工作薄中
	 * 
	 * @param list      List要写入的内容
	 * @param sheetName 名
	 */
	public final void writeSheet(List<List<String>> list, String sheetName) {
		writeSheet(list, getSheetIndex(sheetName));
	}

	/**
	 * 写入数据到工作薄中
	 * 
	 * @param list      List
	 * @param sheetName 名
	 */
	public final void writeSheetByCol(List<Map<String, String>> list, String sheetName) {
		writeSheetByCol(list, getSheetIndex(sheetName));
	}

	/**
	 * 写入数据到工作薄中
	 * 
	 * @param list  List
	 * @param index 页码
	 */
	public final void writeSheetByCol(List<Map<String, String>> list, int index) {
		// 设置Sheet索引
		setIndex(index);
		// 声明map 用于保存行数据
		Map<String, String> map = null;
		// 循环行
		for (int i = 0; i < list.size(); i++) {
			// 获得行数据
			map = list.get(i);
			// 循环列
			for (Map.Entry<String, String> e : map.entrySet()) {
				// 添加数据
				writeContents(i, getColumnIndexByName(e.getKey()), e.getValue());
			}
		}
	}
}