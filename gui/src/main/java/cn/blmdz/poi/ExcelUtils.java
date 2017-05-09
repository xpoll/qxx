/*
 * 版权所有(C) 纳客宝 2014-2030
 * Copyright 2014-2030 Maxxipoint CO.,LTD.
 */
package cn.blmdz.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.base.Strings;

/**
 * 这里写描述
 * 
 * @ClassName: ExcelUtils
 * @version V1.0 @date 2016年2月2日 下午1:56:14
 * @author 刘纪成 jichengliu@maxxipoint.com
 */
public class ExcelUtils {
	/**
	 * 创建excel文档，
	 * 
	 * @param list
	 *            数据
	 * @param keys
	 *            list中map的key数组集合
	 * @param columnNames
	 *            excel的列名
	 * */
	public static Workbook createWorkBook(List<Map<String, Object>> list,
			String[] keys, String columnNames[]) {
		// 创建excel工作簿
		Workbook wb = new HSSFWorkbook();
		// 创建第一个sheet（页），并命名
		Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
		// 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
		for (int i = 0; i < keys.length; i++) {
			if(keys[i].equals("orderNo")||keys[i].equals("payNo")||keys[i].equals("refundTimeStr")||keys[i].equals("courierNo")){
				sheet.setColumnWidth((short) i, (short) (35.7 * 220));
			}else if(keys[i].equals("productName")||keys[i].equals("address")||keys[i].equals("openId")||keys[i].equals("courier")){
				sheet.setColumnWidth((short) i, (short) (35.7 * 280));
			}else if(keys[i].equals("applyRefundStr")||keys[i].equals("createTimeStr")||keys[i].equals("mobile")||keys[i].equals("conCard")||keys[i].equals("productSize")){
				sheet.setColumnWidth((short) i, (short) (35.7 * 150));
			}else{
				sheet.autoSizeColumn(i);
			}
		}

		// 创建第一行
		Row row = sheet.createRow((short) 0);
		row.setHeight((short) 400);
		// 创建两种单元格格式
		CellStyle  cs = wb.createCellStyle();
		CellStyle cs2 = wb.createCellStyle();

		// 创建两种字体
		Font f = wb.createFont();
		Font f2 = wb.createFont();

		// 创建第一种字体样式（用于列名）
		f.setFontHeightInPoints((short) 11);
		f.setColor(IndexedColors.WHITE.getIndex());
		f.setBoldweight(Font.BOLDWEIGHT_BOLD);
		f.setFontName("宋体");

		// 创建第二种字体样式（用于值）
		f2.setFontHeightInPoints((short) 10);
		f2.setColor(IndexedColors.BLACK.getIndex());

		// Font f3=wb.createFont();
		// f3.setFontHeightInPoints((short) 10);
		// f3.setColor(IndexedColors.RED.getIndex());

		// 设置第一种单元格的样式（用于列名）
		cs.setFont(f);
		cs.setBorderLeft(CellStyle.BORDER_THIN);
		cs.setBorderRight(CellStyle.BORDER_THIN);
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_THIN);
		cs.setAlignment(CellStyle.ALIGN_CENTER);
//		cs.setFillBackgroundColor(HSSFColor.ROYAL_BLUE.index);
		cs.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);// 设置背景色
		cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		// 设置第二种单元格的样式（用于值）
		cs2.setFont(f2);
		cs2.setBorderLeft(CellStyle.BORDER_THIN);
		cs2.setBorderRight(CellStyle.BORDER_THIN);
		cs2.setBorderTop(CellStyle.BORDER_THIN);
		cs2.setBorderBottom(CellStyle.BORDER_THIN);
		cs2.setAlignment(CellStyle.ALIGN_CENTER);
		
		// 设置列名
		for (int i = 0; i < columnNames.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(columnNames[i]);
			cell.setCellStyle(cs);
		}
		// 设置每行每列的值
		for (short i = 1; i < list.size(); i++) {
			// Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
			// 创建一行，在页sheet上
			Row row1 = sheet.createRow((short) i);
			row1.setHeight((short) 300);
			// 在row行上创建一个方格
			for (short j = 0; j < keys.length; j++) {
				Cell cell = row1.createCell(j);
				cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list
						.get(i).get(keys[j]).toString());
				cell.setCellStyle(cs2);
			}
		}
		return wb;
	}

	/** 总行数 */
	private int totalRows = 0;
	/** 总列数 */
	private int totalCells = 0;
	/** 错误信息 */
	private String errorInfo;
	/** 构造方法 */
	public ExcelUtils() {
	}

	public int getTotalRows() {
		return totalRows;
	}
	public int getTotalCells() {
		return totalCells;
	}
	public String getErrorInfo() {
		return errorInfo;
	}

	public boolean validateExcel(String filePath) {
		/** 检查文件名是否为空或者是否是Excel格式的文件 */
		if (filePath == null
				|| !(WDWUtil.isExcel2003(filePath) || WDWUtil
						.isExcel2007(filePath))) {

			errorInfo = "文件名不是excel格式";
			return false;
		}
		/** 检查文件是否存在 */
		File file = new File(filePath);
		if (file == null || !file.exists()) {
			errorInfo = "文件不存在";
			return false;
		}
		return true;
	}

	public List<List<String>> read(String filePath,InputStream is,int start) {
		List<List<String>> dataLst = new ArrayList<List<String>>();
		try {
			/** 验证文件是否合法 *//*
			if (!validateExcel(filePath)) {
				System.out.println(errorInfo);
				return null;
			}*/
			/** 判断文件的类型，是2003还是2007 */
			boolean isExcel2003 = true;
			if (WDWUtil.isExcel2007(filePath)) {
				isExcel2003 = false;
			}
			/** 调用本类提供的根据流读取的方法 */
			dataLst = read(is, isExcel2003,start);
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					is = null;
					e.printStackTrace();
				}
			}
		}
		/** 返回最后读取的结果 */
		return dataLst;
	}

	public List<List<String>> read(InputStream inputStream, boolean isExcel2003,int start) {
		List<List<String>> dataLst = null;
		try {
			/** 根据版本选择创建Workbook的方式 */
			Workbook wb = null;
			if (isExcel2003) {
				wb = new HSSFWorkbook(inputStream);
			} else {
				wb = new XSSFWorkbook(inputStream);
			}
			dataLst = read(wb,start);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataLst;
	}

	private List<List<String>> read(Workbook wb,int start) {
		List<List<String>> dataLst = new ArrayList<List<String>>();
		/** 得到第一个shell */
		Sheet sheet = wb.getSheetAt(0);
		System.out.println(sheet.getSheetName());
		/** 得到Excel的行数 */
		this.totalRows = sheet.getPhysicalNumberOfRows();
		/** 得到Excel的列数 */
		if (this.totalRows >= 1 && sheet.getRow(0) != null) {
			this.totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}
		/** 循环Excel的行  从第二行开始*/
		for (int r = start; r < this.totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			List<String> rowLst = new ArrayList<String>();
			/** 循环Excel的列 */
			for (int c = 0; c < this.getTotalCells(); c++) {
				Cell cell = row.getCell(c);
				String cellValue = "";
				if (null != cell) {
					// 以下是判断数据的类型
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;
						
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						cellValue = cell.getNumericCellValue() + "";
						break;

					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = "";
						break;

					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue() + "";
						break;

					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						cellValue = cell.getCellFormula() + "";
						break;


					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;

					default:
						cellValue = "未知类型";
						break;
					}
				}
				if(Strings.isNullOrEmpty(cellValue)){
					rowLst.add(cellValue);
				}
			}
			/** 保存第r行的第c列 */
			if(rowLst!=null&&rowLst.size()>0){
				dataLst.add(rowLst);
			}
		}
		return dataLst;
	}

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("c:/b.xlsx");
		ExcelUtils poi = new ExcelUtils();
		// List<List<String>> list = poi.read("d:/aaa.xls");
		InputStream input = new FileInputStream(file);
		System.out.println(file.exists());
		List<List<String>> list = poi.read("b.xlsx", input, 1);
		System.out.println(list.size());
	}
	
/*	public static void main(String[] args) throws Exception {
		ExcelUtils poi = new ExcelUtils();
		// List<List<String>> list = poi.read("d:/aaa.xls");
		List<List<String>> list = poi.read("c:/book.xlsx");
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				System.out.print("第" + (i) + "行");
				List<String> cellList = list.get(i);
				for (int j = 0; j < cellList.size(); j++) {
					// System.out.print("    第" + (j + 1) + "列值：");
					System.out.print("    " + cellList.get(j));
				}
				System.out.println();
			}
		}
	}*/
}


class WDWUtil {

	public static boolean isExcel2003(String filePath) {
		return filePath.matches("^.+\\.(?i)(xls)$");
	}

	public static boolean isExcel2007(String filePath) {
		return filePath.matches("^.+\\.(?i)(xlsx)$");
	}
}
