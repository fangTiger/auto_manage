package com.xl.irtv.common;

import com.alibaba.fastjson.JSONObject;

/**
 * irtv常量
 * @Author:lww
 * @Date:9:58 2017/9/8
 */
public class IrtvCommonData {

	//拿数据的消息队列名称
	public static final String IRTV_TOPIC = "irtv_data";

	//消息队列对应的根路径
	public static final String IRTV_ZOOKEEPER_ROOT = "";
	//irtv消息队列(正式)
	public static final String IRTV_STORM = "irtv_storm";//TODO 正式

	//irtv消息队列(测试)
//	public static final String IRTV_STORM = "irtv_storm_111";//TODO 测试

	private static final String DATA_PROCESS_URL = "http://192.168.10.55:8083";//TODO 正式
//	private static final String DATA_PROCESS_URL = "http://192.168.10.55:8087";//TODO 测试

	//实体抽取地址
	public static final String EXTRACTION_URL = DATA_PROCESS_URL + "/sentiment/docExtract";

	//分类器地址
	public static final String CLASSIFER_URL = DATA_PROCESS_URL + "/classifier/getHitByRuleOrgId";

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
	public static final String IRTV_INSERT_ALLDATA_CODE = "irtvInsertAllData";

	//有效数据入任务code
	public static final String IRTV_INSERT_DATA_CODE = "irtvInsertData";

	/** 限定机构ID */
	public static final String ORGID_STR = "";//TODO 正式

	/** 限定机构ID */
//	public static final String ORGID_STR = "315";//TODO 测试

	public static JSONObject extraction(){
		return null;
	}

	public static JSONObject classifer(){
		return null;
	}
}
