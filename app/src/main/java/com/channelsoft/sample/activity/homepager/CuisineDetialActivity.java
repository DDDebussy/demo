package com.channelsoft.sample.activity.homepager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.BaseActivity;
import com.channelsoft.sample.adapter.homepageradapter.RecyclerViewAllCuisineAdapter;
import com.channelsoft.sample.adapter.homepageradapter.RecyclerViewCuisineDetialAdapter;
import com.channelsoft.sample.adapter.homepageradapter.RecyclerViewDetialWindowAdapter;
import com.channelsoft.sample.model.homepager.CuisineInfo;
import com.channelsoft.sample.model.homepager.NaShouCaiInfo;
import com.channelsoft.sample.util.CommonUtils;
import com.channelsoft.sample.util.Mpopupwindow;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class CuisineDetialActivity extends BaseActivity {
    private ImageView mImageView;
    private RecyclerView mRecyclerView;
    private List<NaShouCaiInfo> mNaShouCaiInfoList;
    private RecyclerViewCuisineDetialAdapter mAdapter;
    private RecyclerViewDetialWindowAdapter mAdapterWimdow;
    private TextView mTextViewPrice;
    private String mCanguanName;
    private RecyclerView mRecyclerViewWindow;
    private TextView mTextviewOk;
    private TextView mTextViewPriceWindow;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_detial);
        //设置顶部的imageview
        mImageView = (ImageView) findViewById(R.id.imageview_cuisine_detial);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_detial);
        mTextViewPrice = (TextView) findViewById(R.id.textview_price_activity_detail);
        mTextviewOk = (TextView) findViewById(R.id.textview_ok_detial);
        //PopupWindow
        mView = getLayoutInflater().inflate(R.layout.window_selected_detail, null);
        mTextViewPriceWindow = (TextView) mView.findViewById(R.id.textview_price_activity_detail_window);

        //查询厨房详细信息的数据
        final Intent intent = getIntent();
        mCanguanName = intent.getStringExtra(RecyclerViewAllCuisineAdapter.CANGUAN_NAME);
        initNaShouCaiList();
        //设置toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_cuisine_detial);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_cuisine_detial);
        //设置CollapsingToolbarLayout的标题文字
        collapsingToolbar.setTitle(mCanguanName);

        mTextviewOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((Integer.parseInt(mTextViewPrice.getText().toString().trim())>0)) {
                    Intent intentPay = new Intent(CuisineDetialActivity.this, PayActivity.class);
                    String price = mTextViewPrice.getText().toString();
                    intentPay.putExtra("money", price);
                    startActivity(intentPay);
                }
            }
        });


        /**
         *点击价格显示价格详情
         */
        mTextViewPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PopupWindow
                if ((Integer.parseInt(mTextViewPrice.getText().toString().trim())>0)) {
                    Mpopupwindow.startPopupWindow(CuisineDetialActivity.this, 3, mView, mTextViewPrice, null);
                    mRecyclerViewWindow = (RecyclerView) mView.findViewById(R.id.recyclerview_window);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    mRecyclerViewWindow.setLayoutManager(layoutManager);
                    mRecyclerViewWindow.setAdapter(mAdapterWimdow);


                    TextView textViewpay = (TextView) mView.findViewById(R.id.textview_ok_detial_window);
                    textViewpay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ((Integer.parseInt(mTextViewPrice.getText().toString().trim())>0)) {
                                Intent intentPay = new Intent(CuisineDetialActivity.this, PayActivity.class);
                                String price = mTextViewPrice.getText().toString();
                                intentPay.putExtra("money", price);
                                startActivity(intentPay);
                            }
                        }
                    });
                    //window中价格变化的监听
                    mAdapterWimdow.setOnPriceChangeListener(new RecyclerViewDetialWindowAdapter.OnPriceChangeListener() {
                        @Override
                        public void onPriceChange(int price) {
                            mTextViewPrice.setText(price+"");
                            mTextViewPriceWindow.setText(price+"");
                            mAdapter.setPrice(price);
                        }
                    });

                    Mpopupwindow.mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            mAdapter.setmCuisineInfoList(mNaShouCaiInfoList);
                            mAdapter.notifyDataSetChanged();
                            Mpopupwindow.backgroundAlpha(1f, CuisineDetialActivity.this);
                        }
                    });

                }
            }
        });


    }


    private void initNaShouCaiList() {
        mNaShouCaiInfoList = new ArrayList<>();
        BmobQuery<NaShouCaiInfo> query = new BmobQuery<NaShouCaiInfo>("NaShouCaiInfo");
        query.addWhereEqualTo("canguanName", mCanguanName);
        query.findObjects(this, new FindListener<NaShouCaiInfo>() {
            @Override
            public void onSuccess(List<NaShouCaiInfo> list) {
                //recyclerview
                mNaShouCaiInfoList = list;
                mAdapter = new RecyclerViewCuisineDetialAdapter(mNaShouCaiInfoList, getApplicationContext());
                RecyclerViewHeader header = RecyclerViewHeader.fromXml(getApplicationContext(), R.layout.header_nashoucai_info);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(mAdapter);
                header.attachTo(mRecyclerView);

                //显示总价
                mAdapter.setOnPriceChangeListener(new RecyclerViewCuisineDetialAdapter.OnPriceChangeListener() {
                    @Override
                    public void onPriceChange(int price) {
                        mTextViewPrice.setText(price + " ");
                        mTextViewPriceWindow.setText(price + " ");
                        mAdapterWimdow.setPrice(price);
                    }
                });

                mAdapterWimdow = new RecyclerViewDetialWindowAdapter(mNaShouCaiInfoList, CuisineDetialActivity.this);
            }

            @Override
            public void onError(int i, String s) {
                CommonUtils.showToast("加载数据失败");
            }
        });
    }

    //actionbar导入menu中的设置栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //设置menu上item的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //返回
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                BmobQuery<CuisineInfo> query = new BmobQuery<CuisineInfo>("CuisineInfo");
                query.addWhereEqualTo("canguanName", mCanguanName);
                query.findObjects(this, new FindListener<CuisineInfo>() {
                    @Override
                    public void onSuccess(List<CuisineInfo> list) {
                        //focus有可能为空
                        if ((list.get(0).getFocus() != null) && list.get(0).getFocus()) {
                            list.get(0).setValue("focus", false);
                            list.get(0).update(getApplication());
                            CommonUtils.showToast("已取消关注");
                        } else {
                            list.get(0).setValue("focus", true);
                            list.get(0).update(getApplication());
                            CommonUtils.showToast("关注成功");
                        }
                    }

                    @Override
                    public void onError(int i, String s) {
                        CommonUtils.showToast("操作失败");
                    }
                });
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
