package com.xl.manage.bean;

public class DBean {
	private String dispTime;
    private Integer pubMonth;
    private Integer pubDay;
    private Integer pubHour;
    private String crawlTime;
    private String createDate;
    private String idate;
    private String aquerTime;

	public DBean() {
		this.dispTime = "0001-01-01 00:00:00";
		this.pubMonth = 0;
		this.pubDay = 0;
		this.pubHour = 0;
		this.crawlTime = "0001-01-01 00:00:00";
		this.createDate = "0001-01-01 00:00:00";
		this.idate = "0001-01-01 00:00:00";
		this.aquerTime = "0001-01-01 00:00:00";
	}

	public DBean(String dispTime, Integer pubMonth, Integer pubDay, Integer pubHour, String crawlTime, String createDate, String idate, String aquerTime) {
		this.dispTime = dispTime;
		this.pubMonth = pubMonth;
		this.pubDay = pubDay;
		this.pubHour = pubHour;
		this.crawlTime = crawlTime;
		this.createDate = createDate;
		this.idate = idate;
		this.aquerTime = aquerTime;
	}

	@Override
	public String toString() {
		return "DOldBean{" +
				"dispTime='" + dispTime + '\'' +
				", pubMonth=" + pubMonth +
				", pubDay=" + pubDay +
				", pubHour=" + pubHour +
				", crawlTime='" + crawlTime + '\'' +
				", createDate='" + createDate + '\'' +
				", idate='" + idate + '\'' +
				", aquerTime='" + aquerTime + '\'' +
				'}';
	}

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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
