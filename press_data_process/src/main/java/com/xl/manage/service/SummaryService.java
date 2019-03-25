package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.ArticleBean;
import com.xl.manage.common.CommonData;
import com.xl.manage.common.LogCommonData;
import com.xl.tools.HttpClientPoolUtil;
import com.xl.tools.LogHelper;
import com.xl.tools.PublicClass;
import com.xl.tools.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 关键词摘要处理
 * @Author:lww
 * @Date:11:24 2017/11/3
 */
public class SummaryService {

	/**
	 * 关键词摘要处理
	 * @return com.xl.manage.bean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 11:25 2017/11/3
	 * @param bean
	 */
	public ArticleBean dealSummary(ArticleBean bean)throws Exception{
		String result = "";
		JSONObject jsonObj = new JSONObject();
		JSONObject resultObj;
		JSONArray resultArray;
		JSONObject keyObj;

		List<String> keyList = new ArrayList<>();
		String keyStr="";
		String summary="";
		try{
			jsonObj.put("fenci","");
			jsonObj.put("title",bean.getTitle());
			jsonObj.put("content", PublicClass.StripHTML(bean.getContentText()));
			jsonObj.put("limit",10);
			jsonObj.put("type","0");

			result = HttpClientPoolUtil.execute(CommonData.KEYEXTRACT_URL,jsonObj.toJSONString());
			resultObj = JSON.parseObject(result);
			if("1".equals(StringUtil.toTrim(resultObj.getString("code")))){
				keyStr = resultObj.getString("datas");
				resultArray = JSON.parseArray(keyStr);
				for(int i=0;i<resultArray.size();i++){
					keyObj = resultArray.getJSONObject(i);
					keyList.add(keyObj.getString("keyword"));
				}
				bean.setKeyword(keyList);
			}else{
				LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取关键词",JSON.toJSONString(jsonObj),"关键词处理出现异常！"+result,new Exception("关键词获取出现异常！"));
				return null;
			}

			jsonObj = new JSONObject();
			jsonObj.put("keywords",keyStr);
			jsonObj.put("title",bean.getTitle());
			jsonObj.put("content", PublicClass.StripHTML(bean.getContentText()));
			jsonObj.put("limit",3);
			jsonObj.put("type","0");

			result = HttpClientPoolUtil.execute(CommonData.SUMMARY_URL,jsonObj.toJSONString());
			resultObj = JSON.parseObject(result);
			if("1".equals(StringUtil.toTrim(resultObj.getString("code")))){
				summary = resultObj.getString("datas");
				bean.setSummary(summary);
			}else{
				LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取摘要",JSON.toJSONString(jsonObj),"摘要处理出现异常！"+result,new Exception("摘要获取出现异常！"));
				return null;
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取摘要",JSON.toJSONString(jsonObj),"摘要处理出现异常！"+result,new Exception("摘要获取出现异常！"));
			bean = null;
		}
		return bean;
	}

	public static void main(String[] args) {
		String title="DO NOT GO GENTLE INTO THAT WILDDNESS荒野绅士全新揽胜星脉";
		String content="<p>　　&lt;p&gt;DO NOT GO GENTLE INTO THAT WILDDNESS荒野绅士全新揽胜星脉&lt;/p&gt;</p>";

		String result = "";
		JSONObject jsonObj = new JSONObject();
		JSONObject resultObj;
		JSONArray resultArray;
		JSONObject keyObj;

		List<String> keyList = new ArrayList<>();
		String keyStr="";
		String summary="";
		try{
			jsonObj.put("fenci","");
			jsonObj.put("title",title);
			jsonObj.put("content", PublicClass.noHTML(content));
			jsonObj.put("limit",10);
			jsonObj.put("type","0");

			result = HttpClientPoolUtil.execute(CommonData.KEYEXTRACT_URL,jsonObj.toJSONString());
			resultObj = JSON.parseObject(result);
			if("1".equals(StringUtil.toTrim(resultObj.getString("code")))){
				keyStr = resultObj.getString("datas");
				resultArray = JSON.parseArray(keyStr);
				for(int i=0;i<resultArray.size();i++){
					keyObj = resultArray.getJSONObject(i);
					keyList.add(keyObj.getString("keyword"));
				}

			}
			System.out.println(JSON.toJSONString(keyList));

			jsonObj = new JSONObject();
			jsonObj.put("keywords",keyStr);
			jsonObj.put("title",title);
			jsonObj.put("content", PublicClass.noHTML(content));
			jsonObj.put("limit",10);
			jsonObj.put("type","0");

			result = HttpClientPoolUtil.execute(CommonData.SUMMARY_URL,jsonObj.toJSONString());
			resultObj = JSON.parseObject(result);
			if("1".equals(StringUtil.toTrim(resultObj.getString("code")))){
				summary = resultObj.getString("datas");
			}
			System.out.println(summary);
		}catch (Exception e){
			LogHelper.info("----------关键词摘要处理-----------"+e.getMessage());
		}
	}
}
