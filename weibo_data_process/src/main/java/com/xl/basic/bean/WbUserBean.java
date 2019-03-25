package com.xl.basic.bean;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @Author:lww
 * @Date:17:54 2017/10/19
 */
public class WbUserBean {

	private Integer weiboFrom;
	private String userID;
	private String userName;
	private String personalityName;
	private String gender;
	private String location;
	private String locationArea;
	private String profileImageUrl;
	@JSONField(name="isVerified")
	private boolean isVerified;
	@JSONField(name="verified")
	private String verified;
	private String verified_reason;
	private String description;
	private String regisDate;
	private Integer followersCount;
	private Integer favouritesCount;
	private Integer friendsCount;
	private Integer statusesCount;
	private String personalUrl;
	private String url;
	private Integer  isFollow;
	private String statusID;
	private Integer rt;

	public Integer getWeiboFrom() {
		return weiboFrom;
	}

	public void setWeiboFrom(Integer weiboFrom) {
		this.weiboFrom = weiboFrom;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPersonalityName() {
		return personalityName;
	}

	public void setPersonalityName(String personalityName) {
		this.personalityName = personalityName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationArea() {
		return locationArea;
	}

	public void setLocationArea(String locationArea) {
		this.locationArea = locationArea;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}
	@JSONField(name="isVerified")
	public boolean isVerified() {
		return isVerified;
	}

	@JSONField(name="verified")
	public String getVerified() {
		return verified;
	}

	@JSONField(name="veri")
	public void setVerified(String verified) {
		this.verified = verified;
	}

	@JSONField(name="isVerified")
	public void setIsVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	public String getVerified_reason() {
		return verified_reason;
	}

	public void setVerified_reason(String verified_reason) {
		this.verified_reason = verified_reason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegisDate() {
		return regisDate;
	}

	public void setRegisDate(String regisDate) {
		this.regisDate = regisDate;
	}

	public Integer getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}

	public Integer getFavouritesCount() {
		return favouritesCount;
	}

	public void setFavouritesCount(Integer favouritesCount) {
		this.favouritesCount = favouritesCount;
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

	public String getPersonalUrl() {
		return personalUrl;
	}

	public void setPersonalUrl(String personalUrl) {
		this.personalUrl = personalUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(Integer isFollow) {
		this.isFollow = isFollow;
	}

	public String getStatusID() {
		return statusID;
	}

	public void setStatusID(String statusID) {
		this.statusID = statusID;
	}

	public void setVerified(boolean verified) {
		isVerified = verified;
	}

	public Integer getRt() {
		return rt;
	}

	public void setRt(Integer rt) {
		this.rt = rt;
	}
}
