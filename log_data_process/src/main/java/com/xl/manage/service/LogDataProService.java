package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.LogOldBean;
import com.xl.manage.bean.LogEsBean;
import com.xl.manage.bean.LogerrorBean;
import com.xl.manage.common.LogCommonData;
import com.xl.tool.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 日志数据格式化
 * @Author:lww
 * @Date:16:34 2017/11/8
 */
public class LogDataProService {

	public static void main(String[] args)throws Exception {
		System.out.println(HeritrixHelper.getAidByUrl("/inews/model/option/123456/2017-11-30 00:00:00"));
	}

	/**
	 * 日志数据格式化
	 * @return java.util.List<com.xl.basic.bean.LogEsBean>
	 * @Author: lww
	 * @Description:
	 * @Date: 16:39 2017/11/8
	 * @param oldBean
	 */
	public List<LogEsBean> dealData(LogOldBean oldBean){

		long logid;
		List<LogEsBean> logList = null;

		try {
			logList = changeBean(oldBean);
			if(logList!=null&&logList.size()>0){
				for(LogEsBean bean:logList){//获取日志ID
					logid = HeritrixHelper.getAidByUrl("/"+bean.getPlatformCode()+"/"+bean.getModuleCode()+"/"+bean.getOperationCode()+"/"+StringUtil.toTrim(bean.getDataId())+"/"+StringUtil.toTrim(JSON.parseObject(bean.getD().toString()).getString("createTime")));
					bean.setLogId(logid);
				}
			}
		}catch (Exception e){
			LogHelper.error("---------------日志数据格式化出现异常！param["+JSONObject.toJSONString(oldBean)+"]--------------------",e);
		}
		return logList;
	}

	/**
	 * 将日志数据解析并拆分
	 * @return java.util.List<com.xl.manage.bean.LogEsBean>
	 * @Author: lww
	 * @Description:
	 * @Date: 17:53 2017/11/8
	 * @param oldBean
	 */
	private List<LogEsBean> changeBean(LogOldBean oldBean)throws Exception{

		JSONArray jsArray;
		JSONObject jsObject;
		JSONObject dObject;//时间对象
		LogEsBean bean = null;
		LogEsBean newBean = null;
		String url;
		String requestParam;
		String dataContent;
		String urlA;
		String []urlArray;
		Map<String, Object> map;
		JSONArray tOptionArray;
		JSONObject tObject;
		String spicOption = "";//操作类型
		List<LogEsBean> list = new ArrayList<LogEsBean>();
		try {
			if(oldBean!=null){
				
				bean = new LogEsBean();

				tOptionArray = new JSONArray();
				bean.settOperation(tOptionArray);

				bean.setDataId("");
				bean.setTotalTime("");
				bean.setIsPCount(0);
				bean.setPlatformCode(StringUtil.toTrim(oldBean.getPlatformCode()));
				bean.setModuleCode(StringUtil.toTrim(oldBean.getModuleCode()));
				bean.setOperationCode(StringUtil.toTrim(oldBean.getOperationCode()));

				spicOption = ","+bean.getPlatformCode()+"|"+bean.getModuleCode()+"|"+bean.getOperationCode()+",";

				bean.setOperationName(StringUtil.toTrim(oldBean.getOperationName()));

				bean.setDescribe(StringUtil.toTrim(oldBean.getDescribe()));
				bean.setState(oldBean.getState()!=null?oldBean.getState():0);
				bean.setErrorDescribe(StringUtil.toTrim(oldBean.getErrorDescribe()));

				bean.setUserId(oldBean.getUserId()!=null?oldBean.getUserId():0);

				dObject = new JSONObject();
				dObject.put("createTime", DateHelper.formatDateString(StringUtil.toTrim(oldBean.getCreateTime()).replace("/", "-").replace(".0", ""),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_DATETIME).replace(" ", "T") );
				dObject.put("iDate", DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME).replace(" ", "T"));
				bean.setD(dObject);

				bean.setIpAddress(StringUtil.toTrim(oldBean.getIpAddress()));
				bean.setTotalTime(StringUtil.toTrim(oldBean.getTotalTime()+""));
				bean.setFullPath(StringUtil.toTrim(oldBean.getFullPath()));
				bean.setxRequestedWith(StringUtil.toTrim(oldBean.getxRequestedWith()));
				bean.setHost(StringUtil.toTrim(oldBean.getHost()));
				bean.setAcceptLanguage(StringUtil.toTrim(oldBean.getAcceptLanguage()));
				bean.setAccptEncoding(StringUtil.toTrim(oldBean.getAccptEncoding()));
				bean.setAccept(StringUtil.toTrim(oldBean.getAccept()));
				bean.setConnection(StringUtil.toTrim(oldBean.getConnection()));
				bean.setUserAgent(StringUtil.toTrim(oldBean.getUserAgent()));
				bean.setReferer(StringUtil.toTrim(oldBean.getReferer()));
				bean.setCookie(StringUtil.toTrim(oldBean.getCookie()));
				bean.setTaskCode(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME_MSEC));//获取当前时间戳(微秒)
				//格式化请求参数
				requestParam = StringUtil.toTrim(oldBean.getRequestParam());
				if(requestParam.startsWith("\"")&&requestParam.endsWith("\"")){
					requestParam = requestParam.substring(1,requestParam.length()-1);
				}
				bean.setRequestParam(requestParam);

				//格式化dataContent数据
				dataContent = StringUtil.toTrim(oldBean.getDataContent());
				if(dataContent.startsWith("\"")&&dataContent.endsWith("\"")){
					dataContent = dataContent.substring(1,dataContent.length()-1);
				}

				if(dataContent.startsWith("[")&&dataContent.endsWith("]")){
					try {
						jsArray = JSONArray.parseArray(dataContent);
						bean.setTaskType(1);//多条任务
						if(jsArray!=null&&jsArray.size()>0){
							for (Object object : jsArray) {
								newBean = getNewBean(bean);
								jsObject = JSONObject.parseObject(object.toString());
								if(jsObject.containsKey("dataId")){//若存在主键Id,则放入es日志对象中
									newBean.setDataId(StringUtil.toTrim(jsObject.get("dataId")+""));
								}
								tOptionArray = new JSONArray();
								if(bean.getOperationCode().indexOf("_error")==-1){//错误信息不拆分
									map = jsObject;
									for(Map.Entry<String, Object> entry:map.entrySet()){
										if(LogCommonData.SPIC_OPTION_VALUE.indexOf(spicOption)!=-1){//特殊操作类型取值判断
											if("aid".equals(entry.getKey())||"sourceType".equals(entry.getKey())||"type".equals(entry.getKey())){
												tObject = new JSONObject();
												tObject.put("tKey", entry.getKey());
												tObject.put("tValue", StringUtil.toTrim(entry.getValue()+"").replace("\"", "\\\""));
												tOptionArray.add(tObject);
											}
										}else {
											tObject = new JSONObject();
											tObject.put("tKey", entry.getKey());
											tObject.put("tValue", StringUtil.toTrim(entry.getValue()+"").replace("\"", "\\\""));
											tOptionArray.add(tObject);
										}
									}
									newBean.setDataContent(jsObject.toString());
								}else {
									if(bean.getOperationCode().indexOf("edit_EditArtFieldsValue_error")!=-1){
										newBean.setDataContent("");
									}else {
										newBean.setDataContent(jsObject.toString().replace(" ", "\r\n"));
									}
									newBean.setRequestParam(StringUtil.toTrim(newBean.getRequestParam()).replace(" ", "\r\n"));
								}
								newBean.settOperation(tOptionArray);
								list.add(newBean);
							}
						}
					} catch (Exception e) {
						bean.setTaskType(0);//若解析异常，则按string数据处理
						bean.setDataContent(dataContent);
						list.add(bean);
					}
				}else if(dataContent.startsWith("{")&&dataContent.endsWith("}")){
					try {
						jsObject = JSONObject.parseObject(dataContent);
						bean.setTaskType(0);//单条任务
						if(jsObject.containsKey("dataId")){//若存在主键Id,则放入es日志对象中
							bean.setDataId(StringUtil.toTrim(jsObject.get("dataId")+""));
						}
						// 判断是否是特殊的操作类型 - 需要进行url拆条的
						if(LogCommonData.SPICOPTION.indexOf(","+bean.getPlatformCode()+"|"+bean.getModuleCode()+"|"+bean.getOperationCode()+",")!=-1){
							if(jsObject.containsKey("url")){
								url = StringUtil.toTrim(jsObject.get("url")+"");
								if(url.indexOf("\r")!=-1||url.indexOf("\n")!=-1){
									url.replace("\r", "\n");
									urlArray = url.split("\\n");
									if(urlArray.length>1){
										bean.setTaskType(1);//多条任务
									}
									for (int i=0;i<urlArray.length;i++) {
										urlA = StringUtil.toTrim(urlArray[i]);
										if(!"".equals(urlA)){
											jsObject.put("url", urlA);
											newBean = getNewBean(bean);
											newBean.setDataId(urlA);
											tOptionArray = new JSONArray();
											map = jsObject;
											for(Map.Entry<String, Object> entry:map.entrySet()){
												tObject = new JSONObject();
												tObject.put("tKey", entry.getKey());
												tObject.put("tValue", StringUtil.toTrim(entry.getValue()+"").replace("\"", "\\\""));
												tOptionArray.add(tObject);
											}
											newBean.settOperation(tOptionArray);
											newBean.setDataContent(jsObject.toString());
											list.add(newBean);
										}
									}
								}else{
									urlArray = url.split("rnhttp://");
									if(urlArray.length>1){
										bean.setTaskType(1);//多条任务
									}
									for (int i=0;i<urlArray.length;i++) {
										if(i==0){
											urlA = StringUtil.toTrim(urlArray[i]);
										}else {
											urlA = "http://"+StringUtil.toTrim(urlArray[i]);
										}
										if(!"".equals(urlA)){
											jsObject.put("url", urlA);
											newBean = getNewBean(bean);
											newBean.setDataId(urlA);
											tOptionArray = new JSONArray();
											map = (Map<String, Object>)jsObject;
											for(Map.Entry<String, Object> entry:map.entrySet()){
												tObject = new JSONObject();
												tObject.put("tKey", entry.getKey());
												tObject.put("tValue", StringUtil.toTrim(entry.getValue()+"").replace("\"", "\\\""));
												tOptionArray.add(tObject);
											}
											newBean.settOperation(tOptionArray);
											newBean.setDataContent(jsObject.toString());
											list.add(newBean);
										}
									}
								}
							}
						}else {
							tOptionArray = new JSONArray();
							map = jsObject;
							if(bean.getOperationCode().indexOf("_error")==-1){//错误信息不拆分
								for(Map.Entry<String, Object> entry:map.entrySet()){
									if(LogCommonData.SPIC_OPTION_VALUE.indexOf(spicOption)!=-1){//特殊操作类型取值判断
										if("aid".equals(entry.getKey())||"sourceType".equals(entry.getKey())||"type".equals(entry.getKey())){
											tObject = new JSONObject();
											tObject.put("tKey", entry.getKey());
											tObject.put("tValue", StringUtil.toTrim(entry.getValue()+"").replace("\"", "\\\""));
											tOptionArray.add(tObject);
										}
									}else {
										tObject = new JSONObject();
										tObject.put("tKey", entry.getKey());
										tObject.put("tValue", StringUtil.toTrim(entry.getValue()+"").replace("\"", "\\\""));
										tOptionArray.add(tObject);
									}
								}
								bean.setDataContent(jsObject.toString());
							}else {
								if(bean.getOperationCode().indexOf("edit_EditArtFieldsValue_error")!=-1){
									bean.setDataContent("");
								}else {
									bean.setDataContent(jsObject.toString().replace(" ", "\r\n"));
								}
								bean.setRequestParam(StringUtil.toTrim(bean.getRequestParam()).replace(" ", "\r\n"));
							}
							bean.settOperation(tOptionArray);
							list.add(bean);
						}
					} catch (Exception e) {
						bean.setTaskType(0);//若解析异常，则按string数据处理
						bean.setDataContent(dataContent);
						list.add(bean);
					}
				}else {
					bean.setTaskType(0);//单条任务
					bean.setDataContent(dataContent);//既不是JSONObject也不是JSONArrary的数据按String数据处理
					list.add(bean);
				}
			}
		} catch (Exception e) {
			//解析失败时创建新的日志对象;
			LogerrorBean logBean = getLogerrorBean(JSON.toJSONString(oldBean), "解析异常", "日志解析过程中出现异常");
			addToKafka(JSON.toJSONString(logBean));
		}

		return list;
	}


	/**
	 * 获取新的对象
	 * @Title: getNewBean
	 * @data:2016-11-13上午11:57:08
	 * @author:liweiwei
	 *
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	private LogEsBean getNewBean(LogEsBean bean)throws Exception {
		LogEsBean newBean = new LogEsBean();
		newBean.setAccept(bean.getAccept());
		newBean.setAcceptLanguage(bean.getAcceptLanguage());
		newBean.setAccptEncoding(bean.getAccptEncoding());
		newBean.setConnection(bean.getConnection());
		newBean.setCookie(bean.getCookie());
		newBean.setD(bean.getD());
		newBean.setDataContent(bean.getDataContent());
		newBean.setDataId(bean.getDataId());
		newBean.setDescribe(bean.getDescribe());
		newBean.setErrorDescribe(bean.getErrorDescribe());
		newBean.setHost(bean.getHost());
		newBean.setIpAddress(bean.getIpAddress());
		newBean.setIsPCount(bean.getIsPCount());
		newBean.setModuleCode(bean.getModuleCode());
		newBean.setOperationCode(bean.getOperationCode());
		newBean.setOperationName(bean.getOperationName());
		newBean.setPlatformCode(bean.getPlatformCode());
		newBean.setReferer(bean.getReferer());
		newBean.setRequestParam(bean.getRequestParam());
		newBean.setState(bean.getState());
		newBean.setTaskCode(bean.getTaskCode());
		newBean.setTaskType(bean.getTaskType());
		newBean.settOperation(bean.gettOperation());
		newBean.setTotalTime(bean.getTotalTime());
		newBean.setUserAgent(bean.getUserAgent());
		newBean.setUserId(bean.getUserId());
		newBean.setxRequestedWith(bean.getxRequestedWith());
		newBean.setFullPath(bean.getFullPath());
		return newBean;
	}


	/**
	 * 获取日志对象sql
	 * @Title: getLogmainBean
	 * @data:2016-10-11下午5:13:00
	 * @author:liweiwei
	 *
	 * @param json
	 * @return
	 */
	private LogerrorBean getLogerrorBean(String json,String operationName,String describe)throws Exception{
		LogerrorBean bean = new LogerrorBean();

		bean.setPlatformCode(LogCommonData.platformCode);
		bean.setModuleCode("logDealRedis");
		bean.setOperationCode("logDealRedis_error");
		bean.setOperationName(operationName);
		bean.setDescribe(describe);
		bean.setDataContent(json);
		bean.setCreateTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));

		return bean;
	}

	/**
	 * 错误数据入卡夫卡
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 14:19 2017/11/27
	 * @param json
	 */
	private void addToKafka(String json){

		JSONObject obj;
		String result;
		try{
			obj = new JSONObject();
			obj.put("topicName","logTest");
			obj.put("sinKafKey","log_"+System.currentTimeMillis());
			obj.put("sinKafValue",json);

			result = HttpClientPoolUtil.execute(LogCommonData.LOG_KAFKA_URL,obj.toJSONString());
			if(!"success".equals(JSON.parseObject(result).getString("code"))){
				LogHelper.info("-----错误日志入卡夫卡失败！----json["+json+"]");
			}
		}catch (Exception e){
			LogHelper.error("-----错误日志入卡夫卡出现异常！---json["+json+"]------",e);
		}
	}
}
