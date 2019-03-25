package com.xl.tool;

/**
 * 数值帮助类
 * @Author:lww
 * @Date:16:47 2017/11/8
 */
public class NumberHelper {

	/**
	 * 字符型转int
	 * @return int
	 * @Author: lww
	 * @Description:
	 * @Date: 16:48 2017/11/8
	 * @param num 字符串
	 */
	public static int stringToInt(String num){

		int result = 0;
		try{
			result = Integer.parseInt(num);
		}catch(Exception ex){
			result = 0;
		}
		return result;
	}

	/**
	 * 字符型转int
	 * @return int
	 * @Author: lww
	 * @Description:
	 * @Date: 16:49 2017/11/8
	 * @param num 字符串
	 * @param defult 出错时默认值
	 */
	public static int stringToInt(String num,int defult){

		int result = 0;
		try{
			result = Integer.parseInt(num);
		}catch(Exception ex){
			result = defult;
		}
		return result;
	}
}
