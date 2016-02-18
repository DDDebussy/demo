package com.channelsoft.sample.model.homepager;

import cn.bmob.v3.BmobObject;

/**
 * Created by chenlijin on 2015/12/17.
 */
//有关数据库的类继承BmobObject
public class CuisineInfo extends BmobObject{
    private String canguanName;
    private String address;
    private String distance;
    private String approve;
    private String imageUrl;
    private String iconImageUrl;
    private Boolean focus;

    public Boolean getFocus() {
        return focus;
    }

    public void setFocus(Boolean focus) {
        this.focus = focus;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getIconImageUrl() {
        return iconImageUrl;
    }

    public void setIconImageUrl(String iconImageUrl) {
        this.iconImageUrl = iconImageUrl;
    }

    public String getCanguanName() {
        return canguanName;
    }

    public void setCanguanName(String canguanName) {
        this.canguanName = canguanName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }
}
