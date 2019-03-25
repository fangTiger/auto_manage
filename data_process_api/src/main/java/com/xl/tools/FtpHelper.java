package com.xl.tools;

import com.xl.manage.common.CommonData;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author:lww
 * @Date:15:44 2018/3/21
 */
public class FtpHelper {
	/**
	 * 上传网络文件至FTP
	 * @Title: uploadFileFromUrlToFtp
	 * @data:2016-2-23上午9:59:10
	 * @author:zhangshiyuan
	 *
	 * @param file
	 * @throws Exception
	 */
	public static int uploadFileFromUrlToFtp(File file, String fileName, String savePath) throws Exception{
		int flag = 0;
		InputStream in = null;
		try {
			in = new FileInputStream(file);// 通过输入流获取图片数据
			int available = in.available();
			if(available>0){//如果找到了文件
				uploadFtpFile(CommonData.FTP_URL, CommonData.FTP_PORT,
						CommonData.FTP_USERNAME, CommonData.FTP_PASSWORD,
						savePath, fileName, in);
			}
			flag = 1;
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return flag;
	}

	/**
	 * Description: 向FTP服务器上传文件
	 * @Version1.0 Jul 27, 2008 4:31:09 PM by 崔红保（cuihongbao@d-heaven.com）创建
	 * @param url FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param path FTP服务器保存目录
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input 输入流
	 * @return 成功返回true，否则返回false
	 * @throws Exception
	 */
	public static boolean uploadFtpFile(String url,String port,String username,
										String password, String path, String filename, InputStream input) throws Exception {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.setControlEncoding("utf-8");
			ftp.connect(url, Integer.parseInt(port));//连接FTP服务器
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);//登录
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			String[] paths = path.split("/");//创建多层文件夹
			String beforePath;
			for (int i = 0; i < paths.length; i++) {
				beforePath = "";
				for (int j = 0; j < i; j++) {
					if(!"".equals(StringHelper.toTrim(paths[j])))
						beforePath += paths[j]+"/";
				}
				if(!"".equals(StringHelper.toTrim(paths[i]))){
					ftp.makeDirectory("/"+beforePath+paths[i]+"/");
				}
			}
			ftp.changeWorkingDirectory(path);
			ftp.enterLocalPassiveMode();
			// 如果文件不存在
			if(!FileHelper.isFileExist("//"+url+path+filename))
				ftp.storeFile(filename, input);
			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
}
