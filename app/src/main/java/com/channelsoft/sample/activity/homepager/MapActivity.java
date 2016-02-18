package com.channelsoft.sample.activity.homepager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.BaseActivity;

import java.util.ArrayList;

public class MapActivity extends BaseActivity implements AMapLocationListener ,LocationSource , PoiSearch.OnPoiSearchListener{
    //声明变量
    private MapView mapView;
    private AMap aMap;
    private AMapLocationClient mlocationClient;
    private OnLocationChangedListener mListener;
    private AMapLocationClientOption mLocationOption;
    private PoiSearch.Query query;// Poi查询条件类
    private EditText mEditTextAddr;
    private ImageView mImageViewSearch;
    private int currentPage=1;
    private Marker locationMarker; // 选择的点
    private TextView mTextViewAround;
    private LatLonPoint p;
    private ArrayList<MarkerOptions> markerOptionsList;


    //在onCreat方法中给aMap对象赋值
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.map);
        mEditTextAddr = (EditText) findViewById(R.id.edittext_address_search_restaurant);
        mImageViewSearch = (ImageView) findViewById(R.id.circleimageview_search);
        mTextViewAround = (TextView) findViewById(R.id.textview_around_restaurant);

        mapView.onCreate(savedInstanceState);// 必须要写
        aMap = mapView.getMap();
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式，参见类AMap。
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

        mImageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startSearchPoi();
            }
        });
    }

    /**
     * 搜索周边饭馆信息
     */
    private void startSearchPoi() {
        query = new PoiSearch.Query(mEditTextAddr.getText().toString(), "餐饮服务", "北京");
        // keyWord表示搜索字符串，第二个参数表示POI搜索类型，默认为：生活服务、餐饮服务、商务住宅
        //共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域，（这里可以传空字符串，空字符串代表全国在全国范围内进行搜索）
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);//设置查第一页
        PoiSearch poiSearch = new PoiSearch(this,query);
        if(mEditTextAddr.getText().toString().equals("")){
            poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(p.getLatitude(),
                    p.getLongitude()), 1000));//设置周边搜索的中心点以及区域
        }
        poiSearch.setOnPoiSearchListener(this);//设置数据返回的监听器
        poiSearch.searchPOIAsyn();//开始搜索
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                p = new LatLonPoint(aMapLocation.getLatitude(), aMapLocation.getLongitude());
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
            startSearchPoi();
        }
    }


    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int err) {
            StringBuffer sb = new StringBuffer();
            markerOptionsList = new ArrayList();
            for (int i = 0; i < poiResult.getPois().size(); i++) {
                LatLng p = new LatLng(poiResult.getPois().get(i).getLatLonPoint().getLatitude(), poiResult.getPois().get(i).getLatLonPoint().getLongitude());
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(p)
                        .draggable(true)
                        .snippet("nihao")
                        .setFlat(true)
                        .title(poiResult.getPois().get(i).getTitle());
                markerOptionsList.add(markerOptions);
                sb.append(poiResult.getPois().get(i).getTitle() + "\n");
            }

        aMap.addMarkers(markerOptionsList,true);
        mTextViewAround.setText(sb.toString());
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
