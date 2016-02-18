package com.channelsoft.sample.activity.user.signin;

import android.app.Activity;
import android.os.Bundle;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.BaseActivity;
import com.channelsoft.sample.util.StatusBarColor;

public class PasswordSignIn extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_sign_in);
        StatusBarColor.transStatusBarAlpha(getWindow());
    }
}
