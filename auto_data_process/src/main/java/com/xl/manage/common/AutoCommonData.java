package com.xl.manage.common;

/**
 * @Author:lww
 * @Date:9:58 2017/9/8
 */
public class AutoCommonData {

	//拿数据的消息队列名称
	public static String AUTO_TOPIC = "auto_data";
	//消息队列对应的根路径
	public static String AUTO_ZOOKEEPER_ROOT = "";
	//汽车storm(正式)
	public static String AUTO_STORM = "auto_storm_test_13";

	//汽车接口
	private static final String AUTO_API_URL = "http://192.168.10.50:8092";

	//数据处理地址
	private static final String DATA_PROCESS_URL = "http://192.168.10.50:8091";

	//分类器接口
	public static final String CLASSIFIER_URL = DATA_PROCESS_URL + "/classifier/getHitByRuleUrl";

	//规则列表
	private static String AUTO_RULE_URL = AUTO_API_URL + "/auto/getRuleList?flag=FLAG_VALUE&type=TYPE_VALUE";

	//获取格式化后的规则
	private static String AUTO_FROMAT_RULE_URL = AUTO_API_URL + "/auto/getFormatRule?flag=FLAG_VALUE&type=TYPE_VALUE";

	//获取规则时间戳
	private static String AUTO_RULE_STEMP_URL = AUTO_API_URL + "/auto/getRuleStemp?flag=FLAG_VALUE&type=TYPE_VALUE";

	//文章入库
	public static final String AUTO_ARTICLE_INSERT_URL = AUTO_API_URL + "/auto/insertArticleBean";

	//获取规则列表 flag 0-信息源规则 1-内容规则 type 0-活动 1-主题
	public static String getRuleUrl(int flag,int type){
		return AUTO_RULE_URL.replace("FLAG_VALUE",flag+"").replace("TYPE_VALUE",type+"");
	}

	//获取格式化后的规则 flag 0-信息源规则 1-内容规则 type 0-活动 1-主题
	public static String getFormatRuleUrl(int flag,int type){
		return AUTO_FROMAT_RULE_URL.replace("FLAG_VALUE",flag+"").replace("TYPE_VALUE",type+"");
	}

	//获取规则时间戳 flag 0-信息源规则 1-内容规则 type 0-活动 1-主题
	public static String getRuleStempUrl(int flag,int type){
		return AUTO_RULE_STEMP_URL.replace("FLAG_VALUE",flag+"").replace("TYPE_VALUE",type+"");
	}
}
