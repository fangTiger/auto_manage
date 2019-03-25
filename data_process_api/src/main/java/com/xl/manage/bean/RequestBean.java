package com.xl.manage.bean;

/**
 * @Author:lww
 * @Date:10:30 2018/3/23
 */
public class RequestBean {

	private String aid;//任务数据ID
	private String url;//任务url
	private String oldUrl;//原链接地址
	private String artUrl;//文章链接
	private String dispTime;//发布时间
	private String mediaName;//媒体名称
	private String type;//数据类型
	private String crearTime;

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOldUrl() {
		return oldUrl;
	}

	public void setOldUrl(String oldUrl) {
		this.oldUrl = oldUrl;
	}

	public String getArtUrl() {
		return artUrl;
	}

	public void setArtUrl(String artUrl) {
		this.artUrl = artUrl;
	}

	public String getDispTime() {
		return dispTime;
	}

	public void setDispTime(String dispTime) {
		this.dispTime = dispTime;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCrearTime() {
		return crearTime;
	}

	public void setCrearTime(String crearTime) {
		this.crearTime = crearTime;
	}
}
