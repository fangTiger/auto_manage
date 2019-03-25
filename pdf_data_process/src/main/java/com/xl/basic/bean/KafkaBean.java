package com.xl.basic.bean;

import java.util.List;

/**
 * @Author:lww
 * @Date:13:41 2018/3/21
 */
public class KafkaBean {

	private String beanVersion;//kafka参数对象版本
	private KafkaPdfArticleBean pdfArticle;//pdf入卡夫卡文章对象信息
	private List<Docpicbean> docpic;
	private List<Docpsimagebean> docpsimage;
	private List<Doctablebean> doctable;

	public String getBeanVersion() {
		return beanVersion;
	}

	public void setBeanVersion(String beanVersion) {
		this.beanVersion = beanVersion;
	}

	public KafkaPdfArticleBean getPdfArticle() {
		return pdfArticle;
	}

	public void setPdfArticle(KafkaPdfArticleBean pdfArticle) {
		this.pdfArticle = pdfArticle;
	}

	public List<Docpicbean> getDocpic() {
		return docpic;
	}

	public void setDocpic(List<Docpicbean> docpic) {
		this.docpic = docpic;
	}

	public List<Docpsimagebean> getDocpsimage() {
		return docpsimage;
	}

	public void setDocpsimage(List<Docpsimagebean> docpsimage) {
		this.docpsimage = docpsimage;
	}

	public List<Doctablebean> getDoctable() {
		return doctable;
	}

	public void setDoctable(List<Doctablebean> doctable) {
		this.doctable = doctable;
	}
}
