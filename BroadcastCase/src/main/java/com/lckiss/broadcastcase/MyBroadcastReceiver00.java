package com.lckiss.broadcastcase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by root on 17-6-23.
 */

public class MyBroadcastReceiver00 extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "00接收成功", Toast.LENGTH_SHORT).show();
        Log.i("info","自定义的广播接收者00接收到了广播");
        Log.i("info",intent.getAction());
    }
}