package com.xl.baisc.bean;

import java.util.List;

/**
 * 旧广播实体
 * @Author:bagen
 * @create 2017-09-15 14:18
 **/
public class BroadcastOldBean {

    //切片视频ID
    public int monitorId;
    //文件类型（分为广播:radio和电视:tv）
    public String fileType;
    //中文标题
    public String title;
    //中文摘要
    public String summary;
    //中文全文
    public String context;
    //关键字
    public String keywords;
    //记者
    public String author;
    //文件时长
    public int fileDuration;
    //播出时间
    public String dispTime;
    //制作时间
    public String crawlTime;
    //文件地址
    public String filePath;
    //栏目
    public String layout;
    //频道
    public String mediaNameCn;
    //栏目播出时间
    public String broadcastTime;
    //栏目时长
    public int programDuration;
    //广告价值
    public Float advPrice;
    public String city;

    //栏目类型
    public String programType;
    //栏目播出周期
    public List<String> cycle;
    //调性
    public int emotionValue;
    //行业分类
    public List<String> brandCategory;

    public int getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(int monitorId) {
        this.monitorId = monitorId;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getFileDuration() {
        return fileDuration;
    }

    public void setFileDuration(int fileDuration) {
        this.fileDuration = fileDuration;
    }

    public String getDispTime() {
        return dispTime;
    }

    public void setDispTime(String dispTime) {
        this.dispTime = dispTime;
    }

    public String getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(String crawlTime) {
        this.crawlTime = crawlTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getMediaNameCn() {
        return mediaNameCn;
    }

    public void setMediaNameCn(String mediaNameCn) {
        this.mediaNameCn = mediaNameCn;
    }

    public String getBroadcastTime() {
        return broadcastTime;
    }

    public void setBroadcastTime(String broadcastTime) {
        this.broadcastTime = broadcastTime;
    }

    public int getProgramDuration() {
        return programDuration;
    }

    public void setProgramDuration(int programDuration) {
        this.programDuration = programDuration;
    }

    public Float getAdvPrice() {
        return advPrice;
    }

    public void setAdvPrice(Float advPrice) {
        this.advPrice = advPrice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public List<String> getCycle() {
        return cycle;
    }

    public void setCycle(List<String> cycle) {
        this.cycle = cycle;
    }

    public int getEmotionValue() {
        return emotionValue;
    }

    public void setEmotionValue(int emotionValue) {
        this.emotionValue = emotionValue;
    }

    public List<String> getBrandCategory() {
        return brandCategory;
    }

    public void setBrandCategory(List<String> brandCategory) {
        this.brandCategory = brandCategory;
    }
}
