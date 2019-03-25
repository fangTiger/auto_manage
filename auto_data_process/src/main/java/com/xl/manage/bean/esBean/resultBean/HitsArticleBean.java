package com.xl.manage.bean.esBean.resultBean;/**
 * Created by bagen on 2017/7/10.
 *
 * @Author:bagen
 * @create 2017-07-10 21:08
 */



import com.xl.manage.bean.esBean.ArticleBean;

import java.util.List;

/**
 *
 * @Author:bagen
 * @create 2017-07-10 21:08
 **/
public class HitsArticleBean {

    private String index;
    private String type;
    private String id;
    private String score;
    private ArticleBean source;
    private List<Long> sort;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public ArticleBean getSource() {
        return source;
    }

    public void setSource(ArticleBean source) {
        this.source = source;
    }

    public List<Long> getSort() {
        return sort;
    }

    public void setSort(List<Long> sort) {
        this.sort = sort;
    }
}
