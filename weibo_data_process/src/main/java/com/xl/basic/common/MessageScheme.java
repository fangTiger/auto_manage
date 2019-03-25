package com.xl.basic.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.WbOldBean;
import com.xl.basic.bean.WbStatusBean;
import com.xl.basic.bean.WbUserBean;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;
import jdk.nashorn.internal.objects.annotations.Where;
import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;


public  class MessageScheme implements Scheme {

    public static final String STRING_SCHEME_KEY = "weibo";

    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;


    public static WbOldBean deserializeString(ByteBuffer string) {
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
     * 解析bean
     * @Author: lww
     * @Description:
     * @Date: 14:55 2017/9/21
     * @param datas
     */
    public static WbOldBean changeBean(String datas){
        WbOldBean oldBean = JSONObject.parseObject(datas.replace("\"verified\"","\"veri\""),WbOldBean.class);
        if(oldBean!=null&&oldBean.getWbstatusbean()!=null){
            LogHelper.info("已获取数据量 URL:"+oldBean.getWbstatusbean().getTextUrl()+" crawlTime"+oldBean.getWbstatusbean().getCreateTime());
        }
        return oldBean;
    }
}





