package com.channelsoft.sample.model;

import com.channelsoft.sample.model.listener.IStringListener;

/**
 * Created by amglh on 2015/12/15.
 */
public interface IStringModel {
    void load(String url, IStringListener listener);

    void load(String url);
}
