package com.channelsoft.sample.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.channelsoft.sample.R;
import com.channelsoft.sample.fragment.discovery.DisActivity;
import com.channelsoft.sample.fragment.discovery.DisNearby;
import com.channelsoft.sample.util.MyScrollview;

/**
 * Created by 王宗贤 on 2015/12/16.
 */
public class Discovery extends BaseFragment implements View.OnClickListener{
    private View view;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private FrameLayout mFrameContent;
    private DisActivity mDisActivity;
    private DisNearby mDisNearby;
    private Button mBtnDisActivity;
    private Button mBtnDisNearby;
    private TextView mTextLeftRedLine;
    private TextView mTextRightRedLine;
    private SwipeRefreshLayout mSwipeRefresh;
    private MyScrollview myScrollview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_discovery, null);
        initView();
        initFragment();
        setClickListener();
        return view;
    }

    /**
     * 设置监听
     */
    private void setClickListener() {
        mBtnDisActivity.setOnClickListener(this);
        mBtnDisNearby.setOnClickListener(this);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(false);//等待网络请求完毕后停止刷新
            }
        });
    }

    /**
     * 初始化fragment设置
     */
    private void initFragment() {
        mDisActivity=new DisActivity();
        mDisNearby=new DisNearby();

        mFragmentManager=getActivity().getSupportFragmentManager();
        mFragmentTransaction=mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.fragment_dis_content,mDisActivity);
        mFragmentTransaction.add(R.id.fragment_dis_content,mDisNearby);
        mFragmentTransaction.show(mDisActivity);
        mFragmentTransaction.hide(mDisNearby);
        mFragmentTransaction.commit();
    }

    /**
     * 初始化布局文件
     */
    private void initView() {
        mFrameContent= (FrameLayout) view.findViewById(R.id.fragment_dis_content);
        mBtnDisActivity= (Button) view.findViewById(R.id.button_dis_activity);
        mBtnDisNearby = (Button) view.findViewById(R.id.button_dis_nearby);
        mTextLeftRedLine = (TextView) view.findViewById(R.id.linear_dis_below_activity);
        mTextRightRedLine = (TextView) view.findViewById(R.id.linear_dis_below_nearby);
        mSwipeRefresh= (SwipeRefreshLayout) view.findViewById(R.id.swipe_discovery);
        mSwipeRefresh.setColorSchemeResources(R.color.homeBottomOrange);
        myScrollview= (MyScrollview) view.findViewById(R.id.myscrollview_discovery);
    }

    @Override
    public void onClick(View view) {
        mFragmentTransaction=mFragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.button_dis_activity:
                myScrollview.smoothScrollTo(0,0);//定位scrollview到初始位置
                mTextLeftRedLine.setVisibility(View.VISIBLE);
                mTextRightRedLine.setVisibility(View.INVISIBLE);
                mFragmentTransaction.show(mDisActivity);
                mFragmentTransaction.hide(mDisNearby);
                break;
            case R.id.button_dis_nearby:
                myScrollview.smoothScrollTo(0,0);//定位scrollview到初始位置
                mTextLeftRedLine.setVisibility(View.INVISIBLE);
                mTextRightRedLine.setVisibility(View.VISIBLE);
                mFragmentTransaction.show(mDisNearby);
                mFragmentTransaction.hide(mDisActivity);
                break;
        }
        mFragmentTransaction.commit();
    }
}
