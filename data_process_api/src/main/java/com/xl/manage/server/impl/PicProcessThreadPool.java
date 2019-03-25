/*
package com.xl.manage.server.impl;

import com.xl.manage.bean.RequestBean;
import com.xl.manage.server.DataProcessServer;
import com.xl.tools.LogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

*/
/**
 * 图片任务处理线程池
 * @Author:lww
 * @Date:10:37 2018/3/23
 *//*

public class PicProcessThreadPool {

	private static final int corePoolSize = 4;
	private static final int maximumPoolSize = 9;
	private static final int keepAliveTime = 200;
	private static final int capacity = 1;

	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue<>(capacity));

	public static synchronized String putTask(RequestBean bean){

		String result;

		PicTask task;

		boolean isTrue = false;

		try{
			for(int i=1;i<=3;i++){
				if(executor.getPoolSize()<maximumPoolSize){
					task = new PicTask(bean);
					executor.execute(task);
					isTrue = true;
					break;
				}else{
					Thread.sleep(9000);
				}
			}

			if(!isTrue){
				result = "-2";//队列已满
			}else{
				result = "1";//操作成功
			}
		}catch (Exception e){
			LogHelper.error("图片任务增加出现异常！",e);
			result = "-1";//后台异常
		}
		return result;

	}


}
*/
