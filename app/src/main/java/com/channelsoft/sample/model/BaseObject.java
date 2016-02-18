package com.channelsoft.sample.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.bmob.v3.BmobObject;


/**
 * Created by amgl on 2015/11/28.
 */
public class BaseObject{
    Gson gson = new GsonBuilder()
            .serializeNulls()
            .disableHtmlEscaping()
            .excludeFieldsWithoutExposeAnnotation()
            .create();

    public String toJsonString() {
        return gson.toJson(this);
    }
}
