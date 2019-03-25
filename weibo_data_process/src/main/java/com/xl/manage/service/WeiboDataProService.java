package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xl.basic.bean.WbOldBean;
import com.xl.basic.bean.WbUserBean;
import com.xl.manage.bean.DBean;
import com.xl.manage.bean.UBean;
import com.xl.manage.bean.WeiboBean;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WeiboCommonData;
import com.xl.tool.*;
import scala.annotation.meta.param;

import java.util.ArrayList;
import java.util.List;

/**
 * tf数据处理
 * @Author:lww
 * @Date:18:34 2017/9/21
 */
public class WeiboDataProService {

	/**
	 * weibo数据处理
	 * @return com.xl.tf.bean.TfBean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:25 2017/9/25
	 * @param oldBean
	 */
	public List<WeiboBean> dealData(WbOldBean oldBean){

		List<WeiboBean> list;
		String result = "";
		String provinceName;
		JSONObject obj;
		WeiboBean bean = null;
		WeiboBean weiboBean;
		WbUserBean userBean;
		int resultNum;
		List<WeiboBean> resultList = new ArrayList<>();
		try{

			if(oldBean.getWbuserbean()!=null&&!"".equals(StringUtil.toTrim(oldBean.getWbuserbean().getUserID()))){
				if(!"".equals(StringUtil.toTrim(oldBean.getWbuserbean().getLocation()))){
					provinceName = StringUtil.toTrim(oldBean.getWbuserbean().getLocation()).replace("-"," ").replace(","," ").split("\\s")[0];
					oldBean.getWbuserbean().setLocationArea(WeiboDataHelper.getProvince(provinceName));
				}else{
					provinceName = StringUtil.toTrim(oldBean.getWbstatusbean().getLocation()).replace("-"," ").replace(","," ").split("\\s")[0];
					oldBean.getWbuserbean().setLocationArea(WeiboDataHelper.getProvince(provinceName));
				}
			}else{
				provinceName = StringUtil.toTrim(oldBean.getWbstatusbean().getLocation()).replace("-"," ").replace(","," ").split("\\s")[0];
				oldBean.getWbuserbean().setLocationArea(WeiboDataHelper.getProvince(provinceName));
			}

			list = changeBean(oldBean);
			//TODO userBean入库 测试不需要
			try{
				oldBean.getWbuserbean().setRt(0);
				result = HttpClientPoolUtil.execute(WeiboCommonData.WEIBO_USER_INSERT_URL,JSONObject.toJSONString(oldBean.getWbuserbean(),SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
				obj = JSON.parseObject(result);
				if(!"0".equals(obj.getString("code"))){
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"微博用户入库", JSON.toJSONString(oldBean.getWbuserbean()),"微博用户入库失败！！"+result,new Exception("微博用户入库异常！"));
					return null;
				}else{
					if(list.size()>1){//当前数据为转发微博
						weiboBean = list.get(1);
						userBean = new WbUserBean();
						userBean.setLocationArea(weiboBean.getU().getLocationArea());
						userBean.setLocation(weiboBean.getU().getLocation());
						userBean.setDescription("");
						userBean.setFavouritesCount(weiboBean.getU().getFavouritesCount());
						userBean.setFollowersCount(weiboBean.getU().getFollowersCount());
						userBean.setFriendsCount(weiboBean.getU().getFriendsCount());
						userBean.setGender(weiboBean.getU().getGender());
						userBean.setIsFollow(0);
						userBean.setIsVerified(weiboBean.getU().getIsVerified()==0?false:true);
						userBean.setPersonalityName(weiboBean.getU().getUserScreenName());
						userBean.setPersonalUrl(weiboBean.getU().getWeiboUrl());
						userBean.setProfileImageUrl(weiboBean.getU().getProfileImageUrl());
						userBean.setRegisDate("");
						userBean.setRt(1);
						userBean.setStatusesCount(0);
						userBean.setStatusID(weiboBean.getStatusId());
						userBean.setUrl(weiboBean.getTextUrl());
						userBean.setUserID(weiboBean.getU().getUserId()!=null?weiboBean.getU().getUserId():"0");
						userBean.setUserName(weiboBean.getU().getUserName());
						userBean.setVerified("");
						userBean.setVerified_reason("");
						userBean.setWeiboFrom(weiboBean.getWeiboFrom());

						result = HttpClientPoolUtil.execute(WeiboCommonData.WEIBO_USER_INSERT_URL,JSONObject.toJSONString(userBean, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullStringAsEmpty));
						obj = JSON.parseObject(result);
						if(!"0".equals(obj.getString("code"))){
							LogHelper.error(LogCommonData.LOG_CODE_WEB,"微博被转发用户入库", JSON.toJSONString(oldBean.getWbuserbean()),"微博被转发用户入库失败！！"+result,new Exception("微博被转发用户入库异常！"));
							return null;
						}
					}
				}
			}catch (Exception e){
				LogHelper.error(LogCommonData.LOG_CODE_WEB,"微博用户入库", JSON.toJSONString(oldBean.getWbuserbean()),"微博用户入库异常！！"+result,new Exception("微博用户入库异常！"));
			}

			//TODO 测试
//			resultList.add(list.get(0));
			//TODO 正式
			resultNum = norepeat(list);
			if(resultNum==1){//数据重复
				bean = new WeiboBean();
				bean.setStatusId("1");
				resultList.add(bean);
			}else if(resultNum==0){//数据不重复
				bean = list.get(0);
				resultList.add(bean);
			}else{//异常！
				resultList = null;
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"数据格式化处理", JSON.toJSONString(oldBean),"数据格式化处理出现异常！",e);
			resultList = null;
		}
		return resultList;
	}

	/**
	 * 数据排重
	 * @return int
	 * @Author: lww
	 * @Description:
	 * @Date: 14:55 2018/1/10
	 * @param list
	 */
	private int norepeat(List<WeiboBean> list)throws Exception{

		WeiboBean bean;
		String result;
		JSONObject param = new JSONObject();
		JSONObject jsonObj;
		JSONObject jsonObject;
		int resultNum = 0;

		String updateParam = "{\"index\":null,\"aid\":\"AID_VALUE\",\"mediaType\":\"weibo\",\"fields\":[";
		String updateParam2 = "{\"name\":\"d.aquerTime\",\"value\":\"AQUER_VALUE\",\"relationOperator\":\"UPDATE\"}],\"indexType\":\"Weibo\"}";

		String commentCount = "{\"name\":\"commentCount\",\"value\":\"COMMENT_VALUE\",\"relationOperator\":\"UPDATE\"}";
		String retweetedCount = "{\"name\":\"retweetedCount\",\"value\":\"RETWEETED_VALUE\",\"relationOperator\":\"UPDATE\"}";
		String attitudesCount = "{\"name\":\"attitudesCount\",\"value\":\"ATTITUDES_VALUE\",\"relationOperator\":\"UPDATE\"}";

		try{
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					bean = list.get(i);
					if(!isRepeat(bean)){//当此数据不存在时
						if(i==1){
							param.put("url",bean.getTextUrl());
							param.put("monitorIds","");
							param.put("lableIds","");
							param.put("sourceType",8);
							param.put("status",0);
							param.put("userId",0);
							param.put("orgId",0);
							param.put("ewid",0);
							param.put("crawltype",400003);
							param.put("remarks","");

							result = HttpClientPoolUtil.execute(WeiboCommonData.WEIBO_RECRAWL_URL,param.toJSONString());

							jsonObject = JSONObject.parseObject(result);
							if(!"0".equals(StringUtil.toTrim(jsonObject.getString("code")))){
								LogHelper.error(LogCommonData.LOG_CODE_WEB,"微博被转发数据入二次采集", JSON.toJSONString(param),"微博被转发数据入二次采集失败！"+result,new Exception("调用微博二次采集接口异常！！！"));
							}
						}
					}else{//当此数据存在是
						if(i==0){
							resultNum = 1;//主数据已存在
						}else if(i==1){//若被转发数据已存在,更新互动值

							updateParam = updateParam.replace("AID_VALUE",bean.getStatusId());

							if(bean.getCommentCount()!=0){
								updateParam+=commentCount.replace("COMMENT_VALUE",bean.getCommentCount()+"")+",";
							}

							if(bean.getRetweetedCount()!=0){
								updateParam+=retweetedCount.replace("RETWEETED_VALUE",bean.getRetweetedCount()+"")+",";
							}

							if(bean.getAttitudesCount()!=0){
								updateParam+=attitudesCount.replace("ATTITUDES_VALUE",bean.getAttitudesCount()+"")+",";
							}

							updateParam+=updateParam2.replace("AQUER_VALUE",DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));

							if(bean.getCommentCount()!=0||bean.getRetweetedCount()!=0||bean.getAttitudesCount()!=0){

								jsonObj = new JSONObject();
								jsonObj.put("operationCode","weiboUpDPZ");
								jsonObj.put("datas", updateParam);
								jsonObj.put("tokenKey","");

								LogHelper.info("微博被转发数据更新互动值："+JSON.toJSONString(bean));

								result = HttpClientPoolUtil.execute(WeiboCommonData.ES_CACHE_URL,jsonObj.toString());
								if(!"".equals(StringUtil.toTrim(result))){
									jsonObject = JSONObject.parseObject(result);
									if(!"success".equals(jsonObject.getString("code"))){
										LogHelper.error(LogCommonData.LOG_CODE_WEB,"微博被转发数据更新互动值", JSON.toJSONString(jsonObj),"微博被转发数据更新互动值失败！"+result,new Exception("调用微博互动值更新接口出现异常！！！"));
									}else{
										LogHelper.info("微博被转发数据更新互动值成功："+jsonObj.toJSONString());
									}
								}
							}
						}
					}
				}
				return resultNum;
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"数据排重", JSON.toJSONString(list),"数据排重出现异常！",e);
		}
		return -1;
	}

	/*public static void main(String[] args) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("operationCode","weiboUpDPZ");
		jsonObj.put("datas","{\"index\":null,\"aid\":\"3766964722153999\",\"mediaType\":\"weibo\",\"fields\":[{\"name\":\"commentCount\",\"value\":\"1\",\"relationOperator\":\"UPDATE\"},{\"name\":\"retweetedCount\",\"value\":\"2\",\"relationOperator\":\"UPDATE\"},{\"name\":\"attitudesCount\",\"value\":\"0\",\"relationOperator\":\"UPDATE\"},{\"name\":\"d.aquerTime\",\"value\":\"2017-08-28T16:40:53\",\"relationOperator\":\"UPDATE\"}],\"indexType\":\"Weibo\"}");
		jsonObj.put("tokenKey","");

		String result = HttpClientPoolUtil.execute(WeiboCommonData.ES_CACHE_URL,jsonObj.toString());
		if(!"".equals(StringUtil.toTrim(result))){
			JSONObject jsonObject = JSONObject.parseObject(result);
			if(!"success".equals(jsonObject.getString("code"))){
				System.out.println("异常！！！！");
			}
		}
	}*/

	/**
	 * 判断排重库是有已存在 (true-存在 false-不存在)
	 * @return boolean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:37 2018/1/10
	 * @param bean
	 */
	private boolean isRepeat(WeiboBean bean){

		JSONObject jsonObject;
		JSONObject param = new JSONObject();

		String result;
		try{
			param.put("mediaType",bean.getType());
			param.put("dbKey",bean.getStatusId());
			param.put("url",StringUtil.toTrim(bean.getTextUrl()));

			result = HttpClientPoolUtil.execute(WeiboCommonData.DATA_NOREPEAT_URL,param.toJSONString());

			jsonObject = JSONObject.parseObject(result);
			if(jsonObject!=null){
				if("1".equals(StringUtil.toTrim(jsonObject.getString("code")))){
					if(!"".equals(StringUtil.toTrim(jsonObject.getString("datas")))){
						return true;
					}else{
						return false;
					}
				}else{
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"排重接口调用", JSON.toJSONString(bean),"调用排重接口失败！"+result,new Exception("数据排重接口异常！！"));
				}
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"排重接口调用", JSON.toJSONString(bean),"调用排重接口出现异常！",e);
		}
		return false;
	}

	/**
	 * 转换为新的esBean
	 * @return com.xl.tf.bean.TfBean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:55 2017/9/21
	 * @param oldBean
	 */
	private List<WeiboBean> changeBean(WbOldBean oldBean)throws Exception{

		List<WeiboBean> list = new ArrayList<>();

		DBean dBean = new DBean();

		dBean.setDispTime(StringUtil.toTrim(oldBean.getWbstatusbean().getPubTime()).length()>0?DateHelper.formatDateString(StringUtil.toTrim(oldBean.getWbstatusbean().getPubTime()),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_DATETIME):DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		dBean.setPubDay(StringUtil.toTrim(oldBean.getWbstatusbean().getPubTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getWbstatusbean().getPubTime()),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_DD)):0);
		dBean.setPubHour(StringUtil.toTrim(oldBean.getWbstatusbean().getPubTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getWbstatusbean().getPubTime()),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_HH)):0);
		dBean.setPubMonth(StringUtil.toTrim(oldBean.getWbstatusbean().getPubTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getWbstatusbean().getPubTime()),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_MM)):0);
		dBean.setCrawlTime(StringUtil.toTrim(oldBean.getWbstatusbean().getCreateTime()).length()>0?DateHelper.formatDateString(StringUtil.toTrim(oldBean.getWbstatusbean().getCreateTime()),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_DATETIME):DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		dBean.setCreateDate(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));

		UBean userBean = new UBean();
		if(oldBean.getWbuserbean()!=null&&!"".equals(StringUtil.toTrim(oldBean.getWbuserbean().getUserID()))){
			userBean.setUserId(StringUtil.toTrim(oldBean.getWbuserbean().getUserID()).length()>0?StringUtil.toTrim(oldBean.getWbuserbean().getUserID()):"");
			userBean.setUserName(StringUtil.toTrim(oldBean.getWbuserbean().getUserName()).length()>0?StringUtil.toTrim(oldBean.getWbuserbean().getUserName()):"");
			userBean.setUserScreenName(StringUtil.toTrim(oldBean.getWbuserbean().getPersonalityName()).length()>0?StringUtil.toTrim(oldBean.getWbuserbean().getPersonalityName()):StringUtil.toTrim(oldBean.getWbuserbean().getUserName()));
			userBean.setFavouritesCount(oldBean.getWbuserbean().getFavouritesCount()!=null?oldBean.getWbuserbean().getFavouritesCount():0);
			userBean.setFollowersCount(oldBean.getWbuserbean().getFollowersCount()!=null?oldBean.getWbuserbean().getFollowersCount():0);
			userBean.setFriendsCount(oldBean.getWbuserbean().getFriendsCount()!=null?oldBean.getWbuserbean().getFriendsCount():0);
			userBean.setLocation(StringUtil.toTrim(oldBean.getWbuserbean().getLocation()).length()>0?StringUtil.toTrim(oldBean.getWbuserbean().getLocation()):"");
			userBean.setLocationArea(StringUtil.toTrim(oldBean.getWbuserbean().getLocationArea()).length()>0?StringUtil.toTrim(oldBean.getWbuserbean().getLocationArea()):"");
			userBean.setIsVerified(oldBean.getWbuserbean().isVerified()?1:0);
			userBean.setProfileImageUrl(StringUtil.toTrim(oldBean.getWbuserbean().getProfileImageUrl()).length()>0?StringUtil.toTrim(oldBean.getWbuserbean().getProfileImageUrl()):"");
			userBean.setWeiboUrl(StringUtil.toTrim(oldBean.getWbuserbean().getUrl()).length()>0?StringUtil.toTrim(oldBean.getWbuserbean().getUrl()):"");//个人相关链接地址
			userBean.setStatusesCount(oldBean.getWbuserbean().getStatusesCount()!=null?oldBean.getWbuserbean().getStatusesCount():0);
			userBean.setGender(oldBean.getWbuserbean().getGender());
			//若转换失败默认给0
			try{
				userBean.setVerified(Integer.parseInt(StringUtil.toTrim(oldBean.getWbuserbean().getVerified())));
			}catch (Exception e){
				userBean.setVerified(0);
			}
		}

		WeiboBean bean = new WeiboBean();
		bean.setType("weibo");
		bean.setAttitudesCount(oldBean.getWbstatusbean().getAttitudesCount()!=null?oldBean.getWbstatusbean().getAttitudesCount():0);
		bean.setCommentCount(oldBean.getWbstatusbean().getCommentCount()!=null?oldBean.getWbstatusbean().getCommentCount():0);
		bean.setStatusId(StringUtil.toTrim(oldBean.getWbstatusbean().getStatusID()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getStatusID()):"");
		bean.setStatusText(StringUtil.toTrim(oldBean.getWbstatusbean().getStatusText()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getStatusText()):"");
		bean.setIsRetweeted(oldBean.getWbstatusbean().isRetweeted()?1:0);
		bean.setCommentCount(oldBean.getWbstatusbean().getCommentCount());
		bean.setRetweetedCount(oldBean.getWbstatusbean().getRetweetedCount());
		bean.setAttitudesCount(oldBean.getWbstatusbean().getAttitudesCount());
		bean.setTextUrl(oldBean.getWbstatusbean().getTextUrl());
		bean.setSource(oldBean.getWbstatusbean().getSource());
		bean.setReStatusId(oldBean.getWbstatusbean().getReStatusID());
		bean.setWeiboFrom(oldBean.getWbstatusbean().getWeiboFrom());
		bean.setLanguage("");
		bean.setD(dBean);
		bean.setU(userBean);
		bean.setConversation(WeiboDataHelper.getConversations(StringUtil.toTrim(oldBean.getWbstatusbean().getStatusText())));
		bean.setCrawlType(StringUtil.toTrim(oldBean.getWbstatusbean().getCrawlType().toString()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getCrawlType().toString()):"0");

		if("".equals(StringUtil.toTrim(bean.getU().getUserId()))){
			bean.getU().setUserId(StringUtil.toTrim(oldBean.getWbstatusbean().getUserID()));
		}

		if(bean.getU().getIsVerified()==0){
			bean.getU().setIsVerified(oldBean.getWbstatusbean().isVerified()?1:0);
		}

		if("".equals(StringUtil.toTrim(bean.getU().getUserName()))){
			bean.getU().setUserName(StringUtil.toTrim(oldBean.getWbstatusbean().getUserName()));
		}

		if("".equals(StringUtil.toTrim(bean.getU().getUserScreenName()))){
			bean.getU().setUserScreenName(StringUtil.toTrim(oldBean.getWbstatusbean().getUserScreenName()));
		}

		if("".equals(StringUtil.toTrim(bean.getU().getLocation()))){
			bean.getU().setLocation(StringUtil.toTrim(oldBean.getWbstatusbean().getLocation()));
		}

		if("".equals(StringUtil.toTrim(bean.getU().getProfileImageUrl()))){
			bean.getU().setProfileImageUrl(StringUtil.toTrim(oldBean.getWbstatusbean().getProfileImageUrl()));
		}

		if("".equals(StringUtil.toTrim(bean.getU().getWeiboUrl()))){
			bean.getU().setWeiboUrl(StringUtil.toTrim(oldBean.getWbstatusbean().getWeiboUrl()));
		}

		if(bean.getU().getFollowersCount()==0){
			bean.getU().setFollowersCount(oldBean.getWbstatusbean().getFollowersCount());
		}

		if(bean.getU().getStatusesCount()==0){
			bean.getU().setStatusesCount(oldBean.getWbstatusbean().getStatusesCount());
		}

		if(bean.getU().getFavouritesCount()==0){
			bean.getU().setFavouritesCount(oldBean.getWbstatusbean().getFavouritesCount());
		}

		if(bean.getU().getFriendsCount()==0){
			bean.getU().setFriendsCount(oldBean.getWbstatusbean().getFriendsCount());
		}

		//上传数据携带的监测项和标签
		bean.set_monitorIds(StringUtil.toTrim(oldBean.getWbstatusbean().getMonitorIds()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getMonitorIds()):"");
		bean.set_lableIds(StringUtil.toTrim(oldBean.getWbstatusbean().getLableIds()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getLableIds()):"");

		list.add(bean);

		if(bean.getIsRetweeted()==1){//若是转发需要取转发的正文
			bean.set_reStatusText(StringUtil.toTrim(oldBean.getWbstatusbean().getReStatusText()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getReStatusText()):"");
			WeiboBean oldWeiboBean = getReBean(oldBean);
			if(!"".equals(StringUtil.toTrim(oldWeiboBean.getStatusId()))||!"".equals(StringUtil.toTrim(oldWeiboBean.getTextUrl()))){
				list.add(oldWeiboBean);//取出被转发微博
			}
			/*else{
				LogHelper.error(LogCommonData.LOG_CODE_WEB,"文章参数异常",JSON.toJSONString(oldBean),"被转发文章信息为空！",new Exception("被转发文章信息为空！"));
			}*/
		}
		return list;
	}

	/**
	 * 获取转发微博
	 * @return com.xl.manage.bean.WeiboBean
	 * @Author: lww
	 * @Description:
	 * @Date: 17:47 2017/11/15
	 * @param oldBean
	 */
	private WeiboBean getReBean(WbOldBean oldBean)throws Exception{

		DBean dBean = new DBean();
		dBean.setDispTime(StringUtil.toTrim(oldBean.getWbstatusbean().getRePubTime()).length()>0?DateHelper.formatDateString(StringUtil.toTrim(oldBean.getWbstatusbean().getRePubTime()),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_DATETIME):DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		dBean.setPubDay(StringUtil.toTrim(oldBean.getWbstatusbean().getRePubTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getWbstatusbean().getRePubTime()),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_DD)):0);
		dBean.setPubHour(StringUtil.toTrim(oldBean.getWbstatusbean().getRePubTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getWbstatusbean().getRePubTime()),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_HH)):0);
		dBean.setPubMonth(StringUtil.toTrim(oldBean.getWbstatusbean().getRePubTime()).length()>0?Integer.parseInt(DateHelper.formatDateString(StringUtil.toTrim(oldBean.getWbstatusbean().getRePubTime()),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_MM)):0);
		dBean.setCrawlTime(StringUtil.toTrim(oldBean.getWbstatusbean().getCreateTime()).length()>0?DateHelper.formatDateString(StringUtil.toTrim(oldBean.getWbstatusbean().getCreateTime()),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_DATETIME):DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
		dBean.setCreateDate(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));

		UBean userBean = new UBean();
		userBean.setUserId(StringUtil.toTrim(oldBean.getWbstatusbean().getReUserID()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getReUserID()):"");
		userBean.setUserName(StringUtil.toTrim(oldBean.getWbstatusbean().getReUserName()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getReUserName()):"");
		userBean.setUserScreenName(StringUtil.toTrim(oldBean.getWbstatusbean().getReUserScreenName()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getReUserScreenName()):StringUtil.toTrim(oldBean.getWbuserbean().getUserName()));
		userBean.setFavouritesCount(0);
		userBean.setFollowersCount(0);
		userBean.setFriendsCount(0);
		userBean.setLocation("");
		userBean.setLocationArea("");
		userBean.setIsVerified(0);
		userBean.setProfileImageUrl("");
		userBean.setWeiboUrl("");//个人相关链接地址
		userBean.setStatusesCount(0);
		userBean.setGender("");
		userBean.setVerified(0);

		WeiboBean bean = new WeiboBean();
		bean.setType("weibo");
		bean.setCommentCount(oldBean.getWbstatusbean().getReCommentCount()!=null?oldBean.getWbstatusbean().getReCommentCount():0);
		bean.setStatusId(StringUtil.toTrim(oldBean.getWbstatusbean().getReStatusID()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getReStatusID()):"");
		bean.setStatusText(StringUtil.toTrim(oldBean.getWbstatusbean().getReStatusText()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getReStatusText()):"");
		bean.setIsRetweeted(0);
		bean.setCommentCount(oldBean.getWbstatusbean().getReCommentCount());
		bean.setRetweetedCount(oldBean.getWbstatusbean().getReRetweetedCount());
		bean.setAttitudesCount(oldBean.getWbstatusbean().getReAttitudesCount());
		bean.setTextUrl(oldBean.getWbstatusbean().getReTextUrl());
		bean.setSource(oldBean.getWbstatusbean().getReSource());
		bean.setReStatusId("");
		bean.setWeiboFrom(oldBean.getWbstatusbean().getWeiboFrom());
		bean.setLanguage("");
		bean.setD(dBean);
		bean.setU(userBean);
		bean.setConversation(WeiboDataHelper.getConversations(StringUtil.toTrim(oldBean.getWbstatusbean().getReStatusText())));
		bean.setCrawlType(StringUtil.toTrim(oldBean.getWbstatusbean().getCrawlType().toString()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getCrawlType().toString()):"0");

		//上传数据携带的监测项和标签
		bean.set_monitorIds(StringUtil.toTrim(oldBean.getWbstatusbean().getMonitorIds()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getMonitorIds()):"");
		bean.set_lableIds(StringUtil.toTrim(oldBean.getWbstatusbean().getLableIds()).length()>0?StringUtil.toTrim(oldBean.getWbstatusbean().getLableIds()):"");
		return bean;
	}
}
