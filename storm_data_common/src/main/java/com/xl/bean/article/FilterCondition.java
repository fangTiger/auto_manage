package com.xl.bean.article;

import java.util.List;

/**
 * @Author:lww
 * @Date:16:02 2018/5/3
 */
public class FilterCondition {
	private String sourceType;
	private List<List<EsFieldConfBean>> elasticFields;

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public List<List<EsFieldConfBean>> getElasticFields() {
		return elasticFields;
	}

	public void setElasticFields(List<List<EsFieldConfBean>> elasticFields) {
		this.elasticFields = elasticFields;
	}



}
