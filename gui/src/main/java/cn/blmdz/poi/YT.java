package cn.blmdz.poi;

import java.io.File;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class YT {

	public static void main(String[] args) throws Exception {
		
		
		File file = new File("c:/a.xlsx");
		Workbook work = WorkbookFactory.create(file);
		Sheet sheet = work.getSheetAt(0);
		System.out.println(sheet.getSheetName());
		
	}
}
