package com.xl.tools;

import java.io.*;
import java.util.Arrays;
import java.util.List;

/**
 * @Author:lww
 * @Date:18:34 2017/11/1
 */
public class FileHelper {

	/**
	 * 删除指定文件内容
	 *
	 * @param words
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 11:45 2017/10/17
	 */
	public static String delFileContent(String filePath, String words[]) {

		BufferedWriter out;
		File filename;
		InputStreamReader reader = null;
		BufferedReader br;
		StringBuffer line = new StringBuffer();
		String lineTxt;
		List<String> list;

		String result = "-1";
		try {
			list = Arrays.asList(words);
			/* 写入Txt文件 */
			reader = new InputStreamReader(
					new FileInputStream(filePath), "UTF-8"); // 建立一个输入流对象reader
			br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			lineTxt = br.readLine();
			while (lineTxt != null) {
				if (!list.contains(lineTxt)) {
					line.append(lineTxt + "\r\n");
				}
				lineTxt = br.readLine();// 一次读入一行数据
			}

			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
			out.write(line.toString());
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
			result = "1";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 写入文件内容
	 *
	 * @param words
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 11:45 2017/10/17
	 */
	public static String writeFileContent(String filePath, List<String> words) {

		BufferedWriter out=null;
		File filename;
		InputStreamReader reader = null;
		BufferedReader br;
		StringBuffer line = new StringBuffer();
		String lineTxt;

		String result = "-1";
		try {
			/* 写入Txt文件 */
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
			if(words!=null&&words.size()>0){
				for(String word:words){
					out.write(word + "\r\n");
				}
			}
			out.flush(); // 把缓存区内容压入文件
			result = "1";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				try{
					out.close(); // 最后记得关闭文件
				}catch (Exception e){
				}
			}
		}
		return result;
	}

	/**
	 * 写入文件内容
	 *
	 * @param text
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 11:45 2017/10/17
	 */
	public static String writeFileContent(String filePath, String text) {

		BufferedWriter out=null;
		File filename;
		InputStreamReader reader = null;
		BufferedReader br;
		StringBuffer line = new StringBuffer();
		String lineTxt;

		String result = "-1";
		try {
			/* 写入Txt文件 */
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8"));
			out.write(text);
			out.flush(); // 把缓存区内容压入文件
			result = "1";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				try{
					out.close(); // 最后记得关闭文件
				}catch (Exception e){
				}
			}
		}
		return result;
	}


	/**
	 * 读取文件
	 *
	 * @param
	 * @return java.lang.String
	 * @Author: lww
	 * @Description:
	 * @Date: 11:44 2017/10/17
	 */
	public static String readConfigNoWords(String filePath) {

		/* 读入TXT文件 */
		InputStreamReader reader = null;
		BufferedReader br = null;
		StringBuffer line = new StringBuffer();
		String lineTxt;

		try {

			reader = new InputStreamReader(
					new FileInputStream(filePath), "UTF-8"); // 建立一个输入流对象reader
			br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			lineTxt = br.readLine();
			while (lineTxt != null) {
				line.append(lineTxt + ","); // 一次读入一行数据
				lineTxt = br.readLine();
			}
		} catch (Exception e) {
			LogHelper.error("------------------------读取file文章出现异常！-------------------------------", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return line.toString().endsWith(",") ? line.toString().substring(0, line.length() - 1) : line.toString();
	}

	/**
	 * 复制单个文件
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 10:37 2017/11/29
	 * @param oldPath 原文件路径 如：c:/fqf.txt
	 * @param newPath 复制后路径 如：f:/fqf.txt
	 */
	public static void copyFile(String oldPath, String newPath) throws IOException {

		byte[] buffer = new byte[1444];
		InputStream inStream = null;
		FileOutputStream fs = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { //文件存在时
				inStream = new FileInputStream(oldPath); //读入原文件
				fs = new FileOutputStream(newPath);
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; //字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			LogHelper.error("----复制单个文件操作出错---param[oldPath:"+oldPath+",newPath:"+newPath+"]",e);
		}finally {
			if(inStream!=null){
				inStream.close();
			}
			if(fs!=null){
				fs.close();
			}
		}
	}

	/**
	 * 删除指定文件
	 * @return boolean
	 * @Author: lww
	 * @Description:
	 * @Date: 10:54 2017/11/29
	 * @param fileName
	 */
	public static boolean delFile(String fileName){

		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("删除文件失败:" + fileName + "不存在！");
			return false;
		} else {
			return file.delete();
		}
	}

	/**
	 * 复制整个文件夹内容
	 * @param oldPath String 原文件路径 如：c:/fqf
	 * @param newPath String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
			File a=new File(oldPath);
			String[] file=a.list();
			File temp=null;
			for (int i = 0; i < file.length; i++) {
				if(oldPath.endsWith(File.separator)){
					temp=new File(oldPath+file[i]);
				}
				else{
					temp=new File(oldPath+File.separator+file[i]);
				}

				if(temp.isFile()){
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" +
							(temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ( (len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if(temp.isDirectory()){//如果是子文件夹
					copyFolder(oldPath+"/"+file[i],newPath+"/"+file[i]);
				}
			}
		}catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	//删除文件夹以及文件夹下所有文件
	//param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); //删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//删除指定文件夹下所有文件
	//param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
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
				delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);//再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}


	/**
	 * 判断路径或文件是否存在，若不存在创建
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 14:14 2017/12/15
	 * @param path
	 */
	public static void isExitPath(String path){

		String[] folders = path.split("/");
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
			LogHelper.error("创建文件夹出现异常path["+path+"]",e);
		}
	}

	/**
	 * 判断路径或文件是否存在，若不存在创建
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 14:14 2017/12/15
	 * @param path
	 */
	public static boolean isExit(String path){

		String[] folders = path.split("/");
		String folder = folders[0] + "/";
		File myFilePath;
		try {
			for (int i = 1; i < folders.length; i++) {
				folder = folder + folders[i] + "/";
				myFilePath = new File(folder);
				if (!myFilePath.exists()) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			LogHelper.error("判断路径或文件是否存在出现异常path["+path+"]",e);
		}
		return false;
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
