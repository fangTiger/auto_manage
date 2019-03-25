package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.FKeywordModelBean;
import com.xl.basic.bean.KafkaBean;
import com.xl.basic.bean.RKeywordModelBean;
import com.xl.bean.article.*;
import com.xl.manage.common.CommonData;
import com.xl.manage.common.LogCommonData;
import com.xl.tools.*;

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
public class DataProService {

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
	public ArticleBean dealData(KafkaBean oldBean){

		ArticleBean bean;
		try{

			/*if(isRepeat(HeritrixHelper.getAidByUrl(oldBean.getPdfArticle().getUrl().toLowerCase()+"/paper")+"","","press")){//重复数据
				bean = new ArticleBean();
				bean.setAid("-1");
				return bean;
			}*/

			//初始化替换词与屏蔽词
			initReplaceDictionary();
			initShieldDictionary();

			bean = changeBean(oldBean);

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
			new_stemp = this.getStemp(CommonData.REPLACE_DICTIONARY_STEMP_URL);//调用时间戳API获取替换词
			if(replace_dictionary_stemp!=new_stemp){
				//当时间戳变更，重新获取替换词
				result = HttpClientPoolUtil.execute(CommonData.REPLACE_DICTIONARY_URL);
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
			new_stemp = this.getStemp(CommonData.SHIELD_DICTIONARY_STEMP_URL);//调用时间戳API获取屏蔽词
			if(shield_dictionary_stemp!=new_stemp){
				//当时间戳变更，重新获取替换词
				result = HttpClientPoolUtil.execute(CommonData.SHIELD_DICTIONARY_URL.replace("MEDIA_VALUE","press"));
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
	public ArticleBean changeBean(KafkaBean oldBean)throws Exception{

		ArticleBean bean = new ArticleBean();

		AreaBean areaBean = new AreaBean();//调媒体库接口

		MediaBean mediaBean = new MediaBean();
		mediaBean.setMediaNameCn(StringUtil.toTrim(oldBean.getPdfArticle().getMediaNameCn()));
		mediaBean.setMediaNameEn("");
		mediaBean.setDomain("");
		mediaBean.setSite("");
		mediaBean.setMediaType(StringUtil.toTrim(oldBean.getPdfArticle().getMediaType()));
		mediaBean.setMediaId("");
		mediaBean.setMediaArea(areaBean);
		mediaBean.setMediaCategory(new ArrayList<>());

		DBean dBean = new DBean();
		dBean.setCrawlTime(oldBean.getPdfArticle().getCrawlTime());
		if(!"".equals(StringUtil.toTrim(oldBean.getPdfArticle().getDispTime()))){
			if(StringUtil.toTrim(oldBean.getPdfArticle().getDispTime()).length()==10){
				dBean.setDispTime(oldBean.getPdfArticle().getDispTime()+" 00:00:00");
			}else{
				dBean.setDispTime(DateHelper.getNowDate(DateHelper.FMT_DATE_YYYY_MM_DD)+" 00:00:00");
			}
		}else{
			dBean.setDispTime(DateHelper.getNowDate(DateHelper.FMT_DATE_YYYY_MM_DD)+" 00:00:00");
		}
		dBean.setPubDay(StringUtil.toTrim(oldBean.getPdfArticle().getDispTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getPdfArticle().getDispTime()),DateHelper.FMT_DATE_YYYY_MM_DD,DateHelper.FMT_DATE_DD)):0);
		dBean.setPubHour(StringUtil.toTrim(oldBean.getPdfArticle().getDispTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getPdfArticle().getDispTime()),DateHelper.FMT_DATE_YYYY_MM_DD,DateHelper.FMT_DATE_HH)):0);
		dBean.setPubMonth(StringUtil.toTrim(oldBean.getPdfArticle().getDispTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getPdfArticle().getDispTime()),DateHelper.FMT_DATE_YYYY_MM_DD,DateHelper.FMT_DATE_MM)):0);
		dBean.setCreateDate(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		dBean.setIdate(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME).replace(" ","T"));
		dBean.setAquerTime("0001-01-01T00:00:00");

		AdvBean advBean = new AdvBean();

		SigsBean sigsBean = new SigsBean();

		bean.setPage(StringUtil.toTrim(oldBean.getPdfArticle().getPage()));
		bean.setPageSrc(StringUtil.toTrim(oldBean.getPdfArticle().getPageSrc()));
		bean.setCrawlType(oldBean.getPdfArticle().getCrawlType()+"");
		bean.setUrl(StringUtil.toTrim(oldBean.getPdfArticle().getUrl()));
		bean.setWeMName("");
		bean.setArticleLocation(StringUtil.toTrim(oldBean.getPdfArticle().getArticleLocation()));//文章位置
		bean.setFatherUrl("");//父级url
		bean.setPageImage(StringUtil.toTrim(oldBean.getPdfArticle().getPageImage()));
		bean.setFilePath(StringUtil.toTrim(oldBean.getPdfArticle().getFilePath()));
		bean.setPicturesCount(oldBean.getPdfArticle().getPicturesCount());
		bean.setPn(1);
		bean.setPc(1);

		bean.setAid(HeritrixHelper.getAidByUrl(bean.getUrl().toLowerCase()+"/paper")+"");

		bean.setNavigator("");

		String resultSite;//调用API返回的站点信息
		List<String> sigList;

		bean.setLayout(StringUtil.toTrim(oldBean.getPdfArticle().getLayout()));
		bean.setPage(PublicClass.replaceFirst(StringUtil.toTrim(oldBean.getPdfArticle().getPage()),"0"));//OCR数据page字段要去掉前面0 例如:0017 变成17
		bean.setProportion(0d);//OCR将版面占比存到M1字段
		bean.setAuthor(oldBean.getPdfArticle().getAuthor());//OCR记着多值用,分割 原行【中文/中文;英文/英文】

		bean.set_size(0d);//TODO 需要计算转换后的JPG 面积

		//region 调用联索语义分析
		String h_c = PublicClass.StripHTML(StringUtil.toTrim(oldBean.getPdfArticle().getContentText()));
		//调用语指纹API 获取sig和sigAll
		sigList = getSigSigAll(PublicClass.StripHTML(oldBean.getPdfArticle().getTitle()),h_c);
		if(sigList==null){
			return null;
		}else{
			sigsBean.setSig(sigList.get(0));
			sigsBean.setSigall(sigList.get(1));
		}

		bean.setReadnum(0);
		bean.setCommentnum(0);
		bean.setLikenum(0);

		//调用根据文章url获取媒体site和domain的API
		JSONObject sitObj;
		JSONObject sitData;
		try{
			resultSite = HttpClientPoolUtil.execute(CommonData.GET_SITE_DOMAIN_API.replace("URL_VALUE",URLEncoder.encode(oldBean.getPdfArticle().getUrl(),"utf-8")));
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
				LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取媒体site和domain", oldBean.getPdfArticle().getUrl(),"调用根据文章url获取媒体site和domain的API出现异常！result["+resultSite+"]",new Exception("获取site和domain出现异常！"));
				return null;
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"根据url获取时间戳", oldBean.getPdfArticle().getUrl(),"获取文章的域名和站点出现异常！",e);
			return null;
		}
		bean.setContentText(PublicClass.replaceContentText(PublicClass.replaceO(oldBean.getPdfArticle().getContentText(),"contenttext")));

		//TODO 正文图片特殊处理

		/*if(CommonData.SPIC_MEDIA_PIC_LIST.contains(StringUtil.toTrim(bean.getMedia().getMediaNameCn()))){
			String urlPath = bean.getUrl();
			urlPath = urlPath.substring(0,urlPath.lastIndexOf("/"));
			bean.setContentText(PublicClass.getImgSrcSpic(bean.getContentText(),urlPath));
			bean.setContentText(PublicClass.getImageSrcSpic(bean.getContentText(),urlPath));
		}*/

		bean.setAuthor(PublicClass.replaceO(StringUtil.toTrim(oldBean.getPdfArticle().getAuthor()),"author"));//替换特定字符串
		bean.setNavigator(PublicClass.replaceO(bean.getNavigator(),"navigator"));
		bean.setTitle(PublicClass.ReplaceSpecialTitle(PublicClass.replaceO(StringUtil.toTrim(oldBean.getPdfArticle().getTitle()),"title")));
		bean.set_content(oldBean.getPdfArticle().getContentText());
		bean.setWords(this.delHTMLTag(StringUtil.toTrim(bean.getContentText())).length());

		bean.setMedia(mediaBean);
		bean.setSigs(sigsBean);
		bean.setAdv(advBean);
		bean.setD(dBean);

		bean.setType("press");

		if(bean.getSigs().getSig()==null){
			bean.getSigs().setSig("");
		}
		if(bean.getSigs().getSigall()==null){
			bean.getSigs().setSigall("");
		}

		bean.set_articleThumbName(StringUtil.toTrim(oldBean.getPdfArticle().getArticleThumbName()));//缩略图

		bean.set_lableIds(StringUtil.toTrim(oldBean.getPdfArticle().getLableIds()));
		bean.set_monitorIds(StringUtil.toTrim(oldBean.getPdfArticle().getMonitorIds()));

		if(bean.getPicturesCount()==0&&bean.getContentText().indexOf("<img ")!=-1){
			bean.setPicturesCount(PublicClass.getImgSrc(bean.getContentText()).size());
		}

		if("".equals(StringUtil.toTrim(bean.getMedia().getMediaNameCn()))||"null".equals(StringUtil.toTrim(bean.getMedia().getMediaNameCn()))){
			bean.getMedia().setMediaNameCn(bean.getMedia().getDomain());
		}

		if("".equals(StringUtil.toTrim(bean.getMedia().getMediaNameEn()))||"null".equals(StringUtil.toTrim(bean.getMedia().getMediaNameEn()))){
			bean.getMedia().setMediaNameEn(bean.getMedia().getSite());
		}

		if(!"".equals(StringUtil.toTrim(oldBean.getPdfArticle().getEwID()))){//信源任务ID
			bean.set_ewID(StringUtil.toTrim(oldBean.getPdfArticle().getEwID()));
		}

		bean.set_pdfPath(StringUtil.toTrim(oldBean.getPdfArticle().getImageName()));
		return bean;
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
			result = HttpClientPoolUtil.execute(CommonData.GET_SIG_SIGALL_API,jsonObject.toJSONString());
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

}
