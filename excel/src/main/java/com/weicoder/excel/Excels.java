package com.weicoder.excel;

import java.io.File;
import java.util.List;

import com.weicoder.common.io.I;
import com.weicoder.excel.impl.ExcelPOI; 

/**
 * 生成 Excel 接口工厂
 * 
 * @author WD
 */
public final class Excels {
	/**
	 * 创建Excel对象
	 * 
	 * @param fileName 文件路径
	 * @return Excel对象
	 */
	public static Excel getExcel(String fileName) {
//		try {
		return getExcel(I.F.newFile(fileName));
//		} catch (Exception e) {
//			return getExcel(I.F.newFile(fileName));
//		}
	}

	/**
	 * 把数据写Excel最后一列 所有写方法都是写到缓存中
	 * 
	 * @param name 文件名
	 * @param list 数据列表
	 */
	public static void write(String name, List<?> list) {
		Excel excel = getExcel(name);
		excel.write(list);
		excel.write();
	}

	/**
	 * 创建Excel对象
	 * 
	 * @param file 文件
	 * @return Excel对象
	 */
	public static Excel getExcel(File file) {
		return new ExcelPOI(file);
	}

	private Excels() {
	}
}
