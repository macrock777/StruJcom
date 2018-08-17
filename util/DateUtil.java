package com.efive.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	
	
	// Date Parsing... 
	public static Date convertStrToDate(String dateStr, String currentDateFormat){		//Covert to  Java Date   args [currentDateFormat ] like .. dd-MM-yyyy  
		try{
			SimpleDateFormat Objdf = new SimpleDateFormat(currentDateFormat);
			Date parsedDate = Objdf.parse(dateStr);
		 return parsedDate;
	}catch(Exception e){
		e.printStackTrace();
	}
	return null;
	}
	
	
	public static String  convertDate(String dateStr, String currentDateFormat, String requiredFormat){		// Date will Convert To current Format  To  Required Format
		try{																																																	//  Like  dd-MM-yyyy  To MM-dd-yyyyy ...
			SimpleDateFormat Objdf = new SimpleDateFormat(currentDateFormat);		
			Date parsedDate = Objdf.parse(dateStr);						// It will  Convert String To date 
			
			SimpleDateFormat Objdf1 = new SimpleDateFormat(requiredFormat);			
			String parsedDateStr =Objdf1.format(parsedDate);				// It will Convert Date in Required Format
			return parsedDateStr;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Timestamp convertStrToTimestamp(String dateStr, String currentDateFormat){	//Covert to  Java Sql TimeStamp  
		try{
			SimpleDateFormat Objdf = new SimpleDateFormat(currentDateFormat);
			Date parsedDate = Objdf.parse(dateStr);
			Timestamp parsedtimestamp=new Timestamp(parsedDate.getTime());
			return parsedtimestamp;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
		}
	
	
	
	
	
	
	
	
	
	
/*
	
	// ----------------------------------------------  ***  -------------------------------//
	//PART - 1
	public static Date  ddmmyyDateParse(String ddmmyyy){		// dd-MM-yyyy  Covert to  Java Date   For save date in Table 
		try{
				SimpleDateFormat Objdf = new SimpleDateFormat("dd-MM-yyyy");
				Date date = Objdf.parse(ddmmyyy);
			 return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date  mmddyyyyDateParse(String mmddyyyy){		// MM-dd-yyyy  Covert to  Java Date 
		try{
				SimpleDateFormat Objdf = new SimpleDateFormat("MM-dd-yyyy");
				Date date = Objdf.parse(mmddyyyy);
			 return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date  yyyymmddDateParse(String yyyymmdd){		// yyyy -MM-dd  Covert to  Java Date 
		try{
				SimpleDateFormat Objdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = Objdf.parse(yyyymmdd);
			 return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	//PART - 2
	public static Date  ddMMMyyyDateParse(String ddMMMyyyy){		// dd/MMM/yyy Covert to  Java Date 
		try{
				SimpleDateFormat Objdf = new SimpleDateFormat("yyyy-MMM-dd");
				Date date = Objdf.parse(ddMMMyyyy);
			 return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date  MMMddyyy(String MMMddyyyy){		// MMM-dd-yyyy Covert to  Java Date 
		try{
				SimpleDateFormat Objdf = new SimpleDateFormat("MMM-dd-yyyy");
				Date date = Objdf.parse(MMMddyyyy);
			 return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date 	yyyyMMMdd(String MMMddyyyy){		// yyyy-MMM-dd Covert to  Java Date 
		try{
				SimpleDateFormat Objdf = new SimpleDateFormat("yyyy-MMM-dd");
				Date date = Objdf.parse(MMMddyyyy);
			 return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	//PART - 3
		
	public static Date  ddmmyyWithSlash(String ddmmyyy){		// 		dd/MM/yyyy  Covert to  Java Date   For save date in Table 
		try{
				SimpleDateFormat Objdf = new SimpleDateFormat("dd-MM-yyyy");
				Date date = Objdf.parse(ddmmyyy);
			 return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date  mmddyyyyWithSlash(String mmddyyyy){		// 		MM/dd/yyyy  Covert to  Java Date 
		try{
				SimpleDateFormat Objdf = new SimpleDateFormat("MM-dd-yyyy");
				Date date = Objdf.parse(mmddyyyy);
			 return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static Date  yyyymmddWithSlash(String yyyymmdd){		// 		yyyy/MM/dd  Covert to  Java Date 
		try{
				SimpleDateFormat Objdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = Objdf.parse(yyyymmdd);
			 return date;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	*/

}
