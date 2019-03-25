package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.bean.article.ArticleBean;
import com.xl.bean.article.FilterCondition;
import com.xl.bean.article.MediaBean;
import com.xl.bean.article.RuleFilterBean;
import com.xl.manage.common.CommonData;
import com.xl.manage.common.LogCommonData;
import com.xl.tools.HttpClientPoolUtil;
import com.xl.tools.LogHelper;
import com.xl.tools.PublicClass;
import com.xl.tools.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类处理
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class ClassifierService {


	public static void main(String[] args) {
		String result = "";
		ClassifierService service = new ClassifierService();
		String title = "勇冠三站一路夺冠 哈弗H7不靠颜值靠实力";
		String content = ", ,        许多女生都有夜跑的习惯，前阵子女大学生夜跑遭割喉的案子引发了不少人的关注，这件事提醒了我们，广大女生朋友们夜跑一定要注意安全。     在这里我用我的经历告诉大家，女孩子千万不要一个人在外面乱跑，因为等待着你的，除了绑架，还有、挖器官、强奸、人贩子等。     给大家分享一下我的亲身经历。     那天我像往常一样约了室友夜跑，被临时放鸽子了，就只有我一个人跑，跑了一会儿，因为我带着耳机的缘故，听不太清楚周围的声音，我隐约间觉得有人在跟着我。     回头看了一眼，看见一个打扮邋里邋遢的人跟在我身后，正保持着一个不远不近的距离，紧紧跟着我，因为光线不是很好的缘故，我隐隐约约看见他好像盯着我在笑。     我警惕性比较强，就加快了脚步，想着赶紧往家跑。     刚跑了没几步，就感觉后背被什么东西射中了，有些针刺的疼，还有些痒，后来我才知道我是被一种麻醉枪射中了。     我顿时感觉头皮发麻，吓得我赶紧跑，我也不敢回头，我想呼救，但是这周围一个人也没有，只能硬着头皮跑。     在这里提醒大家一下，以后晚上外出的话，一定要选那种人多的地方。     跑了约两三分钟，我感觉整个后背后颈子都是麻的，肯定是麻醉针起作用了，我当时心里就只有一个念头，我不能晕倒，晕倒了以后，等待着我的还不知是什么！     很可能被挖器官、被迫乞讨、被人贩卖到深山老林里，我越想越害怕，忍不住就哭了起来。     就在这个时候，我面前一辆车慢了下来。     我忙跑过去拍窗户喊救命，司机是一个三十多岁的男人，听到我呼救立马就把车停下来了，然后说快上车。     我上车以后，身子一栽，就彻底失去知觉了，本以为我逃了出来，没想到的是，这只不过是那些人贩子的一种手段而已。     等我醒来的时候已经被绑在一张病床上了。     肮脏不堪的房间，刺鼻的消毒水味道辣眼睛，我嘴上被贴了胶布，手脚被绑，吃奶的劲都使出来了也没挣脱，反倒把自己累得满头大汗。     眼泪止不住的往外冒，心头的恐惧没有经历过的人根本无法想象。     我甚至不敢喊叫，怕惹毛了那些人进来暴揍我一顿，只能静静的躺在床上压下恐惧，观察这间房间，一下就看到架子上托盘里的手术刀，绝望了。     肯定是遇到贩卖器官的人贩子了！！     这时候门外响起了说话的声音，吓得我浑身一哆嗦赶紧闭上眼。     门打开了，有脚步声走到我床边停下，那人在旁边站了会，突然伸手到我眼睛上抹了两把，抹去我的眼泪。     完了，他肯定知道我已经醒了。     再装下去已经没有意义，我睁开眼，眼前站了个穿白大褂的男人，精瘦身材小平头，带了个眼镜，人模人样的，只是他那双冰冷不带任何感情的眼睛，看了让人害怕。     感觉我在他眼里就跟一具尸体没什么区别。     “唔……唔唔……”     我根本说不出一个清楚的字，他冷冷笑了下，直接把我推出去。     我继续挣扎，希望他能把我嘴上的胶布撕了，求求他放了我，要钱的话我给他就好了，我宁愿他把我卖去山沟沟里给瘸子当老婆，也不愿意他们挖我器官啊！！     后来我逃出去以后，查了查这帮偷人器官的人的做法，看完是浑身直冒冷汗。     这些人都有庞大严密的组织，绑了人以后，就会根据你的身份伪造一份证明，说你是一个死刑犯，死前愿意捐献出自己的所有器官，白纸黑字让你按手印。     即便有人是报警了，警察上门了，他们说正在进行器官捐献手术，这些警察也无可奈何。     出了这间房，外面是一条巷子，他直接把我推到巷子尽头的一间房，房里打着很亮的手术灯，灯下面站了三个拿着手术刀的医生，他们围着一个女人。     那女人身上什么都没穿躺在手术台上，肚子被开了个血窟窿。     他们正从那个女人的肚子里捞出一坨血肉模糊的东西，要不是我被胶布贴了嘴巴我肯定吐出来了，那三个医生头也没回，继续挖掘，又捞了两坨出来。     这种挖法人还能活么？     我冷汗直冒，那女人脸上溅了很多血，安安静静的闭着眼睛，就像睡着了一样，一点痛苦的表情都没有，这样才更吓人！！     我赶紧扭头冲着站在我旁边的眼镜男大吼，“唔……唔唔……”     眼镜男看也没看我，走到手术台跟前，“还有多久？”     “一个小时吧。”     是不是一个小时之后就轮到我了？     绑在我手上的绳子勒进肉里，脑袋里只有一个念头，就算挣断了手脚也要活着逃出这里。     估计我动作太激烈了，那几个‘医生’都朝我这边看过来，眼镜男眉头恶狠狠的拧着，好像很不高兴，其中一个拿着手术刀的胖子吸了吸鼻子，“这血好香。”     “味道肯定很不错。”另一个也跟着说。     我完全听不懂他们在说什么，眼镜男直接走过来，又把我推出去，砰一声关上房门，离开那个鬼地方我应该庆幸才对，可眼镜男眼底冷冷的杀意让我更加害怕！     见我惧怕的看着他，他突然笑了，“别怕，你会死的没有一点痛苦的，反而会很舒服。”     不要……     我才二十岁大学都没毕业，我一点也不想死啊，可我没有办法，只能眼看着他把我拖出去塞进一辆车里，弯弯绕绕开进了深山老林中，停在一幢破旧的别墅跟前。     他把我从车里拖出来，我双腿发软站都站不稳，被他像扔垃圾一样扔在地上。     “要是不想死得太快，待会别乱叫，乖乖张开双腿就行。”     “？”我扭头望着他。     他只留给我一个背影，钻上车发动车子，车灯正好照着眼前那栋破旧的别墅，黑压压的窗户前好像站了一个白影。     我心头一惊，这里该不会有鬼吧？     眼镜男直接发动车子退下去，调转车头一踩油门走了。     四周一片漆黑，只有别墅门口点了一盏灯，还是诡异的红色，整座山，不对，应该说这一片，除了这栋别墅周围再没有其他建筑，但凡正常人都不会住在这种地方吧？     就算没有鬼，这里面也肯定住了一个怪异的变态！     我呼吸急促，心脏快要从喉咙里跳出来了，努力控制颤抖的腿撑起身子想跑，这时候别墅大门吱呀声打开，诡异的让人毛骨悚然。     里面传来一个男人的声音，“进来。”     那大门里面漆黑一片，像地狱的入口，我哪敢进去，身体下意识往后缩。     “不要让我再说第二遍！”     又来了！！     我呼吸一滞拔腿就往山下跑，后背突然贴上来一股凉意，有条冰冷的手臂缠在了我脖子上……     我想跑迈不动腿，想回头也没有勇气，哆哆嗦嗦把嘴上的胶布撕下来，“求求你放了我，要钱我可以给你，多少钱都……啊……”     话还没说完就被他一把推到旁边的石墩上，紧接着他沉重的身体压到我后背上，冰冷的嘴唇咬住我脖子。     他嘴很冷，嘴里还有股血腥的味道，我摸不清他到底是人是鬼。     “你好像很怕我？”     废话！     我平平淡淡活了二十年，今天第一次见到和谐社会这么血腥的一幕，能不怕？     他的手伸到我胸前狠狠一捏，“问你，是不是很怕我？”     我赶紧说，“怕，很怕，求你放了我吧，我给你钱……”     “钱？”他就像听到什么好听的笑话一样，埋头一口咬在我脖子上狠狠吸，穿破皮肉的痛让我忍不住歪着脖子，能明显感觉到体内的血液在流失。     好痛     他竟然在吸我的血！！     那些医生说我的血很香，这个人该不会是变态喜欢喝人血吧？     以前听说过有些狂热的吸血鬼爱好者喜欢模仿吸血鬼喝血，不过那些都是喝动物的生血，吃人血我还是头一次见！     放开我啊，痛死老子了！     我的手被绑着，根本没法推开他，只能用脚踹，刚踹了一下就被他膝盖顶进来强势把我的腿分开，让我呈一种怪异的姿势趴在他身下。     他好像有些生气，呼吸很重。     “你想干什么？！！”     “你猜。”他的声音声调带着戏谑。     我被吓到了，拼命扭动身体。     “你要干什么！！放开我！”     他的身体紧贴着我，我已经能感觉到他的yw了，我根本不报希望，却还是忍不住求饶，“求求你饶了我，求求你……”     我还是第一次啊，我也有喜欢的人，不想第一次就这么不明不白的给了。     “乖，我会温柔的。”说完，我身上从来没有男人触碰过的地方不断传来触电般的感觉，让我羞耻的恨不得找个地缝钻进去。     “很敏感嘛？”     “不要……这样……”我浑身的力气正在一点点的流失。     他牢牢把我控在身下肆无忌惮，当指腹触及那层薄膜之时，满意的笑了。     “啊”     我身下传来尖锐的一痛，痛的我眼泪直冒，撕裂般的痛苦还没缓和他已经迫不及待开始运动。     剧痛夹杂着一种难以言喻酥麻感，不知道是痛还是什么，让我受不住的喊出声。     他就像一只出闸的猛兽不停蚕食着我，一点也不温柔，我好几次感觉自己快要死掉，又被他拉了回来，感受那痛楚过后的战栗。     这一夜，寂静的山林里尽是我凄惨的呼喊声。     我不知道自己是怎么挺过来的，只记得天蒙蒙亮他才把我打包抱进别墅丢到了一张床上，我一身狼狈，身上腿上到处都是那种让人恶心的液体。     我很想逃，可我浑身的肌肉不停的抽搐，没躺多久就累得睡了过去。     仿佛睡了三天三夜才醒，洁白的床单，到处被我的血染红，身下的，脖子上的，赶紧摸了下脖子，脖子上的伤口已经结巴了。     我拖着被卡车碾过似的身体下床，身上还残存着那种让人恶心的味道。     回想昨晚上竟然在野外被人以那种耻辱的方式夺去了第一次，我委屈的眼泪就止不住的淌。     不对，第五次肯定都没了，那男人简直就是禽兽，翻来覆去的折磨我。     看了眼空荡荡的别墅，他不会一直把我关在这里供他发泄兽性吧？     然后等玩腻了再把我杀掉？     不要啊，我一定要逃出去。     咕咕……     肚子催命似的叫，我小心翼翼的出去看看能不能找点东西吃，吃饱了才有力气逃。     这栋别墅十分陈旧，家具老得来算上古董级别，还什么都没有，不仅没有吃的，还没有佣人，昨晚上的那个男人也不在。     天助我也，这正是逃跑的最佳时机！     我赶紧下到一楼，这种老式别墅的门很高大厚重，我用尽力气掰也掰不开，急的跺脚。     “快开啊！”我把吃奶的劲都使出来了。     “你想干什么？！”     突然，我身后响起一个冰冷的声音，周围的空气好像都随着冷下来了，我赶紧转身抵在门上，看着身后突然冒出来的男人。     那男人看上去大概二十八九，白衬衣，刘海略微遮住他的眼睛，五官深邃，轮廓完美，我已经无法用语言形容了，只能词穷赞叹一个帅字。     帅归帅，总感觉他脸色白的很不正常，深沉的眼神更不像他这个年纪该有的。     昨晚上强X我的男人不会就是他吧？     “没听见我说话？”他声调提高，好像生气了。     “我想……出去找点吃的。”我不敢说自己想逃，怕他真是昨晚上的那个变态男，我现在落他手里，天知道他还会使什么手段。     “你很饿？”     废话，“嗯。”我点点头。     他挑了挑眉毛朝我走过来，“这里什么都没有，只能让李奇送来，如果你表现好的话，我可以考虑打个电话给他。”     李奇？昨晚把我带到这来的那个眼镜男？     说完那句话，他直接在我面前扯开衬衣两颗扣子，露出里面结实的肌肉，我总算明白什么叫诱色可餐了，忍不住吞咽一口唾沫。     不过，他什么意思？     难不成想让我在这里取悦他，用身体来换？     比起被挖器官，贞操什么的的确不值一提，怕就怕他玩够了我的身体，还是要把我送回去挖器官，那我还不如死的有节操一点。     “你……是谁？”我突然好想知道他是谁，能不能主宰我的生死。     “你可以叫我主人。”     主人？这么说我是被卖给他了？     “你会把我送回去挖器官么？”     “话真多，看来你是不想吃饭了。”他说完就转身走，我双腿一软不争气扑到地上抱住他小腿，“我给你做佣人做牛做马也行，能不能不要这样对我……”     “对你怎样？现在明明是给你机会让你对我怎么样。”     “……”这人不仅变态，还不要脸，我竟无法反驳。     可我真的做不来，学校里面只教了画画没教我怎么去讨好男人啊。     而且，我那里还很痛！     我无助的瘫坐在地上，他没再多说什么，抽身离开，我是死是活他根本无所谓。     或者，看着我活活饿死也是他的趣味之一。     可我一点也不想死啊，我父母老来得女，我不能让他们白发人送黑发人。     反正第一次已经没了，第五次和五十次又有什么分别？我要活着！想看后续，请关注味辛宫重号：情惊 （回复 女孩）  我赶紧爬起来朝着他离开的方向追过去，走着走着闻到了一股子香味，米饭，红烧肉，还有青笋……     顺着香味到了餐厅，那男人正坐在主位上端着红酒杯轻晃，看着酒红色的液体沿着杯壁缓缓慢慢滑下，深沉的眼神仿佛一切尽在掌握。     他瞄我一眼，抿了口红酒含在嘴里。     我试探着，小心翼翼一步步走到他跟前，“能不能再给我一次机会？”     他放下酒杯，“我没听清。”     我拳头猛的捏紧，他一定是故意让我难堪，故意想看我卑贱的样子，我选择活下去，就只能选择不要脸了，“请再给我一次机会，让我……取悦你。”     满意了吧？, 这些吓人的事情，全都发生在你毫无防备的时候！, 0]";
		String mediaType = "press_press";
		String type = "1";
		JSONObject obj = new JSONObject();
		ArticleBean bean = new ArticleBean();
		for (int i=0;i<1;i++){
			try{

				obj.put("title", title);
				obj.put("content", content);
				obj.put("mediaType",mediaType);
				obj.put("mediaBean",new MediaBean());
				obj.put("type",type);
				obj.put("orgId",CommonData.ORGID_STR);

				result = HttpClientPoolUtil.execute(CommonData.CLASSIFER_URL,obj.toString());
				System.out.println(result);
				service.dealMResult(bean,result);
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * 处理分类
	 * @return com.xl.manage.bean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 16:52 2017/12/8
	 * @param bean
	 * @param type
	 */
	public ArticleBean dealClass(ArticleBean bean, String type)throws Exception{

		String title = bean.getTitle();
		String content = bean.getContentText();
		String mediaType = bean.getType();

		JSONObject obj = new JSONObject();

		obj.put("title",PublicClass.noHTML(title));
		obj.put("content",PublicClass.noHTML(content));
		obj.put("mediaType","press_"+mediaType);
		obj.put("mediaBean",bean.getMedia());
		obj.put("orgId", CommonData.ORGID_STR);//限定机构
		obj.put("type",type);

		String result = "";
		String resultReturn = "";

		try{
			result = HttpClientPoolUtil.execute(CommonData.CLASSIFER_URL,obj.toString());
			if("1".equals(type)){
				//TODO 正式 测试不需要上传机构
				bean = dealUpdateInfo(bean);
				resultReturn = dealMResult(bean,result);
			}else if("2".equals(type)){
				resultReturn = dealTResult(bean,result);
			}else if("3".equals(type)){
				resultReturn = dealWResult(bean,result);
			}

			if("1".equals(resultReturn)){//操作成功
				return bean;
			}else{
				LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取分类", obj.toJSONString(),"获取命中规则失败！"+resultReturn,new Exception("调用分类出现异常!") );
				return null;
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"匹配分类", obj.toJSONString(),"匹配分类出现异常！",e);
			bean = null;
		}
		return bean;
	}

	/**
	 * 处理上传信息
	 * @return com.xl.manage.bean.WeiboBean
	 * @Author: lww
	 * @Description:
	 * @Date: 17:54 2017/10/20
	 * @param bean
	 */
	private ArticleBean dealUpdateInfo(ArticleBean bean)throws Exception{

		String orgId;
		String monId;
		String labId;
		String[] monitorIds;
		String[] lableIds;

		List<String> orgList = bean.getOrgs()!=null?bean.getOrgs():new ArrayList<>();
		List<String> orgflagList = bean.getOrgsflag()!=null?bean.getOrgsflag():new ArrayList<>();
		List<String> monList = bean.getMonitors()!=null?bean.getMonitors():new ArrayList<>();
		List<String> labList = bean.getLabels()!=null?bean.getLabels():new ArrayList<>();
		String monitors = StringUtil.toTrim(bean.get_monitorIds());
		String lables = StringUtil.toTrim(bean.get_lableIds());

		if (!"".equals(monitors)&&!"0".equals(monitors)){
			LogHelper.info("处理上传监测项AID["+bean.getAid()+"]："+monitors);
			for (String monitorId:monitors.split(",")){
				if (!"".equals(StringUtil.toTrim(monitorId))){
					monitorIds = monitorId.split("\\.");
					if (monitorIds.length > 1){
						try{
							monId = monitorIds[1];
							orgId = monitorIds[0];

							if(!"".equals(StringUtil.toTrim(orgId))&&!"0".equals(StringUtil.toTrim(orgId))&&!orgList.contains(orgId)){
								orgList.add(orgId);
								orgflagList.add(orgId+"_h_0");
								orgflagList.add(orgId+"_r_0");
							}

							if(orgList.contains(orgId)&&!"".equals(StringUtil.toTrim(monId))&&!"0".equals(StringUtil.toTrim(monId))&&!monList.contains(orgId+"_"+monId)){
								monList.add(orgId+"_"+monId);
							}

						}catch(Exception e){}
					}
				}
			}
			bean.setOrgs(orgList);
			bean.setMonitors(monList);
			bean.setOrgsflag(orgflagList);
		}

		if (!"".equals(lables)&&!"0".equals(lables)){
			LogHelper.info("处理上传标签AID["+bean.getAid()+"]："+lables);
			for (String lableId:lables.split(",")){
				if (!"".equals(StringUtil.toTrim(lableId))){
					lableIds = lableId.split("\\.");
					if (lableIds.length > 1){
						try{
							labId = lableIds[1];
							orgId = lableIds[0];

							if(orgList.contains(orgId)&&!"".equals(StringUtil.toTrim(labId))&&!"0".equals(StringUtil.toTrim(labId))&&!labList.contains(orgId+"_"+labId)){
								labList.add(orgId+"_"+labId);
							}
						}catch(Exception e){}
					}
				}
			}
			bean.setOrgs(orgList);
			bean.setMonitors(monList);
			bean.setOrgsflag(orgflagList);
			bean.setLabels(labList);
		}
		return bean;
	}

	/**
	 * 处理监测项
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 17:26 2017/9/17
	 * @param bean
	 * @param result
	 */
	private String dealMResult(ArticleBean bean,String result)throws Exception{

		List<String> orgList = bean.getOrgs()!=null?bean.getOrgs():new ArrayList<>();
		List<String> orgflagList = bean.getMonitors()!=null?bean.getOrgsflag():new ArrayList<>();
		List<String> monList = bean.getMonitors()!=null?bean.getMonitors():new ArrayList<>();
		List<String> extracList = new ArrayList<>();
		List<RuleFilterBean> ruleFilterBeans = new ArrayList<>();
		List<FilterCondition> filterConditionList;
		RuleFilterBean ruleFilterBean;

		String msg ;
		JSONObject obj;
		JSONArray jsonArray;
		JSONArray keywordArray;
		JSONObject clzObj;
		JSONObject keywordObj;
		String orgId;
		String monId;
		String ruleId;
		String extracWord;
		String classids[];

		String resultReturn;

		try{
			if(!"".equals(StringUtil.toTrim(result))){
				obj = JSONObject.parseObject(result);
				if("1".equals(obj.getString("code"))){
					msg = obj.getString("datas");
					jsonArray = JSONArray.parseArray(msg);
					if(jsonArray!=null&&jsonArray.size()>0){
						for(Object object:jsonArray){
							clzObj = JSON.parseObject(object.toString());
							classids = clzObj.getString("classid").split("_");
							orgId = classids[classids.length-3];
							monId = classids[classids.length-2];
							ruleId = classids[classids.length-1];
							keywordArray = clzObj.getJSONArray("keywordDetail");
							if(!"".equals(orgId)){
								if(!orgList.contains(orgId)){
									orgList.add(orgId);
									orgflagList.add(orgId+"_h_0");
									orgflagList.add(orgId+"_r_0");
								}

								if(!"".equals(monId)){
									if(!monList.contains(orgId+"_"+monId)){
										monList.add(orgId+"_"+monId);
									}
									for(Object keyObj:keywordArray){
										keywordObj = JSONObject.parseObject(keyObj.toString());
										if(!"".equals(keywordObj.getString("keyword"))){
											extracWord = "m_"+monId+"_"+keywordObj.getString("keyword")+"_"+keywordObj.get("count");
											if(!extracList.contains(extracWord)){
												extracList.add(extracWord);
											}
										}
									}

									if(clzObj.containsKey("filterCondition")){
										filterConditionList = JSON.parseArray(clzObj.getJSONArray("filterCondition").toJSONString(), FilterCondition.class);
									}else{
										filterConditionList = new ArrayList<>();
									}
									ruleFilterBean = new RuleFilterBean(ruleId,orgId,monId,filterConditionList);
									ruleFilterBeans.add(ruleFilterBean);
								}
							}
						}
					}
				}
			}

			bean.setOrgs(orgList);
			bean.setOrgsflag(orgflagList);
			bean.setMonitors(monList);
			bean.setExtraction(extracList);
			bean.setRuleFilters(ruleFilterBeans);
			resultReturn = "1";
		}catch (Exception e){
			resultReturn = "-1";
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"处理监测项", JSONObject.toJSONString(bean),"解析监测项结果出现异常！"+result,e);
		}

		return resultReturn;
	}

	/**
	 * 标签分类处理
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 17:36 2017/9/17
	 * @param bean
	 * @param result
	 */
	private String dealTResult(ArticleBean bean,String result)throws Exception{

		List<String> orgList = bean.getOrgs();
		List<String> labList = bean.getLabels()!=null?bean.getLabels():new ArrayList<>();
		List<String> extracList = bean.getExtraction();

		JSONObject obj;
		JSONArray jsonArray;
		JSONArray keywordArray;
		JSONObject clzObj;
		JSONObject keywordObj;
		String orgId;
		String monId;
		String extracWord;
		String classids[];

		String resultReturn;
		try{
			if(!"".equals(result)){
				obj = JSONObject.parseObject(result);
				if("1".equals(obj.getString("code"))){
					jsonArray = JSONArray.parseArray(obj.getString("datas"));
					if(jsonArray!=null&&jsonArray.size()>0){
						for(Object object:jsonArray){
							clzObj = JSON.parseObject(object.toString());
							classids = clzObj.getString("classid").split("_");
							orgId = classids[classids.length-3];
							monId = classids[classids.length-2];
							keywordArray = clzObj.getJSONArray("keywordDetail");
							if(!"".equals(orgId)){
								if(orgList.contains(orgId)){
									if(!"".equals(monId)){
										if(!labList.contains(orgId+"_"+monId)){
											labList.add(orgId+"_"+monId);
										}
										for(Object keyObj:keywordArray){
											keywordObj = JSONObject.parseObject(keyObj.toString());
											if(!"".equals(keywordObj.getString("keyword"))){
												extracWord = "l_"+monId+"_"+keywordObj.getString("keyword")+"_"+keywordObj.get("count");
												if(!extracList.contains(extracWord)){
													extracList.add(extracWord);
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			bean.setExtraction(extracList);
			bean.setLabels(labList);
			resultReturn = "1";
		}catch (Exception e){
			resultReturn = "-1";
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"标签分类处理", JSONObject.toJSONString(bean),"解析标签分类结果出现异常！"+result,e);
		}

		return resultReturn;
	}


	/**
	 * 处理预警
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 17:26 2017/9/17
	 * @param bean
	 * @param result
	 */
	private String dealWResult(ArticleBean bean,String result)throws Exception{

		List<String> orgList = bean.getOrgs();
		List<String> warnList = new ArrayList<>();
		List<String> extracList = bean.getExtraction();

		String msg ;
		JSONObject obj;
		JSONArray jsonArray;
		JSONArray keywordArray;
		JSONObject clzObj;
		JSONObject keywordObj;
		String orgId;
		String monId;
		String extracWord;
		String classids[];

		String resultReturn;
		try{
			if(!"".equals(StringUtil.toTrim(result))){
				obj = JSONObject.parseObject(result);
				if("1".equals(obj.getString("code"))){
					msg = obj.getString("datas");
					jsonArray = JSONArray.parseArray(msg);
					if(jsonArray!=null&&jsonArray.size()>0){
						for(Object object:jsonArray){
							clzObj = JSON.parseObject(object.toString());
							classids = clzObj.getString("classid").split("_");
							orgId = classids[classids.length-3];
							monId = classids[classids.length-2];
							keywordArray = clzObj.getJSONArray("keywordDetail");
							if(!"".equals(orgId)){

								//机构不再以预警匹配 2018-03-01
								/*if(!orgList.contains(orgId)){
									orgList.add(orgId);
									orgflagList.add(orgId+"_h_0");
									orgflagList.add(orgId+"_r_0");
								}*/

								if(orgList.contains(orgId)){
									if(!"".equals(monId)){
										if(!warnList.contains(orgId+"_"+monId)){
											warnList.add(orgId+"_"+monId);
										}
										for(Object keyObj:keywordArray){
											keywordObj = JSONObject.parseObject(keyObj.toString());
											if(!"".equals(keywordObj.getString("keyword"))){
												extracWord = "w_"+monId+"_"+keywordObj.getString("keyword")+"_"+keywordObj.get("count");
												if(!extracList.contains(extracWord)){
													extracList.add(extracWord);
												}
											}
										}
									}
								}

							}
						}
					}
				}
			}
			bean.setWarnings(warnList);
			bean.setExtraction(extracList);
			resultReturn = "1";
		}catch (Exception e){
			resultReturn = "-1";
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"预警处理", JSONObject.toJSONString(bean),"解析预警分类结果出现异常！"+result,e);
		}
		return resultReturn;
	}
}
