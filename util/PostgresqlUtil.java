package com.efive.util;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.apache.struts2.ServletActionContext;

import com.efive.agencyonline.agencycommon.CommonWork;
import com.efive.agencyonline.common.CommonAction;
import com.efive.agencyonline.common.EfiveAction;
import com.efive.agencyonline.common.EfiveUtils;
import com.efive.agencyonline.common.GenericDAO;

public class PostgresqlUtil extends EfiveAction {		// DatabaseUtilities
	
	
	protected static GenericDAO genericDAO = GenericDAO.getInstance();
	
	//Max+1 Query 
	public static String getMaxValue(String tablename,  String columnname, String whereClause, String schemaName){		//  if  where cluse is null then pass as  ->  "" or  null
		try{
				String strQuery ="SELECT  coalesce(MAX("+columnname+") ,0)+1 FROM "+ schemaName+"."+tablename; 
					if(null!=whereClause &&  whereClause.trim().length()>0)
							strQuery += " "+whereClause;
				List  <Object> maxPlusValList = genericDAO.getDataFromQuery(strQuery); 
					return maxPlusValList.get(0).toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "0";
	}
	
	public static boolean isOnlyTableExist(String tablename , String tableSchema){			// Default  tableSchema  = 'public'   or  null  
			try{
				 	if(null!= tablename  &&  tablename.trim().length()>0){
				 		String strQuery ="SELECT EXISTS ( " 
																								+" SELECT 1  FROM   pg_catalog.pg_class c " 
																								+" JOIN   pg_catalog.pg_namespace n ON n.oid = c.relnamespace"
																								+" Where  c.relname = '"+tablename+"' ";

																								if(null!=tableSchema && tableSchema.trim().length()>0) 		
																								strQuery +=" AND  n.nspname = '"+tableSchema+"'";
																								
																								strQuery+=" AND    c.relkind = 'r')";


				 			List   isTableExitList =genericDAO.getDataFromQuery(strQuery); 
				 			if(null!=isTableExitList && isTableExitList.size()>0 && isTableExitList.get(0).toString().equals("true"))
				 				return true;											
				 	}
				
			}catch(Exception e){
				e.printStackTrace();
			}			
		return false;
	}

	
// It's check   Table (or View) exists, and the current user has access to it	
	public static boolean isTableOrViewExist(String tablename , String tableSchema){		// Default tableSchema = null or public
		try{
		 	if(null!= tablename  &&  tablename.trim().length()>0){
		 		String strQuery  ="SELECT EXISTS ("
			 																			+" SELECT 1 FROM   information_schema.tables "
			 																			+ " Where  table_name = '"+tablename+"' ";
														  								if(null!=tableSchema && tableSchema.trim().length()>0) 		
														  									strQuery +=" AND  table_schema = '"+tableSchema+"' ";
		 													strQuery+=");";
		 													
		 			System.out.println(strQuery);										
		 			List   isTableExitList =genericDAO.getDataFromQuery(strQuery); 
		 			if(null!=isTableExitList && isTableExitList.size()>0 && isTableExitList.get(0).toString().equals("true"))
		 				return true;											
		 	}
		
	}catch(Exception e){
		e.printStackTrace();
	}			
return false;
	}
	
	
	// LOG Print & Save 
		// Log print and insert in table
			public static void printLog(String logtype , String varName,  String logStr, String schemaName ){
				System.out.println(logtype+" :  "+ varName+" :=  "+ logStr);
				saveLog(logtype,varName,logStr, schemaName);
			}
			
			public static boolean  saveLog(String logtype , String varName,  String logStr, String schemaName){		//Log 	insert in table
				try{
							//Var Declaration		
							String userid ="-1";
							long txnnumber = EfiveUtils.getTxnnumberms();
							Timestamp timestamp = new Timestamp(System.currentTimeMillis());
							HttpServletRequest request = ServletActionContext.getRequest();
							try{			// for get userid  from session 
									HttpSession session = request.getSession();
									userid =  session.getAttribute("userid").toString();
							}catch(Exception e1){
									//e1.printStackTrace();
							}
							
							String strQuery = "INSERT INTO "+schemaName+".logdetails(txnnumber,srno,logtype, varname,logstr,userid, datetime) "
									+"VALUES("+txnnumber+"," 
													+"(SELECT coalesce(MAX(ld.srno),0)+1  FROM "+schemaName+".logdetails ld  WHERE  txnnumber = "+txnnumber+" ) ,"
													+"'"+logtype.replaceAll("'", "''")+"','"+ varName.replaceAll("'", "''")+"','"+logStr.replaceAll("'", "''")+"','"+userid+"','"+timestamp+"'"													
											+")";
							
						// Check  'logdeatils'  table exist 	
						if(false==isOnlyTableExist("logdetails",schemaName)){
								if(true ==createLogdetailtable(schemaName)){
									return CrudCommon.executeSqlQuery(strQuery);
								}
							}else{
								return CrudCommon.executeSqlQuery(strQuery);
							}
						
				}catch(Exception e){
					saveError(e,schemaName);
					e.printStackTrace();
				}
				return false;
				
			}
			public static boolean createLogdetailtable(String schemaName){
				try{
					String strCreatetable ="CREATE TABLE "+schemaName+".logdetails( "
																+" txnnumber numeric(20,0) NOT NULL, "
																+" srno numeric(8,0), "
																+" logtype character(32), "
																+" varname character(32), "
																+" logstr character(4000), "
																+" userid character(32), "
																+" datetime timestamp without time zone, "
																 +" CONSTRAINT logdetails_pkey PRIMARY KEY (txnnumber,srno) "
																+" )";
					
					return CrudCommon.executeSqlQuery(strCreatetable);
					
				}catch(Exception e){
					e.printStackTrace();	
				}
				return false;
			}
		
		
		
	//Error/Exception Save 	
		
		public static boolean saveError(Exception ex, String schemaName){
			try{
				// Var Declaration		
				String userid ="-1";
				long txnnumber = EfiveUtils.getTxnnumberms();
			//	String srno = MysqlUtilities.getValue("errorhandling", "srno", null);
				String type =ex.getClass().getCanonicalName();
				StackTraceElement[] stk = ex.getStackTrace();
				String className = stk[0].getClassName(); 
				String methodName = stk[0].getMethodName();
				int lineno=stk[0].getLineNumber();
				String errorMsg =ex.getMessage();
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				HttpServletRequest request = ServletActionContext.getRequest();
				try{			// for get userid  from session 
					HttpSession session = request.getSession();
					userid =  session.getAttribute("userid").toString();
				}catch(Exception e1){
					//e1.printStackTrace();
				}
				
				String strQuery = "INSERT INTO "+schemaName+".errorhandling(txnnumber,srno,type, classname,methodname, lineno,errormessage,userid, datetime) "
												+"VALUES("+txnnumber+"," 
																	+"(SELECT coalesce(MAX(eh.srno),0)+1  FROM errorhandling eh  WHERE  txnnumber = "+txnnumber+" ) ,"
																	+"'"+type+"','"+className+"','"+methodName+"',"+lineno+",'"+errorMsg+"','"+userid+"','"+timestamp+"'"													
																+")";
				
						if(errorMsg.toLowerCase().equals("could not execute query"))
							CrudCommon.executeSqlQuery("rollback ");
						
						// Check  'logdeatils'  table exist 	
						if(false==isOnlyTableExist("errorhandling",schemaName)){
								if(true == createErrorHandlingTable(schemaName)){
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
		
		public static boolean createErrorHandlingTable(String schemaName){
				try{
					String strCreatetable ="CREATE TABLE "+schemaName+".errorhandling ( "
															 	+" txnnumber DECIMAL(20,0) NOT NULL, "
															 	+" srno DECIMAL(10,0) NOT NULL, "
															 	+" type VARCHAR(128) DEFAULT NULL, "
															 	+" classname VARCHAR(64) DEFAULT NULL, "
															 	+" methodname VARCHAR(32) DEFAULT NULL, "
															 	+" lineno DECIMAL(10,0) DEFAULT NULL, "
															 	+" errormessage VARCHAR(4000) DEFAULT NULL, "
															 	+" userid VARCHAR(32) DEFAULT NULL, "
															 	+" datetime TIMESTAMP(0) DEFAULT CURRENT_TIMESTAMP, "
															 	+" PRIMARY KEY (txnnumber,srno) "
															 	+")";
					
					return CrudCommon.executeSqlQuery(strCreatetable);
				}catch(Exception e){
					e.printStackTrace();	
				}
				return false;
			}
		
		public void main(String[] args) {
			String strQuery=  "SELECT  userid, username, usertype,loginname,password,email,contact1,contact2,address,cityid,stateid,countryid,isfirst,active "
					+" FROM agencyonline.usermaster	where loginname = 'efive@gmail.com'  AND password = 'efive' " 
					+" AND UPPERRR(active)='Y'  ";
			
//PostgresqlUtil.printLog("Login Query ", "strQuery", strQuery,"agencyonline");
			List loginList= genericDAO.getDataFromQuery(strQuery);
			System.out.println(loginList);
			//List  a= genericDAO.getDataFromQuery("SELECT EXISTS (  SELECT 1  FROM   pg_catalog.pg_class c  JOIN   pg_catalog.pg_namespace n ON n.oid = c.relnamespace Where  c.relname = 'errorhandling'  AND  n.nspname = 'agencyonline' AND    c.relkind = 'r')");
			//System.out.println(a);
			//isOnlyTableExist("errorhandling","agencyonline");
		}

		
		
	/*public static String getMaxValueWithSuffixPrefix(String tablename,  String columnname, String whereClause, int noofChar, String fixCharFlag, char charFix, String schemaName){			//  if  where cluse is null then pass as  ->  "" or  null
			try{
					String  strQuery ="";
					if(fixCharFlag =="P")
					 strQuery ="Select LPAD((coalesce(MAX("+columnname+") ,0)+1)::text, "+noofChar+", '"+charFix+"')  FROM  "+ schemaName+"."+tablename; 
					else if(fixCharFlag == "S")
						strQuery ="Select RPAD((coalesce(MAX("+columnname+") ,0)+1)::text, "+noofChar+", '"+charFix+"')  FROM  "+ schemaName+"."+tablename;
					
						if(null!=whereClause &&  whereClause.trim().length()>0)
								strQuery += " "+whereClause.replaceAll("'", "''");
					List  <Object> maxPlusValList =genericDAO.getDataFromQuery(strQuery); 
						return maxPlusValList.get(0).toString();
			}catch(Exception e){
				e.printStackTrace();
			}
			return "0";
		}
		*/
		
		
		
		
		
		
		
		
		
}
