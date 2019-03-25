package com.xl.tool;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
