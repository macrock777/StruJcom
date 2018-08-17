package com.efive.util;

import java.sql.Timestamp;
import javax.servlet.http.HttpSession;

import com.efive.agencyonline.common.EfiveAction;
import com.efive.agencyonline.common.EfiveUtils;

public class ErrorHandling_old extends EfiveAction{
	
	
	public boolean saveError(Exception ex){
		try{
			// Var Declaration		
			String userid ="-1";
			long txnnumber = EfiveUtils.getTxnnumberms();
			String srno = new MysqlUtilities().getMaxValue("errorhandling", "srno", null);
			String type =ex.getClass().getCanonicalName();
			StackTraceElement[] stk = ex.getStackTrace();
			String className = stk[0].getClassName(); 
			String methodName = stk[0].getMethodName();
			int lineno=stk[0].getLineNumber();
			String errorMsg =ex.getMessage();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			try{			// for get userid  from session 
				HttpSession session = request.getSession();
				userid =  session.getAttribute("userid").toString();
			}catch(Exception e1){
				//e1.printStackTrace();
			}
			
			String strQuery = "INSERT INTO errorhandling(txnnumber,srno,type, classname,methodname, lineno,errormessage,userid, datetime) "
											+"VALUES("+txnnumber+"," 
																+"(SELECT IFNULL(MAX(eh.srno),0)+1  FROM errorhandling eh  WHERE  txnnumber = "+txnnumber+" ) ,"
																+"'"+type+"','"+className+"','"+methodName+"',"+lineno+",'"+errorMsg+"','"+userid+"','"+timestamp+"'"													
															+")";
				return 	CrudCommon.executeSqlQuery(strQuery);
				
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	
	
	
}
