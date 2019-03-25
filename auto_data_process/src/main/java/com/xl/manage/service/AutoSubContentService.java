package com.xl.manage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.autoBean.SubjectRuleBean;
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
 * @Author:lww
 * @Date:17:06 2017/9/11
 */
public class AutoSubContentService {

	//信息源规则时间戳缓存
	public static long RULE_CONTENT_STAMP = 0;

	//信息源规则缓存
	public static Map<Integer,SubjectRuleBean> RULE_CONTENT_MAP = new HashMap<>();

	public static void main(String[] args) {
		JSONObject obj = new JSONObject();

		obj.put("title","东风雷诺前8月销量近5万 两新车将上市");
		obj.put("content","<p>  虽然东风雷诺成立的时间不长，但凭借科雷嘉、科雷傲两款SUV，在整体销量上一直处于稳步向前的水平。近日，网通社从东风雷诺官方获悉，两款国产车型在今年1-8月累计销量为46,888辆，完成全年销售目标将近8成。除此之外，雷诺旗下两款进口新车将在年内推向市场，其中新款卡缤将于10月正式上市，而另一款旗舰级MPV车型ESPACE也会在年底正式上市。</p><p>  <img src='http://img1.news18a.com/site/other/201709/ina_1504271869854168795_660.jpg' /></p><p>  <img src='http://img.news18a.com/image/auto/170410/lazyload660.jpg' /></p><p>  2017年1-8月，东风雷诺两款SUV累计销量达到46,888辆，其中，全新一代科雷傲累计销量为30,616辆，而另一款SUV科雷嘉累计销量则是达到了16,272辆。而在刚刚过去的8月份，东风雷诺共售出新车5,000辆，其中全新一代科雷傲售出1,567辆，科雷嘉售出3,433辆。</p><p>  东风雷诺在2017年计划完成“确保6万辆、挑战7万辆、争取8万辆”的全年销量目标。如若全年目标以6万辆计算，东风雷诺在今年前8月已完成全年销量目标的78.1%。依此态势，东风雷诺在2017年完成全年销售7万辆新车的“挑战”目标不是难事。</p><p>  <img src='http://img.news18a.com/image/auto/170410/lazyload660.jpg' /></p><p>  今年还有最后4个月时间，雷诺还将先后推出新款卡缤和ESPACE两款车型。其中，新款卡缤已于成都车展期间正式亮相，随后将会在今年10月正式上市；另一款雷诺旗舰级MPV车型ESPACE也有望在11月17日开幕的广州车展正式上市，而根据此前从国家工商行政管理总局商标局了解到的消息，新车中文名则有望会命名为“尊域”。（图/文 网通社 刘楠）</p>");
		obj.put("ruleUrl",AutoCommonData.getFormatRuleUrl(1,1));
		obj.put("stempUrl",AutoCommonData.getRuleStempUrl(1,1));
		System.out.println(obj.toJSONString());
		System.out.println(HttpClientPoolUtil.execute(AutoCommonData.CLASSIFIER_URL,obj.toString()));
	}
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
		List<SubjectRuleBean> contentList = new ArrayList<>();
		JSONObject obj = new JSONObject();
		JSONObject resultObj;
		try{
			initRule();

			obj.put("title",articleBean.getTitle());
			obj.put("content",articleBean.getContentText());
			obj.put("ruleUrl",AutoCommonData.getFormatRuleUrl(1,1));
			obj.put("stempUrl",AutoCommonData.getRuleStempUrl(1,1));

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
			articleBean.set_subjectContentList(contentList);
		}catch (Exception e){
			articleBean = null;
			LogHelper.error("------------活动内容规则处理出现异常 AutoEveContentService.getContentRule-------------------",e);
		}
		return articleBean;
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
		List<SubjectRuleBean> ruleArray;
		try{

			stamp = HttpClientPoolUtil.execute(AutoCommonData.getRuleStempUrl(0,1));
			stampLong = StringUtil.stringToLong(stamp);
			if(stampLong>RULE_CONTENT_STAMP){//更新规则
				rule = HttpClientPoolUtil.execute(AutoCommonData.getRuleUrl(1,1));
				if(!"".equals(rule)){
					ruleArray = JSONArray.parseArray(rule,SubjectRuleBean.class);
					RULE_CONTENT_MAP.clear();
					for(SubjectRuleBean bean:ruleArray){
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
	private List<SubjectRuleBean> getHitRule(String rule)throws Exception{

//		rule = "[{"classid":"_20_20_20","keywordDetail":[{"count":1,"keyword":"亮相"}],"weight":1},{"classid":"_4_4_4","keywordDetail":[{"count":1,"keyword":"成都车展"}],"weight":1}]";
		String classid;
		String keyword;
		JSONObject ruleObj;
		JSONObject keywordObj;
		JSONArray ruleArray;
		JSONArray keywordArray;

		SubjectRuleBean bean;
		List<SubjectRuleBean> list = new ArrayList<>();

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
