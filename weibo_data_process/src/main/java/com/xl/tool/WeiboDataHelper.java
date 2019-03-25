package com.xl.tool;

import com.alibaba.fastjson.JSON;
import com.xl.manage.common.LogCommonData;
import org.apache.storm.command.list;
import org.json.simple.JSONArray;
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
public class WeiboDataHelper {
	//省份集合
	public static List<String> PROVINCE_LIST = new ArrayList<>();

	static {
		String provinceInfo = "[\"湖南省\",\"海南省\",\"河南省\",\"陕西省\",\"山东省\",\"江西省\",\"山西省\",\"上海市\",\"重庆市\",\"河北省\",\"北京市\",\"天津市\",\"云南省\",\"贵州省\",\"福建省\",\"江苏省\",\"宁夏回族自治区\",\"青海省\",\"新疆维吾尔自治区\",\"甘肃省\",\"安徽省\",\"黑龙江省\",\"广西壮族自治区\",\"广东省\",\"四川省\",\"吉林省\",\"辽宁省\",\"内蒙古自治区\",\"浙江省\",\"湖北省\",\"香港特别行政区\",\"澳门特别行政区\",\"西藏自治区\",\"台湾省\"]";
		try {
			/*File f = new File("D:\\liweiwei\\iDeaWorkspace2017\\auto_manage\\weibo_data_process\\src\\main\\resources\\province_info.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(f);
			NodeList nl = doc.getElementsByTagName("RECORD");
			String name;
			for (int i = 0; i < nl.getLength(); i++) {
				name = doc.getElementsByTagName("name").item(i).getFirstChild().getNodeValue();
				PROVINCE_LIST.add(name);
			}*/

			PROVINCE_LIST = JSON.parseArray(provinceInfo,String.class);
			System.out.println("初始化地域信息成功！");
		} catch (Exception e) {
			System.out.println("初始化地域信息出现异常！");
			e.printStackTrace();
		}
	}

	public static void main(String[] args)throws Exception {

		System.out.println(WeiboDataHelper.getProvince("广东 广州".split(" ")[0]));
	}

	/**
	 * 获取省份信息
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 16:01 2017/10/19
	 * @param name
	 */
	public static String getProvince(String name)throws Exception{

		String provinceName = "";
		for(String province:PROVINCE_LIST){
			if(province.indexOf(name)>-1){
				provinceName = province;
			}
		}
		return provinceName;
	}

	/**
	 * 获取微博话题
	 * @return java.lang.String[]
	 * @Author: lww
	 * @Description:
	 * @Date: 16:12 2017/10/19
	 * @param statusText
	 */
	public static List<String> getConversations(String statusText)throws Exception{

		Pattern pattern;
		Matcher matcher;
		String temp;

		String regEx = "#[^#]+#";
		List<String> conversations = new ArrayList<>();
		try{
			if(statusText.contains("#")){

				// 编译正则表达式
				pattern = Pattern.compile(regEx);
				// 忽略大小写的写法
				// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(statusText);
				// 字符串是否与正则表达式相匹配
				while (matcher.find()){
					temp = StringUtil.toTrim(matcher.group()).replace("#", "");
					if (!conversations.contains(temp) && temp.length() > 0){
						conversations.add(temp);
					}
				}
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取微博话题", statusText,"获取微博话题出现异常！",e);
		}
		return conversations;
	}


	/// <summary>
	/// 去掉weibo正文中短连接以及HTML
	/// </summary>
	/// <param name="StatusText">微博内容</param>
	/// <returns></returns>
	public static String noWeiboUrl(String StatusText)
	{
		String result = StatusText;
		//剔除URL地址
		String start = "http://(t|163|126|url).(cn|fm)/\\w+";
		Pattern pattern = Pattern.compile(start);

		Matcher matcher = pattern.matcher(result);
		String temp;
		while (matcher.find()){
			result = result.replaceAll(start, "");
		}

		//剔除表情符号
		start = "\\[[a-zA-Z0-9\u4e00-\u9fa5]+\\]";
		pattern = Pattern.compile(start);
		matcher = pattern.matcher(result);
		while (matcher.find()){
			result = result.replaceAll(start, "");
		}
		result = noHTML(result);
		return result;

	}

	//替换HTML标记
	public static String noHTML(String Htmlstring)
	{

		if (Htmlstring.toLowerCase().contains("<p>　　http://"))
		{
			Htmlstring = Htmlstring.replace("  ", "　") + "</p>";
			Htmlstring = StringUtil.toTrim(Htmlstring.replace( "<p>　　http://(?<result>.*?)</p>", "").replace("</p></p>", ""));
		}


		//删除脚本
		Htmlstring = Htmlstring.replace("(?i)<script[^>]*?>.*?</script>", "");
		//删除HTML
		Htmlstring = Htmlstring.replace("(?i)<(.[^>]*)>", "");
		// Htmlstring = Regex.Replace(Htmlstring, @"([/r/n])[/s]+", "", RegexOptions.IgnoreCase);

		Htmlstring = Htmlstring.replace("(?i)([\r\n])[/s]+", "");

		Htmlstring = Htmlstring.replace("(?i)-->", "");
		Htmlstring = Htmlstring.replace("(?i)<!--.*", "");

		Htmlstring = Htmlstring.replace("(?i)&(quot|#34);", "\"");
		Htmlstring = Htmlstring.replace("(?i)&(amp|#38);", "&");
		Htmlstring = Htmlstring.replace("(?i)&(lt|#60);", "<");
		Htmlstring = Htmlstring.replace("(?i)&(gt|#62);", ">");
		Htmlstring = Htmlstring.replace("(?i)&(nbsp|#160);", " ");
		Htmlstring = Htmlstring.replace("(?i)&(iexcl|#161);", "/xa1");
		Htmlstring = Htmlstring.replace("(?i)&(cent|#162);", "/xa2");
		Htmlstring = Htmlstring.replace("(?i)&(pound|#163);", "/xa3");
		Htmlstring = Htmlstring.replace("(?i)&(copy|#169);", "/xa9");
		Htmlstring = Htmlstring.replace("(?i)&#(/d+);", "");
		Htmlstring = Htmlstring.replace("(?i)<img[^>]*>;", "");
		// Htmlstring.Replace("<", "");
		//  Htmlstring.Replace(">", "");
		//  Htmlstring.Replace("/r/n", "");
		//Htmlstring = HttpContext.Current.Server.HtmlEncode(Htmlstring).Trim();
		return Htmlstring;
	}

	@Test
	public void test()throws Exception{
		List<String> list=getConversations("#迪奥品牌大使赵丽颖# #赵丽颖1016生日快乐# #赵丽颖盛明兰# #赵丽颖林浅# #赵丽颖密战# #女儿国国王赵丽颖# #赵丽颖迪奥巴黎行# #埋头数据，专注控评# #清辞丽句，颖人入胜# #萌粉姐姐# ＠赵丽颖[哆啦A梦笑]");
		System.out.println(JSON.toJSONString(list));
	}

}
