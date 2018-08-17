package com.efive.agencyonline.agencycommon;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.hibernate.Session;
import org.hibernate.impl.SessionImpl;

import com.efive.agencyonline.common.EfiveDataTableBean;
import com.efive.agencyonline.common.EfiveUtils;
import com.efive.agencyonline.common.StringUtil;
import com.efive.agencyonline.hibernate.Docseries;
import com.efive.agencyonline.hibernate.DocseriesId;
import com.efive.agencyonline.hibernate.HibernateSessionFactory;
import com.efive.util.CrudCommon;
import com.efive.util.PostgresqlUtil;

public class CommonWork extends EfiveDataTableBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String subscriberid,companyid,branchid,branchstate,branchgstin
	,ipaddress,macaddress,userid,loginname,username,usertype,branchname,formtype;
	public List<?> clientList,campaignList,brandList,estimatenoList;
	public String clientid,brandid,fileName,number_p,reportid;
	
	public InputStream reportinputStream;

	private DocseriesId docseriesId = null;
	
	private Docseries docseries = null;
	
	public Session hibernateSession = HibernateSessionFactory.getSession();
	
	public SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	public boolean getSession(){
		try {
			
			HttpSession httpSession = request.getSession();
			
			Object subscriberidObj = httpSession.getAttribute("subscriberid");
			Object companyidObj = httpSession.getAttribute("companyid");
			Object branchidObj = httpSession.getAttribute("branchid");
			Object branchstateObj = httpSession.getAttribute("branchstate");
			Object branchgstinObj = httpSession.getAttribute("branchgstin");
			Object branchnameObj = httpSession.getAttribute("branchname");
			Object ipaddressObj = httpSession.getAttribute("ipaddress");
			Object macaddressObj = httpSession.getAttribute("macaddress");
			Object useridObj = httpSession.getAttribute("userid");
			Object usernameObj = httpSession.getAttribute("username");
			Object usertypeObj = httpSession.getAttribute("usertype");
			Object loginnameObj = httpSession.getAttribute("loginname");
			
			if(null!=subscriberidObj)
				subscriberid = subscriberidObj.toString();
			
			if(null!=companyidObj)
				companyid = companyidObj.toString();
			
			if(null!=branchidObj)
				branchid = branchidObj.toString();
			
			if(null!=branchgstinObj)
				branchgstin = branchgstinObj.toString();
			
			if(null!=branchstateObj)
				branchstate = branchstateObj.toString();
			
			if(null!=branchnameObj)
				branchname = branchnameObj.toString();
			
			if(null!=loginnameObj)
				loginname = loginnameObj.toString();
			
			if(null!=usernameObj)
				username = usernameObj.toString();
			
			if(null!=usertypeObj)
				usertype = usertypeObj.toString();
			
			if(null!=ipaddressObj)
				ipaddress = ipaddressObj.toString();
			
			if(null!=macaddressObj)
				macaddress = macaddressObj.toString();
			
			if(null!=useridObj)
				userid = useridObj.toString();
			
			return true;
		} catch (Exception e) {
			PostgresqlUtil.saveError(e, "agencyonline");
			e.printStackTrace();
			return false;
		}
	}
	
	public String getEstimateListAjax(){
		try {
		getSession();
		
			if(StringUtil.checkNullAndEmpty(subscriberid,companyid,branchid,clientid,brandid,formtype)){
				estimatenoList = genericDAO.getDataFromQuery(returnSqlQuery(4));
			}
			
		} catch (Exception e) {
			PostgresqlUtil.saveError(e, "agencyonline");
			e.printStackTrace();
		}
		return "estimateList_ajax";
	}
	
	public String getClientListAjax(){
		try {
			
			getSession();
			if(StringUtil.checkNullAndEmpty(subscriberid,companyid,branchid)){
				clientList = genericDAO.getDataFromQuery(returnSqlQuery(1));
			}
			
		} catch (Exception e) {
			PostgresqlUtil.saveError(e, "agencyonline");
			e.printStackTrace();
		}
		return "clientlist_ajax";
	}
	
	public String getDocumentSeries(String documentid,String subscriberid,String lastDate){
		
		try {
			if(StringUtil.checkNullAndEmpty(documentid,subscriberid)){
				
				strQuery = "select docseriesid from agencyonline.docseries where "+
						 " CAST(documenttype AS numeric)= (select CAST(documentcode AS numeric) from agencyonline.documenttypemaster where documentid = "+documentid+") "+ 
						 " and subscriberid = "+subscriberid ;
				
				List<BigDecimal> docseriesid = genericDAO.getDataFromQuery(strQuery);
				
				String docseriesnumber = null;
				
				if(null!=docseriesid){
					
					docseriesId = new DocseriesId(docseriesid.get(0).intValue(),Integer.parseInt(subscriberid));
					
					docseries = (Docseries)hibernateSession.get(Docseries.class, docseriesId);
					
					if(docseries.getCurrentseries()==null){
						System.out.println("Info :: Bill Current Series is Empty...!");
						docseriesnumber = String.valueOf(docseries.getSeriesstart()+1);
					}else{
						System.out.println("Info :: Bill Current Series is Available...!");
						docseriesnumber = String.valueOf(docseries.getCurrentseries()+1);
					}
					
					if(null!=lastDate && lastDate.trim().length()>0)
						docseries.setLastdate(dateFormat.parse(lastDate));
					
					docseries.setCurrentseries(Long.parseLong(docseriesnumber));
					CrudCommon.saveOrUpdatePojoObj(hibernateSession,docseries);
					
					return docseriesnumber;
				}else{
					System.out.println("document series id is not found.... Sorry..!");
					return null;
				}
				
			}else{
				return null;
			}			
		} catch (Exception e) {
			PostgresqlUtil.saveError(e, "agencyonline");
			e.printStackTrace();
		}
		return null;
	}
	
	public String getBrandListAjax(){
		try {
			
			getSession();
			if(StringUtil.checkNullAndEmpty(subscriberid,companyid,clientid)){
				brandList = genericDAO.getDataFromQuery(returnSqlQuery(2));
			}
			
		} catch (Exception e) {
			PostgresqlUtil.saveError(e, "agencyonline");
			e.printStackTrace();
		}
		return "brandlist_ajax";
	}
	
	public String getCampaignListAjax(){
		try {
			
			getSession();
			if(StringUtil.checkNullAndEmpty(subscriberid,companyid,clientid,brandid)){
				campaignList = genericDAO.getDataFromQuery(returnSqlQuery(3));
			}
			
		} catch (Exception e) {
			PostgresqlUtil.saveError(e, "agencyonline");
			e.printStackTrace();
		}
		return "campaignlist_ajax";
	}
	
	public String returnSqlQuery(int index){
		try {
			
			switch (index) {
				case 1:
					strQuery = "SELECT clientid,clientname,shortname " +
							" FROM agencyonline.clientmaster WHERE active = 1 " +
							" AND subscriberid = "+subscriberid+" AND branchid = "+branchid+" AND companyid = "+companyid+" ";
					
					PostgresqlUtil.printLog("Client List","strQuery", strQuery, "agencyonline");
					break;
				case 2:
					strQuery = "SELECT brandid,brandname,shortname " +
							" FROM agencyonline.brandmaster WHERE active = 1 AND subscriberid = "+subscriberid+" " +
							" AND  companyid = "+companyid+" AND clientid = "+clientid+" ";
					PostgresqlUtil.printLog("Brand List","strQuery", strQuery, "agencyonline");
					break;
				case 3:
					strQuery = " SELECT campaignid,campaignname,shortname from agencyonline.campaignsmaster WHERE active = 1 "+ 
							" AND subscriberid = "+subscriberid+" AND companyid = "+companyid+" " +
							" AND clientid = "+clientid+" AND brandid = "+brandid+" "; 
					PostgresqlUtil.printLog("Campaign List","strQuery", strQuery, "agencyonline");
					break;
				case 4:
					strQuery ="  select  DISTINCT eh.estimatenumber  from agencyonline.estimateheader eh, 	agencyonline.tvschedulerdetails  sh  " 
							+" where  eh.isestimateapproved = 'Y' and UPPER(eh.estimatetype) = UPPER('"+formtype+"') and  eh.estimatenumber = sh.estimatenumber   "
							+" and  eh.subscriberid = "+subscriberid+"  and    eh.companyid = "+companyid+"  and   eh.branchid = "+branchid+"   "
							+" and  eh.clientid = "+clientid+" AND eh.brandid = "+brandid+" ORDER BY eh.estimatenumber desc ";
					PostgresqlUtil.printLog("Estimate No List","strQuery", strQuery, "agencyonline");
					break;
				case 5:
					strQuery = "SELECT reportfilename,destinationdirpath,generatedfilename "+ 
								" FROM agencyonline.reportdetailmaster WHERE reportid = "+reportid+" AND active = 1 ";
					PostgresqlUtil.printLog("Report Data List ","strQuery", strQuery, "agencyonline");
					break;
			}
			
		} catch (Exception e) {
			PostgresqlUtil.saveError(e, "agencyonline");
			e.printStackTrace();
		}
		return strQuery;
	}
	
	public static String setIfNull(Object obj,String nullValue){
		
		if(!(null!=nullValue && nullValue.trim().length()>0))
			nullValue = "";
		
		if(null==obj)
			return nullValue;
		else
			return obj.toString();
	
	}
	
	//ehstate is used for hoarding and event services
	public HashMap<String, String>  gstCalculator(String servicetype,String clientstatecode,String supplierstatecode,String ehstatecode,
			boolean taxapply,String taxslab,String statetype,String taxableAmount){
		HashMap<String, String> gstDataMap = new HashMap<String, String>();
		String gstcount = "NO",
				cgstamt = "0.00",cgstper = "0.00",
				sgstamt = "0.00",sgstper = "0.00",
				igstamt = "0.00",igstper = "0.00",
				utgstamt= "0.00", utgstper= "0.00";
		String otherstate = null;
		try {
			
			if(StringUtil.checkNullAndEmpty(servicetype,clientstatecode,taxslab,statetype,taxableAmount) 
					&& StringUtil.isNumeric(taxslab) && StringUtil.isNumeric(taxableAmount) && statetype.trim().length()==2 ){
				
				float amount =  Float.parseFloat(taxableAmount);
				float slab =  Float.parseFloat(taxslab);
				if(("hoarding".equalsIgnoreCase(servicetype) || "event".equalsIgnoreCase(servicetype)) 
						&& null!=ehstatecode && ehstatecode.trim().length()>0 ){
					otherstate = ehstatecode;
					
				}else if(!"hoarding".equalsIgnoreCase(servicetype) && !"event".equalsIgnoreCase(servicetype) 
						&& null!=supplierstatecode && supplierstatecode.trim().length()>0 ){
					otherstate = supplierstatecode;
				}
				
				if(taxapply && null!=otherstate && otherstate.trim().length()>0){
					if(clientstatecode.equals(otherstate)){
						
						float taxper = slab / 2;
						//State Territory 
						if("ST".equals(statetype)){
							
							float camt = amount * (taxper / 100);
							float samt = amount * (taxper / 100);
							gstcount = "YES";
							cgstamt = String.format("%.2f",camt);
							cgstper = String.format("%.2f",taxper);
							sgstamt = String.format("%.2f",samt);
							sgstper = String.format("%.2f",taxper);
							
						}else if("UT".equals(statetype)){//Union Territory
							
							float camt = amount * (taxper / 100);
							float uamt = amount * (taxper / 100);
							gstcount = "YES";
							cgstamt = String.format("%.2f",camt);
							cgstper = String.format("%.2f",taxper);
							utgstamt = String.format("%.2f",uamt);
							utgstper = String.format("%.2f",taxper);
						}
						
					}else{
						Float iamt = amount * (slab / 100);
						gstcount = "YES";
						igstamt = String.format("%.2f",iamt);
						igstper = String.format("%.2f",slab);	
					}
				}
			}
			
		} catch (Exception e) {
			gstcount = "ERROR";
			PostgresqlUtil.saveError(e, "agencyonline");
			e.printStackTrace();
		}
		gstDataMap.put("gstcount", gstcount);
		gstDataMap.put("cgstamt", cgstamt);
		gstDataMap.put("cgstper", cgstper);
		gstDataMap.put("sgstamt", sgstamt);
		gstDataMap.put("sgstper", sgstper);
		gstDataMap.put("igstamt", igstamt);
		gstDataMap.put("igstper", igstper);
		gstDataMap.put("utgstamt", utgstamt);
		gstDataMap.put("utgstper", utgstper);
		return gstDataMap;
	}
	
	public float calSpotAmt(String spottype,String duration,String surcharge,String noofspot,String rate,String specialrequest){
		
		float amt=0;
		int durationint =0;
		float surchargefloat =0;
		float ratefloat =0;
		int noofspotint=0;

		if(null!=duration &&  duration.trim().length()>0 && !duration.trim().equalsIgnoreCase("null"))
			 durationint = Integer.parseInt(duration);
		if(null!=surcharge &&  surcharge.trim().length()>0 && !surcharge.trim().equalsIgnoreCase("null"))
			 surchargefloat = Float.parseFloat(surcharge);
		if(null!=rate &&  rate.trim().length()>0  && !rate.trim().equalsIgnoreCase("null"))
			 ratefloat = Float.parseFloat(rate);
		if(null!=noofspot &&  noofspot.trim().length()>0  && !noofspot.trim().equalsIgnoreCase("null"))
			 noofspotint = Integer.parseInt(noofspot);

		if (spottype.equalsIgnoreCase("S") || spottype.equalsIgnoreCase("L")) { 
			if (null == specialrequest || specialrequest.trim().length() == 0)
				amt = (durationint * ((surchargefloat / 100) + 1) * ratefloat * noofspotint) / 10;
			else
				amt = (durationint * 1 * ratefloat * noofspotint) / 10;
		}

		if (spottype.equalsIgnoreCase("P")) {
			amt = noofspotint * ratefloat;
		}

		if (spottype.equalsIgnoreCase("B")) {
			amt = (durationint * ((surchargefloat / 100) + 1) * ratefloat) / 10;
		}
		return amt;
	}
	
	public String returnPdfReport(){
		try {
			if(null!=super.execute())
				return super.execute();
			
			getSession();
			
			if(StringUtil.checkNullAndEmpty(subscriberid,companyid,branchid,number_p,reportid)){
				List<?> dataList = genericDAO.getDataFromQuery(returnSqlQuery(5));
				
				if(null!=dataList && !dataList.isEmpty()){
					Object[] data = (Object[])dataList.get(0);
					String reportfilename = setIfNull(data[0], "");
					String destinationdirpath = setIfNull(data[1], "");
					String generatedfilename = setIfNull(data[2], "");
					if(StringUtil.checkNullAndEmpty(reportfilename,destinationdirpath,generatedfilename)){
						
						String realPath = request.getServletContext().getRealPath("/");
						long txnnumber = EfiveUtils.getTxnnumberms();
						String dbpathdata = realPath.replaceAll("\\\\","/")+destinationdirpath;
						
						File theDir = new File(dbpathdata);
						
						if (!theDir.exists()) {
							theDir.mkdirs();
						}
						
						SessionImpl sessionImpl = (SessionImpl) hibernateSession;
				        Connection connection = sessionImpl.connection();
						
						Map<String,Object> parameters = new HashMap<>();
						parameters.put("subno_p", Double.parseDouble(subscriberid));
						parameters.put("number_p", Double.parseDouble(number_p));
						
						fileName = generatedfilename+"_"+txnnumber+".pdf";
						String filePath = dbpathdata+"/"+fileName;
						
						JasperPrint jrPrint = 
						JasperFillManager.fillReport(getClass().getResourceAsStream(reportfilename),
								parameters,connection);
						JasperExportManager.exportReportToPdfFile(jrPrint,filePath);
						
						reportinputStream = new FileInputStream(new File(filePath));
						
						System.out.println("Successfully...!!!");
						
					}else{
						return "welcome";
					}
				}else{
					return "welcome";
				}
			}else{
				return "welcome";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success_report";
	}
	
	public String getFormtype() {
		return formtype;
	}

	public void setFormtype(String formtype) {
		this.formtype = formtype;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getNumber_p() {
		return number_p;
	}

	public void setNumber_p(String number_p) {
		this.number_p = number_p;
	}

	public String getReportid() {
		return reportid;
	}

	public void setReportid(String reportid) {
		this.reportid = reportid;
	}

	public InputStream getReportinputStream() {
		return reportinputStream;
	}

	public void setReportinputStream(InputStream reportinputStream) {
		this.reportinputStream = reportinputStream;
	}
	
	
	
	
}
