package com.xl.manage.bean;

/**
 * 缩略图入库对象
 * @Author:lww
 * @Date:14:18 2018/3/28
 */
public class ThumbnailBean {

	private String type;
	private String aid;
	private String url;//缩略图路径
	private String artUrl;//文章链接
	private String mediaName;//媒体名称
	private String dispTime;//发布时间
	private String createTime;//创建时间

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

	public String getArtUrl() {
		return artUrl;
	}

	public void setArtUrl(String artUrl) {
		this.artUrl = artUrl;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getDispTime() {
		return dispTime;
	}

	public void setDispTime(String dispTime) {
		this.dispTime = dispTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
