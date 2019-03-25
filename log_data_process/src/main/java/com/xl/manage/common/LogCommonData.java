package com.xl.manage.common;

/**
 * weibo常量
 * @Author:lww
 * @Date:9:58 2017/9/8
 */
public class LogCommonData {

	//拿数据的消息队列名称
	public static final String LOG_TOPIC = "log_data";
	//消息队列对应的根路径
	public static final String LOG_ZOOKEEPER_ROOT = "";
	//log消息队列(正式)
	public static final String LOG_STORM = "log_storm_1";

//	public static final String LOG_STORM = "log_storm_test_3";
	//日志输出信息开头
	public static final String LOG_OUT_INFO = "logData";

	public final static String platformCode = "iLogDeal";

	/* 特殊操作类型仅取aid\type/sourceType */
	public final static String SPIC_OPTION_VALUE = ",iNews|monitorPage|edit_EditArticle,";

	// 判断是否是特殊的操作类型 - 需要进行url拆条的
	public static final String SPICOPTION = ",iNews|upload|upload_copy,iNews|upload|upload_batchList,iNews|upload|upload_batch,iNews|upload|upload_batchStart,";


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

	//有效数据入任务code
	public static final String LOG_INSERT_DATA_CODE = "logInsertData";

	//数据处理流出错日志入kafka地址
	public static final String LOG_KAFKA_URL = "http://192.168.10.72:8083/kafkaConsume/kafkaApi/putKafkaData";
	//获取规则集合
	public static final String WARN_RULE_URL = "http://192.168.10.71:8082/setting/getSettingList";
	/* 获取规则时间戳 */
	public static final String WARN_STEMP_URL = "http://192.168.10.71:8082/setting/getSettingStemp";
	/* 发送微信预警 */
	public static final String SEND_WX_WARNING_URL = "http://59.110.22.117/send/sendTemplate";
}
