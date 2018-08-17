package com.efive.agencyonline.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
public class AjaxHibernateService extends HttpServlet {
	public AjaxHibernateService() {
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("in ajax get");
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		synchronized (this) {
			String queryString = request.getParameter("query");
			queryString = queryString.replace("|", "%").replace("@@", "%");
			StringTokenizer stringTokenizer = new StringTokenizer(queryString,
					"}{");
			List<Integer> listTokens = new ArrayList<Integer>();
			List<QueryParam> listQuery = new ArrayList<QueryParam>();
			while (stringTokenizer.hasMoreTokens()) {

				String token = stringTokenizer.nextToken();
				int index = token.indexOf(">");
				if (index > -1) {
					listTokens.add(listQuery.size());
					token = token.replace(">", "");
				}

				listQuery.add(new QueryParam(token));
			}

			try {
				GenericDAO genericDAO = GenericDAO.getInstance();
				StringBuffer sFileContent = new StringBuffer(
						"<?xml version='1.0'?>\n");
				sFileContent.append("<HEADER>\n");
				int nodeCount = 0;
				for (int count = 0; count < listQuery.size(); count++, nodeCount++) {

					QueryParam queryParam = listQuery.get(count);

					if (listTokens.size() > 0 && listTokens.contains(count)) {

						List<BigDecimal> dataList = genericDAO
								.getNammedQueryData(queryParam.getQueryName(),
										queryParam.getParams());
						if (dataList.get(0).intValue() == 0) {
							queryParam = listQuery.get(count + 1);
						} else {
							queryParam = listQuery.get(count + 2);

						}
						count += 2;
					}
					List<Object[]> dataList = genericDAO.getNammedQueryData(
							queryParam.getQueryName(), queryParam.getParams());
					String field = "";
					for (int cnt = 0; cnt < dataList.size(); cnt++) {
						sFileContent.append("<DETAIL" + nodeCount + ">\n");
						Object objData = dataList.get(cnt);
						 if (objData instanceof String) {
							String strData = (String) objData;
							field = "FIELD" + 1;
							sFileContent.append("<" + field + ">");

							String value = strData;
							value=value.replace("¿", "");
							if (value.contains("&")) {
								value = value.replace("&", "&amp;");
							}
							sFileContent.append(value + "</" + field + ">\n");
							field = "FIELD" + 2;
							sFileContent.append("<" + field + ">");

							sFileContent.append(value + "</" + field + ">\n");

						} else {
							Object[] objDataArray = (Object[]) objData;
							for (int i = 0; i < objDataArray.length; i++) {
								field = "FIELD" + (i + 1);
								sFileContent.append("<" + field + ">");

								String value = objDataArray[i].toString();
								value=value.replace("", "");
								if (value.contains("&")) {
									value = value.replace("&", "&amp;");
								}
								sFileContent.append(value + "</" + field
										+ ">\n");
							}
						}
						sFileContent.append("</DETAIL" + nodeCount + ">\n");
					}

				}
				sFileContent.append("</HEADER>\n");
				PrintWriter out = response.getWriter();
				out.write(sFileContent.toString());
				out.close();

				//System.out.println(sFileContent.toString());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in ajax get");
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
