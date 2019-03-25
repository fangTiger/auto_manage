package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.MediaBean;
import com.xl.manage.bean.WeiboBean;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WeiboCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;
import com.xl.tool.WeiboDataHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分类处理
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class WeiboClassifierService {

	public static void main(String[] args) {
		String result = "";
		String resultReturn = "";

		String title = "如果有衣服上印着特立独行的猪我倒想买//＠马瑞莲·焖肉:果然是猴子 而且不酷";
		String content = "@财新视频 【服装被指种族歧视 南非H&M遭打砸抗议】近日，南非多家H&M服装连锁店遭到当地“经济自由战士”（EFF）成员打砸。这一举动是为抗议H&M带有种族歧视色彩的广告：一名黑肤色小男孩身着一件绿色帽衫，胸前印有“丛林里最酷的猴子”http://t.cn/RQf2LIq";
		String mediaType = "all_weibo";
		String type = "2";

		JSONObject obj = new JSONObject();

		String title1 = WeiboDataHelper.noWeiboUrl(StringUtil.toTrim(title));
		String content1 = WeiboDataHelper.noWeiboUrl(StringUtil.toTrim(content));
		System.out.println(title1);
		System.out.println(content1);
		for (int i=0;i<1;i++){
			obj.put("title",title1);
			obj.put("content",content1);
			obj.put("mediaType",mediaType);
			obj.put("orgId","");
			obj.put("mediaBean",new MediaBean());
			obj.put("type",type);

			try{
				result = HttpClientPoolUtil.execute(WeiboCommonData.CLASSIFER_URL,obj.toString());
			}catch (Exception e){
				e.printStackTrace();
			}
			System.out.println(result);
		}

	}

	/**
	 * 处理分类
	 * @return com.xl.manage.bean.WeiboBean
	 * @Author: lww
	 * @Description:
	 * @Date: 13:55 2017/11/15
	 * @param bean
	 * @param type
	 */
	public WeiboBean dealClass(WeiboBean bean, String type)throws Exception{

		String title = WeiboDataHelper.noWeiboUrl(StringUtil.toTrim(bean.getStatusText()));
		String content = WeiboDataHelper.noWeiboUrl(StringUtil.toTrim(bean.get_reStatusText()));
		String mediaType = bean.getType();

		JSONObject obj = new JSONObject();
		//TODO 测试
//		obj.put("title",WeiboCommonData.getIctclasByContent(title));
//		obj.put("content",WeiboCommonData.getIctclasByContent(content));
		//TODO 正式
		obj.put("title",title);
		obj.put("content",content);

		obj.put("mediaType","all_"+mediaType);
		obj.put("mediaBean",new MediaBean());
		obj.put("orgId",WeiboCommonData.ORGID_STR);//限定机构
		obj.put("type",type);

		String result;
		String resultReturn = "";

		try{
			result = HttpClientPoolUtil.execute(WeiboCommonData.CLASSIFER_URL,obj.toString());
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
	private WeiboBean dealUpdateInfo(WeiboBean bean)throws Exception{

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
			LogHelper.info("处理上传监测项AID["+bean.getStatusId()+"]："+monitors);
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
			LogHelper.info("处理上传标签AID["+bean.getStatusId()+"]："+lables);
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
	private String dealMResult(WeiboBean bean,String result)throws Exception{

		List<String> orgList = bean.getOrgs()!=null?bean.getOrgs():new ArrayList<>();
		List<String> orgflagList = bean.getMonitors()!=null?bean.getOrgsflag():new ArrayList<>();
		List<String> monList = bean.getMonitors()!=null?bean.getMonitors():new ArrayList<>();
		List<String> extracList = new ArrayList<>();

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
	private String dealTResult(WeiboBean bean,String result)throws Exception{

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
	private String dealWResult(WeiboBean bean,String result)throws Exception{

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
