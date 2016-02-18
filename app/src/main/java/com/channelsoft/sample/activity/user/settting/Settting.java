package com.channelsoft.sample.activity.user.settting;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.BaseActivity;
import com.channelsoft.sample.util.StatusBarColor;

public class Settting extends BaseActivity implements OnClickListener{
    private ImageView mImgSettingBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settting);
        StatusBarColor.transStatusBarAlpha(getWindow());
        initView();
        onClickListener();
    }

    /**
     * 点击触发
     */
    private void onClickListener() {
        mImgSettingBack.setOnClickListener(this);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mImgSettingBack= (ImageView) findViewById(R.id.img_user_setting_back);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_user_setting_back:
                finish();
                break;
        }
    }
}
