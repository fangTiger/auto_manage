package com.xl.manage.bean.esBean;

/**
 * Created by Administrator on 2017/7/10.
 */
public enum EsTypeConfBean {
    //有效数据
    ValidData,
    //全量数据
    AllData;

    /**
     * 获取数据类型
     * @Author: lww
     * @Description:
     * @Date: 18:55 2017/7/28
     * @params:
     */
    public static EsTypeConfBean getEsType(String esType)throws Exception{
        if("alldata".equals(esType.toLowerCase())){
            return EsTypeConfBean.AllData;
        }else{
            return EsTypeConfBean.ValidData;
        }
    }
}
