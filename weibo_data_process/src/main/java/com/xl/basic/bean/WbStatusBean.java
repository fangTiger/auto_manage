package com.xl.basic.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author:lww
 * @Date:17:58 2017/10/19
 */
public class WbStatusBean {
	private String statusID;
	private String statusText;
	private String source;
	private String thumbnailPic;
	private String bmiddlePic;
	private String originalPic;
	@JSONField(name="isRetweeted")
	private boolean isRetweeted;
	private Integer commentCount;
	private Integer retweetedCount;
	private Integer attitudesCount;
	private String aquerTime;
	private String userID;
	private String userScreenName;
	private String userName;
	private String location;
	private String profileImageUrl;
	@JSONField(name="isVerified")
	private boolean isVerified;
	private String weiboUrl;
	private String pubTime;
	private String createTime;
	private Integer weiboFrom;
	private Integer followersCount;
	private Integer friendsCount;
	private Integer statusesCount;
	private Integer favouritesCount;
	private Integer emotionValue;
	private Integer emotion;
	private Integer isPost;
	private Integer crawlType;
	private String textUrl;
	private String conversationIDS;
	private Integer userClass;
	private String clientCode;
	private String spare;
	private String competCode;
	private String reStatusID;
	private String videoPicurl;
	private String videoPlayerurl;
	private String videoRealurl;
	private String musicUrl;
	private String rePubTime;
	private String reTextUrl;
	private String reUserName;
	private String reUserScreenName;
	private String reUserID;
	private String reSource;
	private String reStatusText;
	private Integer reRetweetedCount;
	private Integer reCommentCount;
	private Integer reAttitudesCount;
	private String reThumbnailPic;
	private String reBmiddlePic;
	private String reOriginalPic;
	private String reVideoPicurl;
	private String reVideoPlayerurl;
	private String reVideoRealurl;
	private String reMusicUrl;
	private String monitorIds;
	private String lableIds;

	public String getStatusID() {
		return statusID;
	}

	public void setStatusID(String statusID) {
		this.statusID = statusID;
	}

	public String getStatusText() {
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getThumbnailPic() {
		return thumbnailPic;
	}

	public void setThumbnailPic(String thumbnailPic) {
		this.thumbnailPic = thumbnailPic;
	}

	public String getBmiddlePic() {
		return bmiddlePic;
	}

	public void setBmiddlePic(String bmiddlePic) {
		this.bmiddlePic = bmiddlePic;
	}

	public String getOriginalPic() {
		return originalPic;
	}

	public void setOriginalPic(String originalPic) {
		this.originalPic = originalPic;
	}

	public boolean isRetweeted() {
		return isRetweeted;
	}

	public void setRetweeted(boolean retweeted) {
		isRetweeted = retweeted;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getRetweetedCount() {
		return retweetedCount;
	}

	public void setRetweetedCount(Integer retweetedCount) {
		this.retweetedCount = retweetedCount;
	}

	public Integer getAttitudesCount() {
		return attitudesCount;
	}

	public void setAttitudesCount(Integer attitudesCount) {
		this.attitudesCount = attitudesCount;
	}

	public String getAquerTime() {
		return aquerTime;
	}

	public void setAquerTime(String aquerTime) {
		this.aquerTime = aquerTime;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserScreenName() {
		return userScreenName;
	}

	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean verified) {
		isVerified = verified;
	}

	public String getWeiboUrl() {
		return weiboUrl;
	}

	public void setWeiboUrl(String weiboUrl) {
		this.weiboUrl = weiboUrl;
	}

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getWeiboFrom() {
		return weiboFrom;
	}

	public void setWeiboFrom(Integer weiboFrom) {
		this.weiboFrom = weiboFrom;
	}

	public Integer getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}

	public Integer getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}

	public Integer getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(Integer statusesCount) {
		this.statusesCount = statusesCount;
	}

	public Integer getFavouritesCount() {
		return favouritesCount;
	}

	public void setFavouritesCount(Integer favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	public Integer getEmotionValue() {
		return emotionValue;
	}

	public void setEmotionValue(Integer emotionValue) {
		this.emotionValue = emotionValue;
	}

	public Integer getEmotion() {
		return emotion;
	}

	public void setEmotion(Integer emotion) {
		this.emotion = emotion;
	}

	public Integer getIsPost() {
		return isPost;
	}

	public void setIsPost(Integer isPost) {
		this.isPost = isPost;
	}

	public Integer getCrawlType() {
		return crawlType;
	}

	public void setCrawlType(Integer crawlType) {
		this.crawlType = crawlType;
	}

	public String getTextUrl() {
		return textUrl;
	}

	public void setTextUrl(String textUrl) {
		this.textUrl = textUrl;
	}

	public String getConversationIDS() {
		return conversationIDS;
	}

	public void setConversationIDS(String conversationIDS) {
		this.conversationIDS = conversationIDS;
	}

	public Integer getUserClass() {
		return userClass;
	}

	public void setUserClass(Integer userClass) {
		this.userClass = userClass;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getSpare() {
		return spare;
	}

	public void setSpare(String spare) {
		this.spare = spare;
	}

	public String getCompetCode() {
		return competCode;
	}

	public void setCompetCode(String competCode) {
		this.competCode = competCode;
	}

	public String getReStatusID() {
		return reStatusID;
	}

	public void setReStatusID(String reStatusID) {
		this.reStatusID = reStatusID;
	}

	public String getVideoPicurl() {
		return videoPicurl;
	}

	public void setVideoPicurl(String videoPicurl) {
		this.videoPicurl = videoPicurl;
	}

	public String getVideoPlayerurl() {
		return videoPlayerurl;
	}

	public void setVideoPlayerurl(String videoPlayerurl) {
		this.videoPlayerurl = videoPlayerurl;
	}

	public String getVideoRealurl() {
		return videoRealurl;
	}

	public void setVideoRealurl(String videoRealurl) {
		this.videoRealurl = videoRealurl;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getRePubTime() {
		return rePubTime;
	}

	public void setRePubTime(String rePubTime) {
		this.rePubTime = rePubTime;
	}

	public String getReTextUrl() {
		return reTextUrl;
	}

	public void setReTextUrl(String reTextUrl) {
		this.reTextUrl = reTextUrl;
	}

	public String getReUserName() {
		return reUserName;
	}

	public void setReUserName(String reUserName) {
		this.reUserName = reUserName;
	}

	public String getReUserScreenName() {
		return reUserScreenName;
	}

	public void setReUserScreenName(String reUserScreenName) {
		this.reUserScreenName = reUserScreenName;
	}

	public String getReUserID() {
		return reUserID;
	}

	public void setReUserID(String reUserID) {
		this.reUserID = reUserID;
	}

	public String getReSource() {
		return reSource;
	}

	public void setReSource(String reSource) {
		this.reSource = reSource;
	}

	public String getReStatusText() {
		return reStatusText;
	}

	public void setReStatusText(String reStatusText) {
		this.reStatusText = reStatusText;
	}

	public Integer getReRetweetedCount() {
		return reRetweetedCount;
	}

	public void setReRetweetedCount(Integer reRetweetedCount) {
		this.reRetweetedCount = reRetweetedCount;
	}

	public Integer getReCommentCount() {
		return reCommentCount;
	}

	public void setReCommentCount(Integer reCommentCount) {
		this.reCommentCount = reCommentCount;
	}

	public Integer getReAttitudesCount() {
		return reAttitudesCount;
	}

	public void setReAttitudesCount(Integer reAttitudesCount) {
		this.reAttitudesCount = reAttitudesCount;
	}

	public String getReThumbnailPic() {
		return reThumbnailPic;
	}

	public void setReThumbnailPic(String reThumbnailPic) {
		this.reThumbnailPic = reThumbnailPic;
	}

	public String getReBmiddlePic() {
		return reBmiddlePic;
	}

	public void setReBmiddlePic(String reBmiddlePic) {
		this.reBmiddlePic = reBmiddlePic;
	}

	public String getReOriginalPic() {
		return reOriginalPic;
	}

	public void setReOriginalPic(String reOriginalPic) {
		this.reOriginalPic = reOriginalPic;
	}

	public String getReVideoPicurl() {
		return reVideoPicurl;
	}

	public void setReVideoPicurl(String reVideoPicurl) {
		this.reVideoPicurl = reVideoPicurl;
	}

	public String getReVideoPlayerurl() {
		return reVideoPlayerurl;
	}

	public void setReVideoPlayerurl(String reVideoPlayerurl) {
		this.reVideoPlayerurl = reVideoPlayerurl;
	}

	public String getReVideoRealurl() {
		return reVideoRealurl;
	}

	public void setReVideoRealurl(String reVideoRealurl) {
		this.reVideoRealurl = reVideoRealurl;
	}

	public String getReMusicUrl() {
		return reMusicUrl;
	}

	public void setReMusicUrl(String reMusicUrl) {
		this.reMusicUrl = reMusicUrl;
	}

	public String getMonitorIds() {
		return monitorIds;
	}

	public void setMonitorIds(String monitorIds) {
		this.monitorIds = monitorIds;
	}

	public String getLableIds() {
		return lableIds;
	}

	public void setLableIds(String lableIds) {
		this.lableIds = lableIds;
	}
}
