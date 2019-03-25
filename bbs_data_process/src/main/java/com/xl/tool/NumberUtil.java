package com.xl.tool;

/**
 * @Author:lww
 * @Date:17:12 2017/12/13
 */
public class NumberUtil {

	/**
	 * 字符转转Int,默认为0
	 * @return int
	 * @Author: lww
	 * @Description:
	 * @Date: 17:14 2017/12/13
	 * @param str
	 */
	public static int stringToInt(String str){

		int result = 0;
		try{
			result = Integer.parseInt(StringUtil.toTrim(str));
		}catch (Exception e){
		}
		return result;
	}
}
