package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.LogEsBean;
import com.xl.manage.common.LogCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;
import org.omg.CORBA.StringHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志redis
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class LogInsertProService {


	/**
	 * 日志入redis
	 * @return com.xl.irtv.bean.BroadcastBean
	 * @Author: lww
	 * @Description:
	 * @Date: 16:56 2017/9/18
	 * @param bean
	 */
	public LogEsBean insertData(LogEsBean bean)throws Exception{

		System.out.println("--------------------日志入库！！-----------------"+ bean.getLogId());

		JSONObject jsonObj;
		JSONObject resultObj;
		String result = "";
		List<LogEsBean> list;
		try{

			updateField(bean);

			list = new ArrayList<>();
			list.add(bean);

			jsonObj = new JSONObject();
			jsonObj.put("operationCode",LogCommonData.LOG_INSERT_DATA_CODE);
			jsonObj.put("datas", JSONArray.toJSONString(list));
			jsonObj.put("tokenKey","");

			result = HttpClientPoolUtil.execute(LogCommonData.ES_CACHE_URL,jsonObj.toString());
			if(!"".equals(StringUtil.toTrim(result))){
				resultObj = JSONObject.parseObject(result);
				if(!"success".equals(resultObj.getString("code"))){
					return null;
				}
			}
		}catch (Exception e){
			bean = null;
			LogHelper.error("--------------------------日志入库service 出现异常！LogInsertProService.insertData ------------------------------",e);
		}

		return bean;
	}

	/**
	 * 特定更新,根据指定code更新
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 17:42 2017/12/29
	 * @param bean
	 */
	private void updateField(LogEsBean bean){

		String param = "{\"indexType\":\"INDEX_NAME_VALUE\",\"index\":\"\",\"mediaType\":\"MEDIATYPE_VALUE\",\"fields\":\"[{\\\"name\\\":\\\"orgsflag\\\",\\\"value\\\":\\\"ORGSFLAG_VALUE\\\",\\\"relationOperator\\\":\\\"ADD\\\"}]\",\"aid\":\"AID_VALUE\"}";

		JSONObject jsonObj;
		JSONArray jsonArray;
		String indexName = "";
		String aid="";
		String orgsflag = "";
		String contentId = "";
		String aids = "";
		String cId = "";
		String sourceType = "";
		String aidArray[];
		String result;
		if("translateAdd".equals(StringUtil.toTrim(bean.getOperationCode()))&&"贴翻译".equals(StringUtil.toTrim(bean.getOperationName()))){
			try{
				if(bean.gettOperation()!=null&&!"".equals(StringUtil.toTrim(bean.gettOperation().toString()))){
					jsonArray = JSON.parseArray(bean.gettOperation().toString());
					for(Object obj:jsonArray){
						jsonObj = JSON.parseObject(obj.toString());
						if("contentId".equals(StringUtil.toTrim(jsonObj.getString("tKey")))){
							contentId = jsonObj.getString("tValue");
						}else if("sourceType".equals(StringUtil.toTrim(jsonObj.getString("tKey")))){
							sourceType = jsonObj.getString("tValue");
						}else if("cId".equals(StringUtil.toTrim(jsonObj.getString("tKey")))){
							cId = jsonObj.getString("tValue");
						}else if("aids".equals(StringUtil.toTrim(jsonObj.getString("tKey")))){
							aids = jsonObj.getString("tValue");
						}
					}

					if(!"".equals(StringUtil.toTrim(aids))&&!"".equals(StringUtil.toTrim(cId))&&!"".equals(StringUtil.toTrim(sourceType))&&!"".equals(StringUtil.toTrim(contentId))){
						if(MEDIA_INDEX_MAP.containsKey(sourceType)){
							indexName = MEDIA_INDEX_MAP.get(sourceType);

							if("1".equals(StringUtil.toTrim(contentId))){
								orgsflag = cId+"_ft_0";
							}else if("2".equals(StringUtil.toTrim(contentId))){
								orgsflag = cId+"_fs_0";
							}else {
								LogHelper.info("！！！！！！！！！！！！！！！！！！！！贴翻译更新方法解析参数获取orgsflag出现异常！contentId["+contentId+"] logId["+bean.getLogId()+"] ！！！！！！！！！！！！！！！！！！！！！！！！！");
								return ;
							}
							param = param.replace("INDEX_NAME_VALUE",indexName).replace("ORGSFLAG_VALUE",orgsflag).replace("MEDIATYPE_VALUE",sourceType);
							aidArray = aids.split(",");
							for(String aidStr:aidArray){
								param = param.replace("AID_VALUE",aidStr);
								LogHelper.info("开始调用更新："+param);
								result = HttpClientPoolUtil.execute("http://192.168.10.72:8888/dataCenterSolution/data/postEsData",param);
								LogHelper.info("返回结果："+result);
								if(!"success".equals(StringUtil.toTrim(JSON.parseObject(result).getString("code")))){
									LogHelper.info("!!!!!!!!!调用更新接口失败！result["+result+"]!!!!!!!!!!");
								}
							}
						}else{
							LogHelper.info("！！！！！！！！！！！！！！！！！！！！贴翻译更新方法解析参数获取indexName出现异常！sourceType["+sourceType+"] logId["+bean.getLogId()+"] ！！！！！！！！！！！！！！！！！！！！！！！！！");
						}

					}else{
						LogHelper.info("！！！！！！！！！！！！！！！！！！！！贴翻译更新方法解析参数不完整 logId["+bean.getLogId()+"] ！！！！！！！！！！！！！！！！！！！！！！！！！");
					}

				}else{
					LogHelper.info("！！！！！！！！！！！！！！！！！！！！贴翻译更新方法解析参数为空 logId["+bean.getLogId()+"] ！！！！！！！！！！！！！！！！！！！！！！！！");
				}
			}catch (Exception e){
				LogHelper.error("贴翻译更新方法出现异常！ logId["+bean.getLogId()+"] ",e);
			}
		}
	}

	public static Map<String, String> MEDIA_INDEX_MAP = new HashMap<>();
	static{
		MEDIA_INDEX_MAP.put("weixin", "article");
		MEDIA_INDEX_MAP.put("app", "article");
		MEDIA_INDEX_MAP.put("press", "article");
		MEDIA_INDEX_MAP.put("web", "article");
		MEDIA_INDEX_MAP.put("bbs", "bbs");
		MEDIA_INDEX_MAP.put("broadcast", "broadcast");
		MEDIA_INDEX_MAP.put("weibo", "weibo");
	}
}
