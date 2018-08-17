package com.efive.util;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import com.efive.agencyonline.common.EfiveAction;
import com.efive.agencyonline.common.EfiveUtils;

public class LogUtil_old extends EfiveAction{
	

	// Log print and insert in table
	public void printLog(String logtype , String varName,  String logStr){
		System.out.println(logtype+" :  "+ varName+" :=  "+ logStr);
		saveLog(logtype,varName,logStr);
	}
	
	public boolean  saveLog(String logtype , String varName,  String logStr){		//Log 	insert in table
		try{
					//Var Declaration		
					String userid ="-1";
					long txnnumber = EfiveUtils.getTxnnumberms();
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					try{			// for get userid  from session 
							HttpSession session = request.getSession();
							userid =  session.getAttribute("userid").toString();
					}catch(Exception e1){
							//e1.printStackTrace();
					}
				/*	String strQuery = "INSERT INTO logdetails(txnnumber,srno,logtype, varname,logstr,userid, datetime) "
													+"VALUES("+txnnumber+"," 
																	+"(SELECT IFNULL(MAX(ld.srno),0)+1  FROM logdetails ld  WHERE  txnnumber = "+txnnumber+" ) ,"
																	+"'"+logtype+"','"+varName+"','"+logStr+"','"+userid+"','"+timestamp+"'"													
															+")";*/
					String strQuery = "INSERT INTO logdetails(txnnumber,srno,logtype, varname,logstr,userid, datetime) "
							+"VALUES("+txnnumber+"," 
											+"(SELECT IFNULL(MAX(ld.srno),0)+1  FROM logdetails ld  WHERE  txnnumber = "+txnnumber+" ) ,"
											+"'"+logtype+"','"+varName+"','"+logStr+"','"+userid+"','"+timestamp+"'"													
									+")";
				
				return CrudCommon.executeSqlQuery(strQuery);
		}catch(Exception e){
			new ErrorHandling_old().saveError(e);
			e.printStackTrace();
		}
		return false;
		
	}
	
	
}
