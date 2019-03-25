package com.xl.manage.common;

import com.alibaba.fastjson.JSONObject;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.PublicClass;
import com.xl.tool.StringUtil;

/**
 * weibo常量
 * @Author:lww
 * @Date:9:58 2017/9/8
 */
public class WebCommonData {

	//拿数据的消息队列名称(正式)
	public static final String WEB_TOPIC = "apptopic";

//	public static final String WEB_TOPIC = "testapptopic";

	//消息队列对应的根路径
	public static final String WEB_ZOOKEEPER_ROOT = "";
	//web消息队列(正式)
	public static final String WEB_STORM = "xl_app_storm_foraml";//TODO 正式
	//web消息队列(测试)
//	public static final String WEB_STORM = "xl_app_storm_test";//TODO 测试

//	public static final String WEB_STORM = "xl_app_storm_test_201800322_1";//TODO 测试

	//日志输出信息开头
	public static final String LOG_OUT_INFO = "app";

//	private static final String DATA_PROCESS_URL = "http://localhost:8081";
	private static final String DATA_PROCESS_URL = "http://192.168.10.55:8083";//TODO 正式
//	private static final String DATA_PROCESS_URL = "http://192.168.10.55:8087";//TODO 测试

	private static final String SIG_SERVER_URL = "http://192.168.10.18:8082";

	//情感值获取url
	public static final String AFFECTION_URL = DATA_PROCESS_URL + "/sentiment/getAffection";

	//分类器地址
	public static final String CLASSIFER_URL = DATA_PROCESS_URL + "/classifier/getHitByRuleOrgId";

	//实体抽取地址
	public static final String EXTRACTION_URL = DATA_PROCESS_URL + "/sentiment/docExtract";

	//关键词提取地址
	public static final String KEYEXTRACT_URL = DATA_PROCESS_URL + "/sentiment/getXlKeyExtract";

	//摘要提取地址
	public static final String SUMMARY_URL = DATA_PROCESS_URL + "/sentiment/getXlSummary";

	//sig语义指纹获取url
	public static final String SIG_URL = SIG_SERVER_URL + "/api/YzdataPress/GetSigByTitleAndContent";

	//indexSigAll语义指纹获取url
	public static final String INDEX_SIG_ALL_URL = DATA_PROCESS_URL + "/fingerWbApi/getIndexSigAll";

	//indexSig语义指纹获取url
	public static final String INDEX_SIG_URL = DATA_PROCESS_URL + "/fingerWbApi/getIndexSig";

	/** 分词接口 */
	public static final String ICTCLAS_URL = DATA_PROCESS_URL+ "/sentiment/ictclas";

	/** 语言接口 */
	public static final String LANGUAGE_URL = DATA_PROCESS_URL+ "/lanage/langDetection";

	/**
	 * es接口地址
	 * @Author: lww
	 * @Description:
	 * @Date: 19:24 2017/7/28
	 * @params:
	 */
	private static final String ES_PORT_URL = "http://192.168.10.72:8888";

	/**
	 * 任务入缓存列表
	 * @Author: lww
	 * @Description:
	 * @Date: 15:47 2017/7/31
	 * @params:
	 */
	public static final String ES_CACHE_URL  = ES_PORT_URL + "/dataCenterSolution/taskData/postCacheData";

	//全部数据入任务code
	public static final String WEB_INSERT_ALLDATA_CODE = "appInsertAllData";

	//获取媒体库接口
	//TODO 正式
	public static String GET_MEDIA_API = "http://192.168.10.198:8095/api/P_GetByDomain?mediatype=MEDIATYPE_VALUE&medianame=MEDIANAME_VALUE&site=SITE_VALUE&domain=DOMAIN_VALUE";
	//TODO 测试
//	public static String GET_MEDIA_API = "http://192.168.10.198:89/api/P_GetByDomain?mediatype=MEDIATYPE_VALUE&medianame=MEDIANAME_VALUE&site=SITE_VALUE&domain=DOMAIN_VALUE";

	//获取语指纹
	public static String GET_SIG_SIGALL_API = "http://192.168.10.18:8082/api/YzdataPress/GetSigByTitleAndContent";

	//根据文章url获取其对应的站点和域名
	public static String GET_SITE_DOMAIN_API = "http://192.168.10.18:8082/api/GetMediaSiteDomain?url=URL_VALUE";

	/* 直连入库接口 */
	public static final String INSERT_DATA_ES  = "http://59.110.48.236:8081/data/putArticleData";

	//数据排重接口
	public static final String DATA_NOREPEAT_URL = "http://192.168.10.70:8081/data/getDataByKey";

	/** 限定机构ID(正式) */
	public static final String ORGID_STR = "";//TODO 正式
	/** 限定机构ID(测试) */
//	public static final String ORGID_STR = "498";//TODO 测试

	/** 替换词时间戳获取接口 */
	public static final String REPLACE_DICTIONARY_STEMP_URL = "http://192.168.10.198:8099/xl_api/Replacekeyword/ReplacekeywordDateFlag";

	/** 屏蔽词时间戳获取接口 */
	public static final String SHIELD_DICTIONARY_STEMP_URL = "http://192.168.10.198:8099/xl_api/Filterword/FilterwordDateFlag";

	/** 替换词集合获取接口 */
	public static final String REPLACE_DICTIONARY_URL = "http://192.168.10.198:8099/xl_api/Replacekeyword/ReplacekeywordList";

	/** 屏蔽词集合获取接口 */
	public static final String SHIELD_DICTIONARY_URL = "http://192.168.10.198:8099/xl_api/Filterword/FilterwordList?mediaType=MEDIA_VALUE";

	/** 错误数据入DB */
	public static final String ERROR_DATA_INSERT_DB_URL = "http://192.168.10.18:8082/xl_api/DataProcessing/SetArticleInfoDataPass";

	//拷屏图入库接口
	public static final String DOCPICIMAGE_API = "http://192.168.10.18:8082/xl_api/DataProcessing/Setdoc_psimage";

	/**
	 * 根据内容分词并增加特殊拼接符
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 14:40 2017/12/8
	 * @param content
	 */
	public static String getIctclasByContent(String content)throws Exception{

		String result;

		JSONObject paramObj;
		JSONObject resultObj;

		String resultData;
		String []resultDatas;
		StringBuffer sb = new StringBuffer();
		try{
			if(!"".equals(StringUtil.toTrim(content))){
				paramObj = new JSONObject();
				paramObj.put("content", PublicClass.StripHTML(content));
				paramObj.put("isShow",0);
				paramObj.put("type","0");

				result = HttpClientPoolUtil.execute(ICTCLAS_URL,paramObj.toJSONString());
				resultObj = JSONObject.parseObject(result);
				if("1".equals(StringUtil.toTrim(resultObj.getString("code")))){
					resultData = StringUtil.toTrim(resultObj.getString("datas"));
					resultData = resultData.replaceAll(" {1,}"," !!!! ");
					resultDatas = resultData.split(" ");
					for(String data:resultDatas){
						if(!"".equals(StringUtil.toTrim(data))){
							sb.append("^"+data+"^ ");
						}
					}
				}
			}else {
				return "";
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取文本分词", content,"获取文本分词出现异常！",e);
			return null;
		}
		return StringUtil.toTrim(sb.toString()).replace(" ^!!!!^ "," ");
	}

	public static void main(String[] args)throws Exception {
		System.out.println(WebCommonData.getIctclasByContent("途胜汽车,别克汽车,特斯拉自动驾驶"));
	}
}
