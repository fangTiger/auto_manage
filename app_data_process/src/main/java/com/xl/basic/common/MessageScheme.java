package com.xl.basic.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xl.basic.bean.*;
import com.xl.tool.DateHelper;
import com.xl.tool.LogHelper;
import com.xl.tool.StringUtil;
import org.apache.storm.spout.Scheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;


public  class MessageScheme implements Scheme {

    public static final String STRING_SCHEME_KEY = "app";

    public static final Charset UTF8_CHARSET = StandardCharsets.UTF_8;


    public WebOldBean deserializeString(ByteBuffer string) {
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
    public WebOldBean changeBean(String datas){

        WebOldBean oldBean;

        JSONObject jsonObject = JSON.parseObject(datas);

        if(jsonObject.containsKey("beanVersion")){//版本判断
            if("v1".equals(jsonObject.getString("beanVersion"))){
                oldBean = new WebOldBean();
                if(jsonObject.get("docpic")!=null){
                    oldBean.setK_docpic(JSON.parseArray(jsonObject.getJSONArray("docpic").toJSONString(), Docpicbean.class));
                }
                if(jsonObject.get("docpsimage")!=null){
                    oldBean.setK_docpsimage(JSON.parseArray(jsonObject.getJSONArray("docpsimage").toJSONString(), Docpsimagebean.class));
                }
                if(jsonObject.get("doctable")!=null){
                    oldBean.setK_doctable(JSON.parseArray(jsonObject.getJSONArray("doctable").toJSONString(), Doctablebean.class));
                }
                oldBean.setK_article(changeKafkaBean(jsonObject.getJSONObject("article")));
				LogHelper.info("已获取数据量 "+AtomicIntegerBean.addAndGet()+" URL:"+oldBean.getK_article().getUrl()+" crawlTime"+oldBean.getK_article().getCrawlTime());
            }else{
                oldBean = JSONObject.parseObject(datas,WebOldBean.class);
                if(oldBean!=null&&oldBean.getK_article()!=null){
                    LogHelper.info("已获取数据量 "+AtomicIntegerBean.addAndGet()+" URL:"+oldBean.getK_article().getUrl()+" crawlTime"+oldBean.getK_article().getCrawlTime());
                }
            }
        }else{
            oldBean = JSONObject.parseObject(datas,WebOldBean.class);
            if(oldBean!=null&&oldBean.getK_article()!=null){
                LogHelper.info("已获取数据量 "+AtomicIntegerBean.addAndGet()+" URL:"+oldBean.getK_article().getUrl()+" crawlTime"+oldBean.getK_article().getCrawlTime());
            }
        }
       /* oldBean = JSONObject.parseObject(datas,WebOldBean.class);
        if(oldBean!=null&&oldBean.getK_article()!=null){
            LogHelper.info("已获取数据量 URL:"+oldBean.getK_article().getUrl()+" crawlTime"+oldBean.getK_article().getCrawlTime());
        }*/
        return oldBean;
    }

    /**
     * 转换V1版本对象
     * @return com.xl.basic.bean.ArticleOldbean
     * @Author: lww
     * @Description:
     * @Date: 16:51 2018/3/20
     * @param jsonObject
     */
    public ArticleOldbean changeKafkaBean(JSONObject jsonObject){

        ArticleOldbean articleOldbean = new ArticleOldbean();
        articleOldbean.setAid("".equals(StringUtil.toTrim(jsonObject.getString("aid")))?0l:Long.parseLong(jsonObject.getString("aid")));
        articleOldbean.setMediatypeint(Integer.parseInt(jsonObject.getString("mediaType")));
        articleOldbean.setSourceName(jsonObject.getString("mediaNameCn"));
        articleOldbean.setMediaSubType("sapp");
        articleOldbean.setCrawlTime(DateHelper.formatDateString(DateHelper.formatDataSpicel(jsonObject.getString("crawlTime")),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_DATETIME5));
        articleOldbean.setDispTime(DateHelper.formatDateString(DateHelper.formatDataSpicel(jsonObject.getString("dispTime")),DateHelper.FMT_DATE_DATETIME,DateHelper.FMT_DATE_DATETIME5));
        articleOldbean.setSig(jsonObject.getString("sig"));
        articleOldbean.setSigAll(jsonObject.getString("sigAll"));
        articleOldbean.setPage(jsonObject.getString("page"));
        articleOldbean.setPageSrc(jsonObject.getString("pageSrc"));
        articleOldbean.setCrawlType(Integer.parseInt(jsonObject.getString("crawlType")));
        articleOldbean.setUrl(jsonObject.getString("url"));
        articleOldbean.setS3(jsonObject.getString("weMName"));
        articleOldbean.setArticleLocation(jsonObject.getString("articleLocation"));
        articleOldbean.setReferer(jsonObject.getString("fatherUrl"));
        articleOldbean.setThumbnailPic(jsonObject.getString("pageImage"));
        articleOldbean.setPdf(jsonObject.getString("filePath"));
        articleOldbean.setPics(jsonObject.getInteger("picturesCount")+"");
        articleOldbean.setPn(jsonObject.getInteger("pn"));
        articleOldbean.setPc(jsonObject.getInteger("pc"));
        articleOldbean.setNavigator(jsonObject.getString("navigator"));
        articleOldbean.setM1(jsonObject.getInteger("readnum"));
        articleOldbean.setM2(jsonObject.getInteger("commentnum"));
        articleOldbean.setM3(jsonObject.getInteger("likenum"));
        articleOldbean.setHtmlPage(jsonObject.getString("contentText"));
        articleOldbean.setDomain(jsonObject.getString("domain"));
        articleOldbean.setSite(jsonObject.getString("site"));
        articleOldbean.setAuthor(jsonObject.getString("author"));
        articleOldbean.setTitle(jsonObject.getString("title"));
        articleOldbean.setLableIds(jsonObject.getString("lableIds"));
        articleOldbean.setMonitorIds(jsonObject.getString("monitorIds"));
        articleOldbean.setLayout(jsonObject.getString("layout"));
        if(jsonObject.containsKey("ewID")){
			articleOldbean.setEwID(jsonObject.getString("ewID"));
		}
        return articleOldbean;
    }

    public static void main(String[] args) {
        WebOldBean bean = new WebOldBean();
    }
}





