package com.blockchainenergy.common;

public class CurrentUser {

    public static final String SESSION_ATTR_NAME="CurrentUser";//session属性名字

    /**
     * 头像存储位置
     */
    public static final String AVATAR_DIR = "E:/touxiang";

    private String userId;
    private String userName;


    public CurrentUser() {
    }

    public CurrentUser(String userId) {
        this.userId = userId;
    }

    public CurrentUser(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
