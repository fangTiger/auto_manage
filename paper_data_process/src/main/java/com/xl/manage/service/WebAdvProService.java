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
			}

			if("bbs".equals(bean.getMedia().getMediaType())){
				bean.setType("bbs");
			}
			//获取转载源
			this.pagesource(bean);
			bean.setPageSrc(PublicClass.replaceO(bean.getPageSrc(),"source"));

			if("".equals(StringUtil.toTrim(bean.getMedia().getMediaType()))||"null".equals(bean.getMedia().getMediaType())){
				bean.getMedia().setMediaType("epaper");
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"媒体信息获取", JSON.toJSONString(bean),"媒体信息获取出现异常！",e);
			bean = null;
		}
		return bean;
	}

	/// <summary>
	/// 判断来源转载
	/// </summary>
	private void pagesource(ArticleBean bean) {
        //region 判断来源转载是否是同一个网站
		String mediaNameCN;
		MediaResultBean ma;
		if (!"".equals(StringUtil.toTrim(bean.getMedia().getDomain())) && !"".equals(StringUtil.toTrim(bean.getPageSrc()))){

			if (bean.getMedia().getDomain().toLowerCase().contains(bean.getPageSrc().toLowerCase())){ //如果是同一个网站转载源为空  例如:http://www.pcauto.com.cn/nation/1005/10058811.html 来源是pcauto
				bean.setPageSrc("");
			}
		}

		if (!"".equals(bean.getMedia().getMediaNameCn())) {
			if (!"".equals(bean.getPageSrc()) && bean.getMedia().getMediaNameCn().toLowerCase().contains(bean.getPageSrc().toLowerCase())){ //如果网站名称包含转载源
				bean.setPageSrc("");
			}

			if (!"中国网".equals(bean.getPageSrc())  && !"中国网".equals(bean.getMedia().getMediaNameCn())) {
				mediaNameCN = bean.getMedia().getMediaNameCn().toLowerCase();

				if (!"".equals(bean.getPageSrc()) && bean.getPageSrc().toLowerCase().contains(mediaNameCN.endsWith("网")?mediaNameCN.substring(0,mediaNameCN.length()-1):mediaNameCN)){ //如果是转载源包含网站名称
					bean.setPageSrc("");
				}
			}
		}

		if (!"".equals(bean.getPageSrc()) && bean.getPageSrc().split(".").length > 1){
			ma = PublicClass.GetMediaType("web",bean.getMedia().getMediaNameCn(),  bean.getPageSrc(), bean.getPageSrc(), "");
			if (ma != null){
				bean.setPageSrc(ma.getM().getMediaNameCn());
			}
		}
	}

}
