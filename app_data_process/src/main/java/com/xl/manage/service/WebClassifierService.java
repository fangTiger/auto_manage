package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.bean.article.*;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WebCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.PublicClass;
import com.xl.tool.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分类处理
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class WebClassifierService {


	public static void main(String[] args) {
		String result = "";
		WebClassifierService service = new WebClassifierService();
		String title = "英媒：腾讯携手安联投资德国数字零售银行";
		String content = "<p> 参考消息网3月22日报道英媒称，作为柏林金融科技领域旗舰初创公司的德国网上银行N26，从一批投资者筹集了1.6亿美元，牵头这些投资者的是中国互联网和社交媒体集团腾讯（Tencent）以及保险集团安联（Allianz）的数字投资部门Allianz X。</p><p> 据英国《金融时报》网站3月21日报道，本轮筹资是对柏林科技行业投下的最新一张信心票，在过去一年时间里，该行业见证了一连串大手笔投资和在股市上市。</p><p> 安永（EY）20日发布的数据显示，2017年总部位于柏林的初创公司共筹集了29.7亿欧元，远高于上一年的11亿欧元。在欧洲科技枢纽中，只有伦敦比柏林更成功，去年总部位于伦敦的初创公司共筹集48.8亿欧元。</p><p> 报道称，N26的联合创始人和首席执行官瓦伦丁·施塔尔夫（Valentin Stalf）表示，与腾讯和Allianz X的交易是“欧洲金融科技领域最大一轮筹资之一，也是德国金融科技公司有史以来获得的最大筹资”。他把两家新投资者形容为“活跃于各自所在的金融和互联网服务领域的强大提供商”。</p><p> 报道称，施塔尔夫表示，该公司计划利用最新一轮投资来加速推进扩张——N26即将在今年晚些时候登陆英国和美国——并投资于产品研发。该公司于2016年获得了德国银行业务牌照，迄今筹集了2.15亿美元。N26并未透露外部投资者的持股规模，以及该公司整体估值。</p><p> N26创办于2013年，是一家数字零售银行，让客户通过手机APP来完成支付和其他银行交易。该公司在17个欧洲市场拥有85万客户，声称每天签约逾2000名新客户。</p><p> 报道称，与传统零售银行不同，N26没有分行网点，因而成本基础低于竞争对手。尽管近年增长迅速，但该公司只有380名员工。</p><p> N26在20日的一份声明中表示，计划通过使用人工智能来为客户提供更进一步的个性化服务，并补充称，人工智能“将使未来的银行变得更善于适应客户的需求，并以目前传统零售银行所欠缺的方式来解决问题”。</p><p> 安联集团首席数字官索尔马兹·阿尔廷（Solmaz Altin）在一份声明中称：“N26是移动银行领域的明显领先者。N26的银行业务平台正使传统的金融服务商业模式现代化。”</p><p> 一名知情人士向英国《金融时报》表示，这家德国集团贡献了本轮筹资的四分之一，投资约4000万美元。 </p><p> 报道称，腾讯投资（Tencent Investment）管理合伙人林海峰表示：“我们看到欧洲市场对数字银行产品和服务的需求日益增长，很高兴成为N26的战略投资者，建立长期的合作伙伴关系，并参与该行的成长。”</p><p> 参考消息网-出海记记者从N26官网发布的信息获悉，N26于2013年由多年的好友瓦伦丁·施塔尔夫和马克西米利安·泰恩塔尔（Maximilian Tayenthal）共同创立。他们设计的N26是一个以客户为中心和移动第一的零售银行。</p><p> 它重新设计了银行系统，让客户在任何时间任何地点都可以通过手机应用程序享受到简单便捷的银行服务。</p><p> <img src='http://caijing.chinadaily.com.cn/img/attachement/jpg/site1/20180322/448a5bd66a9a1c1d5d5b33.jpg' /></p><p> 资料图（新华社）</p>";
		String mediaType = "all_web";
		String type = "1";
		JSONObject obj = new JSONObject();
		ArticleBean bean = new ArticleBean();
		MediaBean mediaBean = new MediaBean();
		mediaBean.setMediaType("news");
		for (int i=0;i<1;i++){
			try{
//

//				obj.put("title",WebCommonData.getIctclasByContent(PublicClass.StripHTML(title)));
//				obj.put("content",WebCommonData.getIctclasByContent(PublicClass.StripHTML(content)));
				obj.put("title",PublicClass.noHTML(title));
				obj.put("content",PublicClass.noHTML(content));
				obj.put("mediaType",mediaType);
				obj.put("orgId",WebCommonData.ORGID_STR);//限定机构
				obj.put("mediaBean",mediaBean);
				obj.put("type",type);

				System.out.println(obj.toString());

				result = HttpClientPoolUtil.execute(WebCommonData.CLASSIFER_URL,obj.toString());
				System.out.println(result);
//				service.dealMResult(bean,result);
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
		//TODO 测试
//		obj.put("title",WebCommonData.getIctclasByContent(title));
//		obj.put("content",WebCommonData.getIctclasByContent(content));
		//TODO 正式
		obj.put("title",PublicClass.noHTML(title));
		obj.put("content",PublicClass.noHTML(content));

		obj.put("mediaType","all_"+mediaType);
		obj.put("mediaBean",bean.getMedia());
		obj.put("orgId",WebCommonData.ORGID_STR);//限定机构
		obj.put("type",type);

		String result = "";
		String resultReturn = "";

		try{
			result = HttpClientPoolUtil.execute(WebCommonData.CLASSIFER_URL,obj.toString());
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
											extracWord = "m_"+monId+"_"+dealWords(keywordObj.getString("keyword"))+"_"+keywordObj.get("count");
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
												extracWord = "l_"+monId+"_"+dealWords(keywordObj.getString("keyword"))+"_"+keywordObj.get("count");
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
												extracWord = "w_"+monId+"_"+dealWords(keywordObj.getString("keyword"))+"_"+keywordObj.get("count");
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

	/**
	 * 处理规则词
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 15:25 2017/12/25
	 * @param words
	 */
	public static String dealWords(String words)throws Exception{
		//TODO 正式
		return words;
		//TODO 测试
		/*String reg="~[0-9]{1,}~";
		Pattern p = Pattern.compile(reg);
		Matcher m;
		List<String> list = new ArrayList<>();

		String result = "";
		if(!"".equals(StringUtil.toTrim(words))){
			m = p.matcher(StringUtil.toTrim(words));
			while (m.find()) {
				list.add(m.group());
			}
			if(list.size()>0){
				result = StringUtil.toTrim(words).replace("^","").replaceAll("~[0-9]{1,}~",dealWordSecond(list.get(0)));
			}else{
				result = StringUtil.toTrim(words).replace("^","");
			}
		}
		return result;*/
	}

	/**
	 * 处理临近词规则,根据分词规则将定义的临近词+4，比如~1~，改为~5~,代表 ^A^ ^C^ ^B^
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 16:14 2017/12/25
	 * @param word
	 */
	private static String dealWordSecond(String word)throws Exception{
		return "~"+(Integer.parseInt(word.replace("~",""))-4)+"~";
	}
}
