package com.channelsoft.sample.activity.user;

import android.app.Activity;
import android.os.Bundle;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.BaseActivity;
import com.channelsoft.sample.util.StatusBarColor;

public class MyOrderAcitvity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_order_acitvity);
        StatusBarColor.transStatusBarAlpha(getWindow());//将statusbar变为黑色
    }
}
