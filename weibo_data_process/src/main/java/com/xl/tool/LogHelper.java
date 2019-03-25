package com.xl.tool;

import com.xl.manage.common.LogCommonData;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @Author:lww
 * @Date:10:38 2017/7/7
 */
public class LogHelper {


	public static void info(String info){
		System.out.println("["+DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME)+"][INFO]:"+info);
	}

	public static void error(String optionCode,String optionName,String param,String error,Exception e){
		System.out.println("["+DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME)+"][ERROR]:"+error+" [exception]:"+getErrorInfoFromException(e));
		LogCommonData.recordLogData(optionCode,optionName,"",param,error,"false",getErrorInfoFromException(e));
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
}
