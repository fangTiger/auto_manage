package com.xl.tool;

import com.alibaba.fastjson.JSON;
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

	/*public static void main(String[] args) {
		String content = "<p> <p> <p> &nbsp;&nbsp;&nbsp; 本报讯近期，为有效检查各</p><p> 项工作的落实情况，山西省运城市</p><p> 交通运输执法局对基层单位进行</p><p> 了内部审计。此次内部审计围绕</p><p> 市局各项工作目标，重点关注预算</p><p> 执行及财务收支、市局重点工作任</p><p> 务的落实、内部控制制度建立及执</p><p> 行等。通过审计，促使各单位切实</p><p> 履行《行政事业单位内部控制规范</p><p> （试行）&lsquo;》，主动适应预算管理要求，</p><p> 夯实财务基础管理工作，特别是各</p><p> 单位建立了审计整改台账，促进财</p><p> 务内控制度、资产管理制度等的有</p><p> 效执行，强化了各单位负责人项目</p><p> 经费绩效意识和整改落实责任意</p><p> 识，有效发挥了审计监督对全局工</p><p> 作的促进作用。</p><p> &nbsp;&nbsp;&nbsp; （景永忠张振龙）</p></p></p>";
//		content = StringEscapeUtils.unescapeHtml(content);//替换HTML转移符&amp;&nbsp;等等
//		content = content.replaceAll("\\s{1,}", " ");//去掉多余的空格
		System.out.println(replacecontenttext(content,null));
	}*/

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

	/*public static void main(String[] args) {
		String content = "<p> <img src='http://mmbiz.qpic.cn/mmbiz/qweAmHoFdSEBiaOXlWTDlytniaORu8wu4icWILe1yckoNWC8L7wmbLwYllmY8MZh7feibpzsmuzI2IKC4WKsIh6I0Q/640' /></p><p> · 广汽菲亚特克莱斯勒汽车销售有限公司宣布成立，新团队组建完成并进入试运行阶段，这标志着双方合资合作的进一步加深;</p><p> · 广汽菲亚特克莱斯勒汽车有限公司将在2015年国产Jeep自由光车型，在2016年底前，还将有两款全新Jeep车型实现国产;</p><p> · 未来Jeep品牌在华产品线将覆盖从小型SUV到全尺寸SUV各个主要细分市场，形成业内最丰富的SUV产品线;</p><p> · 郑杰将担任广汽菲亚特克莱斯勒汽车销售有限公司总经理;陈道宏将担任广汽菲亚特克莱斯勒汽车销售有限公司执行副总经理。</p><p> · 现有销售网络将全部纳入到新的销售公司网络中，未来经销商的发展将开启多业态化、模块化发展模式。</p><p> · 广汽菲克在华工厂将执行最严格的世界级制造标准(WCM)，以为中国消费者提供最高品质标准的汽车产品。预计到2020年末，长沙、广州两地产能整体规划达到70万台。</p><p> <img src='http://mmbiz.qpic.cn/mmbiz/qweAmHoFdSEBiaOXlWTDlytniaORu8wu4icFVRosvicp5eiaZeS9TbpYSZWaWeAaHmxDDqq1A2Va3YtQMrfWAxkXGAA/640' /></p><p> 广汽菲亚特克莱斯勒汽车有限公司(以下简称“广汽菲克”)今天宣布，成立全新的广汽菲亚特克莱斯勒汽车销售有限公司(以下简称“广汽菲克销售公司”)，办公地点设于上海，未来将负责广汽菲克在中国市场所有车型的销售管理、市场推广、产品规划和售后服务。郑杰将担任广汽菲亚特克莱斯勒汽车销售有限公司总经理;陈道宏将担任广汽菲亚特克莱斯勒汽车销售有限公司执行副总经理。Jeep自由光车型将在年内实现国产，同时，广汽菲克还将于2016年完成两款全新国产Jeep品牌SUV车型的生产。广汽菲克广州工厂将于2016年上半年投产。预计到2020年末，长沙、广州两地产能整体规划达到70万台，届时广汽菲克将拥有10款具有市场竞争力的车型产品。</p><p> <img src='http://mmbiz.qpic.cn/mmbiz/qweAmHoFdSEBiaOXlWTDlytniaORu8wu4ic2OhFuK2IaIx56vrwqX5koUCQjZC73KqmGcVF9ro7klfwicO46nGIgdQ/640' /></p><p> 此次声明的发布，是广州汽车集团股份有限公司(以下简称“广汽集团”)和菲亚特克莱斯勒集团(以下简称“菲克集团”)合作的重要里程碑，标志着双方进一步巩固战略合作伙伴关系，扩展Jeep品牌在中国的产品线，共同拓展中国汽车市场，实现双赢。</p><p> 开放融合，广汽菲克销售公司开启中欧美合资新模式入标题<img src='http://mmbiz.qpic.cn/mmbiz/qweAmHoFdSEBiaOXlWTDlytniaORu8wu4icme6ss1R6BkYdeGoNdLtPHQStxCicibiahr91Y5LqL09eDbZibcVjS0v6gg/640' /></p><p> 据介绍，广汽和菲克的此次合作，在合资管理模式上进行了突破性的尝试：启用职业经理人团队，以“把最合适的人才放在合适的岗位上”为目标，建立“业绩导向”的绩效考评体系;采用分级授权管理模式，打破已有的合资企业中副总以下主要管理岗位一对一派驻、双签体制;改变现行人员派遣模式，将副总以下高管人员的管理权下放给合资销售公司。</p><p> <img src='http://mmbiz.qpic.cn/mmbiz/qweAmHoFdSEBiaOXlWTDlytniaORu8wu4icZjH2qPZ5Euvia2T7B0kJkwd1j55HhlhjJECbqsBWL5XZDrf0XiarOwGg/640' /></p><p> 对于全新的广汽菲克销售有限公司，广汽集团曾庆洪总经理表示：销售公司的成立，标志着广汽菲克的发展进入全新的里程。广汽集团将继续深化欧美系合作，为中国广大消费者提供更高品质的产品和满意的服务。<img src='http://mmbiz.qpic.cn/mmbiz/qweAmHoFdSEBiaOXlWTDlytniaORu8wu4icbRErDpsSe7oUaPfnbNicCrqKWHMqvHUX1djRZKgIutIicPJfvic7qE3pg/640' /></p><p> 菲克集团Jeep品牌总裁兼CEO Mike Manley 表示：广汽集团与菲克集团所创立的这一创新架构将确保合资企业能够充分借助双方母公司的实力，并使我们对中国消费者的需求做出更积极快速的响应。</p><p> Jeep国产，广汽菲克打响进军SUV市场第一枪<img src='http://mmbiz.qpic.cn/mmbiz/qweAmHoFdSEBiaOXlWTDlytniaORu8wu4icxs01mkrqoE07cTvCSkR9jZf5cNzhXdP1icVz7nDbmTJsqHv5Yp95gXg/640' /></p><p> 广汽菲克将于年内国产Jeep自由光车型，到2016年年底，将有包括自由光、自由侠在内的3款Jeep品牌新产品陆续投产，并与进口产品线形成合力，组成业内覆盖细分市场最丰富完整的SUV产品线。同时，克莱斯勒品牌未来将实现国产，与菲亚特品牌共同组成更丰富的轿车产品阵容。</p><p> “这是我们在品牌发展、业务拓展和在华伙伴关系巩固方面迈出的关键性一步，”菲克集团Jeep品牌总裁兼CEO Mike Manley表示。“Jeep品牌在中国的销量2014年增长了近50%，中国现在已发展成为Jeep除美国本土之外的最大市场。”<img src='http://mmbiz.qpic.cn/mmbiz/qweAmHoFdSEBiaOXlWTDlytniaORu8wu4icHotdL6icicLfXGjnFuXsuoCPBc2wJWX3sbic418Y2bE2dMpJYMPHbhhpg/640' /></p><p> “未来Jeep的国产将会提升我们在中国市场竞争中的优势地位，从而将Jeep最专业的性能和最具个性的品牌精神，传递给这个全球最大汽车市场中更多的消费者，”他补充说。</p><p> 大胆创新，优化拓展经销商网络</p><p> 全新广汽菲克销售公司成立后，经销商网络建设将进一步拓展。原两个销售网络(广汽-菲亚特网络和克莱斯勒进口车网络)中的优秀经销商，将成为全新广汽菲克销售公司统一销售网络中的中坚力量;并将有机会获得广汽菲克销售公司所有品牌车型的销售资格，从而打造全品牌、全产品线的销售体系，进一步提高渠道销售能力和服务水平。</p><p> 制造升级，携手打造高品质汽车<img src='http://mmbiz.qpic.cn/mmbiz/qweAmHoFdSEBiaOXlWTDlytniaORu8wu4icUHgIdDmnE2TjwndGgDvKuulsMMMBYnk6ibfysxsibmcYqORHicJFsRUWg/640' /></p><p> 未来Jeep、菲亚特和克莱斯勒国产车型的生产和制造，将执行菲克集团最严格的世界级制造标准(WCM)，与北美及欧洲的工厂完全一致。WCM是一套系统化降低损耗、提高生产率并改善品质与安全的方法体系，致力于为全世界及中国消费者打造最高品质的产品。</p><p> 今天，广汽菲克销售公司的启动运营，标志着菲克集团在中国市场又迈进了新的里程。同时也对广汽集团进一步拓展市场份额起到了重要作用，更意味着未来双方通过更紧密的合作，将为中国消费者带来更全面的产品及更优质的服务体验。</p><p> 本文转自 广汽集团 微信公众号</p>";

		System.out.println(noHTML(content));
	}*/

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
				if(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaNameCN()))&&!"".equals(StringUtil.toTrim(mediaNewBean.getMediaID()))){
					if("web".equals(StringUtil.toTrim(mediaType))||"bbs".equals(StringUtil.toTrim(mediaType))){
						result.getM().setDomain(domain);
						result.getM().setSite(site);
						result.getM().setMediaNameCn(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaNameCN()))?mediaNewBean.getMediaNameCN():domain);
						result.getM().setMediaId(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaID())) ? mediaNewBean.getMediaID() : "");
						result.setFocusAdvPrice(!"".equals(StringUtil.toTrim(mediaNewBean.getFocusAdvPrice()))?Float.parseFloat(mediaNewBean.getFocusAdvPrice()):0f);
						result.setLinkAdvPrice(!"".equals(StringUtil.toTrim(mediaNewBean.getLinkAdvPrice()))?Float.parseFloat(mediaNewBean.getLinkAdvPrice()):0f);
						result.setProgramaAdvPrice(!"".equals(StringUtil.toTrim(mediaNewBean.getProgramaAdvPrice()))?Float.parseFloat(mediaNewBean.getProgramaAdvPrice()):0f);
						result.setSubjectAdvPrice(!"".equals(StringUtil.toTrim(mediaNewBean.getSubjectAdvPrice()))?Float.parseFloat(mediaNewBean.getSubjectAdvPrice()):0f);

						//2018-03-13 地域信息取父级
						result.getM().getMediaArea().setCity(!"".equals(StringUtil.toTrim(mediaNewBean.getCity())) ? mediaNewBean.getCity() : "");
						result.getM().getMediaArea().setProvince(!"".equals(StringUtil.toTrim(mediaNewBean.getProvince()))?mediaNewBean.getProvince() : "");
						result.getM().getMediaArea().setCityId(!"".equals(StringUtil.toTrim(mediaNewBean.getCityID()))?mediaNewBean.getCityID():"");
						result.getM().getMediaArea().setCountry(!"".equals(StringUtil.toTrim(mediaNewBean.getCountry()))?mediaNewBean.getCountry():"");

						if (mediaNewBean.getChildren() != null) {
							result.getM().setMediaNameEn(!"".equals(StringUtil.toTrim(mediaNewBean.getChildren().getMediaNameEN()))?mediaNewBean.getChildren().getMediaNameEN():site);
							result.getM().setMediaType(!"".equals(StringUtil.toTrim(mediaNewBean.getChildren().getMediaType()))?mediaNewBean.getChildren().getMediaType():"");

							if (mediaNewBean != null && mediaNewBean.getChildren().getMediaCategory() != null && mediaNewBean.getChildren().getMediaCategory().length() > 0) {
								result.getM().setMediaCategory(Arrays.asList(StringUtil.toTrim(mediaNewBean.getChildren().getMediaCategory()).split(",")));
							}

							//2018-03-13 地域信息取父级
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
							result.setLayout(!"".equals(StringUtil.toTrim(mediaNewBean.getChildren().getMediaNameEN()))?mediaNewBean.getChildren().getMediaNameCN():"");//频道
						}else{
							result.getM().setMediaNameEn(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaNameEN()))?mediaNewBean.getMediaNameEN():site);
							result.getM().setMediaType(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaType()))?mediaNewBean.getMediaType():"");

							if (mediaNewBean != null && mediaNewBean.getMediaCategory() != null && mediaNewBean.getMediaCategory().length() > 0) {
								result.getM().setMediaCategory(Arrays.asList(removeEmpty(StringUtil.toTrim(mediaNewBean.getMediaCategory()).split(","))));
							}

							result.getA().setPv(mediaNewBean.getPv());//发行量
							result.getA().setUv(mediaNewBean.getUv());
							result.getA().setAdvPrice(mediaNewBean.getAdvPrice());//广告价值
							result.getA().setAlexaRank(mediaNewBean.getAlexaRank());
						}
					}else if("app".equals(StringUtil.toTrim(mediaType))){
						result.getM().setDomain("");
						result.getM().setSite("");
						result.getM().setMediaNameCn(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaNameCN()))?mediaNewBean.getMediaNameCN():domain);
						result.getM().setMediaNameEn(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaNameEN()))?mediaNewBean.getMediaNameEN():domain);
						result.getM().setMediaId(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaID())) ? mediaNewBean.getMediaID() : "");
						result.getM().setMediaType(mediaNewBean.getMediaType());

						if (mediaNewBean != null && mediaNewBean.getMediaCategory() != null && mediaNewBean.getMediaCategory().length() > 0) {
							result.getM().setMediaCategory(Arrays.asList(removeEmpty(StringUtil.toTrim(mediaNewBean.getMediaCategory()).split(","))));
						}

						result.getM().getMediaArea().setCity(!"".equals(StringUtil.toTrim(mediaNewBean.getCity())) ? mediaNewBean.getCity() : "");
						result.getM().getMediaArea().setProvince(!"".equals(StringUtil.toTrim(mediaNewBean.getProvince()))?mediaNewBean.getProvince() : "");
						result.getM().getMediaArea().setCityId(!"".equals(StringUtil.toTrim(mediaNewBean.getCityID()))?mediaNewBean.getCityID():"");
						result.getM().getMediaArea().setCountry(!"".equals(StringUtil.toTrim(mediaNewBean.getCountry()))?mediaNewBean.getCountry():"");

						result.getA().setPv(mediaNewBean.getPv());//发行量
						result.getA().setUv(mediaNewBean.getUv());
						result.getA().setAdvPrice(mediaNewBean.getAdvPrice());//广告价值
						result.getA().setAlexaRank(mediaNewBean.getAlexaRank());
					}else if("press".equals(StringUtil.toTrim(mediaType))){
						mediaNewBean = JSONObject.parseObject(resultJson,MediaNewBean.class);

						result.getM().setDomain(domain);
						result.getM().setSite(site);
						result.getM().setMediaNameCn(StringUtil.toTrim(mediaNewBean.getMediaNameCN()));
						result.getM().setMediaId(StringUtil.toTrim(mediaNewBean.getMediaID()));
						result.getM().setMediaNameEn(StringUtil.toString(mediaNewBean.getMediaNameEN()));
						result.getM().setMediaType(StringUtil.toString(mediaNewBean.getMediaType()));

						if(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaCategory()))){
							result.getM().setMediaCategory(Arrays.asList(removeEmpty(mediaNewBean.getMediaCategory().split(","))));
						}

						result.getM().getMediaArea().setCity(!"".equals(StringUtil.toTrim(mediaNewBean.getCity())) ? mediaNewBean.getCity() : "");
						result.getM().getMediaArea().setProvince(!"".equals(StringUtil.toTrim(mediaNewBean.getProvince()))?mediaNewBean.getProvince() : "");
						result.getM().getMediaArea().setCityId(!"".equals(StringUtil.toTrim(mediaNewBean.getCityID()))?mediaNewBean.getCityID():"");
						result.getM().getMediaArea().setCountry(!"".equals(StringUtil.toTrim(mediaNewBean.getCountry()))?mediaNewBean.getCountry():"");

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

	public static void main(String[] args) {
		String resultJson = HttpClientPoolUtil.execute("http://192.168.10.198:8095/api/P_GetByDomain?mediatype=web&medianame=%E7%BD%91%E6%98%93&site=dy.163.com&domain=163.com");
		if(!"".equals(StringUtil.toTrim(resultJson))&&!"null".equals(StringUtil.toTrim(resultJson))) {
			MediaNewBean mediaNewBean = JSONObject.parseObject(resultJson, MediaNewBean.class);

			System.out.println(JSON.toJSONString(mediaNewBean));
		}
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
