package com.efive.agencyonline.common;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class AuthenticationInterceptor implements Interceptor {

	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}
	
	
    @Override
	public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> session = invocation.getInvocationContext().getSession();
        
       

        if(null==session.get("userid")){
        	return "welcome";
        }
        
        String loginId = session.get("userid").toString();

        if (loginId == null) 
        {
                return "welcome";
        } 
        return invocation.invoke();
}
	

	

//	@Override
/*	public String intercept(ActionInvocation actionInvocation) throws Exception {
		Map session = actionInvocation.getInvocationContext().getSession();
		Map params = actionInvocation.getInvocationContext().getParameters();
		
		System.out.println("session====>"+session);
		System.out.println("session.get(userid).toString()===>"+session.get("userid").toString());
		
		String loginid=session.get("userid").toString();
		System.out.println("dfsfsdf  loginid===>"+loginid);

		//String loginid=session.get("userid").toString();
		if (null == loginid || loginid.length()==0){
			return "welcome";	}
		
		return actionInvocation.invoke();
	}*/
	
	
	

}
