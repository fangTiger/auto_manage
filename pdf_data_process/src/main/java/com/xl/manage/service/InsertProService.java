package com.xl.manage.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xl.basic.bean.AtomicIntegerBean;
import com.xl.bean.article.ArticleBean;
import com.xl.common.StormCommonData;
import com.xl.manage.bean.ArticleInfoTempBean;
import com.xl.manage.common.CommonData;
import com.xl.manage.common.LogCommonData;
import com.xl.tools.DateHelper;
import com.xl.tools.HttpClientPoolUtil;
import com.xl.tools.LogHelper;
import com.xl.tools.StringUtil;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据入库
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class InsertProService {

	/**
	 * 数据入redis
	 * @return com.xl.irtv.bean.BroadcastBean
	 * @Author: lww
	 * @Description:
	 * @Date: 16:56 2017/9/18
	 * @param bean
	 */
	public ArticleBean insertData(ArticleBean bean)throws Exception{

		LogHelper.info("--------------------入库！！总量["+ AtomicIntegerBean.get()+"]-------------------crawlTime:"+bean.getD().getCrawlTime()+"\t mediaName:"+bean.getMedia().getMediaNameCn()+"\t URL:"+bean.getUrl()+"\t AID:"+bean.getAid()+"\t title:"+bean.getTitle());
		JSONObject jsonObj;
		JSONObject resultObj;
		JSONObject jsonObject;
		String result = "";
		List<ArticleBean> list;
		try{
			//TODO 测试
			/*if(bean.getOrgs()!=null&&!bean.getOrgs().isEmpty()){
				list = new ArrayList<>();
				list.add(bean);

				jsonObj = new JSONObject();
				jsonObj.put("esType","ValidData");
				jsonObj.put("sourceType",this.getSourceType(bean.getType()));
				jsonObj.put("storageType","OftenData");
				jsonObj.put("paramsList",JSONArray.toJSONString(list,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty));
				result = HttpClientPoolUtil.execute(CommonData.INSERT_DATA_ES,jsonObj.toString());
				if(!"".equals(StringUtil.toTrim(result))){
					resultObj = JSONObject.parseObject(result);
					if(!"success".equals(resultObj.getString("code"))){
						return null;
					}
				}
			}

			bean.setExtraction(new ArrayList<>());
			bean.setOrgs(new ArrayList<>());
			bean.setOrgsflag(new ArrayList<>());
			bean.setWarnings(new ArrayList<>());
			bean.setLabels(new ArrayList<>());
			bean.setMonitors(new ArrayList<>());

			list = new ArrayList<>();
			list.add(bean);

			jsonObj = new JSONObject();
			jsonObj.put("esType","AllData");
			jsonObj.put("sourceType",this.getSourceType(bean.getType()));
			jsonObj.put("storageType","OftenData");
			jsonObj.put("paramsList",JSONArray.toJSONString(list,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullStringAsEmpty));
			result = HttpClientPoolUtil.execute(CommonData.INSERT_DATA_ES,jsonObj.toString());
			if(!"".equals(StringUtil.toTrim(result))){
				resultObj = JSONObject.parseObject(result);
				if(!"success".equals(resultObj.getString("code"))){
					return null;
				}
			}*/

			//TODO 正式
			if("1".equals(sendWarning(bean))) {
				list = new ArrayList<>();
				list.add(bean);

				jsonObj = new JSONObject();
				jsonObj.put("operationCode",CommonData.WEB_INSERT_ALLDATA_CODE);
				jsonObj.put("datas", JSONArray.toJSONString(list, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
				jsonObj.put("tokenKey","");

				result = HttpClientPoolUtil.execute(CommonData.ES_CACHE_URL,jsonObj.toString());
				if(!"".equals(StringUtil.toTrim(result))){
					resultObj = JSONObject.parseObject(result);
					if(!"success".equals(resultObj.getString("code"))){
						LogHelper.error(LogCommonData.LOG_CODE_WEB,"数据调用入redis接口失败",JSON.toJSONString(jsonObj),"数据调用入redis接口失败！"+result,new Exception(result));
						return null;
					}else{
						insertZen(bean);
						//成功入库入统计
						HttpClientPoolUtil.execute(CommonData.ADD_SUCESS_PROCESS_URL.replace("DATE_VALUE", DateHelper.getNowDate(DateHelper.FMT_DATE_YYYYMMDD)).replace("MEDIANAME_VALUE",bean.getMedia().getMediaNameCn()));
						if(!"".equals(StringUtil.toTrim(bean.get_ewID()))){
							jsonObject = new JSONObject();
							jsonObject.put("layoutMediaId",bean.get_ewID());
							jsonObject.put("lastDispTime",new Date(DateHelper.formatStringToLong(bean.getD().getDispTime(),DateHelper.FMT_DATE_DATETIME)));
							jsonObject.put("lastCrawlTime",new Date(DateHelper.formatStringToLong(bean.getD().getCrawlTime(),DateHelper.FMT_DATE_DATETIME)));
							jsonObject.put("countDate",new Date(DateHelper.formatStringToLong(DateHelper.formatDateString(bean.getD().getCrawlTime(),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_YYYY_MM_DD),DateHelper.FMT_DATE_YYYY_MM_DD)));
							jsonObject.put("gmtCreate",new Date(DateHelper.formatStringToLong(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME),DateHelper.FMT_DATE_DATETIME)));
							result = HttpClientPoolUtil.execute(StormCommonData.CRAWL_LOG_INSERT,jsonObject.toJSONString());
							if("".equals(StringUtil.toTrim(result))||!"200".equals(JSONObject.parseObject(result).getString("code"))){
								LogHelper.error(LogCommonData.LOG_CODE_WEB,"信源采集信息入库",JSON.toJSONString(bean),result,new Exception("PDF信源采集信息入库异常"));
							}
						}
					}
				}
			}else{
				return null;
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"数据入库失败",JSON.toJSONString(bean),"数据入库出现异常！",e);
			bean = null;
		}

		return bean;
	}

	private void insertZen(ArticleBean bean){
		ArticleInfoTempBean tempBean = new ArticleInfoTempBean();
		try{
			tempBean.setArticleId(0);
			tempBean.setHeadline(bean.getTitle());
			tempBean.setAbbv(bean.getMedia().getMediaNameEn());
			tempBean.setAuthor(bean.getAuthor());
			tempBean.setAuthorEN(bean.getMedia().getMediaNameCn());
			tempBean.setPublishTime(bean.getD().getDispTime());
			tempBean.setContent(bean.getContentText());
			tempBean.setPicUrl(bean.getUrl());
			tempBean.setPublishedSource(bean.getPageSrc());
			tempBean.setCopyrightSource(bean.getUrl());
			tempBean.setLayout(bean.getLayout());
			tempBean.setEdition(bean.getPage());
			tempBean.setCrawlTime(bean.getD().getCrawlTime());
			tempBean.setMediaId(0);
			tempBean.setCreateTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
			tempBean.setCheckUser(0);
			tempBean.setCheckTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
			tempBean.setScanner("0");
			tempBean.setAcquisitionTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
			tempBean.setDeleteFlag(1);

			tempBean.setServerPath("");
			tempBean.setArticlePicName("");
			tempBean.setWords(bean.getWords());
			tempBean.setImageName("");
			tempBean.setRelativeSize(bean.getProportion());
			tempBean.setPhotoNum(bean.getPicturesCount());
			tempBean.setNwidth("-1");
			tempBean.setNheight("-1");
			tempBean.setWidth(-1);
			tempBean.setHeight(-1);
			tempBean.setArticleThumbName(bean.getPageImage());
			tempBean.setArticleLocation("");
			tempBean.setServerId(6);
			tempBean.setPicSn(bean.getPicturesCount());
			tempBean.setMediaSubType(bean.getMedia().getMediaType());
			tempBean.setPaperId(-1);
			tempBean.setCoverImageName("");

			Map<String,Object> map = new HashMap<>();
			map.put("jsonBean",JSON.toJSONString(tempBean));

			String result = HttpUtil.post("http://192.168.10.32:8085/peopleManage/peopleManage_insertPDF",map);
			if(!"1".equals(result)){
				LogHelper.info("处理完成数据入审核平台出现异常！"+result);
			}
		}catch (Exception e){
			e.printStackTrace();;
		}
	}

	public static void main(String[] args) {
		ArticleInfoTempBean tempBean = new ArticleInfoTempBean();
		tempBean.setArticleId(0);
		tempBean.setHeadline("测试标题");
		tempBean.setAbbv("ABBV");
		tempBean.setAuthor("作者");
		tempBean.setAuthorEN("作者英文");
		tempBean.setPublishTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		tempBean.setContent("正文");
		tempBean.setPicUrl("http://192.168.0.215:8082/peopleManage/peopleManage_insertPDF");
		tempBean.setPublishedSource("来源");
		tempBean.setCopyrightSource(null);
		tempBean.setLayout("layout");
		tempBean.setEdition("12");
		tempBean.setCrawlTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		tempBean.setMediaId(0);
		tempBean.setCreateTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		tempBean.setCheckUser(0);
		tempBean.setCheckTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		tempBean.setScanner("0");
		tempBean.setAcquisitionTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		tempBean.setDeleteFlag(1);

		tempBean.setServerPath("");
		tempBean.setArticlePicName("");
		tempBean.setWords(0);
		tempBean.setImageName("");
		tempBean.setRelativeSize(1.2d);
		tempBean.setPhotoNum(2);
		tempBean.setNwidth("-1");
		tempBean.setNheight("-1");
		tempBean.setWidth(-1);
		tempBean.setHeight(-1);
		tempBean.setArticleThumbName("");
		tempBean.setArticleLocation("");
		tempBean.setServerId(6);
		tempBean.setPicSn(2);
		tempBean.setMediaSubType("paper");
		tempBean.setPaperId(-1);
		tempBean.setCoverImageName("");

		Map<String,Object> map = new HashMap<>();
		map.put("jsonBean",JSON.toJSONString(tempBean));

		String result = HttpUtil.post("http://192.168.10.32:8085/peopleManage/peopleManage_insertPDF",map);
		System.out.println(result);
	}


	private String getSourceType(String type){
		if("bbs".equals(StringUtil.toTrim(type).toLowerCase())){
			return "BBS";
		}else if("weibo".equals(StringUtil.toTrim(type).toLowerCase())){
			return "WeiBo";
		}else if("tf".equals(StringUtil.toTrim(type).toLowerCase())){
			return "Tf";
		}else if("broadcast".equals(StringUtil.toTrim(type).toLowerCase())){
			return "Broadcast";
		}else{
			return "Article";
		}
	}

	/**
	 * 发送预警
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 18:11 2018/1/2
	 * @param bean
	 */
	private String sendWarning(ArticleBean bean)throws Exception{

		String result = "";

		String warningArray[];
		JSONObject jsonObj = new JSONObject();
		JSONObject param = new JSONObject();

		try{
			if(bean.getWarnings()!=null&&bean.getWarnings().size()>0){
				for(String warnings:bean.getWarnings()){
					warningArray = warnings.split("_");
					jsonObj.put("id",bean.getAid());// 文章AID
					jsonObj.put("wid",warningArray[1]);// 预警ID
					jsonObj.put("type",bean.getType());// 文章类型
					jsonObj.put("title",bean.getTitle());// 文章标题
					jsonObj.put("link",bean.getUrl());// 文章连接
					jsonObj.put("disptime",bean.getD().getDispTime().replace("T"," "));// 发布时间
					jsonObj.put("orgid",warningArray[0]);// 客户ID

					param.put("operationCode","alertwarning");
					param.put("datas",jsonObj.toJSONString());
					param.put("tokenKey","");
					param.put("timeOutFlag","false");
					param.put("time",0);

					result = HttpClientPoolUtil.execute(CommonData.ES_CACHE_URL,param.toJSONString());

					if(!"success".equals(JSON.parseObject(result).getString("code"))){
						LogHelper.error(LogCommonData.LOG_CODE_WEB,"调用预警接口",JSON.toJSONString(bean),"调用预警接口失败！result:"+result,new Exception("预警接口调用失败！"));
						return "";
					}
				}
			}
			return "1";
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"发送预警",JSON.toJSONString(bean),"调用预警接口出现异常！",e);
		}
		return "";
	}
}
