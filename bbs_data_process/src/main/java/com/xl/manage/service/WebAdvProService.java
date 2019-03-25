package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.xl.basic.bean.MediaResultBean;
import com.xl.bean.article.ArticleBean;
import com.xl.manage.common.LogCommonData;
import com.xl.tool.LogHelper;
import com.xl.tool.PublicClass;
import com.xl.tool.StringUtil;

/**
 * 广告价值处理
 * @Author:lww
 * @Date:10:47 2017/11/3
 */
public class WebAdvProService {

	/**
	 * 广告价值处理
	 * @return com.xl.manage.bean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 11:14 2017/11/3
	 * @param bean
	 */
	public ArticleBean dealData(ArticleBean bean)throws Exception{

		MediaResultBean resultBean;
		try{
			resultBean = PublicClass.GetMediaType(bean.getType(),bean.getMedia().getMediaNameCn(),bean.getMedia().getSite(),bean.getMedia().getDomain(),"");
			if(resultBean!=null){
				bean.setAdv(resultBean.getA());
				bean.setMedia(resultBean.getM());
				if(!"".equals(StringUtil.toTrim(resultBean.getLayout()))&&"".equals(StringUtil.toTrim(bean.getLayout()))){
					bean.setLayout(StringUtil.toTrim(resultBean.getLayout()));
				}
				if("news".equals(bean.getMedia().getMediaType())){
					bean.getMedia().setMediaType("bbs");
				}
			}

			bean.setPageSrc(PublicClass.replaceO(bean.getPageSrc(),"source"));

		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"媒体信息获取", JSON.toJSONString(bean),"媒体信息获取出现异常！",e);
			bean = null;
		}
		return bean;
	}

}
