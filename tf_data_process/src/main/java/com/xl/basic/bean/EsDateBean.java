/*  
 * @(#) EsDateBean.java Create on 2017-4-13 下午2:27:11   
 *   
 * Copyright 2017 by xl.   
 */


package com.xl.basic.bean;


/**
 * es时间实体
 * @author liweiwei
 * @date   2017-4-13
 */
public class EsDateBean {
	
	private String dispTime;//发布时间
	private Integer pubMonth;//发布月
	private Integer pubDay;//发布日
	private Integer pubHour;//发布小时
	private String crawlTime;//抓取时间
	private String idate;//入es时间
	private String aquerTime;//更新时间
	
	public String getDispTime() {
		return dispTime;
	}
	public void setDispTime(String dispTime) {
		this.dispTime = dispTime;
	}
	public Integer getPubMonth() {
		return pubMonth;
	}
	public void setPubMonth(Integer pubMonth) {
		this.pubMonth = pubMonth;
	}
	public Integer getPubDay() {
		return pubDay;
	}
	public void setPubDay(Integer pubDay) {
		this.pubDay = pubDay;
	}
	public Integer getPubHour() {
		return pubHour;
	}
	public void setPubHour(Integer pubHour) {
		this.pubHour = pubHour;
	}
	public String getCrawlTime() {
		return crawlTime;
	}
	public void setCrawlTime(String crawlTime) {
		this.crawlTime = crawlTime;
	}
	public String getIdate() {
		return idate;
	}
	public void setIdate(String idate) {
		this.idate = idate;
	}
	public String getAquerTime() {
		return aquerTime;
	}
	public void setAquerTime(String aquerTime) {
		this.aquerTime = aquerTime;
	}
}
