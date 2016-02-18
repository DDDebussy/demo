package com.channelsoft.sample.model.user;

import cn.bmob.v3.BmobObject;

/**
 * Created by 王宗贤 on 2015/12/31.
 */
public class SignStatus extends BmobObject {
    private boolean isSignIn;
    private String userID;
    private String userPassword;

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setIsSignIn(boolean isSignIn) {
        this.isSignIn = isSignIn;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public boolean isSignIn() {
        return isSignIn;
    }

    public String getUserID() {
        return userID;
    }
}
