package com.xl.basic.bean;

/**
 * 词典对象
 * @Author:lww
 * @Date:11:41 2017/11/18
 */
public class RKeywordModelBean {

	public String oldKeyword;
	public String newKeyword;

	public RKeywordModelBean() {
		this.oldKeyword = "";
		this.newKeyword = "";
	}

	public String getOldKeyword() {
		return oldKeyword;
	}

	public void setOldKeyword(String oldKeyword) {
		this.oldKeyword = oldKeyword;
	}

	public String getNewKeyword() {
		return newKeyword;
	}

	public void setNewKeyword(String newKeyword) {
		this.newKeyword = newKeyword;
	}
}
