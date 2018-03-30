package com.lckiss.broadcastcase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by root on 17-6-23.
 * TODO 一句话描述这个文件的作用
 */

public class MyBroadcastReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "接收成功", Toast.LENGTH_SHORT).show();
        Log.i("info","自定义的广播接收者接收到了广播");
        abortBroadcast();//拦截广播
        Log.i("info",intent.getAction());
        Log.i("info","广播已拦截");
    }
}
