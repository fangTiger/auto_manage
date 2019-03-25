package com.xl.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.ArticleBean;
import com.xl.manage.common.CommonData;
import com.xl.manage.common.LogCommonData;
import com.xl.tools.HttpClientPoolUtil;
import com.xl.tools.LogHelper;
import com.xl.tools.PublicClass;
import com.xl.tools.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lww
 * @Date:15:13 2017/9/14
 */
public class ExtractionProService {

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
			result = HttpClientPoolUtil.execute(CommonData.EXTRACTION_URL,obj.toString());
			System.out.println(result);
		}catch (Exception e){
			LogHelper.info("------------获取实体抽取Service出现异常！ ExtractionProService.dealClass  [param:"+obj.toString()+"]--------"+e.getMessage());
		}
		/*String content = "摘要：2017年11月23日，由ENI经济和信息化网与中山CIO协会联合主办的”制造业的数字化转型之道 “CIO知行社在中山召开。来自中山华帝厨电、木林森、香山衡器集团、洁柔纸业、广东卓梅尼等多家制造业时尚及电子制造业企业30多位CIO及IT负责人将参与活动，并就数字化转型战略、数字化实践经验及困惑以及创新信息技术在企业数字化过程中的价值及应用作深入的探讨。 \\n　　2017年11月23日，由ENI经济和信息化网与中山CIO协会联合主办的”制造业的数字化转型之道  “CIO知行社在中山召开。来自中山华帝厨电、木林森、香山衡器集团、洁柔纸业、广东卓梅尼等多家制造业时尚及电子制造业企业30多位CIO及IT负责人将参与活动，并就数字化转型战略、数字化实践经验及困惑以及创新信息技术在企业数字化过程中的价值及应用作深入的探讨。\\n　　在此次CIO知行社聚会中，木林森股份有限公司IT总监蔡宇才将分享木林森股份有限公司数字化实践成果，以及公司信息化建设经验。被誉为”上海第一代互联网络架构师”的上海有孚网络股份有限公司执行副总裁，数据中心联盟理事、上海信息安全行业协会副会长吕鑫，将结合自己16年IT及互联网实践经验，就IT战略规划和架构设计，安全，工业4.0、企业云计算等主题，对制造业企业数字转型方向提供独到见解和建议。\\n　　工信部部长苗圩近日在接受媒体采访时提到：制造业是实体经济的主体，是技术创新的主战场，是供给侧结构性改革的重要领域。新工业革命与我国实施制造强国战略形成历史性交汇，我们必须把握变革趋势和时间窗口，做好信息化与工业化深度融合这篇大文章，努力抢占新一轮产业竞争制高点。\\n　　制造业的数字化转型被看作是实现《中国制造2025》的重要内容，是数字经济发展的重要环节。制造业被看作数字经济的主战场，加快推进制造业数字化转型同样是国家的战略要求。拥有”中山制造”标签的中山制造企业，得益于政府从”十二五”到”十三五”期间持续对先进制造业给予的政策和技术支持，在2017年已取得在丰硕的成果，先进制造业占据了工业的半壁江山，且高端化趋势明显。\\n　　此次中山CIO知行社，在分享数字化转型理念和创新技术的同时，重点结合中山企业数字化转型现状，分享经验和成果，探讨下一步数字化方向。\\n　　参会嘉宾简介：\\n<!--_img_-->\\n木林森股份有限公司\\n　　曾任台湾神达电脑大陆顺达电脑厂运维经理，越南汉达精密科技经营规划经理，佛山市顺德区物联网应用促进中心副主任，佛山市顺德区信息化与工业化融合创新中心主任，广东省智能制造产业联盟筹委会秘书长，长期关注和投入于制造业企业如何利用信息化技术来提高企业竞争力。\\n<!--_img_-->\\n上海有孚网络股份有限公司执行副总裁 吕鑫\\n　　现任上海有孚网络股份有限公司执行副总裁，数据中心联盟理事、上海信息安全行业协会副会长、上海市云计算产业促进中心副会长、上海市云计算产业促进中心第一届云计算专家委员会委员、上海市信息化青年人才协会会员、上海市智慧园区专家库成员、中国大协同联盟企业信息化咨询委员会成员，被誉为“上海第一代互联网络架构师”。\\n　　拥有16年IT及互联网实践经验，对IT管理的各领域都有充分的认识和丰富的实战经验，对IT战略规划和架构设计，安全，工业4.0有深入了解，长期关注并推动企业云计算解决方案，在帮助企业数字转型方向有独到的见解。\\n　　会议联系人：\\n　　会议合作 卫女士18514402481 ar.wei@enicn.com\\n　　参会报名 刘女士15534739996 lie.liu@enicn.com\\n　　责任编辑：DJ编辑\\n<!--_img_-->\\n扫一扫，订阅更多数据中心资讯";

		JSONObject obj = new JSONObject();
		obj.put("msg",content);
		String result = "";
		try{
			result = HttpClientPoolUtil.execute(CommonData.LANGUAGE_URL,obj.toString());
			System.out.println(StringUtil.toTrim(result).replace("\"",""));
		}catch (Exception e){
			LogHelper.error("------------获取实体抽取Service出现异常！ ExtractionProService.dealClass  [param:"+obj.toString()+"]--------",e);
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
			result = HttpClientPoolUtil.execute(CommonData.EXTRACTION_URL,obj.toString());
			resultReturn = dealResult(bean,result);

			if("1".equals(resultReturn)){
				msgObj.put("msg",PublicClass.StripHTML(content));
				result = HttpClientPoolUtil.execute(CommonData.LANGUAGE_URL,msgObj.toString());
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
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"调用实体抽取接口", result,"实体抽取获取失败！ ",new Exception(CommonData.EXTRACTION_URL+"调用失败!"));
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
