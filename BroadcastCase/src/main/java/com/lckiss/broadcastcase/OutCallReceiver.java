package com.lckiss.broadcastcase;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by root on 17-6-23.
 */

public class OutCallReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        //获取号码
        String outCallNumber = getResultData();
        //创建shardpreferences对象，获取该对象中存储的IP号码
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String ipnumber = sp.getString("ipnumber", "");
        //将IP号码添加到外拨号电话的前面
        Log.i("info", ipnumber + outCallNumber);
        setResultData(ipnumber + outCallNumber);

    }
}
