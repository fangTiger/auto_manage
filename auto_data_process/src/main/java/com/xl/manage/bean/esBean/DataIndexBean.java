package com.xl.manage.bean.esBean;

/**
 * Created by Administrator on 2017/7/8.
 */
public class DataIndexBean {

    //索引或数据库名称
    private String name;
    //索引别名
    private String aliasName;
    //数据开始时间
    private String startTime;
    //数据结束时间
    private String endTime;
    //数据类型
    private SourceTypeBean sourceType;
    //数据库类型
    private DataBaseTypeBean dataBaseType;
    //索引类型
    private EsTypeConfBean esTypeConfBean;
    //数据源IP地址
    private String dataSource;

    public DataIndexBean() {
    }

    public DataIndexBean(String name, String aliasName, String startTime, String endTime, SourceTypeBean sourceType, DataBaseTypeBean dataBaseType, EsTypeConfBean esTypeConfBean, String dataSource) {
        this.name = name;
        this.aliasName = aliasName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sourceType = sourceType;
        this.dataBaseType = dataBaseType;
        this.esTypeConfBean = esTypeConfBean;
        this.dataSource = dataSource;
    }

    public String getName(){return name;}
    public void setName(String name){this.name=name;}

    public String getAliasName(){return aliasName;}
    public void setAliasName(String aliasName){this.aliasName=aliasName;}

    public String getStartTime(){return startTime;}
    public void  setStartTime(String startTime){this.startTime=startTime;}

    public String getEndTime(){return endTime;}
    public void  setEndTime(String endTime){this.endTime=endTime;}

    public SourceTypeBean getSourceType(){return sourceType;}
    public void  setSourceType(SourceTypeBean sourceType){this.sourceType=sourceType;}

    public DataBaseTypeBean getDataBaseType(){return dataBaseType;}
    public void  setDataBaseType(DataBaseTypeBean dataBaseType){this.dataBaseType=dataBaseType;}

    public String getDataSource(){return dataSource;}
    public void  setDataSource(String dataSource){this.dataSource=dataSource;}

    public EsTypeConfBean getEsTypeConfBean() {
        return esTypeConfBean;
    }
    public void setEsTypeConfBean(EsTypeConfBean esTypeConfBean) {
        this.esTypeConfBean = esTypeConfBean;
    }
}
