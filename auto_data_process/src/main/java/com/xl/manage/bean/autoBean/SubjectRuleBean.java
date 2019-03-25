/*  
 * @(#) SubjectRuleBean.java Create on 2017-9-6 下午1:32:02   
 *   
 * Copyright 2017 by xl.   
 */


package com.xl.manage.bean.autoBean;

/**
 * 
 * @author liweiwei
 * @date   2017-9-6
 */
public class SubjectRuleBean {

	private Integer id;
	private Integer subjectId;//主题分类Id
	private String sourceRule;//信息源规则
	private String textRule;//内容规则
	private Integer enableFlag;//启用标志位(默认为0关闭，1为启用)
	private Integer createUser;
	private String createTime;
	private Integer deleteFlag;
	private String deleteTime;
	private Integer deleteUser;

	private String _hitWord;//命中词

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getSourceRule() {
		return sourceRule;
	}
	public void setSourceRule(String sourceRule) {
		this.sourceRule = sourceRule;
	}
	public String getTextRule() {
		return textRule;
	}
	public void setTextRule(String textRule) {
		this.textRule = textRule;
	}
	public Integer getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(Integer enableFlag) {
		this.enableFlag = enableFlag;
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

	public String get_hitWord() {
		return _hitWord;
	}

	public void set_hitWord(String _hitWord) {
		this._hitWord = _hitWord;
	}
}
