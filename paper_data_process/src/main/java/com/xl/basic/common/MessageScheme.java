package com.xl.basic.common;

import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.AtomicIntegerBean;
import com.xl.basic.bean.WebOldBean;
import com.xl.tool.LogHelper;
import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;


public  class MessageScheme implements Scheme {

    public static final String STRING_SCHEME_KEY = "web";

    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;


    public static WebOldBean deserializeString(ByteBuffer string) {
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
    public static WebOldBean changeBean(String datas){
        WebOldBean oldBean = JSONObject.parseObject(datas,WebOldBean.class);
		if(oldBean!=null&&oldBean.getK_article()!=null){
			LogHelper.info("待处理数据量："+ AtomicIntegerBean.addAndGet()+" URL:"+oldBean.getK_article().getUrl()+" crawlTime"+oldBean.getK_article().getCrawlTime());
		}
        return oldBean;
    }

    public static void main(String[] args) {
        WebOldBean bean = new WebOldBean();
    }
}





