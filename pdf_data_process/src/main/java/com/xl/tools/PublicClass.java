package com.xl.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.*;
import com.xl.manage.common.CommonData;
import com.xl.manage.common.LogCommonData;
import org.apache.commons.lang.StringEscapeUtils;

import java.net.URLEncoder;
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

	/// <summary>
	/// 替换正文中特殊字符(ocr专用)
	/// </summary>
	/// <param name="content"></param>
	/// <returns></returns>
	public static String replaceContentText(String content)
	{
		String strtemp = "<!--_img_-->";
		content = content.replace(strtemp, "。");//.Replace(strtemp1, "。");
		for (char c :content.toCharArray())
		{
			if ((int)c < 28)
			{
				content = content.replace(String.valueOf(c), "");//将特殊字符转换成空
			}
		}

		/*if (content.toUpperCase().contains("<SCRIPT"))
		{
			content = content.replaceAll("<SCRIPT.*?>(</SCRIPT>)?", "");
		}
		if (content.toUpperCase().contains("<STYLE"))
		{
			content = content.replaceAll("<STYLE.*?>(</STYLE>)?", "");
		}*/

		//使用新版本替换
		content =  PublicClass.replaceO(content, "contenttext");

		content = StringEscapeUtils.unescapeHtml(content);//替换HTML转移符&amp;&nbsp;等等
		content = content.replaceAll(StringEscapeUtils.unescapeHtml("&nbsp;"), " ");//去掉多余的空格
		content = content.replaceAll( "\\s{1,}", " ");//去掉多余的空格
		content = content.replaceAll( "<p> </p>{1,}", "");//去掉多余的<p> </p>
		content = content.replace("<p> ", "<p>  ");//正文段首空两格

		return content;
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
	public static MediaResultBean GetMediaType(String mediaType, String mediaName,String site,String domain)
	{

		//GetByDomain?aurl=https://www.baidu.com&mediaType=web&mediaName=
		String resultJson;
		MediaResultBean result = new MediaResultBean();

		String mediaUrl = CommonData.GET_MEDIA_API;

		MediaNewBean mediaNewBean;
		try{
			mediaUrl = mediaUrl.replace("MEDIATYPE_VALUE",mediaType).replace("MEDIANAME_VALUE", URLEncoder.encode(mediaName,"UTF-8")).replace("SITE_VALUE","").replace("DOMAIN_VALUE","");
			resultJson = HttpClientPoolUtil.execute(mediaUrl);
			if(!"".equals(StringUtil.toTrim(resultJson))&&!"null".equals(StringUtil.toTrim(resultJson))){
				mediaNewBean = JSONObject.parseObject(resultJson,MediaNewBean.class);
				if(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaID()))&&!"".equals(StringUtil.toTrim(mediaNewBean.getMediaNameCN()))){
					result.getM().setDomain(domain);
					result.getM().setSite(site);
					result.getM().setMediaNameCn(StringUtil.toTrim(mediaNewBean.getMediaNameCN()));
					result.getM().setMediaId(StringUtil.toTrim(mediaNewBean.getMediaID()));
					result.getM().setMediaNameEn(StringUtil.toTrim(mediaNewBean.getMediaNameEN()));
					result.getM().setMediaType(StringUtil.toTrim(mediaNewBean.getMediaType()));

					if(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaCategory()))){
						result.getM().setMediaCategory(Arrays.asList(removeEmpty(mediaNewBean.getMediaCategory().split(","))));
					}

					result.getM().getMediaArea().setCity(!"".equals(StringUtil.toTrim(mediaNewBean.getCity())) ? mediaNewBean.getCity() : "");
					result.getM().getMediaArea().setProvince(!"".equals(StringUtil.toTrim(mediaNewBean.getProvince()))?mediaNewBean.getProvince() : "");
					result.getM().getMediaArea().setCityId(!"".equals(StringUtil.toTrim(mediaNewBean.getCityID()))?mediaNewBean.getCityID():"");
					result.getM().getMediaArea().setCountry(!"".equals(StringUtil.toTrim(mediaNewBean.getCityID()))?mediaNewBean.getCountry():"");

					result.getA().setPv(mediaNewBean.getPv()); //发行量
					result.getA().setUv(mediaNewBean.getUv());
					result.getA().setAdvPrice(mediaNewBean.getAdvPrice());//广告价值
					result.getA().setAlexaRank(mediaNewBean.getAlexaRank());
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

	public static List<String> getImgSrc(String htmlStr) {
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

	public static String getImageSrcSpic(String htmlStr,String urlPath) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
//       String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
		String regEx_img = "<image.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			System.out.println(img);
			// Matcher m =
			// Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			while (m.find()) {
				htmlStr = htmlStr.replace("src=\""+m.group(1)+"\"","src=\""+urlPath+"/"+m.group(1)+"\"");
			}
		}
		return htmlStr;
	}

	public static String getImgSrcSpic(String htmlStr,String urlPath) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
//       String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while (m_image.find()) {
			img = img + "," + m_image.group();
			System.out.println(img);
			// Matcher m =
			// Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			while (m.find()) {
				htmlStr = htmlStr.replace("src=\""+m.group(1)+"\"","src=\""+urlPath+"/"+m.group(1)+"\"");
			}
		}
		return htmlStr;
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

	public static void main(String[] args) {
//		String content = "<p align='center'><image style='border:1px black solid;' width='284' height='400' src=\"aa76d569b2b24590823583ed06dd4d98_b_B_BASIC.jpg\"/><image style='border:1px black solid;' width='400' height='266' src=\"59a72c379183422bb61e6d272c0a4398_b_B_BASIC.jpg\"/></p><br/><p class='imageSummary'>浚县农商银行员工代表在烈士公墓前默哀。 王硕 摄</p><br /><p>本报讯 （记者 汪丽娜 通讯员 王硕）4月4日上午，浚县农商银行组织员工代表到浚县烈士陵园开展清明节“缅怀先烈，文明祭扫”活动。</p><p>在庄严肃穆的烈士公墓前，该行员工怀着崇敬之情，全体肃立默哀，并向烈士墓敬献花圈。随后，大家认真听取了老兵代表的发言，被英雄的英勇事迹和甘愿为国献身的精神所感动。</p><p>该行员工表示,要以先烈们前仆后继、勇于献身的革命精神鼓舞斗志，以先烈们艰苦奋斗、顽强拼搏的进取精神坚定信心，强化为民服务意识，努力提升服务能力；铭记历史，脚踏实地，把无私奉献、艰苦奋斗的革命精神内化于心，变成推动实际工作的精神动力，为浚县农商银行的发展贡献力量。</p>";

		/*String content = "<p align='center'></p><br/><p class='imageSummary'>浚县农商银行员工代表在烈士公墓前默哀。 王硕 摄</p><br /><p>本报讯 （记者 汪丽娜 通讯员 王硕）4月4日上午，浚县农商银行组织员工代表到浚县烈士陵园开展清明节“缅怀先烈，文明祭扫”活动。</p><p>在庄严肃穆的烈士公墓前，该行员工怀着崇敬之情，全体肃立默哀，并向烈士墓敬献花圈。随后，大家认真听取了老兵代表的发言，被英雄的英勇事迹和甘愿为国献身的精神所感动。</p><p>该行员工表示,要以先烈们前仆后继、勇于献身的革命精神鼓舞斗志，以先烈们艰苦奋斗、顽强拼搏的进取精神坚定信心，强化为民服务意识，努力提升服务能力；铭记历史，脚踏实地，把无私奉献、艰苦奋斗的革命精神内化于心，变成推动实际工作的精神动力，为浚县农商银行的发展贡献力量。</p>";
		System.out.println(content);
		System.out.println(getImgSrcSpic(content,"http://paper.hebiw.com/epaper/hbrb/2018/04/09/RB02/story/"));*/

		String url = "http://paper.hebiw.com/epaper/hbrb/2018/04/09/RB03/story/2474793.shtml";
		System.out.println(url.substring(0,url.lastIndexOf("/")));

	}

}
