package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xl.manage.bean.WeiboBean;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WeiboCommonData;
import com.xl.tool.DateHelper;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class WeiboInsertProService {


	/**
	 * 数据入redis
	 * @return com.xl.irtv.bean.BroadcastBean
	 * @Author: lww
	 * @Description:
	 * @Date: 16:56 2017/9/18
	 * @param bean
	 */
	public WeiboBean insertData(WeiboBean bean)throws Exception{

		System.out.println("--------------------入库！！-------------------AID:"+bean.getStatusId()+"\t content:"+bean.getStatusText());

		JSONObject jsonObj;
		JSONObject resultObj;
		String result;
		List<WeiboBean> list;
		try{
			//设置入库时间
			bean.getD().setIdate(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));

			//TODO 测试
			/*if(bean.getOrgs()!=null&&!bean.getOrgs().isEmpty()){
				list = new ArrayList<>();
				list.add(bean);

				jsonObj = new JSONObject();
				jsonObj.put("esType","ValidData");
				jsonObj.put("sourceType","WeiBo");
				jsonObj.put("storageType","OftenData");
				jsonObj.put("paramsList",JSONArray.toJSONString(list,SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));

				result = HttpClientPoolUtil.execute(WeiboCommonData.INSERT_DATA_ES,jsonObj.toString());
				if(!"".equals(StringUtil.toTrim(result))){
					resultObj = JSONObject.parseObject(result);
					if(!"success".equals(resultObj.getString("code"))){
						return null;
					}
				}

				bean.setExtraction(new ArrayList<>());
				bean.setMonitors(new ArrayList());
				bean.setOrgs(new ArrayList<>());
				bean.setOrgsflag(new ArrayList<>());
				bean.setWarnings(new ArrayList<>());
				bean.setLabels(new ArrayList<>());

				list = new ArrayList<>();
				list.add(bean);

				jsonObj = new JSONObject();
				jsonObj.put("esType","AllData");
				jsonObj.put("sourceType","WeiBo");
				jsonObj.put("storageType","OftenData");
				jsonObj.put("paramsList",JSONArray.toJSONString(list,SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));

				result = HttpClientPoolUtil.execute(WeiboCommonData.INSERT_DATA_ES,jsonObj.toString());
				if(!"".equals(StringUtil.toTrim(result))){
					resultObj = JSONObject.parseObject(result);
					if(!"success".equals(resultObj.getString("code"))){
						return null;
					}
				}
			}*/


			//TODO 正式
			if("1".equals(sendWarning(bean))){
				list = new ArrayList<>();
				list.add(bean);

				jsonObj = new JSONObject();
				jsonObj.put("operationCode",WeiboCommonData.WEIBO_INSERT_ALLDATA_CODE);
				jsonObj.put("datas", JSONArray.toJSONString(list));
				jsonObj.put("tokenKey","");

				result = HttpClientPoolUtil.execute(WeiboCommonData.ES_CACHE_URL,jsonObj.toString());
				if(!"".equals(StringUtil.toTrim(result))){
					resultObj = JSONObject.parseObject(result);
					if(!"success".equals(resultObj.getString("code"))){
						LogHelper.error(LogCommonData.LOG_CODE_WEB,"数据调用入redis接口失败",JSON.toJSONString(jsonObj),"数据调用入redis接口失败！"+result,new Exception(result));
						return null;
					}
				}
			}else{
				return null;
			}

		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"数据入库失败",JSON.toJSONString(bean),"数据入库出现异常！",e);
			bean = null;
		}

		return bean;
	}

	/**
	 * 发送预警
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 18:11 2018/1/2
	 * @param bean
	 */
	private String sendWarning(WeiboBean bean)throws Exception{

		String result = "";

		String warningArray[];
		JSONObject jsonObj = new JSONObject();
		JSONObject param = new JSONObject();

		try{
			if(bean.getWarnings()!=null&&bean.getWarnings().size()>0){
				for(String warnings:bean.getWarnings()){
					warningArray = warnings.split("_");
					jsonObj.put("id",bean.getStatusId());// 文章AID
					jsonObj.put("wid",warningArray[1]);// 预警ID
					jsonObj.put("type",bean.getType());// 文章类型
					jsonObj.put("title",bean.getStatusText());// 文章标题
					jsonObj.put("link",bean.getTextUrl());// 文章连接
					jsonObj.put("disptime",bean.getD().getDispTime().replace("T"," "));// 发布时间
					jsonObj.put("orgid",warningArray[0]);// 客户ID

					param.put("operationCode","alertwarning");
					param.put("datas",jsonObj.toJSONString());
					param.put("tokenKey","");
					param.put("timeOutFlag","false");
					param.put("time",0);

					result = HttpClientPoolUtil.execute(WeiboCommonData.ES_CACHE_URL,param.toJSONString());

					if(!"success".equals(JSON.parseObject(result).getString("code"))){
						LogHelper.error(LogCommonData.LOG_CODE_WEB,"调用预警接口",JSON.toJSONString(bean),"调用预警接口失败！result:"+result,new Exception("预警接口调用失败！"));
						return "";
					}
				}
			}
			return "1";
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"发送预警",JSON.toJSONString(bean),"调用预警接口出现异常！",e);
		}
		return "";
	}

	private String getSourceType(String type){
		if("bbs".equals(StringUtil.toTrim(type).toLowerCase())){
			return "BBS";
		}else if("weibo".equals(StringUtil.toTrim(type).toLowerCase())){
			return "WeiBo";
		}else if("tf".equals(StringUtil.toTrim(type).toLowerCase())){
			return "Tf";
		}else if("broadcast".equals(StringUtil.toTrim(type).toLowerCase())){
			return "Broadcast";
		}else{
			return "Article";
		}
	}

}
