package com.smartjinyu.photogallery;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

/**
 * Created by smartjinyu on 2017/1/17.
 */

public class NotificationReceiver extends BroadcastReceiver {
    private static final String TAG = "NotificationReceiver";

    @Override
    public void onReceive(Context context,Intent i){
        Log.i(TAG,"received result: " +getResultCode());
        if(getResultCode() != Activity.RESULT_OK){
            //A foreground activity cancelled the broadcast
            return;
        }
        int requestCode = i.getIntExtra(PollService.REQUEST_CODE,0);
        Notification notification = (Notification) i.getParcelableExtra(PollService.NOTIFICATION);
        NotificationManagerCompat notificationManagerCompat
                =NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(requestCode,notification);
    }
}
