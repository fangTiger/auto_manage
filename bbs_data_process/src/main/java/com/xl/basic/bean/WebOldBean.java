package com.xl.basic.bean;

import java.util.List;

/**
 * web卡夫卡数据实体
 * @Author:lww
 * @Date:9:35 2017/11/3
 */
public class WebOldBean {

	private ArticleOldbean k_article;
	private List<Docpicbean> k_docpic;
	private List<Docpsimagebean> k_docpsimage;
	private List<Doctablebean> k_doctable;

	public ArticleOldbean getK_article() {
		return k_article;
	}

	public void setK_article(ArticleOldbean k_article) {
		this.k_article = k_article;
	}

	public List<Docpicbean> getK_docpic() {
		return k_docpic;
	}

	public void setK_docpic(List<Docpicbean> k_docpic) {
		this.k_docpic = k_docpic;
	}

	public List<Docpsimagebean> getK_docpsimage() {
		return k_docpsimage;
	}

	public void setK_docpsimage(List<Docpsimagebean> k_docpsimage) {
		this.k_docpsimage = k_docpsimage;
	}

	public List<Doctablebean> getK_doctable() {
		return k_doctable;
	}

	public void setK_doctable(List<Doctablebean> k_doctable) {
		this.k_doctable = k_doctable;
	}
}
