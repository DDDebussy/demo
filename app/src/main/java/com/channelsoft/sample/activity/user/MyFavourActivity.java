package com.channelsoft.sample.activity.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.BaseActivity;
import com.channelsoft.sample.adapter.useradapter.UserFavourAdapter;
import com.channelsoft.sample.model.homepager.CuisineInfo;
import com.channelsoft.sample.util.StatusBarColor;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class MyFavourActivity extends BaseActivity implements View.OnClickListener{
    private ImageView mImgFavourBack;
    private RecyclerView mRecyclerViewFavour;
    private UserFavourAdapter mUserFavourAdapter;
    private List<CuisineInfo> mDate;
    private ProgressBar mProgressDownloadInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_favour);
        StatusBarColor.transStatusBarAlpha(getWindow());//将statusbar变为黑色
        initView();
        mDate=new ArrayList<CuisineInfo>();
        onClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRestaurantInfo();
    }

    /**
     * 初始化私厨信息
     */
    private void initRestaurantInfo() {
        mProgressDownloadInfo.setVisibility(View.VISIBLE);
        BmobQuery<CuisineInfo> query = new BmobQuery<CuisineInfo>("CuisineInfo");
//      根据updatedAt字段降序显示数据
        query.order("-updatedAt");
//        获取点击了关注的数据
        query.addWhereEqualTo("focus",true);
        query.findObjects(getApplicationContext(), new FindListener<CuisineInfo>() {
            @Override
            public void onSuccess(List<CuisineInfo> list) {
                mDate=list;
                initAdapter();
                mProgressDownloadInfo.setVisibility(View.GONE);
            }
            @Override
            public void onError(int i, String s) {
                Log.d("date","用戶关注中的信息加载失败");
            }
        });
    }

    private void initAdapter() {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerViewFavour.setLayoutManager(layoutManager);
        mUserFavourAdapter=new UserFavourAdapter(mDate,getApplicationContext());
        mRecyclerViewFavour.setAdapter(mUserFavourAdapter);
    }

    /**
     * 出发点击事件
     */
    private void onClickListener() {
        mImgFavourBack.setOnClickListener(this);
    }

    /**
     * 初始化布局文件
     */
    private void initView() {
        mImgFavourBack= (ImageView) findViewById(R.id.img_user_favour_back);
        mRecyclerViewFavour= (RecyclerView) findViewById(R.id.recycler_view_favour);
        mProgressDownloadInfo= (ProgressBar) findViewById(R.id.progressbar_user_favour);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_user_favour_back:
                finish();
                break;
        }
    }
}
