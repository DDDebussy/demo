package com.channelsoft.sample.fragment.discovery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.channelsoft.sample.R;
import com.channelsoft.sample.adapter.discoveryadapter.DisNearbyAdapter;
import com.channelsoft.sample.fragment.BaseFragment;
import com.channelsoft.sample.model.discovery.NeighborComments;
import com.channelsoft.sample.util.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王宗贤 on 2015/12/17.
 */
public class DisNearby extends BaseFragment {
    private View view;
    private RecyclerView mRecycleView;
    private DisNearbyAdapter mDisNearbyAdapter;
    private List<NeighborComments> mList;
    private RecyclerView.LayoutManager mLayoutManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_discovery_nearby,null);
        mRecycleView= (RecyclerView) view.findViewById(R.id.recycler_view_dis_nearby);

        FullyLinearLayoutManager fullyLinearLayoutManager=new FullyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        mRecycleView.setLayoutManager(fullyLinearLayoutManager);


        initInfo();
        mDisNearbyAdapter=new DisNearbyAdapter(mList,getContext());
        mRecycleView.setAdapter(mDisNearbyAdapter);

//        //自动加载
//        mRecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int lastVisibleItem = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
//                int totalItemCount = mLayoutManager.getItemCount();
//                //lastVisibleItem >= totalItemCount - 4 表示剩下4个item自动加载，各位自由选择
//                // dy>0 表示向下滑动
//                if (lastVisibleItem >= totalItemCount - 4 && dy > 0) {
//
//                }
//            }
//        });
        return view;
    }
    /**
     * 初始化传入信息
     */
    private void initInfo() {
        mList=new ArrayList<>();
        for(int i=0;i<10;i++){
            mList.add(new NeighborComments(R.mipmap.ic_launcher));
        }
    }
}
