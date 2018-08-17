package com.efive.util;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.efive.agencyonline.common.EfiveAction;
import com.efive.agencyonline.common.EfiveUtils;


public class MysqlUtilities extends EfiveAction {		// DatabaseUtilities
	
	
	//Max+1 Query 
	public String getMaxValue(String tablename,  String columnname, String whereClause){		//  if  where cluse is null then pass as  ->  "" or  null
		try{
				String strQuery ="SELECT  IFNULL(MAX("+columnname+") ,0)+1 FROM  "+tablename; 
					if(null!=whereClause &&  whereClause.trim().length()>0)
							strQuery += " "+whereClause;
				List  <Object> maxPlusValList =genericDAO.getDataFromQuery(strQuery); 
					return maxPlusValList.get(0).toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "0";
	}
	  
	public boolean isTableExist(String tablename){		//This Method willl check   for  given Table Name  is Exist or Not
		try{
			String strQuery ="SHOW TABLES LIKE '"+tablename.toLowerCase()+"'";
			List   isTableExitList =genericDAO.getDataFromQuery(strQuery); 
			if(null!=isTableExitList && isTableExitList.size()>0)
				return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false ;
	}
	
	
	
// LOG Print & Save 
	// Log print and insert in table
		public void printLog(String logtype , String varName,  String logStr){
			System.out.println(logtype+" :  "+ varName+" :=  "+ logStr);
			saveLog(logtype,varName,logStr);
		}
		
		public  boolean  saveLog(String logtype , String varName,  String logStr){		//Log 	insert in table
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
						
						String strQuery = "INSERT INTO logdetails(txnnumber,srno,logtype, varname,logstr,userid, datetime) "
								+"VALUES("+txnnumber+"," 
												+"(SELECT IFNULL(MAX(ld.srno),0)+1  FROM logdetails ld  WHERE  txnnumber = "+txnnumber+" ) ,"
												+"'"+logtype+"','"+varName+"','"+logStr.replace("'", "''") +"','"+userid+"','"+timestamp+"'"													
										+")";
						
					// Check  'logdeatils'  table exist 	
					if(false==isTableExist("logdetails")){
							if(true ==createLogdetailtable()){
								return CrudCommon.executeSqlQuery(strQuery);
							}
						}else{
							return CrudCommon.executeSqlQuery(strQuery);
						}
					
			}catch(Exception e){
				new ErrorHandling_old().saveError(e);
				e.printStackTrace();
			}
			return false;
			
		}
		public static boolean createLogdetailtable(){
			try{
				String strCreatetable ="CREATE TABLE logdetails ("
														  +" txnnumber DECIMAL(20,0) NOT NULL, " 
														  +" srno DECIMAL(8,0) DEFAULT NULL, "
														  +" logtype VARCHAR(32) DEFAULT NULL,"
														  +" varname VARCHAR(32) DEFAULT NULL, "
														  +" logstr VARCHAR(4000) DEFAULT NULL, "
														  +" userid VARCHAR(32) DEFAULT NULL,"
														  +" datetime DATETIME DEFAULT NULL, "
														  +" PRIMARY KEY (txnnumber) "
														+") ENGINE=INNODB DEFAULT CHARSET=latin1";
				
				return CrudCommon.executeSqlQuery(strCreatetable);
				
			}catch(Exception e){
				e.printStackTrace();	
			}
			return false;
		}
	
	
	
//Error/Exception Save 	
	
	public boolean saveError(Exception ex){
		try{
			// Var Declaration		
			String userid ="-1";
			long txnnumber = EfiveUtils.getTxnnumberms();
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
					// Check  'logdeatils'  table exist 	
					if(false==isTableExist("errorhandling")){
							if(true ==createErrorHandlingTable()){
								return CrudCommon.executeSqlQuery(strQuery);
							}
						}else{
							return CrudCommon.executeSqlQuery(strQuery);
						}											
				
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public boolean createErrorHandlingTable(){
			try{
				String strCreatetable ="CREATE TABLE errorhandling ( "
														  	+" txnnumber decimal(20,0) NOT NULL, "
														  	+" srno decimal(10,0) NOT NULL, "
														  	+" type varchar(128) DEFAULT NULL,"
														  	+" classname varchar(64) DEFAULT NULL, "
														  	+" methodname varchar(32) DEFAULT NULL,"
														  	+" lineno decimal(10,0) DEFAULT NULL, "
														  	+" errormessage varchar(40000) DEFAULT NULL, "
														  	+" userid varchar(32) DEFAULT NULL, "
														  	+" datetime datetime DEFAULT CURRENT_TIMESTAMP, "
														  	+" PRIMARY KEY (txnnumber,srno) "
															+" ) ENGINE=InnoDB DEFAULT CHARSET=latin1";
				
				return CrudCommon.executeSqlQuery(strCreatetable);
				
			}catch(Exception e){
				e.printStackTrace();	
			}
			return false;
		}
	

}
