/*  
 * @(#) ArticleInfoTempBean.java Create on 2016-3-1 下午2:52:26   
 *   
 * Copyright 2016 by xl.   
 */


package com.xl.manage.bean;

/**
 * 文章信息临时表
 * @author liweiwei
 * @date   2016-3-1
 */
public class ArticleInfoTempBean {
	
	private Integer id;
	private Integer articleId;//文章Id
	private String headline;//标题
	private String abbv;//报刊编码
	private String author;//作者
	private String authorEN;//作者英文名称
	private String publishTime;//发布时间
	private String content;//正文内容
	private String picUrl;//图片地址(多值以","逗号分割)
	private String publishedSource;//发布来源
	private String copyrightSource;//版权来源
	private String layout;//版面名称
	private String edition;//版次
	private String crawlTime;//采集时间
	private Integer mediaId;//媒体Id
	private String createTime;//创建时间
	private Integer checkUser;//审核人
	private String checkTime;//审核时间
	private String scanner;//剪报人
	private String acquisitionTime;//获取时间(zen得到数据时间)
	private Integer deleteFlag;//删除标识位（0-正常 1-删除）
	//------新扩展字段
	private String serverPath;//服务器拼接地址
	private String articlePicName;//文章图
	private Integer words;//字数
	private String imageName;//整版图
	private Double relativeSize;//版面比例
	private Integer photoNum;//图片个数
	private String nwidth;//单独宽度
	private String nheight;//单独高度
	private Integer width;//总宽度
	private Integer height;//总高度
	private String articleThumbName;//缩略图
	private String articleLocation;//位置
	private Integer serverId;//服务器编号
	private Integer picSn;//图片顺序编号
	private String mediaSubType;//报纸/杂志标识位--paper:报纸，magazine:杂志
	private Integer paperId;//图片ID
	private String coverImageName;//封面图
	
	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public String getCoverImageName() {
		return coverImageName;
	}

	public void setCoverImageName(String coverImageName) {
		this.coverImageName = coverImageName;
	}

	public String getNwidth() {
		return nwidth;
	}

	public void setNwidth(String nwidth) {
		this.nwidth = nwidth;
	}

	public String getNheight() {
		return nheight;
	}

	public void setNheight(String nheight) {
		this.nheight = nheight;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getMediaSubType() {
		return mediaSubType;
	}

	public void setMediaSubType(String mediaSubType) {
		this.mediaSubType = mediaSubType;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	public Integer getPicSn() {
		return picSn;
	}

	public void setPicSn(Integer picSn) {
		this.picSn = picSn;
	}

	public String getArticleLocation() {
		return articleLocation;
	}
	
	public void setArticleLocation(String articleLocation) {
		this.articleLocation = articleLocation;
	}
	
	public String getAuthorEN() {
		return authorEN;
	}


	public void setAuthorEN(String authorEN) {
		this.authorEN = authorEN;
	}

	public String getServerPath() {
		return serverPath;
	}

	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	public String getArticlePicName() {
		return articlePicName;
	}

	public void setArticlePicName(String articlePicName) {
		this.articlePicName = articlePicName;
	}

	public Integer getWords() {
		return words;
	}

	public void setWords(Integer words) {
		this.words = words;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Double getRelativeSize() {
		return relativeSize;
	}

	public void setRelativeSize(Double relativeSize) {
		this.relativeSize = relativeSize;
	}

	public Integer getPhotoNum() {
		return photoNum;
	}

	public void setPhotoNum(Integer photoNum) {
		this.photoNum = photoNum;
	}

	public String getArticleThumbName() {
		return articleThumbName;
	}

	public void setArticleThumbName(String articleThumbName) {
		this.articleThumbName = articleThumbName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getAbbv() {
		return abbv;
	}

	public void setAbbv(String abbv) {
		this.abbv = abbv;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPublishedSource() {
		return publishedSource;
	}

	public void setPublishedSource(String publishedSource) {
		this.publishedSource = publishedSource;
	}

	public String getCopyrightSource() {
		return copyrightSource;
	}

	public void setCopyrightSource(String copyrightSource) {
		this.copyrightSource = copyrightSource;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getCrawlTime() {
		return crawlTime;
	}

	public void setCrawlTime(String crawlTime) {
		this.crawlTime = crawlTime;
	}

	public Integer getMediaId() {
		return mediaId;
	}

	public void setMediaId(Integer mediaId) {
		this.mediaId = mediaId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(Integer checkUser) {
		this.checkUser = checkUser;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getAcquisitionTime() {
		return acquisitionTime;
	}

	public void setAcquisitionTime(String acquisitionTime) {
		this.acquisitionTime = acquisitionTime;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getScanner() {
		return scanner;
	}

	public void setScanner(String scanner) {
		this.scanner = scanner;
	}
}
