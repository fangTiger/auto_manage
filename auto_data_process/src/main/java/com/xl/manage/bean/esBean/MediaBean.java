package com.xl.manage.bean.esBean;

import java.util.List;

public class MediaBean {

	private String mediaId;
	private String mediaType;
	private String mediaNameCn;
	private String mediaNameEn;
	private AreaBean mediaArea;
	private List<String> mediaCategory;
	private String site;
	private String domain;

	public MediaBean() {
	}

	public MediaBean(String mediaId, String mediaType, String mediaNameCn, String mediaNameEn, AreaBean mediaArea, List<String> mediaCategory, String site, String domain) {
		this.mediaId = mediaId;
		this.mediaType = mediaType;
		this.mediaNameCn = mediaNameCn;
		this.mediaNameEn = mediaNameEn;
		this.mediaArea = mediaArea;
		this.mediaCategory = mediaCategory;
		this.site = site;
		this.domain = domain;
	}


	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaNameCn() {
		return mediaNameCn;
	}

	public void setMediaNameCn(String mediaNameCn) {
		this.mediaNameCn = mediaNameCn;
	}

	public String getMediaNameEn() {
		return mediaNameEn;
	}

	public void setMediaNameEn(String mediaNameEn) {
		this.mediaNameEn = mediaNameEn;
	}

	public AreaBean getMediaArea() {
		return mediaArea;
	}

	public void setMediaArea(AreaBean mediaArea) {
		this.mediaArea = mediaArea;
	}

	public List<String> getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(List<String> mediaCategory) {
		this.mediaCategory = mediaCategory;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String toString() {
		return "MediaBean{" +
				"mediaId='" + mediaId + '\'' +
				", mediaType='" + mediaType + '\'' +
				", mediaNameCn='" + mediaNameCn + '\'' +
				", mediaNameEn='" + mediaNameEn + '\'' +
				", mediaArea=" + mediaArea +
				", mediaCategory=" + mediaCategory +
				", site='" + site + '\'' +
				", domain='" + domain + '\'' +
				'}';
	}
}
