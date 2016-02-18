package com.channelsoft.sample.network;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.channelsoft.sample.app.GlobalContext;

import java.util.Map;

import timber.log.Timber;

/**
 * Created by amglh on 2015/12/15.
 */
public class VolleyRequest {
    private static VolleyRequest mVolleyRequest;
    private static RequestQueue mRequestQueue;

    private VolleyRequest() {
        mRequestQueue = Volley.newRequestQueue(GlobalContext.getInstance());
    }

    public static VolleyRequest getInstance() {
        if (mVolleyRequest == null) {
            synchronized (VolleyRequest.class) {
                if (mVolleyRequest == null) {
                    mVolleyRequest = new VolleyRequest();
                }
            }
        }
        return mVolleyRequest;
    }

    /**
     * 发起get请求
     *
     * @param url
     * @param tag
     * @param listener
     */
    public void requestGet(String url, Object tag, @NonNull VolleyListener listener) {
        Timber.d("request get %s", url);
        start(getRequest(url, tag, listener));
    }

    /**
     * 发起post请求
     *
     * @param url
     * @param tag
     * @param params
     * @param listener
     */
    public void requestPost(String url, Object tag, final Map params, @NonNull VolleyListener listener) {
        Timber.d("request post %s", url);
        start(postRequest(url, tag, params, listener));
    }

    private StringRequest getRequest(String url, Object tag, @NonNull VolleyListener listener) {
        mRequestQueue.cancelAll(tag);
        StringRequest request = new StringRequest(url, listener.sucessListener(), listener.errorListener());
        request.setTag(tag);
        return request;
    }

    private StringRequest postRequest(String url, Object tag, final Map params, @NonNull VolleyListener listener) {
        mRequestQueue.cancelAll(tag);
        StringRequest request = new StringRequest(Request.Method.POST, url, listener.sucessListener(), listener.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        request.setTag(tag);
        return request;
    }

    private void start(Request request) {
        mRequestQueue.add(request);
        mRequestQueue.start();
    }

    /**
     * 获取请求队列
     *
     * @return
     */
    public RequestQueue getRequestQueues() {
        return mRequestQueue;
    }
}
