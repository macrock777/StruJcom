package com.efive.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import com.efive.agencyonline.common.EfiveAction;

public class JasperReportUtil extends EfiveAction {
	
	
	// For Hibernate  >4  ======>>....
	
	
	/* Following Jar File Required For JsperReport in Hibernate >= 4
			 	- jasperreports-6.3.0.jar		//In Hibernate  <=3  Required only this jar
				- com.lowagie.text-2.1.7
			 	- commons-beanutils-1.8.3
			  	- commons-digester-2.1
			  	- commons-logging-4.0.6
			   	- org.apache.commons.collections
			   	- jasperreports-javaflow
  */
 
	/*public static boolean generateJasperReport(String jasperFilePath, Map parameters , String fileSaveAs, Session hibernateSession ){			//fileSaveAs  should be  with  .pdf  Extension
		try{
				if(null!=jasperFilePath && jasperFilePath.trim().length()>0){
					String jasperServerDir ="jasperreport";
					File theDir =  new File(request.getRealPath("/" + jasperServerDir));				// Check  Is dir Exist  or not  Otherewise it will Create  dir 
						if (!theDir.exists()) {
							boolean result = theDir.mkdir();
						} 
					String reportWriteAt= request.getRealPath("/"+jasperServerDir);		// get full path  from Drive or Server  like...   C:/apache/ webapps/ ..
					String prnfilename ="";
						try {
							//for  Hibernate  <= 3
									//prnfilename = JasperFillManager.fillReportToFile(jasperFilePath, parameters,  hibernateSession.connection());
							
							//for Hibernate > = 4
							 			//Connection connection  =getHbConnectionObj(hibernateSession);
							 			prnfilename =JasperFillManager.fillReportToFile(jasperFilePath, parameters, getHbConnectionObj(hibernateSession));
							
							JasperExportManager.exportReportToPdfFile(prnfilename, reportWriteAt+"/"+fileSaveAs);
						}catch(Exception e){
							e.printStackTrace();
							return false;
						}
						
						try{
							File file = new File(reportWriteAt+"/"+fileSaveAs);					// check Files Exist where where file was written
							if (!file.exists())
								file.createNewFile();
						
							// Download Report  File 
							ServletOutputStream sos = response.getOutputStream();
							String fname=reportWriteAt+"/"+fileSaveAs ;
							fname=fname.replace("\\", "/");
							response.setContentType("application/pdf");
							response.setHeader("Content-Disposition", "attachment; filename=\""+ fileSaveAs + "");
							FileInputStream fin = new FileInputStream(file);
							IOUtils.copy(fin, sos);
							fin.close();			
							sos.flush();
							sos.close();
						}catch(Exception ex){
							ex.printStackTrace();
							return  false;
						}
				}				
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}*/
	
	// for get  Hibernate Connection Obj  in Hibernate > =4
	/*public static Connection getHbConnectionObj(final Session hibernateSession){
		 Connection connection =null;
		try{
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        		connection = session.doReturningWork(new ReturningWork<Connection>() {
            @Override
            public Connection execute(Connection conn) throws SQLException {
                return conn;
            }
        });
		}catch(Exception ex){
			ex.printStackTrace();
		}
        return connection;
	}*/
	

	
	
	
	
	
	
	// For Hibernate  <=3  ======>>....
	
	public boolean generateJasperReport(String jasperFilePath, Map parameters , String fileSaveAs, Session hibernateSession ){			//fileSaveAs  should be  with  .pdf  Extension
		// Required .Jar File == >     jasperreports-6.3.0.jar
		try{
				if(null!=jasperFilePath && jasperFilePath.trim().length()>0){
					String jasperServerDir ="jasperreport";
					File theDir =  new File(request.getRealPath("/" + jasperServerDir));				// Check  Is dir Exist  or not  Otherewise it will Create  dir 
						if (!theDir.exists()) {
							boolean result = theDir.mkdir();
						} 
					String reportWriteAt= request.getRealPath("/"+jasperServerDir);		// get full path  from Drive or Server  like...   C:/apache/ webapps/ ..
					String prnfilename ="";
						try {
							 prnfilename = JasperFillManager.fillReportToFile(jasperFilePath, parameters, hibernateSession.connection());
							JasperExportManager.exportReportToPdfFile(prnfilename, reportWriteAt+"/"+fileSaveAs);
						}catch(Exception e){
							e.printStackTrace();
							return false;
						}
						
						try{
							File file = new File(reportWriteAt+"/"+fileSaveAs);					// check Files Exist where where file was written
							if (!file.exists())
								file.createNewFile();
						
							// Download Report  File 
							ServletOutputStream sos = response.getOutputStream();
							String fname=reportWriteAt+"/"+fileSaveAs ;
							fname=fname.replace("\\", "/");
							response.setContentType("application/pdf");
							response.setHeader("Content-Disposition", "attachment; filename=\""+ fileSaveAs + "");
							FileInputStream fin = new FileInputStream(file);
							IOUtils.copy(fin, sos);
							fin.close();			
							sos.flush();
							sos.close();
						}catch(Exception ex){
							ex.printStackTrace();
							return  false;
						}
				}				
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	
}
