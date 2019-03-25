package com.xl.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.KafkaPdfArticleBean;
import com.xl.bean.article.LogCacheBean;
import com.xl.manage.common.CommonData;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Author:lww
 * @Date:10:38 2017/7/7
 */
public class LogHelper {

	//日志kafkaTopic
	public static final String KAFKA_TOPICNAME = "log_data";

	//日志入kafka接口
	public static final String INSERT_KAFKA_URL = "http://192.168.10.72:8083/kafkaConsume/kafkaApi/putKafkaData";


	public static void info(String info){
		System.out.println("["+DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME)+"][INFO]:"+info);
	}

	public static void error(String optionCode,String optionName,String param,String error,Exception e){
		System.out.println("["+DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME)+"][ERROR]:"+error+" [exception]:"+getErrorInfoFromException(e));
//		LogCommonData.recordLogData(optionCode,optionName,"",param,error,"false",getErrorInfoFromException(e));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String[] arr = {"111", "222"};
			arr[2] = "fff";
		} catch (Exception e) {
			String info = getErrorInfoFromException(e);
			System.out.println(info);
		}
	}

	public static String getErrorInfoFromException(Exception e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return sw.toString();
		} catch (Exception e2) {
			return "bad getErrorInfoFromException";
		}
	}

	public static void sendMessage(KafkaPdfArticleBean bean){

		try{
			String content = "{'url':'"+bean.getUrl()+"','imageName':'"+bean.getImageName()+"','crawlType':'"+bean.getCrawlType()+"','title':'"+bean.getTitle()+"','contentText':'"+bean.getContentText()+"'}";
			recordLogData("dataFormatError","数据格式异常","",content,"缩略图地址异常","false","PDF数据格式异常");
			for(String openId: CommonData.openIds){
				sendWX(openId,content);
			}
		}catch (Exception e){

		}
	}

	public static void sendMessageTimeOut(KafkaPdfArticleBean bean){

		try{
			String content = "{'url':'"+bean.getUrl()+"','imageName':'"+bean.getImageName()+"','crawlType':'"+bean.getCrawlType()+"','title':'"+bean.getTitle()+"','contentText':'"+bean.getContentText()+"'}";
			LogHelper.info("PDF处理超时！"+content);
//			recordLogData("dataFormatError","PDF处理超时","",content,"PDF处理超时","false","PDF处理超时");
			/*for(String openId: CommonData.openIds){
				sendWX(openId,content);
			}*/
		}catch (Exception e){

		}
	}

	/**
	 * 将日志插入到缓存中
	 * Created by bagen
	 * @Description:
	 * @create  2017/8/25 - 16:27
	 * @Modificed By:
	 */
	private static void recordLogData(String operatonCode, String operactionName, String dataId,
									 String requestParam, String describe, String resultFlag, String errorDescribe){
		LogCacheBean logBean = new LogCacheBean();
		logBean.setPlatformCode("dataProcess");
		logBean.setModuleCode("PDFProcess");
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
			LogHelper.info("错误数据入kafka："+obj.toJSONString());
			String result = HttpClientPoolUtil.execute(INSERT_KAFKA_URL, obj.toJSONString());
			JSONObject object = JSON.parseObject(result);
			if(!"success".equals(StringUtil.toTrim(object.getString("code")))){
				LogHelper.info("日志数据入卡夫卡出现异常！");
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 发送预警
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 18:58 2017/12/18
	 */
	private static void sendWX(String openId,String content)throws Exception{

		JSONObject obj = new JSONObject();
		String title = "新联预警:PDF处理异常！";
		String error = "PDF数据参数异常！";
		String json = "{\"remark\":{\"color\":\"#173177\",\"value\":\"\"},\"keyword1\":{\"color\":\"#000000\",\"value\":\""+content+"\"},\"keyword2\":{\"color\":\"#173177\",\"value\":\""+ DateHelper.formatDateString(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME).replace("/", "-").replace(".0", ""),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_DATETIME)+"\"},\"first\":{\"color\":\"#173177\",\"value\":\""+title+"\"}}";
		obj.put("touser", openId);
		obj.put("template_id", "3FGxwJX1rC2cMV8BjKPQIz_z60R3382ILftnKgZtNv4");
		obj.put("url", "");
		obj.put("data", JSON.parse(json));

		String result;
		JSONObject jsonObj;
		try{
			result = HttpClientPoolUtil.execute("http://59.110.22.117/send/sendTemplate",obj.toString());
			jsonObj = JSON.parseObject(result);
			if("success".equals(StringUtil.toTrim(jsonObj.getString("code")))){
				LogHelper.info("发送预警成功！");
			}else{
				LogHelper.info("调用发送预警API出现异常");
			}
		}catch (Exception e){
			LogHelper.info("发送预警出现异常！");
		}
	}
}
