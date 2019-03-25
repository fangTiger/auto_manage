package com.xl.manage.bean;

/**
 * 返回的实体
 * @Author:bagen
 * @create 2017-07-20 15:48
 **/
public class ReturnBean {

    private String code;//返回类型
    private String msg;//返回信息
    private String datas;//返回数据

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }
}
