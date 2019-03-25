package com.xl.basic.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.AtomicIntegerBean;
import com.xl.basic.bean.KafkaBean;
import com.xl.manage.common.CommonData;
import com.xl.tools.LogHelper;
import com.xl.tools.StringUtil;
import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;


public  class MessageScheme implements Scheme {

    public static final String STRING_SCHEME_KEY = "press";

    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;

    public static KafkaBean deserializeString(ByteBuffer string) {
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
    public static KafkaBean changeBean(String datas){
		KafkaBean oldBean = JSONObject.parseObject(datas,KafkaBean.class);
		LogHelper.info("数据总量："+AtomicIntegerBean.addAndGet()+"crawlTime:"+oldBean.getPdfArticle().getCrawlTime()+" media:"+oldBean.getPdfArticle().getMediaNameCn()+"\t URL："+oldBean.getPdfArticle().getUrl()+"\t title:"+oldBean.getPdfArticle().getTitle());
        return oldBean;
    }
}





