package com.xl.tools;

/**
 * @Author:lww
 * @Date:16:03 2017/10/19
 */
public class ArticleDataHelper {

	/// <summary>
	/// 整理导航字符串
	/// </summary>
	/// <param name="navigator"></param>
	/// <returns></returns>
	public static String GetNavigator(String navigator){
		if (navigator != null && navigator.trim().length() > 0){
			String result = "";
			navigator = navigator.replaceAll("\n", " ").replaceAll("\t", " ").replaceAll("\t\n", " ").replaceAll("\r\n", " ");

			//使用新版本替换
			navigator = PublicClass.replaceO(navigator, "navigator");


			for (String s : navigator.split(" ")){
				if (!s.trim().contains("http://")){
					result += s.trim() + " ";
				}
			}
			return result.trim();
		}
		else
		{
			return "";
		}
	}

}
