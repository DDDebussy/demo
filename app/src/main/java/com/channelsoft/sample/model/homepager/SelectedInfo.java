package com.channelsoft.sample.model.homepager;

import com.channelsoft.sample.model.BaseObject;

/**
 * Created by chenlijin on 2015/12/25.
 */
public class SelectedInfo extends BaseObject {
    private String caiMing;
    private int price;
    private int num;
    private int sum;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getCaiMing() {
        return caiMing;
    }

    public void setCaiMing(String caiMing) {
        this.caiMing = caiMing;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
