package com.xl.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.KafkaPdfArticleBean;
import com.xl.bean.article.ArticleBean;
import com.xl.manage.common.CommonData;
import com.xl.tools.HttpClientPoolUtil;
import com.xl.tools.LogHelper;
import com.xl.tools.StringUtil;

import java.net.URLEncoder;

/**
 * 缩略图处理
 * @Author:lww
 * @Date:18:24 2018/3/28
 */
public class ThumbService {

	public static void main(String[] args) {
		String url = "http://192.168.10.29/pdf/东阳日报/DYRB2018-04-03-00010.jpg+";
		if(url.endsWith("+")){
			System.out.println(url.substring(0,url.length()-1));
		}
	}

	/**
	 * 根据PDF获取JPG
	 * @return com.xl.manage.bean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:44 2018/3/21
	 * @param bean
	 */
	public ArticleBean changeThumbImage(ArticleBean bean){

		JSONObject jsonObject = new JSONObject();
		JSONObject resultObj;

		String result;

		String url;
		try{
			if("http://192.168.10.29/pdf/".equals(StringUtil.toTrim(bean.get_articleThumbName()))){//若缩略图不完整，发预警，不处理
				return bean;
			}

			jsonObject.put("aid",bean.getAid());
			jsonObject.put("type",bean.getType());
			jsonObject.put("url", URLEncoder.encode(StringUtil.toTrim(bean.get_articleThumbName()),"utf-8").replace("%3A",":").replace("%2F","/"));
			jsonObject.put("oldUrl",URLEncoder.encode(bean.getPageImage(),"utf-8").replace("%3A",":").replace("%2F","/"));
			jsonObject.put("dispTime",bean.getD().getDispTime());
			jsonObject.put("mediaName",bean.getMedia().getMediaNameCn());
			jsonObject.put("artUrl",bean.getUrl());

			result = HttpClientPoolUtil.execute(CommonData.JPG_PROCESS_URL,jsonObject.toJSONString());
			if(result!=null&&!"".equals(StringUtil.toTrim(result))){
				resultObj = JSONObject.parseObject(result);
				if(!"1".equals(resultObj.getString("code"))){
					KafkaPdfArticleBean kafkaPdfArticleBean = new KafkaPdfArticleBean();
					kafkaPdfArticleBean.setUrl(bean.getUrl());
					kafkaPdfArticleBean.setImageName(bean.get_articleThumbName());
					kafkaPdfArticleBean.setCrawlType(bean.getCrawlType());
					kafkaPdfArticleBean.setTitle(bean.getTitle());
					kafkaPdfArticleBean.setContentText(bean.getContentText());
					LogHelper.sendMessage(kafkaPdfArticleBean);
					return bean;
				}
			}
			return bean;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
