package com.xl.irtv.service;

import com.xl.bean.irtv.BroadcastBean;
import com.xl.tool.DateHelper;
import com.xl.tool.HeritrixHelper;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:lww
 * @Date:15:12 2017/9/14
 */
public class IrtvDataProService {

	/**
	 * 数据格式化
	 * @return com.xl.irtv.bean.BroadcastBean
	 * @Author: lww
	 * @Description:
	 * @Date: 15:15 2017/9/14
	 * @param bean
	 */
	public BroadcastBean dataPro(BroadcastBean bean)throws Exception{

		//数据格式化
		try{
			bean.setAid(HeritrixHelper.getAidByUrl(bean.getUrl().toLowerCase())+"");
			bean.getD().setCreateDate(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
			bean.setWords(this.delHTMLTag(StringUtil.toTrim(bean.getContentText())).length());
		}catch (Exception e){
			bean = null;
			LogHelper.error("-------------------数据格式化出现异常！IrtvDataProService.dataPro--------------------",e);
		}
		return bean;
	}

	/**
	 * 去掉文本中html标签
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 14:36 2017/9/19
	 * @param htmlStr
	 */
	private String delHTMLTag(String htmlStr){

		String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
		String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
		String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

		Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
		Matcher m_script=p_script.matcher(htmlStr);
		htmlStr=m_script.replaceAll(""); //过滤script标签

		Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
		Matcher m_style=p_style.matcher(htmlStr);
		htmlStr=m_style.replaceAll(""); //过滤style标签

		Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
		Matcher m_html=p_html.matcher(htmlStr);
		htmlStr=m_html.replaceAll(""); //过滤html标签

		return htmlStr.replace("&nbsp;","").trim(); //返回文本字符串
	}


}
