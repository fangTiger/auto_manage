package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.ArticleBean;
import com.xl.manage.bean.MediaBean;
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
		String title = "再好的“防沉迷系统”也难戒网游之瘾";
		String content = "<p> 马化腾代表在两会上说，网游的防沉迷系统表现还不够好，建议爹妈跟孩子订立“数字契约”，比如家务、学习和户外活动与游戏时长挂钩。我的一位朋友对此嗤之以鼻，他上小学的孩子痴迷“王者荣耀”，斗争的惨痛经验让他坚信“数字契约”毫不现实。在他和很多家长看来，网游就是电子鸦片，理应一禁了之。</p><p> 流行的“王者荣耀”或者“吃鸡”，我没打过，不敢断定是不是电子鸦片。但回想二十年前，小孩们放学就杀向游戏厅投币，家长和学校最恨游戏厅，报纸上痛心疾首的论调于今如出一辙；2000年后社会又开始声讨网吧，2010年有位全国政协委员提议关闭所有网吧；如今智能手机来了，四成收入来自网游的腾讯变成千夫所指。</p><p> 余生也晚，不知道有电子游戏以前，青少年怎么消磨时光的，但我猜测所谓“玩物丧志”并非有了游戏机才演化出来的。既然轻松热闹的玩乐乃是人类天性，为何家长要训诫孩子远离网游呢？我想是人生还有一些不轻松但更高级的乐趣，家长不希望孩子在低级趣味的小宇宙里打转转。</p><p> 然而，教育资源的不平等，使得一些孩子比另一些更身处险境。近年一项美国的研究指出，低收入家庭的学生沉迷网络的时间是高收入家庭孩子的两倍。还有研究显示，父母亲疏于陪伴的孩子，更容易沉湎于虚拟世界。</p><p> 这也跟我的印象相符。我有个亲戚的孩子正上初中，爱打游戏更爱聊游戏，我只要把手机递给他，他能玩到不抬头不吃饭，但他打游戏的时间不多，原因是：他所在的名校课业繁重；他从小上才艺班，课外要体育集训还要排练节目，忙得没空打游戏；另外，他们同学之间除了游戏，有不少闲聊和比拼的话题。教育资源贫乏的孩子，未必有这一套“防沉迷系统”吧？</p><p> 最近有报道说，农村留守儿童沉迷网游现象严重，想来也是自然：他们身边还有其他榜样吗？能尽情蹦跶的空间，除了手机屏幕还有哪儿？前几年有报道说：四川乡村中小学半数没有专职体育老师；河南百分之八十乡村学校体育场地不达标。督促马化腾升级“防沉迷系统”之前，先解决这事儿吧。</p>";
		String mediaType = "press_press";
		String type = "1";
		JSONObject obj = new JSONObject();
		ArticleBean bean = new ArticleBean();
		MediaBean mediaBean = JSONObject.parseObject("{\"domain\":\"stdaily.com\",\"mediaArea\":{\"city\":\"北京市\",\"cityId\":\"110100\",\"country\":\"\",\"province\":\"北京\"},\"mediaCategory\":[\"科技\"],\"mediaId\":\"2726\",\"mediaNameCn\":\"科技日报(数字报)\",\"mediaNameEn\":\"Science and Technology Daily\",\"mediaType\":\"epaper\",\"site\":\"digitalpaper.stdaily.com\"}",MediaBean.class);
		for (int i=0;i<1;i++){
			try{

				obj.put("title",PublicClass.noHTML(title));
				obj.put("content",PublicClass.noHTML(content));
				obj.put("mediaType",mediaType);
				obj.put("mediaBean",mediaBean);
				obj.put("type",type);
				obj.put("orgId",CommonData.ORGID_STR);

				result = HttpClientPoolUtil.execute(CommonData.CLASSIFER_URL,obj.toString());
				System.out.println(result);
				service.dealMResult(bean,result);
				System.out.println(JSON.toJSONString(bean));
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
											extracWord = "m_"+monId+"_"+keywordObj.getString("keyword")+"_"+keywordObj.get("count");
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
