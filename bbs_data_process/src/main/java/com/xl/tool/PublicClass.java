package com.xl.tool;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.*;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WebCommonData;
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

	/**
	 * 符号或数字占全文百分比限定
	 * @return
	 * @Author: lww
	 * @Description:
	 * @Date: 14:42 2017/12/11
	 * @param null
	 */
	public static double NumPercentage = 0.4;

	public static Map<String,List<RKeywordModelBean>> dictionary = new HashMap<>();

	/**
	 * 根据一级媒体类型过滤词典
	 * @return
	 * @Author: lww
	 * @Description:
	 * @Date: 13:56 2017/11/18
	 * @param null
	 */
	public static Map<String,List<FKeywordModelBean>> mediaTypeDictionary = new HashMap<>();

	/// <summary>
	/// 替换指定属性串(2015.12月版本，扩展出来适用于全部字段属性需要做替换)
	/// </summary>
	/// <param name="str">属性值</param>
	/// <param name="o">属性名称</param> title 标题,contenttext 正文,navigator导航,source 来源转载(出处),author 作者
	/// <returns></returns>
	////
	public static String replaceO(String str, String o)
	{
		RKeywordModelBean model;
		if (dictionary != null && dictionary.containsKey(o))
		{
			for (int i = 0; i < dictionary.get(o).size(); i++)
			{
				model = dictionary.get(o).get(i);
				str = str.replace(StringUtil.toTrim(model.getOldKeyword()), StringUtil.toTrim(model.getNewKeyword()));
			}
		}
		return str;

	}

	/// <summary>
	/// 过滤html标签
	/// </summary>
	/// <param name="strHtml">html的内容</param>
	/// <returns></returns>
	public static String StripHTML(String stringToStrip){

		//文章只保留10000长度
		if(stringToStrip!=null&&stringToStrip.length()>10000){
			stringToStrip=stringToStrip.substring(0,10000);
		}
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

	/// <summary>
	/// 是否是过滤的文章
	/// </summary>
	/// <param name="filterRuleList"></param>
	/// <param name="content"></param>
	/// <returns></returns>
	public static String[] IsFilterArticle(List<FKeywordModelBean> filterRuleList, String title, String content,String url)
	{
		String []results = new String[]{"0",""};
		if (filterRuleList != null && filterRuleList.size() > 0)
		{
			for (FKeywordModelBean rule : filterRuleList)
			{
				String text = "";
				switch (rule.getkType())
				{
					case 1://标题
						text = title;
						break;
					case 2://正文
						text = content;
						break;
					case 3://标题或正文
						text = title + "  " + content;
						break;
					case 4://屏蔽信息源
						text = url;
						break;
				}
				if (text.toLowerCase().contains(StringUtil.toTrim(rule.getKeyWord().toLowerCase()))){
					results[0] = "1";
					results[1] = StringUtil.toTrim(rule.getKeyWord());
					break;
				}
			}
		}
		return results;
	}
	/// <summary>
	/// 替换标题非法字符
	/// </summary>
	/// <param name="title"></param>
	/// <returns></returns>
	public static String ReplaceSpecialTitle(String title){
		//TODO 原值为“\((?>[^()]+|\([^()\s]+\s(?<DEPTH>)|\)\s(?<-DEPTH>))*(?(DEPTH)(?!))\)” 可能有错误！！！
//		String p = "\\((?>[^()]+|\\([^()\\s]+\\s(?<DEPTH>)|\\)\\s(?<DEPTH>))*(?<DEPTH>(?!))\\)";
//
//		title = StringUtil.toTrim(title.replaceAll(p, ""));
		if (title.toUpperCase().contains("<SCRIPT"))
		{
			title = title.replaceAll("<SCRIPT.*?>(</SCRIPT>)?", "");
		}
		return title;
	}

	/// <summary>
	/// 替换正文中特殊字符
	/// </summary>
	/// <param name="content"></param>
	/// <returns></returns>
	public static String ReplaceContentText(String content)
	{
		String strtemp = "<!--_img_-->";
		// string strtemp1 = "<!--_table_-->";
		content = content.replace(strtemp, "。");//.Replace(strtemp1, "。");
		for (char c:content.toCharArray()){
			if ((int)c < 28){
				//content = content.Replace(c.ToString(), "。");//将特殊字符转换。
				content = content.replace(c+"", "");//将特殊字符转换成空
			}
		}
		if (content.toUpperCase().contains("<SCRIPT")){
			content = content.replaceAll("<SCRIPT.*?>(</SCRIPT>)?", "");
		}
		if (content.toUpperCase().contains("<STYLE")){
			content = content.replaceAll("<STYLE.*?>(</STYLE>)?", "");
		}

		//使用新版本替换
		content =  PublicClass.replaceO(content, "contenttext");


		content = StringEscapeUtils.unescapeHtml(content);;//替换HTML转移符&amp;&nbsp;等等
		content = content.replaceAll("\\s{1,}", " ");//去掉多余的空格
		content = content.replaceAll("<p> </p>{1,}", "");//去掉多余的<p> </p>

		content = content.replace("<p> ", "<p>  ");//正文段首空两格

		return content;
	}

	/// <summary>
	/// 替换正文中的图片
	/// </summary>
	/// <param name="content">正文</param>
	/// <param name="drpic">图片列表</param>
	/// <returns></returns>
	public static String replacecontenttext(String content, List<Docpicbean> drpic){
		String result = "";
		String strtemp = "<!--_img_-->";

		if (content.indexOf(strtemp) != -1){
			if (drpic != null && drpic.size() > 0)
			{
				drpic.sort((obj1,obj2)->Integer.compare(obj1.getPid(),obj2.getPid()));
				for (Docpicbean row : drpic)//遍历图片对应列表
				{
					if (content.indexOf(strtemp) == -1){
						break;
					}
					result += content.substring(0, content.indexOf(strtemp) + strtemp.length());

					result = result.replace(strtemp, "<img src='" + StringUtil.toTrim(row.getUrl()) + "' />");
					content = content.substring(content.indexOf(strtemp) + strtemp.length());
				}
			}
		}
		result += content;

		result = result.replace("　"," ");
		result = result.replaceAll("\r\n", "</p><p>");
		result = result.replaceAll("\n", "</p><p>");
		result = result.replaceAll("<p>  ", "<p>");//先去掉段落首空格

		result = "<p>" + result + "</p>";
		result = result.replaceAll("<p>", "<p>  ");//段落首加上两个空格

		result = result.replaceAll("(</p><p>  ){1,}", "</p><p> ");//去掉多余的</p><p>　
		result = result.replaceAll("。{1,}", "。");//去掉多余的。
		result = result.replaceAll("</p><p>  。", "</p><p> ");
		result = result.replaceAll("\\s{1,}", " ");//去掉多余的空格
		result = result.replaceAll("<p> </p>{1,}", "");//去掉多余的<p> </p>

		result = StringEscapeUtils.unescapeHtml(result);//替换HTML转移符&amp;&nbsp;等等
		result = result.replaceAll(StringEscapeUtils.unescapeHtml("&nbsp;"), " ");//去掉多余的空格
		result = result.replaceAll("\\s{1,}", " ");//去掉多余的空格
		result = result.replaceAll("<p> </p>{1,}", "");//去掉多余的<p> </p>
		result = result.replace("<p> ", "<p>  ");//正文段首空两格

		return result;
	}

	public static void main(String[] args) {
		String content = "<p> <img src='http://mmbiz.qpic.cn/mmbiz/GfkicuLPG6dzkmUYxz7Rh5aYh8GfwGTJibvP862nViatQcZDEqe8VVgO6wWrfp7ec49Ewia6xbm2P3XUsQgppfZtiaw/0' />“如果说一个品牌是一颗大树，那么用户就是让这棵树生长得繁茂的叶子。因为有了叶的生机勃勃，才有树的欣欣向荣；也因为大树的四季常青，才有叶子的青翠欲滴。品牌与用户，本应是相生相依，相辅相成的关系，因为彼此信任彼此依赖，所以彼此都有更好的未来。为了真正架好客户与品牌之间的桥梁，把关爱带给每一位广汽吉奥的客户，广汽吉奥星朗车友会应运而生。<img src='http://mmbiz.qpic.cn/mmbiz/GfkicuLPG6dynzYqtO5qQ9OxLYmlD1LPTJIPh20DAS3bfCdIS4zR0YBwccZyzCuRV0EDjpHcSL5l4XLDUjaqNAA/0' />今日上午，广汽吉奥汽车销售有限公司副总经理李葆华、广汽吉奥汽车有限公司品牌总监兼销售公司副总经理曾业辉、广汽吉奥汽车销售有限公司副总经理兼售后服务部部长徐正军、广汽吉奥汽车有限公司本部工厂品质保证部部长高贺堂等领导与广汽吉奥星朗车友会首批车主齐聚一堂，共同庆祝星朗车友会的诞生。广汽吉奥汽车销售有限公司综合管理部部长王星主持仪式。<img src='http://mmbiz.qpic.cn/mmbiz/GfkicuLPG6dynzYqtO5qQ9OxLYmlD1LPTonZiceTaiaAKRBPZdwXW1gC8eH6T2p2mwDa0ptrfMkThqSr1kbbUT5Qw/0' /></p><p> <img src='http://mmbiz.qpic.cn/mmbiz/GfkicuLPG6dynzYqtO5qQ9OxLYmlD1LPTMSgf3Ucw5QT42n64qAt2pQb2I0M8gPMjicba5TCxkoWgzeAdUnTYTVA/0' /></p><p> <img src='http://mmbiz.qpic.cn/mmbiz/GfkicuLPG6dynzYqtO5qQ9OxLYmlD1LPTAW34LpwOibz4HOv1Ric7EYaljxxlcYgp5mLDClQIfpGFkQScnicvUXiaBQ/0' /></p><p> <img src='http://mmbiz.qpic.cn/mmbiz/GfkicuLPG6dynzYqtO5qQ9OxLYmlD1LPTicgv3TWMxYjyjTrbohiaT23FCjia7hTibGCKGWF9u6wic0R9LIRNFDYJ0zA/0' /></p><p> <img src='http://mmbiz.qpic.cn/mmbiz/GfkicuLPG6dynzYqtO5qQ9OxLYmlD1LPT2zyz323ALDOlYeUWJYcM0DDricpzV243vA2v15eFnvZfQLRcV4Mm4oQ/0' /></p><p> 广汽吉奥汽车销售有限公司综合管理部部长王星主持仪式</p><p> <img src='http://mmbiz.qpic.cn/mmbiz/GfkicuLPG6dynzYqtO5qQ9OxLYmlD1LPTJkPQiaJ0xHTZTdqhWh7iavnbkSoT5egMKdibicm5eQpxlnrIiaXj3IHcD4A/0' />广汽吉奥品牌总监兼销售公司副总经理曾业辉为车友会授旗广汽吉奥星朗车友会会长由星朗车主张锦忠先生担任。车友会正是在张先生及其他老车主们的积极建言与筹措中备受广汽吉奥领导关注并最终成立的，感谢每一位星朗车主，你们是广汽吉奥前进道路上源源不断的动力。<img src='http://mmbiz.qpic.cn/mmbiz/GfkicuLPG6dynzYqtO5qQ9OxLYmlD1LPT3Qh7yj2tlL0g6K9VIaTbQ9ib1VJPcGpU5SXesp3NIUvqKNpgXyUJiayw/0' />车友会会员参观广汽吉奥工厂流水线以此次星朗车友会成立为契机，广汽吉奥特别策划举办了首批星朗用户广汽吉奥之行活动，并开展了“油我做主 谁与争锋”节油大赛。<img src='http://mmbiz.qpic.cn/mmbiz/GfkicuLPG6dynzYqtO5qQ9OxLYmlD1LPTITKeRX55jw3OWDVwIIIoFeBydfHt0uMve57Vv45gFa8CM7w7wyUVpg/0' /></p><p> <img src='http://mmbiz.qpic.cn/mmbiz/GfkicuLPG6dynzYqtO5qQ9OxLYmlD1LPTk755mNA3oXqV29ibibDRicMb74IyDrHWqAod9If0VNeFKpYkt6crx7mlg/0' />一直以来，广汽吉奥致力于打造最用心的汽车品牌，为消费者带去真诚用心的服务。在此基础上，2014年推向市场的星朗2014款、广汽GX6、GP150等均获得市场积极反馈，消费者好评如潮。未来，在品牌与车主的共同努力之下，我们相信广汽吉奥大家庭必将迎来茁壮成长。↑车友会目前正火热招募会员中，如果您也是星朗车主，并希望和其他车主一起在这个大家庭里相互交流用车感受、分享用车心得、参加线下活动、体验大家庭的温馨快乐，那么就加入我们吧！广汽吉奥星朗车友会QQ群号：224631178，就等你了！<img src='http://mmbiz.qpic.cn/mmbiz/GfkicuLPG6dzkmUYxz7Rh5aYh8GfwGTJibd0JhqcmqGCMbictXGpruZftoWraMLm4puaiaDZqCmict8iaaavXBt9dZOA/0' /></p>";
//		content = StringEscapeUtils.unescapeHtml(content);//替换HTML转移符&amp;&nbsp;等等
//		content = content.replaceAll("\\s{1,}", " ");//去掉多余的空格
		System.out.println(getImgSrc(content).size());
	}

	/// <summary>
	/// 替换正文中的表格
	/// </summary>
	/// <param name="content">正文</param>
	/// <param name="drtable">表格</param>
	/// <returns></returns>
	public static String replacecontenttexttable(String content, List<Doctablebean> drtable)
	{
		String result = "";

		String strtemp_begin = "<!--_table_-->";
		String strtemp_end = "<!--/_table_-->";
		if (content.indexOf(strtemp_begin) != -1 && content.indexOf(strtemp_end) != -1)
		{
			if (drtable != null && drtable.size() > 0)
			{
				drtable.sort((obj1,obj2)->Integer.compare(obj1.getTid(),obj2.getTid()));
				for (Doctablebean row : drtable){//遍历表格对应列表
					int _b = content.indexOf(strtemp_begin);
					int _e = content.indexOf(strtemp_end);
					if (_b == -1 || _e == -1) {
						break;
					}

					_e +=strtemp_end.length();
					result += content.substring(0, _b);
					result += StringUtil.toTrim(row.getCtn());
					content = content.substring(_e);
				}
			}
		}
		result += content;
		return result;

	}

	//替换HTML标记
	public static String noHTML(String Htmlstring)
	{

		//文章只保留10000长度
		if(Htmlstring!=null&&Htmlstring.length()>10000){
			Htmlstring=Htmlstring.substring(0,10000);
		}

		if (Htmlstring.toLowerCase().contains("<p>　　http://"))
		{
			Htmlstring = Htmlstring.replaceAll("  ", "　") + "</p>";
			Htmlstring = StringUtil.toTrim(Htmlstring.replaceAll( "<p>　　http://(?<result>.*?)</p>", "").replace("</p></p>", ""));
		}


		//删除脚本
		Htmlstring = Htmlstring.replaceAll("(?i)<script[^>]*?>.*?</script>", "");
		//删除HTML
		Htmlstring = Htmlstring.replaceAll("(?i)<(.[^>]*)>", "");
		// Htmlstring = Regex.Replace(Htmlstring, @"([/r/n])[/s]+", "", RegexOptions.IgnoreCase);

		Htmlstring = Htmlstring.replaceAll("(?i)([\r\n])[/s]+", "");

		Htmlstring = Htmlstring.replaceAll("(?i)-->", "");
		Htmlstring = Htmlstring.replaceAll("(?i)<!--.*", "");

		Htmlstring = Htmlstring.replaceAll("(?i)&(quot|#34);", "\"");
		Htmlstring = Htmlstring.replaceAll("(?i)&(amp|#38);", "&");
		Htmlstring = Htmlstring.replaceAll("(?i)&(lt|#60);", "<");
		Htmlstring = Htmlstring.replaceAll("(?i)&(gt|#62);", ">");
		Htmlstring = Htmlstring.replaceAll("(?i)&(nbsp|#160);", " ");
		Htmlstring = Htmlstring.replaceAll("(?i)&(iexcl|#161);", "/xa1");
		Htmlstring = Htmlstring.replaceAll("(?i)&(cent|#162);", "/xa2");
		Htmlstring = Htmlstring.replaceAll("(?i)&(pound|#163);", "/xa3");
		Htmlstring = Htmlstring.replaceAll("(?i)&(copy|#169);", "/xa9");
		Htmlstring = Htmlstring.replaceAll("(?i)&#(/d+);", "");
		Htmlstring = Htmlstring.replaceAll("(?i)<img[^>]*>;", "");
		// Htmlstring.Replace("<", "");
		//  Htmlstring.Replace(">", "");
		//  Htmlstring.Replace("/r/n", "");
		//Htmlstring = HttpContext.Current.Server.HtmlEncode(Htmlstring).Trim();
		return Htmlstring;
	}


	/// <summary>
	/// 通过媒体库ＡＰＩ接口获取媒体属性
	/// </summary>
	/// <param name="adbt">数据媒体</param>
	/// <param name="mediatype">媒体类型[编码]</param>
	/// <param name="medianame">媒体名称【微信是中文微信昵称】</param>
	/// <param name="site">媒体站点【微信是微信号】</param>
	/// <param name="domain">媒体域名【微信是微信唯一编码GH 或WX开头】</param>
	/// <param name="idx">文章位置[微信使用]</param>
	/// <returns></returns>
	public static MediaResultBean GetMediaType(String mediaType,String mediaName,String site, String domain, String idx)
	{

		//GetByDomain?aurl=https://www.baidu.com&mediaType=web&mediaName=
		String resultJson;
		MediaResultBean result = new MediaResultBean();

		String mediaUrl = WebCommonData.GET_MEDIA_API;
		if("web".equals(StringUtil.toTrim(mediaType))){
			mediaUrl = mediaUrl.replace("MEDIATYPE_VALUE",mediaType).replace("SITE_VALUE",site).replace("DOMAIN_VALUE",domain).replace("MEDIANAME_VALUE","");
		}else if("app".equals(StringUtil.toTrim(mediaType))||"press".equals(StringUtil.toTrim(mediaType))){
			mediaUrl = mediaUrl.replace("MEDIATYPE_VALUE",mediaType).replace("MEDIANAME_VALUE",mediaName).replace("SITE_VALUE","").replace("DOMAIN_VALUE","");
		}else if("bbs".equals(StringUtil.toTrim(mediaType))){
			mediaUrl = mediaUrl.replace("MEDIATYPE_VALUE","web").replace("SITE_VALUE",site).replace("DOMAIN_VALUE",domain).replace("MEDIANAME_VALUE","");
		}else{
			return null;
		}
		MediaNewBean mediaNewBean;
		try{
			resultJson = HttpClientPoolUtil.execute(mediaUrl);
			if(!"".equals(StringUtil.toTrim(resultJson))&&!"null".equals(StringUtil.toTrim(resultJson))){
				mediaNewBean = JSONObject.parseObject(resultJson,MediaNewBean.class);
				if(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaNameCN()))&&!"".equals(StringUtil.toTrim(mediaNewBean.getMediaID()))) {
					if ("web".equals(StringUtil.toTrim(mediaType)) || "bbs".equals(StringUtil.toTrim(mediaType))) {
						result.getM().setDomain(domain);
						result.getM().setSite(site);
						result.getM().setMediaNameCn(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaNameCN())) ? mediaNewBean.getMediaNameCN() : domain);
						result.getM().setMediaId(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaID())) ? mediaNewBean.getMediaID() : "");
						result.setFocusAdvPrice(!"".equals(StringUtil.toTrim(mediaNewBean.getFocusAdvPrice())) ? Float.parseFloat(mediaNewBean.getFocusAdvPrice()) : 0f);
						result.setLinkAdvPrice(!"".equals(StringUtil.toTrim(mediaNewBean.getLinkAdvPrice())) ? Float.parseFloat(mediaNewBean.getLinkAdvPrice()) : 0f);
						result.setProgramaAdvPrice(!"".equals(StringUtil.toTrim(mediaNewBean.getProgramaAdvPrice())) ? Float.parseFloat(mediaNewBean.getProgramaAdvPrice()) : 0f);
						result.setSubjectAdvPrice(!"".equals(StringUtil.toTrim(mediaNewBean.getSubjectAdvPrice())) ? Float.parseFloat(mediaNewBean.getSubjectAdvPrice()) : 0f);

						//2018-03-13 媒体地域信息取父级
						result.getM().getMediaArea().setCity(!"".equals(StringUtil.toTrim(mediaNewBean.getCity())) ? mediaNewBean.getCity() : "");
						result.getM().getMediaArea().setProvince(!"".equals(StringUtil.toTrim(mediaNewBean.getProvince())) ? mediaNewBean.getProvince() : "");
						result.getM().getMediaArea().setCityId(!"".equals(StringUtil.toTrim(mediaNewBean.getCityID())) ? mediaNewBean.getCityID() : "");
						result.getM().getMediaArea().setCountry(!"".equals(StringUtil.toTrim(mediaNewBean.getCountry())) ? mediaNewBean.getCountry() : "");

						if (mediaNewBean.getChildren() != null) {
							result.getM().setMediaNameEn(!"".equals(StringUtil.toTrim(mediaNewBean.getChildren().getMediaNameEN())) ? mediaNewBean.getChildren().getMediaNameEN() : site);
							result.getM().setMediaType(!"".equals(StringUtil.toTrim(mediaNewBean.getChildren().getMediaType())) ? mediaNewBean.getChildren().getMediaType() : "");

							if (mediaNewBean != null && mediaNewBean.getChildren().getMediaCategory() != null && mediaNewBean.getChildren().getMediaCategory().length() > 0) {
								result.getM().setMediaCategory(Arrays.asList(StringUtil.toTrim(mediaNewBean.getChildren().getMediaCategory()).split(",")));
							}

							//2018-03-13 媒体地域信息取父级
							/*result.getM().getMediaArea().setCity(!"".equals(StringUtil.toTrim(mediaNewBean.getChildren().getCity())) ? mediaNewBean.getChildren().getCity() : (
									!"".equals(StringUtil.toTrim(mediaNewBean.getCity())) ? mediaNewBean.getCity() : ""
							));
							result.getM().getMediaArea().setProvince(!"".equals(StringUtil.toTrim(mediaNewBean.getChildren().getProvince()))?mediaNewBean.getChildren().getProvince() : (
									!"".equals(StringUtil.toTrim(mediaNewBean.getProvince()))?mediaNewBean.getProvince() : ""
							));
							result.getM().getMediaArea().setCityId(!"".equals(StringUtil.toTrim(mediaNewBean.getChildren().getCityID()))?mediaNewBean.getChildren().getCityID():(
									!"".equals(StringUtil.toTrim(mediaNewBean.getCityID()))?mediaNewBean.getCityID():""
							));*/
							result.getA().setPv(mediaNewBean.getChildren().getPv()); //发行量
							result.getA().setUv(mediaNewBean.getChildren().getUv());
							result.getA().setAdvPrice(mediaNewBean.getChildren().getAdvPrice());//广告价值
							result.getA().setAlexaRank(mediaNewBean.getChildren().getAlexaRank());
							result.setLayout(!"".equals(StringUtil.toTrim(mediaNewBean.getChildren().getMediaNameEN())) ? mediaNewBean.getChildren().getMediaNameCN() : "");//频道
						} else {
							result.getM().setMediaNameEn(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaNameEN())) ? mediaNewBean.getMediaNameEN() : site);
							result.getM().setMediaType(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaType())) ? mediaNewBean.getMediaType() : "");

							if (mediaNewBean != null && mediaNewBean.getMediaCategory() != null && mediaNewBean.getMediaCategory().length() > 0) {
								result.getM().setMediaCategory(Arrays.asList(removeEmpty(StringUtil.toTrim(mediaNewBean.getMediaCategory()).split(","))));
							}

							result.getA().setPv(mediaNewBean.getPv());//发行量
							result.getA().setUv(mediaNewBean.getUv());
							result.getA().setAdvPrice(mediaNewBean.getAdvPrice());//广告价值
							result.getA().setAlexaRank(mediaNewBean.getAlexaRank());
						}
					} else if ("app".equals(StringUtil.toTrim(mediaType))) {
						result.getM().setDomain("");
						result.getM().setSite("");
						result.getM().setMediaNameCn(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaNameCN())) ? mediaNewBean.getMediaNameCN() : domain);
						result.getM().setMediaNameEn(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaNameCN())) ? mediaNewBean.getMediaNameCN() : domain);
						result.getM().setMediaId(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaID())) ? mediaNewBean.getMediaID() : "");
						result.getM().setMediaType(mediaNewBean.getMediaType());

						if (mediaNewBean != null && mediaNewBean.getMediaCategory() != null && mediaNewBean.getMediaCategory().length() > 0) {
							result.getM().setMediaCategory(Arrays.asList(removeEmpty(StringUtil.toTrim(mediaNewBean.getMediaCategory()).split(","))));
						}

						result.getM().getMediaArea().setCity(!"".equals(StringUtil.toTrim(mediaNewBean.getCity())) ? mediaNewBean.getCity() : "");
						result.getM().getMediaArea().setProvince(!"".equals(StringUtil.toTrim(mediaNewBean.getProvince())) ? mediaNewBean.getProvince() : "");
						result.getM().getMediaArea().setCityId(!"".equals(StringUtil.toTrim(mediaNewBean.getCityID())) ? mediaNewBean.getCityID() : "");
						result.getM().getMediaArea().setCountry(!"".equals(StringUtil.toTrim(mediaNewBean.getCountry())) ? mediaNewBean.getCountry() : "");
						result.getA().setPv(mediaNewBean.getPv());//发行量
						result.getA().setUv(mediaNewBean.getUv());
						result.getA().setAdvPrice(mediaNewBean.getAdvPrice());//广告价值
						result.getA().setAlexaRank(mediaNewBean.getAlexaRank());
					} else if ("press".equals(StringUtil.toTrim(mediaType))) {
						mediaNewBean = JSONObject.parseObject(resultJson, MediaNewBean.class);

						result.getM().setDomain(domain);
						result.getM().setSite(site);
						result.getM().setMediaNameCn(StringUtil.toTrim(mediaNewBean.getMediaNameCN()));
						result.getM().setMediaId(StringUtil.toTrim(mediaNewBean.getMediaID()));
						result.getM().setMediaNameEn(StringUtil.toString(mediaNewBean.getMediaNameEN()));
						result.getM().setMediaType(StringUtil.toString(mediaNewBean.getMediaType()));

						if (!"".equals(StringUtil.toTrim(mediaNewBean.getMediaCategory()))) {
							result.getM().setMediaCategory(Arrays.asList(removeEmpty(mediaNewBean.getMediaCategory().split(","))));
						}

						result.getM().getMediaArea().setCity(!"".equals(StringUtil.toTrim(mediaNewBean.getCity())) ? mediaNewBean.getCity() : "");
						result.getM().getMediaArea().setProvince(!"".equals(StringUtil.toTrim(mediaNewBean.getProvince())) ? mediaNewBean.getProvince() : "");
						result.getM().getMediaArea().setCityId(!"".equals(StringUtil.toTrim(mediaNewBean.getCityID())) ? mediaNewBean.getCityID() : "");
						result.getM().getMediaArea().setCountry(!"".equals(StringUtil.toTrim(mediaNewBean.getCountry())) ? mediaNewBean.getCountry() : "");

						result.getA().setPv(mediaNewBean.getPv()); //发行量
						result.getA().setUv(mediaNewBean.getUv());
						result.getA().setAdvPrice(mediaNewBean.getAdvPrice());//广告价值
						result.getA().setAlexaRank(mediaNewBean.getAlexaRank());
					}
				}else{
					return null;
				}
			}else{
				return null;
			}
		}catch (Exception e){
			result = null;
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"调用媒体库", mediaUrl,"调用媒体库出现异常！",e);
		}

		return result;
	}

	public static String getImgList(String ImgString){
		String string = "";
		if (ImgString != ""){
//          String reg = "src=.+?(?=(wx_fmt=|wxfrom=|\\?|\"\"|'))";
			String reg="src=\"?(.*?)(\"|>|\\s+\\/>)";
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(ImgString);
			while (m.find()) {
				if(m.group().indexOf("http")!=-1){//说明图片src是正常的图片
//              matchVale = StringHelper.toTrim(m.group()).replace("src='", "");
					if(m.group(1).indexOf("'") != -1){
						string = m.group(1).replaceAll(" ", "%20");
						string = m.group(1).replaceAll("'", "");
					}else {
						string = m.group(1);
					}
				}else
					string = "";
			}
		}
		return string;
	}

	public static  List<String> getImgSrc(String htmlStr) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
		List<String> pics = new ArrayList<String>();
//       String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			// Matcher m =
			// Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			while (m.find()) {
				pics.add(m.group(1));
			}
		}
		return pics;
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

	/// <summary>
	/// 得到数字所占的比例
	/// </summary>
	/// <param name="text"></param>
	/// <returns></returns>
	public static double GetNumPercentage(String text) {
		String regEx = "[0-9]{1}";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(StringUtil.toTrim(text));
		double temp = m.groupCount() * 1.00 / StringUtil.toTrim(text).length();
		return temp;
	}

	/// <summary>
	/// 得到百分号所占的比例
	/// </summary>
	/// <param name="text"></param>
	/// <returns></returns>
	public static double GetPerPercentage(String text){
		String regEx = "%";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(StringUtil.toTrim(text));
		double temp = m.groupCount() * 1.00 / StringUtil.toTrim(text).length();
		return temp;
	}

	/**
	 * 判断是否乱码
	 * @return boolean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:35 2017/12/11
	 * @param text
	 */
	public static boolean isLuanMa(String text){

		boolean b = false;
		int count = 0;
		for (char c : StringUtil.toTrim(text).toCharArray()) {
			if ((int)c < 32)
			{
				count++;
			}
			else if ((int)c > 130 && (int)c < 500)
			{
				count++;
			}
		}
		if (count * 1.00 / StringUtil.toTrim(text).length() > 0.5){
			b = true;
		}
		return b;
	}

	/**
	 * 重复去除开头字符
	 * @return
	 * @Author: lww
	 * @Description:
	 * @Date: 17:13 2017/12/12
	 */
	public static String replaceFirst(String str,String firstWords){
		if(!"".equals(StringUtil.toTrim(str))){
			while (str.startsWith(firstWords)){
				str = str.replaceFirst(firstWords,"");
			}
		}
		return str;
	}

	/**
	 * 移除数组中的空值
	 * @return
	 * @Author: lww
	 * @Description:
	 * @Date: 17:54 2017/12/12
	 */
	public static String[] removeEmpty(String[] arrays){
		String result = "";
		if(arrays==null){
			return new String[]{};
		}else{
			for(String str:arrays){
				if(!"".equals(StringUtil.toTrim(str))){
					result += str+",";
				}
			}
			return result.split(",");
		}
	}

}
