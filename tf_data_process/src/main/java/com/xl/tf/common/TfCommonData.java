package com.xl.tf.common;

import com.alibaba.fastjson.JSONObject;

/**
 * irtv常量
 * @Author:lww
 * @Date:9:58 2017/9/8
 */
public class TfCommonData {

	//拿数据的消息队列名称
	public static final String TF_TOPIC = "tf_storm";
	//消息队列对应的根路径
	public static final String TF_ZOOKEEPER_ROOT = "";
	//tf消息队列(正式)
	public static final String TF_STORM = "tf_storm_data";//TODO 正式
	//tf消息队列(测试)
//	public static final String TF_STORM = "tf_storm_data_test_110";//TODO 测试

	private static final String DATA_PROCESS_URL = "http://192.168.10.55:8083"; //TODO 正式
//	private static final String DATA_PROCESS_URL = "http://192.168.10.55:8087"; //TODO 测试

//	private static final String DATA_PROCESS_URL = "http://192.168.0.215:8080";
	//情感值获取url
	public static final String AFFECTION_URL = DATA_PROCESS_URL + "/sentiment/getAffection";

	//分类器地址
	public static final String CLASSIFER_URL = DATA_PROCESS_URL + "/classifier/getHitByRuleOrgId";

	//微博语义指纹获取url
	public static final String FINGERPRINT_URL = DATA_PROCESS_URL + "/fingerWbApi/getSig";

	private static final String INSERT_TF_USER_URL = "http://192.168.10.61:8098";

	public static final String TF_USER_INSERT_URL = INSERT_TF_USER_URL+"/TFWeiboUserAdd";

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
	public static final String TF_INSERT_ALLDATA_CODE = "tfInsertAllData";

	//有效数据入任务code
	public static final String TF_INSERT_DATA_CODE = "tfInsertData";

	/** 限定机构ID */
	public static final String ORGID_STR = "";//TODO 正式

	/** 限定机构ID */
//	public static final String ORGID_STR = "315";//TODO 测试
}
