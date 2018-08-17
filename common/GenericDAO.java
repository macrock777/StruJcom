package com.efive.agencyonline.common;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.efive.agencyonline.hibernate.HibernateSessionFactory;
import com.efive.util.CrudCommon;




public class GenericDAO {

	private static final ThreadLocal<GenericDAO> threadLocal = new ThreadLocal<GenericDAO>();

	private static GenericDAO genericDAO = null;

	//Logger logger = Logger.getLogger(GenericDAO.class);

	private GenericDAO() {
	}

	public static GenericDAO getInstance() {
		genericDAO = threadLocal.get();

		if (null == genericDAO) {
			genericDAO = new GenericDAO();
			threadLocal.set(genericDAO);
		}

		return genericDAO;
	}

	public List getNammedQueryData(String queryName) {
		return getNammedQueryData(queryName, null);
	}

	public List getNammedQueryData(String queryName,
			HashMap<String, Object> params) {
		try {

			Session session = HibernateSessionFactory.getSession();
			Query query = session.getNamedQuery(queryName);
			if (null != params && params.size() > 0) {
				Object[] keys = params.keySet().toArray();
				for (int i = 0; i < keys.length; i++) {
					Object objValue = params.get(keys[i]);
					if (objValue instanceof List) {
						System.out.println("GenericDAO.getNammedQueryData()");
						query.setParameterList((String) keys[i],
								(Collection) objValue);

					} else if (objValue instanceof String[]) {
						query.setParameterList((String) keys[i],
								(String[]) objValue);
						//logger.error("QueryParam : " + keys[i] + " - "
						//		+ Arrays.toString((String[]) objValue));
					} else if (objValue instanceof Long[]) {
						query.setParameterList((String) keys[i],
								(Long[]) objValue);
						//logger.error("QueryParam : " + keys[i] + " - "
						//		+ Arrays.toString((Long[]) objValue));
					} else {
						if(objValue instanceof String && objValue.equals("@@"))
							objValue="%";
						query.setParameter((String) keys[i], objValue);
						//logger.error("QueryParam : " + keys[i] + " - " + objValue);
					}
					
				}
			}
			List dataList = query.list();

			return dataList;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}
        //Old 
	public List getDataFromQuery(String strQuery) {
		try{
			Session session = HibernateSessionFactory.getSession();
			//logger.error(strQuery);
			SQLQuery query = session.createSQLQuery(strQuery);
			List dataList = query.list();
			return dataList;
		}/*catch(SQLGrammarException sgex){
			if(sgex.getMessage().toLowerCase().equals("could not execute query"))
				CrudCommon.executeSqlQuery("rollback ");
			sgex.printStackTrace();
		}*/catch(Exception ex){
			if(ex.getMessage().toLowerCase().equals("could not execute query"))
				CrudCommon.executeSqlQuery("rollback");
			
			ex.printStackTrace();
		}
		return null;
	}    
	
	


	

}
