package com.channelsoft.sample.activity.discovery;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.BaseActivity;
import com.channelsoft.sample.util.Mpopupwindow;
import com.channelsoft.sample.util.StatusBarColor;

public class Plan extends BaseActivity implements View.OnClickListener {
    private ImageView mImgShare;
    private ImageView mImgBack;
    private TextView mTextPlan;
    private LinearLayout mLinearHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery_plan);
        StatusBarColor.transStatusBarAlpha(getWindow());//将statusbar变为黑色
        initView();

        mImgShare.setOnClickListener(this);
        mImgBack.setOnClickListener(this);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mImgShare = (ImageView) findViewById(R.id.img_discovery_plan_share);
        mImgBack = (ImageView) findViewById(R.id.img_discovery_plan_back);
        mTextPlan = (TextView) findViewById(R.id.text_discovery_plan);
        mLinearHint= (LinearLayout) findViewById(R.id.linear_discovery_hint);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_discovery_plan_share:
                View viewPopItem = getLayoutInflater().inflate(R.layout.popupwindow_item_share, null);
                Mpopupwindow.startPopupWindow(this,4,viewPopItem, mTextPlan, mLinearHint);
                break;
            case R.id.img_discovery_plan_back:
               finish();
                break;
        }
    }

    /**
     * 点击手机上后退按键先关闭popupwindow
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            必须用短路与，如果mPopupWindow是null直接跳出，不再执行后面的判断条件
            if (Mpopupwindow.mPopupWindow != null && Mpopupwindow.mPopupWindow.isShowing()) {
                Mpopupwindow.mPopupWindow.dismiss();
//              return true后这个方法内下面的语句就不执行了。
//                不返回true的话按back键会继续执行下面的语句，退出活动
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("date","此时活动的状态为： onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("date", "此时活动的状态为： onPause");
    }
}
