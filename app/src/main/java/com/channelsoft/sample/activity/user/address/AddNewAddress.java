package com.channelsoft.sample.activity.user.address;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.BaseActivity;
import com.channelsoft.sample.activity.homepager.MapActivity;
import com.channelsoft.sample.model.user.Address;
import com.channelsoft.sample.util.StatusBarColor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class AddNewAddress extends BaseActivity implements View.OnClickListener {
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
                    mTextAddAddressLoaction.setText(aMapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果
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

    private EditText mEditAddAddressName;
    private EditText mEditAddAddressTel;
    private TextView mTextAddAddressSave;
    private ImageView mImgAddAddressBack;
    private TextView mTextAddAddressLoaction;
    private RelativeLayout mRelativeAddAddressTitle;
    private RelativeLayout mRelativeEditAddressTitle;
    private TextView mTextEditSave;
    private ImageView mImgEditBack;
    private Button mBtnDelete;
    private static final int MAX_LENGTH = 9;
    private static final int TEL_MAX_LENGTH = 15;
    private int mPosition;//编辑地址信息时信息的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_address_add_new_address);
        StatusBarColor.transStatusBarAlpha(getWindow());//将statusbar变为黑色
        initView();
        initMap();
        initAddresInfo();
        onClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 初始化地址信息
     */
    private void initAddresInfo() {
        if ("edit".equals(getIntent().getStringExtra("status"))) {
            mBtnDelete.setVisibility(View.VISIBLE);
            mRelativeEditAddressTitle.setVisibility(View.VISIBLE);
            mRelativeAddAddressTitle.setVisibility(View.GONE);
//          通过intent获得数据
            mPosition = getIntent().getIntExtra("position", 0);
            String name = getIntent().getStringExtra("name");
            String tel = getIntent().getStringExtra("tel");
            String address = getIntent().getStringExtra("address");
            mEditAddAddressName.setText(name);
            mEditAddAddressTel.setText(tel);
            mTextAddAddressLoaction.setText(address);


////            从服务器端获取数据
//            BmobQuery<Address> query = new BmobQuery<Address>("Address");
////        String [] hobby = {"filename"};
////        query.addWhereContainsAll("filename", Arrays.asList(hobby));
//            query.findObjects(getApplicationContext(), new FindListener<Address>() {
//                @Override
//                public void onSuccess(List<Address> list) {
//                    mEditAddAddressName.setText(list.get(mPosition).getUserName());
//                    mEditAddAddressTel.setText(list.get(mPosition).getUserTel());
//                    mTextAddAddressLoaction.setText(list.get(mPosition).getUserAddress());
//                    Log.d("date", "     姓名：" + list.get(mPosition).getUserName());
//                    Log.d("date", "     电话：" + list.get(mPosition).getUserTel());
//                    Log.d("date", "     地址：" + list.get(mPosition).getUserAddress());
//                }
//
//                @Override
//                public void onError(int i, String s) {
//
//                }
//            });
        } else {
            mBtnDelete.setVisibility(View.INVISIBLE);
            mRelativeEditAddressTitle.setVisibility(View.GONE);
            mRelativeAddAddressTitle.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 触发点击事件
     */
    private void onClickListener() {
        mImgAddAddressBack.setOnClickListener(this);
        mTextAddAddressSave.setOnClickListener(this);
        mTextEditSave.setOnClickListener(this);
        mImgEditBack.setOnClickListener(this);
        mBtnDelete.setOnClickListener(this);
        mTextAddAddressLoaction.setOnClickListener(this);
        mEditAddAddressTel.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("date", "onTextChanged 被执行---->s=" + s + "----start=" + start
                        + "----before=" + before + "----count" + count);
                temp = s;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("date", "beforeTextChanged 被执行----> s=" + s + "----start=" + start
                        + "----after=" + after + "----count" + count);
            }

            public void afterTextChanged(Editable s) {
                Log.d("date", "afterTextChanged 被执行---->" + s);
                selectionStart = mEditAddAddressTel.getSelectionStart();
                selectionEnd = mEditAddAddressTel.getSelectionEnd();
                if (temp.length() > TEL_MAX_LENGTH) {
//                    Toast.makeText(UserMineActivity.this, "只能输入九个字",
//                            Toast.LENGTH_SHORT).show();
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    mEditAddAddressTel.setText(s);
                    mEditAddAddressTel.setSelection(tempSelection);
                }
                Log.d("date", "   用户的工作：" + s.toString());
            }
        });
        mEditAddAddressName.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("date", "onTextChanged 被执行---->s=" + s + "----start=" + start
                        + "----before=" + before + "----count" + count);
                temp = s;
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("date", "beforeTextChanged 被执行----> s=" + s + "----start=" + start
                        + "----after=" + after + "----count" + count);
            }

            public void afterTextChanged(Editable s) {
                Log.d("date", "afterTextChanged 被执行---->" + s);
                selectionStart = mEditAddAddressName.getSelectionStart();
                selectionEnd = mEditAddAddressName.getSelectionEnd();
                if (temp.length() > MAX_LENGTH) {
//                    Toast.makeText(UserMineActivity.this, "只能输入九个字",
//                            Toast.LENGTH_SHORT).show();
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    mEditAddAddressName.setText(s);
                    mEditAddAddressName.setSelection(tempSelection);
                }
                Log.d("date", "   用户的工作：" + s.toString());
            }
        });
    }

    /**
     * 初始化布局文件
     */
    private void initView() {
        mEditAddAddressName = (EditText) findViewById(R.id.edit_add_address_name);
        mImgAddAddressBack = (ImageView) findViewById(R.id.img_user_add_address_back);
        mTextAddAddressSave = (TextView) findViewById(R.id.text_add_address_save);
        mEditAddAddressTel = (EditText) findViewById(R.id.edit_add_address_tel);
        mEditAddAddressTel.setInputType(EditorInfo.TYPE_CLASS_PHONE);//使点击edittext时打开数字键盘
        mTextAddAddressLoaction = (TextView) findViewById(R.id.text_add_address_location);
        mRelativeAddAddressTitle = (RelativeLayout) findViewById(R.id.relative_add_address_title);
        mRelativeEditAddressTitle = (RelativeLayout) findViewById(R.id.relative_edit_address_title);
        mImgEditBack = (ImageView) findViewById(R.id.img_user_edit_address_back);
        mTextEditSave = (TextView) findViewById(R.id.text_edit_address_save);
        mBtnDelete = (Button) findViewById(R.id.button_edit_address_delete);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_user_add_address_back:
                finish();
                break;
            case R.id.img_user_edit_address_back:
                finish();
                break;
            case R.id.text_add_address_save:
                upDateAddressInfo(mEditAddAddressName.getText().toString(), mEditAddAddressTel.getText().toString(), mTextAddAddressLoaction.getText().toString());
                break;
            case R.id.text_edit_address_save:
                changeAdderssInfo(mPosition);
                break;
            case R.id.button_edit_address_delete:
                deleteAddressInfo(mPosition);
                break;
            case R.id.text_add_address_location:
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 删除数据
     *
     * @param position 删除位于position位置的数据
     */
    private void deleteAddressInfo(final int position) {
        BmobQuery<Address> query = new BmobQuery<Address>("Address");
        query.findObjects(getApplicationContext(), new FindListener<Address>() {
            @Override
            public void onSuccess(List<Address> list) {
                list.get(position).delete(getApplicationContext(), list.get(position).getObjectId(), new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(AddNewAddress.this, "删除成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
                list.get(position).update(getApplicationContext());
            }

            @Override
            public void onError(int i, String s) {

            }
        });

        finish();
    }


    /**
     * 修改数据
     *
     * @param position 修改position位置的数据
     */
    private void changeAdderssInfo(final int position) {
        BmobQuery<Address> query = new BmobQuery<Address>("Address");
        query.findObjects(getApplicationContext(), new FindListener<Address>() {
            @Override
            public void onSuccess(List<Address> list) {
                //修改某一条的信息
                list.get(position).setValue("userName", mEditAddAddressName.getText().toString());
                list.get(position).setValue("userTel", mEditAddAddressTel.getText().toString());
                list.get(position).setValue("userAddress", mTextAddAddressLoaction.getText().toString());
                list.get(position).update(getApplicationContext());
                Toast.makeText(AddNewAddress.this, "修改成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int i, String s) {

            }
        });

        finish();
    }

    /**
     * 上传收货数据
     *
     * @param addr 收货地址
     * @param name 收货人姓名
     * @param tel  收货人电话
     */
    private void upDateAddressInfo(String name, String tel, String addr) {
        Address address = new Address();
        address.setUserName(name);
        address.setUserTel(tel);
        address.setUserAddress(addr);
        address.save(getApplicationContext());
        Toast.makeText(AddNewAddress.this, "添加成功", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void initMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
}
