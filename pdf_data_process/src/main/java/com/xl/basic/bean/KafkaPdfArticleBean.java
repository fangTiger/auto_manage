package com.xl.basic.bean;

/**
 * @author js
 * @date 2018/3/21 9:12
 */
public class KafkaPdfArticleBean {

    private String mediaId;//媒体ID
    private String mediaType;//媒体类型
    private String mediaNameCn;//媒体中文名称
    private String title;//标题
    private String contentText;//正文
    private String author;//作者
    private String dispTime;//发布时间
    private String crawlTime;//抓取时间
    private String pageSrc;//来源
    private String layout;//版面/频道
    private String page;//版位
    private String url;//文章链接
    private String pageImage;//缩略图
    private String filePath;//文件路径
    private Integer picturesCount;//图片数量
    private String articleLocation;//文章位置
    private String crawlType;//抓取类型
    private String articleThumbName;//整版图名称
    private String imageName;//pdf名称
    private String lableIds;//上传标签（多个用英文逗号拼接，如：机构1.标签1，机构2.标签2）
    private String monitorIds;//上传监测项（多个用英文逗号拼接，如：机构1.监测项1，机构2.监测项2）
    private String crawlSource;//预留采集源监控使用
	private String ewID;//信源任务ID

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaNameCn() {
        return mediaNameCn;
    }

    public void setMediaNameCn(String mediaNameCn) {
        this.mediaNameCn = mediaNameCn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getPageSrc() {
        return pageSrc;
    }

    public void setPageSrc(String pageSrc) {
        this.pageSrc = pageSrc;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPageImage() {
        return pageImage;
    }

    public void setPageImage(String pageImage) {
        this.pageImage = pageImage;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getPicturesCount() {
        return picturesCount;
    }

    public void setPicturesCount(Integer picturesCount) {
        this.picturesCount = picturesCount;
    }

    public String getArticleLocation() {
        return articleLocation;
    }

    public void setArticleLocation(String articleLocation) {
        this.articleLocation = articleLocation;
    }

    public String getCrawlType() {
        return crawlType;
    }

    public void setCrawlType(String crawlType) {
        this.crawlType = crawlType;
    }

    public String getArticleThumbName() {
        return articleThumbName;
    }

    public void setArticleThumbName(String articleThumbName) {
        this.articleThumbName = articleThumbName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getLableIds() {
        return lableIds;
    }

    public void setLableIds(String lableIds) {
        this.lableIds = lableIds;
    }

    public String getMonitorIds() {
        return monitorIds;
    }

    public void setMonitorIds(String monitorIds) {
        this.monitorIds = monitorIds;
    }

    public String getCrawlSource() {
        return crawlSource;
    }

    public void setCrawlSource(String crawlSource) {
        this.crawlSource = crawlSource;
    }

	public String getEwID() {
		return ewID;
	}

	public void setEwID(String ewID) {
		this.ewID = ewID;
	}
}