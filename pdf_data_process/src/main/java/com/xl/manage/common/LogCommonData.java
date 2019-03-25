package com.xl.manage.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.bean.article.LogCacheBean;
import com.xl.tools.DateHelper;
import com.xl.tools.HttpClientPoolUtil;
import com.xl.tools.LogHelper;
import com.xl.tools.StringUtil;

/**
 * 日志常量
 * @Author:lww
 * @Date:14:57 2018/1/17
 */
public class LogCommonData {


	public static final String PLATFORM_CODE = "dataProcess";

	public static final String MOCULE_CODE = "dataProcessStorm";

	//日志kafkaTopic
	public static final String KAFKA_TOPICNAME = "log_data";

	//日志入kafka接口
	public static final String INSERT_KAFKA_URL = "http://192.168.10.72:8083/kafkaConsume/kafkaApi/putKafkaData";

	//日志操作类型
	public static final String LOG_CODE_WEB = "pdf";

	/**
	 * 将日志插入到缓存中
	 * Created by bagen
	 * @Description:
	 * @create  2017/8/25 - 16:27
	 * @Modificed By:
	 */
	public static void recordLogData(String operatonCode, String operactionName, String dataId,
									 String requestParam, String describe, String resultFlag, String errorDescribe){
		LogCacheBean logBean = new LogCacheBean();
		logBean.setPlatformCode(PLATFORM_CODE);
		logBean.setModuleCode(MOCULE_CODE);
		logBean.setOperationCode(operatonCode);

		logBean.setDataContent(requestParam);

		logBean.setOperationName(operactionName);
		logBean.setRequestParam(requestParam);
		logBean.setxRequestedWith(resultFlag);
		logBean.setDescribe(describe);
		logBean.setErrorDescribe(errorDescribe);
		logBean.setCreateTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		//TODO 插入kafka日志中

		try{
			JSONObject obj = new JSONObject();
			obj.put("topicName", KAFKA_TOPICNAME);
			obj.put("sinKafKey", operatonCode+"");//key值
			obj.put("sinKafValue", JSON.toJSON(logBean));//插入的数据值
			String result = HttpClientPoolUtil.execute(INSERT_KAFKA_URL, obj.toJSONString());
			JSONObject object = JSON.parseObject(result);
			if(!"success".equals(StringUtil.toTrim(object.getString("code")))){
				LogHelper.info("日志数据入卡夫卡出现异常！");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
