package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.bean.article.*;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WebCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.PublicClass;
import com.xl.tool.StringUtil;

/**
 * 情感值处理
 * @Author:lww
 * @Date:15:33 2017/9/21
 */
public class WebAffectionService {

	/**
	 * 处理情感值
	 * @return com.xl.tf.bean.TfBean
	 * @Author: lww
	 * @Description:
	 * @Date: 15:46 2017/9/21
	 * @param bean
	 */
	public ArticleBean dealAffection(ArticleBean bean){
		JSONObject obj;
		String result = "";
		String datas;
		JSONObject jsonObject1;
		try{
			if(!"".equals(StringUtil.toTrim(PublicClass.StripHTML(bean.getContentText())))){
				obj = new JSONObject();
				obj.put("title",bean.getTitle());
				obj.put("content",StringUtil.toTrim(PublicClass.StripHTML(bean.getContentText())));
				obj.put("mainObj","");
				obj.put("type","0");//走默认类型

				result = HttpClientPoolUtil.execute(WebCommonData.AFFECTION_URL,obj.toString());

				if(result!=null){
					JSONObject jsonObject = JSONObject.parseObject(result);
					if("1".equals(jsonObject.getString("code"))){//请求成功
						datas = jsonObject.getString("datas");
						jsonObject1 = JSON.parseObject(datas);
						bean.setEmotionValue((int)Double.parseDouble(jsonObject1.getString("tone")));
					}else{//访问接口失败
						bean = null;
						LogHelper.info("-----------------访问情感值接口出现异常！url:"+WebCommonData.AFFECTION_URL+";param:"+obj.toString()+"-------------------");
					}
				}else{//访问接口失败
					bean = null;
					LogHelper.info("-----------------访问情感值接口出现异常！url:"+WebCommonData.AFFECTION_URL+";param:"+obj.toString()+"-------------------");
				}
			}else{
				bean.setEmotionValue(0);
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取情感值", JSONObject.toJSONString(bean),"获取情感值出现异常！result:"+result,e);
			bean = null;
		}
		return bean;
	}

	public static void main(String[] args) {
		JSONObject obj;
		String result;
		String datas;
		String dataArray[];
		int emotionValue = 0;
		String content = "<p> <img src=\"//club2.autoimg.cn/album/g25/M07/1A/71/userphotos/2018/03/26/09/500_wKgHIlq4TeOAOHjJAAH_wUYfV_0599.jpg\">这是我\uECF3订单，验明正身<img src=\"//club2.autoimg.cn/album/g25/M05/1C/2E/userphotos/2018/03/26/09/500_ChcCr1q4TeOASm2gAAlEJd-kXhY187.jpg\">被拧滑丝\uECF3螺丝<img src=\"//club2.autoimg.cn/album/g25/M05/1A/71/userphotos/2018/03/26/09/500_wKgHIlq4TeOAE_ObAAGeAWfZ3QE137.jpg\">打客服电话，反正一句话就\uED9C管，爱咋咋滴换轮胎需谨慎啊，一条轮胎便宜个几\uEC40块，换个轴承几百块，还耽误事</p>";
		try{
			content = StringUtil.toTrim(PublicClass.StripHTML(content));
			obj = new JSONObject();
			obj.put("title","途虎养车，换轮胎不当操作把我螺丝拧滑丝，导致轴承报废拒不承认");
			obj.put("content",content);
			obj.put("mainObj","");
			obj.put("type","0");//走默认类型
			int num = 0;
			System.out.println(obj.toJSONString());
			while (num<1){
				result = HttpClientPoolUtil.execute(WebCommonData.AFFECTION_URL,obj.toString());
				System.out.println(result);
				JSONObject jsonObject = JSONObject.parseObject(result);

				if("1".equals(jsonObject.getString("code"))){//请求成功
					datas = jsonObject.getString("datas");
					JSONObject jsonObject1 = JSON.parseObject(datas);
					System.out.println(Integer.parseInt(jsonObject1.getString("tone")));
				}else{//访问接口失败
					System.out.println("访问接口失败!");
				}
				num++;
			}
		}catch (Exception e){

		}
	}
}
