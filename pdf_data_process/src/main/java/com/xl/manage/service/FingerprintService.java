package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.bean.article.ArticleBean;
import com.xl.manage.common.CommonData;
import com.xl.manage.common.LogCommonData;
import com.xl.tools.HttpClientPoolUtil;
import com.xl.tools.LogHelper;
import com.xl.tools.PublicClass;
import com.xl.tools.StringUtil;

/**
 * 语义指纹处理
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class FingerprintService {

	/**
	 * 语义指纹处理
	 * @return com.xl.manage.bean.ArticleBean
	 * @Author: lww
	 * @Description:
	 * @Date: 10:17 2017/11/3
	 * @param bean
	 */
	public ArticleBean dealFingerprint(ArticleBean bean){

		JSONObject obj;
		JSONObject jsonObject;
		String result;
		long fingerprint = 0;
		try{

			if("".equals(StringUtil.toTrim(bean.getTitle()))){
				bean.getSigs().setIndexSig(0l);
			}else{
				obj = new JSONObject();
				obj.put("title",bean.getTitle());
				obj.put("type","0");//走默认类型

				result = HttpClientPoolUtil.execute(CommonData.INDEX_SIG_URL,obj.toString());
				jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
					fingerprint = Long.parseLong(StringUtil.toTrim(jsonObject.getString("datas")));
					bean.getSigs().setIndexSig(fingerprint);
				}else{//访问接口失败
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"访问indexSig语指纹接口", obj.toJSONString(),"访问indexSig语指纹接口出现异常！",new Exception(CommonData.INDEX_SIG_URL+" 访问异常！"));
					return null;
				}
			}

			if("".equals(StringUtil.toTrim(bean.getContentText()))||"".equals(StringUtil.toTrim(PublicClass.StripHTML(bean.getContentText())))){
				bean.getSigs().setIndexSigall(bean.getSigs().getIndexSig());
			}else{
				obj = new JSONObject();
				obj.put("title", bean.getTitle());
				obj.put("content", PublicClass.StripHTML(bean.getContentText()));
				obj.put("limit", 10);
				obj.put("keyword", "");
				obj.put("splitInfo", "");
				obj.put("type","0");//走默认类型
				result = HttpClientPoolUtil.execute(CommonData.INDEX_SIG_ALL_URL,obj.toString());
				jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
					fingerprint = Long.parseLong(StringUtil.toTrim(jsonObject.getString("datas")));
					bean.getSigs().setIndexSigall(fingerprint);
				}else{//访问接口失败
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"访问indexSigAll语指纹接口", obj.toJSONString(),"访问indexSigAll语指纹接口出现异常！",new Exception(CommonData.INDEX_SIG_ALL_URL+" 访问异常！"));
					return null;
				}
			}
		}catch (Exception e){
			LogHelper.error(LogCommonData.LOG_CODE_WEB,"语义指纹处理", JSON.toJSONString(bean),"获取语义指纹出现异常！ WebFingerprintService.dealFingerprint ",e);
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

		String content = "　　　　　　&nbsp; &nbsp; 本报讯山西省孝义市今年以 　　“政府+银行+龙头企业+贫困户”四 　　位一体金融精准扶贫模式共发放贷 　　款25778万元，发放受益金522.66万 　　元，实现金融“精准扶贫贷”覆盖全部 　　建档立卡贫困人口。 　　&nbsp; 该模式以3年为周期，每人每年 　　可实现3000元稳定增收。这既为金 　　融以独特方式服务实体经济、履行社 　　会责任提供了更广阔的舞台，也有效 　　降低了金融扶贫的成本，提高了贫困 　　人口享受金融服务的质量，让贫困户 　　真正受益。 　　&nbsp; &nbsp; 同时，该市以产业扶贫、就业扶 　　贫等为突破口，形成贫困户长期、稳 　　定脱贫的综合机制，为全面脱贫奠 　　定基础。 　　&nbsp; &nbsp; （吴贤）";
		try{
			content = PublicClass.StripHTML(content);
			System.out.println(content);
			obj = new JSONObject();
			obj.put("title","展示冬日滑雪运动新风潮");
			obj.put("content", content);
			obj.put("limit", 10);
			obj.put("keyword", "");
			obj.put("splitInfo", "");
			obj.put("type","0");//走默认类型
			int num = 0;
			while (num<2){
				result =  HttpClientPoolUtil.execute(CommonData.INDEX_SIG_ALL_URL,obj.toString());
//			3590595098927606260 1877054356458025654 916244129519130924 4286566814263024136 7838742445256350890
//			3590595098927606300 1877054356458025700 916244129519130900 4286566814263024000 7838742445256351000
				System.out.println(result);
				JSONObject jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
//				Long.p
					System.out.println("num:["+num+"]"+Long.parseLong(jsonObject.getString("datas")));
//				datas = jsonObject.getString("datas");
//				datas = datas.replace("<p>","");
//				dataArray = datas.split("</p>");
//
//				for(String data:dataArray){
//					if(data.indexOf("polarity")>-1){
//						emotionValue = (int) Double.parseDouble(StringUtil.toTrim(data.split("：")[1]));
//					}
//				}

				}else{//访问接口失败
					System.out.println("访问接口失败!");
				}
				num++;
			}
		}catch (Exception e){

		}
	}


}
