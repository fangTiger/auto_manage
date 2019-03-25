/*  
 * @(#) SubjectArticleBean.java Create on 2017-9-6 下午1:11:22   
 *   
 * Copyright 2017 by xl.   
 */


package com.xl.manage.bean.autoBean;

/**
 * 
 * @author liweiwei
 * @date   2017-9-6
 */
public class SubjectArticleBean {
	private Integer id;
	private Long aid;
	private Integer sourceType;//文章类型(1.网络新闻-web 2.报刊-Press 3.论坛-BBS 8.微博-weibo 15.APP-APP 16.微信-Weixin)
	private Integer subjectId;
	private Integer markRead;//已读未读(0：未读 1：已读)
	private String sig;//语义指纹【联索】
	private String sigAll;//语义指纹【联索】
	private Long indexSig;//语义指纹【联索】
	private Long indexSigAll;//语义指纹【联索】
	private String disptime;//发布时间
	private String targetRuler;//匹配规则(Source:来源规则|text:关键词规则)
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
	public Long getAid() {
		return aid;
	}
	public void setAid(Long aid) {
		this.aid = aid;
	}
	public Integer getSourceType() {
		return sourceType;
	}
	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}
	public Integer getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	public Integer getMarkRead() {
		return markRead;
	}
	public void setMarkRead(Integer markRead) {
		this.markRead = markRead;
	}
	public String getSig() {
		return sig;
	}
	public void setSig(String sig) {
		this.sig = sig;
	}
	public String getSigAll() {
		return sigAll;
	}
	public void setSigAll(String sigAll) {
		this.sigAll = sigAll;
	}
	public Long getIndexSig() {
		return indexSig;
	}
	public void setIndexSig(Long indexSig) {
		this.indexSig = indexSig;
	}
	public Long getIndexSigAll() {
		return indexSigAll;
	}
	public void setIndexSigAll(Long indexSigAll) {
		this.indexSigAll = indexSigAll;
	}
	public String getDisptime() {
		return disptime;
	}
	public void setDisptime(String disptime) {
		this.disptime = disptime;
	}
	public String getTargetRuler() {
		return targetRuler;
	}
	public void setTargetRuler(String targetRuler) {
		this.targetRuler = targetRuler;
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
