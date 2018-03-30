package com.lckiss.contentresolver;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiverSms extends AppCompatActivity {

    private TextView smsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_sms);
     smsText=(TextView)findViewById(R.id.sms_tv);
        ContentResolver resolver=getContentResolver();
        Uri uri=Uri.parse("content://sms/");
        //注册内容观察者
        resolver.registerContentObserver(uri,true,new MyObserver(new Handler()));
    }
    //自定义的内容观察者
    private class MyObserver extends ContentObserver{


        public MyObserver(Handler handler) {
            super(handler);
        }
        //当观察者观察到数据库内容改变后调用的方法


        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Toast.makeText(ReceiverSms.this,"数据库的内容变化了",Toast.LENGTH_SHORT);
            Uri uri=Uri.parse("content://sms/");
           //获取resolver对象
            ContentResolver resolver=getContentResolver();
            //查询变化的数据
            Cursor cursor=resolver.query(uri,new String[]{"address","date","type","body"},null,null,null);
            //因为短信是倒序排序，因此获取最新的一条就是第一个
            cursor.moveToFirst();
            String address=cursor.getString(0);
            String body=cursor.getString(3);
            //更改UI界面
            smsText.setText("短信内容："+body+"\n"+"短信地址："+address);
            cursor.close();
        }
    }

}
