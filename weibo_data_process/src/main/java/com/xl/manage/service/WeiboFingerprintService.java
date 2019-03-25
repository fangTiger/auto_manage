package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.WeiboBean;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WeiboCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;
import org.omg.CORBA.StringHolder;

/**
 * 语义指纹处理
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class WeiboFingerprintService {

	/**
	 * 微博语义指纹处理
	 * @return com.xl.manage.bean.WeiboBean
	 * @Author: lww
	 * @Description:
	 * @Date: 17:01 2017/9/21
	 * @param bean
	 */
	public WeiboBean dealFingerprint(WeiboBean bean){

		JSONObject obj;
		JSONObject jsonObject;
		String result;
		long fingerprint = 0;
		try{
			if(!"".equals(StringUtil.toTrim(bean.getStatusText()))){
				obj = new JSONObject();
				obj.put("title","\"微博信息\"");
				obj.put("content","\""+bean.getStatusText()+"\"");
				obj.put("type","0");//走默认类型

				result = HttpClientPoolUtil.execute(WeiboCommonData.FINGERPRINT_URL,obj.toString());
				jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
					fingerprint = Long.parseLong(StringUtil.toTrim(jsonObject.getString("datas")));
					bean.setSig(fingerprint);
				}else{//访问接口失败
					bean = null;
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"访问微博语指纹接口", obj.toJSONString(),"访问微博语指纹接口出现异常！"+result,new Exception(WeiboCommonData.FINGERPRINT_URL+" 访问异常！"));
				}
			}else{
				bean.setSig(0l);
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"微博语指纹", JSON.toJSONString(bean),"获取微博语指纹异常！",e);
			bean = null;
		}
		return bean;
	}

	public static void main(String[] args) {
		JSONObject obj;
		String result;
		String datas;
		String dataArray[];
		int emotionValue = 0;

		String content = "2018汽车贷款政策调整和金融风控预测11 月 8 日，中国人民银行和银监会发布新修订的汽车贷款管理办法(以下简称办法)，并宣布自 2018 年 1 月 1 日起调整汽车贷款政策。同日，中国人民银行、银监会发布的《关于调整汽车贷款有关政策的通知》规定，自用传统动力汽车贷款最高发放比例为 80%，商用传统动力汽车贷款最高发放比例为 70%;自用新能源汽车贷款最高发放比例为 85%，商用新能源汽车贷款最高发放比例为 75%;二手车贷款最高发放比例为 70%。中国人民银行在公告中表示，做出调整的原因是为了落实国务院调整经济结构的政策，释放多元化消费潜力，推动绿色环保产业经济发展，以提升汽车消费信贷市场供给质效。事实上，原有的汽车贷款管理办法颁布于 2004 年。时隔 13 年后，新修订的办法出台，与原有办法相比，新规产生了哪些新变化?新规颁布又会给行业带来怎样的影响?汽车金融｜预测：汽车金融风控将整体趋严风险管控整体趋严法治周末记者近日走访北京多家 4S 店发现，新规暂时还未带来明显的变化。多家 4S 店销售人员告诉法治周末记者，由于新规是在明年 1 月 1 日起开始实施，目前店内还是延续以往的贷款政策。朝阳区一家奥迪 4S 店的工作人员对记者表示:“现在购车还是 50:50 的贷款方式，即首付 50%，贷款 50%，18 期免息。”在业内人士看来，新规目前虽还未进入正式实施阶段，但也反映出一些新的行业趋势。北京大成(上海)律师事务所高级合伙人陈立彤对法治周末记者指出:“新政策的实施不是单方面的措施，与今年以来监管层对房贷风险的管控具有联动关系。汽车金融风险管控整体来看是趋严方向，比如借款人资信评价系统改为信用评价系统，对于信用评级要审慎使用外部信用评级，通过内外评级结合的方式，都是为了控制金融风险，防范金融机构的危机和潜在漏洞。”如其所言，新办法在风险管控方面作出了条文修改，例如第二十三条将 “贷款人应建立借款人资信评级系统，审慎确定借款人的资信级别” 更改为 “贷款人应建立借款人信用评级系统，审慎使用外部信用评级，通过内外评级结合，确定借款人的信用级别”。在风险防范上，办法还规定，各金融机构应根据借款人信用状况、还款能力等合理确定汽车贷款具体发放比例，同时要强化贷前审查，完善客户资信评估体系，保证贷款第一还款来源能充分覆盖相应本金利息等，央行和银监会的相关机构也会进行跟踪监测和评估。“从严执行新办法，可能也会对汽车电商企业产生一定的冲击”，陈立彤表示，汽车电商一直以金融贷款力度大，贷款门槛低为特点，严格执行新办法，其高风险的客户会被拒之门外，持有牌照的金融机构的资金供给也会减少，当然这部分客户寻找其他途径进行金融贷款的话就会要承担更多的资金成本，准入资格会受到一定程度限定。在陈立彤看来，尽管二手车和新能源的汽车的贷款政策有松动，首付比例有调整，但是从执行层面看，相较于汽车电商的二手车 10% 首付比例，对于良性的汽车放款机制还需要不断完善和调整，等待市场检测。";
		try{
			obj = new JSONObject();
			obj.put("title","\"微博信息\"");
			obj.put("content", "\""+content+"\"");
			obj.put("type","0");//走默认类型

			result =  HttpClientPoolUtil.execute(WeiboCommonData.FINGERPRINT_URL,obj.toString());
//			3590595098927606260 1877054356458025654 916244129519130924 4286566814263024136 7838742445256350890
//			3590595098927606300 1877054356458025700 916244129519130900 4286566814263024000 7838742445256351000
			System.out.println(result);
			JSONObject jsonObject = JSONObject.parseObject(result);
			if("1".equals(jsonObject.getString("code"))){//请求成功
//				Long.p
				System.out.println(Long.parseLong(jsonObject.getString("datas")));
//				datas = jsonObject.getString("datas");
//				datas = datas.replace("<p>","");
//				dataArray = datas.split("</p>");
//
//				for(String data:dataArray){
//					if(data.indexOf("polarity")>-1){
//						emotionValue = (int) Double.parseDouble(StringUtil.toTrim(data.split("：")[1]));
//					}
//				}

			}else{//访问接口失败
				System.out.println("访问接口失败!");
			}
		}catch (Exception e){

		}
	}
}
