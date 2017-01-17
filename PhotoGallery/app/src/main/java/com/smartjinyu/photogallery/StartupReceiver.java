package com.smartjinyu.photogallery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by smartjinyu on 2017/1/17.
 */

public class StartupReceiver extends BroadcastReceiver {
    private static String TAG = "StartupReceiver";

    @Override
    public void onReceive(Context context,Intent intent){
        Log.i(TAG,"Received broadcast intent: " + intent.getAction());
        boolean isOn = QueryPreferences.isAlarmOn(context);
        PollService.setServiceAlarm(context,isOn);
    }
}
