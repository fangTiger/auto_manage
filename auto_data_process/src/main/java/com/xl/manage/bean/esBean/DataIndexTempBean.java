package com.xl.manage.bean.esBean;

/**
 * 索引信息表
 * Created by Administrator on 2017/7/8.
 */
public class DataIndexTempBean {

    //索引或数据库名称
    private String name;
    //索引别名
    private String aliasName;
    //数据开始时间
    private String startTime;
    //数据结束时间
    private String endTime;
    //数据类型
    private String sourceType;
    //数据库类型
    private String dataBaseType;
    //索引类型
    private String esType;
    //数据源IP地址
    private String dataSource;

    public DataIndexTempBean() {
    }

    public DataIndexTempBean(String name, String aliasName, String startTime, String endTime, String sourceType, String dataBaseType, String esType, String dataSource) {
        this.name = name;
        this.aliasName = aliasName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sourceType = sourceType;
        this.dataBaseType = dataBaseType;
        this.esType = esType;
        this.dataSource = dataSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getDataBaseType() {
        return dataBaseType;
    }

    public void setDataBaseType(String dataBaseType) {
        this.dataBaseType = dataBaseType;
    }

    public String getEsType() {
        return esType;
    }

    public void setEsType(String esType) {
        this.esType = esType;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
