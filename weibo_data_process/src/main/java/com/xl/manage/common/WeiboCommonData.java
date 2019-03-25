package com.xl.manage.common;

import com.alibaba.fastjson.JSONObject;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * weibo常量
 * @Author:lww
 * @Date:9:58 2017/9/8
 */
public class WeiboCommonData {


	//拿数据的消息队列名称
	public static final String WEIBO_TOPIC = "weibotopic";
	//消息队列对应的根路径
	public static final String WEIBO_ZOOKEEPER_ROOT = "";
	//weibo消息队列 TODO 正式
	public static final String WEIBO_STORM = "xl_weibo_storm_formal";

	//weibo消息队列 TODO 测试
//	public static final String WEIBO_STORM = "xl_weibo_storm_formal_test";

//	private static final String DATA_PROCESS_URL = "http://192.168.0.215:8080";
	private static final String DATA_PROCESS_URL = "http://192.168.10.55:8083"; //TODO 正式
//	private static final String DATA_PROCESS_URL = "http://192.168.10.55:8087"; //TODO 测试

	//情感值获取url
	public static final String AFFECTION_URL = DATA_PROCESS_URL + "/sentiment/getAffection";

	//分类器地址
	public static final String CLASSIFER_URL = DATA_PROCESS_URL + "/classifier/getHitByRuleOrgId";

	/** 分词接口 */
	public static final String ICTCLAS_URL = DATA_PROCESS_URL+ "/sentiment/ictclas";

	//微博语义指纹获取url
	public static final String FINGERPRINT_URL = DATA_PROCESS_URL + "/fingerWbApi/getSig";

	//微博用户信息入数据库
	public static final String WEIBO_USER_INSERT_URL = "http://192.168.10.18:8082/xl_api/DataProcessing/Setweibo_user";

	//微博二级采集接口
	public static final String WEIBO_RECRAWL_URL = "http://192.168.10.18:8082/xl_api/DataProcessing/post_spider_articleUrl";

	//数据排重接口
	public static final String DATA_NOREPEAT_URL = "http://192.168.10.70:8081/data/getDataByKey";

	/**
	 * es接口地址
	 * @Author: lww
	 * @Description:
	 * @Date: 19:24 2017/7/28
	 * @params:s/
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
	public static final String WEIBO_INSERT_ALLDATA_CODE = "weiboInsertAllData";

	/* 直连入库接口 */
	public static final String INSERT_DATA_ES  = "http://59.110.48.236:8081/data/putArticleData";

	/** 限定机构ID */
	public static final String ORGID_STR = "";//TODO 正式

	/** 限定机构ID */
//	public static final String ORGID_STR = "498";//TODO 测试

	/**
	 * 根据内容分词并增加特殊拼接符
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 14:40 2017/12/8
	 * @param content
	 */
	public static String getIctclasByContent(String content){

		String result;

		JSONObject paramObj;
		JSONObject resultObj;

		String resultData;
		String []resultDatas;
		StringBuffer sb = new StringBuffer();
		try{
			if(!"".equals(StringUtil.toTrim(content))){
				paramObj = new JSONObject();
				paramObj.put("content",content);
				paramObj.put("isShow",0);
				paramObj.put("type","0");

				result = HttpClientPoolUtil.execute(ICTCLAS_URL,paramObj.toJSONString());
				resultObj = JSONObject.parseObject(result);
				if("1".equals(StringUtil.toTrim(resultObj.getString("code")))){
					resultData = resultObj.getString("datas");
					resultDatas = resultData.split(" ");
					for(String data:resultDatas){
						if(!"".equals(StringUtil.toTrim(data))){
							sb.append("^"+data);
						}
					}
					sb.append("^");
				}
			}else {
				return "";
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取文本分词", content,"获取文本分词出现异常！",e);
			return null;
		}
		return sb.toString();
	}
}
