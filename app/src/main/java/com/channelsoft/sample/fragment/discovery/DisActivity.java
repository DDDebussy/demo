package com.channelsoft.sample.fragment.discovery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.channelsoft.sample.R;
import com.channelsoft.sample.adapter.discoveryadapter.DisActivityAdapter;
import com.channelsoft.sample.fragment.BaseFragment;
import com.channelsoft.sample.model.discovery.Welfare;
import com.channelsoft.sample.util.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王宗贤 on 2015/12/17.
 */
public class DisActivity extends BaseFragment{
    private View view;
    private RecyclerView mRecycleView;
    private DisActivityAdapter mDisActivityAdapter;
    private List<Welfare> mList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_discovery_activity, null);

        mRecycleView= (RecyclerView) view.findViewById(R.id.recycler_view_dis_activity);

        FullyLinearLayoutManager fullyLinearLayoutManager=new FullyLinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecycleView.setLayoutManager(fullyLinearLayoutManager);


        initInfo();
        mDisActivityAdapter=new DisActivityAdapter(mList,getContext());
        mRecycleView.setAdapter(mDisActivityAdapter);
        return view;
    }

    /**
     * 初始化传入信息
     */
    private void initInfo() {
        mList=new ArrayList<>();
        mList.add(new Welfare(R.mipmap.hj_guide5));
    }

}
