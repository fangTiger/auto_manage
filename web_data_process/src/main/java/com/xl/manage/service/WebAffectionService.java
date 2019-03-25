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

/**
 * 情感值处理
 * @Author:lww
 * @Date:15:33 2017/9/21
 */
public class WebAffectionService {

	/**
	 * 处理情感值
	 * @return com.xl.tf.bean.TfBean
	 * @Author: lww
	 * @Description:
	 * @Date: 15:46 2017/9/21
	 * @param bean
	 */
	public ArticleBean dealAffection(ArticleBean bean){
		JSONObject obj;
		String result;
		String datas;
		JSONObject jsonObject1;
		try{
			if(!"".equals(StringUtil.toTrim(PublicClass.StripHTML(bean.getContentText())))){
				obj = new JSONObject();
				obj.put("title",bean.getTitle());
				obj.put("content",StringUtil.toTrim(PublicClass.StripHTML(bean.getContentText())));
				obj.put("mainObj","");
				obj.put("type","0");//走默认类型

				result = HttpClientPoolUtil.execute(WebCommonData.AFFECTION_URL,obj.toString());

				JSONObject jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
					datas = jsonObject.getString("datas");
					jsonObject1 = JSON.parseObject(datas);
					bean.setEmotionValue((int)Double.parseDouble(jsonObject1.getString("tone")));
				}else{//访问接口失败
					bean = null;
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"访问情感值接口", JSONObject.toJSONString(bean),"访问情感值接口失败！"+result,new Exception("情感值接口访问失败!"));
				}
			}else{
				bean.setEmotionValue(0);
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取情感值", JSONObject.toJSONString(bean),"获取情感值出现异常！",e);
			bean = null;
		}
		return bean;
	}

	public static void main(String[] args) {

//		System.out.println((int)Double.parseDouble("76.5"));
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				WebAffectionService service = new WebAffectionService();
				service.test();
			}
		});

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				WebAffectionService service = new WebAffectionService();
				service.test();
			}
		});

		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				WebAffectionService service = new WebAffectionService();
				service.test();
			}
		});

		Thread thread4 = new Thread(new Runnable() {
			@Override
			public void run() {
				WebAffectionService service = new WebAffectionService();
				service.test();
			}
		});

		thread.start();
//		thread2.start();
//		thread3.start();
//		thread4.start();

	}

	private void test(){
		JSONObject obj;
		String result;
		String datas;
		String dataArray[];
		int emotionValue = 0;
		String content = "领跑者基地也要通过竞争产生, 1月12日，由中国改革报《能源发展》周刊、中国石油化工集团公司、苏州腾晖光伏技术有限公司联合主办的第三届能源发展与创新论坛在北京召开。  本届论坛以“新时代、新动能、新飞跃”为主题，与会者从“智慧能源与智能汽车协同发展、光伏领跑者如何领跑世界”两大课题着手，多种角度、多种模式探讨我国能源的发展创新与转型，2017年度中国能源创新奖也同时揭晓。  领跑者推进光伏平价上网  “光伏领跑者是通过市场化竞争的方式进行，2017年最大的变化就是以前只是开发企业竞争光伏领跑者基地，今后光伏领跑者基地本身也要通过竞争的方式产生。”水电水利规划设计总院副院长易跃春表示，2018年第三批应用领跑者基地更加注重技术和产业的先进性以及上网电价，企业通过自身的管理水平把电价合理降下来，将在未来的第三批应用领跑者中胜出。而第三批技术领跑者，看重的除了投资能力和业绩能力外，更重要的是技术和产品的先进性，列入其中的都是可推广应用但还没有实现批量制造的前沿技术。    领跑者基地建设对整个行业的技术进步、产业升级、管理优化、成本降低带来了显著的促进作用，国家发展改革委也根据行业发展的特点对电价进行了适当调整，在最新的0.55元、0.65元基础上，降低10%的电价作为应用领跑者入门的门槛。  山西省大同市采煤沉陷区国家先进技术光伏示范基地是首个领跑者基地。它以点带面，在提质增效、促进行业健康发展方面起到了良好的标杆示范作用。山西省大同市发展改革委副主任王林春说：“大同二期50万千瓦光伏发电应用领跑基地，将在总结一期建设经验的基础上，坚持竞争性配置资源，不搞地方保护、不搞行政干预、不搞区别对待，择优选择具有足够投资能力及技术实力的投资商。”基地通过竞争机制产生，将有效保障非技术成本的持续降低，促进用户侧平价上网的早日实现，也会逐步让发电侧的平价上网早日到来。易跃春表示，领跑者基地的建设将使得光伏产业从补充能源、辅助能源向替代能源和主力电力发展。  汽车将从交通工具迈向智能终端  在“人工智能+新能源”的推动下，汽车产业正迎来一场大变革，从交通工具转变为大型移动智能终端、储能单元和数字空间。北京新能源汽车服务股份有限公司（以下简称“北汽新能源”）副总经理李一秀认为，新时代下的汽车生态圈将是“路网+电网+通信网”的叠加，而整车企业、充换电设备、材料拆解、储能企业、光伏风电等将成为行业价值链中不可或缺的一环。  国网电动汽车服务有限公司董事长江冰认为，汽车将从交通工具向智能终端迈进，未来汽车一定是智能汽车，智能汽车一定是电动汽车。“电动车是未来能源系统的一个重要单元，充电桩是未来能源互联网的重要端口，是分布式新能源和电动汽车两个万亿级市场的连接点。”江冰表示。  但是，目前市场上充电桩的增长速度却远不及新能源汽车，无法满足终端消费者充电的便利性需求。同时，新能源汽车的清洁性也颇受争议，电动汽车与新能源发电急需协同发展。清华大学能源互联网创新研究院政研室主任何继江认为，“光储充”一体化的电动汽车充电站将在一定程度上解决上述问题。“以北京为例，一平方千米光伏充电站可以发1.2亿千瓦时电，满足4万辆电动汽车的使用，合理布局光伏充电站后将基本解决所有电动汽车所需的电力。”“电池是电动汽车的主要成本，而汽车动力电池的价格变化与太阳能电池的变化规律一致。”江冰表示，2007~2017年太阳能电池成本下降了92%，据此测算，2020~2023年汽车动力电池的价格将大幅下降，届时电动汽车将迎来拐点。与此同时，“油电平价”、分布式光伏发电平价上网、分布式储能商业化应用以及5G部署的完成将成为电动汽车发展的四大 “灰犀牛”，它们的出现将助力电动汽车快速迎来拐点，开启碾压燃油车的模式。(记者：宋旸）, , 0";
		try{
			obj = new JSONObject();
			obj.put("title","领跑者基地也要通过竞争产生");
			obj.put("content",content);
			obj.put("mainObj","");
			obj.put("type","0");//走默认类型
			int num = 0;
			while (num<2){
				result = HttpClientPoolUtil.execute(WebCommonData.AFFECTION_URL,obj.toString());
				System.out.println(result);
				JSONObject jsonObject = JSONObject.parseObject(result);

				if("1".equals(jsonObject.getString("code"))){//请求成功
					datas = jsonObject.getString("datas");
					JSONObject jsonObject1 = JSON.parseObject(datas);
					System.out.println(Integer.parseInt(jsonObject1.getString("tone")));
				}else{//访问接口失败
					System.out.println("访问接口失败!");
				}
				num++;
			}
		}catch (Exception e){

		}
	}
}
