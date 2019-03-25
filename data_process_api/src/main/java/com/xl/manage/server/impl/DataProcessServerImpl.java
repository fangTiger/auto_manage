package com.xl.manage.server.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.realobjects.pdfbaker.pdf.PdfReader;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.xl.manage.bean.*;
import com.xl.manage.common.CommonData;
import com.xl.manage.server.DataProcessServer;
import com.xl.tools.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.Sanselan;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lww
 * @Date:10:23 2018/3/23
 */
@Service
public class DataProcessServerImpl implements DataProcessServer{

	/**
	 * 新增任务
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 10:46 2018/3/23
	 * @param bean
	 */
	@Override
	public String putTask(RequestBean bean) {
		String result;
		try {

			result = executeTask(bean,bean.getUrl());//先处理内网
			if(!"1".equals(StringHelper.toTrim(result))){//若处理失败

				//20180510 更新：当处理内网失败时,不再处理原网信息
				/*result = executeTask(bean,bean.getOldUrl());//再处理原网
				if(!"1".equals(StringHelper.toTrim(result))){//若失败
					bean.setCrearTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
					CommonData.errorMap.put(bean.getAid(),bean);//入错误列表
				}*/

				bean.setCrearTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
				CommonData.errorMap.put(bean.getAid(),bean);//入错误列表
			}
			result = "1";
		} catch (Exception e) {
			LogHelper.error("任务执行出现异常："+ JSON.toJSONString(bean),e);
			result = "-1";//后台异常
		}
		return result;
//		return PicProcessThreadPool.putTask(bean);
	}

	public static void main(String[] args) {
		DataProcessServerImpl server = new DataProcessServerImpl();

		RequestBean bean = new RequestBean();
		bean.setAid("-8326384955771207240");
		bean.setType("press");
		bean.setUrl("http://192.168.10.47/pdf/%E6%B7%B1%E5%9C%B3%E6%99%9A%E6%8A%A5/SZWB2018-05-18-A16.pdf");
		bean.setOldUrl("");
		bean.setDispTime("2018-05-18 00:00:00");

		server.putTask(bean);
	}


	/**
	 * 缩略图入库
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 14:21 2018/3/28
	 * @param bean
	 */
	@Override
	public String thumbnailToSql(ThumbnailBean bean) {

		Docpsimagebean docpsimagebean;

		File file;
		String imageName="";

		URL url;
		ImageInfo imageInfo = null;
		BufferedImage bufferedImage = null;
		JSONObject jsonObject;

		int dpi;
		String result;
		try{
			DecimalFormat dformat = new DecimalFormat("#.00");

			String nowTime = DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME);

			String year = nowTime.substring(0, 4);//年份
			String month = nowTime.substring(5, 7);//月份
			String day = nowTime.substring(8, 10);//日

			String scanftpPath = CommonData.FTP_SAVE_PATH + year + "/" + CommonData.THUMBFOLDER + "/" + year + month + day + "/";

			file = new File(bean.getUrl());
			imageName = file.getName(); // 获取图片文件名

			url = new URL(bean.getUrl());
			bufferedImage = ImageIO.read(url);

			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();

			try {
				downloadFileFromUrl(bean.getUrl(),CommonData.THUMB_LOCAL_PATH+imageName);
				file = new File(CommonData.THUMB_LOCAL_PATH+imageName);
				imageInfo = Sanselan.getImageInfo(file);
				dpi = imageInfo.getPhysicalWidthDpi();
				if(dpi<=0){
					dpi = 96;
				}
			} catch (Exception e) {
				dpi = 96;
			}

			float finalWidth = Float.parseFloat(dformat.format(width * 2.54 / dpi));
			float finalHeight = Float.parseFloat(dformat.format(height * 2.54 / dpi));

			if(FtpHelper.uploadFileFromUrlToFtp(file,imageName,scanftpPath)==0){
				LogHelper.info("JPG上传出现异常！");
				return "-1";
			}

			docpsimagebean = new Docpsimagebean();
			docpsimagebean.setDispTime(bean.getDispTime());
			docpsimagebean.setMediaType(bean.getType());
			docpsimagebean.setAid(Long.parseLong(bean.getAid()));
			docpsimagebean.setCreateTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
			docpsimagebean.setHeight(finalWidth);
			docpsimagebean.setWidth(finalHeight);
			docpsimagebean.setPicSn(1);
			docpsimagebean.setImagePath(imageName);
			docpsimagebean.setServerID(6);
			docpsimagebean.setType(2);//缩略图

			//TODO 数据入库
			result = HttpClientPoolUtil.execute(CommonData.DOCPICIMAGE_API,JSON.toJSONString(docpsimagebean));
			jsonObject = JSON.parseObject(result);
			if(!"0".equals(StringHelper.toTrim(jsonObject.getString("code")))){
				return "-1";
			}
			return "1";
		}catch (Exception e){
			e.printStackTrace();
			CommonData.errorThumbMap.put(bean.getAid(),bean);
		}finally {
			FileHelper.delFile(CommonData.THUMB_LOCAL_PATH+imageName);
		}
		return "-1";
	}

	/**
	 * 大图处理
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 16:40 2018/3/28
	 * @param bean
	 */
	@Override
	public String bigImage(RequestBean bean) {

		/*URL url = null;
		File file = null;
		String result = "";
		String fileName;
		int dpi = 0;
		BufferedImage bufferedImage = null;
		ImageInfo imageInfo = null;
		DecimalFormat dformat = new DecimalFormat("#.00");
		try {

			file = new File(bean.getUrl());
			fileName = file.getName();

			downloadFileFromUrl(bean.getUrl(),fileName);

			url = new URL(url2);
			bufferedImage = ImageIO.read(url);
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			System.out.println("width:"+width);
			System.out.println("height:"+height);

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

		}*/
		return null;
	}


	/**
	 * 执行任务
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 10:46 2018/3/23
	 * @param bean
	 */
	public String executeTask(RequestBean bean,String urlTask) {

		String result;

		File file;
		File dstFile;
		PDDocument pdDocument=null;
		PDFRenderer renderer = null;
		PdfReader reader = null;
		InputStream in = null;
		URL url;
		HttpURLConnection conn = null;
		double[] picSize = new double[2];
		Docpsimagebean docpsimagebean;
		JSONObject jsonObject;

		StringBuffer imgFilePath;
		String imgFilePathPrefix;
		List<Docpsimagebean> list = new ArrayList<>();
		long ll1 = System.currentTimeMillis();

		boolean flag = false;
		try {

			if(isHav(urlTask,bean.getAid())){
				return "1";
			}

			DecimalFormat dformat = new DecimalFormat("#.00");
			String nowTime = DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME);

			String year = nowTime.substring(0, 4);//年份
			String month = nowTime.substring(5, 7);//月份
			String day = nowTime.substring(8, 10);//日

			String scanftpPath = CommonData.FTP_SAVE_PATH + year + "/" + CommonData.WHOLEFOLDER + "/" + year + month + day + "/";

			file = new File(bean.getUrl());

			int dot = file.getName().lastIndexOf('.');

			String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
			String imgFolderPath = null;
			imgFolderPath = CommonData.PDF_LOCAL_PATH + File.separator + imagePDFName;
			if (createDirectory(imgFolderPath)) {
				long ll2 = System.currentTimeMillis();
				LogHelper.info("获取图片名称耗时："+(ll2-ll1));
				url = new URL(urlTask);// new一个URL对象
				conn = (HttpURLConnection) url.openConnection();// 打开链接
				conn.setRequestMethod("GET");// 设置请求方式为"GET"
				conn.setConnectTimeout(5 * 1000);// 超时响应时间为5秒
				in = conn.getInputStream();// 通过输入流获取图片数据
//				in = new FileInputStream(new File(bean.getOldUrl()));
				long ll3 = System.currentTimeMillis();
				LogHelper.info("获取处理流耗时："+(ll3-ll2));
				pdDocument = PDDocument.load(in);
				renderer = new PDFRenderer(pdDocument);
				// dpi越大转换后越清晰，相对转换速度越慢
				reader = new PdfReader(urlTask);
				int pages = reader.getNumberOfPages();
				reader = null;
				long ll4 = System.currentTimeMillis();
				LogHelper.info("获取PDF文件耗时："+(ll4-ll3));
				for (int i = 0; i < pages; i++) {
					long l11 = System.currentTimeMillis();

					imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
					imgFilePath = new StringBuffer();
					imgFilePath.append(imgFilePathPrefix);
					imgFilePath.append("_");
					imgFilePath.append(String.valueOf(i + 1));
					imgFilePath.append("."+CommonData.PDF_TO_IMAGE);
					dstFile = new File(imgFilePath.toString());
					imgFilePath.setLength(0);

					BufferedImage image = renderer.renderImageWithDPI(i, CommonData.PDF_TO_IMAGE_DPI);

					picSize[0] = Double.parseDouble(dformat.format(image.getWidth() * 2.54 / CommonData.PDF_TO_IMAGE_DPI));
					picSize[1] = Double.parseDouble(dformat.format(image.getHeight() * 2.54 / CommonData.PDF_TO_IMAGE_DPI));
					long l12 = System.currentTimeMillis();
					LogHelper.info("获取图片流耗时："+(l12-l11));

					//若该图片之前已处理 则不再生成图片，只计算图片大小
					if(!CacheBean.set.contains(urlTask)||i>0){
						long l1 = System.currentTimeMillis();
						ImageIO.write(image, CommonData.PDF_TO_IMAGE, dstFile);
						long l2 = System.currentTimeMillis();
						LogHelper.info("图片生成耗时："+(l2-l1));
						handleDpi(dstFile,CommonData.PDF_TO_IMAGE_DPI,CommonData.PDF_TO_IMAGE_DPI);
						long l3 = System.currentTimeMillis();
						LogHelper.info("图片修改dpi耗时："+(l3-l2));

						if(FtpHelper.uploadFileFromUrlToFtp(dstFile,imagePDFName+"_"+String.valueOf(i + 1)+".jpg",scanftpPath)==0){
							LogHelper.info("JPG上传出现异常！");
							return "-1";
						}
						long l4 = System.currentTimeMillis();
						LogHelper.info("上传图片至ftp耗时："+(l4-l3));
						flag = true;
						CacheBean.addTask(urlTask);
					}

					image.flush();
					image=null;

					docpsimagebean = new Docpsimagebean();
					docpsimagebean.setDispTime(bean.getDispTime());
					docpsimagebean.setMediaType(bean.getType());
					docpsimagebean.setAid(Long.parseLong(bean.getAid()));
					docpsimagebean.setCreateTime(DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME));
					docpsimagebean.setHeight((float) picSize[1]);
					docpsimagebean.setWidth((float) picSize[0]);
					docpsimagebean.setPicSn(i+1);
					docpsimagebean.setImagePath(imagePDFName+"_"+String.valueOf(i + 1)+".jpg");
					docpsimagebean.setServerID(6);
					docpsimagebean.setType(4);

					//TODO 图片入库
					result = HttpClientPoolUtil.execute(CommonData.DOCPICIMAGE_API,JSON.toJSONString(docpsimagebean));
					jsonObject = JSON.parseObject(result);
					if(!"0".equals(StringHelper.toTrim(jsonObject.getString("code")))){
						return "-1";
					}
					list.add(docpsimagebean);

				}

				CachePicBean.addTask(urlTask,list);

				if(flag){//删除文件
					FileHelper.delFile(CommonData.PDF_LOCAL_PATH+"/"+imagePDFName);
				}
				result = "1";
			} else {
				LogHelper.info("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
				result = "-1";
			}

		} catch (Exception e) {
			e.printStackTrace();
			LogHelper.error("PDF转JPG出现异常！bean："+JSON.toJSONString(bean),e);
			result = "-1";
		}finally {
			if(reader!=null){
				reader.close();
			}

			if(pdDocument!=null){
				try {
					pdDocument.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if(conn!=null){
				conn.disconnect();
			}
			LogHelper.info("是否生成图片："+flag+" 图片转换总耗时："+(System.currentTimeMillis()-ll1));

		}

		return result;
	}

	private boolean isHav(String url,String aid){
		List<Docpsimagebean> list;
		String result;
		JSONObject jsonObject;
		if(CachePicBean.map.containsKey(url)){
			list = CachePicBean.map.get(url);
			if(list!=null&&list.size()>0){
				for(Docpsimagebean bean:list){
					bean.setAid(Long.parseLong(aid));
					// 图片入库
					result = HttpClientPoolUtil.execute(CommonData.DOCPICIMAGE_API,JSON.toJSONString(bean));
					jsonObject = JSON.parseObject(result);
					if(!"0".equals(StringHelper.toTrim(jsonObject.getString("code")))){
						return false;
					}
				}
				LogHelper.info("已处理图片调用存储过程入sql："+ list.size());
				return true;
			}
		}
		return false;
	}


	/**
	 * 创建路径
	 * @return boolean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:45 2018/3/21
	 * @param folder
	 */
	private boolean createDirectory(String folder) {

		File dir = new File(folder);
		if (dir.exists()) {
			return true;
		} else {
			return dir.mkdirs();
		}
	}

	/**
	 * 改变图片DPI
	 *
	 * @param file
	 * @param xDensity
	 * @param yDensity
	 */
	private void handleDpi(File file,int xDensity, int yDensity) {
		try {
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
			jpegEncoder=null;
			jpegEncodeParam=null;
		} catch (IOException e) {
			e.printStackTrace();
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
}
