package com.xl.tool;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据格式化工具类
 * @Author:lww
 * @Date:11:36 2017/11/18
 */
public class PublicClass {



	/// <summary>
	/// 过滤html标签
	/// </summary>
	/// <param name="strHtml">html的内容</param>
	/// <returns></returns>
	public static String StripHTML(String stringToStrip){
		// paring using RegEx           //
		stringToStrip = stringToStrip.replaceAll("(?i)</p(?:\\s*)>(?:\\s*)<p(?:\\s*)>", " ");//段落替换成\n\n
		stringToStrip = stringToStrip.replaceAll("(?i)<br[^>]*>", " ");//<br>替换成\n
		stringToStrip = stringToStrip.replaceAll("(?i)\"", "''");
		stringToStrip = stringToStrip.replaceAll("——", "");
		stringToStrip = StripHtmlXmlTags(stringToStrip);
		return stringToStrip;
	}

	private static String StripHtmlXmlTags(String content){
		return content.replaceAll("<[^>]+>", "");
	}

	/**
	 * 去除正文多余的标签（只保留p img iframe ）
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 11:20 2017/12/11
	 * @param content
	 */
	public static String replaceAllStyle(String content){

		String regex = "<[^>]+>";
		Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);

		Matcher m = p.matcher(content);
		String val;

		while (m.find()){
			val = m.group();
			if(val.toLowerCase().indexOf("<p")>-1){
				content = content.replace(val, "<p>");
			}else{
				if(val.toLowerCase().indexOf("<iframe")==-1&&
						val.toLowerCase().indexOf("</iframe>")==-1&&
						val.toLowerCase().indexOf("<!--_img_-->")==-1&&
						val.toLowerCase().indexOf("<!--_table_-->")==-1&&
						val.toLowerCase().indexOf("<!--/_table_-->")==-1&&
						val.toLowerCase().indexOf("<img")==-1&&
						val.toLowerCase().indexOf("</p")==-1){
					content = content.replace(val, "");
				}
			}
		}


		content = replaceImgTag(content);

		content = content.replace("<p></p>", "").replace("&amp;", "&");
		return content;
	}

	/// <summary>
	/// 替换文字内的img标签 只保留src属性
	/// </summary>
	/// <param name="HtmlCode"></param>
	/// <returns></returns>
	public static String replaceImgTag(String HtmlCode)
	{
		//zy--2017-6-20
		String newHtml = HtmlCode;
		String regex = "<img.*?src=[\"'].*?>";

		Pattern p = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);

		Matcher m = p.matcher(newHtml);

		String val;
		String imgRegex = " src=[\"'].*?[\"']";
		Pattern imgPattern = Pattern.compile(imgRegex,Pattern.CASE_INSENSITIVE);
		Matcher imgMatcher;

		String img;
		while (m.find()){
			val = m.group();
			imgMatcher = imgPattern.matcher(val);
			if(imgMatcher.find()){
				img = "<img "+imgMatcher.group()+">";
			}else{
				img = "<img>";
			}
			newHtml = newHtml.replace(val, img);
		}
		return newHtml;
	}

}
