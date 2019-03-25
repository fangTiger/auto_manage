package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.bean.article.ArticleBean;
import com.xl.bean.article.FilterCondition;
import com.xl.bean.article.MediaBean;
import com.xl.bean.article.RuleFilterBean;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WebCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.PublicClass;
import com.xl.tool.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类处理
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class WebClassifierService {


	public static void main(String[] args) {
		String result = "";
		WebClassifierService service = new WebClassifierService();
		String title = "众泰再出神车，雷克萨斯失眠:该来的还是来了!";
		String content = "<p> <img src='http://05.imgmini.eastday.com/mobile/20171223/20171223191010_339569db926654f0eba260c1cbceb4f9_4.jpeg' /></p><p> 在外观上，新车比雷克萨斯差不了多少，甚至有盖过的意思，看起来似乎有一种跑车的气息。大嘴式进气格栅再配上大灯，看上去非常的犀利，画风十足。尾灯的设计非常亮眼，看起来异常的绚丽。其长宽高分别为4764/1942/1672mm，轴距为2850mm。</p><p> <img src='http://05.imgmini.eastday.com/mobile/20171223/20171223191010_339569db926654f0eba260c1cbceb4f9_5.jpeg' /></p><p> 内饰上，大迈X7S看起来非常舒适。原来新车在舒适性上下足功夫，一系列工艺做工精细，手感一流，质感非常强。让人感觉“车就是家，家就是车”。</p><p> <img src='http://05.imgmini.eastday.com/mobile/20171223/20171223191010_339569db926654f0eba260c1cbceb4f9_6.jpeg' /></p><p> 动力上，新车搭载1.8T涡轮增压发动机，163马力是它的最大功率，最大扭矩为245N·m。</p><p> <img src='http://05.imgmini.eastday.com/mobile/20171223/20171223191010_339569db926654f0eba260c1cbceb4f9_7.jpeg' /></p><p> 众泰的车型虽然有“山寨”的嫌疑，但是在市场的口碑和销量相当不错，相信这款大迈X7S也会受到人们的认可。</p>";
		String mediaType = "press_press";
		String type = "1";
		JSONObject obj = new JSONObject();
		ArticleBean bean = new ArticleBean();
		for (int i=0;i<1;i++){
			try{

				obj.put("title",PublicClass.StripHTML(title));
				obj.put("content",PublicClass.StripHTML(content));
				obj.put("mediaType",mediaType);
				obj.put("mediaBean",new MediaBean());
				obj.put("type",type);

				result = HttpClientPoolUtil.execute(WebCommonData.CLASSIFER_URL,obj.toString());
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
		obj.put("mediaType","all_"+mediaType);//TODO 正式
//		obj.put("mediaType","press_"+mediaType);//TODO 测试
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
