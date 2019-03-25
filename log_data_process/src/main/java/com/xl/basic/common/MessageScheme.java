package com.xl.basic.common;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.LogOldBean;
import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;


public  class MessageScheme implements Scheme {

    public static int Number = 0;

    public static final String STRING_SCHEME_KEY = "manage";

    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;


    public static LogOldBean deserializeString(ByteBuffer string) {
        if (string.hasArray()) {
            int base = string.arrayOffset();
            return changeBean(new String(string.array(), base + string.position(), string.remaining()));
        } else {
            return changeBean(new String(Utils.toByteArray(string), UTF8_CHARSET));
        }
    }


    //接受kafka数据
    @Override
    public List<Object> deserialize(ByteBuffer byteBuffer) {
        Number++;
        System.out.println("+++++++++++++++++++++++++++++++++++++++++"+Number+"++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return new Values(deserializeString(byteBuffer));
    }

    public Fields getOutputFields() {
        return new Fields(STRING_SCHEME_KEY);
    }

    /**
     * 获取日志数据
     * @return com.xl.basic.bean.LogOldBean
     * @Author: lww
     * @Description:
     * @Date: 14:55 2017/9/21
     * @param datas
     */
    public static LogOldBean changeBean(String datas){
        LogOldBean logOldBean = JSONObject.parseObject(datas,LogOldBean.class);
        return logOldBean;
    }

}





