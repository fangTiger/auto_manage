package com.xl.manage.bean.esBean;

/**
 * 数据插入实体
 * @Author:bagen
 * @create 2017-07-18 13:35
 **/
public class ConsumeInsertBean {

    private String storageType;//常用/历史数据类型
    private String esType;//ValidData/AllData 有效数据/全部数据
    private String sourceType;//article/bbs/weibo
    private String paramsList;//数据集合

    public ConsumeInsertBean() {
    }

    public ConsumeInsertBean(String storageType, String esType, String sourceType, String paramsList) {
        this.storageType = storageType;
        this.esType = esType;
        this.sourceType = sourceType;
        this.paramsList = paramsList;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getEsType() {
        return esType;
    }

    public void setEsType(String esType) {
        this.esType = esType;
    }

    public String getParamsList() {
        return paramsList;
    }

    public void setParamsList(String paramsList) {
        this.paramsList = paramsList;
    }
}
