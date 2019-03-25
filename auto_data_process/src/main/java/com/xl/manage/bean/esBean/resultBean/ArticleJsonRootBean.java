/**
  * Copyright 2017 bejson.com 
  */
package com.xl.manage.bean.esBean.resultBean;

/**
 * Auto-generated: 2017-07-10 20:4:22
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ArticleJsonRootBean {

    private int took;
    private boolean timed_out;
    private ArticleShardsBean _shards;
    private ArticleHitsBean hits;
    public void setTook(int took) {
         this.took = took;
     }
     public int getTook() {
         return took;
     }

    public void setTimed_out(boolean timed_out) {
         this.timed_out = timed_out;
     }
     public boolean getTimed_out() {
         return timed_out;
     }

    public void set_shards(ArticleShardsBean _shards) {
         this._shards = _shards;
     }
     public ArticleShardsBean get_shards() {
         return _shards;
     }

    public void setHits(ArticleHitsBean hits) {
         this.hits = hits;
     }
     public ArticleHitsBean getHits() {
         return hits;
     }

}