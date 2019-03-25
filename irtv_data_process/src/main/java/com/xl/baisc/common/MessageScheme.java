package com.xl.baisc.common;

import com.alibaba.fastjson.JSON;
import com.xl.baisc.bean.AtomicIntegerBean;
import com.xl.baisc.bean.BroadcastOldBean;
import com.xl.bean.irtv.*;
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

    public static final String STRING_SCHEME_KEY = "broacast";

    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;


    public static BroadcastBean deserializeString(ByteBuffer string) {
        if (string.hasArray()) {
            int base = string.arrayOffset();
            return changeBroadCastBean(new String(string.array(), base + string.position(), string.remaining()));
        } else {
            return changeBroadCastBean(new String(Utils.toByteArray(string), UTF8_CHARSET));
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
     * 将卡夫卡获取的数据转换为broadcast
     * @return com.xl.irtv.bean.BroadcastBean
     * @Author: lww
     * @Description:
     * @Date: 16:40 2017/9/15
     * @param irtvStr
     */
    private static  BroadcastBean  changeBroadCastBean(String irtvStr){

        //转换成老广播实体
        BroadcastOldBean oldBean  =  JSON.parseObject(irtvStr,BroadcastOldBean.class);
        //解析成新广播实体
        BroadcastBean  broadcastBean  =  new  BroadcastBean();
        DBean dBean  =  new  DBean();
        BroadcastAreaBean mediaArea  =  new  BroadcastAreaBean();
        BroadcastAdvBean advBean  =  new  BroadcastAdvBean();
        BroadcastMediaBean mediaBean  =  new  BroadcastMediaBean();

        broadcastBean.setFileId(oldBean.getMonitorId()+"");//切片视频ID
        broadcastBean.setType("broadcast");
        broadcastBean.setTitle(StringUtil.toTrim(oldBean.getTitle()));//中文标题
        broadcastBean.setSummary(StringUtil.toTrim(oldBean.getSummary()));//中文摘要
        broadcastBean.setContentText(StringUtil.toTrim(oldBean.getContext()));//中文全文
        broadcastBean.setKeyword(Arrays.asList(getStringArrByName(oldBean.getKeywords())));//关键字
        broadcastBean.setAuthor(StringUtil.toTrim(oldBean.getAuthor()));//记者中文
        broadcastBean.setFileDuration(oldBean.getFileDuration()+"");//文件时长
        dBean.setDispTime(oldBean.getDispTime());//播出时间
        dBean.setCrawlTime(oldBean.getCrawlTime());//制作时间
        broadcastBean.setD(dBean);
        broadcastBean.setUrl(StringUtil.toTrim(oldBean.getFilePath()));//文件地址
        broadcastBean.setLayout(StringUtil.toTrim(oldBean.getLayout()));//栏目
        mediaArea.setProvince(StringUtil.toTrim(oldBean.getCity()));
        mediaBean.setMediaArea(mediaArea);
        mediaBean.setMediaNameCn(StringUtil.toTrim(oldBean.getMediaNameCn()));//频道
        mediaBean.setMediaType(StringUtil.toTrim(oldBean.getFileType()));
        broadcastBean.setMedia(mediaBean);
        broadcastBean.setBroadcastTime(oldBean.getBroadcastTime());//栏目播出时间
        broadcastBean.setProgramDuration(oldBean.getProgramDuration());//栏目时长
        advBean.setAdvPrice(oldBean.getAdvPrice());//广告价值
        broadcastBean.setAdv(advBean);
        broadcastBean.setProgramType(StringUtil.toTrim(oldBean.getProgramType()));//栏目类型
        broadcastBean.setCycle(oldBean.getCycle());//栏目播出周期
        broadcastBean.setEmotionValue(oldBean.getEmotionValue());//调性
        broadcastBean.setBrandCategory(oldBean.getBrandCategory());//行业分类

		LogHelper.info("待处理数据量："+ AtomicIntegerBean.addAndGet()+"\t crawlTime:"+broadcastBean.getD().getCrawlTime()+"\t 标题："+broadcastBean.getTitle());

        return broadcastBean;
    }

    /**
     * 将值为数组格式的值转为数组
     * @param arrays
     * @return
     * @throws Exception
     */
    private static String[] getStringArrByName(String arrays){
        String result = "";
        if(arrays!=null&&!"".equals(arrays)){
            try {
                result = arrays.replace("[","").replace("]","").replace("\"","");
                return "".equals(result)?new String[]{}:result.split(",");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new String[]{};
    }
}





