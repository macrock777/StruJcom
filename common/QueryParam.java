package com.efive.agencyonline.common;

import java.util.HashMap;
import java.util.StringTokenizer;

public class QueryParam {

	private String queryName = "";

	private HashMap<String, Object> params = null;

	public QueryParam(String queryparmas) {
		int index=queryparmas.indexOf("@");
		if (index == -1) {
			queryName = queryparmas;
			return;
		}
		queryName=queryparmas.substring(0,index);
		String queryParam=queryparmas.substring(index+1);
		StringTokenizer stringTokenizer=new StringTokenizer(queryParam,"~");
		params=new HashMap<String, Object>();
		
		while(stringTokenizer.hasMoreTokens())
		{
			params.put(stringTokenizer.nextToken(), stringTokenizer.nextToken());
		}
	}

	public HashMap<String, Object> getParams() {
		return params;
	}

	public String getQueryName() {
		return queryName;
	}

}
