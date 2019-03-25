/*  
 * @(#) EventItemBean.java Create on 2017-9-6 下午1:10:38   
 *   
 * Copyright 2017 by xl.   
 */


package com.xl.manage.bean.autoBean;

/**
 * 
 * @author liweiwei
 * @date   2017-9-6
 */
public class EventItemBean {
	private Integer id;
	private String eventName;//活动名称
	private String alias;//别名
	private Integer fatherId;//父级ID
	private Integer parentId;//根级ID
	private Integer layer;//层级（0顶级）
	private Integer createUser;
	private String createTime;
	private Integer deleteFlag;
	private String deleteTime;
	private Integer deleteUser;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Integer getFatherId() {
		return fatherId;
	}
	public void setFatherId(Integer fatherId) {
		this.fatherId = fatherId;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	public Integer getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getDeleteTime() {
		return deleteTime;
	}
	public void setDeleteTime(String deleteTime) {
		this.deleteTime = deleteTime;
	}
	public Integer getDeleteUser() {
		return deleteUser;
	}
	public void setDeleteUser(Integer deleteUser) {
		this.deleteUser = deleteUser;
	}
	
	
}
