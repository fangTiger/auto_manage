package com.xl.tf.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.tf.bean.TfBean;
import com.xl.tf.common.TfCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;

/**
 * 情感值处理
 * @Author:lww
 * @Date:15:33 2017/9/21
 */
public class TfAffectionService {

	/**
	 * 处理情感值
	 * @return com.xl.tf.bean.TfBean
	 * @Author: lww
	 * @Description:
	 * @Date: 15:46 2017/9/21
	 * @param bean
	 */
	public TfBean dealAffection(TfBean bean){
		JSONObject obj;
		String result;
		String datas;

		JSONObject jsonObject1;
		try{
			obj = new JSONObject();
			obj.put("title","");
			obj.put("content",bean.getStatusText());
			obj.put("mainObj","");
			obj.put("type","0");//走默认类型

			result = HttpClientPoolUtil.execute(TfCommonData.AFFECTION_URL,obj.toString());

			JSONObject jsonObject = JSONObject.parseObject(result);
			if("1".equals(jsonObject.getString("code"))){//请求成功
				datas = jsonObject.getString("datas");
				jsonObject1 = JSON.parseObject(datas);
				bean.setEmotionValue((int)Double.parseDouble(jsonObject1.getString("tone")));
			}else{//访问接口失败
				bean = null;
				LogHelper.info("-----------------访问情感值接口出现异常！url:"+TfCommonData.AFFECTION_URL+";param:"+obj.toString()+"-------------------");
			}
		}catch (Exception e){
			bean = null;
			LogHelper.error("------------获取情感值出现异常！ TfAffectionService.dealAffection  [param:"+JSONObject.toJSONString(bean)+"]--------",e);
		}
		return bean;
	}

	public static void main(String[] args) {
		JSONObject obj;
		String result;
		String datas;
		String dataArray[];
		int emotionValue = 0;

		try{
			obj = new JSONObject();
			obj.put("title","");
			obj.put("content","Brazil: Parliamentary commission rejects obstruction, criminal association charges against President Temer… https://t.co/ElK6ob1dMh");
			obj.put("mainObj","");
			obj.put("type","0");//走默认类型

			result = HttpClientPoolUtil.execute(TfCommonData.AFFECTION_URL,obj.toString());
			System.out.println(result);
//			JSONObject jsonObject = JSONObject.parseObject(result);
//			if("1".equals(jsonObject.getString("code"))){//请求成功
//				datas = jsonObject.getString("datas");
//				datas = datas.replace("<p>","");
//				dataArray = datas.split("</p>");
//
//				for(String data:dataArray){
//					if(data.indexOf("polarity")>-1){
//						emotionValue = (int) Double.parseDouble(StringUtil.toTrim(data.split("：")[1]));
//					}
//				}
//
//			}else{//访问接口失败
//				System.out.println("访问接口失败!");
//			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
