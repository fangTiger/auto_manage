package com.xl.basic.common;

import com.alibaba.fastjson.JSONObject;
import com.xl.manage.bean.esBean.ArticleBean;
import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;


public  class MessageScheme implements Scheme {

    public static final String STRING_SCHEME_KEY = "auto";

    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    private int data_num = 0;

    public static ArticleBean deserializeString(ByteBuffer string) {
        if (string.hasArray()) {
            int base = string.arrayOffset();
            return JSONObject.parseObject(new String(string.array(), base + string.position(), string.remaining()),ArticleBean.class);
        } else {
            return JSONObject.parseObject(new String(Utils.toByteArray(string), UTF8_CHARSET),ArticleBean.class);
        }
    }


    //接受kafka数据
    @Override
    public List<Object> deserialize(ByteBuffer byteBuffer) {
        return new Values(deserializeString(byteBuffer));
       // return null;
    }

    public Fields getOutputFields() {
        return new Fields(STRING_SCHEME_KEY);
    }

}





