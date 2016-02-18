package com.channelsoft.sample.receiver;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.channelsoft.sample.MainActivity;
import com.channelsoft.sample.R;
import com.channelsoft.sample.app.GlobalContext;

import cn.bmob.push.PushConstants;

public class MyPushMessageReceiver extends BroadcastReceiver {

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Toast.makeText(context, "BmobPushDemo收到消息："+intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING), Toast.LENGTH_SHORT).show();
            String str = intent.getStringExtra(PushConstants.EXTRA_PUSH_MESSAGE_STRING);

            NotificationManager notificationManager = (NotificationManager) GlobalContext.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intentRV = new Intent(GlobalContext.getInstance(),MainActivity.class);
            PendingIntent pending = PendingIntent.getActivity(GlobalContext.getInstance(), 1, intent, PendingIntent.FLAG_ONE_SHOT);
            Notification notification = new Notification.Builder(GlobalContext.getInstance()).setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker("我是一个消息").setContentText("我是标题").setContentText(str).setContentInfo("我是信息")
                    .setAutoCancel(true).setContentIntent(pending)
                    .setWhen(System.currentTimeMillis()).build();
            notificationManager.notify(1,notification);
        }
    }

}