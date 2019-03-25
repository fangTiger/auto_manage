package com.xl.tool;

/**
 * @Author:lww
 * @Date:10:38 2017/7/7
 */
public class LogHelper {

	public static void info(String info){
		System.out.println("["+DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME)+"][INFO]:"+info);
	}

	public static void error(String error,Exception e){
		System.out.println("["+DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME)+"][ERROR]:"+error+" [exception]:"+e.getMessage());
		e.printStackTrace();
	}
}
