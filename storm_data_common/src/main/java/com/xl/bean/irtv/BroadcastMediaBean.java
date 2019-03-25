package com.xl.bean.irtv;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lww
 * @Date:15:09 2017/9/13
 */
public class BroadcastMediaBean {
	private String mediaId;
	private String mediaType;
	private String mediaNameCn;
	private String mediaNameEn;
	private BroadcastAreaBean mediaArea;
	private List<String> mediaCategory;

	public BroadcastMediaBean() {
		this.mediaId = "";
		this.mediaType="";
		this.mediaNameCn="";
		this.mediaNameEn="";
		this.mediaArea = new BroadcastAreaBean();
		this.mediaCategory = new ArrayList<>();
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

	public BroadcastAreaBean getMediaArea() {
		return mediaArea;
	}

	public void setMediaArea(BroadcastAreaBean mediaArea) {
		this.mediaArea = mediaArea;
	}

	public List<String> getMediaCategory() {
		return mediaCategory;
	}

	public void setMediaCategory(List<String> mediaCategory) {
		this.mediaCategory = mediaCategory;
	}
}
