/*  
 * @(#) Bean2.java Create on 2017-10-19 下午2:27:59   
 *   
 * Copyright 2017 by xl.   
 */


package com.xl.basic.bean;

/**
 * 
 * @author xinlian
 * @date   2017-10-19
 */
public class ArticleOldbean {
//	articlebean------------
/// 媒体类型  1-网络新闻  2-数字报  3-论坛  4-博客  5-杂志  6-广播  7-电视  8--微博  9-视频  10-贴吧  11-sns 12-元搜索 13 -OCR 15 -APP 16 -微信
/// News = 1,Paper = 2,BBS = 3,Blog = 4,Sousuo = 12,Video = 9,Print = 13,Weixin = 16,App = 15
	private long aid;
	private int mediatypeint;/*News = 1,Paper = 2,BBS = 3,Blog = 4,ZaZhi = 5,GuangBo = 6,TV = 7,Weibo = 8,Video = 9,TieBa = 10,SNS = 11,Sousuo = 12,Wenda = 14,*/
	private int docID;
	private String title;
	private String author;
	private String htmlPage;
	private String dispTime;
	private String crawlTime;
	private String pageSrc;
	private String url;
	private String site  ;
	private String domain;
	private String htmlPageLen;
	private String topicID;
	private String topicName;
	private String referer;
	private String sourceName;
	private String tags;
	private String abs;
	private String page;
	private String layout;
	private String thumbnailPic;
	private String pdf;
	private String pics;
	private String sig;
	private String sigAll;
	private String s1;
	private String s2;
	private double m1;
	private double m2;
	private double m3;
	private String b1;
	private String b2;
	private int pn;
	private int pc;
	private String navigator;
	private String mediaType;
	private String pgClass;
	private int crawlType;
	private String mediaSubType;
	private String areaSubType;
	private String proSubType;
	private String govSubType;
	private String articleLocation;
	private String monitorIds;
	private String lableIds;
	private int types;
	private String areaList;
	private String classList;
	private String s3;
	private String s4;
	private double m4;

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public int getMediatypeint() {
		return mediatypeint;
	}
	public void setMediatypeint(int mediatypeint) {
		this.mediatypeint = mediatypeint;
	}
	public int getDocID() {
		return docID;
	}
	public void setDocID(int docID) {
		this.docID = docID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getHtmlPage() {
		return htmlPage;
	}
	public void setHtmlPage(String htmlPage) {
		this.htmlPage = htmlPage;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public String getHtmlPageLen() {
		return htmlPageLen;
	}
	public void setHtmlPageLen(String htmlPageLen) {
		this.htmlPageLen = htmlPageLen;
	}
	public String getTopicID() {
		return topicID;
	}
	public void setTopicID(String topicID) {
		this.topicID = topicID;
	}
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getAbs() {
		return abs;
	}
	public void setAbs(String abs) {
		this.abs = abs;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getLayout() {
		return layout;
	}
	public void setLayout(String layout) {
		this.layout = layout;
	}
	public String getThumbnailPic() {
		return thumbnailPic;
	}
	public void setThumbnailPic(String thumbnailPic) {
		this.thumbnailPic = thumbnailPic;
	}
	public String getPdf() {
		return pdf;
	}
	public void setPdf(String pdf) {
		this.pdf = pdf;
	}
	public String getPics() {
		return pics;
	}
	public void setPics(String pics) {
		this.pics = pics;
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
	public String getS1() {
		return s1;
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}
	public String getS2() {
		return s2;
	}
	public void setS2(String s2) {
		this.s2 = s2;
	}
	public double getM1() {
		return m1;
	}
	public void setM1(double m1) {
		this.m1 = m1;
	}
	public double getM2() {
		return m2;
	}
	public void setM2(double m2) {
		this.m2 = m2;
	}
	public double getM3() {
		return m3;
	}
	public void setM3(double m3) {
		this.m3 = m3;
	}
	public String getB1() {
		return b1;
	}
	public void setB1(String b1) {
		this.b1 = b1;
	}
	public String getB2() {
		return b2;
	}
	public void setB2(String b2) {
		this.b2 = b2;
	}
	public int getPn() {
		return pn;
	}
	public void setPn(int pn) {
		this.pn = pn;
	}
	public int getPc() {
		return pc;
	}
	public void setPc(int pc) {
		this.pc = pc;
	}
	public String getNavigator() {
		return navigator;
	}
	public void setNavigator(String navigator) {
		this.navigator = navigator;
	}
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getPgClass() {
		return pgClass;
	}
	public void setPgClass(String pgClass) {
		this.pgClass = pgClass;
	}
	public int getCrawlType() {
		return crawlType;
	}
	public void setCrawlType(int crawlType) {
		this.crawlType = crawlType;
	}
	public String getMediaSubType() {
		return mediaSubType;
	}
	public void setMediaSubType(String mediaSubType) {
		this.mediaSubType = mediaSubType;
	}
	public String getAreaSubType() {
		return areaSubType;
	}
	public void setAreaSubType(String areaSubType) {
		this.areaSubType = areaSubType;
	}
	public String getProSubType() {
		return proSubType;
	}
	public void setProSubType(String proSubType) {
		this.proSubType = proSubType;
	}
	public String getGovSubType() {
		return govSubType;
	}
	public void setGovSubType(String govSubType) {
		this.govSubType = govSubType;
	}
	public String getArticleLocation() {
		return articleLocation;
	}
	public void setArticleLocation(String articleLocation) {
		this.articleLocation = articleLocation;
	}
	public String getMonitorIds() {
		return monitorIds;
	}
	public void setMonitorIds(String monitorIds) {
		this.monitorIds = monitorIds;
	}

	public String getLableIds() {
		return lableIds;
	}

	public void setLableIds(String lableIds) {
		this.lableIds = lableIds;
	}

	public int getTypes() {
		return types;
	}
	public void setTypes(int types) {
		this.types = types;
	}
	public String getAreaList() {
		return areaList;
	}
	public void setAreaList(String areaList) {
		this.areaList = areaList;
	}
	public String getClassList() {
		return classList;
	}
	public void setClassList(String classList) {
		this.classList = classList;
	}
	public String getS3() {
		return s3;
	}
	public void setS3(String s3) {
		this.s3 = s3;
	}
	public String getS4() {
		return s4;
	}
	public void setS4(String s4) {
		this.s4 = s4;
	}
	public double getM4() {
		return m4;
	}
	public void setM4(double m4) {
		this.m4 = m4;
	}

}
