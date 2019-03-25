package com.xl.basic.bean;

/**
 * 处理出现错误的数据
 * @Author:lww
 * @Date:14:14 2017/11/18
 */
public class ArticleErrorBean {
	private int mediatypeint;
	private String title;
	private String htmlPage;
	private String url;
	private String reason;
	private long aid;

	public int getMediatypeint() {
		return mediatypeint;
	}

	public void setMediatypeint(int mediatypeint) {
		this.mediatypeint = mediatypeint;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtmlPage() {
		return htmlPage;
	}

	public void setHtmlPage(String htmlPage) {
		this.htmlPage = htmlPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}
}
