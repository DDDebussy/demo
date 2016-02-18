package com.channelsoft.sample.app;

import android.widget.Toast;

import timber.log.Timber;

/**
 * Created by amgl on 2015/9/22.
 */
public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler previousHandler;

    public ExceptionHandler() {
        this.previousHandler = null;
    }

    public ExceptionHandler(Thread.UncaughtExceptionHandler handler) {
        this.previousHandler = handler;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//        MobclickAgent.reportError(GlobalContext.getInstance(), ex);
        ex.printStackTrace();
        Timber.e(ex, ex.getMessage());

        Toast.makeText(GlobalContext.getInstance(), ex.getMessage(), Toast.LENGTH_SHORT).show();
//        GlobalContext.getInstance().exit();
        if (previousHandler != null) {
            previousHandler.uncaughtException(thread, ex);
        }
    }
}
