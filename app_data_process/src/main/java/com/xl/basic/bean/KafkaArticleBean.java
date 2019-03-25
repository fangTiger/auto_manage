package com.xl.basic.bean;



/**
 * @Author:lww
 * @Date:11:21 2018/3/14
 */
public class KafkaArticleBean {
	private String type;//媒体一级
	private String mediaType;//媒体二级
	private String mediaNameCn;//媒体中文
	private String mediaNameEn;//媒体英文
	private String site;//媒体站点
	private String domain;//媒体域名
	private String title;//标题
	private String summary;//摘要
	private String contentText;//正文
	private String keyword;//关键词（多个用英文逗号分隔）
	private String author ;//作者
	private String dispTime;//发布时间
	private String crawlTime;//采集时间
	private String pageSrc;//转载源
	private String layout;//版面
	private String page;//版位
	private String url;//文章链接
	private Integer words;//文章字数
	private String fatherUrl;//父级链接
	private String pageImage;//图片链接
	private String filePath;//文件路径
	private Integer picturesCount;//图片数量
	private Integer readnum;//阅读数
	private Integer commentnum;//评论数（回复数）
	private Integer likenum;//点赞数
	private Integer pn;//第几页
	private Integer pc;//总页数
	private Integer pv;//发行量
	private String sig;//语指纹（标题）
	private String sigall;//语指纹（正文）
	private String indexSig;//语指纹（标题）
	private String indexSigall;//语指纹（标题+正文）
	private String aid;//文章唯一ID(默认10101010 ）
	private Integer emotionValue;//文章情感
	private Integer isBooked;//是否订阅
	private String examine;//标志位
	private String navigator;//导航
	private String articleLocation;//文章位置
	private Double proportion;//比例
	private String crawlType;//采集类型
	private String language;//语言
	private String weMName;//自媒体下的账号名称
	private Integer sharenum;//分享数
	private String lableIds;//上传标签（多个用英文逗号拼接，如：机构1.标签1，机构2.标签2）
	private String monitorIds;//上传监测项（多个用英文逗号拼接，如：机构1.监测项1，机构2.监测项2）
	private String crawlSource;//预留采集源监控使用

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDispTime() {
		return dispTime;
	}

	public void setDispTime(String dispTime) {
		this.dispTime = dispTime;
	}

	public String getCrawlTime() {
		return crawlTime;
	}

	public void setCrawlTime(String crawlTime) {
		this.crawlTime = crawlTime;
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

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public String getSigall() {
		return sigall;
	}

	public void setSigall(String sigall) {
		this.sigall = sigall;
	}

	public String getIndexSig() {
		return indexSig;
	}

	public void setIndexSig(String indexSig) {
		this.indexSig = indexSig;
	}

	public String getIndexSigall() {
		return indexSigall;
	}

	public void setIndexSigall(String indexSigall) {
		this.indexSigall = indexSigall;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public String getLableIds() {
		return lableIds;
	}

	public void setLableIds(String lableIds) {
		this.lableIds = lableIds;
	}

	public String getMonitorIds() {
		return monitorIds;
	}

	public void setMonitorIds(String monitorIds) {
		this.monitorIds = monitorIds;
	}

	public String getCrawlSource() {
		return crawlSource;
	}

	public void setCrawlSource(String crawlSource) {
		this.crawlSource = crawlSource;
	}

}
