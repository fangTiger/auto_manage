package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.MediaResultBean;
import com.xl.bean.article.ArticleBean;
import com.xl.manage.common.CommonData;
import com.xl.manage.common.LogCommonData;
import com.xl.tools.HttpClientPoolUtil;
import com.xl.tools.LogHelper;
import com.xl.tools.PublicClass;
import com.xl.tools.StringUtil;

/**
 * 广告价值处理
 * @Author:lww
 * @Date:10:47 2017/11/3
 */
public class AdvProService {

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

			//TODO 20180113 调整排重在处理完PDF和缩略图之后
			if(isRepeat(bean.getAid(),"","press")){//重复数据
				bean = new ArticleBean();
				bean.setAid("-1");
				return bean;
			}

			resultBean = PublicClass.GetMediaType(bean.getType(),bean.getMedia().getMediaNameCn(),bean.getMedia().getSite(),bean.getMedia().getDomain());
			if(resultBean!=null){
				bean.setAdv(resultBean.getA());
				bean.setMedia(resultBean.getM());
			}
			//重新计算广告价值（ocr）面积乘广告价值单价
			bean.getAdv().setAdvPrice(0f);
			bean.setPageSrc(PublicClass.replaceO(bean.getPageSrc(),"source"));

			if("".equals(StringUtil.toTrim(bean.getMedia().getMediaType()))||"press".equals(StringUtil.toTrim(bean.getMedia().getMediaType()))||"null".equals(StringUtil.toTrim(bean.getMedia().getMediaType()))){
				bean.getMedia().setMediaType("paper");
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"媒体信息获取", JSON.toJSONString(bean),"媒体信息获取出现异常！",e);
			bean = null;
		}
		return bean;
	}

	/**
	 * 判断排重库是有已存在 (true-存在 false-不存在)
	 * @return boolean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:37 2018/1/10
	 * @param aid
	 * @param url
	 * @param type
	 */
	private boolean isRepeat(String aid,String url,String type){

		JSONObject jsonObject;
		JSONObject param = new JSONObject();

		String result;
		try{
			param.put("mediaType",type);
			param.put("dbKey",aid);
			param.put("url",StringUtil.toTrim(url));

			result = HttpClientPoolUtil.execute(CommonData.DATA_NOREPEAT_URL,param.toJSONString());

			jsonObject = JSONObject.parseObject(result);
			if(jsonObject!=null){
				if("1".equals(StringUtil.toTrim(jsonObject.getString("code")))){
					if(!"".equals(StringUtil.toTrim(jsonObject.getString("datas")))){
						return true;
					}else{
						return false;
					}
				}else{
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"数据排重接口调用", JSON.toJSONString(param),"调用排重接口失败！",new Exception("数据排重接口调用失败！"));
				}
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"数据排重", JSON.toJSONString(param),"数据排重出现异常！",e);
		}
		return false;
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
			ma = PublicClass.GetMediaType("web",  bean.getPageSrc(), bean.getPageSrc(), "");
			if (ma != null){
				bean.setPageSrc(ma.getM().getMediaNameCn());
			}
		}
	}

}
