package com.channelsoft.sample.activity.user;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.channelsoft.sample.R;
import com.channelsoft.sample.activity.BaseActivity;
import com.channelsoft.sample.model.user.Address;
import com.channelsoft.sample.model.user.UserInfo;
import com.channelsoft.sample.util.GetPictureUtils;
import com.channelsoft.sample.util.Mpopupwindow;
import com.channelsoft.sample.util.NetWorkUtils;
import com.channelsoft.sample.util.StatusBarColor;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserMineActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mImgBack;
    private RelativeLayout mRelativewHeader;
    private LinearLayout mLinearHint;//打开popupwindow时的背景透明图
    private TextView mTextBottomLine;//定位popupwindow的view
    private View mViewUserMine;//头像
    private View mViewUserAge;//年龄
    private View mViewUserSex;//性别
    private RelativeLayout mRelativeCarema;//pop头像相机
    private RelativeLayout mRelativePicture;//pop头像图册
    private RelativeLayout mRelativeCancel;//pop头像取消
    private RelativeLayout mRelativeAge60;//pop年龄60s
    private RelativeLayout mRelativeAge70;//pop年龄70s
    private RelativeLayout mRelativeAge80;//pop年龄80s
    private RelativeLayout mRelativeAge90;//pop年龄90s
    private RelativeLayout mRelativeAgeCancel;//pop年龄取消
    private RelativeLayout mRelativeSexCancel;//pop性别取消
    private RelativeLayout mRelativeSexMale;//pop性别男
    private RelativeLayout mRelativeSexFemale;//pop性别女

    private CircleImageView mCirImgHeader;//头像
    private TextView mTextSaveUserInfo;//保存用户信息
    private File mFilePhoto;//头像的保存文件
    private GetPictureUtils mPictureUtils;//调用相机和相册获得头像的工具类
    private String mPath;//头像保存路径
    private ProgressBar mProgressBarUserInfoSave;//保存图像的进度条
    private EditText mEditName;//昵称
    private EditText mEditJob;//工作
    private TextView mTextSex;//性别
    private TextView mTextAge;//年龄
    private InputMethodManager mInputMethodManager;//软键盘管理
    private boolean mIsOpen;//isOpen为true，则表示软键盘已打开
    private static final int MAX_LENGTH = 9;//名字或工作的最大字数
    private String mUserName;//用户名
    private String mSex;//性别
    private String mAge;//年龄
    private String mJob;//工作
    private SharedPreferences.Editor editor;
    private String mUserTel = "15764240546";
    private boolean signIn=true;//是否登录的判断
    private boolean sameUser=true;//是否是同一个用户的判断

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mine);
        StatusBarColor.transStatusBarAlpha(getWindow());//将statusbar变为黑色
        initInputMethod();
        initView();
        initUserInfo();
        initUserInfoNativeStorage();
        initPopupWindow();
        initGetPictures();
        onClickListener();
    }

    /**
     * 初始化个人信息本地存储
     */
    private void initUserInfoNativeStorage() {
        SharedPreferences preferences_write = getSharedPreferences("userInfo"+mUserTel, MODE_PRIVATE);
        editor = preferences_write.edit();
    }

    /**
     * 初始化用户信息
     */
    private void initUserInfo() {
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>("UserInfo");
        query.addWhereEqualTo("tel", mUserTel);
        query.findObjects(getApplicationContext(), new FindListener<UserInfo>() {
            @Override
            public void onSuccess(List<UserInfo> list) {
                if (list.size() != 0) {
                    mUserName = list.get(list.size() - 1).getName();//获取姓名
                    mSex = list.get(list.size() - 1).getSex();//获取性别
                    mAge = list.get(list.size() - 1).getAge();//获取年龄
                    mJob = list.get(list.size() - 1).getJob();//获取工作
                    mEditName.setText(mUserName);
                    mTextSex.setText(mSex);
                    mTextAge.setText(mAge);
                    mEditJob.setText(mJob);
                }
            }

            @Override
            public void onError(int i, String s) {
            }
        });
    }

    /**
     * 初始化软键盘
     */
    private void initInputMethod() {
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mIsOpen = mInputMethodManager.isActive();//isOpen若返回true，则表示输入法打开
    }

    /**
     * 初始化头像图片
     */
    private void initGetPictures() {

        mPictureUtils = new GetPictureUtils(mFilePhoto, UserMineActivity.this);
        mPath = Environment.getExternalStorageDirectory() + "/"+mUserTel+".jpg";
        Log.d("date", "    存储的路径：" + Environment.getExternalStorageDirectory().getAbsolutePath());
        // 创建mFilePhoto文件。存储头像
        mFilePhoto = new File(mPath);
        if (NetWorkUtils.isConnection()) {
            if (mFilePhoto.exists()) {
                mCirImgHeader.setImageURI(Uri.fromFile(mFilePhoto));
            } else {
//            mCirImgHeader.setImageResource(R.mipmap.ic_launcher);
//            此处获取从user中网上下载的图片filename,并且存到文件中且设置图片
                String downloadPath = getIntent().getStringExtra("downloadFilePath");
                GetPictureUtils.saveMyBitmap(BitmapFactory.decodeFile(downloadPath), mFilePhoto);
                mCirImgHeader.setImageBitmap(BitmapFactory.decodeFile(downloadPath));
            }
        } else {
            if (mFilePhoto.exists()) {
                mCirImgHeader.setImageURI(Uri.fromFile(mFilePhoto));
            } else {

            }
        }

    }

    /**
     * 初始化popupwindow
     */
    private void initPopupWindow() {
        //点开头像popupwindow的view
        mViewUserMine = getLayoutInflater().inflate(R.layout.popupwindow_item_user_mine_picture, null);
        mRelativeCarema = (RelativeLayout) mViewUserMine.findViewById(R.id.relative_popupwindow_user_mine_item_carema);
        mRelativePicture = (RelativeLayout) mViewUserMine.findViewById(R.id.relative_popupwindow_user_mine_item_pictures);
        mRelativeCancel = (RelativeLayout) mViewUserMine.findViewById(R.id.relative_popupwindow_user_mine_item_cancel);

        //点开性别popupwindow的view
        mViewUserAge = getLayoutInflater().inflate(R.layout.popupwindow_item_user_mine_age, null);
        mRelativeAge60 = (RelativeLayout) mViewUserAge.findViewById(R.id.relative_popupwindow_user_mine_item_age_60);
        mRelativeAge70 = (RelativeLayout) mViewUserAge.findViewById(R.id.relative_popupwindow_user_mine_item_age_70);
        mRelativeAge80 = (RelativeLayout) mViewUserAge.findViewById(R.id.relative_popupwindow_user_mine_item_age_80);
        mRelativeAge90 = (RelativeLayout) mViewUserAge.findViewById(R.id.relative_popupwindow_user_mine_item_age_90);
        mRelativeAgeCancel = (RelativeLayout) mViewUserAge.findViewById(R.id.relative_popupwindow_user_mine_item_age_cancel);
        //点开年龄popupwindow的view
        mViewUserSex = getLayoutInflater().inflate(R.layout.popupwindow_item_user_mine_sex, null);
        mRelativeSexFemale = (RelativeLayout) mViewUserSex.findViewById(R.id.relative_popupwindow_user_mine_item_sex_female);
        mRelativeSexMale = (RelativeLayout) mViewUserSex.findViewById(R.id.relative_popupwindow_user_mine_item_sex_male);
        mRelativeSexCancel = (RelativeLayout) mViewUserSex.findViewById(R.id.relative_popupwindow_user_mine_item_sex_cancel);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mImgBack = (ImageView) findViewById(R.id.img_user_mine_back);
        mRelativewHeader = (RelativeLayout) findViewById(R.id.relativew_user_mine_header);
        mLinearHint = (LinearLayout) findViewById(R.id.linear_user_mine_hint);
        mTextBottomLine = (TextView) findViewById(R.id.text_user_mine_line);
        mCirImgHeader = (CircleImageView) findViewById(R.id.circleimageview_user_mine_header);
        mTextSaveUserInfo = (TextView) findViewById(R.id.text_user_mine_save);
        mProgressBarUserInfoSave = (ProgressBar) findViewById(R.id.progressbar_user_mine_save);
        mEditName = (EditText) findViewById(R.id.edit_user_mine_name);
        mEditJob = (EditText) findViewById(R.id.edit_user_mine_job);
        mTextAge = (TextView) findViewById(R.id.text_user_mine_age);
        mTextSex = (TextView) findViewById(R.id.text_user_mine_sex);
    }

    /**
     * 点击事件
     */
    private void onClickListener() {
        mImgBack.setOnClickListener(this);
        mRelativewHeader.setOnClickListener(this);
        mRelativeCarema.setOnClickListener(this);
        mRelativeCancel.setOnClickListener(this);
        mRelativePicture.setOnClickListener(this);
        mTextSaveUserInfo.setOnClickListener(this);
        mTextAge.setOnClickListener(this);
        mTextSex.setOnClickListener(this);
        mRelativeSexCancel.setOnClickListener(this);
        mRelativeSexFemale.setOnClickListener(this);
        mRelativeSexMale.setOnClickListener(this);
        mRelativeAgeCancel.setOnClickListener(this);
        mRelativeAge60.setOnClickListener(this);
        mRelativeAge70.setOnClickListener(this);
        mRelativeAge80.setOnClickListener(this);
        mRelativeAge90.setOnClickListener(this);
        //        监听edittext的变化
        mEditJob.addTextChangedListener(new TextWatcher() {
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
                selectionStart = mEditJob.getSelectionStart();
                selectionEnd = mEditJob.getSelectionEnd();
                if (temp.length() > MAX_LENGTH) {
//                    Toast.makeText(UserMineActivity.this, "只能输入九个字",
//                            Toast.LENGTH_SHORT).show();
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    mEditJob.setText(s);
                    mEditJob.setSelection(tempSelection);
                }
                mJob = s.toString();
//                editor.putString("userJob", mJob);
//                editor.commit();
                Log.d("date", "   用户的工作：" + s.toString());
            }
        });
        mEditName.addTextChangedListener(new TextWatcher() {
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
                selectionStart = mEditName.getSelectionStart();
                selectionEnd = mEditName.getSelectionEnd();
                if (temp.length() > MAX_LENGTH) {
//                    Toast.makeText(UserMineActivity.this, "只能输入九个字",
//                            Toast.LENGTH_SHORT).show();
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    mEditName.setText(s);
                    mEditName.setSelection(tempSelection);
                }
                mUserName = s.toString();
//                editor.putString("userName", mUserName);
//                editor.commit();
                Log.d("date", "   用户的工作：" + s.toString());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.relativew_user_mine_header:
//                打开popupwindow；如果此时软键盘打开，则关闭软键盘
                if (mIsOpen) {
//                    mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    mInputMethodManager.hideSoftInputFromWindow(mEditJob.getWindowToken(), 0);
                }
                Mpopupwindow.startPopupWindow(this, 4, mViewUserMine, mTextBottomLine, mLinearHint);
                break;
            case R.id.img_user_mine_back:
                finish();
                break;
            case R.id.relative_popupwindow_user_mine_item_cancel:
                Mpopupwindow.mPopupWindow.dismiss();
                break;
            case R.id.relative_popupwindow_user_mine_item_carema:
                mPictureUtils.getPhotoFromCamera(mPath);//从相机中获取头像
                break;
            case R.id.relative_popupwindow_user_mine_item_pictures:
                mPictureUtils.getPhotoFromPicture(mPath);//从相册中获取头像
                break;
            case R.id.text_user_mine_save:
                mProgressBarUserInfoSave.setVisibility(View.VISIBLE);
                updateHeadIcon(mPath);
                break;
            case R.id.text_user_mine_age:
                if (mIsOpen) {
//                    如果之前打开了软件盘，则先关闭软键盘
//                    mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    mInputMethodManager.hideSoftInputFromWindow(mEditJob.getWindowToken(), 0);

                }
                Mpopupwindow.startPopupWindow(this, 3, mViewUserAge, mTextBottomLine, mLinearHint);
                break;
            case R.id.text_user_mine_sex:
                if (mIsOpen) {
//                    mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    mInputMethodManager.hideSoftInputFromWindow(mEditJob.getWindowToken(), 0);
                }
                Mpopupwindow.startPopupWindow(this, 4, mViewUserSex, mTextBottomLine, mLinearHint);
                break;
            case R.id.relative_popupwindow_user_mine_item_sex_male:
                Mpopupwindow.mPopupWindow.dismiss();
                mTextSex.setText("男");
                mSex = "男";
                break;
            case R.id.relative_popupwindow_user_mine_item_sex_female:
                Mpopupwindow.mPopupWindow.dismiss();
                mTextSex.setText("女");
                mSex = "女";
                break;
            case R.id.relative_popupwindow_user_mine_item_sex_cancel:
                Mpopupwindow.mPopupWindow.dismiss();
                break;
            case R.id.relative_popupwindow_user_mine_item_age_60:
                Mpopupwindow.mPopupWindow.dismiss();
                mTextAge.setText("60后");
                mAge = "60后";
                break;
            case R.id.relative_popupwindow_user_mine_item_age_70:
                Mpopupwindow.mPopupWindow.dismiss();
                mTextAge.setText("70后");
                mAge = "70后";
                break;
            case R.id.relative_popupwindow_user_mine_item_age_80:
                Mpopupwindow.mPopupWindow.dismiss();
                mTextAge.setText("80后");
                mAge = "80后";
                break;
            case R.id.relative_popupwindow_user_mine_item_age_90:
                Mpopupwindow.mPopupWindow.dismiss();
                mTextAge.setText("90后");
                mAge = "90后";
                break;
            case R.id.relative_popupwindow_user_mine_item_age_cancel:
                Mpopupwindow.mPopupWindow.dismiss();
                break;
        }

    }

    /**
     * 上传修改用户数据
     *
     * @param filename 上传文件成功时的filename
     * @param userName 上传用户名
     * @param sex      性别
     * @param age      年龄
     * @param job      工作
     */
    private void updateUserInfo(final String filename, final String userName, final String sex, final String age, final String job, String tel,boolean signIn,boolean sameUser) {
        if (signIn) {
            if (!sameUser) {
                UserInfo ui = new UserInfo();
                ui.setPath(filename);
                ui.setName(userName);
                ui.setSex(sex);
                ui.setAge(age);
                ui.setJob(job);
                ui.setTel(tel);
                ui.save(getApplicationContext());
            } else {
                BmobQuery<UserInfo> query = new BmobQuery<UserInfo>("UserInfo");
                query.addWhereEqualTo("tel", tel);
                query.findObjects(getApplicationContext(), new FindListener<UserInfo>() {
                    @Override
                    public void onSuccess(List<UserInfo> list) {
                        //修改某一条的信息
                        list.get(list.size()-1).setValue("path",filename);
                        list.get(list.size()-1).setValue("name",userName);
                        list.get(list.size()-1).setValue("sex",sex);
                        list.get(list.size()-1).setValue("age",age);
                        list.get(list.size()-1).setValue("job",job);
                        list.get(list.size()-1).update(getApplicationContext());
                        Log.d("date","姓名"+userName);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
            }
        } else {
//                默认信息
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GetPictureUtils.PICTURE_FROM_CAMERA:
                    mPictureUtils.startPhotoZoom(Uri.fromFile(mFilePhoto));//调用系统剪裁工具
                    break;
                case GetPictureUtils.PICTURE_FROM_PICTURE:
                    Uri uri = data.getData();
                    mPictureUtils.startPhotoZoom(uri);//调用图片剪裁工具
                    break;
                case GetPictureUtils.PHOTO_REQUEST_CUT:
                    //裁剪相片
                    if (data != null) {
                        Bitmap photo = mPictureUtils.setPicToView(data);
                        mCirImgHeader.setImageBitmap(photo);//获得剪裁后的图片后将照片设置显示
                    }
                    if (Mpopupwindow.mPopupWindow != null && Mpopupwindow.mPopupWindow.isShowing()) {
                        Mpopupwindow.mPopupWindow.dismiss();
                    }
                    break;
            }
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 上传图片
     *
     * @param filepath 文件路径
     */
    private void updateHeadIcon(String filepath) {
        BTPFileResponse response = BmobProFile.getInstance(getApplicationContext()).upload(filepath, new UploadListener() {

            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                Log.d("date", "文件上传成功：" + fileName + ",可访问的文件地址：" + file.getUrl());
                // TODO Auto-generated method stub
                // fileName ：文件名（带后缀），这个文件名是唯一的，开发者需要记录下该文件名，方便后续下载或者进行缩略图的处理
                // url        ：文件地址
                // file        :BmobFile文件类型，`V3.4.1版本`开始提供，用于兼容新旧文件服务。
                editor.putString("userJob", mJob);
                editor.putString("userName", mUserName);
                editor.commit();
                updateUserInfo(fileName, mUserName, mSex, mAge, mJob, mUserTel,signIn,sameUser);
                mProgressBarUserInfoSave.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onProgress(int progress) {
                // TODO Auto-generated method stub
                Log.i("date", "onProgress :" + progress);
                mProgressBarUserInfoSave.setProgress(progress);
                if (progress == 100) {
                    finish();
                }
            }

            @Override
            public void onError(int statuscode, String errormsg) {
                // TODO Auto-generated method stub
                Log.i("date", "文件上传失败：" + errormsg);
                Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
