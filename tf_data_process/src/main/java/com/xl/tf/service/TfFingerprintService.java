package com.xl.tf.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.tf.bean.TfBean;
import com.xl.tf.common.TfCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;

/**
 * 语义指纹处理
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class TfFingerprintService {

	/**
	 * 微博语义指纹处理
	 * @return com.xl.tf.bean.TfBean
	 * @Author: lww
	 * @Description:
	 * @Date: 17:01 2017/9/21
	 * @param bean
	 */
	public TfBean dealFingerprint(TfBean bean){
		JSONObject obj;
		JSONObject jsonObject;
		String result;
		long fingerprint = 0;
		try{
			obj = new JSONObject();
			obj.put("title","微博信息");
			obj.put("content",StringUtil.toTrim(bean.getStatusText()));
			obj.put("type","0");//走默认类型

			result = HttpClientPoolUtil.execute(TfCommonData.FINGERPRINT_URL,obj.toString());
			jsonObject = JSONObject.parseObject(result);
			if("1".equals(jsonObject.getString("code"))){//请求成功
				fingerprint = Long.parseLong(StringUtil.toTrim(jsonObject.getString("datas")));
				bean.setSig(fingerprint);
			}else{//访问接口失败
				bean = null;
				LogHelper.info("-----------------访问微博语指纹接口出现异常！url:"+TfCommonData.FINGERPRINT_URL+";param:"+obj.toString()+"-------------------");
			}
		}catch (Exception e){
			bean = null;
			LogHelper.error("------------获取微博语指纹出现异常！ TfFingerprintService.dealFingerprint  [param:"+JSONObject.toJSONString(bean)+"]--------",e);
		}
		return bean;
	}

	public static void main(String[] args) {
		JSONObject obj;
		String result;
		String datas;
		String dataArray[];
		int emotionValue = 0;

		String content = "#BREAKING Twin suicide bombers detonate themselves outside police headquarters in #Damascus, local media reports https://t.co/H1BR6stbwY";

		try{
			obj = new JSONObject();
			obj.put("title","\"微博信息\"");
			obj.put("content", content);
			obj.put("type","0");//走默认类型

			result = HttpClientPoolUtil.execute(TfCommonData.FINGERPRINT_URL,obj.toString());
//			3590595098927606260 1877054356458025654 916244129519130924 4286566814263024136 7838742445256350890
//			3590595098927606300 1877054356458025700 916244129519130900 4286566814263024000 7838742445256351000
			System.out.println(result);
			JSONObject jsonObject = JSONObject.parseObject(result);
			if("1".equals(jsonObject.getString("code"))){//请求成功
//				Long.p
				System.out.println(Long.parseLong(jsonObject.getString("datas")));
//				datas = jsonObject.getString("datas");
//				datas = datas.replace("<p>","");
//				dataArray = datas.split("</p>");
//
//				for(String data:dataArray){
//					if(data.indexOf("polarity")>-1){
//						emotionValue = (int) Double.parseDouble(StringUtil.toTrim(data.split("：")[1]));
//					}
//				}

			}else{//访问接口失败
				System.out.println("访问接口失败!");
			}
		}catch (Exception e){

		}
	}
}
