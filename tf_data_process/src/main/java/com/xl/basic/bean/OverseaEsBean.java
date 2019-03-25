/*  
 * @(#) OverseaEsBean.java Create on 2017-4-13 下午2:02:48   
 *   
 * Copyright 2017 by xl.   
 */


package com.xl.basic.bean;


/**
 * es实体
 * @author liweiwei
 * @date   2017-4-13
 */
public class OverseaEsBean {
	
	private String type;//facebook或者twitter
	private String statusId;//文章消息ID
	private String statusText;//文章消息正文
	private Integer isRetweeted;//是否转发(0:否,1:是)
	private Integer commentCount;//评论数
	private Integer retweetedCount;//转发数
	private Integer attitudesCount;//点赞数
	private String textUrl;//文章url
	private String source;//消息发送设备
	private String inReplyToStatusId;//回复的文章ID
	private String inReplyToUserId;//回复人的ID
	private String lang;//语种
	private String reStatusID;//转发的文章ID
	private EsDateBean d;//时间对象
	private EsUserBean u;//用户对象

	public OverseaEsBean() {
		this.type = "";
		this.statusId = "";
		this.statusText = "";
		this.isRetweeted = 0;
		this.commentCount = 0;
		this.retweetedCount = 0;
		this.attitudesCount = 0;
		this.textUrl = "";
		this.source = "";
		this.inReplyToStatusId = "";
		this.inReplyToUserId = "";
		this.lang = "";
		this.reStatusID = "";
		this.d = new EsDateBean();
		this.u = new EsUserBean();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public Integer getIsRetweeted() {
		return isRetweeted;
	}

	public void setIsRetweeted(Integer isRetweeted) {
		this.isRetweeted = isRetweeted;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getRetweetedCount() {
		return retweetedCount;
	}

	public void setRetweetedCount(Integer retweetedCount) {
		this.retweetedCount = retweetedCount;
	}

	public Integer getAttitudesCount() {
		return attitudesCount;
	}

	public void setAttitudesCount(Integer attitudesCount) {
		this.attitudesCount = attitudesCount;
	}

	public String getTextUrl() {
		return textUrl;
	}

	public void setTextUrl(String textUrl) {
		this.textUrl = textUrl;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public void setInReplyToStatusId(String inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	public String getInReplyToUserId() {
		return inReplyToUserId;
	}

	public void setInReplyToUserId(String inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getReStatusID() {
		return reStatusID;
	}

	public void setReStatusID(String reStatusID) {
		this.reStatusID = reStatusID;
	}

	public EsDateBean getD() {
		return d;
	}

	public void setD(EsDateBean d) {
		this.d = d;
	}

	public EsUserBean getU() {
		return u;
	}

	public void setU(EsUserBean u) {
		this.u = u;
	}
}
