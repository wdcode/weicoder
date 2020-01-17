package com.weicoder.test;

import com.weicoder.excel.Excel;
import com.weicoder.excel.builder.ExcelBuilder;

public class ExcelTest {

	public static void main(String[] args) {
		String f = "E:\\robot.xlsx";
		String n = "E:\\1.xlsx";
		Excel e = ExcelBuilder.getExcel(f);
		Excel x = ExcelBuilder.getExcel(n);
		System.out.println("c="+e.getColumns()+";r="+e.getRows());
		System.out.println(e.readContents(0,0));
		for(int i=0;i<e.getRows();i++) { 
			x.writeContents(i,0, e.readContents(i, 0));
			x.writeContents(i,1, e.readContents(i, 1));
		}
		x.write();
		e.close();
		x.close();
	} 
}
