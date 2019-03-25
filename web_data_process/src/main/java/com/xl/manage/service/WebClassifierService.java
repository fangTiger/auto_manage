package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.ArticleBean;
import com.xl.manage.bean.MediaBean;
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
		String title = "美国福特和德国博世汽车被控尾气排放造假";
		String content = "<p> 原标题：美国福特和德国博世汽车被控尾气排放造假</p><p> 美国福特汽车公司以及为其供应汽车零部件的德国博世集团被指控合谋进行尾气排放造假，1月10日被诉至美国密歇根东区联邦地区法院。</p><p> 负责代理这起集体诉讼的美国哈根斯－伯曼律师事务所在诉状中称，两被告故意为2011年至2017年生产的福特Ｆ－250和Ｆ－350柴油版皮卡车安装尾气排放造假软件，通过车检后却在实际行驶时排放最多高出标准50倍的有害尾气，主要是氮氧化物。但福特公司却宣称，涉事车辆代表了“成熟的技术”和福特“创新的战略”，是“最清洁的柴油车”。</p><p> 哈根斯－伯曼律师事务所曾代理了针对菲亚特克莱斯勒、奔驰、通用和大众等多家知名车企的尾气排放案。这家律师事务所称，上述数据是他们通过独立检测得出的结果。消费者平均比同款汽油车多花8400美元，来购买福特柴油版皮卡车，却不知存在尾气造假问题，因此提起集体诉讼。</p><p> 原告代理律师指责被告违反了涉及尾气排放、消费者权益、广告营销等58项法律法规。</p><p> 据彭博新闻社报道，福特公司回应说，这些指控是“毫无依据”的，并表示将为自己辩护。博世集团则表示高度重视有关尾气排放造假软件的指控，但将不会就正在调查和诉讼中的事项做进一步评论。</p><p> 来源：新华网</p><p> 以上内容转载自其他媒体，目的在于传播更多信息，转载内容并不代表（www.d1ev.com）立场。返回搜狐，查看更多</p><p> 责任编辑：</p>";
		String mediaType = "all_web";
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
				obj.put("orgId",WebCommonData.ORGID_STR);//限定机构

				System.out.println(obj.toJSONString());

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

		String title = PublicClass.noHTML(bean.getTitle());
		String content = PublicClass.noHTML(bean.getContentText());
		String mediaType = bean.getType();

		JSONObject obj = new JSONObject();


		//TODO 测试
		obj.put("title",WebCommonData.getIctclasByContent(title));
		obj.put("content",WebCommonData.getIctclasByContent(content));

		//TODO 正式
//		obj.put("title",title);
//		obj.put("content",content);

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
//				bean = dealUpdateInfo(bean);
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
//		return words;

		//TODO 测试
		String reg="~[0-9]{1,}~";
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
		return result;
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
