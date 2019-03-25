package com.xl.bean.article;

import java.util.List;

/**
 * 过略条件
 * @Author:lww
 * @Date:10:07 2018/5/4
 */
public class RuleFilterBean {
	private String ruleId;//规则ID
	private String orgId;//机构
	private String monitorId;//监测项
	private List<FilterCondition> filterCondition;//过略条件

	public RuleFilterBean() {
	}

	public RuleFilterBean(String ruleId,String orgId, String monitorId, List<FilterCondition> filterCondition) {
		this.ruleId = ruleId;
		this.orgId = orgId;
		this.monitorId = monitorId;
		this.filterCondition = filterCondition;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(String monitorId) {
		this.monitorId = monitorId;
	}

	public List<FilterCondition> getFilterCondition() {
		return filterCondition;
	}

	public void setFilterCondition(List<FilterCondition> filterCondition) {
		this.filterCondition = filterCondition;
	}
}
