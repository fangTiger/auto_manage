package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.ArticleBean;
import com.xl.manage.common.CommonData;
import com.xl.manage.common.LogCommonData;
import com.xl.tools.HttpClientPoolUtil;
import com.xl.tools.LogHelper;
import com.xl.tools.PublicClass;
import com.xl.tools.StringUtil;

/**
 * 情感值处理
 * @Author:lww
 * @Date:15:33 2017/9/21
 */
public class AffectionService {

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
		String result;
		String datas;
		JSONObject jsonObject1;
		try{
			if(!"".equals(StringUtil.toTrim(PublicClass.StripHTML(bean.getContentText())))){
				obj = new JSONObject();
				obj.put("title",bean.getTitle());
				obj.put("content",StringUtil.toTrim(PublicClass.StripHTML(bean.getContentText())));
				obj.put("mainObj","");
				obj.put("type","0");//走默认类型

				result = HttpClientPoolUtil.execute(CommonData.AFFECTION_URL,obj.toString());

				JSONObject jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
					datas = jsonObject.getString("datas");
					jsonObject1 = JSON.parseObject(datas);
					bean.setEmotionValue((int)Double.parseDouble(jsonObject1.getString("tone")));
				}else{//访问接口失败
					bean = null;
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"访问情感值接口", JSONObject.toJSONString(bean),"访问情感值接口失败！"+result,new Exception("情感值接口访问失败!"));
				}
			}else{
				bean.setEmotionValue(0);
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"获取情感值", JSONObject.toJSONString(bean),"获取情感值出现异常！",e);
			bean = null;
		}
		return bean;
	}

	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				AffectionService service = new AffectionService();
				service.test();
			}
		});

		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				AffectionService service = new AffectionService();
				service.test();
			}
		});

		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				AffectionService service = new AffectionService();
				service.test();
			}
		});

		Thread thread4 = new Thread(new Runnable() {
			@Override
			public void run() {
				AffectionService service = new AffectionService();
				service.test();
			}
		});

		thread.start();
//		thread2.start();
//		thread3.start();
//		thread4.start();

	}

	private void test(){
		JSONObject obj;
		String result;
		String datas;
		String dataArray[];
		int emotionValue = 0;
		String content = "<p> 原标题：69所中小学幼儿园获评首批卫生示范校</p><p> 本报讯</p><p> (记者 匡丽娜)重庆69所中小学幼儿园获评首批“重庆市中小学卫生示范学校”，这是重庆日报记者11月10日从重庆市中小学健康学校/卫生示范学校创建工作推进会上获得的消息。</p><p> 据了解，为打造绿色生态卫生校园，提高学校卫生管理水平、提升学生体质健康水平，我市经层层评审，命名万州区鸡公岭小学（天星校区）等69所中小学幼儿园为“重庆市中小学卫生示范学校”。</p><p> 作者：匡丽娜返回搜狐，查看更多</p><p> 责任编辑：</p>";
		try{
			obj = new JSONObject();
			obj.put("title","<p> 原标题：69所中小学幼儿园获评首批卫生示范校</p>");
			obj.put("content",content);
			obj.put("mainObj","");
			obj.put("type","0");//走默认类型
			int num = 0;
			while (num<100){
				result = HttpClientPoolUtil.execute(CommonData.AFFECTION_URL,obj.toString());
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
