package com.xl.bean.irtv;


import com.xl.bean.article.RuleFilterBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lww
 * @Date:15:07 2017/9/13
 */
public class BroadcastBean {

	private String type;
	private BroadcastMediaBean media;
	private String title;
	private String summary;
	private String contentText;
	private List<String> keyword;
	private String author;
	private DBean d;
	private String layout;
	private String url;
	private Integer words;
	private String pageImage;
	private String filePath;
	private BroadcastAdvBean adv;
	private String aid;
	private Integer emotionValue;
	private List<Long> industryCode;
	private List<Long> classCode;
	private List<Long> contentCode;
	private List<Long> eventCode;
	private Integer isBooked;
	private String examine;
	private String articleLocation;
	private Double proportion;
	private String crawlType;
	private String language;//语言
	private String programType;//栏目类型
	private List<String> cycle;//栏目播出周期
	private String broadcastTime;//栏目播出时间
	private Integer programDuration;//栏目时长（源视频）
	private String fileDuration;//文件时长（切片）
	private String fileId;//切片视频ID
	private List<String> brandCategory;//行业分类(品牌对应时长)
	private List<String> extraction;//扩展字段
	private List<String> peopleExtraction;
	private List<String> locationExtraction;
	private List<String> mechanismExtraction;
	private List<String> mediaExtraction;
	private List<String> countryExtraction;
	private List<String> provinceExtraction;
	private List<String> orgs;
	private List<String> monitors;
	private List<String> labels;
	private List<String> warnings;
	private List<String> orgsflag;

	private List<RuleFilterBean> ruleFilters;//过略条件

	public BroadcastBean() {
		this.type = "";
		this.media = new BroadcastMediaBean();
		this.title = "";
		this.summary = "";
		this.contentText = "";
		this.keyword = new ArrayList<>();
		this.author = "";
		this.d = new DBean();
		this.layout = "";
		this.url = "";
		this.words = 0;
		this.pageImage = "";
		this.filePath = "";
		this.adv = new BroadcastAdvBean();
		this.aid = "";
		this.emotionValue = 0;
		this.industryCode = new ArrayList<>();
		this.classCode = new ArrayList<>();
		this.contentCode = new ArrayList<>();
		this.eventCode = new ArrayList<>();
		this.isBooked = 0;
		this.examine = "";
		this.articleLocation = "";
		this.proportion = 0.0;
		this.crawlType = "0";
		this.language = "";
		this.programType = "";
		this.cycle = new ArrayList<>();
		this.broadcastTime = "";
		this.programDuration = 0;
		this.fileDuration = "";
		this.fileId = "";
		this.brandCategory = new ArrayList<>();
		this.extraction = new ArrayList<>();
		this.peopleExtraction = new ArrayList<>();
		this.locationExtraction = new ArrayList<>();
		this.mechanismExtraction = new ArrayList<>() ;
		this.mediaExtraction = new ArrayList<>();
		this.countryExtraction = new ArrayList<>();
		this.provinceExtraction = new ArrayList<>();
		this.orgs = new ArrayList<>();
		this.monitors = new ArrayList<>();
		this.labels = new ArrayList<>();
		this.warnings = new ArrayList<>();
		this.orgsflag = new ArrayList<>();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BroadcastMediaBean getMedia() {
		return media;
	}

	public void setMedia(BroadcastMediaBean media) {
		this.media = media;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public List<String> getKeyword() {
		return keyword;
	}

	public void setKeyword(List<String> keyword) {
		this.keyword = keyword;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public DBean getD() {
		return d;
	}

	public void setD(DBean d) {
		this.d = d;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getWords() {
		return words;
	}

	public void setWords(Integer words) {
		this.words = words;
	}

	public String getPageImage() {
		return pageImage;
	}

	public void setPageImage(String pageImage) {
		this.pageImage = pageImage;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public BroadcastAdvBean getAdv() {
		return adv;
	}

	public void setAdv(BroadcastAdvBean adv) {
		this.adv = adv;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public Integer getEmotionValue() {
		return emotionValue;
	}

	public void setEmotionValue(Integer emotionValue) {
		this.emotionValue = emotionValue;
	}

	public List<Long> getIndustryCode() {
		return industryCode;
	}

	public void setIndustryCode(List<Long> industryCode) {
		this.industryCode = industryCode;
	}

	public List<Long> getClassCode() {
		return classCode;
	}

	public void setClassCode(List<Long> classCode) {
		this.classCode = classCode;
	}

	public List<Long> getContentCode() {
		return contentCode;
	}

	public void setContentCode(List<Long> contentCode) {
		this.contentCode = contentCode;
	}

	public List<Long> getEventCode() {
		return eventCode;
	}

	public void setEventCode(List<Long> eventCode) {
		this.eventCode = eventCode;
	}

	public Integer getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(Integer isBooked) {
		this.isBooked = isBooked;
	}

	public String getExamine() {
		return examine;
	}

	public void setExamine(String examine) {
		this.examine = examine;
	}

	public String getArticleLocation() {
		return articleLocation;
	}

	public void setArticleLocation(String articleLocation) {
		this.articleLocation = articleLocation;
	}

	public Double getProportion() {
		return proportion;
	}

	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}

	public String getCrawlType() {
		return crawlType;
	}

	public void setCrawlType(String crawlType) {
		this.crawlType = crawlType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public List<String> getCycle() {
		return cycle;
	}

	public void setCycle(List<String> cycle) {
		this.cycle = cycle;
	}

	public String getBroadcastTime() {
		return broadcastTime;
	}

	public void setBroadcastTime(String broadcastTime) {
		this.broadcastTime = broadcastTime;
	}

	public Integer getProgramDuration() {
		return programDuration;
	}

	public void setProgramDuration(Integer programDuration) {
		this.programDuration = programDuration;
	}

	public String getFileDuration() {
		return fileDuration;
	}

	public void setFileDuration(String fileDuration) {
		this.fileDuration = fileDuration;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public List<String> getBrandCategory() {
		return brandCategory;
	}

	public void setBrandCategory(List<String> brandCategory) {
		this.brandCategory = brandCategory;
	}

	public List<String> getExtraction() {
		return extraction;
	}

	public void setExtraction(List<String> extraction) {
		this.extraction = extraction;
	}

	public List<String> getPeopleExtraction() {
		return peopleExtraction;
	}

	public void setPeopleExtraction(List<String> peopleExtraction) {
		this.peopleExtraction = peopleExtraction;
	}

	public List<String> getLocationExtraction() {
		return locationExtraction;
	}

	public void setLocationExtraction(List<String> locationExtraction) {
		this.locationExtraction = locationExtraction;
	}

	public List<String> getMechanismExtraction() {
		return mechanismExtraction;
	}

	public void setMechanismExtraction(List<String> mechanismExtraction) {
		this.mechanismExtraction = mechanismExtraction;
	}

	public List<String> getMediaExtraction() {
		return mediaExtraction;
	}

	public void setMediaExtraction(List<String> mediaExtraction) {
		this.mediaExtraction = mediaExtraction;
	}

	public List<String> getCountryExtraction() {
		return countryExtraction;
	}

	public void setCountryExtraction(List<String> countryExtraction) {
		this.countryExtraction = countryExtraction;
	}

	public List<String> getProvinceExtraction() {
		return provinceExtraction;
	}

	public void setProvinceExtraction(List<String> provinceExtraction) {
		this.provinceExtraction = provinceExtraction;
	}

	public List<String> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<String> orgs) {
		this.orgs = orgs;
	}

	public List<String> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<String> monitors) {
		this.monitors = monitors;
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

	public List<String> getOrgsflag() {
		return orgsflag;
	}

	public void setOrgsflag(List<String> orgsflag) {
		this.orgsflag = orgsflag;
	}

	public List<RuleFilterBean> getRuleFilters() {
		return ruleFilters;
	}

	public void setRuleFilters(List<RuleFilterBean> ruleFilters) {
		this.ruleFilters = ruleFilters;
	}
}
