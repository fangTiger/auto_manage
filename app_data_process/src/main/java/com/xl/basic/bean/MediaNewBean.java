package com.xl.basic.bean;

/**
 * @Author:lww
 * @Date:15:56 2017/11/18
 */
public class MediaNewBean {

	private String mediaID;
	private String mediaType;
	private String mediaNameCN;
	private String mediaNameEN;
	private String mediaCategory;
	private String site;
	private String domain;
	private int pv;
	private float advPrice;
	//临时扩展的多个广告价值
	private String focusAdvPrice;//焦点图价值
	private String linkAdvPrice;//文字链价值
	private String programaAdvPrice;//视频栏目
	private String subjectAdvPrice;//专题价值
	private int uv;
	private int alexaRank;
	private String cityID;
	private String city;
	private String province;
	private String country;
	private SiteModelBean children;

	public String getMediaID() {
		return mediaID;
	}

	public void setMediaID(String mediaID) {
		this.mediaID = mediaID;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaNameCN() {
		return mediaNameCN;
	}

	public void setMediaNameCN(String mediaNameCN) {
		this.mediaNameCN = mediaNameCN;
	}

	public String getMediaNameEN() {
		return mediaNameEN;
	}

	public void setMediaNameEN(String mediaNameEN) {
		this.mediaNameEN = mediaNameEN;
	}

	public String getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(String mediaCategory) {
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

	public int getPv() {
		return pv;
	}

	public void setPv(int pv) {
		this.pv = pv;
	}

	public float getAdvPrice() {
		return advPrice;
	}

	public void setAdvPrice(float advPrice) {
		this.advPrice = advPrice;
	}

	public String getFocusAdvPrice() {
		return focusAdvPrice;
	}

	public void setFocusAdvPrice(String focusAdvPrice) {
		this.focusAdvPrice = focusAdvPrice;
	}

	public String getLinkAdvPrice() {
		return linkAdvPrice;
	}

	public void setLinkAdvPrice(String linkAdvPrice) {
		this.linkAdvPrice = linkAdvPrice;
	}

	public String getProgramaAdvPrice() {
		return programaAdvPrice;
	}

	public void setProgramaAdvPrice(String programaAdvPrice) {
		this.programaAdvPrice = programaAdvPrice;
	}

	public String getSubjectAdvPrice() {
		return subjectAdvPrice;
	}

	public void setSubjectAdvPrice(String subjectAdvPrice) {
		this.subjectAdvPrice = subjectAdvPrice;
	}

	public int getUv() {
		return uv;
	}

	public void setUv(int uv) {
		this.uv = uv;
	}

	public int getAlexaRank() {
		return alexaRank;
	}

	public void setAlexaRank(int alexaRank) {
		this.alexaRank = alexaRank;
	}

	public String getCityID() {
		return cityID;
	}

	public void setCityID(String cityID) {
		this.cityID = cityID;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public SiteModelBean getChildren() {
		return children;
	}

	public void setChildren(SiteModelBean children) {
		this.children = children;
	}
}
