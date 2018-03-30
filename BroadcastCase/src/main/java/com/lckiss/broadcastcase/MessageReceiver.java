package com.lckiss.broadcastcase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by root on 17-6-23.
 */

public class MessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //获取所有短信数据
        Object[] objects = (Object[]) intent.getExtras().get("pdus");
        for (Object obj : objects) {
            //将Pdu中的对象转化成SmsMessage对象
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
            String body = smsMessage.getMessageBody();
            String sender = smsMessage.getOriginatingAddress();
            Log.i("info", "body:" + body);
            Log.i("info", "sender:" + sender);
            if ("15555215556".equals(sender)) {
                Log.i("info", "垃圾短信,立即终止");
                abortBroadcast();
            }
        }
    }
}
