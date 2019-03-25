package com.xl.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.bean.article.ArticleBean;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WebCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.PublicClass;
import com.xl.tool.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lww
 * @Date:15:13 2017/9/14
 */
public class WebExtractionProService {

	/**
	 * 实体抽取(包含语言提取)
	 * @return com.xl.manage.bean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 10:08 2017/11/3
	 * @param bean
	 */
	public ArticleBean dealExtraction(ArticleBean bean)throws Exception{

		String title = bean.getTitle();
		String content = bean.getContentText();

		JSONObject msgObj = new JSONObject();

		JSONObject obj = new JSONObject();
		obj.put("title",title);
		obj.put("content", PublicClass.StripHTML(content));
		obj.put("link","");
		obj.put("sourceType","");
		obj.put("type","0");

		String result = "";
		String resultReturn;
		try{
			result = HttpClientPoolUtil.execute(WebCommonData.EXTRACTION_URL,obj.toString());
			resultReturn = dealResult(bean,result);

			if("1".equals(resultReturn)){
				msgObj.put("msg",PublicClass.StripHTML(content));
				result = HttpClientPoolUtil.execute(WebCommonData.LANGUAGE_URL,msgObj.toString());
				bean.setLanguage(StringUtil.toTrim(result).replace("\"",""));
				return bean;
			}else{
				return null;
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"实体抽取(包含语言提取)", obj.toJSONString(),"实体抽取(包含语言提取)出现异常！ WebExtractionProService.dealExtraction",e);
			bean = null;
		}
		return bean;
	}

	/**
	 * 解析实体抽取结果
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 15:23 2017/9/18
	 * @param bean
	 * @param result
	 */
	private String dealResult(ArticleBean bean,String result)throws Exception{

		String msg ;
		JSONObject obj;
		JSONObject resultObj;

		String peopleStr = "";
		String locationStr = "";
		String mechanismStr = "";
		String mediaStr = "";
		String countryStr = "";
		String provinceStr = "";

		String resultReturn;
		try{
			if(!"".equals(StringUtil.toTrim(result))){
				obj = JSONObject.parseObject(result);
				if("1".equals(obj.getString("code"))){
					msg = obj.getString("datas");
					resultObj = JSONObject.parseObject(msg);

					peopleStr = resultObj.getString("people");
					locationStr = resultObj.getString("location");
					mechanismStr = resultObj.getString("organization");
					mediaStr = resultObj.getString("media");
					countryStr = resultObj.getString("country");
					provinceStr = resultObj.getString("province");
				}else{
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"调用实体抽取接口", result,"实体抽取获取失败！ ",new Exception(WebCommonData.EXTRACTION_URL+"调用失败!"));
					return "-1";
				}
			}
			bean.setPeopleExtraction(this.dealResult(peopleStr));
			bean.setLocationExtraction(this.dealResult(locationStr));
			bean.setMechanismExtraction(this.dealResult(mechanismStr));
			bean.setMediaExtraction(this.dealResult(mediaStr));
			bean.setCountryExtraction(this.dealResult(countryStr));
			bean.setProvinceExtraction(this.dealResult(provinceStr));
			resultReturn = "1";
		}catch (Exception e){
			resultReturn = "-1";
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"解析实体抽取结果", result,"解析实体抽取出结果出现异常！ WebExtractionProService.dealResult",e);
		}
		return resultReturn;
	}

	/**
	 * 处理返回结果
	 * @return java.util.List<java.lang.String>
	 * @Author: lww
	 * @Description:
	 * @Date: 15:23 2017/9/18
	 * @param result
	 */
	private List<String> dealResult(String result)throws Exception{

		List<String> resultList = new ArrayList<>();
		String[] resultArray;
		if(!"".equals(StringUtil.toTrim(result))){
			resultArray = result.split("#");
			for(String val:resultArray){
				if(!"".equals(StringUtil.toTrim(val))){
					resultList.add(val);
				}
			}
		}
		return resultList;
	}
}
