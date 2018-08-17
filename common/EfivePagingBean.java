package com.efive.agencyonline.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EfivePagingBean extends EfiveAction {

	protected List<String[]> cmbSerachList;

	protected List<String[]> activeList;

	protected List<String[]> cmbPages = new ArrayList<String[]>();

	protected String[] tableheaders;

	protected List tableData;

	protected String cmbWith;

	protected String cmbSearch;

	protected String searchText;

	protected static int displayRecords;

	protected int totalRecords;

	protected static int currentPage;

	protected int totalPages;

	protected int fromRecord;

	protected int toRecord;

	protected int cmbPage;

	protected int buttonSize;

	protected int sortField;

	protected String orderBy;

	protected String isduplicate;

	private String linkid;

	protected String addright;

	protected String editright;

	protected String deleteright;

	protected String viewright;

	protected int starting, ending;

	public int getStarting() {
		return starting;
	}

	public void setStarting(int starting) {
		this.starting = starting;
	}

	public int getEnding() {
		return ending;
	}

	public void setEnding(int ending) {
		this.ending = ending;
	}

	public int getButtonSize() {
		return buttonSize;
	}

	public void setButtonSize(int buttonSize) {
		this.buttonSize = buttonSize;
	}

	public String getAddright() {
		return addright;
	}

	public void setAddright(String addright) {
		this.addright = addright;
	}

	public String getDeleteright() {
		return deleteright;
	}

	public void setDeleteright(String deleteright) {
		this.deleteright = deleteright;
	}

	public String getEditright() {
		return editright;
	}

	public void setEditright(String editright) {
		this.editright = editright;
	}

	public String getViewright() {
		return viewright;
	}

	public void setViewright(String viewright) {
		this.viewright = viewright;
	}

	public String getLinkid() {
		return linkid;
	}

	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}

	public String getIsduplicate() {
		return isduplicate;
	}

	public void setIsduplicate(String isduplicate) {
		this.isduplicate = isduplicate;
	}

	public String getOrderBy() {
		if (null == orderBy || orderBy.length() == 0)
			orderBy = "asc";
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getSortField() {
		return sortField;
	}

	public void setSortField(int sortField) {
		this.sortField = sortField;
	}

	public int getCmbPage() {
		return cmbPage;
	}

	public void setCmbPage(int cmbPage) {
		this.cmbPage = cmbPage;
	}

	public List<String[]> getCmbPages() {
		return cmbPages;
	}

	public void setCmbPages(List<String[]> cmbPages) {
		this.cmbPages = cmbPages;
	}

	public EfivePagingBean() {
		activeList = new ArrayList<String[]>();
		activeList.add(new String[] { "Y", "Yes" });
		activeList.add(new String[] { "N", "No" });

	}

	public String getCmbSearch() {
		return cmbSearch;
	}

	public void setCmbSearch(String cmbSearch) {
		this.cmbSearch = cmbSearch;
	}

	public List<String[]> getCmbSerachList() {
		return cmbSerachList;
	}

	public void setCmbSerachList(List<String[]> cmbSerachList) {
		this.cmbSerachList = cmbSerachList;
	}

	public String getCmbWith() {
		return cmbWith;
	}

	public void setCmbWith(String cmbWith) {
		this.cmbWith = cmbWith;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		EfivePagingBean.currentPage = currentPage;
		//request.getSession().setAttribute("starting", currentPage);
		//request.getSession().setAttribute("ending", currentPage);
	}

	public int getDisplayRecords() {
		return displayRecords;
	}

	public void setDisplayRecords(int displayRecords) {
		EfivePagingBean.displayRecords = displayRecords;
	}

	public int getFromRecord() {
		return fromRecord;
	}

	public void setFromRecord(int fromRecord) {
		this.fromRecord = fromRecord;
	}

	public String[] getTableheaders() {
		return tableheaders;
	}

	public void setTableheaders(String[] tableheaders) {
		this.tableheaders = tableheaders;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public List getTableData() {
		return tableData;
	}

	public void setTableData(List tableData) {
		this.tableData = tableData;
	}

	public int getToRecord() {
		return toRecord;
	}

	public void setToRecord(int toRecord) {
		this.toRecord = toRecord;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	public void calculatePageStatus() {
		  //System.out.println(currentPage + "              popat");
			totalRecords = null == tableData ? 0 : tableData.size();
			if (displayRecords == 0)
					displayRecords = 10;				
			
				if(displayRecords!=10){	
				displayRecords =getDisplayRecords();
			 }
			
			else if(totalRecords>10){			
				//displayRecords =10;
				displayRecords =10;				
			}
			
		int nopage = totalRecords / displayRecords;
		double pages = ((double) totalRecords) / displayRecords;
		if (pages - nopage > 0)
			nopage++;
//		System.out.println("fromrecord : "+fromRecord);
//		System.out.println("Currentpage : "+currentPage);
		
		fromRecord = (currentPage - 1) * displayRecords + 1;
		if (displayRecords > totalRecords)
			fromRecord = 1;
		toRecord = fromRecord + displayRecords - 1;
		totalPages = nopage;
		if (totalPages == 1)
			currentPage = 1;
		if (totalPages > 1) {
			cmbPages.clear();
			for (int i = 1; i <= totalPages; i++) {
				/*
				 * cmbPages.add(new String[]{"" + i, " Page " + i + " of " +
				 * nopage});
				 */
				cmbPages.add(new String[] { "" + i, "" + i });
			}		
		}
	//	System.out.println("starting-ending 0  " + starting + "   " + ending);
		
		if (totalPages > 10 && (null!=request.getSession().getAttribute("starting") && null!=request.getSession()	.getAttribute("ending").toString())) {
				int mod = currentPage % 10;
				starting = currentPage - mod;
				int tstarting = Integer.parseInt(request.getSession().getAttribute("starting").toString());
				ending = Integer.parseInt(request.getSession().getAttribute("ending").toString());
				// if(starting != tstarting){

			System.out.println(starting + mod + "     =  " + totalPages);
			if (currentPage == (ending + 1) || (starting + mod) == totalPages) {

				/*
				 * }else{ starting =
				 * Integer.parseInt(request.getSession().getAttribute
				 * ("starting").toString()); ending =
				 * Integer.parseInt(request.getSession
				 * ().getAttribute("ending").toString()); }
				 */
				System.out.println(starting + "   " + tstarting);

				starting = Integer.parseInt(request.getSession().getAttribute("starting").toString());
				ending = Integer.parseInt(request.getSession().getAttribute("ending").toString());
				System.out.println(starting + "  ****  " + tstarting);
			} else {
				if ((starting + mod) >= totalPages)
					ending = mod;
				else {
					ending = starting + 9;
				}
			}
			if (currentPage == totalPages) {
				starting = currentPage - mod;
				ending = currentPage;
			}
			System.out.println("starting-ending 1  " + starting + "   "+ ending);
			// ending = starting + mod;
		} else {
			starting = 0;
			ending = totalPages;
			if(null!=request.getSession().getAttribute("starting") && null!=request.getSession().getAttribute("ending").toString()){
				ending = 10;
			}
			if(ending>10){
				ending = 9;
			}
		}
			if(currentPage == starting && starting!=0){
				starting = starting-10;
				
				if(starting != (totalPages-10))
					ending = ending-10;
			}
			System.out.println(starting + "  ######  " + starting);
			request.getSession().setAttribute("starting", starting);
			request.getSession().setAttribute("ending", ending);
			// buttonSize = 10;
			cmbPage = currentPage;
	}

	public static void copyData(EfivePagingBean sourceForm) {
		displayRecords = sourceForm.getDisplayRecords();
		currentPage = sourceForm.getCurrentPage();
	}

	public List<String[]> getActiveList() {
		return activeList;
	}

	public void setActiveList(List<String[]> activeList) {
		this.activeList = activeList;
	}

	public String sortData() {
		if (null != super.execute())
			return super.execute();
		System.out.println("order by "+getOrderBy());
		System.out.println("SortField : "+sortField);
		int orderby = -1;
		if (getOrderBy().equals("asc")) {
			orderby = 1;
		}
		EfiveListComparator mudraListComparator = new EfiveListComparator(getSortField(), orderby);
		EfivePagingBean efivePagingBean = (EfivePagingBean) request.getSession().getAttribute("mainform");	
		tableData =efivePagingBean.tableData;
		 Collections.sort(tableData, mudraListComparator);	 
		copyData(efivePagingBean);
		calculatePageStatus();
		return "input";
	}
}