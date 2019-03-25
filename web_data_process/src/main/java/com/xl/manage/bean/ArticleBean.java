package com.xl.manage.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;


public class ArticleBean {

	private String type;
    private MediaBean media;
    private String title;
    private String summary;		      
    private String contentText;		      
    private List<String> keyword;		       
    private String author ;  
    private DBean d;
    private String pageSrc;		      
    private String layout;		        
    private String page;		      
    private String url;		      
    private Integer words;
    private String fatherUrl;		     
    private String pageImage;		   
    private String filePath;		     
    private Integer picturesCount;
    private Integer readnum;
    private Integer commentnum;
    private Integer likenum;
    private Integer pn;
    private Integer pc;
    private AdvBean adv;
    private SigsBean sigs;
	private String aid;
    private Integer emotionValue;
    private List<Long> industryCode;
    private List<Long> classCode;
    private List<Long> contentCode;
    private List<Long> eventCode;
    private Integer isBooked;
    private String examine;
    private String navigator;
    private String articleLocation;
    private Double proportion;
    private String crawlType;
    private String language;//语言
    private String weMName;//自媒体下的账号名称
    private Integer sharenum;//分享数
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

	@JSONField(serialize=false)
	private String _monitorIds;//上传数据的监测项
	@JSONField(serialize=false)
	private String _lableIds;//上传数据的标签

	public ArticleBean() {
		this.type = "";
		this.media = new MediaBean();
		this.title = "";
		this.summary = "";
		this.contentText = "";
		this.keyword = new ArrayList<>();
		this.author = "";
		this.d = new DBean();
		this.pageSrc = "";
		this.layout = "";
		this.page = "";
		this.url = "";
		this.words = 0;
		this.fatherUrl = "";
		this.pageImage = "";
		this.filePath = "";
		this.picturesCount = 0;
		this.readnum = 0;
		this.commentnum = 0;
		this.likenum = 0;
		this.pn = 0;
		this.pc = 0;
		this.adv = new AdvBean();
		this.sigs = new SigsBean();
		this.aid = "";
		this.emotionValue = 0;
		this.industryCode = new ArrayList<>();
		this.classCode = new ArrayList<>();
		this.contentCode = new ArrayList<>();
		this.eventCode = new ArrayList<>();
		this.isBooked = 0;
		this.examine = "";
		this.navigator = "";
		this.articleLocation = "";
		this.proportion = 0d;
		this.crawlType = "";
		this.language = "";
		this.weMName = "";
		this.sharenum = 0;
		this.extraction = new ArrayList<>();
		this.peopleExtraction = new ArrayList<>();
		this.locationExtraction =  new ArrayList<>();
		this.mechanismExtraction =  new ArrayList<>();
		this.mediaExtraction =  new ArrayList<>();
		this.countryExtraction =  new ArrayList<>();
		this.provinceExtraction =  new ArrayList<>();
		this.orgs =  new ArrayList<>();
		this.monitors =  new ArrayList<>();
		this.labels =  new ArrayList<>();
		this.warnings =  new ArrayList<>();
		this.orgsflag =  new ArrayList<>();
	}

	public ArticleBean(String type, MediaBean media, String title, String summary, String contentText, List<String> keyword, String author, DBean d, String pageSrc, String layout, String page, String url, Integer words, String fatherUrl, String pageImage, String filePath, Integer picturesCount, Integer readnum, Integer commentnum, Integer likenum, Integer pn, Integer pc, AdvBean adv, SigsBean sigs, String aid, Integer emotionValue, List<Long> industryCode, List<Long> classCode, List<Long> contentCode, List<Long> eventCode, Integer isBooked, String examine, String navigator, String articleLocation, Double proportion, String crawlType, String language, String weMName, Integer sharenum, List<String> extraction, List<String> peopleExtraction, List<String> locationExtraction, List<String> mechanismExtraction, List<String> mediaExtraction, List<String> countryExtraction, List<String> provinceExtraction, List<String> orgs, List<String> monitors, List<String> labels, List<String> warnings, List<String> orgsflag) {
		this.type = type;
		this.media = media;
		this.title = title;
		this.summary = summary;
		this.contentText = contentText;
		this.keyword = keyword;
		this.author = author;
		this.d = d;
		this.pageSrc = pageSrc;
		this.layout = layout;
		this.page = page;
		this.url = url;
		this.words = words;
		this.fatherUrl = fatherUrl;
		this.pageImage = pageImage;
		this.filePath = filePath;
		this.picturesCount = picturesCount;
		this.readnum = readnum;
		this.commentnum = commentnum;
		this.likenum = likenum;
		this.pn = pn;
		this.pc = pc;
		this.adv = adv;
		this.sigs = sigs;
		this.aid = aid;
		this.emotionValue = emotionValue;
		this.industryCode = industryCode;
		this.classCode = classCode;
		this.contentCode = contentCode;
		this.eventCode = eventCode;
		this.isBooked = isBooked;
		this.examine = examine;
		this.navigator = navigator;
		this.articleLocation = articleLocation;
		this.proportion = proportion;
		this.crawlType = crawlType;
		this.language = language;
		this.weMName = weMName;
		this.sharenum = sharenum;
		this.extraction = extraction;
		this.peopleExtraction = peopleExtraction;
		this.locationExtraction = locationExtraction;
		this.mechanismExtraction = mechanismExtraction;
		this.mediaExtraction = mediaExtraction;
		this.countryExtraction = countryExtraction;
		this.provinceExtraction = provinceExtraction;
		this.orgs = orgs;
		this.monitors = monitors;
		this.labels = labels;
		this.warnings = warnings;
		this.orgsflag = orgsflag;
	}

	@Override
	public String toString() {
		return "ArticleOldBean{" +
				"type='" + type + '\'' +
				", media=" + media +
				", title='" + title + '\'' +
				", summary='" + summary + '\'' +
				", contentText='" + contentText + '\'' +
				", keyword=" + keyword +
				", author='" + author + '\'' +
				", d=" + d +
				", pageSrc='" + pageSrc + '\'' +
				", layout='" + layout + '\'' +
				", page='" + page + '\'' +
				", url='" + url + '\'' +
				", words=" + words +
				", fatherUrl='" + fatherUrl + '\'' +
				", pageImage='" + pageImage + '\'' +
				", filePath='" + filePath + '\'' +
				", picturesCount=" + picturesCount +
				", readnum=" + readnum +
				", commentnum=" + commentnum +
				", likenum=" + likenum +
				", pn=" + pn +
				", pc=" + pc +
				", adv=" + adv +
				", sigs=" + sigs +
				", aid='" + aid + '\'' +
				", emotionValue=" + emotionValue +
				", industryCode=" + industryCode +
				", classCode=" + classCode +
				", contentCode=" + contentCode +
				", eventCode=" + eventCode +
				", isBooked=" + isBooked +
				", examine='" + examine + '\'' +
				", navigator='" + navigator + '\'' +
				", articleLocation='" + articleLocation + '\'' +
				", proportion=" + proportion +
				", crawlType='" + crawlType + '\'' +
				", language='" + language + '\'' +
				", weMName='" + weMName + '\'' +
				", sharenum=" + sharenum +
				", extraction=" + extraction +
				", peopleExtraction=" + peopleExtraction +
				", locationExtraction=" + locationExtraction +
				", mechanismExtraction=" + mechanismExtraction +
				", mediaExtraction=" + mediaExtraction +
				", countryExtraction=" + countryExtraction +
				", provinceExtraction=" + provinceExtraction +
				", orgs=" + orgs +
				", monitors=" + monitors +
				", labels=" + labels +
				", warnings=" + warnings +
				", orgsflag=" + orgsflag +
				'}';
	}

	public String getWeMName() {
		return weMName;
	}

	public void setWeMName(String weMName) {
		this.weMName = weMName;
	}

	public Integer getSharenum() {
		return sharenum;
	}

	public void setSharenum(Integer sharenum) {
		this.sharenum = sharenum;
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

	public MediaBean getMedia() {
		return media;
	}

	public void setMedia(MediaBean media) {
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

	public String getPageSrc() {
		return pageSrc;
	}

	public void setPageSrc(String pageSrc) {
		this.pageSrc = pageSrc;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
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

	public String getFatherUrl() {
		return fatherUrl;
	}

	public void setFatherUrl(String fatherUrl) {
		this.fatherUrl = fatherUrl;
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

	public Integer getPicturesCount() {
		return picturesCount;
	}

	public void setPicturesCount(Integer picturesCount) {
		this.picturesCount = picturesCount;
	}

	public Integer getReadnum() {
		return readnum;
	}

	public void setReadnum(Integer readnum) {
		this.readnum = readnum;
	}

	public Integer getCommentnum() {
		return commentnum;
	}

	public void setCommentnum(Integer commentnum) {
		this.commentnum = commentnum;
	}

	public Integer getLikenum() {
		return likenum;
	}

	public void setLikenum(Integer likenum) {
		this.likenum = likenum;
	}

	public Integer getPn() {
		return pn;
	}

	public void setPn(Integer pn) {
		this.pn = pn;
	}

	public Integer getPc() {
		return pc;
	}

	public void setPc(Integer pc) {
		this.pc = pc;
	}

	public AdvBean getAdv() {
		return adv;
	}

	public void setAdv(AdvBean adv) {
		this.adv = adv;
	}

	public SigsBean getSigs() {
		return sigs;
	}

	public void setSigs(SigsBean sigs) {
		this.sigs = sigs;
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

	public String getNavigator() {
		return navigator;
	}

	public void setNavigator(String navigator) {
		this.navigator = navigator;
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

	public List<String> getExtraction() {
		return extraction;
	}

	public void setExtraction(List<String> extraction) {
		this.extraction = extraction;
	}

	public String get_monitorIds() {
		return _monitorIds;
	}

	public void set_monitorIds(String _monitorIds) {
		this._monitorIds = _monitorIds;
	}

	public String get_lableIds() {
		return _lableIds;
	}

	public void set_lableIds(String _lableIds) {
		this._lableIds = _lableIds;
	}

}
