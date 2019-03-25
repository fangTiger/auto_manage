package com.xl.manage.bean.esBean;

/**
 * Created by Administrator on 2017/7/8.
 */
public enum SourceTypeBean {
    Article ,//文章
    BBS, //论坛
    WeiBo //微博
    ;

    /**
     * 获取索引类型
     * @Author: lww
     * @Description:
     * @Date: 18:57 2017/7/28
     * @params:
     */
    public static SourceTypeBean getSourceType(String sourceType)throws Exception{
        if("article".equals(sourceType.toLowerCase())){
            return SourceTypeBean.Article;
        }else if("bbs".equals(sourceType.toLowerCase())){
            return SourceTypeBean.BBS;
        }else{
            return SourceTypeBean.WeiBo;
        }
    }
}

