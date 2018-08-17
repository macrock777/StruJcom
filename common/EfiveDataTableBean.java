package com.efive.agencyonline.common;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class EfiveDataTableBean extends EfiveAction{
	
		public  String strQuery;
		public String fromBlock;
		public String whereClause;
		public String countQuery;
		
		public  List tableData;
		
	//DataTable ajax request Handling Parameter
		public String iDisplayStart;		//sStart
		public String iDisplayLength;		//sAmount
		public String sEcho;
		public String iSortCol_0;		//sCol sort column no
		public String sSortDir_0;		//sdir ordering asending 
		public String sSearch;
	
	//Column Name which is Display in DataTable
		public String[] cols ;						//{"sh.subscriberid", "sh.subscribername", "sh.contactperson",  "rss.fromdate","rss.todate","sh.subscriberid", "sh.active","sh.subscriberid"};
		public String dir = "asc";
	
	// Calcute   records   &  Pages  & search etc ... 
		public int totalAfterFilter =0;
		public int amount = 10;			// Default  Display Records
		public int start;
		public int echo;	
		public int col ;
		
		public JSONObject result = new JSONObject();
		public JSONArray array = new JSONArray();
		
		
		public String getStrQuery() {
			return strQuery;
		}
		public void setStrQuery(String strQuery) {
			this.strQuery = strQuery;
		}
		public String getFromBlock() {
			return fromBlock;
		}
		public void setFromBlock(String fromBlock) {
			this.fromBlock = fromBlock;
		}
		public String getWhereClause() {
			return whereClause;
		}
		public void setWhereClause(String whereClause) {
			this.whereClause = whereClause;
		}
		public String getCountQuery() {
			return countQuery;
		}
		public void setCountQuery(String countQuery) {
			this.countQuery = countQuery;
		}
		public List getTableData() {
			return tableData;
		}
		public void setTableData(List tableData) {
			this.tableData = tableData;
		}
		public String getiDisplayStart() {
			return iDisplayStart;
		}
		public void setiDisplayStart(String iDisplayStart) {
			this.iDisplayStart = iDisplayStart;
		}
		public String getiDisplayLength() {
			return iDisplayLength;
		}
		public void setiDisplayLength(String iDisplayLength) {
			this.iDisplayLength = iDisplayLength;
		}
		public String getsEcho() {
			return sEcho;
		}
		public void setsEcho(String sEcho) {
			this.sEcho = sEcho;
		}
		public String getiSortCol_0() {
			return iSortCol_0;
		}
		public void setiSortCol_0(String iSortCol_0) {
			this.iSortCol_0 = iSortCol_0;
		}
		public String getsSortDir_0() {
			return sSortDir_0;
		}
		public void setsSortDir_0(String sSortDir_0) {
			this.sSortDir_0 = sSortDir_0;
		}
		public String getsSearch() {
			return sSearch;
		}
		
		public void setsSearch(String sSearch) {
			this.sSearch = sSearch;
		}
		public String[] getCols() {
			return cols;
		}
		public void setCols(String[] cols) {
			this.cols = cols;
		}
		public String getDir() {
			return dir;
		}
		public void setDir(String dir) {
			this.dir = dir;
		}
		public int getTotalAfterFilter() {
			return totalAfterFilter;
		}
		public void setTotalAfterFilter(int totalAfterFilter) {
			this.totalAfterFilter = totalAfterFilter;
		}
		public int getAmount() {
			return amount;
		}
		public void setAmount(int amount) {
			this.amount = amount;
		}
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getEcho() {
			return echo;
		}
		public void setEcho(int echo) {
			this.echo = echo;
		}
		public int getCol() {
			return col;
		}
		public void setCol(int col) {
			this.col = col;
		}
		public JSONObject getResult() {
			return result;
		}
		public void setResult(JSONObject result) {
			this.result = result;
		}
		public JSONArray getArray() {
			return array;
		}
		public void setArray(JSONArray array) {
			this.array = array;
		}
		
		
		
		
// Code....
	public void calculateDataTableVar(){				// like paging ,  No of  rec. ,    
			 try{
						if(iDisplayStart != null){
							start = Integer.parseInt(iDisplayStart);
							if(start<0)
								start = 0;
						}
						if (iDisplayLength != null) {
							amount = Integer.parseInt(iDisplayLength);
						    if (amount < 10 || amount > 100)
						            amount = 10;
						}
						if (sEcho != null) {
							echo = Integer.parseInt(sEcho);
						}
						if (iSortCol_0 != null) {
							col = Integer.parseInt(iSortCol_0);
						    if (col < 0 || col >	cols.length ) //no of column in dataTable
						    	col = 0;
						}
						if (sSortDir_0 != null) {
							if (!sSortDir_0.equals("asc"))
							    dir = "desc";
						}
				 }catch(Exception ex){
					// PostgresqlUtil.saveError(ex, "agencyonline");
					 ex.printStackTrace();
				 }
		 }
		
	
	

}
