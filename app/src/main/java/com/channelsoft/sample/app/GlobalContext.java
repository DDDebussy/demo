package com.channelsoft.sample.app;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;

import cn.bmob.push.BmobPush;
import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import timber.log.Timber;

/**
 * Created by amglh on 2015/12/15.
 */
public class GlobalContext extends Application {
    private static final String BUG_TAGS_APP_KEY = "17644739dda960bbdcd374332f341fc5";
    private static final String CACHE_FOLDER_NAME = "SampleCache";
    private static final String BMOB_APP_ID = "d224b928c46bee9e985040e97b6fead2";

    //Application 实例
    private static GlobalContext instance = null;

    public static GlobalContext getInstance() {
        return instance;
    }

    public static String DEVICE_INFO = null;
    public static String APP_INFO = null;
    public static String APP_VERSION = null;
    public static int APP_VERSION_CODE = 0;
    private static String APP_PACKAGE = null;
    private static String ANDROID_VERSION = null;
    private static String PHONE_MODEL = null;
    private static String PHONE_MANUFACTURER = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initLog();
        initConstants();
        initBugTags();
        initBmob();
        initBmobPush();
        initBmobSms();

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(Thread.getDefaultUncaughtExceptionHandler()));


    }

    //初始化短信推送
    private void initBmobSms() {
        BmobSMS.initialize(this,BMOB_APP_ID);
    }

    private void initBmobPush() {
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this, BMOB_APP_ID);
    }

    private void initBmob() {
        // 初始化 Bmob SDK
        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, BMOB_APP_ID);
    }

    private void initLog() {
        Timber.plant(new Timber.DebugTree());
    }

    private void initConstants() {
        ANDROID_VERSION = android.os.Build.VERSION.RELEASE;
        PHONE_MODEL = android.os.Build.MODEL;
        PHONE_MANUFACTURER = android.os.Build.MANUFACTURER;

        StringBuilder sb = new StringBuilder();
        sb.append("PHONE_MANUFACTURER: ").append(PHONE_MANUFACTURER).append("\n").append("PHONE_MODEL: ").append(PHONE_MODEL).append("\n").append("ANDROID_VERSION: ").append(ANDROID_VERSION).append("\n");
        DEVICE_INFO = sb.toString();

        PackageManager packageManager = this.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    this.getPackageName(), 0);
            APP_VERSION = packageInfo.versionName;
            APP_VERSION_CODE = packageInfo.versionCode;
            APP_PACKAGE = packageInfo.packageName;

            sb = new StringBuilder();
            sb.append("APP_PACKAGE: ").append(APP_PACKAGE).append("\n").append("APP_VERSION: ").append(APP_VERSION).append("\n");
            APP_INFO = sb.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Timber.d("====== device-info ======\n%s====== app-info ======\n%s", DEVICE_INFO, APP_INFO);
    }

    private void initBugTags() {
        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingLocation(true).//是否获取位置
                trackingCrashLog(true).//是否收集crash
                trackingConsoleLog(true).//是否收集console log
                trackingUserSteps(true).//是否收集用户操作步骤
                crashWithScreenshot(true).//crash附带图
                versionName(APP_VERSION).//自定义版本名称
                versionCode(APP_VERSION_CODE).//自定义版本号
                build();
        Bugtags.start(BUG_TAGS_APP_KEY, this, Bugtags.BTGInvocationEventBubble, options);
    }
}
