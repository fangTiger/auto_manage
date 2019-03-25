package com.xl.manage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.autoBean.EventRuleBean;
import com.xl.manage.bean.esBean.ArticleBean;
import com.xl.manage.common.AutoCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动内容规则处理
 * @Author:lww
 * @Date:17:05 2017/9/11
 */
public class AutoEveContentService {

	//信息源规则时间戳缓存
	public static long RULE_CONTENT_STAMP = 0;

	//信息源规则缓存
	public static Map<Integer,EventRuleBean> RULE_CONTENT_MAP = new HashMap<>();

	/**
	 * 获取内容规则
	 * @return com.xl.manage.bean.esBean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 18:20 2017/9/11
	 * @param articleBean
	 */
	public ArticleBean getContentRule(ArticleBean articleBean)throws Exception{

		String rule;//打上的规则
		List<EventRuleBean> contentList = new ArrayList<>();
		JSONObject obj = new JSONObject();
		JSONObject resultObj;
		try{

			initRule();

			obj.put("title",articleBean.getTitle());
			obj.put("content",articleBean.getContentText());
			obj.put("ruleUrl",AutoCommonData.getFormatRuleUrl(1,0));
			obj.put("stempUrl",AutoCommonData.getRuleStempUrl(1,0));

			rule = HttpClientPoolUtil.execute(AutoCommonData.CLASSIFIER_URL,obj.toString());
			resultObj = JSONObject.parseObject(rule);
			if("1".equals(resultObj.getString("code"))){
				contentList = getHitRule(resultObj.getString("datas"));
				if(contentList==null){
					return null;
				}
			}else{
				LogHelper.info("-------------获取规则接口调用出现异常！url:["+AutoCommonData.CLASSIFIER_URL+"] msg:["+rule+"]-------------------------");
				return null;
			}
			articleBean.set_eventContentList(contentList);
		}catch (Exception e){
			articleBean = null;
			LogHelper.error("------------活动内容规则处理出现异常 AutoEveContentService.getContentRule-------------------",e);
		}
		return articleBean;
	}

	public static void main(String[] args)throws Exception {
		AutoEveContentService service = new AutoEveContentService();
		service.initRule();
	}

	/**
	 * 初始化内容规则
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 16:43 2017/9/19
	 * @param
	 */
	private void initRule()throws Exception{

		long stampLong;
		String stamp;
		String rule;
		List<EventRuleBean> ruleArray;
		try{

			stamp = HttpClientPoolUtil.execute(AutoCommonData.getRuleStempUrl(0,0));
			stampLong = StringUtil.stringToLong(stamp);
			if(stampLong>RULE_CONTENT_STAMP){//更新规则
				rule = HttpClientPoolUtil.execute(AutoCommonData.getRuleUrl(1,0));
				if(!"".equals(rule)){
					ruleArray = JSONArray.parseArray(rule,EventRuleBean.class);
					RULE_CONTENT_MAP.clear();
					for(EventRuleBean bean:ruleArray){
						RULE_CONTENT_MAP.put(bean.getId(),bean);
					}
				}else{
					LogHelper.info("获取主题内容规则为空！");
				}
			}
		}catch (Exception e){
			LogHelper.error("-----------------主题内容规则处理出现异常AutoEveSourceService.getRuleList---------------------",e);
		}
	}

	/**
	 * 根据匹配上的规则获取规则对象
	 * @return java.util.List<com.xl.manage.bean.autoBean.EventRuleBean>
	 * @Author: lww
	 * @Description:
	 * @Date: 19:40 2017/9/11
	 * @param rule
	 */
	private List<EventRuleBean> getHitRule(String rule)throws Exception{
//		rule = "[{"classid":"_20_20_20","keywordDetail":[{"count":1,"keyword":"亮相"}],"weight":1},{"classid":"_4_4_4","keywordDetail":[{"count":1,"keyword":"成都车展"}],"weight":1}]";
		String classid;
		String keyword;
		JSONObject ruleObj;
		JSONObject keywordObj;
		JSONArray ruleArray;
		JSONArray keywordArray;

		EventRuleBean bean;
		List<EventRuleBean> list = new ArrayList<>();

		try{
			ruleArray = JSONArray.parseArray(rule);
			if(ruleArray!=null&&!ruleArray.isEmpty()){
				for(int i=0;i<ruleArray.size();i++){
					keyword = "NULL";
					ruleObj = ruleArray.getJSONObject(i);
					classid = ruleObj.getString("classid").substring(ruleObj.getString("classid").lastIndexOf("_")+1);
					bean = RULE_CONTENT_MAP.get(Integer.parseInt(classid));
					keywordArray = ruleObj.getJSONArray("keywordDetail");
					if(keywordArray!=null&&!keywordArray.isEmpty()){
						keyword = "";
						for(Object obj:keywordArray){
							keywordObj = JSONObject.parseObject(obj.toString());
							keyword += keywordObj.getString("keyword")+"_"+keywordObj.getInteger("count")+",";
						}
						if(keyword.endsWith(",")){
							keyword = keyword.substring(0,keyword.length()-1);
						}
					}
					bean.set_hitWord(keyword);
					list.add(bean);
				}
			}
		}catch (Exception e){
			LogHelper.error("-----------获取已匹配规则发生异常！AutoSubContentService.getHitRule-----------------",e);
		}
		return list;
	}

}
