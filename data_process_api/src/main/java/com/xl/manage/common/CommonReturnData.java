package com.xl.manage.common;

import com.xl.manage.bean.ReturnBean;
import com.xl.tools.StringHelper;

/**
 * 返回值常量
 * @Author:lww
 * @Date:14:53 2017/8/18
 */
public class CommonReturnData {

	/*
	 * 操作失败
	 */

	public final static String OPR_FAIL = "0";
	/*
	 * 后台异常
	 */
	public final static String MANAGE_ERROR = "-1";

	/*
	 * 传入参数为空
	 */
	public final static String PARAMETER_NULL = "-3";

	/*
	 * 队列满了
	 */
	public final static String PARAMETER_QUEUE_FULL = "-2";

	/**
	 * 获取返回结果对象
	 * @Author: lww
	 * @Description:
	 * @Date: 15:04 2017/8/18
	 * @params:
	 */
	public static ReturnBean getReturn(String result){

		ReturnBean returnBean = new ReturnBean();
		if("1".equals(StringHelper.toTrim(result))){
			returnBean.setDatas(result);
			returnBean.setCode("1");
			returnBean.setMsg("操作成功");
		}else if("-3".equals(StringHelper.toTrim(result))){
			returnBean.setDatas(null);
			returnBean.setCode("-3");
			returnBean.setMsg("传入参数为空");
		}else if("-2".equals(StringHelper.toTrim(result))){
			returnBean.setDatas(null);
			returnBean.setCode("-2");
			returnBean.setMsg("处理线程已满");
		}else{
			returnBean.setDatas(null);
			returnBean.setCode("-1");
			returnBean.setMsg("后台异常");
		}

		return returnBean;
	}
}
