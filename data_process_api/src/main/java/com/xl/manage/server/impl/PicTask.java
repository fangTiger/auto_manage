/*
package com.xl.manage.server.impl;

import com.alibaba.fastjson.JSON;
import com.realobjects.pdfbaker.pdf.PdfReader;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.xl.manage.bean.CacheBean;
import com.xl.manage.bean.CachePicBean;
import com.xl.manage.bean.Docpsimagebean;
import com.xl.manage.bean.RequestBean;
import com.xl.manage.common.CommonData;
import com.xl.tools.DateHelper;
import com.xl.tools.FtpHelper;
import com.xl.tools.LogHelper;
import com.xl.tools.StringHelper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

*/
/**
 * 任务执行
 * @Author:lww
 * @Date:11:02 2018/3/23
 *//*

public class PicTask implements Runnable {

	private RequestBean bean;

	public PicTask(RequestBean bean) {
		this.bean = bean;
	}

	*/
/**
	 * 开始执行任务
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 10:42 2018/3/23
	 * @param
	 *//*

	@Override
	public void run() {

		String result;
		try {
			result = executeTask(bean,bean.getUrl());//先处理内网
			if(!"1".equals(StringHelper.toTrim(result))){//若处理失败
				result = executeTask(bean,bean.getOldUrl());//再处理原网
				if(!"1".equals(StringHelper.toTrim(result))){//若失败
					CommonData.errorMap.put(bean.getAid(),bean);//入错误列表
				}
			}
		} catch (Exception e) {
			LogHelper.error("任务执行出现异常："+ JSON.toJSONString(bean),e);
		}
	}

	*/
/**
	 * 执行任务
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 10:46 2018/3/23
	 * @param bean
	 *//*

	public String executeTask(RequestBean bean,String urlTask) {

		String result;

		File file;
		PDDocument pdDocument=null;
		PDFRenderer renderer = null;
		PdfReader reader = null;
		InputStream in = null;
		URL url;
		HttpURLConnection conn = null;
		double[] picSize = new double[2];
		Docpsimagebean docpsimagebean;
		List<Docpsimagebean> list = new ArrayList<>();
		long ll1 = System.currentTimeMillis();

		boolean flag = true;
		try {

			if(isHav(urlTask)){
				return "1";
			}

			DecimalFormat dformat = new DecimalFormat("#.00");
			String nowTime = DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME);

			String year = nowTime.substring(0, 4);//年份
			String month = nowTime.substring(5, 7);//月份
			String day = nowTime.substring(8, 10);//日

			String scanftpPath = CommonData.FTP_SAVE_PATH + year + "/" + CommonData.WHOLEFOLDER + "_test/" + year + month + day + "/";

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
				long ll3 = System.currentTimeMillis();
				LogHelper.info("获取处理流耗时："+(ll3-ll2));
				pdDocument = PDDocument.load(in);
				renderer = new PDFRenderer(pdDocument);
				// dpi越大转换后越清晰，相对转换速度越慢
				reader = new PdfReader(urlTask);
				int pages = reader.getNumberOfPages();
				StringBuffer imgFilePath ;
				long ll4 = System.currentTimeMillis();
				LogHelper.info("获取PDF文件耗时："+(ll4-ll3));
				for (int i = 0; i < pages; i++) {
					long l11 = System.currentTimeMillis();

					String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
					imgFilePath = new StringBuffer();
					imgFilePath.append(imgFilePathPrefix);
					imgFilePath.append("_");
					imgFilePath.append(String.valueOf(i + 1));
					imgFilePath.append("."+CommonData.PDF_TO_IMAGE);
					File dstFile = new File(imgFilePath.toString());

					BufferedImage image = renderer.renderImageWithDPI(i, CommonData.PDF_TO_IMAGE_DPI);
					picSize[0] = Double.parseDouble(dformat.format(image.getWidth() * 2.54 / CommonData.PDF_TO_IMAGE_DPI));
					picSize[1] = Double.parseDouble(dformat.format(image.getHeight() * 2.54 / CommonData.PDF_TO_IMAGE_DPI));
					long l12 = System.currentTimeMillis();
					LogHelper.info("获取图片流耗时："+(l12-l11));

					//若该图片之前已处理 则不再生成图片，只计算图片大小
					if(!CacheBean.isHav(urlTask)){
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
					}else{
						flag = false;
					}

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

					list.add(docpsimagebean);

				}

				CachePicBean.addTask(urlTask,list);

				if(list!=null&&list.size()>0){
					LogHelper.info("调用存储过程入sql："+ list.size());
				}
				result = "1";
			} else {
				LogHelper.info("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
				result = "-1";
			}
		} catch (Exception e) {
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

	private boolean isHav(String url){
		List<Docpsimagebean> list;
		if(CachePicBean.isHav(url)){
			list = CachePicBean.map.get(url);
			if(list!=null&&list.size()>0){
				*/
/*for(Docpsimagebean bean:list){


				}*//*

				LogHelper.info("已处理图片调用存储过程入sql："+ list.size());
				return true;
			}
		}

		return false;
	}

	*/
/**
	 * 创建路径
	 * @return boolean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:45 2018/3/21
	 * @param folder
	 *//*

	private boolean createDirectory(String folder) {

		File dir = new File(folder);
		if (dir.exists()) {
			return true;
		} else {
			return dir.mkdirs();
		}
	}

	*/
/**
	 * 改变图片DPI
	 *
	 * @param file
	 * @param xDensity
	 * @param yDensity
	 *//*

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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
*/
