package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.KafkaPdfArticleBean;
import com.xl.bean.article.ArticleBean;
import com.xl.manage.common.CommonData;
import com.xl.manage.common.LogCommonData;
import com.xl.tools.HttpClientPoolUtil;
import com.xl.tools.LogHelper;
import com.xl.tools.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * @Author:lww
 * @Date:15:31 2018/3/7
 */
public class PDFProService {


	public static void main(String[] args) throws UnsupportedEncodingException {


//		String url2 = "http://192.168.10.29/pdf/大河报/DHB2018-03-22-A104.pdf";
//
//		System.out.println(URLEncoder.encode(url2,"utf-8").replace("%3A",":").replace("%2F",":"));
	}

	/**
	 * 根据PDF获取JPG
	 * @return com.xl.manage.bean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:44 2018/3/21
	 * @param bean
	 */
	public ArticleBean changeImage(ArticleBean bean){

		JSONObject jsonObject = new JSONObject();
		JSONObject resultObj;

		String result;

		try{

			if(bean.get_pdfPath().endsWith("/0")){//以/0结尾的 表示没有PDF
				//TODO 处理大图转整版图
				return bean;
			}else if("http://192.168.10.29/pdf/".equals(StringUtil.toTrim(bean.get_pdfPath()))){
				return bean;
			}else if("2843160214209836524".equals(bean.getAid())||"2843160526696091799".equals(bean.getAid())){
				return bean;
			}

			jsonObject.put("aid",bean.getAid());
			jsonObject.put("type",bean.getType());
			jsonObject.put("url",URLEncoder.encode(bean.get_pdfPath(),"utf-8").replace("%3A",":").replace("%2F","/"));
			jsonObject.put("oldUrl",URLEncoder.encode(bean.getFilePath(),"utf-8").replace("%3A",":").replace("%2F","/"));
			jsonObject.put("dispTime",bean.getD().getDispTime());
			jsonObject.put("mediaName",bean.getMedia().getMediaNameCn());
			jsonObject.put("artUrl",bean.getUrl());

			result = HttpClientPoolUtil.execute(CommonData.PDF_PROCESS_URL,jsonObject.toJSONString());
			if(result!=null&&!"".equals(StringUtil.toTrim(result))){
				resultObj = JSONObject.parseObject(result);
				if(!"1".equals(resultObj.getString("code"))){
					LogHelper.info("图片处理失败！");
					return null;
				}
			}else{
				KafkaPdfArticleBean kafkaPdfArticleBean = new KafkaPdfArticleBean();
				kafkaPdfArticleBean.setUrl(bean.getUrl());
				kafkaPdfArticleBean.setImageName(bean.get_articleThumbName());
				kafkaPdfArticleBean.setCrawlType(bean.getCrawlType());
				kafkaPdfArticleBean.setTitle(bean.getTitle());
				kafkaPdfArticleBean.setContentText(bean.getContentText());
				LogHelper.sendMessageTimeOut(kafkaPdfArticleBean);
			}
			return bean;
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"PDF转图片", JSON.toJSONString(bean),"PDF转图片出现异常！",e);
		}
		return null;

	}
}
