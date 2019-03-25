/**
  * Copyright 2017 bejson.com 
  */
package com.xl.manage.bean.esBean.resultBean;
import java.util.List;

/**
 * Auto-generated: 2017-07-10 20:4:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ArticleHitsBean {

    private int total;
    private String max_score;
    private List<HitsArticleBean> hits;


    public void setTotal(int total) {
         this.total = total;
     }
     public int getTotal() {
         return total;
     }

    public void setMax_score(String max_score) {
         this.max_score = max_score;
     }
    public String getMax_score() {
         return max_score;
     }

    public List<HitsArticleBean> getHits() {
        return hits;
    }

    public void setHits(List<HitsArticleBean> hits) {
        this.hits = hits;
    }
}