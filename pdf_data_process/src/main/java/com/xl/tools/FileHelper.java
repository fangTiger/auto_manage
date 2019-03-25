package com.xl.tools;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @author xieyan
 *
 */
public class FileHelper {

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public static void deleteAllFileInFolder(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				deleteAllFileInFolder(path + "/" + tempList[i]); // 先删除文件夹里面的文件
			}
		}
	}
	
	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 *            String
	 * @return boolean
	 */
	public static void deleteFile(String filePathAndName) {
		try {
			File myDelFile = new File(filePathAndName);
			myDelFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	public static byte[] getLocalImageToByte(String path) throws Exception{
		File file = new File(path);
		byte[] b = getByte(file);
		return b;
	}
	/**
	 * 把网络图片转换成byte数组
	 * @author xieyan
	 * @date   2012-4-10下午04:47:17
	 * @param urlString
	 * @return
	 */
	public static byte[] getWebImageToByte(String urlString){
		URL url = null;
		HttpURLConnection con = null;
		InputStream is = null;
		byte[] data = null;
		try{
			url = new URL(urlString);//创建URL
			con = (HttpURLConnection)url.openConnection();//获得连接
			con.setRequestMethod("GET");//get方式
			con.setReadTimeout(5*1000);//请求最大时间
			is = con.getInputStream();//获得流
			data = readinputstream(is);//读取流到二进制数组
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return data;
	}
	/**
	 * 将输入流转换成byte数组
	 * @author xieyan
	 * @date   2012-4-10下午04:47:35
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public static byte[] readinputstream(InputStream is) throws Exception{
		
		ByteArrayOutputStream outstream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];//缓存区
		int len = 0;
		while((len = is.read(buffer) )!= -1){
		 outstream.write(buffer, 0, len);//写入内存
		}
		is.close();
		return outstream.toByteArray();
	}
	@SuppressWarnings("unused")
	private static byte[] getByte(File file) throws Exception{
		byte[] bytes = null;
        if(file!=null){
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if(length>Integer.MAX_VALUE)   //当文件的长度超过了int的最大值
            {
                System.out.println("this file is max ");
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while(offset<bytes.length&&(numRead=is.read(bytes,offset,bytes.length-offset))>=0)
            {
                offset+=numRead;
            }
            //如果得到的字节长度和file实际的长度不一致就可能出错了
            if(offset<bytes.length)
            {
                System.out.println("file length is error");
                return null;
            }
            is.close();
        }
        return bytes;
    }
	
	public static String readTemplate(String templatePath) throws IOException{
		String result = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(templatePath),"UTF-8"));
		String line;
		while((line=reader.readLine())!=null){
			result+=line;
		}
		return result;
	}
	/**
	 * 得到基本路径到WEB-INF
	 * @Title: getBasePath
	 * @data:2012-7-24下午06:16:25
	 * @author:zxd
	 *
	 * @throws Exception
	 */
	public static String  getBase_class()throws Exception{
		String basePath = FileHelper.class.getResource("/").getPath();
		basePath = basePath.substring(0,basePath.indexOf("classes"));
		return basePath;
	}
	/**得到基本路径到webroot
	 * 
	 * @Title: getBaseWebInf
	 * @data:2012-7-24下午06:27:26
	 * @author:zxd
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getBase_WebRoot()throws Exception{
		String basePath = FileHelper.class.getResource("/").getPath();
		basePath = basePath.substring(0,basePath.indexOf("WEB-INF")-1);
		return basePath;
		
	}
	
	/**
	 * 得到文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	public static void newFolders(String folderPath){
		String[] folders = folderPath.split("/");
		String folder = folders[0] + "/";
		File myFilePath = null;
		try {
			for (int i = 1; i < folders.length; i++) {
				folder = folder + folders[i] + "/";
				myFilePath = new File(folder);
				if (!myFilePath.exists()) {
					myFilePath.mkdir();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断文件是否存在
	 * 
	 * @param filename
	 *            String 文件名
	 * @return boolean
	 * @author
	 */
	public static boolean isFileExist(String filename) {
		File file = new File(filename);
		return file.exists();
	}
}
