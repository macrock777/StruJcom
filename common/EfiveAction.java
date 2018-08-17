package com.efive.agencyonline.common;

import javax.servlet.http.HttpSession;

public class EfiveAction extends CommonAction {

	@Override
	public String execute() {
		HttpSession session = request.getSession();
		if (session.isNew()) {
			return "welcome";
		}
		return null;
	}

}
