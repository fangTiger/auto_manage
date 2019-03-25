package com.xl.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.esBean.ArticleBean;
import com.xl.manage.bean.esBean.resultBean.ArticleHitsBean;
import com.xl.manage.bean.esBean.resultBean.ArticleJsonRootBean;
import com.xl.manage.bean.esBean.resultBean.HitsArticleBean;
import com.xl.tool.LogHelper;
import scala.annotation.meta.param;

import java.util.ArrayList;
import java.util.List;

/**
 * 汽车数据处理流
 * @Author:lww
 * @Date:10:30 2017/9/8
 */
public class AutoDataProService {
	/**
	 * 获取汽车数据
	 * @return com.xl.manage.bean.esBean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:58 2017/9/11
	 * @param bean
	 */
	public String isAutoData(ArticleBean bean)throws Exception{

		String result;
		try {
			if(this.isPassSource(bean)){
				LogHelper.info("AID:"+bean.getAid());
				result = "1";//符合条件的数据
			}else{
				result = "2";//不需要的数据
			}
		}catch (Exception e){
			result = "0";
			LogHelper.error("----------------获取汽车数据处理过程出现异常！AutoDataProService.getAutoData------------",e);
		}
		return result;
	}

	/**
	 * 判断数据源是否符合
	 * @return boolean
	 * @Author: lww
	 * @Description:
	 * @Date: 15:45 2017/9/11
	 * @param articleBean
	 */
	private boolean isPassSource(ArticleBean articleBean)throws Exception{
		boolean isFlag = false;
		//网络+app判断
		if("web".equalsIgnoreCase(articleBean.getType())||"app".equalsIgnoreCase(articleBean.getType())){
			//自媒体判断
			if("newsSelfme".equalsIgnoreCase(articleBean.getMedia().getMediaType())||"appSelfme".equalsIgnoreCase(articleBean.getMedia().getMediaType())){
				//媒体分类是汽车
				if(articleBean.getMedia().getMediaCategory().contains("汽车")){
					isFlag = true;
				}
			}else{
				//媒体分类是汽车且导航有汽车
				if(articleBean.getMedia().getMediaCategory().contains("汽车")||articleBean.getNavigator().indexOf("汽车")>-1){
					isFlag = true;
				}
			}
		}else if("press".equalsIgnoreCase(articleBean.getType())){
			//媒体分类是汽车
			if(articleBean.getMedia().getMediaCategory().contains("汽车")||articleBean.getLayout().indexOf("汽车")>-1||articleBean.getLayout().toLowerCase().indexOf("suv")>-1){
				isFlag = true;
			}
		}else if("weixin".equalsIgnoreCase(articleBean.getType())){
			//媒体分类是汽车
			if(articleBean.getMedia().getMediaCategory().contains("汽车")){
				isFlag = true;
			}
		}
		return isFlag;
	}
}
