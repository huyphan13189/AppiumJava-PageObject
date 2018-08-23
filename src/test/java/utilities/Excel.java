package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class Excel {

	public static List<String> readFirstRow(String filePath,String sheetName){
		List<String> fieldsArrayList  = new ArrayList<String>();
		try{
			FileInputStream myInput = new FileInputStream(new File(filePath));
			Workbook workBook = WorkbookFactory.create(myInput);
			Sheet sheet = workBook.getSheet(sheetName);
			Row firstRow = sheet.getRow(0);
			int length  = firstRow.getLastCellNum();
			Cell cell = null;

			for( int i = 0 ; i < length ; i++ )

			{
				cell = firstRow.getCell(i);

				fieldsArrayList.add(cell.toString());

			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return fieldsArrayList;
	} 

	public static List<HashMap<String,String>> readXSLXFile(String filePath,String sheetName){
		List<HashMap<String,String>> listData  = new ArrayList<HashMap<String,String>>();
		try{
			FileInputStream myInput = new FileInputStream(new File(filePath));
			Workbook workBook = WorkbookFactory.create(myInput);
			Sheet sheet = workBook.getSheet(sheetName);

			for ( int i = 1 ; i <= sheet.getLastRowNum() ; i++ )
			{
				Row rowFields = sheet.getRow(0);
				Row data = sheet.getRow(i);
				if (data==null || data.getLastCellNum() <= 0) continue;
				HashMap<String,String> hmData = new HashMap<>();

				for( int j = 0 ; j < rowFields.getLastCellNum() ; j++ )

				{
					Cell dataCell = data.getCell(j);
					hmData.put(rowFields.getCell(j).toString().trim(),convertCell(dataCell));
				}

				listData.add(hmData);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return listData;
	}
	
	public static List<HashMap<String,String>> readXSLXFileID(String filePath,String sheetName, String testCaseID){
		List<HashMap<String,String>> listData  = new ArrayList<HashMap<String,String>>();
		try{
			FileInputStream myInput = new FileInputStream(new File(filePath));
			Workbook workBook = WorkbookFactory.create(myInput);
			Sheet sheet = workBook.getSheet(sheetName);

			for ( int i = 1 ; i <= sheet.getLastRowNum() ; i++ )
			{
				Row rowFields = sheet.getRow(0);
				Row data = sheet.getRow(i);
				if (data==null || data.getLastCellNum() <= 0 || !data.getCell(0).toString().trim().equals(testCaseID)) continue;
				HashMap<String,String> hmData = new HashMap<>();

				for( int j = 0 ; j < rowFields.getLastCellNum() ; j++ )

				{
					Cell dataCell = data.getCell(j);
					hmData.put(rowFields.getCell(j).toString().trim(),convertCell(dataCell));
				}

				listData.add(hmData);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return listData;
	}
	
	@SuppressWarnings("unchecked")
	public static Object[][] readXSLXFileDataProvider(String filePath,String sheetName){
		HashMap<String,String>[][] listData = null ;
		try{
			FileInputStream myInput = new FileInputStream(new File(filePath));
			Workbook workBook = WorkbookFactory.create(myInput);
			Sheet sheet = workBook.getSheet(sheetName);
			listData = new HashMap[sheet.getLastRowNum()][1];
			for ( int i = 1 ; i <= sheet.getLastRowNum() ; i++ )
			{
				Row rowFields = sheet.getRow(0);
				Row data = sheet.getRow(i);
				if (data==null || data.getLastCellNum() <= 0) continue;
				HashMap<String,String> hmData = new HashMap<>();

				for( int j = 0 ; j < rowFields.getLastCellNum() ; j++ )

				{
					Cell dataCell = data.getCell(j);
					hmData.put(rowFields.getCell(j).toString().trim(),convertCell(dataCell));
				}

				listData[i-1][0]=hmData;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return listData;
	}
	
	@SuppressWarnings("deprecation")
	private static String convertCell(Cell c){
		c.setCellType(Cell.CELL_TYPE_STRING);
		String result ="";
		if (c == null || c.getCellTypeEnum()== CellType.BLANK){
			result ="";
		}else{
			result = c.toString().trim();
		}
			
		return result;
	}

}
