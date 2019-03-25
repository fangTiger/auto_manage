package com.xl.manage.bean;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author:lww
 * @Date:17:35 2018/3/22
 */
public class CacheBean {

	public static int maxTask = 3000;
	public static Set<String> set = new HashSet<>();
	public static Queue<String> queue = new LinkedBlockingQueue<String>();

	public static boolean isHav(String task){
		if(!set.contains(task)){
			addTask(task);
			return false;
		}else{
			return true;
		}
	}

	public static synchronized void addTask(String task){
		if(maxTask>set.size()){
			set.add(task);
			queue.add(task);
		}else{
			queue.add(task);
			String taskFirst = queue.poll();
			set.remove(taskFirst);
			set.add(task);
		}
	}

	public static void main(String[] args) {
		/*for(int i=1;i<=30;i++){
			CacheBean.isHav(i+"");
		}

		for(String set:CacheBean.set){
			System.out.println(set);
		}*/
	}
}
