package com.xl.manage.bean;

/**
 * code预警用户联合查询结果
 * @Author:lww
 * @Date:17:33 2017/11/28
 */
public class WarnCodeUserR {
	private String name;
	private String code;
	private Integer level;
	private Integer codeType;
	private String desc;
	private Integer userId;
	private String openId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getCodeType() {
		return codeType;
	}

	public void setCodeType(Integer codeType) {
		this.codeType = codeType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
