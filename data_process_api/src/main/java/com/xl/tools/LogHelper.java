package com.xl.tools;

/**
 * @Author:lww
 * @Date:16:31 2017/8/23
 */
public class LogHelper {

	/**
	 * 日志输出
	 * @Author: lww
	 * @Description:
	 * @Date: 16:33 2017/8/23
	 * @params:
	 */
	public static void info(String info){
		System.out.println("["+ DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME)+"]"+info);
	}

	/**
	 * 异常输出
	 * @Author: lww
	 * @Description:
	 * @Date: 16:33 2017/8/23
	 * @params:
	 */
	public static void error(String error,Exception e){
		System.out.println("["+ DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME)+"]"+error+" [exception:"+e.getMessage()+"]");
	}
}
