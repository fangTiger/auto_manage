package com.xl.bean.weibo.tf;


import com.alibaba.fastjson.annotation.JSONField;
import com.xl.bean.article.RuleFilterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * twitter,facebook 实体
 * @Author:lww
 * @Date:21:31 2017/7/11
 */
public class TfBean {

	private String type;
	private String statusId;
	private String statusText;
	private Integer isRetweeted;
	private Integer commentCount;
	private Integer retweetedCount;
	private String textUrl;
	private Integer weiboFrom;
	private String crawlType;
	private Integer emotionValue;
	private List<String> conversation;
	private String examine;
	private String source;
	private String reStatusId;
	private Integer isBooked;
	private Long sig;
	private Integer attitudesCount;
	private TfUserBean u;
	private DBean d;
	private String language;
	private List<String> extraction;//扩展字段
	private List<String> monitors;
	private List<String> orgs;
	private List<String> labels;
	private List<String> warnings;
	private List<String> orgsflag;

	private List<RuleFilterBean> ruleFilters;//过略条件

	@JSONField(serialize=false)
	private String _ewID;//信源任务ID

	public TfBean() {
		this.type = "";
		this.statusId = "";
		this.statusText = "";
		this.isRetweeted = 0;
		this.commentCount = 0;
		this.retweetedCount = 0;
		this.textUrl = "";
		this.weiboFrom = 0;
		this.crawlType = "0";
		this.emotionValue = 0;
		this.conversation = new ArrayList<>();
		this.examine = "";
		this.source = "";
		this.reStatusId = "";
		this.isBooked = 0;
		this.sig = 0l;
		this.attitudesCount = 0;
		this.u = new TfUserBean();
		this.d = new DBean();
		this.language = "";
		this.extraction = new ArrayList<>();
		this.monitors = new ArrayList<>();
		this.orgs = new ArrayList<>();
		this.labels = new ArrayList<>();
		this.warnings = new ArrayList<>();
		this.orgsflag = new ArrayList<>();
	}

	public TfBean(String type, String statusId, String statusText, Integer isRetweeted, Integer commentCount, Integer retweetedCount, String textUrl, Integer weiboFrom, String crawlType, Integer emotionValue, List<String> conversation, String examine, String source, String reStatusId, Integer isBooked, Long sig, Integer attitudesCount, TfUserBean u, DBean d, String language, List<String> extraction, List<String> monitors, List<String> orgs, List<String> labels, List<String> warnings, List<String> orgsflag) {
		this.type = type;
		this.statusId = statusId;
		this.statusText = statusText;
		this.isRetweeted = isRetweeted;
		this.commentCount = commentCount;
		this.retweetedCount = retweetedCount;
		this.textUrl = textUrl;
		this.weiboFrom = weiboFrom;
		this.crawlType = crawlType;
		this.emotionValue = emotionValue;
		this.conversation = conversation;
		this.examine = examine;
		this.source = source;
		this.reStatusId = reStatusId;
		this.isBooked = isBooked;
		this.sig = sig;
		this.attitudesCount = attitudesCount;
		this.u = u;
		this.d = d;
		this.language = language;
		this.extraction = extraction;
		this.monitors = monitors;
		this.orgs = orgs;
		this.labels = labels;
		this.warnings = warnings;
		this.orgsflag = orgsflag;
	}

	public List<String> getConversation() {
		return conversation;
	}

	public void setConversation(List<String> conversation) {
		this.conversation = conversation;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public List<String> getOrgsflag() {
		return orgsflag;
	}

	public void setOrgsflag(List<String> orgsflag) {
		this.orgsflag = orgsflag;
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

	public String getTextUrl() {
		return textUrl;
	}

	public void setTextUrl(String textUrl) {
		this.textUrl = textUrl;
	}

	public Integer getWeiboFrom() {
		return weiboFrom;
	}

	public void setWeiboFrom(Integer weiboFrom) {
		this.weiboFrom = weiboFrom;
	}

	public String getCrawlType() {
		return crawlType;
	}

	public void setCrawlType(String crawlType) {
		this.crawlType = crawlType;
	}

	public Integer getEmotionValue() {
		return emotionValue;
	}

	public void setEmotionValue(Integer emotionValue) {
		this.emotionValue = emotionValue;
	}

	public String getExamine() {
		return examine;
	}

	public void setExamine(String examine) {
		this.examine = examine;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getReStatusId() {
		return reStatusId;
	}

	public void setReStatusId(String reStatusId) {
		this.reStatusId = reStatusId;
	}

	public Integer getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(Integer isBooked) {
		this.isBooked = isBooked;
	}

	public Long getSig() {
		return sig;
	}

	public void setSig(Long sig) {
		this.sig = sig;
	}

	public Integer getAttitudesCount() {
		return attitudesCount;
	}

	public void setAttitudesCount(Integer attitudesCount) {
		this.attitudesCount = attitudesCount;
	}

	public TfUserBean getU() {
		return u;
	}

	public void setU(TfUserBean u) {
		this.u = u;
	}

	public DBean getD() {
		return d;
	}

	public void setD(DBean d) {
		this.d = d;
	}

	public List<String> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<String> monitors) {
		this.monitors = monitors;
	}

	public List<String> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<String> orgs) {
		this.orgs = orgs;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}

	public List<String> getExtraction() {
		return extraction;
	}

	public void setExtraction(List<String> extraction) {
		this.extraction = extraction;
	}

	public List<RuleFilterBean> getRuleFilters() {
		return ruleFilters;
	}

	public void setRuleFilters(List<RuleFilterBean> ruleFilters) {
		this.ruleFilters = ruleFilters;
	}

	public String get_ewID() {
		return _ewID;
	}

	public void set_ewID(String _ewID) {
		this._ewID = _ewID;
	}
}
