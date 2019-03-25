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
		String content = "1月25日，在省十三届人代会第一次会议上，省长楼阳生代表省人民政府向大会作报告。报告提出，“推动县域内城乡义务教育一体化发展，推动高校要健全学科专业结构优化调整机制，建好优势专业、新增急需专业、削减需求适应度低的专业等”。 25日下午，省政协委员们针对政府工作报告，谈建议说看法。其中，山西大学附属中学校长刘伟对如何促进城乡义务教育均衡发展表达了看法，山西财经大学校长刘维奇则希望政府能够宏观指导高校专业的设立和削减，并提出各个高校应具备特色专业。此外，记者也找到政协委员们对于教育方面的一些提案，太原市文学院院长张守耀的提案提出了在我省规划建设职业教育城的想法，民进山西省委则在提案中提出希望我省中考体育能够增加游泳项目。 均衡发展城乡义务教育 政府工作报告中提出，要实施“学前教育第三期行动计划”，所有县（市、区）通过义务教育基本均衡国家评估认定，推动县域内城乡义务教育一体化发展，着力解决中小学生课外负担重、“择校热”“大班额”等突出问题。 在政协委员对政府工作报告的分组讨论中，山西大学附属中学校长刘伟对如何均衡发展城乡义务教育发表了自己的看法。他认为，由于各地区教育基础不同，地市之间、城乡之间义务教育发展程度有着较大差距，发展不均衡、不充分的问题较为突出。 其中，部分地区的中小学校舍和教育教学设施，停留在基本教学的阶段。随着优质教育资源向区域中心地区学校聚集，导致城市学校和农村学校教育相比仍存在一定差距。成绩作为衡量各校水平关健标准，造成生源的重大流动，加剧了城乡义务教育发展的不平衡。 为此，刘伟提出了一些探索性的建议。学校的不均衡发展由学校自身导致。各地要加强对学校的建设，按照精准脱“弱”的思路，从硬件设施上全面提升，下大决心改变目前这种薄弱情况。 在城乡教育发展一体化的过程中，针对部分地区学校 “小”“散”“弱”的情况，积极整合，合理调配，特别要建设一些配备齐全、管理到位、投入充足的乡村寄宿学校，调配优质资源办学。考虑到地方财政难保证学校的投入，刘伟建议由省、市财政作为专项资金进入年度固定预算，来保证寄宿制学校的优质发展。 同时，刘伟建议促进义务教育阶段各学校之间的资源整合，加大优质学校和薄弱学校一体化发展力度。通过名校对薄弱学校的全面托管，复制和迁移办学模式，推进集团化办学，快速拓展优质教育资源。 刘伟表示：“要推动义务教育由基本均衡向优质均衡发展，关键要通过带动作用帮助薄弱学校发展。一些城市通过集团化办学方式带动薄弱学校的发展提供了很好的借鉴。” 人才培养需政府指导 “高校自己设立和削减专业，有其盲目性和局限性，这需要政府部门参与并进行宏观指导。”1月25日下午，在教育界别的政协委员分组讨论中，来自山西财经大学的校长刘维奇提出了自己的建议。 在今年的政府工作报告中，提到了要强化人才支撑，其中包括健全高校学科专业结构优化调整机制，建好优势专业、新增急需专业、削减需求适应度低的专业。 对此，刘维奇表达了自己的看法，“高校学科的设立和削减，让高校自己选择的话，一些社会需求量大的专业，因为不够热门，就可能被高校压缩和削减，而高校为了生源问题，会设立一些热门专业，学生毕业后供大于求，遇到了就业难的问题。所以我希望政府部门可以参与其中，为高校专业的设立和削减上进行宏观指导。” 对于如何宏观的指导山西各高校专业设立，刘维奇提出，“专业建设要依托于学科建设，发挥学科优势，有了学科优势，才能有更专业的优势，才能培养出更优秀的人才。”同时，刘维奇还提出各高校要具备专业特色，“高校设立专业要有各自的专业特色，财经大学要往财会方面发展，理工大学要往理工科方面发展，不能因为专业的热门而设立专业。一个学校如果只为了生源的好坏来设立专业，不仅没有专业的师资力量，对人才教育也是不负责任的。” 此外，刘维奇希望政府部门对高校专业设立的宏观指导，能够根据山西各地的不同情况。“我省高校有省城的，有地市的，政府在指导过程中，最好能将各地不同的就业需求考虑进来，将总需求和人才供给结合考虑，这样有利于全省布局。”刘维奇表示。 新建职业教育城 在政协委员对政府工作报告进行分组讨论的同时，政协委员们也提交了自己的提案。记者注意到，在涉及到教育方面的提案中，太原市文学院院长张守耀提出，建议在省城规划建设职业教育城，集中职业教育院校，使之成为我省资源型经济转型发展的各类技术人才的“蓄水池”。 在提案中，张守耀首先阐述了我省职业教育的现状和存在的问题。职业院校数量多，规模小，都较为分散。2016年，全省具备办学资质的中职学校有375所（不含技工学校），校均规模901人，比全国中职校均1500人低40%，校均规模不足500人的中职学校有148所，占比达39.46%。 全省职业院校还存在着优质资源短缺的问题。据了解，全省有28个县（市、区）还没有建成合格的县级职教中心；高职院校办学空间狭小，校园占地面积不足的有26所，总缺额4246亩。职业教育师资力量在数量和质量上都难已适应当前我省资源型经济转型发展对职业教育的需求，成为当前我省职业教育发展的一大障碍。此外，职业院校还存在着办学活力不足和服务能力不强的系列问题。 为此，在充分考察国内其他省市设立的职教城基础上，张守耀提出在山西转型综改示范区规划建设职业教育城，将部分职业教育院校集中迁入省城职业教育城，利用职教城整合职业教育资源，建设集技能人才培养、大学生实训、农转工培训和社会化继续教育培训于一体的职业教育园区，为我省培养产业发展的应用型人才。 张守耀提出，职业教育城应该按照“职教围绕产业而发展、产业依托职教而壮大、职教与产业互动共赢”的办学理念，突出“产、学、研、用”协同发展，把职业教育城建设成为教城互动、产教互动、职教改革、技能培训的引领区、创新区、示范区，以带动我省产业发展。 同时，利用职业教育城，积极开展技能培训、深化校企合作、加强师资队伍建设、创新人才培养模式、大力发展职教集团、鼓励联合开放办学、加强对外交流合。 中考体育增加游泳 在山西省政协委员今年的提案中，民进山西省委提出，当今社会大部分家长过于重知识、轻能力，超负荷地学习导致学生身体素质远达不到国家对同龄孩子的要求标准，大部分学生处于亚健康状态，传统的体育运动如果得不到科学的锻炼，很容易导致学生骨折、拉伤甚至猝死等现象，这使得学校及体育教师不得已将体育课弱化或降低运动强度，从而达不到对学生身体素质的提高要求。为此，民进山西省委建议我省中考体育增加游泳选考项目，不但降低了运动带来的损伤，而且可以强化学生们的身体素质。 将游泳加入到中考体育的考试项目当中，既达到了体育课程的多样化，也有效地提高了学生学习的积极性，打破传统单一的 “陆地式体育教学”。游泳加入中考将会引起社会对游泳这项运动的关注，有利于游泳教学的规范化，推动体育教学的发展。 同时，中考体育加入游泳项目的多元化。游泳是一项与传统跑跳有着较大不同的运动项目。中考是一种面向广大考生的注重公平性，选拔性的考试，人的潜能各有不同，游泳加入中考给不擅长跑跳技能的同学于一种新的选择与尝试，使得中考体育实现以人为本，项目优化两者统筹兼顾。 为此，民进山西省委建议，在我省有条件的地市进行试验。如太原市的64家游泳场馆，每年培训青少年游泳达到10万余人，为中考体育增加游泳项目奠定了基础，成熟时可向全省推广游泳培训经验。 在全省范围内，鼓励有条件的学校通过政府拨款、社会引资多建游泳场馆。夏季在社区、学校操场搭建临时泳池。加强师资培训，特别在体育院校多招收游泳专业的学生。提高体育院系游泳专业课教学质量，使体育院校学生具备游泳教学和训练能力。定期举办游泳比赛，促使学生积极主动地参加游泳锻炼。本报记者 张帅";

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
