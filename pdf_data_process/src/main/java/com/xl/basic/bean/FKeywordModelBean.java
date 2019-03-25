package com.xl.basic.bean;

/**
 * @Author:lww
 * @Date:13:54 2017/11/18
 */
public class FKeywordModelBean {
	private String keyWord;
	private int kType;

	public FKeywordModelBean() {
		this.keyWord = "";
		this.kType = 1;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public int getkType() {
		return kType;
	}

	public void setkType(int kType) {
		this.kType = kType;
	}
}
