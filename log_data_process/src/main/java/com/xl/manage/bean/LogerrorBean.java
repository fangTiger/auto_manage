/*  
 * @(#) LogerrorBean.java Create on 2016-7-20 下午2:53:24   
 *   
 * Copyright 2016 by xl.   
 */


package com.xl.manage.bean;


/**
 * 日志入库失败信息表
 * @author liweiwei
 * @date   2016-7-20
 */
public class LogerrorBean {
	
	private String id;
	private String dataId;//数据主键
	private String platformCode;//平台code
	private String moduleCode;//模块code
	private String operationCode;//操作类型code
	private String operationName;//操作名称
	private String describe;//操作描述
	private Integer objectCount;//操作对象数量（针对数据，增删改）
	private String dataContent;//数据内容
	private Integer state;//状态 0：失败 ，1:成功
	private String errorDescribe;//失败原因
	private Integer userId;//操作人Id
	private String createTime;//创建时间
	private String ipAddress;//操作IP
	private Long totalTime;//总耗时（毫秒）
	private String fullPath;//请求完整路径
	private String requestParam;//请求参数
	private String xRequestedWith;//客户端请求类型
	private String host;//指定请求资源的Intenet主机和端口号，必须表示请求url的原始服务器或网关的位置。
	private String acceptLanguage;//客户端浏览器支持语言
	private String accptEncoding;//客户端浏览器支持的编码类型
	private String accept;//客户端希望接受的数据类型
	private String connection;//客户端与服务器通信时长链接的处理方式
	private String userAgent;//包含发出请求的用户信息
	private String referer;//允许客户端指定请求uri的源资源地址
	private String cookie;//cookie
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
	public String getPlatformCode() {
		return platformCode;
	}
	public void setPlatformCode(String platformCode) {
		this.platformCode = platformCode;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getOperationCode() {
		return operationCode;
	}
	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
	public String getOperationName() {
		return operationName;
	}
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public Integer getObjectCount() {
		return objectCount;
	}
	public void setObjectCount(Integer objectCount) {
		this.objectCount = objectCount;
	}
	public String getDataContent() {
		return dataContent;
	}
	public void setDataContent(String dataContent) {
		this.dataContent = dataContent;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getErrorDescribe() {
		return errorDescribe;
	}
	public void setErrorDescribe(String errorDescribe) {
		this.errorDescribe = errorDescribe;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public Long getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Long totalTime) {
		this.totalTime = totalTime;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public String getRequestParam() {
		return requestParam;
	}
	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}
	public String getxRequestedWith() {
		return xRequestedWith;
	}
	public void setxRequestedWith(String xRequestedWith) {
		this.xRequestedWith = xRequestedWith;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getAcceptLanguage() {
		return acceptLanguage;
	}
	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}
	public String getAccptEncoding() {
		return accptEncoding;
	}
	public void setAccptEncoding(String accptEncoding) {
		this.accptEncoding = accptEncoding;
	}
	public String getAccept() {
		return accept;
	}
	public void setAccept(String accept) {
		this.accept = accept;
	}
	public String getConnection() {
		return connection;
	}
	public void setConnection(String connection) {
		this.connection = connection;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	
}
