package com.efive.agencyonline.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

public class CommonAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {

	protected GenericDAO genericDAO = GenericDAO.getInstance();
	//protected Logger logger = Logger.getLogger(CommonAction.class);
	
	public HttpServletRequest request;
	public HttpServletResponse response;
	
	@Override
	public void setServletRequest(HttpServletRequest request) {

		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return request;
	}	

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpServletResponse getServletResponse() {
		return response;
	}


}
