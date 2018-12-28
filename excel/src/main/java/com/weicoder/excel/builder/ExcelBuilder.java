package com.weicoder.excel.builder;

import java.io.File;

import com.weicoder.common.io.FileUtil;
import com.weicoder.excel.Excel;
import com.weicoder.excel.impl.ExcelPOI;

/**
 * 生成 Excel 接口工厂
 * @author WD
 * @version 1.0
 */
public final class ExcelBuilder {
	/**
	 * 创建Excel对象
	 * @param fileName 文件路径
	 * @return Excel对象
	 */
	public static Excel getExcel(String fileName) {
		return getExcel(FileUtil.newFile(fileName));
	}

	/**
	 * 创建Excel对象
	 * @param file 文件
	 * @return Excel对象
	 */
	public static Excel getExcel(File file) {
		return new ExcelPOI(file);
	}

	private ExcelBuilder() {}
}
