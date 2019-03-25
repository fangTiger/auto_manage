package com.xl.manage.bean;

public class AreaBean {

	private String cityId;
	private String city;
	private String province;
	private String country;//国家

	public AreaBean() {
		this.cityId = "";
		this.city = "";
		this.province = "";
		this.country = "";
	}

	public AreaBean(String cityId, String city, String province, String country) {
		this.cityId = cityId;
		this.city = city;
		this.province = province;
		this.country = country;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
