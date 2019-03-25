package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.ArticleBean;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WebCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.PublicClass;
import com.xl.tool.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lww
 * @Date:15:13 2017/9/14
 */
public class WebExtractionProService {

	public static void main(String[] args) {

		String title = "新普拉多是不是有2.0T";
		String content = "摘要：2017年11月23日，由ENI经济和信息化网与中山CIO协会联合主办的”制造业的数字化转型之道 “CIO知行社在中山召开。来自中山华帝厨电、木林森、香山衡器集团、洁柔纸业、广东卓梅尼等多家制造业时尚及电子制造业企业30多位CIO及IT负责人将参与活动，并就数字化转型战略、数字化实践经验及困惑以及创新信息技术在企业数字化过程中的价值及应用作深入的探讨。 \\n　　2017年11月23日，由ENI经济和信息化网与中山CIO协会联合主办的”制造业的数字化转型之道  “CIO知行社在中山召开。来自中山华帝厨电、木林森、香山衡器集团、洁柔纸业、广东卓梅尼等多家制造业时尚及电子制造业企业30多位CIO及IT负责人将参与活动，并就数字化转型战略、数字化实践经验及困惑以及创新信息技术在企业数字化过程中的价值及应用作深入的探讨。\\n　　在此次CIO知行社聚会中，木林森股份有限公司IT总监蔡宇才将分享木林森股份有限公司数字化实践成果，以及公司信息化建设经验。被誉为”上海第一代互联网络架构师”的上海有孚网络股份有限公司执行副总裁，数据中心联盟理事、上海信息安全行业协会副会长吕鑫，将结合自己16年IT及互联网实践经验，就IT战略规划和架构设计，安全，工业4.0、企业云计算等主题，对制造业企业数字转型方向提供独到见解和建议。\\n　　工信部部长苗圩近日在接受媒体采访时提到：制造业是实体经济的主体，是技术创新的主战场，是供给侧结构性改革的重要领域。新工业革命与我国实施制造强国战略形成历史性交汇，我们必须把握变革趋势和时间窗口，做好信息化与工业化深度融合这篇大文章，努力抢占新一轮产业竞争制高点。\\n　　制造业的数字化转型被看作是实现《中国制造2025》的重要内容，是数字经济发展的重要环节。制造业被看作数字经济的主战场，加快推进制造业数字化转型同样是国家的战略要求。拥有”中山制造”标签的中山制造企业，得益于政府从”十二五”到”十三五”期间持续对先进制造业给予的政策和技术支持，在2017年已取得在丰硕的成果，先进制造业占据了工业的半壁江山，且高端化趋势明显。\\n　　此次中山CIO知行社，在分享数字化转型理念和创新技术的同时，重点结合中山企业数字化转型现状，分享经验和成果，探讨下一步数字化方向。\\n　　参会嘉宾简介：\\n<!--_img_-->\\n木林森股份有限公司\\n　　曾任台湾神达电脑大陆顺达电脑厂运维经理，越南汉达精密科技经营规划经理，佛山市顺德区物联网应用促进中心副主任，佛山市顺德区信息化与工业化融合创新中心主任，广东省智能制造产业联盟筹委会秘书长，长期关注和投入于制造业企业如何利用信息化技术来提高企业竞争力。\\n<!--_img_-->\\n上海有孚网络股份有限公司执行副总裁 吕鑫\\n　　现任上海有孚网络股份有限公司执行副总裁，数据中心联盟理事、上海信息安全行业协会副会长、上海市云计算产业促进中心副会长、上海市云计算产业促进中心第一届云计算专家委员会委员、上海市信息化青年人才协会会员、上海市智慧园区专家库成员、中国大协同联盟企业信息化咨询委员会成员，被誉为“上海第一代互联网络架构师”。\\n　　拥有16年IT及互联网实践经验，对IT管理的各领域都有充分的认识和丰富的实战经验，对IT战略规划和架构设计，安全，工业4.0有深入了解，长期关注并推动企业云计算解决方案，在帮助企业数字转型方向有独到的见解。\\n　　会议联系人：\\n　　会议合作 卫女士18514402481 ar.wei@enicn.com\\n　　参会报名 刘女士15534739996 lie.liu@enicn.com\\n　　责任编辑：DJ编辑\\n<!--_img_-->\\n扫一扫，订阅更多数据中心资讯";

		JSONObject obj = new JSONObject();
		obj.put("title",title);
		obj.put("content",content);
		obj.put("link","");
		obj.put("sourceType","");
		obj.put("type","0");

		String result = "";
		try{
			for(int i=0;i<1;i++){
				result = HttpClientPoolUtil.execute(WebCommonData.EXTRACTION_URL,obj.toString());
				System.out.println(result);
			}
		}catch (Exception e){
			LogHelper.info("------------获取实体抽取Service出现异常！ WebExtractionProService.dealClass  [param:"+obj.toString()+"]--------");
		}
		/*String content = "很，这么晚才与家来分享领克01的体验。一是为了避开领克发布会之后的信息，二是Harris还是才疏学浅，不敢在拜读其他老师咖之前，就对这台，或许能够代表中国汽车工艺的车型妄加评价。另外，由于Harris在时拍了，这篇文章曾面临搁浅。但在体验中，我并的时间和来更的解读这台车，所以在本文中，我将尽可能、同时尽可能主观地带来我对于这台领克01的感受。车外，用出色设计手法打造出的另类关于领克的，褒贬不一或许能对它的概括，外界的评价有着十分的两极。但是，每当我和朋友聊到领克时，他们几乎都统一的回答：“丑！”这一评价多半基于这个度有些的，三层式的设计让不少人感到有些不可理喻。不过在参加了领克4月份的发布会，以及前段时间的上市会之后，我对于领克的家族设计手法有了一定的概念。整体或许很难，但是上的处理看出设计师的功底。不过颜值这东西，就像电子烟的烟油一样，百人百口，每人都有自己的审美。家听到领克01的介绍也够多了，我也不试图来家改变想法。所以接下来，我只发表一些主观的，对于的评价看法。 像极了法拉利...... 的在时会一定程度地影响视线 除了，其他部位的比例和都很合我意 个人认为，格栅窄一点，主灯放到上方，把压扁一点会不少关于，最后和家分享一个段子吧。论汽车设计师最的是什么，不是如何设计出人的元素，也不是如何与工程师妥协工程与上的，而是如何将自己十分随性十分写意的设计手法，用“猎豹式的”、“獠牙般的口”、“能量晶体”这样的词汇来介绍给媒体和消费者。而关于这一点，领克做得非常。，员为中心的车厢设计关于的配置和，我在宝骏和长城车型的时候，经常会和朋友分享一个（今天怎么一直讲段子...）：为什么这台车“”，因为它的任何，都描述为“超越同级的表现”，而任足都归纳为“到，。”虽然是一句话，但在之中，这是好车的通性之一。 第一次说这句话还是在宝骏310W的时候可在这台领克01的时候，类似的感受完全出现。整体的品质完全符合合资的水平，设计上任何伤，和工艺水平也很。部分十分，性也，也十分的歪向侧。同时，上还留下了的实体键操作，这在屏天下的今天实属不易。Call me old-school，但我还是觉得实体更加。 拨杆可能是唯一有影子的地方了 滚轮设计点赞，比操作更关于，真皮的覆盖面积十分令人，四肢能够触碰到的地方都是包裹。另外，我很用来点缀和边缘的金属材质。比金属更具，又的俗气和晃眼，摸上去的也很。当然了，不少人也已经听了。所以关于的一切，我来说（chui）些（mao）不（qiu）足（ci）。 用抽拉方式调节风量很，但只有风量全开的时候最范围的角度调整 虽然很，但是填充稍微了一些，偏 的台是很，但对于有些 非常，但是有些过于巧，操作时只能用手指 当然了，不过它...... 语音系统的识别，只能识别具体指令，不能随性地沟通。不过呢，在相处时间中，Harris仔细把玩这套引以的系统，还不能的介绍。不过各位放心，咱们老任已经下了领克01的，关于操作系统，以及和等日常用车生活的内容，Harris将用我们自己的工作车来体验。把车......我的个天老爷！出发之前，我的一个同事曾在网上看到一些前期体验的媒体说道，“领克的动态行驶表现完全超越国产水平。”他就对即将“上路”的我说，“帮我好好体验体验，到底有说的那么神。”而在和领克01相处了一天之后，我信誓旦旦地对他说：“你听到的看到的，都是真的。”先来介绍一些基本数据。这台领克01使用的是与沃尔沃共同研发的CMA平台架构，搭载一台190匹、300牛米的2.0T机，其中车型搭配箱，而的车型搭载7速箱。先来聊聊总成的表现，在2.0T动辄250匹的当下，01的账面数据并不出色。的感受也反应了这一点，度的值并任何的表现。但难能的，其实是这套总成的匹配默契度和的源源不断。在两千转之后的任何下，机都输出近乎类似的，箱的也很，完全出现或是需要令者等待的时刻。而在日常的巡航和中，两千五百转以下的区间才最能考验机和与箱之间的匹配。同样的，01得也十分出色。速挪动完全或是，之间的切换也很。不的说，这台箱与我开过的的箱——众的DQ500之间几乎差距。继续吹毛求疵的话，那就是绿灯亮起，你松开踩下的这个过程中，的启动与箱的结合会让你察觉到一丝的。 或者你不的话，直接使用跟车的ACC系统，那上面这段话就什么意义了......再来聊一聊，CMA的功底令人。上路十分钟你就能感受到的程度，侧向和的过滤都非常出色，幅度的起伏也有任何拉不住的感觉。在碎石路上，或许会有人把此刻的表现定义为“”，但对于一台调教十分具有欧系气息的车型来说，这完全降我对这台的度。车在跑的时候，室又是什么感受？NVH性能也称赞，和前都采用了双层，也有任何多余的从或是其他地方传来。机的控制尤其一提，首先做得很出色，完全有的感觉。然而同时，它又像是地故意“漏”了声浪进入车辆，让员地频率来判断机。这一特性在山道或是赛道中真是提供了巨的。说后背（度）和（）的感受，再来聊聊四肢的体验。姿态，与的调节。也很出色，速速以及各个模式之间的力度区别也是比较的。的控制有点迷幻，它并解锁，所以从P档切换D或R档需要拨动两次，在使用中会有些应。另外，采用左减右加的模式，使得我在结束之后也还整如何切换箱的模式。最后这一点我得好好聊聊，那就是01的，除了911、F-Type那些性能跑车之外，这就是我踩过的了。初段响应极佳，轻微的行程就感受到实打实的制。同时的反馈力度比较，线性，只要稍加就出现任何的。最主要的是，制极为，01的这套系统把这台1.6吨重的在35就从一百公里的刹停。同时得益于，前倾的趋势也不。不得不说，陆的卡钳搭配ITT的片，表现真的名不虚传。对了对了，我还下了赛道！开一台国产SUV下赛道，这听起来是一件透顶的事情。然而，事实却完全不是这样。得益于的功底和表现，这台领克01在赛道上充满了。极限可控，弯中也轻微的trail braking和提前给油。或许稍显，但是极具线性的依旧能够提供的。";
		JSONObject obj = new JSONObject();
		obj.put("msg",content);
		String result = "";
		try{
			result = HttpClientPoolUtil.execute(WebCommonData.LANGUAGE_URL,obj.toString());
			System.out.println(StringUtil.toTrim(result).replace("\"",""));
		}catch (Exception e){
			LogHelper.error("------------获取实体抽取Service出现异常！ WebExtractionProService.dealClass  [param:"+obj.toString()+"]--------",e);
		}*/
	}

	/**
	 * 实体抽取(包含语言提取)
	 * @return com.xl.manage.bean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 10:08 2017/11/3
	 * @param bean
	 */
	public ArticleBean dealExtraction(ArticleBean bean)throws Exception{

		String title = bean.getTitle();
		String content = bean.getContentText();

		JSONObject msgObj = new JSONObject();

		JSONObject obj = new JSONObject();
		obj.put("title",title);
		obj.put("content", PublicClass.StripHTML(content));
		obj.put("link","");
		obj.put("sourceType","");
		obj.put("type","0");

		String result = "";
		String resultReturn;
		try{
			result = HttpClientPoolUtil.execute(WebCommonData.EXTRACTION_URL,obj.toString());
			resultReturn = dealResult(bean,result);

			if("1".equals(resultReturn)){
				msgObj.put("msg",PublicClass.StripHTML(content));
				result = HttpClientPoolUtil.execute(WebCommonData.LANGUAGE_URL,msgObj.toString());
				bean.setLanguage(StringUtil.toTrim(result).replace("\"",""));
				return bean;
			}else{
				return null;
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"实体抽取(包含语言提取)", obj.toJSONString(),"实体抽取(包含语言提取)出现异常！ WebExtractionProService.dealExtraction",e);
			bean = null;
		}
		return bean;
	}

	/**
	 * 解析实体抽取结果
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 15:23 2017/9/18
	 * @param bean
	 * @param result
	 */
	private String dealResult(ArticleBean bean,String result)throws Exception{

		String msg ;
		JSONObject obj;
		JSONObject resultObj;

		String peopleStr = "";
		String locationStr = "";
		String mechanismStr = "";
		String mediaStr = "";
		String countryStr = "";
		String provinceStr = "";

		String resultReturn;
		try{
			if(!"".equals(StringUtil.toTrim(result))){
				obj = JSONObject.parseObject(result);
				if("1".equals(obj.getString("code"))){
					msg = obj.getString("datas");
					resultObj = JSONObject.parseObject(msg);

					peopleStr = resultObj.getString("people");
					locationStr = resultObj.getString("location");
					mechanismStr = resultObj.getString("organization");
					mediaStr = resultObj.getString("media");
					countryStr = resultObj.getString("country");
					provinceStr = resultObj.getString("province");
				}else{
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"调用实体抽取接口", result,"实体抽取获取失败！ ",new Exception(WebCommonData.EXTRACTION_URL+"调用失败!"));
					return "-1";
				}
			}
			bean.setPeopleExtraction(this.dealResult(peopleStr));
			bean.setLocationExtraction(this.dealResult(locationStr));
			bean.setMechanismExtraction(this.dealResult(mechanismStr));
			bean.setMediaExtraction(this.dealResult(mediaStr));
			bean.setCountryExtraction(this.dealResult(countryStr));
			bean.setProvinceExtraction(this.dealResult(provinceStr));
			resultReturn = "1";
		}catch (Exception e){
			resultReturn = "-1";
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"解析实体抽取结果", result,"解析实体抽取出结果出现异常！ WebExtractionProService.dealResult",e);
		}
		return resultReturn;
	}

	/**
	 * 处理返回结果
	 * @return java.util.List<java.lang.String>
	 * @Author: lww
	 * @Description:
	 * @Date: 15:23 2017/9/18
	 * @param result
	 */
	private List<String> dealResult(String result)throws Exception{

		List<String> resultList = new ArrayList<>();
		String[] resultArray;
		if(!"".equals(StringUtil.toTrim(result))){
			resultArray = result.split("#");
			for(String val:resultArray){
				if(!"".equals(StringUtil.toTrim(val))){
					resultList.add(val);
				}
			}
		}
		return resultList;
	}
}
