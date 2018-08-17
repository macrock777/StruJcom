package com.efive.agencyonline.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pareshj
 * 
 * 
 * Preferences - Java - Code Style - Code Templates
 */
public class Ajaxsample extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Ajaxsample() {
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response){
		//			throws ServletException, IOException {
		//		 String country=request.getParameter("countryname");
		//		System.out.println("in ajax "+country);
		//		  Map<String, String> ind = new LinkedHashMap<String, String>();
		//		     ind.put("1", "New delhi");
		//		     ind.put("2", "Tamil Nadu");
		//		     ind.put("3", "Kerala");
		//		     ind.put("4", "Andhra Pradesh");
		//		     
		//		     Map<String, String> us = new LinkedHashMap<String, String>();
		//		     us.put("1", "Washington");
		//		     us.put("2", "California");
		//		     us.put("3", "Florida");
		//		     us.put("4", "New York");
		//		     String json = null ;
		////		     if(country.equals("India")){
		////		      json= new Gson().toJson(ind);   
		////		     }
		////		     else if(country.equals("US")){
		////		      json=new Gson().toJson(us);  
		////		     }
		//		     Writer writer = new StringWriter();
		//			  JsonGenerator jsonGenerator = null;
		//			try {
		//				jsonGenerator = new JsonFactory().
		//				        createJsonGenerator(writer);
		//			} catch (IOException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//			  ObjectMapper mapper = new ObjectMapper();
		//			  try {
		//				mapper.writeValue(jsonGenerator, ind);
		//			} catch (JsonGenerationException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			} catch (JsonMappingException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			} catch (IOException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//			  try {
		//				jsonGenerator.close();
		//			} catch (IOException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//			  json=writer.toString();
		//			  System.out.println(writer.toString());
		//		     response.setContentType("application/json");
		//		     response.setCharacterEncoding("UTF-8");
		//		     response.getWriter().write(json);  
				PrintWriter out=null;
		    try {
		    	response.setContentType("text/html;charset=UTF-8");
		         out = response.getWriter();
		        String category =request.getParameter("category");
		        System.out.println("selRegion=="+category);
		
		        
		                out.print(
		                    "<option value='1'>Product Name 1 For Category 2</option>" +
		                    "<option value='2'>Product Name 2 For Category 2</option>" +
		                    "<option value='3'>Product Name 3 For Category 2</option>");
		       
		    }  catch (Exception ex) {
		        out.print("Error getting product name..." + ex.toString());
		    }
		    finally {
		        out.close();
		    }

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		GenericDAO genericDAO = GenericDAO.getInstance();

		String queryString = request.getParameter("query");
		System.out.println(queryString);
		List<Object[]> dataList = genericDAO.getDataFromQuery(queryString);
		StringBuffer sFileContent = new StringBuffer("<?xml version='1.0'?>\n");
		sFileContent.append("<HEADER>\n");
		String field = "";
		for (int cnt = 0; cnt < dataList.size(); cnt++) {
			sFileContent.append("<DETAIL>\n");
			Object objData = dataList.get(cnt);
			Object[] objDataArray = (Object[]) objData;
			for (int i = 0; i < objDataArray.length; i++) {
				field = "COLUMN" + (i + 1);
				sFileContent.append("<" + field + ">");

				String value = objDataArray[i].toString();
				if (value.contains("&")) {
					value = value.replace("&", "&amp;");
				}
				sFileContent.append(value + "</" + field + ">\n");
			}
			sFileContent.append("</DETAIL>\n");
		}

		sFileContent.append("</HEADER>\n");
		PrintWriter out = response.getWriter();
		out.write(sFileContent.toString());
		out.close();
		// System.out.println(sFileContent.toString());

	}
}
