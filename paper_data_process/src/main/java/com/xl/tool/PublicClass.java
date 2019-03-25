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
		String content = " 我活着长到现在，简直是个奇迹。 前几天，我们公司集体去影院围观了《奇迹男孩》，男生沉默女生流泪，为可爱坚强的男主和他善良的爸爸妈妈姐姐疯狂打call。 有的人说：“有这样的父母，就是让我在他们家当条狗都愿意啊！”还有个女生则是想到了自己小时候被欺负的经历，庆幸自己有个好爸爸，到学校跟班主任及时沟通，才没有被排挤。 更多的人最近深受日本那个养蛙游戏的荼毒，纷纷表示：别说是养娃了，我们这一代可是连只蛙儿子都养不好的啊。 养蛙这事，让我发现，为人父母的都是戏精。 多亏了他们的戏精，我们才能活到现在。 别的都行，不要动我的梦想 每个孩子的童年，都是由一个个梦想连缀而成的。有些孩子天赋异禀，从小就才华出众，这样的人往往是我们这些熊孩子的噩梦：他们是“隔壁家的孩子”。 但才华出众的孩子往往也有自己的烦恼。比如《寻梦环游记》里的小男主，从小吉他弹得飞起，能唱能跳，一看就是superstar的好苗子。但家人却成了他追求梦想道理上最大的阻力。 “热爱音乐的米格尔不幸地出生在一个视音乐为洪水猛兽的大家庭之中，一家人只盼着米格尔快快长大，好继承家里传承了数代的制鞋产业。”这是豆瓣上《寻梦环游记》的开头介绍。 看过电影的我们都知道，一开始，米格尔追求音乐的道路是非常坎坷的，因为他有一个因为音乐抛家弃子的祖先，音乐，在这个家里，成了诅咒。 而作为家庭权威的代表，米格尔的祖母在所有事情上都对这个宝贝金孙百般呵护，却旗帜鲜明地反对他的梦想，不惜通过砸烂吉他的方式来表达自己的态度。长辈的不理解，是米格尔最大的不幸。 米格尔的奶奶，一开始像个“可爱又迷人的反派角色” 。 当然，米格尔也是幸运的，因为影片一个亡灵还魂的故事洗白了他曾曾祖父维克多抛弃妻子的原因，最终用一首《Remember Me》，让家人明白了音乐在这个家庭中的重要性。 我却在想：如果没有亡灵节，如果维克托没有被洗白，这个故事最终会是怎样的？很显然，如果有这些如果，米格尔的音乐生涯还没开始就结束了：他将带着家人的期许，成为一名鞋匠。 而这，也是现实生活中大多数孩子的故事：梦想若得不到家人的理解，往往在萌芽之际就已经夭折。 看到摔吉他的桥段，是不是让你想起《神秘巨星》里，女孩尹希娅的直男癌爸爸？他粗暴地干涉尹希娅的人生，拒绝让她接触音乐和电脑，甚至想借着到利雅得工作的机会，让自己的女儿早早嫁给一个沙特商人的儿子。这还是亲爹吗？ 如果有“渣男奥斯卡”，今年的最佳渣男奖就是你了 ！ 我们每个人都是从童年的梦想中成长过来的，这个道路上，有的梦想实现了，更多的梦想因为各种原因而泯灭。尽力为孩子在追梦的道路上实现梦想，是每个父母的职责。在我看来，这是比吃饱穿暖更重要的事。 粗暴干涉孩子的梦想，是差劲的父母才会做的事情。 不要那样保护我 被家人粗暴干涉的孩子是可悲的，但被家人盲目溺爱的孩子，也一样可怜。 从小要风得风要雨得雨的小孩会长成啥样？看看《哈利?波特》里马尔福家的公子就知道了。 人见人嫌的德拉科·马尔福同志。 盲目的溺爱，让马尔福同志养成了目中无人的习惯，仗着自己显赫家世，在霍格沃兹横行霸道。 在他的眼里，大概有钱人真的就是可以为所欲为的吧。直到他遇见了人生中最大的bug：哈利·波特。 从小有求必应的马尔福不明白，自己为什么就是比不过一个孤儿。马尔福的性格跟他的父母直接相关，显然，马尔福家的孩子带带都是贵族血统（巫师名门）、名校（斯莱特林）出身，在魔法部有着深远的影响力。 上一届人见人嫌的卢修斯·马尔福同志。 在新上映的电影《奇迹男孩》里，也有一个类似的情节：因为容貌的原因，男孩奥吉常常被同学欺负。那位欺负奥吉的男生的父母被校长教导了办公室，校长对他们说：“奥吉没有办法改变容貌，但我们可以改变对他的看法。” 这对父母，就跟马尔福家的父母一样，仗着自己有钱有势，对校长说了一句：“现实世界会告诉他答案”。 儿童的社会，往往比成年人残酷。 每个目中无人的马尔福，以及每一个欺负小男孩的富家小孩，都是在他们目中无人的父辈充满溺爱的凝视下养育而成的。这样的温室教育，在家怎么拽都可以，但一到社会中，他们就成了令人讨厌、无知又自私的混蛋。 保护不等于溺爱。过于溺爱孩子，无微不至的温室教育也是不利于孩子的成长的。 这样的奇娃究竟是怎么养出来的？ “相信自己的孩子”是一句听起来容易做起来非常有技术含量的事情，它意味着你要在给孩子充分自由的前提下，默默守护着他们。既不能干涉他们自由意志，也不能让他们遭遇危险。这对父母来说，是很大的考验。 不能管孩子，又不能不管孩子。放任孩子自由不羁出去浪，又默默为他们操碎了心。准备好出行的食物和用品，在背包里放护身符保平安……这不就是养蛙吗？！ 《琅琊榜之风起长林》里的长林王，在这方面就是一个无可挑剔的父亲。在教育二儿子萧平旌这件事上，他和大儿子萧平章可谓煞费苦心。 平章和平旌的父亲，刚领便当的“国民公公”长林王。咦我剧透了吗？ 少年萧平旌生性逍遥，带着股江湖气息。为了给他一个宽松的环境，父亲和大哥把朝堂之上的事物都揽了下来，让他得以在琅琊阁专心学艺。 后来，萧平旌主动请缨，押送重要犯人到帝都，长林王和长林世子充分相信他的能力，暗地里却又对可能遭遇的劫狱做了长足的准备。最后，萧平旌凭借着自己的能力圆满完成了这件任务。 但萧平旌对父亲的做法还是颇有抱怨：为啥父王每次都把所有可能的突发情况都算得那么准？ 为啥我长这么帅还是要被我爹耍？ 这就是为人父母的智慧：放任孩子在成长道路中一往无前，同时永远准备好备用方案，以防万一，时刻守护着TA。 《风起长林》的这一段，让我想起了我那个香港表弟小时候的故事。我的表弟从小精通钢琴、算数和西班牙语，对学习这件事儿乐此不疲。在八岁的时候，他就养成了每天看大公司资讯，给父母推荐股票的习惯。我一直很好奇，这样的奇娃到底是怎样教出来的。 有一次我去香港，住在他们家。我姨丈吩咐我表弟第二天自己坐巴士到港岛买东西，上补习班，然后买菜回家。 当时我就惊呆了，指着他的鼻子骂：“有你这样当爹的吗！你自己偷懒不买菜就算了还叫自己八岁的儿子去！”我姨丈黑着脸说：“我教儿子不用你管”。 第二天清晨，我在睡梦中被我姨丈叫醒，他拉着我，跟踪他儿子一整天。表弟坐上巴士二层，我们就躲在一层的角落；表弟到商店里采购东西，我们保持着二十米的观望距离；表弟上补习班，我们在外头等着；表弟去买菜，我们躲在菜市场外的奶茶铺里，生怕他被人骗了。 那天晚上，我们一路跟踪着这娃，比他提前一分钟赶回了家。表弟一进门，我姨丈喝着凉了半天的茶，一副悠闲看报的样子，毫不在意地问他“今天的事情都办好了吗？”而我，已累得瘫在一旁的沙发上。 “他每次一个人出门，我都是这么跟过来的。”我姨丈说。 教育小孩，真是一件博大精深的事儿啊。 而我的牙医朋友，则是在哄骗孩子及时清洁牙齿这件事儿上煞费苦心。她说，根据理论，吃完东西的二十分钟是预防蛀牙的黄金时间。食物残渣带来的酸让蛀牙有机可乘，单靠早晚刷牙完全不够。 让牙齿脱离脱矿蛀牙期的“黄金20分钟”。 因此她一直煞费苦心地想要孩子养成餐后清洁牙齿的习惯，各种连哄带骗地监督孩子去漱口或者嚼无糖口香糖。但孩子上学后大部分时间都在学校，无法亲自监督的问题让她一直很苦恼，直到最近她看到了这个视频 吃饭时，饭盒上的萌猫感受到温度的变化，会变化出清洁的小漫画，提醒孩子们在餐后清洁口腔。让孩子们抓紧在牙齿面临脱矿危险的黄金20分钟内清洁牙齿，降低蛀牙的风险。通过这一童趣十足的办法，保证家长在缺席的时候，孩子也能及时清洁自己的口腔。 小小的一个温感创意，却透露着孩子教育和保护的心思：顺应孩子的天性，守护他们玩耍的快乐，同时培养良好习惯，让孩子们碰到问题时能下意识用正确的方法及时应对。这就是父母温暖引导，未雨绸缪的结果。 你以为自己是不受拘束自由自在长大的，其实很多时候，家长已经默默在你身后帮你扫除了好多障碍。这么一想，我们每个人能长到这么大，真是个奇迹啊。 或许不久的将来，你也可以学会传递这份温暖。在此之前，你需要先认识一下设计这款饭盒的暖男“趣爱牙”的爱牙君。爱牙君的推文虽然满满的都是干货，读起来却风趣幽默。贱暖痞帅的文风之下，隐藏着一个关爱牙齿更关心你的随身口腔私人管家。 艾森 所以今天我的目标是没有蛀牙么？ 韩寒 开什么玩笑，当然是10W+ ";
//		content = StringEscapeUtils.unescapeHtml(content);//替换HTML转移符&amp;&nbsp;等等
//		content = content.replaceAll("\\s{1,}", " ");//去掉多余的空格
		System.out.println(StripHTML(content).length());
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
							/*result.getM().getMediaArea().setCity(!"".equals(StringUtil.toTrim(mediaNewBean.getChildren().getCity())) ? mediaNewBean.getChildren().getCity() : "");
							result.getM().getMediaArea().setProvince(!"".equals(StringUtil.toTrim(mediaNewBean.getChildren().getProvince()))?mediaNewBean.getChildren().getProvince() : "");
							result.getM().getMediaArea().setCityId(!"".equals(StringUtil.toTrim(mediaNewBean.getChildren().getCityID()))?mediaNewBean.getChildren().getCityID():"");*/

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
						result.getM().setMediaNameEn(!"".equals(StringUtil.toTrim(mediaNewBean.getMediaNameEN())) ? mediaNewBean.getMediaNameEN() : domain);
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
						result.getM().setMediaNameEn(StringUtil.toTrim(mediaNewBean.getMediaNameEN()));
						result.getM().setMediaType(StringUtil.toTrim(mediaNewBean.getMediaType()));

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
