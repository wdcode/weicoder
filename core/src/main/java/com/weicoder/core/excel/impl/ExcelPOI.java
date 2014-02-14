package com.weicoder.core.excel.impl;

import java.io.File;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.weicoder.common.io.FileUtil;
import com.weicoder.common.lang.Conversion;
import com.weicoder.core.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.core.excel.base.BaseExcel;

/**
 * 使用POI操作Excel<br/>
 * <h2>注: 内部使用</h2>
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-09
 */
public final class ExcelPOI extends BaseExcel {
	// 工作薄 读
	private Workbook	workbook;
	// 写入文件流
	private File		file;

	/**
	 * 构造方法
	 * @param file 文件
	 */
	public ExcelPOI(File file) {
		// 设置为第一个工作薄
		setIndex(0);
		try {
			// 如果文件存在
			workbook = EmptyUtil.isEmpty(file) ? new HSSFWorkbook() : WorkbookFactory.create(file);
			// 获得输出流
			this.file = file;
		} catch (Exception e) {}
	}

	/**
	 * 关闭Excel
	 */
	public void close() {
		workbook = null;
		file = null;
	}

	/**
	 * 创建工作薄
	 * @param name 工作薄名
	 * @param index 页码
	 */
	public void createSheet(String name, int index) {
		// 创建工作薄
		workbook.createSheet(name);
		// 设置索引
		setIndex(index);
	}

	/**
	 * 创建工作薄
	 * @param name 工作薄名
	 */
	public void createSheet(String name) {
		// 创建工作薄
		workbook.createSheet(name);
		// 设置索引
		setIndex(workbook.getSheetIndex(name));
	}

	/**
	 * 获取指定Sheet表中所包含的总列数
	 * @param index 指定Sheet
	 * @return Sheet的总列数
	 */
	public int getColumns(int index) {
		// 获得行
		Row row = workbook.getSheetAt(index).getRow(0);
		// 行为空返回0,不为空反回列
		return row == null ? 0 : row.getPhysicalNumberOfCells();
	}

	/**
	 * 获得Sheet，指定行列内容
	 * @param row 行码
	 * @param col 列码
	 * @return 单元格内容
	 */
	public String readContents(int row, int col) {
		// 获得Sheet
		Sheet sheet = workbook.getSheetAt(getIndex());
		// 获得Row
		Row hRow = sheet.getRow(row);
		// 获得Cell
		Cell cell = hRow == null ? null : hRow.getCell(col);
		// 返回单元格内容
		return Conversion.toString(cell);
	}

	/**
	 * 获得工作薄（Workbook）中工作表（Sheet）的个数
	 * @return Sheet 的个数
	 */
	public int getSheets() {
		return workbook.getNumberOfSheets();
	}

	/**
	 * 获取指定Sheet表中所包含的总行数
	 * @param num 指定Sheet
	 * @return Sheet的总行数
	 */
	public int getRows(int num) {
		return workbook.getSheetAt(getIndex()).getPhysicalNumberOfRows();
	}

	/**
	 * 根据Sheet名获得位置
	 * @param name Sheet名
	 * @return 位置
	 */
	public int getSheetIndex(String name) {
		return workbook.getSheetIndex(name);
	}

	/**
	 * 获得指定Sheet的名称
	 * @param num 指定Sheet
	 * @return Sheet的名称
	 */
	public String getSheetName(int num) {
		return workbook.getSheetName(num);
	}

	/**
	 * 写Excel
	 */
	public void write() {
		try {
			// 写Excel
			workbook.write(FileUtil.getOutputStream(file));
		} catch (IOException e) {
			Logs.warn(e);
		}
	}

	/**
	 * 写到指定的单元格
	 * @param row 行
	 * @param col 列
	 * @param content 内容
	 */
	public void writeContents(int row, int col, String content) {
		// 获得Sheet
		Sheet sheet = null;
		try {
			// 如果Sheet存在获得Sheet
			sheet = workbook.getSheetAt(getIndex());
		} catch (Exception e) {
			// 不存在创建Sheet
			sheet = workbook.createSheet();
		}
		// 获得Row
		Row hRow = sheet.getRow(row);
		if (hRow == null) {
			// 不存在创建Sheet
			hRow = sheet.createRow(row);
		}
		// 获得Cell
		Cell cell = hRow.getCell(col);
		if (cell == null) {
			// 不存在创建Sheet
			cell = hRow.createCell(col);
		}
		// 添加值
		cell.setCellValue(content);
	}
}
