/*  
 * @(#) Bean1.java Create on 2017-10-19 下午2:23:55   
 *   
 * Copyright 2017 by xl.   
 */


package com.xl.basic.bean;

/**
 * 
 * @author xinlian
 * @date   2017-10-19
 */
public class Docpicbean {
//	docpicbean---------------
	private long aid;
	private int did;
	private int pid;
	private int fmt;
	private String text  ;
	private String url;
	private String path ;
	private String utm ;
	private String disptime ;
	public long getAid() {
		return aid;
	}
	public void setAid(long aid) {
		this.aid = aid;
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getFmt() {
		return fmt;
	}
	public void setFmt(int fmt) {
		this.fmt = fmt;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUtm() {
		return utm;
	}
	public void setUtm(String utm) {
		this.utm = utm;
	}
	public String getDisptime() {
		return disptime;
	}
	public void setDisptime(String disptime) {
		this.disptime = disptime;
	}
}
