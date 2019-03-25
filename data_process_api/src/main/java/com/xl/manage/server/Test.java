package com.xl.manage.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.realobjects.pdfbaker.pdf.PdfReader;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.xl.manage.bean.CacheBean;
import com.xl.manage.bean.CachePicBean;
import com.xl.manage.bean.Docpsimagebean;
import com.xl.manage.common.CommonData;
import com.xl.tools.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:lww
 * @Date:13:55 2018/3/29
 */
public class Test {
	public static void main(String[] args) {


		String filePath = "http://192.168.10.29/pdf/%E5%A4%A7%E6%B2%B3%E6%8A%A5/DHB2018-03-29-F07.pdf";

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

			DecimalFormat dformat = new DecimalFormat("#.00");
			String nowTime = DateHelper.getNowDate(DateHelper.FMT_DATE_DATETIME);

			String year = nowTime.substring(0, 4);//年份
			String month = nowTime.substring(5, 7);//月份
			String day = nowTime.substring(8, 10);//日

			String scanftpPath = CommonData.FTP_SAVE_PATH + year + "/" + CommonData.WHOLEFOLDER + "/" + year + month + day + "/";

			file = new File(filePath);

			int dot = file.getName().lastIndexOf('.');

			String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
			String imgFolderPath = null;
			imgFolderPath = "D:\\javatest\\image" + File.separator + imagePDFName;
			if (createDirectory(imgFolderPath)) {
				long ll2 = System.currentTimeMillis();
				LogHelper.info("获取图片名称耗时："+(ll2-ll1));
				url = new URL(filePath);// new一个URL对象
				conn = (HttpURLConnection) url.openConnection();// 打开链接
				conn.setRequestMethod("GET");// 设置请求方式为"GET"
				conn.setConnectTimeout(5 * 1000);// 超时响应时间为5秒
				in = conn.getInputStream();// 通过输入流获取图片数据
				long ll3 = System.currentTimeMillis();
				LogHelper.info("获取处理流耗时："+(ll3-ll2));
				pdDocument = PDDocument.load(in);
				renderer = new PDFRenderer(pdDocument);
				// dpi越大转换后越清晰，相对转换速度越慢
				reader = new PdfReader(filePath);
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
					if(!CacheBean.set.contains(filePath)){
						long l1 = System.currentTimeMillis();
						ImageIO.write(image, CommonData.PDF_TO_IMAGE, dstFile);
						long l2 = System.currentTimeMillis();
						LogHelper.info("图片生成耗时："+(l2-l1));
						handleDpi(dstFile,CommonData.PDF_TO_IMAGE_DPI,CommonData.PDF_TO_IMAGE_DPI);
						long l3 = System.currentTimeMillis();
						LogHelper.info("图片修改dpi耗时："+(l3-l2));

						/*if(FtpHelper.uploadFileFromUrlToFtp(dstFile,imagePDFName+"_"+String.valueOf(i + 1)+".jpg",scanftpPath)==0){
							LogHelper.info("JPG上传出现异常！");
						}
						long l4 = System.currentTimeMillis();
						LogHelper.info("上传图片至ftp耗时："+(l4-l3));
						flag = true;*/
					}

					image.flush();
					image=null;


				}

				/*if(list!=null&&list.size()>0){
					LogHelper.info("调用存储过程入sql："+ list.size());
				}*/
				result = "1";
			} else {
				LogHelper.info("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
				result = "-1";
			}
		} catch (Exception e) {
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

	}

	/**
	 * 创建路径
	 * @return boolean
	 * @Author: lww
	 * @Description:
	 * @Date: 14:45 2018/3/21
	 * @param folder
	 */
	private static boolean createDirectory(String folder) {

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
	private static void handleDpi(File file,int xDensity, int yDensity) {
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
}
