package com.channelsoft.sample.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.DownloadListener;
import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.user.MyFavourActivity;
import com.channelsoft.sample.activity.user.MyOrderAcitvity;
import com.channelsoft.sample.activity.user.settting.Settting;
import com.channelsoft.sample.activity.user.UserMineActivity;
import com.channelsoft.sample.activity.user.address.MyAddressActivity;
import com.channelsoft.sample.activity.user.signin.NoteSignIn;
import com.channelsoft.sample.model.user.SignStatus;
import com.channelsoft.sample.model.user.UserInfo;
import com.channelsoft.sample.util.NetWorkUtils;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 王宗贤 on 2015/12/16.
 */
public class User extends BaseFragment implements View.OnClickListener {
    private RelativeLayout mRelatUserMine;
    private View view;
    private File mFilePhoto;
    private CircleImageView mCircleHeader;
    private static final int RETURN_PICTURE = 0x11;
    private TextView mTextUserName;
    private TextView mTextUserJob;
    private String userTel = "15764240546";
    private String mPicturePath;
    private String mDownLoadFilePath;//网上下载的的图片路径
    private RelativeLayout mRelativeUserAddress;//送餐地址
    private RelativeLayout mRelativeUserFavour;//用户关注
    private RelativeLayout mRelativeUserOrder;//用户订单
    private RelativeLayout mRelativeUserInvite;//分享
    private RelativeLayout mRelativeUserSetting;//设置
    private SharedPreferences.Editor editor;
    private boolean mSignStatus;//登陆状态

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, null);
        initView();
        onClickListener();
        return view;

    }

    /**
     * 事件点击
     */
    private void onClickListener() {
        mRelatUserMine.setOnClickListener(this);
        mRelativeUserAddress.setOnClickListener(this);
        mRelativeUserFavour.setOnClickListener(this);
        mRelativeUserOrder.setOnClickListener(this);
        mRelativeUserInvite.setOnClickListener(this);
        mRelativeUserSetting.setOnClickListener(this);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mRelatUserMine = (RelativeLayout) view.findViewById(R.id.relative_user_mine);
        mCircleHeader = (CircleImageView) view.findViewById(R.id.circleimgageview_user_header_icon);
        mTextUserName = (TextView) view.findViewById(R.id.text_user_name);
        mTextUserJob = (TextView) view.findViewById(R.id.text_user_job);
        mRelativeUserAddress = (RelativeLayout) view.findViewById(R.id.relative_user_address);
        mRelativeUserFavour = (RelativeLayout) view.findViewById(R.id.relative_user_favour);
        mRelativeUserOrder = (RelativeLayout) view.findViewById(R.id.relative_user_order);
        mRelativeUserInvite = (RelativeLayout) view.findViewById(R.id.relative_user_invite);
        mRelativeUserSetting = (RelativeLayout) view.findViewById(R.id.relative_user_check_setting);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relative_user_mine:
                Intent intentMine = new Intent(getContext(), UserMineActivity.class);
                intentMine.putExtra("downloadFilePath", mDownLoadFilePath);
                startActivityForResult(intentMine, RETURN_PICTURE);
                break;
            case R.id.relative_user_address:
                startNewActivity(MyAddressActivity.class);
                break;
            case R.id.relative_user_favour:
                startNewActivity(MyFavourActivity.class);
                break;
            case R.id.relative_user_order:
                startNewActivity(MyOrderAcitvity.class);
                break;
            case R.id.relative_user_invite:

                break;
            case R.id.relative_user_check_setting:
                startNewActivity(Settting.class);
                break;
        }
    }

    /**
     * 打开登录界面
     */
    private void startNewActivity(Class activity) {
        Intent intent = new Intent(getContext(), activity);
        getContext().startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        mFilePhoto = new File(Environment.getExternalStorageDirectory() + "/" + userTel + ".jpg");

//        如果网络连接且头像文件不存在的时候下载头像，其他情况时使用本地头像。
        if (NetWorkUtils.isConnection()) {

            if (!mFilePhoto.exists()) {
                downloadUserInfo();
            } else {
                mCircleHeader.setImageBitmap(BitmapFactory.decodeFile(mFilePhoto.getPath()));
                initUserInfoNativeStorage();//初始化个人信息
            }
        } else {
            Toast.makeText(getContext(), "网络连接失败", Toast.LENGTH_SHORT).show();
//            if (mFilePhoto.exists()) {
//                mCircleHeader.setImageBitmap(BitmapFactory.decodeFile(mFilePhoto.getPath()));
//            }
        }
    }

    /**
     * 初始化本地个人信息数据
     */
    private void initUserInfoNativeStorage() {
        SharedPreferences preferences_read = getActivity().getSharedPreferences("userInfo" + userTel, Context.MODE_PRIVATE);
//       第一个参数传入key值 第二个参数是默认返回值  String defValue
        String name = preferences_read.getString("userName", "饭友");
        String job = preferences_read.getString("userJob", "工程师");
        mTextUserName.setText(name);
        mTextUserJob.setText(job);
    }

    /**
     * 下载用户状态信息
     * @param userID 用户的电话
     */
    private void downloadUserStatus(String userID){
        BmobQuery<SignStatus> query=new BmobQuery<SignStatus>("SignStatus");
        query.addWhereEqualTo("userID",userID);
        query.findObjects(getContext(), new FindListener<SignStatus>() {
            @Override
            public void onSuccess(List<SignStatus> list) {
                if(list.size()==0||list.get(list.size()-1).isSignIn()==false){
//               不曾注册或者为登陆的时候弹出登录界面
                    startNewActivity(NoteSignIn.class);
                }else{
                    downloadUserInfo();
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
    /**
     * 下载用户信息
     */
    private void downloadUserInfo() {
        SharedPreferences preferences_write = getContext().getSharedPreferences("userInfo" + userTel, Context.MODE_PRIVATE);
        editor = preferences_write.edit();

        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>("UserInfo");
        query.addWhereEqualTo("tel", userTel);
        query.findObjects(getContext(), new FindListener<UserInfo>() {
            @Override
            public void onSuccess(List<UserInfo> list) {
                if (list.size() != 0) {
                    mPicturePath = list.get(list.size() - 1).getPath();//获取最新的上传的图片数据
                    mTextUserName.setText(list.get(list.size() - 1).getName());
                    mTextUserJob.setText(list.get(list.size() - 1).getJob());

                    editor.putString("userJob", list.get(list.size() - 1).getJob());
                    editor.putString("userName", list.get(list.size() - 1).getName());
                    editor.commit();
                    Log.d("date", "     数组数据：" + mPicturePath);
                    downloadUserHeader();
                }
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

    /**
     * 下载头像
     */
    private void downloadUserHeader() {

        BmobProFile.getInstance(getContext()).download(mPicturePath, new DownloadListener() {

            @Override
            public void onSuccess(String fullPath) {
                // TODO Auto-generated method stub
                Log.d("date", "下载成功：" + fullPath);
                Toast.makeText(getContext(), "下载成功", Toast.LENGTH_SHORT).show();
                mCircleHeader.setImageBitmap(BitmapFactory.decodeFile(fullPath));
                mDownLoadFilePath = fullPath;
            }

            @Override
            public void onProgress(String localPath, int percent) {
                // TODO Auto-generated method stub
                Log.d("date", "download-->onProgress :" + percent);
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
                Log.d("date", "下载出错：" + statuscode + "--" + errormsg);
                Toast.makeText(getContext(), "下载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode==getActivity().RESULT_OK){
//            switch (requestCode){
//                case RETURN_PICTURE:
//                    mPicturePath=data.getStringExtra("picutre");
//                    Log.d("date","   intent返回的数据："+data.getStringExtra("picutre"));
//                    break;
//            }
//        }
//    }
}
