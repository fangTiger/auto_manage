package com.xl.basic.bean;


public class Docpsimagebean {
//	docpsimagebean---------
	private long aid;
	private String mediaType;
	private int serverID ;
	private float width ;
	private float height ;
	private int picSn ;
	private String imagePath ;
	private String dispTime ;
	private int type ;
	private String createTime;

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public int getServerID() {
		return serverID;
	}
	public void setServerID(int serverID) {
		this.serverID = serverID;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public int getPicSn() {
		return picSn;
	}
	public void setPicSn(int picSn) {
		this.picSn = picSn;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getDispTime() {
		return dispTime;
	}
	public void setDispTime(String dispTime) {
		this.dispTime = dispTime;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
