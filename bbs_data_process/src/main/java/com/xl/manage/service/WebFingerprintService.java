package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.bean.article.ArticleBean;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WebCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.PublicClass;
import com.xl.tool.StringUtil;

/**
 * 语义指纹处理
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class WebFingerprintService {

	/**
	 * 语义指纹处理
	 * @return com.xl.manage.bean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 10:17 2017/11/3
	 * @param bean
	 */
	public ArticleBean dealFingerprint(ArticleBean bean){

		JSONObject obj;
		JSONObject jsonObject;
		String result;
		long fingerprint = 0;
		try{

			if("".equals(StringUtil.toTrim(bean.getTitle()))){
				bean.getSigs().setIndexSig(0l);
			}else{
				obj = new JSONObject();
				obj.put("title",bean.getTitle());
				obj.put("type","0");//走默认类型

				result = HttpClientPoolUtil.execute(WebCommonData.INDEX_SIG_URL,obj.toString());
				jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
					fingerprint = Long.parseLong(StringUtil.toTrim(jsonObject.getString("datas")));
					bean.getSigs().setIndexSig(fingerprint);
				}else{//访问接口失败
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"访问indexSig语指纹接口", obj.toJSONString(),"访问indexSig语指纹接口出现异常！",new Exception(WebCommonData.INDEX_SIG_URL+" 访问异常！"));
					return null;
				}
			}

			if("".equals(StringUtil.toTrim(bean.getContentText()))||"".equals(StringUtil.toTrim(PublicClass.StripHTML(bean.getContentText())))){
				bean.getSigs().setIndexSigall(bean.getSigs().getIndexSig());
			}else{
				obj = new JSONObject();
				obj.put("title", bean.getTitle());
				obj.put("content", PublicClass.StripHTML(bean.getContentText()));
				obj.put("limit", 10);
				obj.put("keyword", "");
				obj.put("splitInfo", "");
				obj.put("type","0");//走默认类型
				result = HttpClientPoolUtil.execute(WebCommonData.INDEX_SIG_ALL_URL,obj.toString());
				jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
					fingerprint = Long.parseLong(StringUtil.toTrim(jsonObject.getString("datas")));
					bean.getSigs().setIndexSigall(fingerprint);
				}else{//访问接口失败
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"访问indexSigAll语指纹接口", obj.toJSONString(),"访问indexSigAll语指纹接口出现异常！",new Exception(WebCommonData.INDEX_SIG_ALL_URL+" 访问异常！"));
					return null;
				}
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"语义指纹处理", JSON.toJSONString(bean),"获取语义指纹出现异常！ WebFingerprintService.dealFingerprint ",e);
			bean = null;
		}
		return bean;
	}

}
