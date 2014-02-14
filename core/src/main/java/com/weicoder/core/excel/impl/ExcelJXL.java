package com.weicoder.core.excel.impl;

import java.io.File;

import com.weicoder.common.constants.StringConstants;

import com.weicoder.core.log.Logs;
import com.weicoder.common.util.EmptyUtil;
import com.weicoder.common.util.DateUtil;
import com.weicoder.core.excel.base.BaseExcel;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.EmptyCell;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
 * 使用JXL操作Excel<br/>
 * <h2>注: 内部使用</h2>
 * @author WD
 * @since JDK7
 * @version 1.0 2009-03-09
 */
public final class ExcelJXL extends BaseExcel {
	// 工作薄 读
	private Workbook			workbook;
	// 工作薄 写
	private WritableWorkbook	writableWorkbook;

	/**
	 * 构造方法
	 * @param file excel文件
	 */
	public ExcelJXL(File file) {
		try {
			// 设置索引
			setIndex(0);
			// 判断文件是否存在
			if (EmptyUtil.isEmpty(file)) {
				// 设置WritableWorkbook对象
				writableWorkbook = Workbook.createWorkbook(file);
			} else {
				// 流获得工作薄
				workbook = Workbook.getWorkbook(file);
				// 设置WritableWorkbook对象
				writableWorkbook = Workbook.createWorkbook(file, workbook);
			}
		} catch (Exception e) {
			// 记录日志
			Logs.error(e);
		}
	}

	/**
	 * 获得Sheet，指定行列内容
	 * @param row 行码
	 * @param col 列码
	 * @return 单元格内容
	 */
	public String readContents(int row, int col) {
		// 声明 content
		String content = StringConstants.EMPTY;
		// 获得Sheet
		Sheet sheet = workbook.getSheet(getIndex());
		// 获得Cell
		Cell cell = sheet.getCell(col, row);
		// 添加内容到列 列表
		if (!EmptyUtil.isEmpty(cell)) {
			content = cell.getContents();
		}
		// 返回单元格内容
		return content;
	}

	/**
	 * 获得工作薄（Workbook）中工作表（Sheet）的个数
	 * @return Sheet 的个数
	 */
	public int getSheets() {
		return workbook.getNumberOfSheets();
	}

	/**
	 * 获得指定Sheet的名称
	 * @param num 指定Sheet
	 * @return Sheet的名称
	 */
	public String getSheetName(int num) {
		return workbook.getSheet(num).getName();
	}

	/**
	 * 获取指定Sheet表中所包含的总列数
	 * @param index 指定Sheet
	 * @return Sheet的总列数
	 */
	public int getColumns(int index) {
		return workbook.getSheet(index).getColumns();
	}

	/**
	 * 获取指定Sheet表中所包含的总行数
	 * @param num 指定Sheet
	 * @return Sheet的总行数
	 */
	public int getRows(int num) {
		return workbook.getSheet(num).getRows();
	}

	/**
	 * 关闭Excel
	 */
	public void close() {
		// 关闭读
		// 判断读对象不为空
		if (workbook != null) {
			// 关闭
			try {
				// 关闭
				workbook.close();
			} catch (Exception e) {
				Logs.error(e);
			} finally {
				// 置null
				workbook = null;
			}
		}
		// 关闭写
		// 判断不为空
		if (writableWorkbook != null) {
			// 关闭
			try {
				writableWorkbook.close();
			} catch (Exception e) {
				Logs.error(e);
			} finally {
				// 置null
				writableWorkbook = null;
			}
		}
	}

	/**
	 * 创建工作薄
	 * @param name 工作薄名
	 * @param index 页码
	 */
	public void createSheet(String name, int index) {
		// 创建工作薄
		writableWorkbook.createSheet(name, index);
		// 设置索引
		setIndex(index);
	}

	/**
	 * 创建工作薄
	 * @param name 工作薄名
	 */
	public void createSheet(String name) {
		// 创建工作薄
		createSheet(name, writableWorkbook.getNumberOfSheets());
	}

	/**
	 * 写Excel
	 */
	public void write() {
		try {
			writableWorkbook.write();
		} catch (Exception e) {
			Logs.error(e);
		}
	}

	/**
	 * 根据Sheet名获得位置
	 * @param name Sheet名
	 * @return 位置
	 */
	public int getSheetIndex(String name) {
		// 声明索引
		int index = -1;
		// 获得所有Sheet
		Sheet[] sheets = workbook.getSheets();
		// 根据名获得索引
		for (int i = 0; i < sheets.length; i++) {
			// 名字相同
			if (sheets[i].equals(name)) {
				// 设置索引
				index = i;
			}
		}
		// 返回索引
		return index;
	}

	/**
	 * 写到指定的单元格
	 * @param row 行
	 * @param col 列
	 * @param content 内容
	 */
	public void writeContents(int row, int col, String content) {
		// 获得WritableSheet
		WritableSheet sheet = null;
		try {
			// 如果Sheet存在获得Sheet
			sheet = writableWorkbook.getSheet(getIndex());
		} catch (Exception e) {
			// 不存在创建Sheet
			sheet = writableWorkbook.createSheet("Sheet", getIndex());
		}
		try {
			// 获得WritableCell
			WritableCell cell = sheet.getWritableCell(col, row);
			if (cell instanceof EmptyCell) {
				// 不存在创建Label
				Label label = new Label(col, row, content);
				// 添加值
				label.setString(content);
				// 添加Cell
				sheet.addCell(label);
			} else {
				// 判断单元格类型
				if (cell.getType() == CellType.NUMBER) {
					// 转换为Number
					Number number = (Number) cell;
					// 更新值
					number.setValue(Double.parseDouble(content));
				} else if (cell.getType() == CellType.DATE) {
					// 转换为Label
					DateTime dateTime = (DateTime) cell;
					// 更新值
					dateTime.setDate(DateUtil.toDate(content));
				} else {
					// 转换为Label
					Label label = (Label) cell;
					// 更新值
					label.setString(content);
				}
			}
		} catch (Exception e) {
			Logs.error(e);
		}
	}
}
