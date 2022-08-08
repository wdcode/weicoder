package com.weicoder.test;

import org.junit.Test;

import com.weicoder.excel.Excel;
import com.weicoder.excel.Excels;

public class ExcelTest {
	@Test
	public void main() {
		String f = "robot.xlsx";
		String n = "1.xlsx";
		Excel e = Excels.getExcel(f);
		Excel x = Excels.getExcel(n);
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
