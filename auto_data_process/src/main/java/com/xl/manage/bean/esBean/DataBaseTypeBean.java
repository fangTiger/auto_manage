package com.xl.manage.bean.esBean;

/**
 * Created by Administrator on 2017/7/8.
 */
public enum DataBaseTypeBean {
    SqlServer,
    ElasticSearch;

    /**
     * 获取数据库类型
     * @Author: lww
     * @Description:
     * @Date: 11:23 2017/7/31
     * @params:
     */
    public static DataBaseTypeBean getDataBaseType(String dataBaseType)throws Exception{
        if("sqlserver".equals(dataBaseType.toLowerCase())){
            return DataBaseTypeBean.SqlServer;
        }else{
            return DataBaseTypeBean.ElasticSearch;
        }
    }
}
