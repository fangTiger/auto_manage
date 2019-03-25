package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.LogOldBean;
import com.xl.manage.bean.WarnCodeUserR;
import com.xl.manage.common.LogCommonData;
import com.xl.tool.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志预警处理
 * @Author:lww
 * @Date:9:12 2017/11/9
 */
public class LogWarningService {

	/* 预警规则缓存 */
	public static List<WarnCodeUserR> warnCodeUserRList = new ArrayList<>();
	/* 最新预警规则时间戳 */
	public static long WARN_STEMP = 0l;

	public static Map<String,String> warnMap = new HashMap<>();
	/**
	 * 预警
	 * @return com.xl.basic.bean.LogOldBean
	 * @Author: lww
	 * @Description:
	 * @Date: 9:13 2017/11/9
	 * @param bean
	 */
	public LogOldBean warning(LogOldBean bean)throws Exception{


		String code;
		long time = System.currentTimeMillis();
		String warninfo[];

		boolean isTrue = false;
		try{
			if(initWarnRule()){//初始化预警成功

				code = bean.getPlatformCode()+"_"+bean.getModuleCode()+"_"+bean.getOperationCode();

				if(warnMap.containsKey(code)){
					warninfo = warnMap.get(code).split("_");
					if(time - Long.parseLong(warninfo[0])>(30*60*1000)){
						warnMap.put(code,time+"_1");
						isTrue = true;
					}else{
						if(Integer.parseInt(warninfo[1])>10){
							warnMap.put(code,warninfo[0]+"_"+(Integer.parseInt(warninfo[1])+1));
						}else{
							warnMap.put(code,warninfo[0]+"_"+(Integer.parseInt(warninfo[1])+1));
							isTrue = true;
						}
					}
				}else{
					warnMap.put(code,time+"_1");
					isTrue = true;
				}
				if(isTrue){
					//发送预警
					this.sendWarning(bean);
				}
			}
		}catch (Exception e){
			bean = null;
			LogHelper.error("--------预警出现异常！----------",e);
		}
		return bean;
	}



	/**
	 * 初始化预警
	 * @return boolean
	 * @Author: lww
	 * @Description:
	 * @Date: 18:54 2017/11/28
	 * @param
	 */
	public static boolean initWarnRule(){

		String result = "";
		long stemp;
		List<WarnCodeUserR> list;
		try{
			result = HttpClientPoolUtil.executeText(LogCommonData.WARN_STEMP_URL);
			stemp = Long.parseLong(result);
			if(stemp!=WARN_STEMP){
				result = HttpClientPoolUtil.executeText(LogCommonData.WARN_RULE_URL);
				System.out.println(result);
				if(!"-1".equals(result)){
					list = JSON.parseArray(result,WarnCodeUserR.class);
					warnCodeUserRList = list;
					WARN_STEMP = stemp;
				}else{
					LogHelper.info("--------------获取预警集合为空！------------------");
				}
				return true;
			}else{
				if(warnCodeUserRList!=null&&warnCodeUserRList.size()>0){
					return true;
				}else{
					return false;
				}
			}
		}catch (Exception e){
			LogHelper.error("--------------------发送预警出现异常！---------------------",e);
		}
		return false;
	}

	/**
	 * 发送预警
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 18:58 2017/11/28
	 * @param bean
	 */
	private void sendWarning(LogOldBean bean)throws Exception{

		if(!warnCodeUserRList.isEmpty()){
			for(WarnCodeUserR rBean:warnCodeUserRList){
				if(rBean.getLevel()==1){//平台预警
					if(StringUtil.toTrim(bean.getPlatformCode()).equals(rBean.getCode())){
						this.sendWX(bean,rBean);
					}
				}else if(rBean.getLevel()==2){//模块预警
					if(StringUtil.toTrim(rBean.getCode()).split("_")[1].equals(bean.getModuleCode())){
						this.sendWX(bean,rBean);
					}
				}else if(rBean.getLevel()==3){//操作预警
					if(StringUtil.toTrim(rBean.getCode()).split("_")[2].equals(bean.getOperationCode())){
						this.sendWX(bean,rBean);
					}
				}
			}
		}
	}

	/**
	 * 发送预警
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 18:58 2017/12/18
	 * @param bean
	 * @param rBean
	 */
	private void sendWX(LogOldBean bean,WarnCodeUserR rBean)throws Exception{

		JSONObject obj = new JSONObject();
		String title = "新联预警:"+rBean.getDesc();
		String error = StringUtil.toTrim(bean.getErrorDescribe()).replace("\"","");
		String content = "平台-"+bean.getPlatformCode()+";模块-"+bean.getModuleCode()+";操作-"+bean.getOperationCode()+",操作名称-"+bean.getOperationName()+",异常信息-"+error.substring(0,error.length()>100?100:error.length());
		String json = "{\"remark\":{\"color\":\"#173177\",\"value\":\"\"},\"keyword1\":{\"color\":\"#000000\",\"value\":\""+content+"\"},\"keyword2\":{\"color\":\"#173177\",\"value\":\""+ DateHelper.formatDateString(StringUtil.toTrim(bean.getCreateTime()).replace("/", "-").replace(".0", ""),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_DATETIME)+"\"},\"first\":{\"color\":\"#173177\",\"value\":\""+title+"\"}}";
		obj.put("touser", rBean.getOpenId());//|oEdr4t1KQRzOWfsKTI2WdVOqa-3M
    	obj.put("template_id", "3FGxwJX1rC2cMV8BjKPQIz_z60R3382ILftnKgZtNv4");
    	obj.put("url", "");
    	obj.put("data", JSON.parse(json));

		String result;
		JSONObject jsonObj;
		try{
			result = HttpClientPoolUtil.execute(LogCommonData.SEND_WX_WARNING_URL,obj.toString());
			jsonObj = JSON.parseObject(result);
			if("success".equals(StringUtil.toTrim(jsonObj.getString("code")))){
				LogHelper.info("发送预警成功！");
			}else{
				LogHelper.error("调用发送预警API出现异常",new Exception("调用发送预警API出现异常！"));
			}
		}catch (Exception e){
			LogHelper.error("发送预警出现异常！",e);
		}
	}

	public static void main(String[] args) {

		System.out.println(LogWarningService.initWarnRule());;
		/*JSONObject obj = new JSONObject();
		String title = "新联预警:";
		String content = "平台：";
		String json = "{\"remark\":{\"color\":\"#173177\",\"value\":\"\"},\"keyword1\":{\"color\":\"#000000\",\"value\":\""+content+"\"},\"keyword2\":{\"color\":\"#173177\",\"value\":\"2017-11-22 18:55:00\"},\"first\":{\"color\":\"#173177\",\"value\":\""+title+"\"}}";
		obj.put("touser", "oEdr4t-3ZqgS5nkelAjq_7U7nb7E,oEdr4t1KQRzOWfsKTI2WdVOqa-3M");//|oEdr4t1KQRzOWfsKTI2WdVOqa-3M
		obj.put("template_id", "3FGxwJX1rC2cMV8BjKPQIz_z60R3382ILftnKgZtNv4");
		obj.put("url", "");
		obj.put("data", JSON.parse(json));

		String result = HttpClientPoolUtil.execute(LogCommonData.SEND_WX_WARNING_URL,obj.toString());

		System.out.println(result);*/

	}

}
