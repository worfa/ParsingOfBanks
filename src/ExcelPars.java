import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;


import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelPars {
		
	private static String nameFileEx;
	
	
	//создание файла эксель, либо проверка на уже созданность, это сделано, чтобы
	//можно было пользоваться программой без предварительного ручного 
	//создания файла.
	public static boolean CreateFileEx(String nameFile) throws IOException
	{
		nameFileEx = nameFile;
		boolean okCreate = false;
		
		File file = new File(nameFile + ".xls");
		if(file.exists()) {
			System.out.println(nameFile + "уже создан  " + file.getCanonicalPath() + "\n" + file.canWrite() );
			okCreate = false;
		}else {
			file.createNewFile();
			System.out.println(nameFile + " создан в корневой дериктории " + file.canWrite());
			
			okCreate = true;
		}
	
		return okCreate;
	}
	
	//задание первой строки с названиями колонок.
	
	public static void WriteNameColumnExcel(String nameFile) throws Exception
	{
		Date date = new Date();
		date.getTime();
		
		HSSFWorkbook readBook = new HSSFWorkbook(new FileInputStream(nameFile + ".xls"));
		Workbook book = new HSSFWorkbook();
		
		Sheet sheet;
		if (readBook.getSheetAt(0) == null) {
			System.out.println("Произошло пересоздание ");
		
		sheet =	book.createSheet("Currensies Dollar and Evr");
		
		//Create row name 0 index
		Row row = sheet.createRow(0);
		
		Cell nameRub = row.createCell(0);
		nameRub.setCellValue("Dollar");
		
		Cell nameDollar = row.createCell(1);
		nameDollar.setCellValue("Evr");
		
		Cell nameDate = row.createCell(2);
		nameDate.setCellValue("Date");
			
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		
		book.write(new FileOutputStream(nameFile + ".xls",true));
		book.close();
		}
		
	}
	
	public static void WriteCurrencies(String nameFile, String DollarValue, String EvrValue) throws Exception 
	{ 
		Date dateMyPC = new Date();
		dateMyPC.getTime();
		
		HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(nameFile + ".xls"));
		Sheet sheet = book.getSheetAt(0);
		//получение последней строки для того, чтобы в таблице создавались следующие
		//строки.
		System.out.println("Programming method calculate" + sheet.getLastRowNum());
		int lastRow = getLastRowMyTable(sheet);
		System.out.print("должен быть номер последней строки " + lastRow);
		
		Row row = sheet.createRow(lastRow);
				
		Cell dollar = row.createCell(0);
		dollar.setCellValue(DollarValue);
		
		Cell rub = row.createCell(1);
		rub.setCellValue(EvrValue);
		
		Cell myDate = row.createCell(2);
		myDate.setCellValue(dateMyPC);
		
		//задание определенного формата ячейке даты, для корректного отображения
		DataFormat format = book.createDataFormat();
		CellStyle dateStyle = book.createCellStyle();
		dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
		myDate.setCellStyle(dateStyle);
		
		//задание автоматического размера колонок, для корректного отображения 
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(2);
		
		book.write(new FileOutputStream(nameFile + ".xls"));
		book.close();
	}
	
	//Метод для получения последней строки в таблице,
	//это было сделано, так как встроенные методы не считали
	//строки в таблице.
	public static int getLastRowMyTable(Sheet sheet) {
		int numLast = 0;
		Row row = sheet.getRow(numLast);
		Cell c = null;
		try {
			c = row.getCell(0);
		}catch(Exception e) {
			return numLast;
		}
		
	//счет строк, с постоянным чтением ключевых ячейки в каждой строке
	//либо до пустой строки, либо до пустой ключевой ячейки.
 		while(c != null) {
			numLast++;
			row = sheet.getRow(numLast);
			if(row != null) {
				c = row.getCell(0);
			} else {
				break;
			}
		}
		return numLast;
	}
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
	 * Проверка на обновление курса валюты, для последующей отсылки сообщений
	 * на почту. Делается это для того чтобы не произошло переполнения почты
	 * и уменьшения расхода сообщений, а также чтобы не происходил спам одинаковой
	 * информацией
	::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	
	public static boolean checkRefreshCurrensy(String nameFile) throws IOException {
		boolean checkRefhCurs = false;
		
		
		HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(nameFile + ".xls"));
		Sheet sheet = book.getSheetAt(0);
		
		int lastNumRow = sheet.getLastRowNum() - 1;
		int secLastNumRow = sheet.getLastRowNum();
		
		Row rowLast = sheet.getRow(lastNumRow);
		Row rowSecLastNum = sheet.getRow(secLastNumRow);
		
		Cell dollarRowLast = rowLast.getCell(0);
		Cell euroRowLast = rowLast.getCell(1);
		
		String dollarRowLastStr = dollarRowLast.getStringCellValue();
		String euroRowLastStr = euroRowLast.getStringCellValue();
		
		System.out.println("\n" + dollarRowLastStr + "  " + euroRowLast);
		
		Cell dollarSecRowLast = rowSecLastNum.getCell(0);
		Cell euroSecRowLast = rowSecLastNum.getCell(1);
		
		String dollarSecRowLastStr = dollarSecRowLast.getStringCellValue();
		String euroSecRowLastStr = euroSecRowLast.getStringCellValue();
		
		System.out.println("\n" + dollarSecRowLastStr + "  " + euroSecRowLast );
		
		System.out.println();
		
		if(dollarRowLastStr != dollarSecRowLastStr | euroSecRowLastStr != euroRowLastStr) {
			checkRefhCurs = true;
		}else {checkRefhCurs = false;}
		
		System.out.println("checkRefhCurs = " + checkRefhCurs);
		
		return checkRefhCurs;
		
	}
}
