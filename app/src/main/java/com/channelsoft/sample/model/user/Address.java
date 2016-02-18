package com.channelsoft.sample.model.user;

import cn.bmob.v3.BmobObject;

/**
 * Created by 王宗贤 on 2015/12/28.
 */
public class Address extends BmobObject{
    private String userName;
    private String userTel;
    private String userAddress;


    public String getUserName() {
        return userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }
}
