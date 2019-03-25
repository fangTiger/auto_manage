package com.xl.manage.common;

import com.alibaba.fastjson.JSON;
import com.xl.manage.bean.RequestBean;
import com.xl.manage.bean.ThumbnailBean;
import com.xl.tools.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 * @Author:lww
 * @Date:9:58 2017/9/8
 */
public class CommonData {

	//PDF转图片后缀名
	public static final String PDF_TO_IMAGE = "jpg";

//	public static final String PDF_LOCAL_PATH = "D:\\javatest\\image";
	//本地转换后图片存储路径
	public static final String PDF_LOCAL_PATH = "/home/storm/pdf";

//	public static final String THUMB_LOCAL_PATH = "D:\\javatest\\image\\";

	public static final String THUMB_LOCAL_PATH = "/home/storm/image/";

	public static final Integer PDF_TO_IMAGE_DPI = 300;

	//1-文章图 2-缩略图 3-扫描图 4-整版图 5-入口图 6-焦点图 7-封面图
	public static final String ARTICALFOLDER = "ArticleImage";//文章图
	public static final String THUMBFOLDER = "ThumbnailImage";//缩略图文件夹
	public static final String SCANFOLDER = "ScanImage";//扫描图文件夹
	public static final String WHOLEFOLDER = "WholeImage";//整版图文件夹
	public static final String FOCUSFOLDER = "FocusImage";//显著位置图
	public static final String COVERFOLDER = "CoverImage";//封面图文件夹

	//拷屏图入库接口
	public static final String DOCPICIMAGE_API = "http://192.168.10.18:8082/xl_api/DataProcessing/Setdoc_psimage";

	/* FTP服务器hostname */
	public static final String FTP_URL = "192.168.10.69";

	/* FTP服务器端口 */
	public static final String FTP_PORT = "21";

	/* FTP登录账号 */
	public static final String FTP_USERNAME = "Administrator";

	/* FTP登录密码 */
	public static final String FTP_PASSWORD = "zhuxinLJ123";

	/* FTP服务器保存目录 */
	public static final String FTP_SAVE_PATH = "/inewspicture/";

	//处理失败任务集合
	public static Map<String,RequestBean> errorMap = new HashMap<>();

	//处理失败缩略图任务集合
	public static Map<String,ThumbnailBean> errorThumbMap = new HashMap<>();

	//媒体处理统计<日期，<媒体，数据量>>
	private static Map<String,Map<String,Integer>> countMap = new HashMap<>();


	//媒体处理成功统计<日期，<媒体，数据量>>
	private static Map<String,Map<String,Integer>> processSucessMap = new HashMap<>();

	public static Map<String,Map<String,Integer>> getCountMap(String key,int flag){

		Map<String,Map<String,Integer>> result = null;
		if(flag==0){
			if(countMap.containsKey(key)){
				result = new HashMap<>();
				result.put(key,countMap.get(key));
			}
		}else{
			return countMap;
		}
		return result;
	}

	public static Map<String,Map<String,Integer>> getSucessCountMap(String key,int flag){

		Map<String,Map<String,Integer>> result = null;
		if(flag==0){
			if(processSucessMap.containsKey(key)){
				result = new HashMap<>();
				result.put(key,processSucessMap.get(key));
			}
		}else{
			return processSucessMap;
		}
		return result;
	}

	public static void main(String[] args) {
		Map<String,Integer> map = new HashMap<>();
		map.put("22",2);
		countMap.put("11",map);
		System.out.println(JSON.toJSONString(countMap));
		countMap.get("11").put("22",33);
		System.out.println(JSON.toJSONString(countMap));
	}

	/**
	 * 增加统计媒体
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 17:05 2018/3/28
	 * @param key
	 * @param mediaName
	 */
	public synchronized static void addSucessCount(String key,String mediaName){

		Map<String,Integer> map;

		if(processSucessMap.containsKey(key)){
			map = processSucessMap.get(key);
			if(map.containsKey(mediaName)){
				map.put(mediaName,(map.get(mediaName)+1));
			}else{
				map.put(mediaName,1);
			}
		}else{
			map = new HashMap<>();
			map.put(mediaName,1);
			processSucessMap.put(key,map);
		}
	}

	/**
	 * 增加统计媒体
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 17:05 2018/3/28
	 * @param key
	 * @param mediaName
	 */
	public synchronized static void addCount(String key,String mediaName){

		Map<String,Integer> map;

		if(countMap.containsKey(key)){
			map = countMap.get(key);
			if(map.containsKey(mediaName)){
				map.put(mediaName,(map.get(mediaName)+1));
			}else{
				map.put(mediaName,1);
			}
		}else{
			map = new HashMap<>();
			map.put(mediaName,1);
			countMap.put(key,map);
		}
	}

	/**
	 * 删除统计操作
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 17:13 2018/3/28
	 * @param key
	 * @param mediaName
	 */
	public synchronized static void delCount(String key,String mediaName){

		Map<String,Integer> map;

		if(!"".equals(StringHelper.toTrim(key))){
			if(countMap.containsKey(key)){
				if(!"".equals(StringHelper.toTrim(mediaName))){
					map = countMap.get(key);
					if(map.containsKey(mediaName)){
						map.remove(mediaName);
					}
				}else{
					countMap.remove(key);
				}
			}
		}else{
			countMap.clear();
		}
	}

}
