package com.channelsoft.sample.model.listener;

/**
 * Created by amgl on 2015/11/28.
 */
public interface IStringListener {
    /**
     * 成功时回调
     *
     * @param result
     */
    void onSuccess(String result);

    /**
     * 失败时回调，简单处理，没做什么
     */
    void onError();
}
