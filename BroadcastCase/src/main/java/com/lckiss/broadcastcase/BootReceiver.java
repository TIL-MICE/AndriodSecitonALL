package com.lckiss.broadcastcase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by root on 17-6-23.
 *执行开机启动指令的接收器
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i=new Intent(context,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//确定activity运行在任务栈中
        //启动
        context.startActivity(i);
    }
}
