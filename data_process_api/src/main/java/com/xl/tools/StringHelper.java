package com.xl.tools;

import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author xie_yan
 * 
 */
public class StringHelper{


	public static String toTrim(String msg){
		if(msg==null){
			return "";
		}
		String str = msg.trim();
		try{
			while(str.startsWith(" ")){
				str = str.substring(1,str.length()).trim();
			}
			while(str.endsWith(" ")){
				str = str.substring(0,str.length()-1).trim();
			}
		}catch (Exception e){
			str = "";
		}
		return str;
	}

	/**
	 * 判断字符串是否是数字
	 * 
	 * @author xieyan
	 * @param srcStr
	 *            String, 要判断的源字符串
	 * @return true, 是数字；false, 不是数字
	 */
	public static boolean isDigit(String srcStr) {
		if (srcStr == null) {
			return false;
		}
		int srcStrLenght = srcStr.length();
		if (srcStrLenght <= 0) {
			return false;
		}
		for (int i = 0; i < srcStrLenght; i++) {
			if (i == 0 && srcStr.charAt(i) == '-') {
				continue;
			}
			if (!Character.isDigit(srcStr.charAt(i))) {
				return false;
			}
			return true;
		}
		return false;
	}

	/***
	 * 切断字符串 （没有省略号）
	 * 
	 * @param input
	 * @param length
	 * @return
	 */
	public static String formatStringNoPoint(String input, int length) {
		String temp = "<SPAN title=\"" + input + "\">";
		temp += StringHelper.formatString(input, length);
		temp += "</SPAN>";
		return temp;
	}

	/**
	 * 切断字符串
	 * 
	 * @param msg
	 * @param fixLength
	 * @return
	 */
	public static String formatString(String msg, int fixLength) {
		if (msg == null)
			msg = "";
		if (msg.trim().equals("null") || msg.trim().equals(""))
			msg = "";
		byte data[] = msg.getBytes();
		if (data.length <= fixLength)
			return msg;
		byte tmp[] = new byte[fixLength];
		System.arraycopy(data, 0, tmp, 0, fixLength);
		data = tmp;
		int count = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[data.length - 1 - i] >= 0)
				break;
			count++;
		}

		switch (count % 2) {
		case 1: // '\001'
			byte tmp2[] = new byte[data.length - 1];
			System.arraycopy(data, 0, tmp2, 0, data.length - 1);
			data = tmp2;
			break;
		}
		String reSult = new String(data);
		return reSult;
	}

	public static String getZeroToLine(int num) {
		String result = "";
		if (num == 0) {
			result = "-";
		} else {
			result = num + "";
		}
		return result;
	}

	/**
	 * 将模板中的变量替换成相应的栏目数据
	 * 
	 * @param variable
	 * @return
	 */
	public static String replaceValue(String templateContent, String variable, String value) {
		variable = "${" + variable + "}$";
		templateContent = templateContent.replace(variable, value);
		return templateContent;
	}


	/**
	 * 字段为空的话，写成N/A形式
	 * 
	 * @Title: isNull
	 * @data:2012-9-7上午11:55:51
	 * @author:zxd
	 * 
	 * @param result
	 * @return
	 */
	public static String isNull(String result) {
		if (result == null || result.equals("")) {
			result = "N/A";
		}
		return result;

	}

	

	/**
	 * 取src里面的内容
	 * 
	 * @Title: matchIMGSrc
	 * @data:2012-10-12下午04:31:14
	 * @author:zxd
	 * 
	 * @param content
	 */
	public static String matchIMGSrc(String content) {
		String src = "";
		if (content != null && !content.equals("")) {
			Matcher ma = Pattern.compile("<IMG.*src=(.*?)[^>]*?>").matcher(content);
			while (ma.find()) {
				ma = Pattern.compile("src=\"(.*?)\"{1}?").matcher(ma.group());
				while (ma.find()) {
					src += ma.group(1) + ",";
				}
			}
		}
		if (src!=null&&!"".equals(src)) {
			src = src.substring(0, src.length() - 1);
		}
		return src;
	}

	/**
	 * 删除P标签
	 * @Title: delLabelP
	 * @data:2013-5-24上午9:51:16
	 * @author:wxy
	 *
	 * @param content
	 * @return
	 */
	public static String delLabelP(String content){
		content = content.replaceAll("<BR>", "");
		content = content.replaceAll("<br/>", "");
		content = content.replaceAll("<br>", "");
		content = content.replaceAll("<P ALIGN=CENTER>", "");
		content = content.replaceAll("<P ALIGN=CENTER>", "");
		content = content.replaceAll("</P> </P>", "</P>");
		return content;
	}
	
	/**
	 * 删除P标签
	 * @Title: delLabelP
	 * @data:2013-5-24上午9:51:16
	 * @author:wxy
	 *
	 * @param content
	 * @return
	 */
	public static String delLabelP2(String content){
		if (content != null && !content.equals("")) {
			Matcher ma = Pattern.compile("<[^>]*>([ 　]*)<[^>]*>").matcher(content);
			while (ma.find()) {
				content =content.replace(ma.group(1), "");
			}
			content = content.replaceAll("</P></?P>", "</P>");
			content = content.replaceAll("</p></?p>", "</P>");
			if (content.contains("</P>")) {
				content = content.replaceAll("\r", "");
				content = content.replaceAll("\n", "");
			}else {
				content = content.replaceAll("\r\n　", "</P>　");
				content = content.replaceAll("\r\n ", "</P> ");
				content = content.replaceAll("\r\n", "");
			}
			content = content.replaceAll("</P>", "\n");
			content = content.replaceAll("</p>", "\n");
			content = content.replaceAll("<[^>]*>", "");
			//content = content.replaceAll("http:*\\.jpg", "");
			content = content.replaceAll("[\n][ 　]*[\n]", "\n");
			content = content.replaceAll("。\n　", "。</P>　");
			content = content.replaceAll("。\n　", "。</p>　");
			content = content.replaceAll("，\n　", "，</P>　");
			content = content.replaceAll("，\n　", "，</p>　");
			content = content.replaceAll("\n　", "</P>　");
			content = content.replaceAll("\n　", "</p>　");
			content = content.replaceAll("\n ", "</P> ");
			content = content.replaceAll("\n ", "</p> ");
			content = content.replaceAll("\n", "");
			content = content.replaceAll("</P>", "\n");
			content = content.replaceAll("</p>", "\n");
		}
		return content;
	}
	/**
	 * 删除IMG标签
	 * @Title: delteImg2
	 * @data:2013-5-24上午9:52:44
	 * @author:wxy
	 *
	 * @param content
	 * @return
	 */
	public static String delteImg2(String content) {
		if (content != null && !content.equals("")) {
			Matcher ma = Pattern.compile("<IMG[^>]*>").matcher(content);
			while (ma.find()) {
				content =content.replace(ma.group(), "");
			}
			Matcher ma1 = Pattern.compile("<img[^>]*>").matcher(content);
			while (ma1.find()) {
				content =content.replace(ma1.group(), "");
			}
			content = content.replace("<P>", "");
			content = content.replace("<p>", "");
			content = content.replace("</P>", "\n");
			content = content.replace("</p>", "\n");
			content = content.replace(">", "");
		}
		return content;
	}
	
	/**
	 * 删除IMG标签
	 * 
	 * @Title: delteImg
	 * @data:2012-10-12下午04:53:05
	 * @author:zxd
	 * 
	 * @return
	 */
	public static String delteImg(String content) {
		if (content != null && !content.equals("")) {
			Matcher ma = Pattern.compile("<P>　　<P ALIGN=CENTER><IMG.*src=(.*?)[^>]*?></P> </P>").matcher(content);
			while (ma.find()) {
				content =content.replace(ma.group(), "");
			}
			content = content.replace(" ", "");
			content = content.replace("<P>", "");
			content = content.replace("</P>", "\n");
			content = content.replace(">", "");
		}
		return content;
	}

	/**
	 * 取出英文括号里面的内容
	 * 
	 * @Title: getKuoHao
	 * @data:2012-10-16下午02:29:22
	 * @author:zxd
	 * 
	 * @param src
	 * @return
	 */
	public static String getKuoHao(String src) {
		String result = "";
		if (src != null && !src.equals("")) {
			Matcher ma = Pattern.compile("\\(([^\\)]*)\\)").matcher(src);
			while (ma.find()) {
				result = ma.group(1);
			}
		}
		return result;
	}

	/**
	 * 取中文括号里面的内容
	 * 
	 * @Title: getKuoHao2
	 * @data:2012-10-16下午02:33:31
	 * @author:zxd
	 * 
	 * @param src
	 * @return
	 */
	public static String getKuoHao2(String src) {
		String result = "";
		if (src != null && !src.equals("")) {
			Matcher ma = Pattern.compile("\\（([^\\)]*)\\）").matcher(src);
			while (ma.find()) {
				result = ma.group(1);
			}
		}
		return result;
	}

	/**
	 * 使字符串==0,000,000
	 * 
	 * @Title: getDataFormatShuZi
	 * @data:2012-10-22下午05:37:17
	 * @author:zxd
	 * 
	 * @return
	 */
	public static String getDataFormatShuZi(int number) {
		String result = "0";
		if (number > 0) {
			NumberFormat nf = NumberFormat.getInstance();
			result = nf.format(number);
		}
		return result;
	}
	/**
	 * 字符串转成为00,000,000
	 * @Title: getDataFormatShuZi
	 * @data:2012-11-9下午03:53:56
	 * @author:zxd
	 *
	 * @param number
	 * @return
	 */
	public static String getDataFormatShuZi3(String number) {
		String result = "0";
		if(number!=null && !number.equals("")){
			int num = Integer.parseInt(number);
			if (num > 0) {
				NumberFormat nf = NumberFormat.getInstance();
				result = nf.format(num);
			}
		}
		return result;
	}
	/**
	 * long型转换00,000,000
	 * @Title: getDataFormatShuZi2
	 * @data:2012-10-25上午10:47:15
	 * @author:zxd
	 *
	 * @param number
	 * @return
	 */
	public static String getDataFormatShuZi2(long number) {
		String result = "0";
		if (number > 0) {
			NumberFormat nf = NumberFormat.getInstance();
			result = nf.format(number);
		}
		return result;
	}

	/**
	 * 判断字符串是否是英文
	 * @Title: isEnglish
	 * @data:2013-3-28上午11:15:12
	 * @author:lilei
	 *
	 * @param str
	 * @return
	 */
	public static boolean isEnglish(String str){
		//去掉空格
		String s = str.replaceAll(" ", "");
		Pattern pattern = Pattern.compile("[a-zA-Z]+"); 
        Matcher m = pattern.matcher(s);
        return m.matches();  
	}
	/**
	 * <>  ---> &gt; &lt;
	 * @Title: stringFilter
	 * @data:2012-12-17上午11:36:32
	 * @author:liweidong
	 *
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str){
		if(str!=null){
			str = str.replaceAll(">", "&gt;");
			str = str.replaceAll("<", "&lt;");
			str = str.replaceAll("\"", "&quot;");
			return str;
		}
		return "";
	}
	/**
	 * 过滤字符串中的特殊字符
	 * @Title: stringFilter2
	 * @data:2013-12-10下午4:55:34
	 * @author:lilei
	 *
	 * @param str
	 * @return
	 */
	public static String stringFilter2(String str){
		if(str != null){
			StringBuffer sb = new StringBuffer();
			String[] strings = str.split("");
			for(String string : strings){
				if(Charset.forName("UTF-8").newEncoder().canEncode(string)){
					sb.append(string);
				}
			}
			return sb.toString();
		}else{
			return "";
		}
	}
//	public static void main(String[] args) {
//		String str1 = "新闻";
//        //String str2 = "C0";
//        //String str3 = "#瓷肌?2013快乐男声全国巡演上海站#@花啦啦啦啦愛宇不停 @雲吞-SUE__ @好想消失__ 小编，我即不会作图也不会写文章，只能疯狂转发，求求你，请赐我一张票，我真的很想去看花花，我的真很想再去'🙏🙏🙏🙏🙏🙏 😭😭😭😭";
//        /*String[] strs = str3.split("");
//        for(String string : strs){
//        	System.out.println(string);
//        	System.out.println(Charset.forName("UTF-8").newEncoder().canEncode(string));
//        }*/
//        char[] str = str1.toCharArray();
//        for(char c : str){
//        	System.out.println(Character.valueOf(c).hashCode());
//        }
//
//       /* long time1 = System.currentTimeMillis();
//        for(int i = 0;i < str3.length()-1;i++){
//        	String string = str3.substring(i,i+1);
//        	System.out.println(string);
//        	System.out.println(Charset.forName("UTF-8").newEncoder().canEncode(string));
//        }*/
////        System.out.println(System.currentTimeMillis()-time1);
////        System.out.print(str2.compareTo(str1));
////		System.out.println(StringHelper.getCNString("大润发：已积极整改\"黑尾巴\"班车欢迎监督"));
////		System.out.println(StringHelper.getCNString("耐克鞋真他妈坑人，不赶咱们李宁。再也不买耐克了，卡脚不说，还不给换。 我在:http://t.cn/zT2DkXp"));
////		System.out.println(StringHelper.checkSimilar(StringHelper.getCNString("李宁，，，，，，，，，，，一切皆有可能。。。。。。。"),
////				StringHelper.getCNString("耐克鞋真他妈坑人，不赶咱们李宁。再也不买耐克了，卡脚不说，还不给换。 我在:http://t.cn/zT2DkXp")));
//	}
}
