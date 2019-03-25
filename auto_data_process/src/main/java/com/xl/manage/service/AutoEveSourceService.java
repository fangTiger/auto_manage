package com.xl.manage.service;

import com.alibaba.fastjson.JSONArray;
import com.xl.manage.bean.autoBean.EventRuleBean;
import com.xl.manage.bean.esBean.ArticleBean;
import com.xl.manage.common.AutoCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动信息源规则处理
 * @Author:lww
 * @Date:17:06 2017/9/11
 */
public class AutoEveSourceService {

	//信息源规则时间戳缓存
	public static long RULE_SOURCE_STAMP = 0;

	//信息源规则缓存
	public static List<EventRuleBean> RULE_SOURCE_LIST = new ArrayList<>();

	/**
	 * 获取信息源规则
	 * @return com.xl.manage.bean.esBean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 18:20 2017/9/11
	 * @param articleBean
	 */
	public ArticleBean getSourceRule(ArticleBean articleBean)throws Exception{

		String[] ruleArrays;

		List<EventRuleBean> ruleList = new ArrayList<>();
		List<EventRuleBean> list = getRuleList();
		if(list!=null&&!list.isEmpty()){
			for(EventRuleBean ruleBean:list){
				if(!"".equals(StringUtil.toTrim(ruleBean.getSourceRule()))){
					ruleArrays = StringUtil.toTrim(ruleBean.getSourceRule()).split(",");
					for(String rule:ruleArrays){
						if(rule.startsWith("\"")&&rule.endsWith("\"")){
							//英文双引号代表全部匹配
							if(articleBean.getNavigator().equals(rule.substring(1,ruleBean.getSourceRule().length()-1))){
								ruleBean.set_hitWord(rule);
								ruleList.add(ruleBean);
								break;
							}
						}else{
							if(articleBean.getNavigator().indexOf(rule)>-1){
								ruleBean.set_hitWord(rule);
								ruleList.add(ruleBean);
								break;
							}
						}
					}
				}
			}
			articleBean.set_eventSourceList(ruleList);
		}
		return articleBean;
	}

	/**
	 * 获取信息源规则列表
	 * @return java.util.List<com.xl.manage.bean.autoBean.EventRuleBean>
	 * @Author: lww
	 * @Description:
	 * @Date: 17:54 2017/9/11
	 * @param
	 */
	private List<EventRuleBean> getRuleList()throws Exception{

		long stampLong;
		String stamp;
		String rule;
		List<EventRuleBean> ruleArray;
		try{

			stamp = HttpClientPoolUtil.execute(AutoCommonData.getRuleStempUrl(0,0));//TODO 调用信息源规则时间戳API
			stampLong = StringUtil.stringToLong(stamp);
			if(stampLong>RULE_SOURCE_STAMP){//更新规则

				rule = HttpClientPoolUtil.execute(AutoCommonData.getRuleUrl(0,0));//TODO 调用信息源规则API
				if(!"".equals(rule)){
					ruleArray = JSONArray.parseArray(rule,EventRuleBean.class);
					RULE_SOURCE_LIST.clear();
					RULE_SOURCE_LIST.addAll(ruleArray);
				}else{
					LogHelper.info("获取信息源规则为空！");
					ruleArray = RULE_SOURCE_LIST;
				}
			}else{
				ruleArray = RULE_SOURCE_LIST;
			}
		}catch (Exception e){
			ruleArray = RULE_SOURCE_LIST;
			LogHelper.error("-----------------信息源规则处理出现异常AutoEveSourceService.getRuleList---------------------",e);
		}

		return ruleArray;
	}

	public static void main(String[] args) {
		System.out.println(HttpClientPoolUtil.execute(AutoCommonData.getRuleStempUrl(0,0)));
	}
}
