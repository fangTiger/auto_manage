package com.xl.bean.weibo.tf;

/**
 * twitter,facebook 微博用户对象
 * @Author:lww
 * @Date:9:20 2017/7/12
 */
public class TfUserBean {

	private String userId;
	private String userScreenName;
	private String profileImageUrl;
	private String userName;
	private String location;
	private String locationArea;
	private String gender;
	private Integer isVerified;
	private String weiboUrl;
	private Integer followersCount;
	private Integer friendsCount;
	private Integer statusesCount;
	private Integer favouritesCount;

	public TfUserBean() {
		this.userId = "";
		this.userScreenName = "";
		this.profileImageUrl = "";
		this.userName = "";
		this.location = "";
		this.locationArea = "";
		this.gender = "";
		this.isVerified = 0;
		this.weiboUrl = "";
		this.followersCount = 0;
		this.friendsCount = 0;
		this.statusesCount = 0;
		this.favouritesCount = 0;
	}

	public TfUserBean(String userId, String userScreenName, String profileImageUrl, String userName, String location, String locationArea, String gender, Integer isVerified, String weiboUrl, Integer followersCount, Integer friendsCount, Integer statusesCount, Integer favouritesCount) {
		this.userId = userId;
		this.userScreenName = userScreenName;
		this.profileImageUrl = profileImageUrl;
		this.userName = userName;
		this.location = location;
		this.locationArea = locationArea;
		this.gender = gender;
		this.isVerified = isVerified;
		this.weiboUrl = weiboUrl;
		this.followersCount = followersCount;
		this.friendsCount = friendsCount;
		this.statusesCount = statusesCount;
		this.favouritesCount = favouritesCount;
	}

	@Override
	public String toString() {
		return "TfUserBean{" +
				"userId='" + userId + '\'' +
				", userScreenName='" + userScreenName + '\'' +
				", profileImageUrl='" + profileImageUrl + '\'' +
				", userName='" + userName + '\'' +
				", location='" + location + '\'' +
				", locationArea='" + locationArea + '\'' +
				", gender='" + gender + '\'' +
				", isVerified=" + isVerified +
				", weiboUrl='" + weiboUrl + '\'' +
				", followersCount=" + followersCount +
				", friendsCount=" + friendsCount +
				", statusesCount=" + statusesCount +
				", favouritesCount=" + favouritesCount +
				'}';
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserScreenName() {
		return userScreenName;
	}

	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
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

	public String getLocationArea() {
		return locationArea;
	}

	public void setLocationArea(String locationArea) {
		this.locationArea = locationArea;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Integer isVerified) {
		this.isVerified = isVerified;
	}

	public String getWeiboUrl() {
		return weiboUrl;
	}

	public void setWeiboUrl(String weiboUrl) {
		this.weiboUrl = weiboUrl;
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
}
