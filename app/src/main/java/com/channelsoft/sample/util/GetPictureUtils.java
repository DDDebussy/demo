package com.channelsoft.sample.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;


import com.channelsoft.sample.activity.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LiShuang on 2015/10/18.
 */
public class GetPictureUtils {
    public static final int PICTURE_FROM_CAMERA = 0X32;//调用系统相机获取图片
    public static final int PICTURE_FROM_PICTURE = 0X34;// 调用系统向册获取图片
    public static final int PHOTO_REQUEST_CUT = 0X35;// 结果

    private File mFile;
    private BaseActivity mActivity;

    public GetPictureUtils(File mFile, BaseActivity mActivity) {
        this.mFile = mFile;
        this.mActivity = mActivity;
    }

    /**
     * 从相册中获取图片
     * @param path 图片存储的文件的位置
     */
    public void getPhotoFromPicture(String path) {
        Intent intentPicture = new Intent();
        //设置启动相册的Action
        intentPicture.setAction(Intent.ACTION_GET_CONTENT);
        mFile = new File(path);
        if (!mFile.exists()) {
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //设置类型
        intentPicture.setType("image/*");
        //启动相册，这里使用有返回结果的启动
        mActivity.startActivityForResult(intentPicture, PICTURE_FROM_PICTURE);
    }

    /**
     * 从相机拍摄获取图片
     * @param path 图片存储的文件的位置
     */
    public void getPhotoFromCamera(String path) {
        Intent intentCamera = new Intent();
        //启动相机的Action
        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //文件的保存位置
        mFile = new File(path);
        if (!mFile.exists()) {
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //设置图片拍摄后保存的位置
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFile));
        //启动相机，这里使用有返回结果的启动
        mActivity.startActivityForResult(intentCamera, PICTURE_FROM_CAMERA);
    }
    /**
     * 对照片进行剪裁
     * @param uri 照片存储的地址。
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        mActivity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    /**
     * 获得剪切后的图片
     */
    public Bitmap setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        Bitmap photo = null;
        if (bundle != null) {
             photo = bundle.getParcelable("data");
            saveMyBitmap(photo,mFile);//将获得的头像保存。
        }
        return photo;
    }

    /**
     * 保存bitmap到sd卡
     * @param mBitmap
     * @param mFile 存图片的文件
     */
    public static void saveMyBitmap(Bitmap mBitmap,File mFile) {
        if (!mFile.exists()) {
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(mFile);
            //将图片缓存到mFileIcon中
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
