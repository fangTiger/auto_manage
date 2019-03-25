package com.xl.manage.bean.esBean;

public class SigsBean {
	
	private String sig;
	private String sigall;
	private Long indexSig;
	private Long indexSigall;

	public SigsBean() {
	}

	public SigsBean(String sig, String sigall, Long indexSig, Long indexSigall) {
		this.sig = sig;
		this.sigall = sigall;
		this.indexSig = indexSig;
		this.indexSigall = indexSigall;
	}

	public String getSig() {
		return sig;
	}

	public void setSig(String sig) {
		this.sig = sig;
	}

	public String getSigall() {
		return sigall;
	}

	public void setSigall(String sigall) {
		this.sigall = sigall;
	}

	public Long getIndexSig() {
		return indexSig;
	}

	public void setIndexSig(Long indexSig) {
		this.indexSig = indexSig;
	}

	public Long getIndexSigall() {
		return indexSigall;
	}

	public void setIndexSigall(Long indexSigall) {
		this.indexSigall = indexSigall;
	}

	@Override
	public String toString() {
		return "SigsBean{" +
				"sig='" + sig + '\'' +
				", sigall='" + sigall + '\'' +
				", indexSig=" + indexSig +
				", indexSigall=" + indexSigall +
				'}';
	}
}
