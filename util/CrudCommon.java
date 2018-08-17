package com.efive.util;

import java.sql.Statement;
import org.hibernate.Session;
import org.hibernate.exception.SQLGrammarException;

import com.efive.agencyonline.common.EfiveAction;
import com.efive.agencyonline.hibernate.HibernateSessionFactory;


public class CrudCommon  extends EfiveAction{
	
	
	public static boolean saveOrUpdatePojoObj(Session hibernateSession, Object Obj){			// Save Or Update Pojo Object
		 try{
					hibernateSession.beginTransaction();
					hibernateSession.saveOrUpdate(Obj);
					hibernateSession.flush();
					hibernateSession.getTransaction().commit();
					return true;
			}catch(Exception e){
					e.printStackTrace();
					hibernateSession.getTransaction().rollback();
					return false;
			}finally{
					hibernateSession.clear();
			}
	}
	
	
	// Following Method For Hibernate >= 5
	/*public static boolean executeSqlQuery(final String sqlquery){  	// For    Insert/Update/Delete   - Sql Query
		
		final Session hibernateSession =  HibernateSessionFactory.getSession();
		final boolean[]  returnval = new boolean[1];
        try{
			hibernateSession.beginTransaction();		
			hibernateSession.doWork(new Work(){
				public   void execute(Connection connection)  {
		            	  try{
				            	  Statement statement =null;
				            	  statement = connection.createStatement();
				            	  statement.executeUpdate(sqlquery);
				            	  statement.close();
				            	  hibernateSession.flush();
				            	  hibernateSession.getTransaction().commit();
				            	  returnval[0] =true;
		            	  }catch(Exception e) {
		            		  e.printStackTrace();
		            			hibernateSession.getTransaction().rollback();		
		            			returnval[0] =false;
		            	  }
		          }		// execute() over
			});
		}catch(Exception e){
			e.printStackTrace();
			returnval[0] = false;
		}
		return returnval[0];
	}*/
	
	
	
	
	// Following Method For Hibernate < 5		(if Hibernate 3)
	
	 public static boolean executeSqlQuery(String sqlquery){
			try{
				Session hibernateSession = HibernateSessionFactory.getSession();
					try{
							hibernateSession.beginTransaction();
							java.sql.Connection connection = hibernateSession.connection();
							Statement statement1 = null;					
							statement1 = connection.createStatement();
							statement1.executeUpdate(sqlquery);
							statement1.close();
							hibernateSession.flush();
							hibernateSession.getTransaction().commit();
							return true;
						}catch(Exception e){
								e.printStackTrace();
								hibernateSession.getTransaction().rollback();				
								return false;
						}
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
	} 
	
	
	
	
	

}
