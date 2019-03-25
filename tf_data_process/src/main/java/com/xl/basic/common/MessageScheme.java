package com.xl.basic.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.OverseaEsBean;
import com.xl.tf.bean.DBean;
import com.xl.tf.bean.TfBean;
import com.xl.tf.bean.TfUserBean;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;
import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;


public  class MessageScheme implements Scheme {


    public static final String STRING_SCHEME_KEY = "tf";

    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;


    public static OverseaEsBean deserializeString(ByteBuffer string) {
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
        return new Values(deserializeString(byteBuffer));
    }

    public Fields getOutputFields() {
        return new Fields(STRING_SCHEME_KEY);
    }

    /**
     * 转换为新的esBean
     * @return com.xl.tf.bean.TfBean
     * @Author: lww
     * @Description:
     * @Date: 14:55 2017/9/21
     * @param datas
     */
    public static OverseaEsBean changeBean(String datas){
        OverseaEsBean oldBean = JSONObject.parseObject(datas,OverseaEsBean.class);
        return oldBean;
    }

}





