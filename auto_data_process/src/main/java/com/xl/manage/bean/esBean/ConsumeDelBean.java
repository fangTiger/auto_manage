package com.xl.manage.bean.esBean;

/**
 * 删除实体
 * @Author:bagen
 * @create 2017-08-01 18:56
 **/
public class ConsumeDelBean {

    private String sourceType;//weibo/article/bbs
    private String aids;//多个aid
    private String mediaType;//媒体类型
    private String esType;//有效/全部数据

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getAids() {
        return aids;
    }

    public void setAids(String aids) {
        this.aids = aids;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getEsType() {
        return esType;
    }

    public void setEsType(String esType) {
        this.esType = esType;
    }
}
