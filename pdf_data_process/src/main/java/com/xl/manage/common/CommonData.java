package com.xl.manage.common;

import com.alibaba.fastjson.JSONObject;
import com.xl.tools.HttpClientPoolUtil;
import com.xl.tools.LogHelper;
import com.xl.tools.StringUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常量
 * @Author:lww
 * @Date:9:58 2017/9/8
 */
public class CommonData {

	public static List<String> mediaNameList = new ArrayList<>();
	static{
		String []mediaNameArray = {"测试媒体","大河报","安吉新闻","东阳日报","永康日报","今日武义","今日新昌","奉贤报","北仑新区时刊","慈溪日报","今日江山","石狮日报","今日桐庐","今日婺城","邳州日报","今日象山","大渡口报","江阴日报","今日柯城","今日临海","今日路桥","靖江日报","太仓日报","太湖周刊","宝安日报","武进日报","东台日报","金湖快报","启东日报","浦东时报","三江都市报","信阳晚报","牛城晚报","邢台日报","宿迁日报","宿迁晚报","宜宾日报","宜春日报","红河日报","周口晚报","常德日报","常德晚报","十堰晚报","安康日报","达州晚报","达州日报","克拉玛依日报","乌蒙新报","大兴安岭日报","广元日报","来宾日报","凉山日报","毕节日报","攀枝花日报","张家界日报","云浮日报","西藏日报","德宏团结报","淇河晨报","鹤壁日报","科技日报","法制日报","人民日报","三亚日报","上饶日报","盐城晚报","扬州晚报","西海都市报","江门日报","华兴时报","荆州日报","闽北日报","盐阜大众报","处州晚报","绵阳晚报","京九晚报","潮州日报","大理日报","德阳日报","黄冈日报","鄂东晚报","黄河晨报","焦作日报","乐山日报","内江日报","曲靖日报","人民日报(海外版)","南国都市报","南宁日报","绍兴日报","绍兴晚报","石家庄日报","珠江商报","西部商报","彭城晚报","烟台晚报","云南日报","长春日报","长春晚报","浙中新报","徐州日报","甘肃日报","海南日报","河北工人报","吉林日报","金华晚报","证券导报","燕赵晚报","黔西南日报","包头日报","洛阳晚报","保定日报","保定晚报","北方晨报","淮安日报","京江晚报","镇江日报","闽南日报","南阳日报","青海日报","农民日报","人民法院报","劳动午报","团结报","中国纪检监察报","解放军报","中国水运报","东方城乡报","京郊日报","上海法治报","中国渔业报","深圳日报","首都建设报","广东建设报","证券时报","辽宁日报","河南商报","大连日报","东方今报","河南法制报","湖南日报","重庆日报","现代金报","深圳都市报","哈尔滨日报","呼和浩特日报","呼和浩特晚报","重庆时报","江海晚报","今晚报","武陵都市报","星沙时报","洛阳日报","南通日报","南阳晚报","宁波日报","青岛日报","青岛财经日报","三峡都市报","韶关日报","泰州日报","泰州晚报","无锡日报","西安晚报","西南商报","潇湘晨报","今晨6点","烟台日报","扬州日报","云南经济日报","青年时报","台州日报","台州晚报","济宁日报","济宁晚报","金华日报","开封日报","兰州晨报","闽东日报","青年报","西宁晚报","中国高新技术产业导报","晶报","成都晚报","新文化报","重庆商报","淮海商报","东莞日报","东风汽车报","滨海时报","东南商报","宁波晚报","西安日报","余杭晨报","武汉晨报","武汉晚报","长江日报","河南日报","河南日报农村版","长沙晚报","四川科技报","东莞时报","当代商报","羊城晚报地方版","东亚经贸新闻","海西晨报","常州日报","常州晚报","春城晚报","福建日报","广西日报","贵阳日报","新晚报","河北日报","城市晚报","嘉兴日报","南湖晚报","都市时报","经济日报","新华每日电讯","光明日报","崇明报","大河健康报","大众卫生报","东昌时讯","洞头新闻","丰台报","福建侨报","华东旅游报","今日安宁","今日消费","武汉科技报","新民晚报(家庭周刊)","新民晚报(业主周刊)","信息早报","中国出版传媒商报","安庆晚报","亳州晚报","成都商报","滁州日报","大江晚报","国际旅游岛商报","华商晨报","华西都市报","淮海晚报","检察日报","江西日报","眉山日报","人民铁道","商丘日报","市场星报","皖东晨刊","芜湖日报","内蒙古日报","南方教育时报","余姚日报","洛阳商报"};
		mediaNameList = Arrays.asList(mediaNameArray);
	}

	/**
	 * 预警发送人
	 * @return
	 * @Author: lww
	 * @Description:
	 * @Date: 19:17 2018/4/9
	 * @param null
	 */
	public static String[] openIds = {"oEdr4tzVjUjceJGbCnB5_1EJ-320","oEdr4t7bI5yqeR9wqzC-uHEqHXcU"};

	/**
	 * 需要处理正文中图片链接的媒体
	 * @return
	 * @Author: lww
	 * @Description:
	 * @Date: 19:16 2018/4/9
	 * @param null
	 */
	public static List<String> SPIC_MEDIA_PIC_LIST = new ArrayList<String>(){
		{
			add("鹤壁日报");
			add("淇河晨报");
		}
	};

	//拿数据的消息队列名称(正式)
	public static final String TOPIC = "pdftopic";
//	public static final String TOPIC = "testpdftopic";
	//消息队列对应的根路径
	public static final String ZOOKEEPER_ROOT = "";
	//web消息队列(正式)
	public static final String STORM = "xl_pdf_storm_formal_3";//TODO 正式

	//web消息队列(测试)
//	public static final String STORM = "xl_pdf_storm_test_20180329_5";//TODO 测试
	//web消息队列(测试)
//	public static final String STORM = "xl_pdf_storm_test_1";//TODO 测试

	//日志输出信息开头
	public static final String LOG_OUT_INFO = "press";

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

	//全部数据入任务code
	public static final String WEB_INSERT_ALLDATA_CODE = "pressInsertAllData";

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

	//错误数据入库
	public static final String ERROR_DATA_INSERT_DB_URL = "http://192.168.10.18:8082/xl_api/DataProcessing/SetArticleInfoDataPass";

	//拷屏图入库接口
	public static final String DOCPICIMAGE_API = "http://192.168.10.18:8082/xl_api/DataProcessing/Setdoc_psimage";

	//PDF图片处理接口
	public static final String PDF_PROCESS_URL = "http://192.168.10.50:8083/dataApi/putTask";

	//JPG图片处理接口
	public static final String JPG_PROCESS_URL = "http://192.168.10.50:8083/dataApi/thumbnailImage";

	//成功处理接口
	public static final String ADD_SUCESS_PROCESS_URL = "http://192.168.10.50:8083/dataApi/addSucessCount?date=DATE_VALUE&mediaName=MEDIANAME_VALUE";

	public static Map<String,Map<String,Integer>> COUNT_MAP = new ConcurrentHashMap<>();
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



	public static void main(String[] args)throws Exception {
		CommonData.getIctclasByContent("原标题：汽车发动机保养常识，别再伤车了爱车人士都知道，发动机相当于汽车的心脏，心脏若不好好保养，就很容易出现问题，但是发动机要怎么保养好呢？");
	}
}
