package com.xl.tf.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.tf.bean.TfBean;
import com.xl.tf.common.TfCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class TfInsertProService {


	/**
	 * 数据入redis
	 * @return com.xl.irtv.bean.BroadcastBean
	 * @Author: lww
	 * @Description:
	 * @Date: 16:56 2017/9/18
	 * @param bean
	 */
	public TfBean insertData(TfBean bean)throws Exception{

		JSONObject jsonObj;
		JSONObject resultObj;
		String result = "";
		List<TfBean> list;

		LogHelper.info("正在入库："+bean.getType()+" "+bean.getStatusId());

		try{

			if("1".equals(sendWarning(bean))){
				list = new ArrayList<>();
				list.add(bean);

				jsonObj = new JSONObject();
				jsonObj.put("operationCode",TfCommonData.TF_INSERT_ALLDATA_CODE);
				jsonObj.put("datas",JSONArray.toJSONString(list));
				jsonObj.put("tokenKey","");
				result =  HttpClientPoolUtil.execute(TfCommonData.ES_CACHE_URL,jsonObj.toString());
				if(!"".equals(StringUtil.toTrim(result))){
					resultObj = JSONObject.parseObject(result);
					if(!"success".equals(resultObj.getString("code"))){
						LogHelper.error("数据入库失败！jsonObj:"+jsonObj.toJSONString()+" ;result:"+result,new Exception(result));
						return null;
					}
				}
			}else{
				return null;
			}
		}catch (Exception e){
			bean = null;
			LogHelper.error("-------------------------- 数据入库出现异常！TfInsertProService.dealFormat ------------------------------",e);
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
	private String sendWarning(TfBean bean)throws Exception{

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

					result = HttpClientPoolUtil.execute(TfCommonData.ES_CACHE_URL,param.toJSONString());

					if(!"success".equals(JSON.parseObject(result).getString("code"))){
						LogHelper.info("调用预警接口失败！result:"+result);
						return "";
					}
				}
			}
			return "1";
		}catch (Exception e){
			LogHelper.error("调用预警接口出现异常！jsonObj:"+jsonObj.toString(),e);
		}
		return "";
	}
}
