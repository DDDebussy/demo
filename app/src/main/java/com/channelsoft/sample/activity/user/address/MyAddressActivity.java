package com.channelsoft.sample.activity.user.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.BaseActivity;
import com.channelsoft.sample.adapter.useradapter.UserAddressAdapter;
import com.channelsoft.sample.model.user.Address;
import com.channelsoft.sample.util.StatusBarColor;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class MyAddressActivity extends BaseActivity implements View.OnClickListener {
    private Button mBtnAddAddress;
    private ImageView mImgAddressBack;
    private RecyclerView mRecyclerViewAddress;
    private UserAddressAdapter mUserAddressAdapter;
    private List<Address> mDate;
    private ProgressBar mProgressUpdate;
    private TextView mTextRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_address);
        StatusBarColor.transStatusBarAlpha(getWindow());//将statusbar变为黑色
        initView();
        initDownloadAddressInfo();
        onClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initDownloadAddressInfo();
    }

    /**
     * 初始化下载信息
     */
    private void initDownloadAddressInfo() {
        mProgressUpdate.setVisibility(View.VISIBLE);
        mRecyclerViewAddress.setVisibility(View.INVISIBLE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewAddress.setLayoutManager(layoutManager);
        mDate = new ArrayList<>();

        BmobQuery<Address> query = new BmobQuery<Address>("Address");
        query.findObjects(getApplicationContext(), new FindListener<Address>() {
            @Override
            public void onSuccess(List<Address> list) {
                mDate = list;
                mUserAddressAdapter = new UserAddressAdapter(mDate, getApplicationContext());
                mRecyclerViewAddress.setAdapter(mUserAddressAdapter);
                mProgressUpdate.setVisibility(View.GONE);
                mRecyclerViewAddress.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * 监听器
     */
    private void onClickListener() {
        mBtnAddAddress.setOnClickListener(this);
        mImgAddressBack.setOnClickListener(this);
        mTextRefresh.setOnClickListener(this);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mBtnAddAddress = (Button) findViewById(R.id.button_address_add_address);
        mImgAddressBack = (ImageView) findViewById(R.id.img_user_address_back);
        mRecyclerViewAddress = (RecyclerView) findViewById(R.id.recyclerview_user_address);
        mProgressUpdate = (ProgressBar) findViewById(R.id.progressBar_address);
        mTextRefresh= (TextView) findViewById(R.id.text_user_address_refresh);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_address_add_address:
                Intent intentAddAddress = new Intent(getApplicationContext(), AddNewAddress.class);
                startActivity(intentAddAddress);
                break;
            case R.id.img_user_address_back:
                finish();
                break;
            case R.id.text_user_address_refresh:
                initDownloadAddressInfo();
                break;
        }
    }
}
