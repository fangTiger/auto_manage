package com.xl.bean.article;

public class AdvBean{
	private Integer pv;
    private Float advPrice;
    private Integer uv;
    private Integer alexaRank;

	public AdvBean() {
		this.pv = 0;
		this.advPrice = 0f;
		this.uv = 0;
		this.alexaRank = 0;
	}

	public AdvBean(Integer pv, Float advPrice, Integer uv, Integer alexaRank) {
		this.pv = pv;
		this.advPrice = advPrice;
		this.uv = uv;
		this.alexaRank = alexaRank;
	}

	@Override
	public String toString() {
		return "AdvOldBean{" +
				"pv=" + pv +
				", advPrice=" + advPrice +
				", uv=" + uv +
				", alexaRank=" + alexaRank +
				'}';
	}

	public Integer getPv() {
		return pv;
	}

	public void setPv(Integer pv) {
		this.pv = pv;
	}

	public Float getAdvPrice() {
		return advPrice;
	}

	public void setAdvPrice(Float advPrice) {
		this.advPrice = advPrice;
	}

	public Integer getUv() {
		return uv;
	}

	public void setUv(Integer uv) {
		this.uv = uv;
	}

	public Integer getAlexaRank() {
		return alexaRank;
	}

	public void setAlexaRank(Integer alexaRank) {
		this.alexaRank = alexaRank;
	}
}
