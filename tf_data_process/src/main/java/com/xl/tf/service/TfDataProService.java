package com.xl.tf.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.EsUserBean;
import com.xl.basic.bean.OverseaEsBean;
import com.xl.tf.bean.DBean;
import com.xl.tf.bean.SqlUserBean;
import com.xl.tf.bean.TfBean;
import com.xl.tf.bean.TfUserBean;
import com.xl.tf.common.TfCommonData;
import com.xl.tool.DateHelper;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;

/**
 * tf数据处理
 * @Author:lww
 * @Date:18:34 2017/9/21
 */
public class TfDataProService {

	/**
	 * tf数据处理
	 * @return com.xl.tf.bean.TfBean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:25 2017/9/25
	 * @param oldBean
	 */
	public TfBean dealData(OverseaEsBean oldBean){

		TfBean bean;
		String result = "";
		try{

			bean = changeBean(oldBean);
			//TODO userBean 入库
			try{
				result = HttpClientPoolUtil.execute(TfCommonData.TF_USER_INSERT_URL,JSONObject.toJSONString(getSqlUserBean(oldBean.getU(),bean.getWeiboFrom())));
			}catch (Exception e){
				LogHelper.error("----------------------tf用户信息入库出现异常！！！！！！！result["+result+"]----------------------------------",e);
			}
		}catch (Exception e){
			bean = null;
			LogHelper.error("------------------tf数据处理出现异常！param:["+JSONObject.toJSONString(oldBean)+"]---------------------------",e);
		}

		return bean;
	}

	/**
	 * 转换为新的esBean
	 * @return com.xl.tf.bean.TfBean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:55 2017/9/21
	 * @param oldBean
	 */
	public static TfBean changeBean(OverseaEsBean oldBean)throws Exception{

		DBean dBean = new DBean();

		dBean.setCrawlTime(oldBean.getD().getCrawlTime());
		dBean.setDispTime(oldBean.getD().getDispTime());
		dBean.setIdate(oldBean.getD().getIdate());
		dBean.setPubDay(oldBean.getD().getPubDay());
		dBean.setPubHour(oldBean.getD().getPubHour());
		dBean.setPubMonth(oldBean.getD().getPubMonth());

		TfUserBean tfUserBean = new TfUserBean();
		tfUserBean.setUserId(oldBean.getU().getUserID());
		tfUserBean.setUserName(oldBean.getU().getUserName());
		tfUserBean.setUserScreenName(oldBean.getU().getUserScreenName());
		tfUserBean.setFavouritesCount(oldBean.getU().getFavouritesCount());
		tfUserBean.setFollowersCount(oldBean.getU().getFollowersCount());
		tfUserBean.setFriendsCount(oldBean.getU().getFriendsCount());
		tfUserBean.setLocation(oldBean.getU().getLocation());
		tfUserBean.setIsVerified(oldBean.getU().getIsVerified());
		tfUserBean.setProfileImageUrl(oldBean.getU().getProfileImageUrl());
		tfUserBean.setWeiboUrl(oldBean.getU().getRelativeUrl());//个人相关链接地址
		tfUserBean.setStatusesCount(oldBean.getU().getStatusesCount());

		TfBean bean = new TfBean();
		bean.setType(oldBean.getType());
		bean.setAttitudesCount(oldBean.getAttitudesCount());
		bean.setCommentCount(oldBean.getCommentCount());
		bean.setStatusId(oldBean.getStatusId());
		bean.setStatusText(oldBean.getStatusText());
		bean.setIsRetweeted(oldBean.getIsRetweeted());
		bean.setCommentCount(oldBean.getCommentCount());
		bean.setRetweetedCount(oldBean.getRetweetedCount());
		bean.setAttitudesCount(oldBean.getAttitudesCount());
		bean.setTextUrl(oldBean.getTextUrl());
		bean.setSource(oldBean.getSource());
		bean.setReStatusId(StringUtil.toTrim(oldBean.getReStatusID()));
		bean.setWeiboFrom(0);//twitter-5 facebook-6

		if("twitter".equals(oldBean.getType())){
			bean.setWeiboFrom(5);
		}else if("facebook".equals(oldBean.getType())){
			bean.setWeiboFrom(6);
		}

		bean.setLanguage(oldBean.getLang());
		bean.setD(dBean);
		bean.setU(tfUserBean);

		return bean;
	}

	/**
	 * 获取数据库用户对象
	 * @return com.xl.tf.bean.SqlUserBean
	 * @Author: lww
	 * @Description:
	 * @Date: 11:44 2017/10/12
	 * @param userBean
	 * @param weiboFrom
	 */
	private SqlUserBean getSqlUserBean(EsUserBean userBean, Integer weiboFrom){
		SqlUserBean bean = new SqlUserBean();

		bean.setCreatedAt(userBean.getCreatedAt());
		bean.setDescription(userBean.getDescription());
		bean.setFavouritesCount(userBean.getFavouritesCount());
		bean.setFollowersCount(userBean.getFollowersCount());
		bean.setFriendsCount(userBean.getFriendsCount());
		bean.setGroupId(userBean.getGroupId());
		bean.setIsVerified(userBean.getIsVerified());
		bean.setLang(userBean.getLang());
		bean.setLocation(userBean.getLocation());
		bean.setProfileImageUrl(userBean.getProfileImageUrl());
		bean.setRelativeUrl(userBean.getRelativeUrl());
		bean.setStatusesCount(userBean.getStatusesCount());
		bean.setTimeZone(userBean.getTimeZone());
		bean.setUserID(userBean.getUserID());
		bean.setUserName(userBean.getUserName());
		bean.setUserScreenName(userBean.getUserScreenName());
		bean.setUserUrl(userBean.getUserUrl());
		bean.setWeiboFrom(weiboFrom);
		return bean;
	}

}
