package com.xl.basic.bean;

import com.xl.manage.bean.AdvBean;
import com.xl.manage.bean.MediaBean;

/**
 * 媒体库API结果解析bean
 * @Author:lww
 * @Date:16:10 2017/11/18
 */
public class MediaResultBean {
	private MediaBean m;
	private AdvBean a;
	private String layout;
	//临时扩展的多个广告价值
	private float focusAdvPrice;//焦点图价值
	private float linkAdvPrice;//文字链价值
	private float programaAdvPrice;//专题价值
	private float subjectAdvPrice;//视频栏目价值
	private String error;//错误信息

	public MediaResultBean() {
		this.m = new MediaBean();
		this.a = new AdvBean();
		this.layout = "";
		this.focusAdvPrice = 0.0f;
		this.linkAdvPrice = 0.0f;
		this.programaAdvPrice = 0.0f;
		this.subjectAdvPrice = 0.0f;
		this.error = "";
	}

	public MediaBean getM() {
		return m;
	}

	public void setM(MediaBean m) {
		this.m = m;
	}

	public AdvBean getA() {
		return a;
	}

	public void setA(AdvBean a) {
		this.a = a;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public float getFocusAdvPrice() {
		return focusAdvPrice;
	}

	public void setFocusAdvPrice(float focusAdvPrice) {
		this.focusAdvPrice = focusAdvPrice;
	}

	public float getLinkAdvPrice() {
		return linkAdvPrice;
	}

	public void setLinkAdvPrice(float linkAdvPrice) {
		this.linkAdvPrice = linkAdvPrice;
	}

	public float getProgramaAdvPrice() {
		return programaAdvPrice;
	}

	public void setProgramaAdvPrice(float programaAdvPrice) {
		this.programaAdvPrice = programaAdvPrice;
	}

	public float getSubjectAdvPrice() {
		return subjectAdvPrice;
	}

	public void setSubjectAdvPrice(float subjectAdvPrice) {
		this.subjectAdvPrice = subjectAdvPrice;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
