package com.xl.basic.bean;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:lww
 * @Date:11:21 2018/5/3
 */
public class AtomicIntegerBean {
	private static AtomicInteger atomicInteger = new AtomicInteger(0);

	/**
	 * 递增并获取
	 * @return void
	 * @Author: lww
	 * @Description:
	 * @Date: 11:23 2018/5/3
	 * @param
	 */
	public static int addAndGet(){
		return atomicInteger.incrementAndGet();
	}

	/**
	 * 递减并获取
	 * @return int
	 * @Author: lww
	 * @Description:
	 * @Date: 11:25 2018/5/3
	 * @param
	 */
	public static int reduceAndGet(){
		return atomicInteger.decrementAndGet();
	}

	public static int get(){
		return atomicInteger.get();
	}
}
