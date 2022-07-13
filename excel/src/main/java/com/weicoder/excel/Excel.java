package com.weicoder.excel;

import java.util.List;
import java.util.Map;

/**
 * Excel 相关操作接口 使用ExcelFactory获得实例
 * 
 * @author WD
 */
public interface Excel {
	/**
	 * 获得第一张Sheet表内容
	 * 
	 * @return 返回List
	 */
	List<List<String>> readSheet();

	/**
	 * 获得所有Sheet表内容
	 * 
	 * @return 返回List
	 */
	List<List<String>> readSheetByAll();

	/**
	 * 获得指定页码Sheet表内容
	 * 
	 * @param index 页码
	 * @return 返回List
	 */
	List<List<String>> readSheet(int index);

	/**
	 * 获得指定名称Sheet表内容
	 * 
	 * @param sheetName Sheet名
	 * @return 返回List
	 */
	List<List<String>> readSheet(String sheetName);

	/**
	 * 获得指定名称Sheet表内容
	 * 
	 * @param sheetName Sheet名
	 * @return 返回List
	 */
	List<Map<String, String>> readSheetByCol(String sheetName);

	/**
	 * 获得第一张Sheet表内容
	 * 
	 * @return 返回List
	 */
	List<Map<String, String>> readSheetByCol();

	/**
	 * 读取默认sheet下的指定列
	 * 
	 * @param col 第几类 从0开始
	 * @return 列值集合表
	 */
	List<String> readCol(int col);

	/**
	 * 获得第一张Sheet表内容
	 * 
	 * @return 返回List
	 */
	List<Map<String, String>> readSheetByColByAll();

	/**
	 * 获得指定页码Sheet表内容
	 * 
	 * @param index 页码
	 * @return 返回List
	 */
	List<Map<String, String>> readSheetByCol(int index);

	/**
	 * 获得Sheet，第一行第一列内容
	 * 
	 * @return 单元格内容
	 */
	String readContents();

	/**
	 * 获得指定Sheet，指定行列内容
	 * 
	 * @param index Sheet码 注 添加了这个参数 就直接设置了Sheet 以后调用 getContents(int row,int col)就可获得这页内容
	 * @param row   行码
	 * @param col   列码
	 * @return 单元格内容
	 */
	String readContents(int index, int row, int col);

	/**
	 * 写入第一张Sheet表内容
	 * 
	 * @param list 列表
	 */
	void writeSheet(List<List<String>> list);

	/**
	 * 写入数据到工作薄中
	 * 
	 * @param list  List要写入的内容
	 * @param index 页码
	 */
	void writeSheet(List<List<String>> list, int index);

	/**
	 * 获得第一张Sheet表内容
	 * 
	 * @param list 列表
	 */
	void writeSheetByCol(List<Map<String, String>> list);

	/**
	 * 写Sheet，第一行第一列内容
	 * 
	 * @param content 单元格内容
	 */
	void writeContents(String content);

	/**
	 * 写Sheet，新行第N列内容
	 * 
	 * @param col     第几列
	 * @param content 单元格内容
	 */
	void writeContentsByNewRow(int col, String content);

	/**
	 * 写Sheet，新行第1列内容
	 * 
	 * @param content 单元格内容
	 */
	void writeContentsByNewRow(String content);

	/**
	 * 写Sheet，N行新列内容
	 * 
	 * @param row     第几行
	 * @param content 单元格内容
	 */
	void writeContentsByNewCol(int row, String content);

	/**
	 * 写Sheet，1行新列内容
	 * 
	 * @param content 单元格内容
	 */
	void writeContentsByNewCol(String content);

	/**
	 * 获得指定Sheet，指定行列内容
	 * 
	 * @param index   Sheet码 注 添加了这个参数 就直接设置了Sheet 以后调用 getContents(int row,int col)就可获得这页内容
	 * @param row     行码
	 * @param col     列码
	 * @param content 内容
	 */
	void writeContents(int index, int row, int col, String content);

	/**
	 * 获得Sheet所有列名
	 * 
	 * @return 数组保存列名
	 */
	String[] getColumnNames();

	/**
	 * 获得指定Sheet所有列名
	 * 
	 * @param name Sheet名
	 * @return 数组保存列名
	 */
	String[] getColumnNames(String name);

	/**
	 * 获得指定Sheet所有列名
	 * 
	 * @param index Sheet索引
	 * @return 数组保存列名
	 */
	String[] getColumnNames(int index);

	/**
	 * 根据列名返回列索引
	 * 
	 * @param colName 列名
	 * @return 列索引 -1 为没有这列
	 */
	int getColumnIndexByName(String colName);

	/**
	 * 返回正在使用的工作薄索引
	 * 
	 * @return int
	 */
	int getIndex();

	/**
	 * 设置工作薄索引
	 * 
	 * @param index 工作薄索引
	 */
	void setIndex(int index);

	/**
	 * 获取Sheet的名称
	 * 
	 * @return Sheet的名称
	 */
	String getSheetName();

	/**
	 * 获取Sheet表中所包含的总列数
	 * 
	 * @return Sheet的总列数
	 */
	int getColumns();

	/**
	 * 获取Sheet表中所包含的总行数
	 * 
	 * @return Sheet的总行数
	 */
	int getRows();

	/**
	 * 写入数据到工作薄中
	 * 
	 * @param list      List要写入的内容
	 * @param sheetName 名
	 */
	void writeSheet(List<List<String>> list, String sheetName);

	/**
	 * 写入数据到工作薄中
	 * 
	 * @param list      List
	 * @param sheetName 名
	 */
	void writeSheetByCol(List<Map<String, String>> list, String sheetName);

	/**
	 * 写入数据到工作薄中
	 * 
	 * @param list  List
	 * @param index 页码
	 */
	void writeSheetByCol(List<Map<String, String>> list, int index);

	/**
	 * 获取指定Sheet表中所包含的总列数
	 * 
	 * @param index 指定Sheet
	 * @return Sheet的总列数
	 */
	int getColumns(int index);

	/**
	 * 获得工作薄（Workbook）中工作表（Sheet）的个数
	 * 
	 * @return Sheet 的个数
	 */
	int getSheets();

	/**
	 * 获取指定Sheet表中所包含的总行数
	 * 
	 * @param num 指定Sheet
	 * @return Sheet的总行数
	 */
	int getRows(int num);

	/**
	 * 根据Sheet名获得位置 如果不存在返回-1
	 * 
	 * @param name Sheet名
	 * @return 位置
	 */
	int getSheetIndex(String name);

	/**
	 * 获得指定Sheet的名称
	 * 
	 * @param num 指定Sheet
	 * @return Sheet的名称
	 */
	String getSheetName(int num);

	/**
	 * 创建工作薄
	 * 
	 * @param name  工作薄名
	 * @param index 页码
	 */
	void createSheet(String name, int index);

	/**
	 * 创建工作薄
	 * 
	 * @param name 工作薄名
	 */
	void createSheet(String name);

	/**
	 * 获得Sheet，指定行列内容
	 * 
	 * @param row 行码
	 * @param col 列码
	 * @return 单元格内容
	 */
	String readContents(int row, int col);

	/**
	 * 读取本sheet下所有行的数据 把指定列转成对象
	 * 
	 * @param <E>
	 * @param cls 数据实体的类
	 * @return 本sheet下所有行数据
	 */
	<E> List<E> reads(Class<E> cls);

	/**
	 * 读取指定行的数据为指定对象
	 * 
	 * @param <E>
	 * @param row 指定行
	 * @param cls 数据实体的类
	 * @return 返回指定类型的实体
	 */
	<E> E read(int row, Class<E> cls);

	/**
	 * 写到指定的单元格
	 * 
	 * @param row     行
	 * @param col     列
	 * @param content 内容
	 */
	void writeContents(int row, int col, String content);

	/**
	 * 把数据写Excel最后一列 所有写方法都是写到缓存中
	 */
	<E> void write(E e);

	/**
	 * 把数据写Excel最后一列 所有写方法都是写到缓存中
	 */
	<E> void write(List<E> list);

	/**
	 * 写Excel 所有写方法都是写到缓存中 只有执行了此方法 写入操作才会成功
	 */
	void write();

	/**
	 * 关闭资源
	 */
	void close();
}
