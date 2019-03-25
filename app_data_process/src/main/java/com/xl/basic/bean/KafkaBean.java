package com.xl.basic.bean;

import java.util.List;

/**
 * @Author:lww
 * @Date:11:50 2018/3/14
 */
public class KafkaBean {

	private String beanVersion;//kafka参数对象版本
	private KafkaArticleBean article;//卡夫卡文章对象信息
	private List<Docpicbean> docpic;
	private List<Docpsimagebean> docpsimage;
	private List<Doctablebean> doctable;

	public String getBeanVersion() {
		return beanVersion;
	}

	public void setBeanVersion(String beanVersion) {
		this.beanVersion = beanVersion;
	}

	public KafkaArticleBean getArticle() {
		return article;
	}

	public void setArticle(KafkaArticleBean article) {
		this.article = article;
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
