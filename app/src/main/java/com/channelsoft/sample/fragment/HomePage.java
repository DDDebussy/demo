package com.channelsoft.sample.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.homepager.MapActivity;
import com.channelsoft.sample.adapter.homepageradapter.CuisineAdapter;
import com.channelsoft.sample.fragment.homefragment.AllCuisineFragment;
import com.channelsoft.sample.fragment.homefragment.FocusCuisineFragment;
import com.channelsoft.sample.fragment.homefragment.NewCuisineFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 陈利津 on 2015/12/16.
 */
public class HomePage extends BaseFragment {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    aMapLocation.getLatitude();//获取纬度
                    aMapLocation.getLongitude();//获取经度
                    aMapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(aMapLocation.getTime());
                    df.format(date);//定位时间
                    mTextViewAddress.setText(aMapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果
                    aMapLocation.getCountry();//国家信息
                    aMapLocation.getProvince();//省信息
                    aMapLocation.getCity();//城市信息
                    aMapLocation.getDistrict();//城区信息
                    aMapLocation.getRoad();//街道信息
                    aMapLocation.getCityCode();//城市编码
                    aMapLocation.getAdCode();//地区编码
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    private TextView mTextViewAddress;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepage, null);
        //初始化地图
        initMap();

        mTextViewAddress = (TextView) view.findViewById(R.id.textview_address_homepage);

        mTextViewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                getActivity().startActivity(intent);
            }
        });
        //设置ViewPager
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager_homepager);
        //设置viewpager加载的个数
        viewPager.setOffscreenPageLimit(7);
        setupViewPager(viewPager);
        //设置tablayout，viewpager上的标题
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs_homepager);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void initMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 设置item
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        CuisineAdapter adapter = new CuisineAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new AllCuisineFragment(), "全部");
        adapter.addFragment(new FocusCuisineFragment(), "已关注");
        adapter.addFragment(new NewCuisineFragment(), "新厨房");
        adapter.addFragment(new AllCuisineFragment(), "全部");
        adapter.addFragment(new FocusCuisineFragment(), "已关注");
        adapter.addFragment(new NewCuisineFragment(), "新厨房");
        adapter.addFragment(new AllCuisineFragment(), "全部");
        adapter.addFragment(new FocusCuisineFragment(), "已关注");
        adapter.addFragment(new NewCuisineFragment(), "新厨房");
        viewPager.setAdapter(adapter);
    }

}
