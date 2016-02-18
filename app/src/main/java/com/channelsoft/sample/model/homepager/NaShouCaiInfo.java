package com.channelsoft.sample.model.homepager;

import cn.bmob.v3.BmobObject;

/**
 * Created by chenlijin on 2015/12/21.
 */
public class NaShouCaiInfo extends BmobObject {
    private String CaiMing;
    private String MiaoShu;
    private Integer price;
    private Integer sum;
    private Integer leftNum;
    private String imageUrl;
    private String canguanName;
    private Integer curNum=0;

    public Integer getCurNum() {
        return curNum;
    }

    public void setCurNum(Integer curNum) {
        this.curNum = curNum;
    }

    public String getCanguanName() {
        return canguanName;
    }

    public void setCanguanName(String canguanName) {
        this.canguanName = canguanName;
    }

    public String getCaiMing() {
        return CaiMing;
    }

    public void setCaiMing(String caiMing) {
        CaiMing = caiMing;
    }

    public String getMiaoShu() {
        return MiaoShu;
    }

    public void setMiaoShu(String miaoShu) {
        MiaoShu = miaoShu;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getLeftNum() {
        return leftNum;
    }

    public void setLeftNum(Integer leftNum) {
        this.leftNum = leftNum;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
