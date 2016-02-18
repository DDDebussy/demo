package com.channelsoft.sample.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.channelsoft.sample.app.GlobalContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by amglh on 2015/12/15.
 */
public class CommonUtils {
    //该方法会判断当前sd卡是否存在，然后选择缓存地址
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo
                    (context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public static String encryptionWithMD5(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mDigest.digest().length; i++) {
                String hex = Integer.toHexString(0xFF & mDigest.digest()[i]);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            cacheKey = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    public static void showToast(String content, int time) {
        Toast.makeText(GlobalContext.getInstance(), content, time).show();
    }

    public static void showToast(String content) {
        showToast(content, Toast.LENGTH_SHORT);
    }

    public static boolean isAndroid5() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isGif(String url) {
        return !TextUtils.isEmpty(url) && url.endsWith(".gif");
    }

    public static void loadToImageView(String url, ImageView imageView) {
        if (url.endsWith("gif")) {
            loadToImageViewStaticGif(url, imageView);
        } else {
            Glide.with(GlobalContext.getInstance())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imageView);
        }
    }

    public static void loadToImageViewFitCenter(String url, ImageView imageView) {
        if (url.endsWith("gif")) {
            loadToImageViewStaticGif(url, imageView);
        } else {
            Glide.with(GlobalContext.getInstance())
                    .load(url)
                    .into(imageView);
        }
    }

    public static void loadToImageViewZoomIn(String url, ImageView imageView) {
        if (url.endsWith("gif")) {
            loadToImageViewZoomInGif(url, imageView);
        } else {
            Glide.with(GlobalContext.getInstance())
                    .load(url)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);
        }
    }

    public static void loadToImageViewZoomInGif(String url, ImageView imageView) {
        Glide.with(GlobalContext.getInstance())
                .load(url)
                .asGif()
                .fitCenter()
                .into(imageView);
    }


    public static void loadToImageViewStaticGif(String url, ImageView imageView) {
        Glide.with(GlobalContext.getInstance())
                .load(url)
                .asBitmap()
                .centerCrop()
                .into(imageView);
    }

    public static void saveImage(String url, final String name) {
        Glide.with(GlobalContext.getInstance())
                .load(url)
                .asBitmap()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                        // Do something with bitmap here.
                        saveBitmap(name,resource);
                    }

                });
    }

    public static void saveBitmap(String bitName, Bitmap mBitmap) {
        File f = new File("/sdcard/Pictures/" + bitName+".png");
        try {
            f.createNewFile();
        } catch (IOException e) {

        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
            showToast("保存成功");
        } catch (IOException e) {
            e.printStackTrace();
            showToast("保存失败");
        }
    }
}
