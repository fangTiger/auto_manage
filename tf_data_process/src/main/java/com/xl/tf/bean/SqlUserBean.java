package com.xl.tf.bean;

/**
 * @Author:lww
 * @Date:10:53 2017/10/12
 */
public class SqlUserBean {

	private String userID;//用户唯一ID
	private String groupId;//用户分组
	private String userScreenName;//用户主页名称
	private String userName;//用户名
	private String profileImageUrl;//头像地址 http
	private String location;//用户居住地
	private String description;//用户描述
	private String relativeUrl;//个人相关链接地址
	private String userUrl;//twitter主页链接地址
	private Integer followersCount;//关注者人数
	private Integer friendsCount;//正在关注数
	private String createdAt;//用户创建时间
	private String timeZone;//时区
	private String lang;//语种
	private Integer statusesCount;//推文数量
	private Integer isVerified;//0-未认证 1-认证
	private Integer favouritesCount;//喜欢数(类似收藏数)
	private Integer weiboFrom;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRelativeUrl() {
		return relativeUrl;
	}

	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}

	public String getUserUrl() {
		return userUrl;
	}

	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
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

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Integer getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(Integer statusesCount) {
		this.statusesCount = statusesCount;
	}

	public Integer getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Integer isVerified) {
		this.isVerified = isVerified;
	}

	public Integer getFavouritesCount() {
		return favouritesCount;
	}

	public void setFavouritesCount(Integer favouritesCount) {
		this.favouritesCount = favouritesCount;
	}

	public Integer getWeiboFrom() {
		return weiboFrom;
	}

	public void setWeiboFrom(Integer weiboFrom) {
		this.weiboFrom = weiboFrom;
	}
}
