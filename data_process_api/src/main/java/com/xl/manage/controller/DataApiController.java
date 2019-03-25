package com.xl.manage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.xl.manage.bean.*;
import com.xl.manage.common.CommonData;
import com.xl.manage.common.CommonReturnData;
import com.xl.manage.server.DataProcessServer;
import com.xl.tools.DateHelper;
import com.xl.tools.LogHelper;
import com.xl.tools.StringHelper;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.Sanselan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:lww
 * @Date:10:22 2018/3/23
 */
@Controller
@RequestMapping(value = "/dataApi/*")
public class DataApiController {

	@Autowired
	@Qualifier("dataProcessServerImpl")
	private DataProcessServer server;

	/**
	 * PDF转IMG任务处理
	 * @return com.xl.manage.bean.ReturnBean
	 * @Author: lww
	 * @Description:
	 * @Date: 17:53 2018/3/28
	 * @param bean
	 */
	@ResponseBody
	@RequestMapping(value = "putTask",method = RequestMethod.POST)
	public ReturnBean putTask(@RequestBody RequestBean bean){

		ReturnBean returnBean = CommonReturnData.getReturn(CommonReturnData.OPR_FAIL);
		try{
			if("".equals(StringHelper.toTrim(bean.getAid()))||"".equals(StringHelper.toTrim(bean.getUrl()))){
				returnBean = CommonReturnData.getReturn(CommonReturnData.PARAMETER_NULL);
			}else{
				CommonData.addCount(getNowDay(),bean.getMediaName());
				returnBean = CommonReturnData.getReturn(server.putTask(bean));
			}
		}catch (Exception e){
			LogHelper.error("PDF转IMG出现异常！",e);
		}
		return returnBean;
	}

	/**
	 * 获取处理失败数据
	 * @return java.util.List<com.xl.manage.bean.RequestBean>
	 * @Author: lww
	 * @Description:
	 * @Date: 11:41 2018/3/23
	 * @param flag (1-PDF转IMG 2-大图转IMG)
	 */
	@ResponseBody
	@RequestMapping(value = "getErrorList",method = RequestMethod.GET)
	public JSONArray getErrorList(String flag){

		JSONArray jsonArray = new JSONArray();

		if("2".equals(flag)){
			if(CommonData.errorThumbMap.isEmpty()){
				return jsonArray;
			}else{
				for(Map.Entry<String,ThumbnailBean> entry:CommonData.errorThumbMap.entrySet()){
					jsonArray.add(entry.getValue());
				}
				return jsonArray;
			}
		}else{
			if(CommonData.errorMap.isEmpty()){
				return jsonArray;
			}else{
				for(Map.Entry<String,RequestBean> entry:CommonData.errorMap.entrySet()){
					jsonArray.add(entry.getValue());
				}
				return jsonArray;
			}
		}
	}

	/**
	 * 删除指定错误数据
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 11:43 2018/3/23
	 * @param aid
	 */
	@ResponseBody
	@RequestMapping(value = "delError",method = RequestMethod.GET)
	public String delError(String aid,String flag){

		try{
			if("2".equals(flag)){
				if(CommonData.errorThumbMap.containsKey(aid)){
					CommonData.errorThumbMap.remove(aid);
				}
			}else{
				if(CommonData.errorMap.containsKey(aid)){
					CommonData.errorMap.remove(aid);
				}
			}
			return "1";
		}catch (Exception e){
			e.printStackTrace();
			return "-1";
		}
	}

	/**
	 * 清空错误数据
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 11:44 2018/3/23
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value = "delAllError",method = RequestMethod.GET)
	public String delAllError(String flag){
		try{
			if("2".equals(flag)){
				CommonData.errorThumbMap.clear();
			}else{
				CommonData.errorMap.clear();
			}
			return "1";
		}catch (Exception e){
			e.printStackTrace();
			return "-1";
		}
	}

	/**
	 * 清空错误数据
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 11:44 2018/3/23
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value = "getCache",method = RequestMethod.GET)
	public String getCache(){
		try{
			return "{CachePicBean.map.size:"+ CachePicBean.map.size()+",CachePicBean.queue.size:"+CachePicBean.queue.size()+",CacheBean.set.size:"+ CacheBean.set.size()+",CacheBean.queue.size:"+CacheBean.queue.size()+"}";
		}catch (Exception e){
			e.printStackTrace();
			return "-1";
		}
	}

	/**
	 * 缩略图入库
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 11:44 2018/3/23
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value = "thumbnailImage",method = RequestMethod.POST)
	public ReturnBean thumbnailImage(@RequestBody ThumbnailBean bean){
		ReturnBean returnBean = CommonReturnData.getReturn(CommonReturnData.OPR_FAIL);

		try{

			if("".equals(StringHelper.toTrim(bean.getAid()))||"".equals(StringHelper.toTrim(bean.getUrl()))){
				returnBean = CommonReturnData.getReturn(CommonReturnData.PARAMETER_NULL);
			}else{
				returnBean = CommonReturnData.getReturn(server.thumbnailToSql(bean));
			}
		}catch (Exception e){
			LogHelper.error("缩略图处理出现异常！",e);
		}
		return returnBean;
	}

	/**
	 * 缩略图入库
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 11:44 2018/3/23
	 * @param
	 */
	@ResponseBody
	@RequestMapping(value = "bigImage",method = RequestMethod.POST)
	public ReturnBean bigImage(@RequestBody ThumbnailBean bean){
		ReturnBean returnBean = CommonReturnData.getReturn(CommonReturnData.OPR_FAIL);
		try{
			if("".equals(StringHelper.toTrim(bean.getAid()))||"".equals(StringHelper.toTrim(bean.getUrl()))){
				returnBean = CommonReturnData.getReturn(CommonReturnData.PARAMETER_NULL);
			}else{
				CommonData.addCount(getNowDay(),bean.getMediaName());
				returnBean = CommonReturnData.getReturn(server.thumbnailToSql(bean));
			}
		}catch (Exception e){
			LogHelper.error("缩略图处理出现异常！",e);
		}
		return returnBean;
	}

	/**
	 * 获取统计数据
	 * @return com.xl.manage.bean.ReturnBean
	 * @Author: lww
	 * @Description:
	 * @Date: 17:30 2018/3/28
	 * @param date 日期(YYYYMMDD)
	 * @param flag 是否全部(0-否 1-是)
	 */
	@ResponseBody
	@RequestMapping(value = "getCountMap",method = RequestMethod.GET)
	public ReturnBean getCountMap(@RequestParam(value="date", defaultValue="")String date,@RequestParam(value="flag", defaultValue="0")String flag){

		ReturnBean bean = new ReturnBean();

		Map<String,Map<String,Integer>> result = CommonData.getCountMap(date,Integer.parseInt(flag));

		if(result!=null){
			bean.setCode("1");
			bean.setMsg("请求成功");
			bean.setDatas(JSON.toJSONString(result));
		}else{
			bean.setCode("1");
			bean.setMsg("请求成功");
			bean.setDatas("");
		}
		return bean;
	}

	/**
	 * 删除指定统计数据
	 * @return com.xl.manage.bean.ReturnBean
	 * @Author: lww
	 * @Description:
	 * @Date: 17:30 2018/3/28
	 * @param date 日期(YYYYMMDD)
	 * @param mediaName 媒体名称
	 */
	@ResponseBody
	@RequestMapping(value = "delCountMap",method = RequestMethod.GET)
	public ReturnBean delCountMap(@RequestParam(value="date", defaultValue="")String date,@RequestParam(value="mediaName", defaultValue="")String mediaName){

		ReturnBean bean = new ReturnBean();

		try{
			CommonData.delCount(date,mediaName);
			bean.setCode("1");
			bean.setMsg("请求成功");
			bean.setDatas("");
		}catch (Exception e){
			bean.setCode("-1");
			bean.setMsg("请求失败！");
			bean.setDatas(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 获取统计数据
	 * @return com.xl.manage.bean.ReturnBean
	 * @Author: lww
	 * @Description:
	 * @Date: 17:30 2018/3/28
	 * @param date 日期(YYYYMMDD)
	 * @param flag 是否全部(0-否 1-是)
	 */
	@ResponseBody
	@RequestMapping(value = "getSucessCountMap",method = RequestMethod.GET)
	public ReturnBean getSucessCountMap(@RequestParam(value="date", defaultValue="")String date,@RequestParam(value="flag", defaultValue="1")String flag){

		ReturnBean bean = new ReturnBean();

		Map<String,Map<String,Integer>> result = CommonData.getSucessCountMap(date,Integer.parseInt(flag));

		if(result!=null){
			bean.setCode("1");
			bean.setMsg("请求成功");
			bean.setDatas(JSON.toJSONString(result));
		}else{
			bean.setCode("1");
			bean.setMsg("请求成功");
			bean.setDatas("");
		}
		return bean;
	}

	/**
	 * 统计数据
	 * @return com.xl.manage.bean.ReturnBean
	 * @Author: lww
	 * @Description:
	 * @Date: 17:30 2018/3/28
	 * @param date 日期(YYYYMMDD)
	 * @param mediaName 媒体名称
	 */
	@ResponseBody
	@RequestMapping(value = "addSucessCount",method = RequestMethod.GET)
	public ReturnBean addSucessCount(@RequestParam(value="date", defaultValue="")String date,@RequestParam(value="mediaName", defaultValue="")String mediaName){

		ReturnBean bean = new ReturnBean();

		try{
			CommonData.addSucessCount(date,mediaName);
			bean.setCode("1");
			bean.setMsg("请求成功");
			bean.setDatas("");
		}catch (Exception e){
			bean.setCode("-1");
			bean.setMsg("请求失败！");
			bean.setDatas(e.getMessage());
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 获取当天日期
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 17:16 2018/3/28
	 * @param
	 */
	private String getNowDay(){
		return DateHelper.getNowDate(DateHelper.FMT_DATE_YYYYMMDD);
	}

	public static void main(String[] args) {

		String ufl = "D:\\ctdsb.jpg";
		String ufl2 = "D:\\ctdsb_new.jpg";
		String url2 = "http://ctdsb.cnhubei.com/ctdsb/20180328/page_05.jpg";
		/*String ufl = "D:\\hbrb.jpg";
		String ufl2 = "D:\\hbrb_new.jpg";
		String url2 = "http://hbrb.cnhubei.com/hbrb/20180328/page_01.jpg";*/


	/*	String ufl = "D:\\sxwb.jpg";
		String ufl2 = "D:\\sxwb_new.jpg";
		String url2 = "http://192.168.10.29/pdf/%E4%B8%89%E5%B3%A1%E6%99%9A%E6%8A%A5/SXWB2018-3-28-15.jpg";*/

		URL url = null;
		File file = null;
		String result = "";
		int dpi = 0;
		BufferedImage bufferedImage = null;
		ImageInfo imageInfo = null;
		DecimalFormat dformat = new DecimalFormat("#.00");
		try {
			downloadFileFromUrl(url2,ufl);

			url = new URL(url2);
			bufferedImage = ImageIO.read(url);
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			System.out.println("width:"+width);
			System.out.println("height:"+height);
			file = new File(ufl);
			try {
				imageInfo = Sanselan.getImageInfo(file);
				dpi = imageInfo.getPhysicalWidthDpi();
				if(dpi<=0){
					dpi = 96;
					result = "0,0";
				}
			} catch (Exception e) {
				dpi = 96;
				e.printStackTrace();
				result = "0,0";
			}

			System.out.println(dpi);
			Double finalWidth = width * 2.54 / dpi;
			Double finalHeight = height * 2.54 / dpi;
			result = dformat.format(finalWidth) + "," + dformat.format(finalHeight);

			System.out.println(finalWidth*184/2.54);
			System.out.println(finalHeight*184/2.54);

			resizeImage(ufl,(int)(finalWidth*184/2.54),(int)(finalHeight*184/2.54),ufl2);
			handleDpi(ufl2,300,300);
			System.out.println(result);
		} catch (Exception e) {

		}


	}

	/**
	 * 下载网络文件至文件夹
	 *
	 * @Title: downloadFileFromUrl
	 * @data:2016-2-18下午2:01:36
	 * @author:zhangshiyuan
	 *
	 * @param link
	 * @param savePath
	 * @throws Exception
	 */
	public static int downloadFileFromUrl(String link,String savePath) throws Exception{
		int flag = 0;
		InputStream in = null;
		OutputStream os = null;
		File f = new File(savePath);
		if(!f.getParentFile().exists())
			f.getParentFile().mkdirs();
		try {
			URL url = new URL(link);// new一个URL对象
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();// 打开链接
			conn.setRequestMethod("GET");// 设置请求方式为"GET"
			conn.setConnectTimeout(5 * 1000);// 超时响应时间为5秒
			in = conn.getInputStream();// 通过输入流获取图片数据
			os = new FileOutputStream(savePath);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while((bytesRead = in.read(buffer,0,8192))!=-1){
				os.write(buffer,0,bytesRead);
			}
			flag = 1;
		} catch (Exception e) {
			flag = 0;
			System.out.println("错误的链接：" + link);
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
			if (os != null) {
				os.flush();
				os.close();
			}
		}
		return flag;
	}

	/**
	 * 改变图片DPI
	 *
	 * @param xDensity
	 * @param yDensity
	 */
	public static void handleDpi(String ufl,int xDensity, int yDensity) {
		try {
			File file = new File(ufl);
			BufferedImage image = ImageIO.read(file);

			JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(new FileOutputStream(file));
			JPEGEncodeParam jpegEncodeParam = jpegEncoder.getDefaultJPEGEncodeParam(image);
			jpegEncodeParam.setDensityUnit(JPEGEncodeParam.DENSITY_UNIT_DOTS_INCH);
			jpegEncoder.setJPEGEncodeParam(jpegEncodeParam);
			jpegEncodeParam.setQuality(0.75f, false);
			jpegEncodeParam.setXDensity(xDensity);
			jpegEncodeParam.setYDensity(yDensity);
			jpegEncoder.encode(image, jpegEncodeParam);
			image.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 *  重置图片大小
	 * @param filepath 原路径
	 * @param width 新的宽度
	 * @param height 新的高度
	 */
	public static void resizeImage(String  filepath, int width, int height,String newFilePath) {
		try {
			File srcFile = new File(filepath);

			File destFile = new File(newFilePath);

			if(!destFile.getParentFile().exists())
				destFile.getParentFile().mkdirs();
			Image i = ImageIO.read(srcFile);
			i = resizeImage(i, width, height);
			ImageIO.write((RenderedImage) i, "jpg", destFile);
		} catch (Exception e) {
			System.out.println("文件不存在");
			e.printStackTrace();
		}
	}

	public static Image resizeImage(Image srcImage, int width, int height) {
		try {

			BufferedImage buffImg = null;
			buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			buffImg.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);

			return buffImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
