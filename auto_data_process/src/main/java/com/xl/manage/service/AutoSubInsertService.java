package com.xl.manage.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.autoBean.EventArticleBean;
import com.xl.manage.bean.autoBean.SubjectArticleBean;
import com.xl.manage.bean.autoBean.SubjectRuleBean;
import com.xl.manage.bean.esBean.ArticleBean;
import com.xl.manage.common.AutoCommonData;
import com.xl.tool.DateHelper;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lww
 * @Date:14:21 2017/9/19
 */
public class AutoSubInsertService {

	/**
	 * 数据入库
	 * @return boolean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:30 2017/9/19
	 * @param bean
	 */
	public boolean insertBean(ArticleBean bean)throws Exception{

		List<EventArticleBean> eventList;
		List<SubjectArticleBean> subjectList;
		JSONObject obj;

		String result = "";
		try{
			subjectList = this.getSubjectData(bean);
			if(subjectList==null){
				return false;//处理失败
			}else{

				if(!subjectList.isEmpty()){
					obj = new JSONObject();
					obj.put("type","2");
					obj.put("subjectList",subjectList);
					//调用入库接口API
					result = HttpClientPoolUtil.execute(AutoCommonData.AUTO_ARTICLE_INSERT_URL,obj.toString());
				}

				if("1".equals(result)||"".equals(result)){
					return true;
				}else{
					return false;
				}
			}
		}catch (Exception e){
			LogHelper.error("---------------主题数据入库数据出现异常！AutoSubInsertService.insertBean--------------------",e);
			return false;
		}
	}

	/**
	 * 获取入库数据
	 * @return com.alibaba.fastjson.JSONObject
	 * @Author: lww
	 * @Description:
	 * @Date: 15:21 2017/9/19
	 * @param bean
	 */
	private List<SubjectArticleBean> getSubjectData(ArticleBean bean)throws Exception{
		List<SubjectArticleBean> list = new ArrayList<>();

		SubjectRuleBean ruleBean;
		SubjectArticleBean articleBean;
		List<SubjectRuleBean> subjectSourceList;
		List<SubjectRuleBean> subjectContentList;

		List<Integer> reId = new ArrayList<>();//排重
		try{
			subjectSourceList = bean.get_subjectSourceList();
			subjectContentList = bean.get_subjectContentList();

			if(subjectSourceList!=null&&!subjectSourceList.isEmpty()){
				for(SubjectRuleBean sourceRuleBean:subjectSourceList){
					ruleBean = null;
					if(subjectContentList!=null&&!subjectContentList.isEmpty()){
						for(SubjectRuleBean contentRuleBean:subjectContentList){
							if(sourceRuleBean.getId().intValue()==contentRuleBean.getId().intValue()){

								ruleBean = contentRuleBean;
								break;
							}
						}
					}
					reId.add(sourceRuleBean.getId());
					//获取转换对象
					articleBean = getSubjectBean(bean,sourceRuleBean,ruleBean);
					list.add(articleBean);
				}
			}

			if(subjectContentList!=null&&!subjectContentList.isEmpty()){
				for(SubjectRuleBean contentRuleBean:subjectContentList){
					if(!reId.contains(contentRuleBean.getId())){
						articleBean = getSubjectBean(bean,null,contentRuleBean);
						list.add(articleBean);
					}
				}
			}
		}catch (Exception e){
			list = null;
			LogHelper.error("---------------获取主题入库数据出现异常！AutoSubInsertService.getSubjectData--------------------",e);
		}
		return list;
	}

	/**
	 * 获取主题文章对象
	 * @return com.xl.manage.bean.autoBean.EventArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 17:27 2017/9/19
	 * @param bean
	 * @param sourceRuleBean
	 * @param contentRuleBean
	 */
	private SubjectArticleBean getSubjectBean(ArticleBean bean, SubjectRuleBean sourceRuleBean, SubjectRuleBean contentRuleBean)throws Exception{

		SubjectArticleBean articleBean = new SubjectArticleBean();
		articleBean.setAid(Long.parseLong(bean.getAid()));
		articleBean.setIndexSig(bean.getSigs().getIndexSig());
		articleBean.setIndexSigAll(bean.getSigs().getIndexSigall());
		articleBean.setSig(bean.getSigs().getSig());
		articleBean.setSigAll(bean.getSigs().getSigall());
		articleBean.setSourceType(this.getSourceType(bean.getType()));
		articleBean.setDisptime(bean.getD().getDispTime().replace("T"," "));
		articleBean.setCreateTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		articleBean.setCreateUser(0);
		if(sourceRuleBean!=null){
			articleBean.setSubjectId(sourceRuleBean.getSubjectId());
			if(contentRuleBean!=null){
				articleBean.setTargetRuler(sourceRuleBean.get_hitWord()+"|"+contentRuleBean.get_hitWord());
			}else{
				articleBean.setTargetRuler(sourceRuleBean.get_hitWord()+"|NULL");
			}
		}else if(contentRuleBean!=null){
			articleBean.setSubjectId(contentRuleBean.getSubjectId());
			articleBean.setTargetRuler("NULL|"+contentRuleBean.get_hitWord());
		}
		return articleBean;
	}

	/**
	 * 获取媒体类型
	 * @return int
	 * @Author: lww
	 * @Description:
	 * @Date: 15:36 2017/9/19
	 * @param mediaType
	 */
	private int getSourceType(String mediaType)throws Exception{

		//文章类型(1.网络新闻-web 2.报刊-Press 3.论坛-BBS 8.微博-weibo 15.APP-APP 16.微信-Weixin)
		if("web".equals(mediaType)){
			return 1;
		}else if("press".equals(StringUtil.toTrim(mediaType).toLowerCase())){
			return 2;
		}else if("bbs".equals(StringUtil.toTrim(mediaType).toLowerCase())){
			return 3;
		}else if("weibo".equals(StringUtil.toTrim(mediaType).toLowerCase())){
			return 8;
		}else if("app".equals(StringUtil.toTrim(mediaType).toLowerCase())){
			return 15;
		}else if("weixin".equals(StringUtil.toTrim(mediaType).toLowerCase())){
			return 16;
		}else{
			return 0;
		}
	}

}
