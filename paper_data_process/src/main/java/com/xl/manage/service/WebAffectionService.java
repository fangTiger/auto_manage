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
 * 情感值处理
 * @Author:lww
 * @Date:15:33 2017/9/21
 */
public class WebAffectionService {

	/**
	 * 处理情感值
	 * @return com.xl.tf.bean.TfBean
	 * @Author: lww
	 * @Description:
	 * @Date: 15:46 2017/9/21
	 * @param bean
	 */
	public ArticleBean dealAffection(ArticleBean bean){
		JSONObject obj;
		String result;
		String datas;
		JSONObject jsonObject1;
		try{
			if(!"".equals(StringUtil.toTrim(PublicClass.StripHTML(bean.getContentText())))){
				obj = new JSONObject();
				obj.put("title",bean.getTitle());
				obj.put("content",StringUtil.toTrim(PublicClass.StripHTML(bean.getContentText())));
				obj.put("mainObj","");
				obj.put("type","0");//走默认类型

				result = HttpClientPoolUtil.execute(WebCommonData.AFFECTION_URL,obj.toString());

				JSONObject jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
					datas = jsonObject.getString("datas");
					jsonObject1 = JSON.parseObject(datas);
					bean.setEmotionValue((int)Double.parseDouble(jsonObject1.getString("tone")));
				}else{//访问接口失败
					bean = null;
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"访问情感值接口", JSONObject.toJSONString(bean),"访问情感值接口失败！result:"+result,new Exception("情感值接口访问失败!"));
				}
			}else{
				bean.setEmotionValue(0);
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取情感值", JSONObject.toJSONString(bean),"获取情感值出现异常！",e);
			bean = null;
		}
		return bean;
	}

}
