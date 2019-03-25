package com.xl.bean.article;

/**
 * @Author:lww
 * @Date:16:04 2018/5/3
 */
public class EsFieldConfBean {
	//字段名称
	private String name;
	//字段值
	private String value;
	//关系运算符
	private EsRelationConfBean relationOperator;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public EsRelationConfBean getRelationOperator() {
		return relationOperator;
	}

	public void setRelationOperator(EsRelationConfBean relationOperator) {
		this.relationOperator = relationOperator;
	}
}
