package com.xl.irtv.service;

import com.alibaba.fastjson.JSONObject;
import com.xl.bean.irtv.BroadcastBean;
import com.xl.irtv.common.IrtvCommonData;
import com.xl.tool.HttpClientPoolUtil;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lww
 * @Date:15:13 2017/9/14
 */
public class IrtvExtractionProService {

	public static void main(String[] args) {

		String title = "新普拉多是不是有2.0T";
		String content = "问身旁是不是有二点零T的都有什么配置.<br />好多呢我想还没上市而且在林中.<br />打开三六七月份的什么呢.<br />今天是你是风显示出来辟过谣因为从去年年底到今年上半年的风传.<br />说改款之后的新普拉多恩惠主推三点五.<br />之后的还会版二点七的停产上这个皇冠上的能安装多人怎样发动机的这一上会让这个.<br />制造成本下降动力不输给三点五但是价格能就可以打死软下来就比原来的二七零零二点七排量邮件多了.<br />不怕多上的唯一的一个版本就是它的二点七的发动机没劲的划时代的成立的时间.<br />帮最好不见车主起诉但是呢在.<br />今年的年终六七月份的反义词充电显示出来辟过谣.<br />当我们通常是这样的就是成交量扑朔迷离的这种招数.<br />常常有这个歌那个要盒子显示小批弯腰之后有一天他们如何治疗.<br />包括很多明星的故事也都这样出来就是飘飘过一段时间又被证实反应就是无风不起浪.<br />也不有个说法仔仔一些村里面还是谣言都会变成真的.<br />那这个我也没办法判断.<br />真的这个普拉多上市之后上市之后形成上市之后或者说上述的半年之后在的人体到底有还没有我怎么告诉大家在六七月份什么意思分别出来辟过谣.<br />说我们会停产二点七我们会欣赏三点五但是.<br />我们不会少点聆听我记得很清楚的节目里面都不包括什么.<br />";

		JSONObject obj = new JSONObject();
		obj.put("title",title);
		obj.put("content",content);
		obj.put("link","");
		obj.put("sourceType","");
		obj.put("type","0");

		String result = "";
		try{
			result = HttpClientPoolUtil.execute(IrtvCommonData.EXTRACTION_URL,obj.toString());
			System.out.println(result);
		}catch (Exception e){
			LogHelper.error("------------获取实体抽取出现异常！ IrtvClassProService.dealClass  [param:"+obj.toString()+"]--------",e);
		}
	}

	/**
	 * 实体抽取
	 * @return com.xl.irtv.bean.BroadcastBean
	 * @Author: lww
	 * @Description:
	 * @Date: 16:46 2018/1/15
	 * @param bean
	 */
	public BroadcastBean dealExtraction(BroadcastBean bean)throws Exception{

		String title = bean.getTitle();
		String content = bean.getContentText();

		JSONObject obj = new JSONObject();
		obj.put("title",title);
		obj.put("content",content);
		obj.put("link","");
		obj.put("sourceType","");
		obj.put("type","0");

		String result = "";
		String resultReturn;
		JSONObject msgObj = new JSONObject();
		try{
			result = HttpClientPoolUtil.execute(IrtvCommonData.EXTRACTION_URL,obj.toString());
			resultReturn = dealResult(bean,result);

			if("1".equals(resultReturn)){
				msgObj.put("msg",StringUtil.StripHTML(content));
				result = HttpClientPoolUtil.execute(IrtvCommonData.LANGUAGE_URL,msgObj.toString());
				bean.setLanguage(StringUtil.toTrim(result).replace("\"",""));
				return bean;
			}else{
				LogHelper.info("----------------------------获取实体抽取Service出现异常！param["+obj.toString()+"]------------------------");
				return null;
			}
		}catch (Exception e){
			bean = null;
			LogHelper.error("------------获取实体抽取出现异常！ IrtvClassProService.dealClass  [param:"+obj.toString()+"]--------",e);
		}
		return bean;
	}

	/**
	 * 解析实体抽取结果
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 15:23 2017/9/18
	 * @param bean
	 * @param result
	 */
	private String dealResult(BroadcastBean bean,String result)throws Exception{

		String msg ;
		JSONObject obj;
		JSONObject resultObj;

		String peopleStr = "";
		String locationStr = "";
		String mechanismStr = "";
		String mediaStr = "";
		String countryStr = "";
		String provinceStr = "";

		String resultReturn;
		try{
			if(!"".equals(StringUtil.toTrim(result))){
				obj = JSONObject.parseObject(result);
				if("1".equals(obj.getString("code"))){
					msg = obj.getString("datas");
					resultObj = JSONObject.parseObject(msg);

					peopleStr = resultObj.getString("people");
					locationStr = resultObj.getString("location");
					mechanismStr = resultObj.getString("organization");
					mediaStr = resultObj.getString("media");
					countryStr = resultObj.getString("country");
					provinceStr = resultObj.getString("province");
				}else{
					LogHelper.info("----------------------------获取实体抽取出现异常！result["+result+"]------------------------");
					return "-1";
				}
			}
			bean.setPeopleExtraction(this.dealResult(peopleStr));
			bean.setLocationExtraction(this.dealResult(locationStr));
			bean.setMechanismExtraction(this.dealResult(mechanismStr));
			bean.setMediaExtraction(this.dealResult(mediaStr));
			bean.setCountryExtraction(this.dealResult(countryStr));
			bean.setProvinceExtraction(this.dealResult(provinceStr));
			resultReturn = "1";
		}catch (Exception e){
			resultReturn = "-1";
			LogHelper.error("------------解析分类结果出现异常！ IrtvClassProService.dealMClass  [param:"+result+"]--------",e);
		}
		return resultReturn;
	}

	/**
	 * 处理返回结果
	 * @return java.util.List<java.lang.String>
	 * @Author: lww
	 * @Description:
	 * @Date: 15:23 2017/9/18
	 * @param result
	 */
	private List<String> dealResult(String result)throws Exception{

		List<String> resultList = new ArrayList<>();
		String[] resultArray;
		if(!"".equals(StringUtil.toTrim(result))){
			resultArray = result.split("#");
			for(String val:resultArray){
				if(!"".equals(StringUtil.toTrim(val))){
					resultList.add(val);
				}
			}
		}
		return resultList;
	}
}
