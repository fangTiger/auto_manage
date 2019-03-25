package com.xl.irtv.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.bean.article.FilterCondition;
import com.xl.bean.article.RuleFilterBean;
import com.xl.bean.irtv.BroadcastBean;
import com.xl.bean.irtv.BroadcastMediaBean;
import com.xl.irtv.common.IrtvCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理分类
 * @Author:lww
 * @Date:15:13 2017/9/14
 */
public class IrtvClassProService {

	public static void main(String[] args) {
		String result = "";
		String resultReturn = "";

		String title = "奥迪R8后壳异响 刹车抖动刹不住";
		String content = "<br />关键词:奥迪R8";
		String mediaType = "all_broadcast";
		String type = "1";
		IrtvClassProService service = new IrtvClassProService();
		JSONObject obj = new JSONObject();

		for (int i=0;i<10;i++){
			obj.put("title","车型咨询：mkx罗密欧，雷克萨斯is,奔驰glc");
			obj.put("content","<br />关键词:奔驰");
			obj.put("mediaType",mediaType);
			obj.put("orgId",IrtvCommonData.ORGID_STR);//限定机构
			obj.put("mediaBean",new BroadcastMediaBean());
			obj.put("type",type);
			try{
				result = HttpClientPoolUtil.execute(IrtvCommonData.CLASSIFER_URL,obj.toString());
			}catch (Exception e){
				e.printStackTrace();
			}
			System.out.println(result);
		}
	}

	/**
	 * 打分类
	 * @return com.xl.irtv.bean.BroadcastBean
	 * @Author: lww
	 * @Description:
	 * @Date: 16:40 2018/1/15
	 * @param bean
	 * @param type
	 */
	public BroadcastBean dealClass(BroadcastBean bean, String type)throws Exception{

		String title = StringUtil.noHTML(StringUtil.toTrim(bean.getTitle()));
		String content = StringUtil.noHTML(StringUtil.toTrim(bean.getContentText()));
		String mediaType = bean.getType();

		JSONObject obj = new JSONObject();
		obj.put("title",title);
		obj.put("content",content);
		obj.put("mediaType","all_"+mediaType);
		obj.put("orgId",IrtvCommonData.ORGID_STR);//限定机构
		obj.put("mediaBean",bean.getMedia());
		obj.put("type",type);

		String result = "";
		String resultReturn = "";
		try{
			result = HttpClientPoolUtil.execute(IrtvCommonData.CLASSIFER_URL,obj.toString());
			if("1".equals(type)){
				resultReturn = dealMResult(bean,result);
			}else if("2".equals(type)){
				resultReturn = dealTResult(bean,result);
			}else if("3".equals(type)){
				resultReturn = dealWResult(bean,result);
			}

			if("1".equals(resultReturn)){//操作成功
				return bean;
			}else{
				return null;
			}
		}catch (Exception e){
			bean = null;
			LogHelper.error("------------获取分类出现异常！ IrtvClassProService.dealClass  [param:"+obj.toString()+"]--------",e);
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
	private String dealMResult(BroadcastBean bean,String result)throws Exception{

		List<String> orgList = new ArrayList<>();
		List<String> orgflagList = new ArrayList<>();
		List<String> monList = new ArrayList<>();
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
			LogHelper.error("------------解析分类结果出现异常！ IrtvClassProService.dealMClass  [param:"+result+"]--------",e);
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
	private String dealTResult(BroadcastBean bean,String result)throws Exception{

		List<String> orgList = bean.getOrgs();
		List<String> labList = new ArrayList<>();
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
				if("1".equals(StringUtil.toTrim(obj.get("code")+""))){
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
			LogHelper.error("------------解析分类结果出现异常！ IrtvClassProService.dealTClass  [param:"+result+"]--------",e);
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
	private String dealWResult(BroadcastBean bean,String result)throws Exception{

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
			LogHelper.error("------------解析分类结果出现异常！ IrtvClassProService.dealMClass  [param:"+result+"]--------",e);
		}
		return resultReturn;
	}
}
