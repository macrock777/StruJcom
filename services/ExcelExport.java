package com.efive.services;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.efive.agencyonline.common.EfiveAction;



public class ExcelExport extends EfiveAction{
	


public String exporttoExcel(String columns[], String query,String filename) {

	/*String loginid = String.valueOf(request.getSession().getAttribute(
			"LoginId"));*/
	String myPath = request.getRealPath("/excel");
	String fileName = filename + ".xls";
		
	File f = new File(myPath + File.separator + fileName);
	try {
	  	// Now Check Is Dir   Exist  
	  	File	theDir =  new File(myPath);
	  		if (!theDir.exists()) 
	  			theDir.mkdir();	 

		if (!f.exists())
			/*f.mkdir();*/
			f.createNewFile();

		ServletOutputStream sos = response.getOutputStream();
		Cell cell = null;
		Row row = null;

		XSSFWorkbook wb = new XSSFWorkbook();
		Sheet sheet1 = wb.createSheet("Sheet1");
		//XSSFSheet sheet1 = wb.createSheet("Sheet1");
		row = sheet1.createRow(0);
		
		XSSFFont defaultFont= wb.createFont();
	    defaultFont.setBold(true);
	    
	    CellStyle fontBold = wb.createCellStyle();
	    fontBold.setFont(defaultFont);
	    
		for (int i = 0; i < columns.length; i++) {
			
			cell = row.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(fontBold);
		}

		List<Object[]> dataList = genericDAO.getDataFromQuery(query);
		for (int i = 0; i < dataList.size(); i++) {
			Object[] objRow = dataList.get(i);
			row = sheet1.createRow(i + 2);
			for (int j = 0; j < columns.length; j++) {
				cell = row.createCell(j);
				Object objData = objRow[j];
				if (objData instanceof BigDecimal) {
					CellStyle rightAlign = wb.createCellStyle();
					rightAlign.setAlignment(CellStyle.ALIGN_RIGHT);
					
					cell.setCellStyle(rightAlign);
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cell.setCellValue(String.format("%.2f", ((BigDecimal) objData).doubleValue()));
					
				} else if (objData instanceof Clob) {
					Clob data = (Clob) objData;
					try {
						if (data.length() > 0) {
							long length = data.length();
							if (length > 32767)
								length = 32767L;
							cell.setCellValue(data.getSubString(1L,
									(int) length));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					cell.setCellValue(null == objData ? "" : objData
							.toString());
				}

			}
		}
	
		long txnnumber = com.efive.agencyonline.common.EfiveUtils.getTxnnumber();
		String xlsfilename = txnnumber + "_" + filename + ".xlsx";
		response.setContentType("application/excel");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ xlsfilename + "");
		wb.write(sos);
		sos.flush();
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;
}
	
}
