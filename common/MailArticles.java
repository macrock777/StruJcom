package com.efive.agencyonline.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MailArticles {

	String clientid, headlines, publicationname, articledate, uploadcity,
			journalist, haspdf, hashtml, companyname, articlesummary,
			pubgroupname, pubcategory, hassummary, companyid, articlebody,
			pagenumber, state, language, tags,link,otherpubs;
	

	long articleid, emailpriority, mailsectionid, tone;
	BigDecimal space, height, width;
	public List<String> companyList = new ArrayList<String>();

	public String getOtherpubs() {
		return otherpubs;
	}

	public void setOtherpubs(String otherpubs) {
		this.otherpubs = otherpubs;
	}
	
	public String getHassummary() {
		return hassummary;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setHassummary(String hassummary) {
		this.hassummary = hassummary;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getSpace() {
		return space;
	}

	public void setSpace(BigDecimal space) {
		this.space = space;
	}

	public String getArticlesummary() {
		return articlesummary;
	}

	public void setArticlesummary(String articlesummary) {
		this.articlesummary = articlesummary;
	}

	public String getClientid() {
		return clientid;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getHeadlines() {
		return headlines;
	}

	public void setHeadlines(String headlines) {
		this.headlines = headlines;
	}

	public String getPublicationname() {
		return publicationname;
	}

	public void setPublicationname(String publicationname) {
		this.publicationname = publicationname;
	}

	public String getArticledate() {
		return articledate;
	}

	public void setArticledate(String articledate) {
		this.articledate = articledate;
	}

	public String getUploadcity() {
		return uploadcity;
	}

	public void setUploadcity(String uploadcity) {
		this.uploadcity = uploadcity;
	}

	public String getJournalist() {
		return journalist;
	}

	public void setJournalist(String journalist) {
		this.journalist = journalist;
	}

	public String getHaspdf() {
		return haspdf;
	}

	public void setHaspdf(String haspdf) {
		this.haspdf = haspdf;
	}

	public String getHashtml() {
		return hashtml;
	}

	public void setHashtml(String hashtml) {
		this.hashtml = hashtml;
	}

	public long getArticleid() {
		return articleid;
	}

	public void setArticleid(long articleid) {
		this.articleid = articleid;
	}

	public long getEmailpriority() {
		return emailpriority;
	}

	public void setEmailpriority(long emailpriority) {
		this.emailpriority = emailpriority;
	}

	public List<String> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<String> companyList) {
		this.companyList = companyList;
	}

	@Override
	public String toString() {
		String str = clientid + "~" + articledate + "~" + headlines + "~"
				+ journalist + "~" + pagenumber + "\n";
		for (int i = 0; i < companyList.size(); i++) {
			str += companyList.get(i) + "\n";
		}

		return str;
	}

	public String getPubgroupname() {
		return pubgroupname;
	}

	public void setPubgroupname(String pubgroupname) {
		this.pubgroupname = pubgroupname;
	}

	public String getPubcategory() {
		return pubcategory;
	}

	public void setPubcategory(String pubcategory) {
		this.pubcategory = pubcategory;
	}

	public String getCompanyid() {
		return companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	public long getMailsectionid() {
		return mailsectionid;
	}

	public void setMailsectionid(long mailsectionid) {
		this.mailsectionid = mailsectionid;
	}

	public String getArticlebody() {
		return articlebody;
	}

	public void setArticlebody(String articlebody) {
		this.articlebody = articlebody;
	}

	public void setPagenumber(String pagenumber) {
		this.pagenumber = pagenumber;
	}

	public String getPagenumber() {
		return pagenumber;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public long getTone() {
		return tone;
	}

	public void setTone(long tone) {
		this.tone = tone;
	}
}
