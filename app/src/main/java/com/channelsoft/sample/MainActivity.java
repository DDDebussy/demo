package com.channelsoft.sample;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.channelsoft.sample.activity.BaseActivity;
import com.channelsoft.sample.fragment.Discovery;
import com.channelsoft.sample.fragment.HomePage;
import com.channelsoft.sample.fragment.User;
import com.channelsoft.sample.util.StatusBarColor;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private Discovery mDiscovery;
    private User mUser;
    private HomePage mHomepage;

    private LinearLayout mLinearHomePage;
    private LinearLayout mLinearUser;
    private LinearLayout mLinearDiscovery;
    private CheckBox mCheckBoxHomePage;
    private CheckBox mCheckBoxUser;
    private CheckBox mCheckBoxDiscovery;
    private TextView mTextHomePage;
    private TextView mTextUser;
    private TextView mTextDiscovery;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarColor.transStatusBarAlpha(getWindow());//将statusbar变为黑色


        initView();
        initFragment();

        mTextHomePage.setTextColor(getResources().getColor(R.color.homeBottomOrange));

        clickItem();

    }

    /**
     * 点击事件触发
     */
    private void clickItem() {
        mLinearDiscovery.setOnClickListener(this);
        mLinearHomePage.setOnClickListener(this);
        mLinearUser.setOnClickListener(this);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mLinearUser = (LinearLayout) findViewById(R.id.linear_user);
        mLinearDiscovery = (LinearLayout) findViewById(R.id.linear_discovery);
        mLinearHomePage = (LinearLayout) findViewById(R.id.linear_home_page);
        mCheckBoxDiscovery = (CheckBox) findViewById(R.id.checkbox_discovery);
        mCheckBoxHomePage = (CheckBox) findViewById(R.id.checkbox_home_page);
        mCheckBoxUser = (CheckBox) findViewById(R.id.checkbox_user);
        mTextDiscovery = (TextView) findViewById(R.id.text_discovery);
        mTextHomePage = (TextView) findViewById(R.id.text_home_page);
        mTextUser = (TextView) findViewById(R.id.text_user);
    }

    /**
     * 初始化fragment
     */
    private void initFragment() {
        mUser=new User();
        mDiscovery=new Discovery();
        mHomepage=new HomePage();
        mFragmentManager=getSupportFragmentManager();
        mFragmentTransaction=mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_content, mUser);
        mFragmentTransaction.add(R.id.fragment_content, mDiscovery);
        mFragmentTransaction.add(R.id.fragment_content, mHomepage);
        mFragmentTransaction.hide(mUser);
        mFragmentTransaction.hide(mDiscovery);
        mFragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.linear_home_page:
                checkAllFalse();
                allWhite();
                mTextHomePage.setTextColor(getResources().getColor(R.color.homeBottomOrange));
                mCheckBoxHomePage.setChecked(true);
                mFragmentTransaction.show(mHomepage);
                mFragmentTransaction.hide(mUser);
                mFragmentTransaction.hide(mDiscovery);
                break;
            case R.id.linear_discovery:
                checkAllFalse();
                allWhite();
                mTextDiscovery.setTextColor(getResources().getColor(R.color.homeBottomOrange));
                mCheckBoxDiscovery.setChecked(true);
                mFragmentTransaction.hide(mHomepage);
                mFragmentTransaction.hide(mUser);
                mFragmentTransaction.show(mDiscovery);
                break;
            case R.id.linear_user:
                checkAllFalse();
                allWhite();
                mTextUser.setTextColor(getResources().getColor(R.color.homeBottomOrange));
                mCheckBoxUser.setChecked(true);
                mFragmentTransaction.hide(mHomepage);
                mFragmentTransaction.show(mUser);
                mFragmentTransaction.hide(mDiscovery);
                break;

        }
        mFragmentTransaction.commit();
    }
    /**
     * 将所有的checkbox设置为未选中状态
     */
    private void checkAllFalse() {
        mCheckBoxHomePage.setChecked(false);
        mCheckBoxDiscovery.setChecked(false);
        mCheckBoxUser.setChecked(false);
    }

    /**
     * 使底部按键下面多有的文字都变成灰色
     */
    private void allWhite() {
        mTextHomePage.setTextColor(getResources().getColor(R.color.gray));
        mTextDiscovery.setTextColor(getResources().getColor(R.color.gray));
        mTextUser.setTextColor(getResources().getColor(R.color.gray));
    }
}