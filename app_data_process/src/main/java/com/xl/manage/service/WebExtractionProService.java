package com.xl.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.bean.article.ArticleBean;
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
		String content = "连日来，中国福建龙岩“９小时完成火车站改造”的视频风行世界，引发外媒和外国网民追捧，“奇迹”“惊心动魄”“这才是中国速度”之类的留言不绝。 这并不是外媒第一次点赞“中国速度”。从北京三元桥一夜之间整体改造成功到南昌８小时内拆除立交桥，从世界最牛高铁网到各种跨海大桥，从中国制造到“中国智造”，越来越多的外媒关注飞跃式发展的中国。英国广播公司曾先后播发《你所不知道的中国》《难以想象的中国工程》等纪录片，多侧面展现中国诸多史诗级工程，中国展现的高速度和高水平令外国观众们叹为观止。 有比较就有高下。在赞叹“中国速度”的同时，诸多外国网友纷纷“吐槽”自己的国家。欧盟总部大楼翻修用了１３年，同期上海从无到有建起了浦东新区；十多年前破土动工的德国柏林新机场，启用时间最近说要等到２０２０年；而北京新机场２０１４年底开工，预计２０１９年即能投入使用。英国伦敦著名的大本钟去年８月开始维修，民众要等到２０２１年才能听到其下一次正式整点报时。有人说，若是大本钟在中国维修，４个月恐怕都嫌长。 面对外国朋友们对“中国速度”的点赞，首先我们当然是欢欣和自豪。４０年前当中国启动改革开放之时，发现自己落后太多，加速追赶“搞快点”成为从领导人到老百姓的普遍共识，“深圳速度”风靡全国。进入新世纪，中国连续赶超传统发达国家成为世界第二大经济体，这一改变世界经济版图和国际格局的伟大超越就是“中国速度”最鲜明、最集中的体现。 不过，在对发展速度欢欣自豪的同时，我们也要对效率、质量、管理等问题进行反思。从粗放式向精细化发展是人类社会发展进步的基本趋势。中国的发展已悄然进入新阶段，我们应该早日思考发展的硬速度与配套“软件”相适应的问题。 追求速度不能放弃对品质的追求，要摒弃短平快和凑合的思维。马路头年刚铺，第二年就开膛破肚；人行道上个月刚铺好，这个月就支离破碎；房子刚装修不久，墙裂了、马桶坏了……生活中常见到一些质量问题，大都是片面图快和缺乏“工匠精神”引起的。德国卫生间的马桶水箱“藏”在墙里，追求质量的德国人就有这种技术自信水箱及其零配件用上几十年也不会坏。“工匠精神”意味着精益求精、精雕细琢，意味着对高品质追求的责任感与自豪感。 追求速度不能以牺牲环境为代价，不能放弃对规划管理的放松。看见炼钢赚钱就大上快上，不顾周边环境的承载能力；看到共享单车前景好就一哄而上，而今许多城市的地铁口、商业区已车多为患。如今，“金山银山不如绿水青山”的理念越来越深入人心，人们对美好生活的要求已悄然超越对速度的呼唤。 追求速度不能漠视效率、服务和人文关怀。国内许多机场、车站、高速公路等硬件设施已经超过西方发达国家，但在运行效率、方便乘客等诸多方面尚不能让人满意。比如，国际航空协会制定的航班中转衔接时间标准为６０分钟，德国法兰克福机场规定最长中转时间标准为４５分钟，这也是其成为全球著名航空枢纽的一个重要原因。与这种中转效率相比，国内的航空枢纽恐怕还有不小差距。 硬件要硬，软件也要硬；速度重要，质量与以人为本更重要。我们不用慢下来，但无疑要在其他方面紧紧跟上。（记者吴黎明）";

		JSONObject obj = new JSONObject();
		obj.put("title",title);
		obj.put("content",content);
		obj.put("link","");
		obj.put("sourceType","");
		obj.put("type","0");

		String result = "";
		try{
			result = HttpClientPoolUtil.execute(WebCommonData.EXTRACTION_URL,obj.toString());
			System.out.println(result);
		}catch (Exception e){
			LogHelper.info("------------获取实体抽取Service出现异常！ WebExtractionProService.dealClass  [param:"+obj.toString()+"]--------"+e.getMessage());
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
		String content = PublicClass.StripHTML(bean.getContentText());

		JSONObject msgObj = new JSONObject();

		JSONObject obj = new JSONObject();
		obj.put("title",title);
		obj.put("content", content);
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
				LogHelper.info("----------------------------获取实体抽取Service出现异常！param["+obj.toString()+"]------------------------");
				return null;
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"实体抽取(包含语言提取)", obj.toJSONString(),"实体抽取(包含语言提取)出现异常！result:"+result,e);
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
			}else{
				LogHelper.error(LogCommonData.LOG_CODE_WEB,"调用实体抽取接口", result,"实体抽取获取失败！ ",new Exception(WebCommonData.EXTRACTION_URL+"调用失败!"));
				return "-1";
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
