package com.channelsoft.sample.fragment.homefragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.channelsoft.sample.R;
import com.channelsoft.sample.adapter.homepageradapter.RecyclerViewAllCuisineAdapter;
import com.channelsoft.sample.model.homepager.CuisineInfo;
import com.channelsoft.sample.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by chenlijin on 2015/12/17.
 */
public class NewCuisineFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;
    private List<CuisineInfo> mCuisineInfoList;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerViewAllCuisineAdapter mAdapter;
    private int limit = 4;		// 每页的数据是7条
    private int curPage = 1;		// 当前加载页的编号，从1开始

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_cuisine, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_new_cuisine);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefreshlayout_new_cuisine);
        mFloatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab_new_cuisine);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        initCuisineInfoList();

        //设置recyclerview滑动时的事件
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                //floatappbar的显示
                if (lastVisibleItem > 3) {
                    mFloatingActionButton.setVisibility(View.VISIBLE);
                } else {
                    mFloatingActionButton.setVisibility(View.GONE);
                }
                //lastVisibleItem >= totalItemCount - 3表示剩下3个item自动加载
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 3 && dy > 0) {
                    BmobQuery<CuisineInfo> query = new BmobQuery<CuisineInfo>("CuisineInfo");
                    //返回8条数据，如果不加上这条语句，默认返回10条数据
                    query.setLimit(limit);
                    // 根据updatedAt字段降序显示数据
                    query.order("-updatedAt");
                    //跳过八条数据
                    query.setSkip(limit*curPage);
                    curPage++;
                    //执行查询方法
                    query.findObjects(getContext(), new FindListener<CuisineInfo>() {
                        @Override
                        public void onSuccess(List<CuisineInfo> cuisineInfos) {
                            mCuisineInfoList.addAll(cuisineInfos);
                            mAdapter.setmCuisineInfoList(mCuisineInfoList);
                            mAdapter.notifyDataSetChanged();
                            curPage++;
                        }
                        @Override
                        public void onError(int code, String msg) {
                            CommonUtils.showToast("查询失败："+msg);
                        }
                    });
                }
            }
        });

        //下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initCuisineInfoList();
                curPage = 1;
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.smoothScrollToPosition(0);
            }
        });
        return view;
    }

    private void initCuisineInfoList() {
        mCuisineInfoList = new ArrayList<>();
        BmobQuery<CuisineInfo> query = new BmobQuery<CuisineInfo>("CuisineInfo");
        //返回8条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(limit);
        // 根据updatedAt字段降序显示数据
        query.order("-updatedAt");
        //执行查询方法
        query.findObjects(getContext(), new FindListener<CuisineInfo>() {
            @Override
            public void onSuccess(List<CuisineInfo> cuisineInfos) {
                mCuisineInfoList = cuisineInfos;
                mAdapter = new RecyclerViewAllCuisineAdapter(mCuisineInfoList, getActivity());
                mRecyclerView.setAdapter(mAdapter);
            }
            @Override
            public void onError(int code, String msg) {
                CommonUtils.showToast("查询失败："+msg);
            }
        });
    }

    private void onRefreshComplete(List<CuisineInfo> cuisineInfoList) {
        mAdapter.setmCuisineInfoList(cuisineInfoList);
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
