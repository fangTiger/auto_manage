/*  
 * @(#) EsBean.java Create on 2016-10-21 下午3:28:39   
 *   
 * Copyright 2016 by xl.   
 */


package com.xl.manage.bean;

/**
 * 日志esBean
 * @author liweiwei
 * @date   2016-10-21
 */
public class LogEsBean {

	private Long logId;//日志唯一ID
	private String taskCode;//任务code-规则：平台code_模块code_操作code_操作人id_13位时间戳
	private Integer taskType;//任务类型 -0：单条数据任务，1：多条数据任务
	private String dataId;//数据唯一Id - 数据类型+aId
	private String platformCode;//平台code
	private String moduleCode;//模块code
	private String operationCode;//操作类型code
	private String operationName;//操作名称
	private String describe;//操作描述
	private String dataContent;//数据内容
	private Integer state;//状态-0：失败 ，1：成功
	private String errorDescribe;//失败原因
	private Integer userId;//操作人-0：机器，其他为用户ID
	private Object d;//创建时间
	private String ipAddress;//操作IP
	private String totalTime;//总耗时（毫秒）
	private String fullPath;//请求完整路径
	private String requestParam;//请求参数
	private String xRequestedWith;//客户端请求类型
	private String host;//指定请求资源的Intenet主机和端口号-必须表示请求url的原始服务器或网关的位置。
	private String acceptLanguage;//客户端浏览器支持语言
	private String accptEncoding;//客户端浏览器支持的编码类型
	private String accept;//客户端希望接受的数据类型
	private String connection;//客户端与服务器通信时长链接的处理方式
	private String userAgent;//包含发出请求的用户信息
	private String referer;//允许客户端指定请求uri的源资源 
	private String cookie;//cookie
	private Integer isPCount;//默认0-未处理 1-已处理
	private Object tOperation;//统计操作

	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public Integer getTaskType() {
		return taskType;
	}
	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
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
	public String getDataContent() {
		return dataContent;
	}
	public void setDataContent(String dataContent) {
		this.dataContent = dataContent;
	}

	public Object getD() {
		return d;
	}

	public void setD(Object d) {
		this.d = d;
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
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
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
	public Integer getIsPCount() {
		return isPCount;
	}
	public void setIsPCount(Integer isPCount) {
		this.isPCount = isPCount;
	}
	public Object gettOperation() {
		return tOperation;
	}
	public void settOperation(Object tOperation) {
		this.tOperation = tOperation;
	}

}
