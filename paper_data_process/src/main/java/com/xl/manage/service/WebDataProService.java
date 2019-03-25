package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.*;
import com.xl.bean.article.*;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WebCommonData;
import com.xl.tool.*;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * web数据格式化处理
 * @Author:lww
 * @Date:18:34 2017/9/21
 */
public class WebDataProService {

	/**
	 * 替换词时间戳
	 * @return
	 * @Author: lww
	 * @Description:
	 * @Date: 16:51 2017/12/7
	 * @param null
	 */
	public static long replace_dictionary_stemp = 0l;

	/**
	 * 屏蔽词时间戳
	 * @return
	 * @Author: lww
	 * @Description:
	 * @Date: 16:52 2017/12/7
	 * @param null
	 */
	public static long shield_dictionary_stemp = 0l;

	/**
	 * web数据格式化处理
	 * @return com.xl.tf.bean.TfBean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:25 2017/9/25
	 * @param oldBean
	 */
	public ArticleBean dealData(WebOldBean oldBean){

		ArticleBean bean;
		JSONObject jsonObject;
		String result;
		try{

			//TODO 测试库不再排重
			if(isRepeat("",StringUtil.toTrim(oldBean.getK_article().getUrl()),"press")){//重复数据
				bean = new ArticleBean();
				bean.setAid("-1");
				return bean;
			}

			//初始化替换词与屏蔽词
			initReplaceDictionary();
			initShieldDictionary();

			//过滤文章,若文章不符合要求,该条不再入es
			if(!this.isQualifiedArticle(oldBean)){
				bean = new ArticleBean();
				bean.setAid("-1");
				return bean;
			}

			bean = changeBean(oldBean);

			if(bean!=null&&!"-1".equals(bean.getAid())){
				//TODO 上传考评图(测试不需要上传)
				if(oldBean.getK_docpsimage()!=null&&oldBean.getK_docpsimage().size()>0){
					for(Docpsimagebean docpsimagebean:oldBean.getK_docpsimage()){
						docpsimagebean.setAid(Long.parseLong(bean.getAid()));
						docpsimagebean.setMediaType(bean.getType());
						result = HttpClientPoolUtil.execute(WebCommonData.DOCPICIMAGE_API,JSON.toJSONString(docpsimagebean));
						jsonObject = JSON.parseObject(result);
						if(!"0".equals(StringUtil.toTrim(jsonObject.getString("code")))){
							LogHelper.error(LogCommonData.LOG_CODE_WEB,"拷屏图上传", JSON.toJSONString(docpsimagebean),"拷屏图上传出现异常！！",new Exception("拷屏图上传出现异常！！"));
							return null;
						}
					}
				}
			}

		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"数据格式化处理", JSON.toJSONString(oldBean),"数据格式化处理出现异常！",e);
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

			result = HttpClientPoolUtil.execute(WebCommonData.DATA_NOREPEAT_URL,param.toJSONString());

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

	/**
	 * 初始化替换词集合
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 16:47 2017/12/7
	 * @param
	 */
	private void initReplaceDictionary(){
		String result;
		long new_stemp;
		JSONObject jsonObject;
		JSONArray jsonArray;
		JSONObject wordObj;
		RKeywordModelBean bean;
		List<RKeywordModelBean> rList;
		Map<String,List<RKeywordModelBean>> map = new HashMap<>();

		String fields[];
		try{
			new_stemp = this.getStemp(WebCommonData.REPLACE_DICTIONARY_STEMP_URL);//调用时间戳API获取替换词
			if(replace_dictionary_stemp!=new_stemp){
				//当时间戳变更，重新获取替换词
				result = HttpClientPoolUtil.execute(WebCommonData.REPLACE_DICTIONARY_URL);
				//解析结果并放入PublicClass类的公共变量中
				jsonObject = JSON.parseObject(result);
				if("success".equals(StringUtil.toTrim(jsonObject.getString("msg")))){
					jsonArray = jsonObject.getJSONArray("datas");
					if(!jsonArray.isEmpty()){
						for(int i=0;i<jsonArray.size();i++){
							wordObj = jsonArray.getJSONObject(i);
							fields = wordObj.getString("c_replacetype").split(",");
							bean = new RKeywordModelBean();
							bean.setOldKeyword(wordObj.getString("c_word"));
							bean.setNewKeyword(wordObj.getString("c_newword"));
							for(String field:fields){
								if(!"".equals(StringUtil.toTrim(field))){
									if(map.containsKey(field)){
										rList = map.get(field);
										rList.add(bean);
										map.put(field,rList);
									}else{
										rList = new ArrayList<>();
										rList.add(bean);
										map.put(field,rList);
									}
								}
							}
						}
					}
					PublicClass.dictionary = map;
				}
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"初始化替换词集合", "","初始化替换词词典出现异常！",e);
		}
	}

	/**
	 * 是否合格数据(数据过滤)
	 * @return boolean
	 * @Author: lww
	 * @Description:
	 * @Date: 11:51 2017/12/11
	 * @param oldBean
	 */
	private boolean isQualifiedArticle(WebOldBean oldBean)throws Exception{
		boolean resultBool = false;
		String reason = "";
		List<FKeywordModelBean> fkeyword;

		if (oldBean.getK_article().getMediatypeint() != 13 && oldBean.getK_article().getMediatypeint() != 10 && oldBean.getK_article().getMediatypeint() != 14 && oldBean.getK_article().getMediatypeint() != 3){//排除 13:OCR 10:贴吧 14:问答 3：论坛
			if ("".equals(StringUtil.toTrim(oldBean.getK_article().getUrl()))){
				reason = "文章地址为空";
			}else if ("".equals(StringUtil.toTrim(oldBean.getK_article().getTitle()))){
				reason = "文章标题为空";
			}

			double num = PublicClass.GetNumPercentage(StringUtil.toTrim(oldBean.getK_article().getHtmlPage()));
			if (num >= PublicClass.NumPercentage)
			{
				reason = "数字太多，超过" + PublicClass.NumPercentage;
			}
			num = PublicClass.GetPerPercentage(StringUtil.toTrim(oldBean.getK_article().getHtmlPage()));
			if (num >= PublicClass.NumPercentage) {
				reason = "百分号太多，超过" + PublicClass.NumPercentage;
			}
		}

		if("".equals(StringUtil.toTrim(reason))&&"".equals(StringUtil.toTrim(oldBean.getK_article().getUrl()))){
			reason = "文章地址为空";
		}

		if("".equals(StringUtil.toTrim(reason))&&"".equals(StringUtil.toTrim(oldBean.getK_article().getTitle()))){
			reason = "文章地址为空";
		}

		if("".equals(StringUtil.toTrim(reason))&&"".equals(StringUtil.toTrim(oldBean.getK_article().getHtmlPage()))){//排除视频排除OCR排除电台weixin
			reason = "文章正文为空";
		}

		if("".equals(StringUtil.toTrim(reason))&&PublicClass.isLuanMa(StringUtil.toTrim(oldBean.getK_article().getTitle()))){
			reason = "为乱码";
		}

		if("".equals(StringUtil.toTrim(reason))){
			//判断媒体类型过滤词是否存在,若存在该篇文章入错误信息库,并不再入es
			if (PublicClass.mediaTypeDictionary.containsKey("web")){
				fkeyword = PublicClass.mediaTypeDictionary.get("web");
				String []result = PublicClass.IsFilterArticle(fkeyword, StringUtil.toTrim(oldBean.getK_article().getTitle()), StringUtil.toTrim(oldBean.getK_article().getHtmlPage()), StringUtil.toTrim(oldBean.getK_article().getUrl()));
				if ("1".equals(result[0])){
					reason = "包含过滤词：" + result[1];
				}else{
					resultBool = true;
				}
			}else{
				resultBool = true;
			}
		}

		//TODO 错误数据暂时补入库(测试不需要)
		if(!resultBool){
			insertErrorDb(oldBean,reason);
		}
		return resultBool;
	}

	/**
	 * 错误数据入DB
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 13:50 2017/12/25
	 * @param oldBean
	 * @param reason
	 */
	private void insertErrorDb(WebOldBean oldBean,String reason){

		ArticleErrorBean errorBean;
		Long aid;
		try{
			aid = HeritrixHelper.getAidByUrl(oldBean.getK_article().getUrl());
			errorBean = new ArticleErrorBean();

			errorBean.setReason(reason);
			//错误数据只赋予基本项
			errorBean.setTitle(PublicClass.ReplaceSpecialTitle(PublicClass.replaceO(StringUtil.toTrim(oldBean.getK_article().getTitle()),"title")));
			errorBean.setHtmlPage(PublicClass.ReplaceContentText(StringUtil.toTrim(oldBean.getK_article().getHtmlPage())));
			errorBean.setUrl(StringUtil.toTrim(oldBean.getK_article().getUrl()));
			errorBean.setAid(aid);
			// 错误信息调用API入库，且该条不再入es
			JSONObject object = new JSONObject();
			object.put("Mediatypeint",oldBean.getK_article().getMediatypeint());
			object.put("Title",PublicClass.ReplaceSpecialTitle(PublicClass.replaceO(StringUtil.toTrim(oldBean.getK_article().getTitle()),"title")));
			object.put("HtmlPage",PublicClass.ReplaceContentText(StringUtil.toTrim(oldBean.getK_article().getHtmlPage())));
			object.put("URL",StringUtil.toTrim(oldBean.getK_article().getUrl()));
			object.put("Reason",reason);
			object.put("AID",aid);

			HttpClientPoolUtil.execute(WebCommonData.ERROR_DATA_INSERT_DB_URL,object.toString());
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"错误数据入DB", JSON.toJSONString(oldBean),"错误数据入DB出现异常！",e);
		}
	}

	/**
	 * 初始化屏蔽词集合
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 17:01 2017/12/7
	 * @param
	 */
	private void initShieldDictionary(){

		String result;
		long new_stemp;
		JSONObject jsonObject;
		JSONArray jsonArray;
		JSONObject wordObj;
		FKeywordModelBean bean;
		List<FKeywordModelBean> rList;
		Map<String,List<FKeywordModelBean>> map = new HashMap<>();

		String fields[];
		String mediaType;
		try{
			new_stemp = this.getStemp(WebCommonData.SHIELD_DICTIONARY_STEMP_URL);//调用时间戳API获取屏蔽词
			if(shield_dictionary_stemp!=new_stemp){
				//当时间戳变更，重新获取替换词
				result = HttpClientPoolUtil.execute(WebCommonData.SHIELD_DICTIONARY_URL.replace("MEDIA_VALUE","press"));
				//解析结果并放入PublicClass类的公共变量中
				jsonObject = JSON.parseObject(result);
				if("success".equals(StringUtil.toTrim(jsonObject.getString("msg")))){
					jsonArray = jsonObject.getJSONArray("datas");
					if(!jsonArray.isEmpty()){
						for(int i=0;i<jsonArray.size();i++){
							wordObj = jsonArray.getJSONObject(i);
							bean = new FKeywordModelBean();
							bean.setKeyWord(wordObj.getString("c_word"));
							bean.setkType(this.getType(wordObj.getString("c_filtertype")));
							mediaType = wordObj.getString("c_resource");
							if(!"".equals(StringUtil.toTrim(mediaType))){
								if(map.containsKey(mediaType)){
									rList = map.get(mediaType);
									rList.add(bean);
									map.put(mediaType,rList);
								}else{
									rList = new ArrayList<>();
									rList.add(bean);
									map.put(mediaType,rList);
								}
							}
						}
					}
					PublicClass.mediaTypeDictionary = map;
				}
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"初始化屏蔽词典", "","初始化屏蔽词典出现异常！",e);
		}
	}

	private int getType(String field){
		if("title".equals(StringUtil.toTrim(field))){
			return 1;
		}else if("contenttext".equals(StringUtil.toTrim(field))){
			return 2;
		}else if(field.indexOf("title")>-1&&field.indexOf("contenttext")>-1){
			return 3;
		}else{
			return 4;
		}
	}

	/**
	 * 根据url获取时间戳
	 * @return long
	 * @Author: lww
	 * @Description:
	 * @Date: 18:48 2017/12/7
	 * @param url
	 */
	private long getStemp(String url){

		String result_stemp;
		long result = 0l;

		JSONObject jsonObject;
		try{
			result_stemp = HttpClientPoolUtil.execute(url);
			jsonObject = JSON.parseObject(result_stemp);
			if("success".equals(jsonObject.getString("msg"))){
				result = Long.parseLong(jsonObject.getString("datas"));
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"根据url获取时间戳", url,"获取时间戳出现异常！",e);
		}
		return result;
	}
	/**
	 * 转换为esBean
	 * @return com.xl.manage.bean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 9:51 2017/11/3
	 * @param oldBean
	 */
	public ArticleBean changeBean(WebOldBean oldBean)throws Exception{

		ArticleBean bean = new ArticleBean();

		AreaBean areaBean = new AreaBean();//调媒体库接口

		MediaBean mediaBean = new MediaBean();
		mediaBean.setMediaNameCn(this.replacesource(oldBean.getK_article().getSourceName()).trim().split("_")[0].trim());
		mediaBean.setMediaNameEn("");
		mediaBean.setDomain(StringUtil.toTrim(oldBean.getK_article().getDomain()));
		mediaBean.setSite(StringUtil.toTrim(oldBean.getK_article().getSite()));
		mediaBean.setMediaType(StringUtil.toTrim(oldBean.getK_article().getMediaSubType()));
		mediaBean.setMediaId("");
		mediaBean.setMediaArea(areaBean);
		mediaBean.setMediaCategory(new ArrayList<>());

		DBean dBean = new DBean();
		dBean.setCrawlTime(DateHelper.formatDateString(oldBean.getK_article().getCrawlTime(),DateHelper.FMT_DATE_DATETIME5,DateHelper.FMT_DATE_DATETIME));
		dBean.setDispTime(DateHelper.formatDateString(oldBean.getK_article().getDispTime(),DateHelper.FMT_DATE_DATETIME5,DateHelper.FMT_DATE_DATETIME));
		dBean.setPubDay(StringUtil.toTrim(oldBean.getK_article().getDispTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getK_article().getDispTime()),DateHelper.FMT_DATE_DATETIME5,DateHelper.FMT_DATE_DD)):0);
		dBean.setPubHour(StringUtil.toTrim(oldBean.getK_article().getDispTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getK_article().getDispTime()),DateHelper.FMT_DATE_DATETIME5,DateHelper.FMT_DATE_HH)):0);
		dBean.setPubMonth(StringUtil.toTrim(oldBean.getK_article().getDispTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getK_article().getDispTime()),DateHelper.FMT_DATE_DATETIME5,DateHelper.FMT_DATE_MM)):0);
		dBean.setCreateDate(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		dBean.setIdate(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME).replace(" ","T"));
		dBean.setAquerTime("0001-01-01T00:00:00");

		//TODO 若发布时间为1900-01-01T00:00:00 默认为采集时间
		if("1900-01-01 00:00:00".equals(dBean.getDispTime())){
			dBean.setDispTime(dBean.getCrawlTime());
			dBean.setPubDay(StringUtil.toTrim(oldBean.getK_article().getCrawlTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getK_article().getCrawlTime()),DateHelper.FMT_DATE_DATETIME5,DateHelper.FMT_DATE_DD)):0);
			dBean.setPubHour(StringUtil.toTrim(oldBean.getK_article().getCrawlTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getK_article().getCrawlTime()),DateHelper.FMT_DATE_DATETIME5,DateHelper.FMT_DATE_HH)):0);
			dBean.setPubMonth(StringUtil.toTrim(oldBean.getK_article().getCrawlTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getK_article().getCrawlTime()),DateHelper.FMT_DATE_DATETIME5,DateHelper.FMT_DATE_MM)):0);
		}

		AdvBean advBean = new AdvBean();

		SigsBean sigsBean = new SigsBean();
		sigsBean.setSig(oldBean.getK_article().getSig());
		sigsBean.setSigall(oldBean.getK_article().getSigAll());

		bean.setPage(StringUtil.toTrim(oldBean.getK_article().getPage()));
		bean.setPageSrc(StringUtil.toTrim(oldBean.getK_article().getPageSrc()));
		bean.setCrawlType(oldBean.getK_article().getCrawlType()+"");
		bean.setUrl(StringUtil.toTrim(oldBean.getK_article().getUrl()));
		bean.setWeMName(StringUtil.toTrim(oldBean.getK_article().getS3()));
		bean.setArticleLocation(StringUtil.toTrim(oldBean.getK_article().getArticleLocation()));//文章位置
		bean.setFatherUrl(StringUtil.toTrim(oldBean.getK_article().getReferer()));//父级url
		bean.setPageImage(StringUtil.toTrim(oldBean.getK_article().getThumbnailPic()));
		bean.setFilePath(StringUtil.toTrim(oldBean.getK_article().getPdf()));
		bean.setPicturesCount(NumberUtil.stringToInt(oldBean.getK_article().getPics()));
		bean.setPn(oldBean.getK_article().getPn());
		bean.setPc(oldBean.getK_article().getPc());

		if(oldBean.getK_article().getAid()==0||oldBean.getK_article().getAid()==10101010){
			bean.setAid(HeritrixHelper.getAidByUrl(bean.getUrl().toLowerCase())+"");
		}else{
			bean.setAid(oldBean.getK_article().getAid()+"");
		}

		if("0".equals(bean.getCrawlType())){//垂直采集的导航使用采集端配置的信息源名称
			bean.setNavigator(oldBean.getK_article().getSourceName());
		}else{
			bean.setNavigator(ArticleDataHelper.GetNavigator(StringUtil.toTrim(oldBean.getK_article().getNavigator())));
		}

		String site = "";
		String layout = "";
		String domain = "";
		String page = "";
		int hitnb = 0;
		int repliesnb = 0;
		int likenum = 0;
		String resultSite;//调用API返回的站点信息
		List<String> sigList;

		switch (oldBean.getK_article().getMediatypeint()){
			case 1:
				//this.MediaSubType = "news";//默认二级媒体类型
				if (StringUtil.toTrim(oldBean.getK_article().getLayout()).length() > 0){
					layout = StringUtil.toTrim(oldBean.getK_article().getLayout());

				}else{
					layout = StringUtil.toTrim(replacesource(StringUtil.toTrim(oldBean.getK_article().getSourceName()))).split("_").length > 1 ? StringUtil.toTrim(replacesource(StringUtil.toTrim(oldBean.getK_article().getSourceName()))).split("_")[1] : "";
				}

				site = oldBean.getK_article().getSite();
				domain = oldBean.getK_article().getDomain();
				page = "";
				hitnb = 0;
				repliesnb = 0;
				mediaBean.setMediaType("news");
				bean.setType("web");
				break;
			case 2:
				layout = StringUtil.toTrim(oldBean.getK_article().getLayout());
				page = StringUtil.toTrim(oldBean.getK_article().getPage());
				hitnb = 0;
				repliesnb = 0;

				mediaBean.setMediaType("epaper");
				bean.setType("press");
				break;
			case 3:
				if (StringUtil.toTrim(oldBean.getK_article().getLayout()).length() > 0){
					layout = StringUtil.toTrim(oldBean.getK_article().getLayout());
				} else {
					int _l = StringUtil.toTrim(replacesource(StringUtil.toTrim(oldBean.getK_article().getSourceName()))).split("_").length;

					if (_l > 1){
						layout = StringUtil.toTrim(replacesource(StringUtil.toTrim(oldBean.getK_article().getSourceName())).split("_")[_l - 1]);

					} else {
						layout = "";
					}
				}
				site = oldBean.getK_article().getSite();
				domain = oldBean.getK_article().getDomain();
				page = "";
				//阅读数（点击数）
				hitnb = StringUtil.toTrim(oldBean.getK_article().getM1()+"").length() > 0 ? (int)Double.parseDouble(StringUtil.toTrim(oldBean.getK_article().getM1()+"")) : 0;
				//回复数(评论数)
				repliesnb = StringUtil.toTrim(oldBean.getK_article().getM2()+"").length() > 0 ? (int)Double.parseDouble(StringUtil.toTrim(oldBean.getK_article().getM2()+"")) : 0;

				mediaBean.setMediaType("bbs");
				bean.setType("bbs");
				break;
			case 4:
				// this.MediaSubType = "blog";//默认二级媒体类型

				site = oldBean.getK_article().getSite();
				domain = oldBean.getK_article().getDomain();
				layout = "";
				page = "";

				//阅读数（点击数）
				hitnb = StringUtil.toTrim(oldBean.getK_article().getM1()+"").length() > 0 ? (int)Double.parseDouble(StringUtil.toTrim(oldBean.getK_article().getM1()+"")) : 0;
				//回复数(评论数)
				repliesnb = StringUtil.toTrim(oldBean.getK_article().getM2()+"").length() > 0 ? (int)Double.parseDouble(StringUtil.toTrim(oldBean.getK_article().getM2()+"")) : 0;

				mediaBean.setMediaType("blog");
				bean.setType("web");
				break;
			case 9://视频
				// this.MediaSubType = "video";//默认二级媒体类型
				if (!"".equals(StringUtil.toTrim(oldBean.getK_article().getLayout()))){
					layout = StringUtil.toTrim(oldBean.getK_article().getLayout());
				}else{
					layout = StringUtil.toTrim(replacesource(oldBean.getK_article().getSourceName())).split("_").length > 1 ? StringUtil.toTrim(replacesource(oldBean.getK_article().getSourceName())).split("_")[1] : "";
				}
				page = "";
				hitnb = 0;
				repliesnb = 0;
				mediaBean.setMediaType("video");
				bean.setType("web");
				break;
			case 10:
				//this.MediaSubType = "贴吧";//默认二级媒体类型

				site = oldBean.getK_article().getSite();
				domain = oldBean.getK_article().getDomain();
				layout = "";
				page = "";
				hitnb = 0;
				repliesnb = 0;
				mediaBean.setMediaType("tieba");
				bean.setType("web");
				break;
			case 11:
				//this.MediaSubType = "sns";//默认二级媒体类型
				site = oldBean.getK_article().getSite();
				domain = oldBean.getK_article().getDomain();
				layout = "";
				page = "";
				hitnb = 0;
				repliesnb = 0;
				mediaBean.setMediaType("sns");
				bean.setType("web");
				break;
			case 12://元搜索
				// this.MediaSubType = "sousuo";//默认二级媒体类型

				site = oldBean.getK_article().getSite();
				domain = oldBean.getK_article().getDomain();

				if (StringUtil.toTrim(oldBean.getK_article().getLayout()).length() > 0){
					layout = StringUtil.toTrim(oldBean.getK_article().getLayout());
				}else{
					layout = StringUtil.toTrim(replacesource(StringUtil.toTrim(oldBean.getK_article().getSourceName()))).split("_").length > 1 ? StringUtil.toTrim(replacesource(StringUtil.toTrim(oldBean.getK_article().getSourceName()))).split("_")[1] : "";
				}
				page = "";
				hitnb = 0;
				repliesnb = 0;
				mediaBean.setMediaNameCn(StringUtil.toTrim(oldBean.getK_article().getS1()));
				mediaBean.setMediaType("sousuo");
				bean.setType("web");
				break;
			case 14:
				// this.MediaSubType = "wenda";//默认二级媒体类型

				site = oldBean.getK_article().getSite();
				domain = oldBean.getK_article().getDomain();
				layout = "";
				page = "";
				hitnb = 0;
				repliesnb = 0;
				mediaBean.setMediaType("wenda");
				bean.setType("web");
				break;
			case 15://APP
				if (StringUtil.toTrim(oldBean.getK_article().getLayout()).length() > 0){
					layout = StringUtil.toTrim(oldBean.getK_article().getLayout());

				}else{
					layout = StringUtil.toTrim(replacesource(StringUtil.toTrim(oldBean.getK_article().getSourceName()))).split("_").length > 1 ? StringUtil.toTrim(replacesource(StringUtil.toTrim(oldBean.getK_article().getSourceName()))).split("_")[1] : "";
				}
				page = "";

				//阅读数（点击数）
				hitnb = StringUtil.toTrim(oldBean.getK_article().getM1()+"").length() > 0 ? (int)Double.parseDouble(StringUtil.toTrim(oldBean.getK_article().getM1()+"")) : 0;
				//回复数(评论数)
				repliesnb = StringUtil.toTrim(oldBean.getK_article().getM2()+"").length() > 0 ? (int)Double.parseDouble(StringUtil.toTrim(oldBean.getK_article().getM2()+"")) : 0;
				//点赞数
				likenum = StringUtil.toTrim(oldBean.getK_article().getM3()+"").length() > 0 ? (int)Double.parseDouble(StringUtil.toTrim(oldBean.getK_article().getM3()+"")) : 0;
				bean.setType("app");
				break;
		}

		//排除不需要sig的媒体类型数据
		if(oldBean.getK_article().getMediatypeint()!=11){
			if (!"0".equals(bean.getCrawlType())&&!"1".equals(bean.getCrawlType())&&
					"".equals(sigsBean.getSig())&&"".equals(sigsBean.getSigall())){//语义指纹为空的调用联索提取语义指纹
				//region 调用联索语义分析
				String h_c = PublicClass.StripHTML(StringUtil.toTrim(oldBean.getK_article().getHtmlPage()));
				//调用语指纹API 获取sig和sigAll
				sigList = getSigSigAll(PublicClass.StripHTML(oldBean.getK_article().getTitle()),h_c);
				if(sigList==null){
					return null;
				}else{
					sigsBean.setSig(sigList.get(0));
					sigsBean.setSigall(sigList.get(1));
				}
			}
		}

		if(!"".equals(StringUtil.toTrim(site))){
			mediaBean.setSite(site);
		}

		if(!"".equals(StringUtil.toTrim(domain))){
			mediaBean.setDomain(domain);
		}

		if(!"".equals(StringUtil.toTrim(layout))){
			bean.setLayout(layout);
		}

		if(!"".equals(page)){
			bean.setPage(page);
		}

		bean.setReadnum(hitnb);
		bean.setCommentnum(repliesnb);
		bean.setLikenum(likenum);

		//调用根据文章url获取媒体site和domain的API
		JSONObject sitObj;
		JSONObject sitData;
		try{
			resultSite = HttpClientPoolUtil.execute(WebCommonData.GET_SITE_DOMAIN_API.replace("URL_VALUE",URLEncoder.encode(oldBean.getK_article().getUrl(),"utf-8")));
			sitObj = JSON.parseObject(resultSite);
			if(sitObj!=null&&"success".equals(sitObj.getString("msg"))){
				sitData = sitObj.getJSONObject("datas");
				if(!"".equals(sitData.getString("domain"))){
					mediaBean.setDomain(StringUtil.toTrim(sitData.getString("domain")));
				}
				if(!"".equals(sitData.getString("site"))){
					mediaBean.setSite(StringUtil.toTrim(sitData.getString("site")));
				}
			}else{
				LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取媒体site和domain", oldBean.getK_article().getUrl(),"调用根据文章url获取媒体site和domain的API出现异常！result["+resultSite+"]",new Exception("获取site和domain出现异常！"));
				return null;
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"根据url获取时间戳", oldBean.getK_article().getUrl(),"获取文章的域名和站点出现异常！",e);
			return null;
		}

		if (!"0".equals(bean.getCrawlType()) && !"1".equals(bean.getCrawlType()) && !"2".equals(bean.getCrawlType())){
			//去除HTML标签的属性
			bean.setContentText(PublicClass.replacecontenttexttable(PublicClass.replacecontenttext(PublicClass.replaceO(PublicClass.replaceAllStyle(StringUtil.toTrim(oldBean.getK_article().getHtmlPage())),"contenttext"),oldBean.getK_docpic()),oldBean.getK_doctable()));
		}else{
			bean.setContentText(PublicClass.replacecontenttexttable(PublicClass.replacecontenttext(PublicClass.replaceO(oldBean.getK_article().getHtmlPage(),"contenttext"),oldBean.getK_docpic()),oldBean.getK_doctable()));
		}

		bean.setAuthor(PublicClass.replaceO(StringUtil.toTrim(oldBean.getK_article().getAuthor()),"author"));//替换特定字符串
		bean.setNavigator(PublicClass.replaceO(bean.getNavigator(),"navigator"));
		bean.setTitle(PublicClass.ReplaceSpecialTitle(PublicClass.replaceO(StringUtil.toTrim(oldBean.getK_article().getTitle()),"title")));
		bean.setWords(this.delHTMLTag(StringUtil.toTrim(bean.getContentText())).length());

		bean.setMedia(mediaBean);
		bean.setSigs(sigsBean);
		bean.setAdv(advBean);
		bean.setD(dBean);

		if ("新闻".equals(bean.getMedia().getMediaType()) || "web".equals(bean.getMedia().getMediaType())) {
			bean.getMedia().setMediaType("news");
		}

		if("".equals(StringUtil.toTrim(bean.getType()))){
			bean.setType("web");
		}

		if(bean.getSigs().getSig()==null){
			bean.getSigs().setSig("");
		}
		if(bean.getSigs().getSigall()==null){
			bean.getSigs().setSigall("");
		}

		bean.set_monitorIds(StringUtil.toTrim(oldBean.getK_article().getMonitorIds()));
		bean.set_lableIds(StringUtil.toTrim(oldBean.getK_article().getLableIds()));

		if(bean.getPicturesCount()==0&&bean.getContentText().indexOf("<img ")!=-1){
			bean.setPicturesCount(PublicClass.getImgSrc(bean.getContentText()).size());
		}

		if("".equals(StringUtil.toTrim(bean.getMedia().getMediaNameCn()))||"null".equals(StringUtil.toTrim(bean.getMedia().getMediaNameCn()))){
			bean.getMedia().setMediaNameCn(bean.getMedia().getDomain());
		}

		if("".equals(StringUtil.toTrim(bean.getMedia().getMediaNameEn()))||"null".equals(StringUtil.toTrim(bean.getMedia().getMediaNameEn()))){
			bean.getMedia().setMediaNameEn(bean.getMedia().getSite());
		}

		if(!"".equals(StringUtil.toTrim(oldBean.getK_article().getEwID()))){//信源任务ID
			bean.set_ewID(StringUtil.toTrim(oldBean.getK_article().getEwID()));
		}

		return bean;
	}


	/**
	 * 替换源
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 10:51 2017/11/18
	 * @param obj
	 */
	private String replacesource(Object obj){

		if (obj == null){
			return "";
		}

		if (obj.toString().trim().contains(">")) {
			return obj.toString().trim().replace(">", "_");
		}else{
			return obj.toString().trim();
		}
	}

	/**
	 * 获取文章sig和sigAll
	 * @return java.util.List<java.lang.String>
	 * @Author: lww
	 * @Description:
	 * @Date: 9:15 2017/11/20
	 * @param title
	 * @param content
	 */
	private List<String> getSigSigAll(String title,String content){

		String result="";
		JSONObject resultObj;
		List<String> list = new ArrayList<>();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("title",title);
		jsonObject.put("content",content);

		try{
			result = HttpClientPoolUtil.execute(WebCommonData.GET_SIG_SIGALL_API,jsonObject.toJSONString());
			resultObj = JSON.parseObject(result);
			list.add(resultObj.getString("sig"));
			list.add(resultObj.getString("sigall"));
		}catch (Exception e){
			list = null;
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取sig和sigAll", jsonObject.toJSONString(),"获取sig和sigAll出现异常！"+result,e);
		}
		return list;
	}

	/**
	 * 去掉文本中html标签
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 14:36 2017/9/19
	 * @param htmlStr
	 */
	private String delHTMLTag(String htmlStr){

		String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
		String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
		String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

		Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
		Matcher m_script=p_script.matcher(htmlStr);
		htmlStr=m_script.replaceAll(""); //过滤script标签

		Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
		Matcher m_style=p_style.matcher(htmlStr);
		htmlStr=m_style.replaceAll(""); //过滤style标签

		Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
		Matcher m_html=p_html.matcher(htmlStr);
		htmlStr=m_html.replaceAll(""); //过滤html标签

		return htmlStr.replace("&nbsp;","").trim(); //返回文本字符串
	}

	/*public static void main(String[] args)throws Exception {

//		String title = "[路演]金风科技:出口及海上风电将为增长点";
//		String content = "<p> 全景网4月6日讯 金风科技（002202）2015年度业绩说明会周三下午在全景网举行。公司董事兼副总裁曹志刚表示，2016与2015年相比风电发展更加趋稳，差异不大。出口及海上风电将成为公司未来业务增长点；风电整体解决方案及大服务市场将是公司持续加强的业务。（全景网） </p><p> http://ircs.p5w.net/ircs/topicInteraction/bbs.do?rid=16014</p>";
//		List<String> list = new ArrayList<>();
//
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("title",PublicClass.StripHTML(title));
//		jsonObject.put("content",PublicClass.StripHTML(content));
//
//		String result = HttpClientPoolUtil.execute(WebCommonData.GET_SIG_SIGALL_API,jsonObject.toJSONString());
//		JSONObject resultObj = JSON.parseObject(result);
//		list.add(resultObj.getString("sig"));
//		list.add(resultObj.getString("sigall"));

		String result = HttpClientPoolUtil.execute("http://192.168.10.18:8082/api/GetMediaSiteDomain?url="+ URLEncoder.encode("http://sports.mop.com/a/171120104246070000000.html","utf-8"));
		System.out.println(result);

	}*/

}
