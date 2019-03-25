package com.xl.manage.bean;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author:lww
 * @Date:17:35 2018/3/22
 */
public class CachePicBean {

	public static int maxTask = 3000;
	public static Map<String,List<Docpsimagebean>> map = new HashMap<>();
	public static Queue<String> queue = new LinkedBlockingQueue<String>();

	public static boolean isHav(String task){
		if(!map.containsKey(task)){
			return false;
		}else{
			return true;
		}
	}

	public static synchronized void addTask(String task,List<Docpsimagebean> docpsimagelist){
		if(maxTask>map.size()){
			map.put(task,docpsimagelist);
			queue.add(task);
		}else{
			queue.add(task);
			String taskFirst = queue.poll();
			map.remove(taskFirst);
			map.put(task,docpsimagelist);
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
