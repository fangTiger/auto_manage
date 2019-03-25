package com.xl.manage.bean.esBean;

/**
 * 修改数据库任务实体
 * @Author:bagen
 * @create 2017-07-31 10:15
 **/
public class ConsumeTaskCacheBean {

    private String operationCode;//操作code
    private String datas;//数据库名字
    private String tokenKey;//认证

    public ConsumeTaskCacheBean() {
    }

    public ConsumeTaskCacheBean(String operationCode, String datas) {
        this.operationCode = operationCode;
        this.datas = datas;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    @Override
    public String toString() {
        return "ConsumeTaskCacheBean{" +
                "operationCode='" + operationCode + '\'' +
                ", datas='" + datas + '\'' +
                ", tokenKey='" + tokenKey + '\'' +
                '}';
    }
}
