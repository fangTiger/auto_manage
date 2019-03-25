package com.xl.manage.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.bean.article.ArticleBean;
import com.xl.manage.common.LogCommonData;
import com.xl.manage.common.WebCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.PublicClass;
import com.xl.tool.StringUtil;
import org.junit.Test;

/**
 * 语义指纹处理
 * @Author:lww
 * @Date:15:34 2017/9/21
 */
public class WebFingerprintService {

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

				result = HttpClientPoolUtil.execute(WebCommonData.INDEX_SIG_URL,obj.toString());
				jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
					fingerprint = Long.parseLong(StringUtil.toTrim(jsonObject.getString("datas")));
					bean.getSigs().setIndexSig(fingerprint);
				}else{//访问接口失败
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"访问indexSig语指纹接口", obj.toJSONString(),"访问indexSig语指纹接口出现异常！",new Exception(WebCommonData.INDEX_SIG_URL+" 访问异常！"));
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
				result = HttpClientPoolUtil.execute(WebCommonData.INDEX_SIG_ALL_URL,obj.toString());
				jsonObject = JSONObject.parseObject(result);
				if("1".equals(jsonObject.getString("code"))){//请求成功
					fingerprint = Long.parseLong(StringUtil.toTrim(jsonObject.getString("datas")));
					bean.getSigs().setIndexSigall(fingerprint);
				}else{//访问接口失败
					LogHelper.error(LogCommonData.LOG_CODE_WEB,"访问indexSigAll语指纹接口", obj.toJSONString(),"访问indexSigAll语指纹接口出现异常！",new Exception(WebCommonData.INDEX_SIG_ALL_URL+" 访问异常！"));
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

		String content = "<p> 恒生指数昨日上涨0.7%，收报29,060点。市场成交额降至921亿元。重磅股腾讯(700)，友邦(1299)和滙丰(5)分别升1.3%，1.0%和0.4%。中国移动(941)几乎无变化。港交所(388)大涨4.2%，是表现最好的成分股。消费股收盘收高。恒安国际(1044)和旺旺中国(151)分别上涨2.5%和0.9%……</p><p> 相关文档请查阅附件：</p><p> [茂宸证券]每日市场透视</p><p> (责任编辑：J01)</p>";
		try{
			content = PublicClass.StripHTML(content);
			System.out.println(content);
			obj = new JSONObject();
			obj.put("title","[茂宸证券]每日市场透视");//В понедельник Совбез ООН проголосует проект резолюции по статусу Иерусалима
			obj.put("content",PublicClass.StripHTML(content));
			obj.put("limit", 10);
			obj.put("keyword", "");
			obj.put("splitInfo", "");
			obj.put("type","0");//走默认类型
			int num = 0;
			while (num<1){
				result =  HttpClientPoolUtil.execute(WebCommonData.INDEX_SIG_ALL_URL,obj.toString());
				System.out.println(result);
//			3590595098927606260 1877054356458025654 916244129519130924 4286566814263024136 7838742445256350890
//			3590595098927606300 1877054356458025700 916244129519130900 4286566814263024000 7838742445256351000
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

	@Test
	public void testSig(){
		String title = "平时不留心，刚买的新车也会自燃！";
		String content = "<p> <p data-role=\"original-title\" style=\"display:none\">原标题：平时不留心，刚买的新车也会自燃！</p></p><p> <p> <span>炎炎夏日，如果平时不注意保养和检修，车辆很容易发生自燃，各位车主需要多加注意！</span></p> </p><p> <p> <strong>车辆自燃四大诱因</strong></p> </p><p> <p> <img width=\"100%\" src=\"\" /></p> </p><p> <p> <img width=\"auto\" src=\"http://img.mp.sohu.com/upload/20170807/a9a8bb2db2384eb5af27db4f6e01e347.png\" /></p> </p><p> <p> <span><strong>1. 车内电路老化</strong></span></p> </p><p> <p> <span>在车辆自燃事故中，有不少是因为车辆油路、电路老化造成的。由于这些电路绝大多数都在发动机舱内，所有很容易受到车主的忽视，如果电路绝缘胶皮破损、插头虚接，那么在车辆行驶的颠簸中，难免就会出现“搭铁”，就会造成车内电路短路，引发火源。</span></p> </p><p> <p> <span><strong>2<span>. 车内私改电路</span></strong></span></p> </p><p> <p> <span>其实很多新车出现自燃的情况，绝大多数都是因为私改车内线路造成的。一些车主很喜欢给自己爱车增加“配置”，如果改装工人的水平不高，或者是街边小店的人操刀，那么就会埋下车辆自燃的“火种”！</span></p> </p><p> <p> <span><strong>3. 车内有易燃危险品</strong></span></p> </p><p> <p> 不要把香水、打火机、气压罐……等物品放在车内，至少也要远离太阳光的直晒。如果一旦这些危险物品发生意外爆炸，也很可能引燃车辆，后果同样很严重。</p> </p><p> <p> <strong>如果车辆自燃，怎么办？</strong></p> </p><p> <p> <img width=\"100%\" src=\"\" /></p> </p><p> <p> <img width=\"auto\" src=\"http://img.mp.sohu.com/upload/20170807/8ed2431bfc03469e9fb801f1957e5d65_th.png\" /></p> </p><p> <p> <span><strong>1. 不慌乱不恋财，及时报警</strong></span></p> </p><p> <p> <span>如果车辆已经冒烟自燃，那么必须及时靠边停车，取出灭火器，切勿慌张，切勿恋财！同时立即报警求得援助！</span></p> </p><p> <p> <span><strong>2. 发现有征兆，立即靠边停车</strong></span></p> </p><p> <p> <span>很多时候车辆自燃不会直接起明火，而是先有焦糊的气味，或是烟雾。需要驾驶员保持敏感度和警觉性。</span></p> </p><p> <p> <span><strong>3. 看清火势，切勿盲目打开引擎盖</strong></span></p> </p><p> <p> <span>如果发现发动机舱出现大量烟雾，并带有明火，切勿盲目打开引擎盖！因为引擎盖的打开，会让空气流通得更快，增加火势的蔓延。</span></p> </p><p> <p> <strong>如何预防车辆自燃</strong></p> </p><p> <p> <img width=\"100%\" src=\"\" /></p> </p><p> <p> <img width=\"auto\" src=\"http://img.mp.sohu.com/upload/20170807/14dcd17811234881ab7224ebdc73cb16_th.png\" /></p> </p><p> <p> <strong>1. 车辆勤检查</strong></p> </p><p> <p> <span>预防车辆自燃最好的方法就是“勤检查”，一定要养成定期检查车内、发动机舱内情况的好习惯，第一时间发现，第一时间处理！</span></p> </p><p> <p> <strong>2. 避免私改线路</strong></p> </p><p> <p> <span>私改线路的问题由来已久。购买新车的车主，如果需要“增配”，请到广汽丰田第一店，不要私改线路，造成隐患。</span></p> </p><p> <p> <strong>3. 油路漏油应及时更换</strong></p> </p><p> <p> 如果我们发现车内有漏油情况出现，一定要及时查找漏油点，判断是否影响车辆正常工作，或者是否存在严重的安全问题。</p> </p><p> <p> <span>其实无论是缺乏养护，还是私改线路……可以说绝大多数的“车辆自燃”，都是因人为所致，如果不幸遇到车辆自燃，切勿贪恋财物，切勿慌张行事，需要及时报警，尽快远离，等待消防队救援。</span></p> </p><p> <p> <img width=\"auto\" src=\"\" /><a href=\"//www.sohu.com/?strategyid=00001 \" target=\"_blank\" title=\"点击进入搜狐首页\" id=\"backsohucom\" style=\"white-space: nowrap;\"><span class=\"backword\"><i class=\"backsohu\"></i>返回搜狐，查看更多</span></a></p> <p data-role=\"editor-name\">责任编辑：<span></span></p></p><p> </article></p><p> <div class=\"statement\">声明：本文由入驻搜狐号的作者撰写，除搜狐官方账号外，观点仅代表作者本人，不代表搜狐立场。</p>";
		JSONObject obj;
		obj = new JSONObject();
		obj.put("title",title);
		obj.put("content", content);
		String result =  HttpClientPoolUtil.execute(WebCommonData.SIG_URL,obj.toString());
		System.out.println(result);
	}


}
